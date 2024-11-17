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
import database.ArkaDatabase;
import models.ArkaPolicy;

public class ArkaStatement {
    private Scanner scanner;
    private ArkaClientManager clientManager;

    public ArkaStatement(Scanner scanner, ArkaClientManager clientManager) {
        this.scanner = scanner;
        this.clientManager = clientManager;
    }

    public void generateStatement(ArkaPolicy chosenPolicy, String beneficiaryName, String relationship, ArkaClient client, int paymentPeriod) {
        try {
            System.out.println("\n----- Proposal Statement -----");
            System.out.println("On the Life of: " + client.getFullName());
            System.out.println("Age: " + calculateAge(client.getDateOfBirth()));
            System.out.println("Chosen Insurance Plan: " + chosenPolicy.getPlanName());
            System.out.println("Payment Period: " + paymentPeriod + " years");

            DecimalFormat df = new DecimalFormat("#.00");
            String formattedCoverageAmount = df.format(chosenPolicy.getCoverageAmount());
            System.out.println("Coverage Amount: Php " + formattedCoverageAmount);

            double expectedPremium = chosenPolicy.getPremiumAmount(paymentPeriod);

            String formattedPremiumAmount = df.format(expectedPremium);
            System.out.println("Premium Amount: Php " + formattedPremiumAmount);
    
            System.out.println("Beneficiary: " + beneficiaryName + " (" + relationship + ")");
            System.out.println("-----------------------------");
    
            System.out.print("\nEnter payment amount (exact amount): ");
            double paymentAmount = scanner.nextDouble();
            scanner.nextLine();
    
            if (paymentAmount != expectedPremium) {
                System.out.println("Error: The payment amount must be exactly Php " + expectedPremium + ".00 for the chosen payment period.");
                return;
            }
    
            String paymentFrequency = "Annually";
    
            LocalDate startDate = LocalDate.now();
    
            LocalDate birthDate = client.getDateOfBirth();
            LocalDate endDate = birthDate.plusYears(101).minusDays(1);
    
            Set<String> existingPolicyIDs = getExistingPolicyIDs();
    
            String policyID = clientManager.generatePolicyID(existingPolicyIDs);
    
            savePolicyDetails(policyID, client.getClientID(), chosenPolicy, startDate, endDate, paymentAmount, paymentPeriod, paymentFrequency, "ACTIVE", beneficiaryName, relationship);
    
            ArkaPayment.collectAndProcessPayment(scanner, client, chosenPolicy, paymentAmount, paymentFrequency, paymentPeriod);
    
        } catch (Exception e) {
            System.out.println("An error occurred while generating the statement: " + e.getMessage());
            e.printStackTrace();
        }
    }              

    private void savePolicyDetails(String policyID, String clientID, ArkaPolicy chosenPolicy, LocalDate startDate, LocalDate endDate, double premium, int paymentPeriod, String paymentFrequency, String status, String beneficiaryName, String beneficiaryRelationship) {
        String sql = "INSERT INTO policy (policyID, clientID, policyType, startDate, endDate, premiumAmount, coverageAmount, paymentPeriod, paymentFrequency, status, beneficiaryName, beneficiaryRelationship) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    
        try (Connection conn = ArkaDatabase.getConnection();  
             PreparedStatement stmt = conn.prepareStatement(sql)) {
    
            stmt.setString(1, policyID);  
            stmt.setString(2, clientID);  
            stmt.setString(3, chosenPolicy.getPlanName());
            stmt.setObject(4, startDate);  
            stmt.setObject(5, endDate);    
            stmt.setDouble(6, premium);    
            stmt.setDouble(7, chosenPolicy.getCoverageAmount());  
            stmt.setInt(8, paymentPeriod); 
            stmt.setString(9, paymentFrequency); 
            stmt.setString(10, status); 
            stmt.setString(11, beneficiaryName); 
            stmt.setString(12, beneficiaryRelationship); 
    
            stmt.executeUpdate();
            System.out.println("Policy details saved successfully.");
        } catch (SQLException e) {
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
            System.out.println("Error calculating age: " + e.getMessage());
        }
        return 0;
    }
}