package client.payment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import client.ArkaClient;
import client.ArkaClientManager;
import db.ArkaDatabase;
import models.ArkaPolicy;
import utils.ArkaCustom;

public class ArkaStatement {
    private Scanner scanner;
    private ArkaClientManager clientManager;

    public ArkaStatement(Scanner scanner, ArkaClientManager clientManager) {
        this.scanner = scanner;
        this.clientManager = clientManager;
    }

    public void generateStatement(ArkaPolicy chosenPolicy, String beneficiaryName, String relationship, ArkaClient client, int paymentPeriod) {
        try {
            int totalLineLength = 49;
            String text = "Proposal Statement";

            int textLength = text.length();
            int spacesNeeded = (totalLineLength - textLength) / 2;
            String spaces = ArkaCustom.generateSpaces(spacesNeeded);

            System.out.println(ArkaCustom.ANSI_BOLD + "\n-------------------------------------------------------------\n" + ArkaCustom.ANSI_RESET);
            System.out.println(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_PURPLE + spaces + "Proposal " + ArkaCustom.ANSI_RESET + ArkaCustom.ANSI_PURPLE + "Statement" + ArkaCustom.ANSI_RESET);

            System.out.println(ArkaCustom.ANSI_BOLD + "\nOn the Life of: " + ArkaCustom.ANSI_RESET + client.getFullName());
            System.out.println(ArkaCustom.ANSI_BOLD + "Age: " + ArkaCustom.ANSI_RESET + calculateAge(client.getDateOfBirth()));
            System.out.println(ArkaCustom.ANSI_BOLD + "Chosen Insurance Plan: " + ArkaCustom.ANSI_RESET + chosenPolicy.getPlanName());
            System.out.println(ArkaCustom.ANSI_BOLD + "Payment Period: " + ArkaCustom.ANSI_RESET + paymentPeriod + " years");

            DecimalFormat df = new DecimalFormat("#.00");
            String formattedCoverageAmount = df.format(chosenPolicy.getCoverageAmount());
            System.out.println(ArkaCustom.ANSI_BOLD + "Coverage Amount: " + ArkaCustom.ANSI_RESET + "Php " + formattedCoverageAmount);

            double expectedPremium = chosenPolicy.getPremiumAmount(paymentPeriod);

            String formattedPremiumAmount = df.format(expectedPremium);
            System.out.println(ArkaCustom.ANSI_BOLD + "Premium Amount: " + ArkaCustom.ANSI_RESET + "Php " + formattedPremiumAmount);

            System.out.println(ArkaCustom.ANSI_BOLD + "Beneficiary: " + ArkaCustom.ANSI_RESET + beneficiaryName + " (" + relationship + ")");

            System.out.println(ArkaCustom.ANSI_BOLD + "\n------------------------------------------------------------------------------------------\n" + ArkaCustom.ANSI_RESET);
            System.out.println(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_PURPLE + "ARKA: " + ArkaCustom.ANSI_RESET + ArkaCustom.ANSI_PURPLE + "Payment Information" + ArkaCustom.ANSI_RESET);

            double paymentAmount = 0.0;

            while (true) {
                System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_PURPLE + "\n> " + ArkaCustom.ANSI_RESET);
                System.out.print("Enter payment amount (exact amount): ");

                paymentAmount = scanner.nextDouble();
                scanner.nextLine();

                if (paymentAmount == expectedPremium) {
                    break;
                } else {
                    System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_YELLOW + "\t>> " + ArkaCustom.ANSI_RESET);
                    System.out.println("Error: The payment amount must be exactly Php " + expectedPremium + ".00 for the chosen payment period.");
                    continue;
                }
            }            

            String paymentFrequency = "Annually";

            LocalDate startDate = LocalDate.now();

            LocalDate birthDate = client.getDateOfBirth();
            LocalDate endDate = birthDate.plusYears(101).minusDays(1);

            Set<String> existingPolicyIDs = getExistingPolicyIDs();

            String policyID = clientManager.generatePolicyID(existingPolicyIDs);

            String loggedInAgentID = clientManager.getLoggedInAgentID();

            savePolicyDetails(policyID, client.getClientID(), loggedInAgentID, chosenPolicy, startDate, endDate, paymentAmount, paymentPeriod, paymentFrequency, "ACTIVE", beneficiaryName, relationship);

            ArkaPayment.collectAndProcessPayment(scanner, client, loggedInAgentID, chosenPolicy, paymentAmount, paymentFrequency, paymentPeriod);

        } catch (Exception e) {
            System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_YELLOW + "\t>> " + ArkaCustom.ANSI_RESET);
            System.out.println("An error occurred while generating the statement: " + e.getMessage());
            e.printStackTrace();
        }
    }              

    private void savePolicyDetails(String policyID, String clientID, String agentID, ArkaPolicy chosenPolicy, LocalDate startDate, LocalDate endDate, double premium, int paymentPeriod, String paymentFrequency, String status, String beneficiaryName, String beneficiaryRelationship) {
        String sql = "INSERT INTO policy (policyID, clientID, agentID, policyType, startDate, endDate, premiumAmount, coverageAmount, paymentPeriod, paymentFrequency, status, beneficiaryName, beneficiaryRelationship) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    
        try (Connection conn = ArkaDatabase.getConnection();  
             PreparedStatement stmt = conn.prepareStatement(sql)) {
    
            stmt.setString(1, policyID);  
            stmt.setString(2, clientID);  
            stmt.setString(3, agentID);
            stmt.setString(4, chosenPolicy.getPlanName());
            stmt.setObject(5, startDate);  
            stmt.setObject(6, endDate);    
            stmt.setDouble(7, premium);    
            stmt.setDouble(8, chosenPolicy.getCoverageAmount());  
            stmt.setInt(9, paymentPeriod); 
            stmt.setString(10, paymentFrequency); 
            stmt.setString(11, status); 
            stmt.setString(12, beneficiaryName); 
            stmt.setString(13, beneficiaryRelationship); 
            stmt.executeUpdate();
            System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_CYAN + "\t>> " + ArkaCustom.ANSI_RESET);
            System.out.println("Policy details saved successfully.");
        } catch (SQLException e) {
            System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_YELLOW + "\t>> " + ArkaCustom.ANSI_RESET);
            System.out.println("Error saving policy details to the database.");
            e.printStackTrace();
        }
    }

    private Set<String> getExistingPolicyIDs() {
        Set<String> policyIDs = new HashSet<>();
        String sql = "SELECT policyID FROM policy"; 

        try (Connection conn = ArkaDatabase.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                policyIDs.add(rs.getString("policyID"));
            }
        } catch (SQLException e) {
            System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_YELLOW + "\t>> " + ArkaCustom.ANSI_RESET);
            System.out.println("Error retrieving existing policy IDs.");
            e.printStackTrace();
        }
        return policyIDs;
    }

    private int calculateAge(LocalDate dateOfBirth) {
        try {
            LocalDate currentDate = LocalDate.now();
            if ((dateOfBirth != null) && (currentDate != null)) {
                Period period = Period.between(dateOfBirth, currentDate);
                return period.getYears();
            }
        } catch (Exception e) {
            System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_YELLOW + "\t>> " + ArkaCustom.ANSI_RESET);
            System.out.println("Error calculating age: " + e.getMessage());
        }
        return 0;
    }
}