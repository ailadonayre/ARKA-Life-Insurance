package client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

import db.ArkaDatabase;
import manager.ArkaMenu;
import utils.ArkaCustom;

public class ArkaClientManager extends ArkaClient {
    private List<ArkaClient> clients;

    public ArkaClientManager() {
        super(
        "DEFAULT_CLIENT_ID",
        "LastName",
        "FirstName",
        "",
        "Mr./Ms.",
        "Male/Female",
        LocalDate.now(),
        "Single",
        "Default Place",
        "Default Citizenship",
        "Default Nationality",
        "Default Country",
        "Default Province",
        "Default City",
        "Default Barangay",
        "Default Street",
        "0000000000",
        "default@example.com",
        "Default Occupation",
        "Default Company Name",
        1,
        "Default Income Source",
        "DEFAULT_AGENT_ID"
        );
        clients = new ArrayList<>();
    }

    public boolean clientExists(String emailAddress, String contactNumber) throws SQLException {
        String sql = "SELECT COUNT(*) FROM client WHERE emailAddress = ? OR contactNumber = ?";

        try (Connection connection = ArkaDatabase.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, emailAddress);
            stmt.setString(2, contactNumber);
            
            try (ResultSet rs = stmt.executeQuery()) {
                rs.next();
                return rs.getInt(1) > 0;
            }
        }
    }

    public void addClient(ArkaClient client, String loggedInAgentID) {
        try {
            if (clientExists(client.getEmailAddress(), client.getContactNumber())) {
                System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_YELLOW + "\t>> " + ArkaCustom.ANSI_RESET);
                System.out.println("Client with this email or phone number already exists.");
                new ArkaMenu().showMenu(loggedInAgentID);
                return;
            }
        } catch (SQLException e) {
            System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_YELLOW + "\t>> " + ArkaCustom.ANSI_RESET);
            System.out.println("Error checking if client exists.");
            e.printStackTrace();
            new ArkaMenu().showMenu(loggedInAgentID);
            return;
        }

        String clientSql = "INSERT INTO client (clientID, lastName, firstName, middleName, honorific, sex, dateOfBirth, " +
                        "civilStatus, placeOfBirth, contactNumber, emailAddress, occupation, companyName, agentID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        String addressSql = "INSERT INTO address (clientID, country, province, city, barangay, street) VALUES (?, ?, ?, ?, ?, ?)";
        String citizenshipSql = "INSERT INTO citizenship (clientID, citizenship, nationality) VALUES (?, ?, ?)";
        String incomeSql = "INSERT INTO income (clientID, annualIncome, sourceIncome) VALUES (?, ?, ?)";

        if (client.clientID == null || client.clientID.isEmpty()) {
            client.clientID = generateClientID();
        }

        try (Connection conn = ArkaDatabase.getConnection()) {
            try (PreparedStatement clientStatement = conn.prepareStatement(clientSql)) {
                clientStatement.setString(1, client.clientID);
                clientStatement.setString(2, client.lastName);
                clientStatement.setString(3, client.firstName);
                clientStatement.setString(4, client.middleName);
                clientStatement.setString(5, client.honorific);
                clientStatement.setString(6, client.sex);
                clientStatement.setDate(7, java.sql.Date.valueOf(client.dateOfBirth));
                clientStatement.setString(8, client.civilStatus);
                clientStatement.setString(9, client.placeOfBirth);
                clientStatement.setString(10, client.contactNumber);
                clientStatement.setString(11, client.emailAddress);
                clientStatement.setString(12, client.occupation);
                clientStatement.setString(13, client.companyName);
                clientStatement.setString(14, loggedInAgentID);
                clientStatement.executeUpdate();
            }

            try (PreparedStatement addressStatement = conn.prepareStatement(addressSql)) {
                addressStatement.setString(1, client.clientID);
                addressStatement.setString(2, client.country);
                addressStatement.setString(3, client.province);
                addressStatement.setString(4, client.city);
                addressStatement.setString(5, client.barangay);
                addressStatement.setString(6, client.street);
                addressStatement.executeUpdate();
            }

            try (PreparedStatement citizenshipStatement = conn.prepareStatement(citizenshipSql)) {
                citizenshipStatement.setString(1, client.clientID);
                citizenshipStatement.setString(2, client.citizenship);
                citizenshipStatement.setString(3, client.nationality);
                citizenshipStatement.executeUpdate();
            }

            try (PreparedStatement incomeStatement = conn.prepareStatement(incomeSql)) {
                incomeStatement.setString(1, client.clientID);
                incomeStatement.setInt(2, client.annualIncome);
                incomeStatement.setString(3, client.sourceIncome);
                incomeStatement.executeUpdate();
            }

            System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_CYAN + "\t>> " + ArkaCustom.ANSI_RESET);
            System.out.println("Client successfully added.");

        } catch (SQLException e) {
            System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_YELLOW + "\t>> " + ArkaCustom.ANSI_RESET);
            System.out.println("Error establishing database connection or executing queries.");
            e.printStackTrace();
            new ArkaMenu().showMenu(loggedInAgentID);
            return;
        }
    }

    public String getAgentID(String username) {
        String agentID = null;
        String query = "SELECT agentID FROM agent WHERE username = ?";

        try (Connection connection = ArkaDatabase.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    agentID = rs.getString("agentID");
                }
            }
        } catch (SQLException e) {
            System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_YELLOW + "\t>> " + ArkaCustom.ANSI_RESET);
            System.out.println("Error retrieving agent ID: " + e.getMessage());
            e.printStackTrace();
        }

        return agentID;
    }

    public String generateClientID() {
        try {
            String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
            String numbers = "0123456789";
            StringBuilder clientID = new StringBuilder();
            for (int i = 0; i < 5; i++) {
                clientID.append(letters.charAt((int) (Math.random() * letters.length())));
            }
            for (int i = 0; i < 5; i++) {
                clientID.append(numbers.charAt((int) (Math.random() * numbers.length())));
            }
            return clientID.toString();
        } catch (Exception e) {
            System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_YELLOW + "\t>> " + ArkaCustom.ANSI_RESET);
            System.out.println("Error generating client ID.");
            e.printStackTrace();
            return null;
        }
    }

     public String generatePolicyID(Set<String> existingPolicyIDs) {
        try {
            Random random = new Random();
            StringBuilder policyID;
            String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    
            do {
                policyID = new StringBuilder();
                policyID.append('A');
                for (int i = 0; i < 5; i++) {
                    policyID.append(random.nextInt(10));
                }
                for (int i = 0; i < 2; i++) {
                    policyID.append(letters.charAt(random.nextInt(letters.length())));
                }
            } while (existingPolicyIDs.contains(policyID.toString()));
    
            return policyID.toString();
        } catch (Exception e) {
            System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_YELLOW + "\t>> " + ArkaCustom.ANSI_RESET);
            System.out.println("Error generating policy ID.");
            e.printStackTrace();
            return null;
        }
    }

    public String generatePaymentID() {
        Random random = new Random();
        String paymentID;
        Set<String> existingPaymentIDs = fetchExistingPaymentIDs();

        do {
            paymentID = String.format("%07d", random.nextInt(10000000));
        } while (existingPaymentIDs.contains(paymentID));

        return paymentID;
    }

    public String getPolicyID() {
        Set<String> existingPolicyIDs = fetchExistingPolicyIDs();
        return generatePolicyID(existingPolicyIDs);
    }

    public Set<String> fetchExistingPolicyIDs() {
        Set<String> policyIDs = new HashSet<>();
        String sql = "SELECT policyID FROM policy";

        try (Connection conn = ArkaDatabase.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                policyIDs.add(resultSet.getString("policyID"));
            }

        } catch (SQLException e) {
            System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_YELLOW + "\t>> " + ArkaCustom.ANSI_RESET);
            System.out.println("Error fetching existing policy IDs from database.");
            e.printStackTrace();
        }

        return policyIDs;
    }

    private Set<String> fetchExistingPaymentIDs() {
        Set<String> paymentIDs = new HashSet<>();
        String sql = "SELECT paymentID FROM payment";

        try (Connection conn = ArkaDatabase.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                paymentIDs.add(resultSet.getString("paymentID"));
            }

        } catch (SQLException e) {
            System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_YELLOW + "\t>> " + ArkaCustom.ANSI_RESET);
            System.out.println("Error fetching existing payment IDs from database.");
            e.printStackTrace();
        }

        return paymentIDs;
    }

    public void printPolicyDetails(String clientID) {
        String sql = "SELECT * FROM policy WHERE clientID = ?";
        
        String paymentSql = "SELECT paymentDate, nextPayment, lastPayment FROM payment WHERE clientID = ? " +
                             "ORDER BY paymentDate DESC LIMIT 1";
    
        try (Connection conn = ArkaDatabase.getConnection()) {
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, clientID);
                ResultSet rs = stmt.executeQuery();
    
                DecimalFormat df = new DecimalFormat("#.00");
    
                while (rs.next()) {
                    String policyType = rs.getString("policyType");
                    LocalDate startDate = rs.getDate("startDate").toLocalDate();
                    LocalDate endDate = rs.getDate("endDate").toLocalDate();
                    int paymentPeriod = rs.getInt("paymentPeriod");
                    double premiumAmount = rs.getDouble("premiumAmount");
                    double coverageAmount = rs.getDouble("coverageAmount");
                    
                    String formattedPremiumAmount = df.format(premiumAmount);
                    String formattedCoverageAmount = df.format(coverageAmount);
    
                    int totalLineLength = 49;
                    String text = "Policy Information";

                    int textLength = text.length();
                    int spacesNeeded = (totalLineLength - textLength) / 2;
                    String spaces = ArkaCustom.generateSpaces(spacesNeeded);

                    System.out.println(ArkaCustom.ANSI_BOLD + "\n-------------------------------------------------------------\n" + ArkaCustom.ANSI_RESET);
                    System.out.println(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_PURPLE + spaces + "Policy " + ArkaCustom.ANSI_RESET + ArkaCustom.ANSI_PURPLE + "Information" + ArkaCustom.ANSI_RESET);

                    System.out.println(ArkaCustom.ANSI_BOLD + "\nPolicy Type: " + ArkaCustom.ANSI_RESET + policyType);
                    System.out.println(ArkaCustom.ANSI_BOLD + "Start Date: " + ArkaCustom.ANSI_RESET + startDate);
                    System.out.println(ArkaCustom.ANSI_BOLD + "End Date: " + ArkaCustom.ANSI_RESET + endDate);
                    System.out.println(ArkaCustom.ANSI_BOLD + "Premium Period: " + ArkaCustom.ANSI_RESET + paymentPeriod + " years");
                    System.out.println(ArkaCustom.ANSI_BOLD + "Premium Amount: " + ArkaCustom.ANSI_RESET + "Php " + formattedPremiumAmount);
                    System.out.println(ArkaCustom.ANSI_BOLD + "Coverage Amount: " + ArkaCustom.ANSI_RESET + "Php " + formattedCoverageAmount);
                }
            }
    
            try (PreparedStatement paymentStmt = conn.prepareStatement(paymentSql)) {
                paymentStmt.setString(1, clientID);
                ResultSet paymentRs = paymentStmt.executeQuery();
    
                if (paymentRs.next()) {
                    LocalDate paymentDate = paymentRs.getDate("paymentDate").toLocalDate();
                    LocalDate nextPayment = paymentRs.getDate("nextPayment").toLocalDate();
                    LocalDate lastPayment = paymentRs.getDate("lastPayment").toLocalDate();
    
                    int totalLineLength = 49;
                    String text = "Payment Information";

                    int textLength = text.length();
                    int spacesNeeded = (totalLineLength - textLength) / 2;
                    String spaces = ArkaCustom.generateSpaces(spacesNeeded);

                    System.out.println(ArkaCustom.ANSI_BOLD + "\n-------------------------------------------------------------\n" + ArkaCustom.ANSI_RESET);
                    System.out.println(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_PURPLE + spaces + "Payment " + ArkaCustom.ANSI_RESET + ArkaCustom.ANSI_PURPLE + "Information" + ArkaCustom.ANSI_RESET);

                    System.out.println(ArkaCustom.ANSI_BOLD + "\nLast Payment Date: " + ArkaCustom.ANSI_RESET + paymentDate);
                    System.out.println(ArkaCustom.ANSI_BOLD + "Next Payment Date: " + ArkaCustom.ANSI_RESET + nextPayment);
                    System.out.println(ArkaCustom.ANSI_BOLD + "Last Date of Insurance Payment: " + ArkaCustom.ANSI_RESET + lastPayment);

                    System.out.println(ArkaCustom.ANSI_BOLD + "\n-------------------------------------------------------------" + ArkaCustom.ANSI_RESET);
                } else {
                    System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_YELLOW + "\t>> " + ArkaCustom.ANSI_RESET);
                    System.out.println("No payment details found for this client.");
                }
            }
    
        } catch (SQLException e) {
            System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_YELLOW + "\t>> " + ArkaCustom.ANSI_RESET);
            System.out.println("Error retrieving policy or payment details.");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_YELLOW + "\t>> " + ArkaCustom.ANSI_RESET);
            System.out.println("Unexpected error occurred while retrieving policy or payment details.");
            e.printStackTrace();
        }
    }

    public List<ArkaClient> getAllClients() {
        List<ArkaClient> clients = new ArrayList<>();
        String sql = "SELECT client.clientID, client.lastName, client.firstName, client.middleName, client.honorific, client.sex, " +
                    "client.dateOfBirth, client.civilStatus, client.placeOfBirth, client.contactNumber, client.emailAddress, " +
                    "client.occupation, client.companyName, " +
                    "citizenship.citizenship, citizenship.nationality, " +
                    "address.country, address.province, address.city, address.barangay, address.street, " +
                    "income.annualIncome, income.sourceIncome " +
                    "FROM client " +
                    "LEFT JOIN citizenship ON client.clientID = citizenship.clientID " +
                    "LEFT JOIN address ON client.clientID = address.clientID " +
                    "LEFT JOIN income ON client.clientID = income.clientID";

        try (Connection conn = ArkaDatabase.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                ArkaClient client = new ArkaClient(
                    resultSet.getString("clientID"),
                    resultSet.getString("lastName"),
                    resultSet.getString("firstName"),
                    resultSet.getString("middleName"),
                    resultSet.getString("honorific"),
                    resultSet.getString("sex"),
                    resultSet.getDate("dateOfBirth").toLocalDate(),
                    resultSet.getString("civilStatus"),
                    resultSet.getString("placeOfBirth"),
                    resultSet.getString("citizenship"),
                    resultSet.getString("nationality"),
                    resultSet.getString("country"),
                    resultSet.getString("province"),
                    resultSet.getString("city"),
                    resultSet.getString("barangay"),
                    resultSet.getString("street"),
                    resultSet.getString("contactNumber"),
                    resultSet.getString("emailAddress"),
                    resultSet.getString("occupation"),
                    resultSet.getString("companyName"),
                    resultSet.getInt("annualIncome"),
                    resultSet.getString("sourceIncome"),
                    resultSet.getString("agentID")
                );
                clients.add(client);
            }
        } catch (SQLException e) {
            System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_YELLOW + "\t>> " + ArkaCustom.ANSI_RESET);
            System.out.println("Error fetching clients from database.");
            e.printStackTrace();
        }
        return clients;
    }

    public ArkaClient getClientByID(String clientID) {
        ArkaClient client = null;
        String sql = "SELECT c.*, a.*, ci.*, i.*, p.policyID FROM client c " +
                    "JOIN address a ON c.clientID = a.clientID " +
                    "JOIN citizenship ci ON c.clientID = ci.clientID " +
                    "JOIN income i ON c.clientID = i.clientID " +
                    "JOIN policy p ON c.clientID = p.clientID " +
                    "WHERE c.clientID = ?";

        try (Connection conn = ArkaDatabase.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, clientID);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                client = new ArkaClient(
                    rs.getString("clientID"),
                    rs.getString("lastName"),
                    rs.getString("firstName"),
                    rs.getString("middleName"),
                    rs.getString("honorific"),
                    rs.getString("sex"),
                    rs.getDate("dateOfBirth").toLocalDate(),
                    rs.getString("civilStatus"),
                    rs.getString("placeOfBirth"),
                    rs.getString("citizenship"),
                    rs.getString("nationality"),
                    rs.getString("country"),
                    rs.getString("province"),
                    rs.getString("city"),
                    rs.getString("barangay"),
                    rs.getString("street"),
                    rs.getString("contactNumber"),
                    rs.getString("emailAddress"),
                    rs.getString("occupation"),
                    rs.getString("companyName"),
                    rs.getInt("annualIncome"),
                    rs.getString("sourceIncome"),
                    rs.getString("agentID")
                );

                client.setPolicyID(rs.getString("policyID"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_YELLOW + "\t>> " + ArkaCustom.ANSI_RESET);
            System.out.println("Error fetching client by ID.");
        }
        return client;
    } 
    
    public List<ArkaClient> getClientsByAgentID(String agentID) {
        List<ArkaClient> clients = new ArrayList<>();
        String sql = "SELECT * FROM client WHERE agentID = ?";
        
        try (Connection conn = ArkaDatabase.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, agentID);
            ResultSet resultSet = statement.executeQuery();
            
            while (resultSet.next()) {
                ArkaClient client = new ArkaClient();
                client.setClientID(resultSet.getString("clientID"));
                client.setLastName(resultSet.getString("lastName"));
                client.setFirstName(resultSet.getString("firstName"));
                client.setMiddleName(resultSet.getString("middleName"));
                client.setHonorific(resultSet.getString("honorific"));
                clients.add(client);
            }
        } catch (SQLException e) {
            System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_YELLOW + "\t>> " + ArkaCustom.ANSI_RESET);
            System.out.println("Error fetching clients.");
            e.printStackTrace();
        }
        return clients;
    }

    public ArkaClient getClientByPolicyID(String policyID) {
        for (ArkaClient client : clients) {
            if (client.getPolicy() != null && client.getPolicy().getPolicyID().equals(policyID)) {
                return client;
            }
        }
        return null;
    }

    public void viewClients(List<ArkaClient> clients) {
        System.out.println();
        if (clients.isEmpty()) {
            System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_YELLOW + "\t>> " + ArkaCustom.ANSI_RESET);
            System.out.println("No clients available.");
        } else {
            int index = 1;
            for (ArkaClient client : clients) {
                String formattedName = String.format("%s %s %c. %s", client.honorific, client.firstName,
                        client.middleName.isEmpty() ? ' ' : client.middleName.charAt(0), client.lastName);
                System.out.printf("%d. " + ArkaCustom.ANSI_BOLD + "%s " + ArkaCustom.ANSI_RESET + "%s", index++, client.clientID, formattedName);
                System.out.println();
            }
        }
    }

    public void searchClient(Scanner scanner, String agentID) {
        System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_PURPLE + "\n> " + ArkaCustom.ANSI_RESET);
        System.out.print("Enter Client ID to search: ");
        String clientID = scanner.nextLine();
    
        ArkaClient client = getClientByIDAndAgent(clientID, agentID);
    
        if (client != null) {
            int totalLineLength = 49;
            String text = "Client Information";

            int textLength = text.length();
            int spacesNeeded = (totalLineLength - textLength) / 2;
            String spaces = ArkaCustom.generateSpaces(spacesNeeded);

            System.out.println(ArkaCustom.ANSI_BOLD + "\n-------------------------------------------------------------\n" + ArkaCustom.ANSI_RESET);
            System.out.println(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_PURPLE + spaces + "Client " + ArkaCustom.ANSI_RESET + ArkaCustom.ANSI_PURPLE + "Information" + ArkaCustom.ANSI_RESET);

            System.out.println(ArkaCustom.ANSI_BOLD + "\nClient ID: " + ArkaCustom.ANSI_RESET + client.getClientID());
            System.out.println(ArkaCustom.ANSI_BOLD + "Name: " + ArkaCustom.ANSI_RESET + client.getHonorific() + " " + client.getFirstName() + " " + 
                               client.getMiddleName().charAt(0) + ". " + client.getLastName());
            System.out.println(ArkaCustom.ANSI_BOLD + "Date of Birth: " + ArkaCustom.ANSI_RESET + client.getDateOfBirth());
            System.out.println(ArkaCustom.ANSI_BOLD + "Contact Number: " + ArkaCustom.ANSI_RESET + client.getContactNumber());
            System.out.println(ArkaCustom.ANSI_BOLD + "Email: " + ArkaCustom.ANSI_RESET + client.getEmailAddress());
    
            printPolicyDetails(clientID);
        } else {
            System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_YELLOW + "\t>> " + ArkaCustom.ANSI_RESET);
            System.out.println("Client not found or not associated with your account.");
        }
    }
    
    public ArkaClient getClientByIDAndAgent(String clientID, String agentID) {
        ArkaClient client = null;
        String query = "SELECT c.clientID, c.lastName, c.firstName, c.middleName, c.honorific, c.sex, c.dateOfBirth, c.civilStatus, " +
                       "c.placeOfBirth, ci.citizenship, ci.nationality, a.country, a.province, a.city, a.barangay, a.street, " +
                       "c.contactNumber, c.emailAddress, c.occupation, c.companyName, i.annualIncome, i.sourceIncome, c.agentID " +
                       "FROM client c " +
                       "JOIN address a ON c.clientID = a.clientID " +
                       "JOIN citizenship ci ON c.clientID = ci.clientID " +
                       "JOIN income i ON c.clientID = i.clientID " +
                       "WHERE c.clientID = ? AND c.agentID = ?";
    
        try (Connection connection = ArkaDatabase.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
    
            stmt.setString(1, clientID);
            stmt.setString(2, agentID);
    
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    client = new ArkaClient(
                        rs.getString("clientID"),
                        rs.getString("lastName"),
                        rs.getString("firstName"),
                        rs.getString("middleName"),
                        rs.getString("honorific"),
                        rs.getString("sex"),
                        rs.getDate("dateOfBirth").toLocalDate(),
                        rs.getString("civilStatus"),
                        rs.getString("placeOfBirth"),
                        rs.getString("citizenship"),
                        rs.getString("nationality"),
                        rs.getString("country"),
                        rs.getString("province"),
                        rs.getString("city"),
                        rs.getString("barangay"),
                        rs.getString("street"),
                        rs.getString("contactNumber"),
                        rs.getString("emailAddress"),
                        rs.getString("occupation"),
                        rs.getString("companyName"),
                        rs.getInt("annualIncome"),
                        rs.getString("sourceIncome"),
                        rs.getString("agentID")
                    );
                }
            }
        } catch (SQLException e) {
            System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_YELLOW + "\t>> " + ArkaCustom.ANSI_RESET);
            System.out.println("Error retrieving client details: " + e.getMessage());
            e.printStackTrace();
        }
    
        return client;
    }
    
    public void removeClientByID(String clientID) {
        // Remove the client from the list
        clients.removeIf(client -> client.getClientID().equals(clientID));
    
        // Delete the client from the database
        String query = "DELETE FROM client WHERE clientID = ?";
        try (Connection connection = ArkaDatabase.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, clientID);
            statement.executeUpdate();
            System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_CYAN + "\t>> " + ArkaCustom.ANSI_RESET);
            System.out.println("Client with ID " + clientID + " has been deleted from the database.");
        } catch (SQLException e) {
            System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_YELLOW + "\t>> " + ArkaCustom.ANSI_RESET);
            System.out.println("An error occurred while deleting the client from the database: " + e.getMessage());
        }
    }
    
}