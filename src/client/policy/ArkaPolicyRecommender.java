package client.policy;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Set;

import client.ArkaClient;
import client.ArkaClientManager;
import models.ArkaGintoPlan;
import models.ArkaPilakPlan;
import models.ArkaPolicy;
import models.ArkaTansoPlan;

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
                    System.out.println("Sorry, based on your eligibility score, no plan is available for your income range.");
                    return;
                }
            } else if (annualIncome >= 15000) {
                if (eligibilityScore >= 50) {
                    chosenPolicy = new ArkaTansoPlan(policyID);
                } else {
                    System.out.println("Sorry, based on your eligibility score, no plan is available for your income range.");
                    return;
                }
            } else {
                System.out.println("Sorry, based on your eligibility score, no plan is available for your income range.");
                return;
            }
    
            if (chosenPolicy == null) {
                System.out.println("Error: No valid policy selected.");
                return;
            }
    
            System.out.println("\nBased on your eligibility score (" + formattedEligibilityScore + "%) and your annual income of Php "
                    + annualIncome + ", our recommended plan for you is:");
            chosenPolicy.displayPlan();
    
            System.out.print("\nWould you like to proceed with this insurance plan? (Y/N): ");
            String choice = scanner.nextLine().toUpperCase();
    
            if ("N".equals(choice)) {
                displayAlternativePlans(client);
            } else if ("Y".equals(choice)) {
                if (chosenPolicy != null) {
                    new ArkaBeneficiary(scanner, chosenPolicy, clientManager).collectBeneficiaryDetails(client);
                } else {
                    System.out.println("Error: No policy was selected. Please select a valid policy.");
                }
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        } catch (Exception e) {
            System.out.println("An error occurred while recommending a policy: " + e.getMessage());
            e.printStackTrace();
        }
    }    

    private void displayAlternativePlans(ArkaClient client) {
        ArkaClientManager clientManager = new ArkaClientManager();
    
        Set<String> existingPolicyIDs = clientManager.fetchExistingPolicyIDs();
    
        String policyID = clientManager.generatePolicyID(existingPolicyIDs);
    
        try {
            System.out.println("\nARKA Insurance Plans");
            ArkaGintoPlan ginto = new ArkaGintoPlan(policyID);
            ginto.displayPlan();
            
            ArkaPilakPlan pilak = new ArkaPilakPlan(policyID);
            pilak.displayPlan();
            
            ArkaTansoPlan tanso = new ArkaTansoPlan(policyID);
            tanso.displayPlan();
    
            System.out.print("\nChoose your insurance plan (1-3): ");
            int choice = scanner.nextInt();
            scanner.nextLine();
    
            switch (choice) {
                case 1:
                    chosenPolicy = new ArkaGintoPlan(policyID);
                    break;
                case 2:
                    chosenPolicy = new ArkaPilakPlan(policyID);
                    break;
                case 3:
                    chosenPolicy = new ArkaTansoPlan(policyID);
                    break;
                default:
                    System.out.println("Invalid choice.");
                    return;
            }
    
            new ArkaBeneficiary(scanner, chosenPolicy, clientManager).collectBeneficiaryDetails(client);
    
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a number between 1 and 3.");
            scanner.nextLine();
        } catch (Exception e) {
            System.out.println("An error occurred while displaying alternative plans: " + e.getMessage());
            e.printStackTrace();
        }
    }
    

    public ArkaPolicy getChosenPolicy() {
        return chosenPolicy;
    }
}
