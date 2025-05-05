
package finalprojectprogramming;

import java.util.Scanner;

public class FinalProjectprogramming {

    public static void main(String[] args) {
  /*
this app depents on this idea
------------------------------

n refers to number of books in the library 
        // used in the check for loops and adding and removing books
        //begining from 0 to max - 1

j is the index we get after check get initial value = -1 in each iteration
    //-1 refers to invalid input

max refers to the maximum index in the table

============================================================================================================
||index          |     0      |     1      |     2      |     3      |....................|     max-1       ||
============================================================================================================
||bookname       |bookname0   |bookname1   |bookname2   |bookname3   |....................|booknamemax-1    ||
============================================================================================================
||description    |description0|description1|description2|description3|....................|descriptionmax-1 ||
============================================================================================================
||id             |id0         |id1         |id2         |id3         |....................|idmax-1          ||
============================================================================================================
||issue          |issue0      |issue1      |issue2      |issue3      |....................|issuemax-1       ||
============================================================================================================
 
*/



Scanner input = new Scanner(System.in);
         
        
    //book the memory
    //make the empty table
    System.out.println("what's the max number of books?");
    int max = input.nextInt();
    String names [] = new String [max];
    int id [] = new int [max]; 
    String des [] = new String [max];
    String issue [] = new String [max];
    int n = 0 ;
    int c;
    
        do{
        int j=-1; 
        
        //getting our operation
        System.out.println("Welcome to the library management system!");
        System.out.println("1.Add a book");
        System.out.println("2.Search for a book (by title or ID)");
        System.out.println("3.Issue a book (by Id)");
        System.out.println("4.Return a book (by ID)");
        System.out.println("5.Delete a book (by ID)");
        System.out.println("6.Edit book details (title/Descripsion by ID)");
        System.out.println("7.View all books");
        System.out.println("8.Exit");
        c = input.nextInt();
                
        
        //operations
        
            //create
            if(c==1){
                //check if you reched the max
                if(n<max){
                    System.out.println("Enter the name of the book");
                    String nameCheck = input.next();
                
                
                //check if the book name is tooken
                boolean used=false;
                for(int i=0;i<n;i++)
                    if(nameCheck.equals(names[i]))used=true;

                if(used==true)System.out.println("the book name is already used");
                else{
                    //making dataframe
                    names[n]=nameCheck;
                    id[n]=n;
                    issue[n]="available";
                    System.out.println("Enter the description of the book");
                    des[n]=input.next();
                    System.out.println("id : "+id[n]);            
                    n++; 
                }      
                }else System.out.println("you reached the maximum limit already"); 
            }


            //search
            else if (c==2){
                if(n==0)
                    System.out.println("There is no books to be edited");
                else{
                    //ask the user if he has the id or the title
                    System.out.println("what's your available information to the book");
                    System.out.println("if 1 by id else by name");
                    int check = input.nextInt();
                    
                    
                    //check input
                    if (check==1){
                        
                        
                        //check by ID
                        System.out.println("Enter the ID of the book");
                        int idCheck = input.nextInt(); 
                        for (int i=0;i<n;i++){
                            if(idCheck==id[i])
                                j=i;
                        }
                        
                        //result of the search
                        if(j==-1)System.out.println("invalid ID");
                        else 
                        {
                            System.out.println("The name is "+names[j]);
                            System.err.println("The current state is "+issue[j]);
                        }
                    }
                    else {
                            //check by name
                            System.out.println("Enter the name of the book");
                            String nameCheck = input.next();                                    
                            for (int i=0;i<n;i++){
                                if((nameCheck.toUpperCase()).equals(names[i].toUpperCase()))
                                    j=i;
                            }

                            if(j==-1)System.out.println("invallid Name");
                            //result of the search
                            else {
                                System.out.println("The id = "+ id[j]);
                                System.err.println("The current state is "+issue[j]);
                            }
                    }
                }
            }


            //issue 
            else if(c==3){
                if(n==0)
                    System.out.println("There is no books to be borrowed");
                else{
                    //check by ID
                    System.out.println("Enter the ID of the book");
                    int idCheck = input.nextInt(); 
                    for (int i=0;i<n;i++){
                        if(idCheck==id[i])
                            j=i;
                    }
                    
                    // take the issue from the user          
                    if(j==-1)System.out.println("invalid ID");
                    else {
                        System.out.println("The name is "+names[j]);
                        if(j==-1)System.out.println("invalid ID");
                        else{
                            if(issue[j].equals("available")){
                                System.out.println("are you sure you want take this book");
                                System.out.println("if yes Enter 1 if no enter any other number");
                                int borrow = input.nextInt();
                                    if(borrow==1){
                                        issue[j]= "unavailable" ;
                                        System.out.println("The book has been taken");    
                                    }
                            }else
                                System.out.println("this book is already taken");
                        }
                    }
                }
            }

            
            //return
            else if (c==4){

                if(n==0)
                    System.out.println("There is no books to return");
                else{
                    //check by ID
                    System.out.println("Enter the ID of the book");
                    int idCheck = input.nextInt(); 
                    for (int i=0;i<n;i++){
                        if(idCheck==id[i])
                            j=i;
                    }


                    //check input
                    if(j==-1)System.out.println("invalid ID");
                    else{
                        if(issue[j].equals("available")){
                            System.out.println("This book is already in the library");
                        }else{
                            issue[j]= "available" ;
                            System.out.println("The book has been returned");
                        }
                    }
                }
            }


            //delete
            else if (c==5){
                //check by ID
                if(n==0)
                    System.out.println("There is no books to be deleted");
                else{
                    System.out.println("Enter the ID of the book");
                    int idCheck = input.nextInt();
                    for (int i=0;i<n;i++){
                        if(idCheck==id[i])
                        j=i;
                    }
                    
                    if(j==-1)System.out.println("invalid ID");
                    else {
                        System.out.println("book name is "+names[j]);
                        System.out.println("are you sure you want to delete this book");
                        System.out.println("if yes enter 1 if no enter any other number");
                        int check = input.nextInt();
                        if(check==1){
                        //deleting process
                        if(j==n-1){
                            names[j]="removed book";
                            id[j] =0;
                            issue[j]="removed book";
                            des[j] = "removed book";
                        }else{
                            for(int i=j;i<n;i++){
                                names[i]=names[i+1];
                                id[i]=id[i+1];
                                issue[i]=issue[i+1];
                                des[i] = des[i+1];           
                            }
                        }
                        n--; 
                        System.out.println("The book has been removed");
                        }
                    }
                }
            }


            //Edit data of the book
            else if (c==6){
                if(n==0)
                    System.out.println("There is no books to be edited");
                else{
                //check by ID
                    System.out.println("Enter the ID of the book");
                    int idCheck = input.nextInt(); 
                    for (int i=0;i<n;i++){
                        if(idCheck==id[i])
                            j=i;
                    }
                    
                    //check input
                    if(j==-1)System.out.println("invalid ID");
                    else {
                        System.out.println("Enter the new name of the book");
                        names[j]= input.next();
                        System.out.println("Enter the new Id of the book");
                        id[j] = input.nextInt() ;
                        System.out.println("Enter the new issue of the book");
                        issue[j]= input.next();
                        System.out.println("Enter the new description of the book");
                        des[j] = input.next();
                    }
                }
            }

            //view the books
            else if (c==7){
                if(n==0)
                        System.out.println("There is no books to view");
                else{
                    for (int i=0;i<n;i++){
                            System.out.println("The book name is "+names[i]);
                            System.out.println("The book id is "+id[i]);
                            System.out.println("The book description is "+des[i]);
                            System.out.println("The book issue is "+issue[i]);
                    }
                }
            }

            //check the input
            else if (c>8||c<1){
                System.out.println("invalid input");
            }

            //just a separator
            System.out.println("=========================================");

        }while(c!=8);
    System.out.println("Goodbye");
}}
   
    

