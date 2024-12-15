package db;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import models.ArkaGintoPlan;
import models.ArkaPilakPlan;
import models.ArkaPolicy;
import models.ArkaTansoPlan;
import utils.ArkaCustom;

public class ArkaDatabase {
    private static final String URL = "jdbc:mysql://localhost:3306/arka_db";
    private static final String USER = "arka_user";
    private static final String PASSWORD = "00-ARKA_DB";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_YELLOW + "\t>> " + ArkaCustom.ANSI_RESET);
            System.out.println("MySQL JDBC driver not found.");
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_YELLOW + "\t>> " + ArkaCustom.ANSI_RESET);
            System.out.println("Error connecting to the database.");
            e.printStackTrace();
            throw e;
        }
    }
    
    public void initializeDatabase() {
        try (Connection connection = getConnection()) {
            checkAndCreateTables(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean tableExists(Connection connection, String tableName) throws SQLException {
        String query = "SHOW TABLES LIKE ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, tableName);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        }
    }

    private void checkAndCreateTables(Connection connection) throws SQLException {
        if (!tableExists(connection, "agent")) {
            createAgentTable();
        }
        if (!tableExists(connection, "client")) {
            createClientTable();
        }
        if (!tableExists(connection, "policy")) {
            createPolicyTable();
        }
        if (!tableExists(connection, "payment")) {
            createPaymentTable();
        }
        if (!tableExists(connection, "address")) {
            createAddressTable();
        }
        if (!tableExists(connection, "citizenship")) {
            createCitizenshipTable();
        }
        if (!tableExists(connection, "income")) {
            createIncomeTable();
        }
    }
    
    private void createAgentTable() throws SQLException {
        String createAgentTableSQL = "CREATE TABLE `agent` ("
                + "`agentID` varchar(10) NOT NULL, "
                + "`username` varchar(255) NOT NULL, "
                + "`password` varchar(255) NOT NULL, "
                + "PRIMARY KEY (`agentID`), "
                + "UNIQUE KEY `username` (`username`)"
                + ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci";
        executeSQL(createAgentTableSQL);
    }
    
    private void createClientTable() throws SQLException {
        String createClientTableSQL = "CREATE TABLE `client` ("
                + "`clientID` varchar(10) NOT NULL, "
                + "`firstName` varchar(100) NOT NULL, "
                + "`lastName` varchar(100) NOT NULL, "
                + "`email` varchar(255) NOT NULL, "
                + "`province` varchar(100) NOT NULL, "
                + "`city` varchar(100) NOT NULL, "
                + "`barangay` varchar(100) DEFAULT NULL, "
                + "`street` varchar(100) DEFAULT NULL, "
                + "PRIMARY KEY (`clientID`)"
                + ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci";
        executeSQL(createClientTableSQL);
    }
    
    private void createPolicyTable() throws SQLException {
        String createPolicyTableSQL = "CREATE TABLE `policy` ("
                + "`policyID` varchar(20) NOT NULL, "
                + "`clientID` varchar(10) NOT NULL, "
                + "`policyType` varchar(50) NOT NULL, "
                + "`coverageAmount` decimal(15,2) NOT NULL, "
                + "`policyDate` date DEFAULT NULL, "
                + "`policyEndDate` date DEFAULT NULL, "
                + "`beneficiaries` varchar(255) NOT NULL, "
                + "`agentID` varchar(10) NOT NULL, "
                + "PRIMARY KEY (`policyID`), "
                + "KEY `clientID` (`clientID`), "
                + "KEY `fk_policy_agentID` (`agentID`), "
                + "CONSTRAINT `fk_policy_agentID` FOREIGN KEY (`agentID`) REFERENCES `agent` (`agentID`) ON DELETE CASCADE, "
                + "CONSTRAINT `policy_ibfk_1` FOREIGN KEY (`clientID`) REFERENCES `client` (`clientID`) ON DELETE CASCADE"
                + ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci";
        executeSQL(createPolicyTableSQL);
    }
    
    private void createPaymentTable() throws SQLException {
        String createPaymentTableSQL = "CREATE TABLE `payment` ("
                + "`paymentID` int NOT NULL AUTO_INCREMENT, "
                + "`clientID` varchar(10) NOT NULL, "
                + "`agentID` varchar(10) NOT NULL, "
                + "`policyID` varchar(20) NOT NULL, "
                + "`paymentDate` date DEFAULT NULL, "
                + "`paymentAmount` decimal(15,2) NOT NULL, "
                + "`nextPayment` date DEFAULT NULL, "
                + "`lastPayment` date DEFAULT NULL, "
                + "PRIMARY KEY (`paymentID`), "
                + "KEY `clientID` (`clientID`), "
                + "KEY `policyID` (`policyID`), "
                + "KEY `fk_payment_agentID` (`agentID`), "
                + "CONSTRAINT `fk_payment_agentID` FOREIGN KEY (`agentID`) REFERENCES `agent` (`agentID`) ON DELETE CASCADE, "
                + "CONSTRAINT `payment_ibfk_1` FOREIGN KEY (`clientID`) REFERENCES `client` (`clientID`) ON DELETE CASCADE, "
                + "CONSTRAINT `payment_ibfk_2` FOREIGN KEY (`policyID`) REFERENCES `policy` (`policyID`) ON DELETE CASCADE"
                + ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci";
        executeSQL(createPaymentTableSQL);
    }
    
    private void createAddressTable() throws SQLException {
        String createAddressTableSQL = "CREATE TABLE `address` ("
                + "`addressID` int NOT NULL AUTO_INCREMENT, "
                + "`clientID` varchar(10) NOT NULL, "
                + "`country` varchar(100) NOT NULL, "
                + "`province` varchar(100) NOT NULL, "
                + "`city` varchar(100) NOT NULL, "
                + "`barangay` varchar(100) DEFAULT NULL, "
                + "`street` varchar(100) DEFAULT NULL, "
                + "PRIMARY KEY (`addressID`), "
                + "KEY `fk_clientID_address` (`clientID`), "
                + "CONSTRAINT `address_ibfk_1` FOREIGN KEY (`clientID`) REFERENCES `client` (`clientID`) ON DELETE CASCADE"
                + ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci";
        executeSQL(createAddressTableSQL);
    }
    
    private void createCitizenshipTable() throws SQLException {
        String createCitizenshipTableSQL = "CREATE TABLE `citizenship` ("
                + "`citizenshipID` int NOT NULL AUTO_INCREMENT, "
                + "`clientID` varchar(10) NOT NULL, "
                + "`citizenship` varchar(100) NOT NULL, "
                + "`nationality` varchar(100) NOT NULL, "
                + "PRIMARY KEY (`citizenshipID`), "
                + "KEY `fk_clientID_citizenship` (`clientID`), "
                + "CONSTRAINT `citizenship_ibfk_1` FOREIGN KEY (`clientID`) REFERENCES `client` (`clientID`) ON DELETE CASCADE"
                + ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci";
        executeSQL(createCitizenshipTableSQL);
    }
    
    private void createIncomeTable() throws SQLException {
        String createIncomeTableSQL = "CREATE TABLE `income` ("
                + "`incomeID` int NOT NULL AUTO_INCREMENT, "
                + "`clientID` varchar(10) NOT NULL, "
                + "`sourceIncome` varchar(100) NOT NULL, "
                + "`annualIncome` int NOT NULL, "
                + "PRIMARY KEY (`incomeID`), "
                + "KEY `fk_clientID_income` (`clientID`), "
                + "CONSTRAINT `income_ibfk_1` FOREIGN KEY (`clientID`) REFERENCES `client` (`clientID`) ON DELETE CASCADE"
                + ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci";
        executeSQL(createIncomeTableSQL);
    }

    private void executeSQL(String query) throws SQLException {
        try (Connection connection = getConnection(); Statement statement = connection.createStatement()) {
            statement.executeUpdate(query);
        }
    }

    public boolean validateMostRecentPayment(String policyID, String paymentID) {
        boolean isValid = false;
        String query = "SELECT MAX(paymentDate) AS latestDate FROM payment WHERE policyID = ?";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, policyID);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String latestDate = resultSet.getString("latestDate");

                String paymentQuery = "SELECT paymentDate FROM payment WHERE policyID = ? AND paymentID = ?";
                try (PreparedStatement paymentStmt = connection.prepareStatement(paymentQuery)) {
                    paymentStmt.setString(1, policyID);
                    paymentStmt.setString(2, paymentID);
                    ResultSet paymentResult = paymentStmt.executeQuery();

                    if (paymentResult.next()) {
                        String paymentDate = paymentResult.getString("paymentDate");
                        isValid = paymentDate.equals(latestDate);
                    }
                }
            }

        } catch (SQLException e) {
            System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_YELLOW + "\t>> " + ArkaCustom.ANSI_RESET);
            System.out.println("Error validating Payment ID: " + ArkaCustom.ANSI_BOLD + paymentID + ArkaCustom.ANSI_RESET);
            e.printStackTrace();
        }

        return isValid;
    }

    public String getPolicyID(String clientID) {
        String policyID = null;
        String query = "SELECT policyID FROM policy WHERE clientID = ?";
    
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
    
            preparedStatement.setString(1, clientID);
            ResultSet resultSet = preparedStatement.executeQuery();
    
            if (resultSet.next()) {
                policyID = resultSet.getString("policyID");
            }
    
        } catch (SQLException e) {
            System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_YELLOW + "\t>> " + ArkaCustom.ANSI_RESET);
            System.out.println("Error retrieving policyID for clientID: " + ArkaCustom.ANSI_BOLD + clientID + ArkaCustom.ANSI_RESET);
            e.printStackTrace();
        }
    
        return policyID;
    }

    public ArkaPolicy getPolicyDetails(String policyID) {
        ArkaPolicy policy = null;
        String query = "SELECT policyID, policyType, premiumAmount, paymentPeriod FROM policy WHERE policyID = ?";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, policyID);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String planName = resultSet.getString("policyType");
                double premiumAmount = resultSet.getDouble("premiumAmount");
                int paymentPeriod = resultSet.getInt("paymentPeriod");

                if ("Ginto Plan".equalsIgnoreCase(planName)) {
                    policy = new ArkaGintoPlan(policyID);
                } else if ("Pilak Plan".equalsIgnoreCase(planName)) {
                    policy = new ArkaPilakPlan(policyID);
                } else if ("Tanso Plan".equalsIgnoreCase(planName)) {
                    policy = new ArkaTansoPlan(policyID);
                }

                if (policy != null) {
                    policy.setPremiumAmount(premiumAmount);
                    policy.setPaymentPeriod(paymentPeriod);
                }
            }

        } catch (SQLException e) {
            System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_YELLOW + "\t>> " + ArkaCustom.ANSI_RESET);
            System.out.println("Error retrieving policy details for Policy ID: " + ArkaCustom.ANSI_BOLD + policyID + ArkaCustom.ANSI_RESET);
            e.printStackTrace();
        }

        return policy;
    }

    public double getPremiumAmount(String policyID) {
        double premiumAmount = 0.0;
        String query = "SELECT premiumAmount FROM policy WHERE policyID = ?";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, policyID);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                premiumAmount = resultSet.getDouble("premiumAmount");
            }

        } catch (SQLException e) {
            System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_YELLOW + "\t>> " + ArkaCustom.ANSI_RESET);
            System.out.println("Error retrieving premium amount for Policy ID: " + ArkaCustom.ANSI_YELLOW + policyID + ArkaCustom.ANSI_YELLOW);
            e.printStackTrace();
        }

        return premiumAmount;
    }

    public int getPaymentPeriod(String policyID) {
        int paymentPeriod = 0;
        String query = "SELECT paymentPeriod FROM policy WHERE policyID = ?";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, policyID);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                paymentPeriod = resultSet.getInt("paymentPeriod");
            }

        } catch (SQLException e) {
            System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_YELLOW + "\t>> " + ArkaCustom.ANSI_RESET);
            System.out.println("Error retrieving payment period for Policy ID: " + ArkaCustom.ANSI_BOLD + policyID + ArkaCustom.ANSI_RESET);
            e.printStackTrace();
        }

        return paymentPeriod;
    }

    public LocalDate getNextPaymentDate(String policyID) {
        try {
            String query = "SELECT nextPayment FROM payment WHERE policyID = ? ORDER BY paymentDate DESC LIMIT 1";
            PreparedStatement statement = getConnection().prepareStatement(query);
            statement.setString(1, policyID);
            ResultSet resultSet = statement.executeQuery();
    
            if (resultSet.next()) {
                Date nextPayment = resultSet.getDate("nextPayment");
                return nextPayment.toLocalDate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
        return null;
    }
}