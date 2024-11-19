package client.policy;

import java.text.DecimalFormat;
import java.util.Scanner;
import java.util.Set;

import client.ArkaClient;
import client.ArkaClientManager;
import models.ArkaGintoPlan;
import models.ArkaPilakPlan;
import models.ArkaPolicy;
import models.ArkaTansoPlan;
import utils.ArkaCustom;

public class ArkaPolicyRecommender {
    private Scanner scanner;
    private ArkaEligibility eligibilityCalculator;
    private ArkaPolicy chosenPolicy;

    public ArkaPolicyRecommender(Scanner scanner) {
        this.scanner = scanner;
        this.eligibilityCalculator = new ArkaEligibility(scanner);
    }

    public void recommendPolicy(ArkaClient client) {
        int annualIncome = client.getAnnualIncome();
        ArkaClientManager clientManager = new ArkaClientManager();
    
        Set<String> existingPolicyIDs = clientManager.fetchExistingPolicyIDs();
        String policyID = clientManager.generatePolicyID(existingPolicyIDs);
    
        try {
            double eligibilityScore = eligibilityCalculator.calculateEligibilityScore(client);
            String formattedEligibilityScore = String.format("%.2f", eligibilityScore);
    
            if (annualIncome >= 200000) {
                if (eligibilityScore >= 80) {
                    chosenPolicy = new ArkaGintoPlan(policyID);
                } else if (eligibilityScore >= 40) {
                    chosenPolicy = new ArkaPilakPlan(policyID);
                } else {
                    chosenPolicy = new ArkaTansoPlan(policyID);
                }
            } else if (annualIncome >= 80000) {
                if (eligibilityScore >= 80) {
                    chosenPolicy = new ArkaPilakPlan(policyID);
                } else if (eligibilityScore >= 40) {
                    chosenPolicy = new ArkaTansoPlan(policyID);
                } else {
                    System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_CYAN + "\t>> " + ArkaCustom.ANSI_RESET);
                    System.out.println("Sorry, based on your eligibility score, no plan is available for your income range.");
                    return;
                }
            } else if (annualIncome >= 15000) {
                if (eligibilityScore >= 50) {
                    chosenPolicy = new ArkaTansoPlan(policyID);
                } else {
                    System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_CYAN + "\t>> " + ArkaCustom.ANSI_RESET);
                    System.out.println("Sorry, based on your eligibility score, no plan is available for your income range.");
                    return;
                }
            } else {
                System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_CYAN + "\t>> " + ArkaCustom.ANSI_RESET);
                System.out.println("Sorry, no plan is available for your income range.");
                return;
            }
    
            if (chosenPolicy == null) {
                System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_YELLOW + "\t>> " + ArkaCustom.ANSI_RESET);
                System.out.println("Error: No valid policy selected.");
                return;
            }
            
            DecimalFormat df = new DecimalFormat("#.00");

            System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_CYAN + "\t>> " + ArkaCustom.ANSI_RESET);
            System.out.println("Based on your eligibility score (" + ArkaCustom.ANSI_BOLD + formattedEligibilityScore + ArkaCustom.ANSI_RESET + "%) and your annual income of Php "
                                + ArkaCustom.ANSI_BOLD + df.format(annualIncome) + ArkaCustom.ANSI_RESET + ", our recommended ARKA plan for you is:");
            chosenPolicy.displayPlan();
            
            System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_PURPLE + "\n> " + ArkaCustom.ANSI_RESET);
            System.out.print("Would you like to proceed with this insurance plan? (Y/N): ");
            String choice = scanner.nextLine().toUpperCase();
    
            if ("N".equals(choice)) {
                displayAlternativePlans(client);
            } else if ("Y".equals(choice)) {
                if (chosenPolicy != null) {
                    new ArkaBeneficiary(scanner, chosenPolicy, clientManager).collectBeneficiaryDetails(client);
                } else {
                    System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_YELLOW + "\t>> " + ArkaCustom.ANSI_RESET);
                    System.out.println("Error: No policy was selected. Please select a valid policy.");
                }
            } else {
                System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_YELLOW + "\t>> " + ArkaCustom.ANSI_RESET);
                System.out.println("Invalid choice. Please try again.");
            }
        } catch (Exception e) {
            System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_YELLOW + "\t>> " + ArkaCustom.ANSI_RESET);
            System.out.println("An error occurred while recommending a policy: " + e.getMessage());
            e.printStackTrace();
        }
    }    

    private void displayAlternativePlans(ArkaClient client) {
        ArkaClientManager clientManager = new ArkaClientManager();
        Set<String> existingPolicyIDs = clientManager.fetchExistingPolicyIDs();
        String policyID = clientManager.generatePolicyID(existingPolicyIDs);
    
        System.out.println(ArkaCustom.ANSI_BOLD + "\n------------------------------------------------------------------------------------------\n" + ArkaCustom.ANSI_RESET);
        System.out.println(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_PURPLE + "ARKA: " + ArkaCustom.ANSI_RESET + ArkaCustom.ANSI_PURPLE + "Choose a Different Plan" + ArkaCustom.ANSI_RESET);
    
        while (true) {
            try {
                ArkaGintoPlan ginto = new ArkaGintoPlan(policyID);
                ginto.displayPlan();
                
                ArkaPilakPlan pilak = new ArkaPilakPlan(policyID);
                pilak.displayPlan();
                
                ArkaTansoPlan tanso = new ArkaTansoPlan(policyID);
                tanso.displayPlan();
    
                System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_PURPLE + "\n> " + ArkaCustom.ANSI_RESET);
                System.out.print("Choose your insurance plan (1-3) or press 'X' to cancel: ");
                String input = scanner.nextLine().trim();
    
                if (input.equalsIgnoreCase("X")) {
                    System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_CYAN + "\t>> " + ArkaCustom.ANSI_RESET);
                    System.out.println("Operation canceled. Deleting client and returning to the main menu.");
    
                    String clientID = client.getClientID();
                    clientManager.removeClientByID(clientID);
                    return;
                }
    
                int choice;
                try {
                    choice = Integer.parseInt(input);
                } catch (NumberFormatException e) {
                    System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_YELLOW + "\t>> " + ArkaCustom.ANSI_RESET);
                    System.out.println("Invalid input. Please enter a number between 1 and 3 or 'X' to cancel.");
                    continue;
                }
    
                switch (choice) {
                    case 1:
                        chosenPolicy = new ArkaGintoPlan(policyID);
                        System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_CYAN + "\t>> " + ArkaCustom.ANSI_RESET);
                        System.out.println("You have chosen the " + ArkaCustom.ANSI_BOLD + "Ginto (Gold) Plan." + ArkaCustom.ANSI_RESET);
                        break;
                    case 2:
                        chosenPolicy = new ArkaPilakPlan(policyID);
                        System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_CYAN + "\t>> " + ArkaCustom.ANSI_RESET);
                        System.out.println("You have chosen the " + ArkaCustom.ANSI_BOLD + "Pilak (Silver) Plan." + ArkaCustom.ANSI_RESET);
                        break;
                    case 3:
                        chosenPolicy = new ArkaTansoPlan(policyID);
                        System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_CYAN + "\t>> " + ArkaCustom.ANSI_RESET);
                        System.out.println("You have chosen the " + ArkaCustom.ANSI_BOLD + "Tanso (Bronze) Plan." + ArkaCustom.ANSI_RESET);
                        break;
                    default:
                        System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_YELLOW + "\t>> " + ArkaCustom.ANSI_RESET);
                        System.out.println("Invalid choice. Please choose a number between 1 and 3.");
                        continue;
                }
    
                new ArkaBeneficiary(scanner, chosenPolicy, clientManager).collectBeneficiaryDetails(client);
                break;
            } catch (Exception e) {
                System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_YELLOW + "\t>> " + ArkaCustom.ANSI_RESET);
                System.out.println("An error occurred while processing your request: " + e.getMessage());
                e.printStackTrace();
                break;
            }
        }
    }      

    public ArkaPolicy getChosenPolicy() {
        return chosenPolicy;
    }
}
