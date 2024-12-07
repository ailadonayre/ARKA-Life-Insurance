package db;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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