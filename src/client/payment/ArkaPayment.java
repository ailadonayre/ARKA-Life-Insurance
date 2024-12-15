package client.payment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Scanner;

import client.ArkaClient;
import client.ArkaClientManager;
import db.ArkaDatabase;
import models.ArkaPolicy;
import utils.ArkaCustom;

public class ArkaPayment {
    private String paymentID;
    private String clientID;
    private String policyID;
    private String agentID;
    private LocalDate paymentDate;
    private double paymentAmount;
    private LocalDate nextPayment;
    private LocalDate lastPayment;

    public ArkaPayment(ArkaClient client, String agentID, ArkaPolicy policy, double paymentAmount, String paymentFrequency, int paymentPeriod) {
        this.clientID = client.getClientID();
        this.agentID = agentID;
        this.paymentDate = LocalDate.now();
        this.paymentAmount = paymentAmount;
        this.paymentID = new ArkaClientManager().generatePaymentID();

        ArkaDatabase database = new ArkaDatabase();
        this.policyID = database.getPolicyID(client.getClientID());

        calculatePaymentDates(paymentFrequency, paymentPeriod);
    }

    private void calculatePaymentDates(String paymentFrequency, int paymentPeriod) {
        this.lastPayment = paymentDate.plusYears(paymentPeriod);

        if ("annually".equalsIgnoreCase(paymentFrequency)) {
            this.nextPayment = paymentDate.plusYears(1);
        }

        if (paymentDate.equals(lastPayment)) {
            this.nextPayment = null;
        }
    }

    public void settlePayment() {
        String checkPolicySQL = "SELECT COUNT(*) FROM policy WHERE policyID = ?";
        try (Connection conn = db.ArkaDatabase.getConnection();
             PreparedStatement checkStatement = conn.prepareStatement(checkPolicySQL)) {
    
            checkStatement.setString(1, this.policyID);
            ResultSet resultSet = checkStatement.executeQuery();
    
            if (resultSet.next() && resultSet.getInt(1) == 0) {
                System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_YELLOW + "\t>> " + ArkaCustom.ANSI_RESET);
                System.out.println("Policy ID not found.");
                return;
            }
    
            String sql = "INSERT INTO payment (paymentID, clientID, agentID, policyID, paymentDate, paymentAmount, nextPayment, lastPayment) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setString(1, this.paymentID);
                statement.setString(2, this.clientID);
                statement.setString(3, this.agentID);
                statement.setString(4, this.policyID);
                statement.setDate(5, java.sql.Date.valueOf(this.paymentDate));
                statement.setDouble(6, this.paymentAmount);
                statement.setDate(7, this.nextPayment != null ? java.sql.Date.valueOf(this.nextPayment) : null);
                statement.setDate(8, java.sql.Date.valueOf(this.lastPayment));
                int rowsInserted = statement.executeUpdate();

                if (rowsInserted > 0) {
                    System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_CYAN + "\t>> " + ArkaCustom.ANSI_RESET);
                    System.out.println("Payment successfully processed!");
                    printReceipt();
    
                    updatePolicyStatus(conn);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_YELLOW + "\t>> " + ArkaCustom.ANSI_RESET);
                System.out.println("Error processing payment.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_YELLOW + "\t>> " + ArkaCustom.ANSI_RESET);
            System.out.println("Error checking policy or database connection.");
        }
    }    

    private void updatePolicyStatus(Connection conn) throws SQLException {
        String policyStatusSQL = "UPDATE policy SET status = ? WHERE policyID = ?";

        try (PreparedStatement statement = conn.prepareStatement(policyStatusSQL)) {
            if (this.paymentDate.isBefore(this.nextPayment) || this.paymentDate.isEqual(this.nextPayment)) {
                statement.setString(1, "ACTIVE");
            } else {
                statement.setString(1, "INACTIVE");
            }

            statement.setString(2, this.policyID);
            statement.executeUpdate();
        }
    }

    private void printReceipt() {
        ArkaClientManager clientManager = new ArkaClientManager();
        ArkaClient client = clientManager.getClientByID(clientID);

        if (client != null) {
            String formattedName = String.format("%s %s %c. %s", client.getHonorific(), client.getFirstName(),
                    client.getMiddleName().isEmpty() ? ' ' : client.getMiddleName().charAt(0), client.getLastName());

            String formattedAmount = String.format("%.2f", paymentAmount);
            
            int totalLineLength = 49;
            String text = "Payment Receipt";

            int textLength = text.length();
            int spacesNeeded = (totalLineLength - textLength) / 2;
            String spaces = ArkaCustom.generateSpaces(spacesNeeded);

            System.out.println(ArkaCustom.ANSI_BOLD + "\n-------------------------------------------------------------\n" + ArkaCustom.ANSI_RESET);
            System.out.println(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_PURPLE + spaces + "Payment " + ArkaCustom.ANSI_RESET + ArkaCustom.ANSI_PURPLE + "Receipt" + ArkaCustom.ANSI_RESET);

            System.out.println(ArkaCustom.ANSI_BOLD + "\nClient: " + ArkaCustom.ANSI_RESET + formattedName);
            System.out.println(ArkaCustom.ANSI_BOLD + "Payment Date: " + ArkaCustom.ANSI_RESET + paymentDate);
            System.out.println(ArkaCustom.ANSI_BOLD + "Policy ID: " + ArkaCustom.ANSI_RESET + policyID);
            System.out.println(ArkaCustom.ANSI_BOLD + "Amount Paid: " + ArkaCustom.ANSI_RESET + "Php " + formattedAmount);
            System.out.println(ArkaCustom.ANSI_BOLD + "Payment ID: " + ArkaCustom.ANSI_RESET + paymentID);
            System.out.println(ArkaCustom.ANSI_BOLD + "Next Payment: " + ArkaCustom.ANSI_RESET + (nextPayment != null ? nextPayment : "N/A"));
            System.out.println(ArkaCustom.ANSI_BOLD + "Last Payment: " + ArkaCustom.ANSI_RESET + lastPayment);
        } else {
            System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_YELLOW + "\t>> " + ArkaCustom.ANSI_RESET);
            System.out.println("Error: Client not found.");
        }
    }

    public static void collectAndProcessPayment(Scanner scanner, ArkaClient client, String loggedInAgentID, ArkaPolicy policy, double paymentAmount, String paymentFrequency, int paymentPeriod) {
        ArkaPayment paymentHandler = new ArkaPayment(client, loggedInAgentID, policy, paymentAmount, paymentFrequency, paymentPeriod);
        paymentHandler.settlePayment();
    }    
}