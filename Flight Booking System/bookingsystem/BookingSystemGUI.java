package bookingsystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class BookingSystemGUI extends JFrame {
    // بيانات النظام
    private Booking_System system;
    // المستخدم الحالي
    private User currentUser;
    // قوائم عرض
    private DefaultListModel<String> customerListModel;
    private DefaultListModel<String> flightListModel;
    private DefaultListModel<String> bookingListModel;
    private DefaultListModel<String> agentListModel;

    public BookingSystemGUI() {
        system = new Booking_System();
        setTitle("Flight Booking System");
        setSize(900, 650);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        showLoginPanel();
    }

    // شاشة تسجيل الدخول أو إنشاء حساب
    private void showLoginPanel() {
        JPanel loginPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("Flight Booking System", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        loginPanel.add(title, gbc);

        gbc.gridwidth = 2;
        gbc.gridx = 0; gbc.gridy = 1;
        loginPanel.add(new JLabel("Select Role:"), gbc);

        String[] roles = {"Customer", "Agent", "Admin"};
        JComboBox<String> roleBox = new JComboBox<>(roles);
        gbc.gridy = 2;
        loginPanel.add(roleBox, gbc);

        // Panel for login fields (hidden initially)
        JPanel fieldsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints fgbc = new GridBagConstraints();
        fgbc.insets = new Insets(5, 5, 5, 5);
        fgbc.fill = GridBagConstraints.HORIZONTAL;
        fgbc.gridx = 0; fgbc.gridy = 0;
        fieldsPanel.add(new JLabel("Username:"), fgbc);
        JTextField usernameField = new JTextField(15);
        fgbc.gridx = 1;
        fieldsPanel.add(usernameField, fgbc);
        fgbc.gridx = 0; fgbc.gridy = 1;
        fieldsPanel.add(new JLabel("Password:"), fgbc);
        JPasswordField passwordField = new JPasswordField(15);
        fgbc.gridx = 1;
        fieldsPanel.add(passwordField, fgbc);
        JButton loginBtn = new JButton("Login");
        JButton registerBtn = new JButton("Sign-Up");
        JPanel btnPanel = new JPanel();
        btnPanel.add(loginBtn); btnPanel.add(registerBtn);
        fgbc.gridx = 0; fgbc.gridy = 2; fgbc.gridwidth = 2;
        fieldsPanel.add(btnPanel, fgbc);
        fieldsPanel.setVisible(false);

        gbc.gridy = 3;
        loginPanel.add(fieldsPanel, gbc);

        // Show login fields only after role selection
        roleBox.addActionListener(e -> fieldsPanel.setVisible(true));

        setContentPane(loginPanel);
        revalidate();
        repaint();

        loginBtn.addActionListener(e -> {
            String uname = usernameField.getText();
            String pass = new String(passwordField.getPassword());
            String role = (String) roleBox.getSelectedItem();
            User user = findUser(uname, pass, role);
            if (user != null) {
                currentUser = user;
                showDashboard(role);
            } else {
                JOptionPane.showMessageDialog(this, "Invalid credentials or user not found.");
            }
        });

        registerBtn.addActionListener(e -> showRegisterPanel());
    }

    // شاشة تسجيل ديناميكية حسب الدور
    private void showRegisterPanel() {
        JPanel regPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("Register New Account", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        regPanel.add(title, gbc);
        gbc.gridwidth = 1;

        gbc.gridx = 0; gbc.gridy = 1;
        regPanel.add(new JLabel("Role:"), gbc);
        String[] roles = {"Customer", "Agent", "Admin"};
        JComboBox<String> roleBox = new JComboBox<>(roles);
        gbc.gridx = 1;
        regPanel.add(roleBox, gbc);

        // الحقول المشتركة
        JPanel fieldsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints fgbc = new GridBagConstraints();
        fgbc.insets = new Insets(5, 5, 5, 5);
        fgbc.fill = GridBagConstraints.HORIZONTAL;

        fgbc.gridx = 0; fgbc.gridy = 0;
        fieldsPanel.add(new JLabel("Username:"), fgbc);
        JTextField usernameField = new JTextField(15);
        fgbc.gridx = 1;
        fieldsPanel.add(usernameField, fgbc);

        fgbc.gridx = 0; fgbc.gridy = 1;
        fieldsPanel.add(new JLabel("Password:"), fgbc);
        JPasswordField passwordField = new JPasswordField(15);
        fgbc.gridx = 1;
        fieldsPanel.add(passwordField, fgbc);

        fgbc.gridx = 0; fgbc.gridy = 2;
        fieldsPanel.add(new JLabel("Name:"), fgbc);
        JTextField nameField = new JTextField(15);
        fgbc.gridx = 1;
        fieldsPanel.add(nameField, fgbc);

        fgbc.gridx = 0; fgbc.gridy = 3;
        fieldsPanel.add(new JLabel("Email:"), fgbc);
        JTextField emailField = new JTextField(15);
        fgbc.gridx = 1;
        fieldsPanel.add(emailField, fgbc);

        fgbc.gridx = 0; fgbc.gridy = 4;
        fieldsPanel.add(new JLabel("Contact Info:"), fgbc);
        JTextField contactField = new JTextField(15);
        fgbc.gridx = 1;
        fieldsPanel.add(contactField, fgbc);

        // Customer fields
        JPanel customerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints cgbc = new GridBagConstraints();
        cgbc.insets = new Insets(5, 5, 5, 5);
        cgbc.fill = GridBagConstraints.HORIZONTAL;
        cgbc.gridx = 0; cgbc.gridy = 0;
        customerPanel.add(new JLabel("Address:"), cgbc);
        JTextField addressField = new JTextField(15);
        cgbc.gridx = 1;
        customerPanel.add(addressField, cgbc);
        cgbc.gridx = 0; cgbc.gridy = 1;
        customerPanel.add(new JLabel("Preference:"), cgbc);
        JTextField prefField = new JTextField(15);
        cgbc.gridx = 1;
        customerPanel.add(prefField, cgbc);

        // Agent fields
        JPanel agentPanel = new JPanel(new GridBagLayout());
        GridBagConstraints agbc = new GridBagConstraints();
        agbc.insets = new Insets(5, 5, 5, 5);
        agbc.fill = GridBagConstraints.HORIZONTAL;
        agbc.gridx = 0; agbc.gridy = 0;
        agentPanel.add(new JLabel("Department:"), agbc);
        JTextField deptField = new JTextField(15);
        agbc.gridx = 1;
        agentPanel.add(deptField, agbc);
        agbc.gridx = 0; agbc.gridy = 1;
        agentPanel.add(new JLabel("Commission:"), agbc);
        JTextField commField = new JTextField(15);
        agbc.gridx = 1;
        agentPanel.add(commField, agbc);

        // Admin fields
        JPanel adminPanel = new JPanel(new GridBagLayout());
        GridBagConstraints adgbc = new GridBagConstraints();
        adgbc.insets = new Insets(5, 5, 5, 5);
        adgbc.fill = GridBagConstraints.HORIZONTAL;
        adgbc.gridx = 0; adgbc.gridy = 0;
        adminPanel.add(new JLabel("Security Level:"), adgbc);
        JTextField secField = new JTextField(15);
        adgbc.gridx = 1;
        adminPanel.add(secField, adgbc);

        JPanel dynamicPanel = new JPanel(new BorderLayout());
        dynamicPanel.add(customerPanel, BorderLayout.CENTER);

        roleBox.addActionListener(e -> {
            dynamicPanel.removeAll();
            String role = (String) roleBox.getSelectedItem();
            if (role.equals("Customer")) {
                dynamicPanel.add(customerPanel, BorderLayout.CENTER);
            } else if (role.equals("Agent")) {
                dynamicPanel.add(agentPanel, BorderLayout.CENTER);
            } else if (role.equals("Admin")) {
                dynamicPanel.add(adminPanel, BorderLayout.CENTER);
            }
            dynamicPanel.revalidate();
            dynamicPanel.repaint();
        });

        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        regPanel.add(fieldsPanel, gbc);

        gbc.gridy = 3;
        regPanel.add(dynamicPanel, gbc);

        JButton regBtn = new JButton("Sign up");
        JButton backBtn = new JButton("Back");
        JPanel btnPanel = new JPanel();
        btnPanel.add(regBtn); btnPanel.add(backBtn);
        gbc.gridy = 4; gbc.gridwidth = 2;
        regPanel.add(btnPanel, gbc);

        setContentPane(regPanel);
        revalidate();
        repaint();

        regBtn.addActionListener(e -> {
            String uname = usernameField.getText();
            String pass = new String(passwordField.getPassword());
            String name = nameField.getText();
            String email = emailField.getText();
            String contact = contactField.getText();
            String role = (String) roleBox.getSelectedItem();
            try {
                if (role.equals("Customer")) {
                    String address = addressField.getText();
                    String pref = prefField.getText();
                    if (uname.isEmpty() || pass.isEmpty() || name.isEmpty() || email.isEmpty() || contact.isEmpty() || address.isEmpty() || pref.isEmpty())
                        throw new Exception();
                    customer c = new customer(address, pref, uname, pass, name, email, Integer.parseInt(contact));
                    system.addCust(c);
                    JOptionPane.showMessageDialog(this, "Customer registered successfully!");
                } else if (role.equals("Agent")) {
                    String dept = deptField.getText();
                    String comm = commField.getText();
                    if (uname.isEmpty() || pass.isEmpty() || name.isEmpty() || email.isEmpty() || contact.isEmpty() || dept.isEmpty() || comm.isEmpty())
                        throw new Exception();
                    Agent a = new Agent(dept, Double.parseDouble(comm), uname, pass, name, email, Integer.parseInt(contact));
                    system.agent.add(a);
                    JOptionPane.showMessageDialog(this, "Agent registered successfully!");
                } else if (role.equals("Admin")) {
                    String sec = secField.getText();
                    if (uname.isEmpty() || pass.isEmpty() || name.isEmpty() || email.isEmpty() || contact.isEmpty() || sec.isEmpty())
                        throw new Exception();
                    Administrator ad = new Administrator(Integer.parseInt(sec), uname, pass, name, email, Integer.parseInt(contact));
                    system.admin.add(ad);
                    JOptionPane.showMessageDialog(this, "Admin registered successfully!");
                }
                showLoginPanel();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid input or missing fields.");
            }
        });
        backBtn.addActionListener(e -> showLoginPanel());
    }

    // البحث عن مستخدم
    private User findUser(String uname, String pass, String role) {
        if (role.equals("Customer")) {
            for (customer c : system.getCustomers()) {
                if (c.login(uname, pass)) return c;
            }
        } else if (role.equals("Agent")) {
            for (Agent a : system.agent) {
                if (a.login(uname, pass)) return a;
            }
        } else if (role.equals("Admin")) {
            for (Administrator ad : system.admin) {
                if (ad.login(uname, pass)) return ad;
            }
        }
        return null;
    }

    // توجيه المستخدم للوحة التحكم المناسبة
    private void showDashboard(String role) {
        if (role.equals("Customer")) showCustomerDashboard();
        else if (role.equals("Agent")) showAgentDashboard();
        else if (role.equals("Admin")) showAdminDashboard();
    }

    // لوحة تحكم العميل
    private void showCustomerDashboard() {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel welcome = new JLabel("Welcome, " + ((customer)currentUser).getUserName() + " (Customer)", SwingConstants.CENTER);
        welcome.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(welcome, BorderLayout.NORTH);

        JPanel btnPanel = new JPanel(new GridLayout(3, 3, 10, 10)); // 3x3 grid for 9 buttons
        JButton bookBtn = new JButton("Book Flight");
        JButton cancelBtn = new JButton("Cancel Booking");
        JButton editBtn = new JButton("Edit Profile");
        JButton avf = new JButton("View Available Flights");
        JButton priceBtn = new JButton("Calculate Price");
        JButton viewBookingsBtn = new JButton("View My Bookings");
        JButton logoutBtn = new JButton("Logout");
        JButton confirmBookingBtn = new JButton("Confirm Booking");
        JButton paymentBtn = new JButton("Payment");

        btnPanel.add(bookBtn);
        btnPanel.add(cancelBtn);
        btnPanel.add(editBtn);
        btnPanel.add(avf);
        btnPanel.add(priceBtn);
        btnPanel.add(viewBookingsBtn);
        btnPanel.add(logoutBtn);
        btnPanel.add(confirmBookingBtn);
        btnPanel.add(paymentBtn);

        panel.add(btnPanel, BorderLayout.CENTER);

        setContentPane(panel);
        revalidate(); repaint();

        bookBtn.addActionListener(e -> bookFlightDialog((customer)currentUser));
        cancelBtn.addActionListener(e -> cancelBookingDialog((customer)currentUser));
        editBtn.addActionListener(e -> editProfileDialog((customer)currentUser));
        avf.addActionListener(e -> viewFlightsDialog()); // أو viewPricesDialog() حسب رغبتك
        viewBookingsBtn.addActionListener(e -> viewCustomerBookings((customer)currentUser));
        logoutBtn.addActionListener(e -> { currentUser = null; showLoginPanel(); });

        priceBtn.addActionListener(e -> {
            // يمكنك هنا فتح نافذة تطلب من المستخدم اختيار رحلة وعدد الركاب ثم تعرض السعر
            JOptionPane.showMessageDialog(this, "يرجى اختيار الرحلة وعدد الركاب لحساب السعر.");
            // أو يمكنك إعادة استخدام منطق من bookFlightDialog
        });

        confirmBookingBtn.addActionListener(e -> confirmBookingDialog((customer)currentUser));
        paymentBtn.addActionListener(e -> paymentDialog((customer)currentUser));
    }

    // لوحة تحكم الموظف
    private void showAgentDashboard() {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel welcome = new JLabel("Welcome, " + ((Agent)currentUser).getUserName() + " (Agent)", SwingConstants.CENTER);
        welcome.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(welcome, BorderLayout.NORTH);

        JPanel btnPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        JButton addFlightBtn = new JButton("Add Flight");
        JButton editFlightBtn = new JButton("Edit/Delete Flight");
        JButton viewFlightsBtn = new JButton("View Flights");
        JButton logoutBtn = new JButton("Logout");
        btnPanel.add(addFlightBtn); btnPanel.add(editFlightBtn); btnPanel.add(viewFlightsBtn); btnPanel.add(logoutBtn);
        panel.add(btnPanel, BorderLayout.CENTER);

        setContentPane(panel);
        revalidate(); repaint();

        addFlightBtn.addActionListener(e -> addFlightDialogByAgent());
        editFlightBtn.addActionListener(e -> editOrDeleteFlightDialog());
        viewFlightsBtn.addActionListener(e -> viewFlightsDialog());
        logoutBtn.addActionListener(e -> { currentUser = null; showLoginPanel(); });
    }

    // لوحة تحكم المدير
    private void showAdminDashboard() {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel welcome = new JLabel("Welcome, " + ((Administrator)currentUser).getUserName() + " (Admin)", SwingConstants.CENTER);
        welcome.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(welcome, BorderLayout.NORTH);

        JPanel btnPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        JButton addAgentBtn = new JButton("Add Agent");
        JButton removeAgentBtn = new JButton("Remove Agent");
        JButton viewAgentsBtn = new JButton("View Agents");
        JButton logoutBtn = new JButton("Logout");
        btnPanel.add(addAgentBtn); btnPanel.add(removeAgentBtn); btnPanel.add(viewAgentsBtn); btnPanel.add(logoutBtn);
        panel.add(btnPanel, BorderLayout.CENTER);

        setContentPane(panel);
        revalidate(); repaint();

        addAgentBtn.addActionListener(e -> addAgentDialog((Administrator)currentUser));
        removeAgentBtn.addActionListener(e -> removeAgentDialog((Administrator)currentUser));
        viewAgentsBtn.addActionListener(e -> viewAgentsDialog());
        logoutBtn.addActionListener(e -> { currentUser = null; showLoginPanel(); });
    }

    // --- وظائف العميل ---
    private void bookFlightDialog(customer c) {
        if (system.getFlights().isEmpty()) {
            JOptionPane.showMessageDialog(this, "No flights available.");
            return;
        }
        ArrayList<Flightt> flights = system.getFlights();
        String[] flightNames = flights.stream().map(f -> f.getFlightNumber() + " - " + f.getAirline()).toArray(String[]::new);
        String selectedFlight = (String) JOptionPane.showInputDialog(this, "Select Flight:", "Book Flight", JOptionPane.QUESTION_MESSAGE, null, flightNames, flightNames[0]);
        if (selectedFlight == null) return;
        Flightt f = flights.get(java.util.Arrays.asList(flightNames).indexOf(selectedFlight));
        String numPassengersStr = JOptionPane.showInputDialog(this, "Number of Passengers:");
        if (numPassengersStr == null) return;
        int numPassengers = 1;
        try { numPassengers = Integer.parseInt(numPassengersStr); } catch (Exception ex) { JOptionPane.showMessageDialog(this, "Invalid number."); return; }
        ArrayList<Passenger> passengers = new ArrayList<>();
        ArrayList<String> seatClasses = new ArrayList<>();
        for (int i = 0; i < numPassengers; i++) {
            JTextField pname = new JTextField();
            JTextField passport = new JTextField();
            JTextField dob = new JTextField();
            JTextField req = new JTextField();
            String[] seatOptions = {"economy", "first class", "buisness"};
            JComboBox<String> seatBox = new JComboBox<>(seatOptions);
            Object[] fields = {
                "Passenger Name:", pname,
                "Passport Number:", passport,
                "Date of Birth:", dob,
                "Special Requests:", req,
                "Seat Class:", seatBox
            };
            int res = JOptionPane.showConfirmDialog(this, fields, "Passenger " + (i+1), JOptionPane.OK_CANCEL_OPTION);
            if (res == JOptionPane.OK_OPTION) {
                passengers.add(new Passenger(pname.getText(), passport.getText(), dob.getText(), req.getText()));
                seatClasses.add((String) seatBox.getSelectedItem());
            } else {
                return;
            }
        }
        Booking b = system.createBooking(c, f, passengers, seatClasses);
        JOptionPane.showMessageDialog(this, "Booking created!\nTotal Price: " + b.calculateTotalPrice());
    }

    private void cancelBookingDialog(customer c) {
        ArrayList<Booking> bookings = system.getBookings();
        ArrayList<Booking> myBookings = new ArrayList<>();
        for (Booking b : bookings) {
            if (b != null && b.getStatus().equals("Confirmed") &&b.getCustomer() == c) {
                myBookings.add(b);
            }
        }
        if (myBookings.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No confirmed bookings to cancel.");
            return;
        }
        String[] bookingRefs = myBookings.stream().map(b -> b.getBookingReference()).toArray(String[]::new);
        String selected = (String) JOptionPane.showInputDialog(this, "Select Booking to Cancel:", "Cancel Booking", JOptionPane.QUESTION_MESSAGE, null, bookingRefs, bookingRefs[0]);
        if (selected == null) return;
        Booking b = myBookings.get(java.util.Arrays.asList(bookingRefs).indexOf(selected));
        b.cancelBooking();
        JOptionPane.showMessageDialog(this, "Booking cancelled.");
    }

    private void editProfileDialog(customer c) {
        JTextField name = new JTextField(c.getUserName());
        JTextField pass = new JTextField();
        JTextField email = new JTextField();
        JTextField contact = new JTextField();
        Object[] fields = {
            "Name:", name,
            "Password:", pass,
            "Email:", email,
            "Contact Info:", contact
        };
        int res = JOptionPane.showConfirmDialog(this, fields, "Edit Profile", JOptionPane.OK_CANCEL_OPTION);
        if (res == JOptionPane.OK_OPTION) {
            c.updateProfile(name.getText(), c.getUserName(), pass.getText(), email.getText(), Integer.parseInt(contact.getText()));
            JOptionPane.showMessageDialog(this, "Profile updated.");
        }
    }

    private void viewPricesDialog() {
        if (system.getFlights().isEmpty()) {
            JOptionPane.showMessageDialog(this, "No flights available.");
            return;
        }
        StringBuilder sb = new StringBuilder();
        for (Flightt f : system.getFlights()) {
            sb.append(f.ttoString()).append("\n");
        }
        JTextArea area = new JTextArea(sb.toString());
        area.setEditable(false);
        JOptionPane.showMessageDialog(this, new JScrollPane(area), "Flight Prices", JOptionPane.INFORMATION_MESSAGE);
    }

    private void viewCustomerBookings(customer c) {
        ArrayList<Booking> bookings = system.getBookings();
        StringBuilder sb = new StringBuilder();
        for (Booking b : bookings) {
            if (b != null && b.getCustomer() == c) {
                sb.append(b.getBookingReference()).append(" - ").append(b.getStatus()).append("\n");
            }
        }
        if (sb.length() == 0) sb.append("No bookings found.");
        JTextArea area = new JTextArea(sb.toString());
        area.setEditable(false);
        JOptionPane.showMessageDialog(this, new JScrollPane(area), "My Bookings", JOptionPane.INFORMATION_MESSAGE);
    }

    // --- وظائف الموظف ---
    private void addFlightDialogByAgent() {
        JTextField flightNum = new JTextField();
        JTextField airline = new JTextField();
        JTextField origin = new JTextField();
        JTextField dest = new JTextField();
        JTextField dep = new JTextField();
        JTextField arr = new JTextField();
        JTextField date = new JTextField();
        JTextField firstSeats = new JTextField();
        JTextField busSeats = new JTextField();
        JTextField ecoSeats = new JTextField();
        JTextField firstPrice = new JTextField();
        JTextField ecoPrice = new JTextField();
        JTextField busPrice = new JTextField();
        Object[] fields = {
            "Flight Number:", flightNum,
            "Airline:", airline,
            "Origin:", origin,
            "Destination:", dest,
            "Departure Time:", dep,
            "Arrival Time:", arr,
            "Date:", date,
            "First Class Seats:", firstSeats,
            "Business Class Seats:", busSeats,
            "Economy Class Seats:", ecoSeats,
            "First Class Price:", firstPrice,
            "Economy Price:", ecoPrice,
            "Business Price:", busPrice
        };
        int res = JOptionPane.showConfirmDialog(this, fields, "Add Flight", JOptionPane.OK_CANCEL_OPTION);
        if (res == JOptionPane.OK_OPTION) {
            try {
                Flightt f = new Flightt(
                    Integer.parseInt(flightNum.getText()),
                    airline.getText(),
                    origin.getText(),
                    dest.getText(),
                    dep.getText(),
                    arr.getText(),
                    date.getText(),
                    Integer.parseInt(firstSeats.getText()),
                    Integer.parseInt(busSeats.getText()),
                    Integer.parseInt(ecoSeats.getText()),
                    Double.parseDouble(firstPrice.getText()),
                    Double.parseDouble(ecoPrice.getText()),
                    Double.parseDouble(busPrice.getText())
                );
                system.addFlight(f);
                JOptionPane.showMessageDialog(this, "Flight added successfully!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid input.");
            }
        }
    }

    private void editOrDeleteFlightDialog() {
        if (system.getFlights().isEmpty()) {
            JOptionPane.showMessageDialog(this, "No flights available.");
            return;
        }
        ArrayList<Flightt> flights = system.getFlights();
        String[] flightNames = flights.stream().map(f -> f.getFlightNumber() + " - " + f.getAirline()).toArray(String[]::new);
        String selectedFlight = (String) JOptionPane.showInputDialog(this, "Select Flight:", "Edit/Delete Flight", JOptionPane.QUESTION_MESSAGE, null, flightNames, flightNames[0]);
        if (selectedFlight == null) return;
        Flightt f = flights.get(java.util.Arrays.asList(flightNames).indexOf(selectedFlight));
        String[] options = {"Edit", "Delete"};
        int choice = JOptionPane.showOptionDialog(this, "Edit or Delete Flight?", "Edit/Delete", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        if (choice == 0) {
            JTextField dep = new JTextField(f.getDepartureTime());
            JTextField arr = new JTextField(f.getArrivalTime());
            JTextField date = new JTextField(f.getDate());
            Object[] fields = {
                "Departure Time:", dep,
                "Arrival Time:", arr,
                "Date:", date
            };
            int res = JOptionPane.showConfirmDialog(this, fields, "Edit Flight", JOptionPane.OK_CANCEL_OPTION);
            if (res == JOptionPane.OK_OPTION) {
                f.updateSchedule(dep.getText(), arr.getText(), date.getText());
                JOptionPane.showMessageDialog(this, "Flight updated.");
            }
        } else if (choice == 1) {
            system.getFlights().remove(f);
            JOptionPane.showMessageDialog(this, "Flight deleted.");
        }
    }

    private void viewFlightsDialog() {
        if (system.getFlights().isEmpty()) {
            JOptionPane.showMessageDialog(this, "No flights available.");
            return;
        }
        StringBuilder sb = new StringBuilder();
        for (Flightt f : system.getFlights()) {
            sb.append(f.ttoString()).append("\n");
        }
        JTextArea area = new JTextArea(sb.toString());
        area.setEditable(false);
        JOptionPane.showMessageDialog(this, new JScrollPane(area), "Flights", JOptionPane.INFORMATION_MESSAGE);
    }

    // --- وظائف المدير ---
    private void addAgentDialog(Administrator admin) {
        JTextField dept = new JTextField();
        JTextField comm = new JTextField();
        JTextField uname = new JTextField();
        JTextField pass = new JTextField();
        JTextField name = new JTextField();
        JTextField email = new JTextField();
        JTextField contact = new JTextField();
        Object[] fields = {
            "Department:", dept,
            "Commission:", comm,
            "Username:", uname,
            "Password:", pass,
            "Name:", name,
            "Email:", email,
            "Contact Info:", contact
        };
        int res = JOptionPane.showConfirmDialog(this, fields, "Add Agent", JOptionPane.OK_CANCEL_OPTION);
        if (res == JOptionPane.OK_OPTION) {
            try {
                Agent a = new Agent(dept.getText(), Double.parseDouble(comm.getText()), uname.getText(), pass.getText(), name.getText(), email.getText(), Integer.parseInt(contact.getText()));
                admin.addAgent(a);
                system.agent.add(a);
                JOptionPane.showMessageDialog(this, "Agent added successfully!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid input.");
            }
        }
    }

    private void removeAgentDialog(Administrator admin) {
        if (system.agent.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No agents available.");
            return;
        }
        String[] agentNames = system.agent.stream().map(a -> a.getUserName() + " (" + a.getAgentID() + ")").toArray(String[]::new);
        String selected = (String) JOptionPane.showInputDialog(this, "Select Agent to Remove:", "Remove Agent", JOptionPane.QUESTION_MESSAGE, null, agentNames, agentNames[0]);
        if (selected == null) return;
        Agent a = system.agent.get(java.util.Arrays.asList(agentNames).indexOf(selected));
        admin.removeAgent(a);
        system.agent.remove(a);
        JOptionPane.showMessageDialog(this, "Agent removed.");
    }

    private void viewAgentsDialog() {
        if (system.agent.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No agents available.");
            return;
        }
        StringBuilder sb = new StringBuilder();
        for (Agent a : system.agent) {
            sb.append(a.getUserName()).append(" - ").append(a.getDepartment()).append("\n");
        }
        JTextArea area = new JTextArea(sb.toString());
        area.setEditable(false);
        JOptionPane.showMessageDialog(this, new JScrollPane(area), "Agents", JOptionPane.INFORMATION_MESSAGE);
    }

    // نافذة لتأكيد الحجز
    private void confirmBookingDialog(customer c) {
        ArrayList<Booking> bookings = system.getBookings();
        ArrayList<Booking> myBookings = new ArrayList<>();
        for (Booking b : bookings) {
            if (b != null && !b.getStatus().equals("Confirmed") && b.getCustomer() == c) {
                myBookings.add(b);
            }
        }
        if (myBookings.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No bookings to confirm.");
            return;
        }
        String[] bookingRefs = myBookings.stream().map(b -> b.getBookingReference()).toArray(String[]::new);
        String selected = (String) JOptionPane.showInputDialog(this, "Select Booking to Confirm:", "Confirm Booking", JOptionPane.QUESTION_MESSAGE, null, bookingRefs, bookingRefs[0]);
        if (selected == null) return;
        Booking b = myBookings.get(java.util.Arrays.asList(bookingRefs).indexOf(selected));
        b.confirmBooking(); // أو أي دالة التأكيد في كلاس Booking
        JOptionPane.showMessageDialog(this, "Booking confirmed.");
    }

    // نافذة الدفع
    private void paymentDialog(customer c) {
        ArrayList<Booking> bookings = system.getBookings();
        ArrayList<Booking> myBookings = new ArrayList<>();
        for (Booking b : bookings) {
            if (b != null && b.getCustomer() == c && !b.isPaid()) { // تأكد أن لديك دالة isPaid أو تحقق من حالة الدفع
                myBookings.add(b);
            }
        }
        if (myBookings.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No unpaid bookings.");
            return;
        }
        String[] bookingRefs = myBookings.stream().map(b -> b.getBookingReference()).toArray(String[]::new);
        String selected = (String) JOptionPane.showInputDialog(this, "Select Booking to Pay:", "Payment", JOptionPane.QUESTION_MESSAGE, null, bookingRefs, bookingRefs[0]);
        if (selected == null) return;
        Booking b = myBookings.get(java.util.Arrays.asList(bookingRefs).indexOf(selected));
        String[] methods = {"Credit Card", "Cash", "Other"};
        String method = (String) JOptionPane.showInputDialog(this, "Select Payment Method:", "Payment", JOptionPane.QUESTION_MESSAGE, null, methods, methods[0]);
        if (method == null) return;
        system.processPayment(b, method); // أو الدالة المناسبة في كلاس Booking_System
        JOptionPane.showMessageDialog(this, "Payment processed.");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new BookingSystemGUI().setVisible(true);
        });
    }
} 