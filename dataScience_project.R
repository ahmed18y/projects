library(shiny)
library(dplyr)
library(ggplot2)
library(arulesViz)
library(reader)
library(arules)

ui <- fluidPage(
  titlePanel("Customer Spending Analysis and K-means Clustering"),
  sidebarLayout(
    sidebarPanel(
      fileInput("file1", "Upload CSV File", accept = ".csv"),
      numericInput("min_sup", "Enter Minimum Support", value = 0.01, min = 0, step = 0.01),
      numericInput("min_conf", "Enter Minimum Confidence", value = 0.5, min = 0, step = 0.01),
      numericInput("num_centroids", "Enter Number of Clusters (2-4)", value = 3, min = 2, max = 4)
    ),
    mainPanel(
      tabsetPanel(
        tabPanel("Data Overview",
                 tableOutput("data_table"),
                 textOutput("duplicate_summary"),
                 textOutput("missing_values")),
        tabPanel("Visualization",
                 plotOutput("age_total_plot"),
                 plotOutput("payment_pie_chart"),
                 plotOutput("city_total_barplot"),
                 plotOutput("total_histogram")),
        tabPanel("Clustering",
                 plotOutput("kmeans_plot"),
                 tableOutput("clustering_table")),
        tabPanel("Association Rules",
                 verbatimTextOutput("association_rules"),
                 plotOutput("association_plot"))
      )
    )
  )
)

server=function(input, output) {
  data=reactive({
    req(input$file1)
    grc=read.csv(input$file1$datapath)
    df=distinct(grc)
    if (!"count" %in% colnames(df)) return(df)
    outlier=boxplot.stats(df$count)$out
    df=df[-which(df$count%in%outlier),]
    return(df)
  })
  
  output$data_table=renderTable({
    data()
  })
  
  output$duplicate_summary=renderText({
    df=data()
    paste("Number of duplicated rows:", sum(duplicated(df)))
  })
  
  output$missing_values=renderText({
    df=data()
    paste("Number of missing values:", sum(is.na(df)))
  })
  
  output$age_total_plot <- renderPlot({
    dff <- data()
    age_total <- aggregate(total ~ age, data = dff, sum)
    barplot(height = age_total$total, names.arg = age_total$age, xlab = "Ages", 
            ylab = "Total", main = "Compare Age and Total Spending", col = "pink")
  })
  
  output$payment_pie_chart <- renderPlot({
    dff <- data()
    payment_summary <- aggregate(total ~ paymentType, data = dff, sum)
    x = table(payment_summary$total)
    percentage = paste0(100 * (x / sum(x)), "%")
    pie(x, labels = percentage, main = "Compare Pays by Count", col = c("blue", "red"))
    legend("topright", legend = c("Cash", "Credit"), fill = c("blue", "red"))
  })
  
  output$city_total_barplot <- renderPlot({
    dff <- data()
    city_total <- aggregate(total ~ city, data = dff, sum)
    city_total <- city_total %>% arrange(desc(total))
    barplot(height = city_total$total, names.arg = city_total$city, col = "black", 
            xlab = "Total Spending", ylab = "City")
  })
  
  output$total_histogram=renderPlot({
    dff=data()
    hist(dff$total, col = "red", border = "blue", main = "Total Frequency", xlab = "Total", ylab = "Frequency")
  })
  
  output$kmeans_plot <- renderPlot({
    dff=data()
    integercolumns <- dff[, c("age", "total")]
    scaled_data <- scale(integercolumns)
    num_centroids <- input$num_centroids
    if(num_centroids >= 2 && num_centroids <= 4){
      kmeans_result <- kmeans(integercolumns, centers = num_centroids)
      dff$Cluster <- as.factor(kmeans_result$cluster)
      ggplot(dff, aes(x = age, y = total, color = Cluster)) +
        geom_point(size = 3) +
        scale_color_manual(values = c("blue", "lightgreen", "red", "black")) +
        labs(title = "K-means Clustering: Age vs Total Spending", x = "Age", y = "Total Spending") +
        theme_minimal()
    }
  })
  
  output$clustering_table <- renderTable({
    dff <- data()
    integercolumns <- dff[, c("age", "total")]
    kmeans_result <- kmeans(integercolumns, centers = input$num_centroids)
    dff$Cluster <- as.factor(kmeans_result$cluster)
    return(dff)
  })
  
  output$association_rules <- renderPrint({
    dff <- data()
    tran = strsplit(as.character(dff$items), ",")
    transactions = as(tran, "transactions")
    rules <- apriori(transactions, parameter = list(supp = input$min_sup, conf = input$min_conf))
    if (length(rules) == 0) {
      return("No rules generated with the given support and confidence.")
    } else {
      top_rule <- sort(rules, by = "lift")
      inspect(top_rule)
    }
  })
  
  output$association_plot <- renderPlot({
    dff <- data()
    tran = strsplit(as.character(dff$items), ",")
    transactions = as(tran, "transactions")
    rules <- apriori(transactions, parameter = list(supp = input$min_sup, conf = input$min_conf))
    if (length(rules) > 0) {
      plot(rules, method = "graph", control = list(
        type = "items", edgeCol = "blue", nodeCol = c("red", "lightgreen", "black")), 
        main = "Customized Association Rules Graph")
    }
  })
}

shinyApp(ui = ui, server = server)
