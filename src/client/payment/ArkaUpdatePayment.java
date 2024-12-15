package client.payment;

import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.Scanner;

import client.ArkaClient;
import client.ArkaClientManager;
import db.ArkaDatabase;
import models.ArkaPolicy;
import utils.ArkaCustom;

public class ArkaUpdatePayment {
    private ArkaDatabase database = new ArkaDatabase();
    private ArkaClientManager clientManager = new ArkaClientManager();
    private Scanner scanner = new Scanner(System.in);

    public void updateClientPayment(String agentID) {
        String clientID = null;
        String policyID = null;
        ArkaClient client = null;

        try {
            while (true) {
                System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_PURPLE + "\n> " + ArkaCustom.ANSI_RESET);
                System.out.print("Enter the Client ID to update payment: ");
                clientID = scanner.nextLine();

                client = clientManager.getClientByID(clientID);
                if (client == null) {
                    System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_YELLOW + "\t>> " + ArkaCustom.ANSI_RESET);
                    System.out.println("Client not found for Client ID: " + clientID);
                    return;
                }

                if (!client.getAgentID().equals(agentID)) {
                    System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_YELLOW + "\t>> " + ArkaCustom.ANSI_RESET);
                    System.out.println("You do not have access to this client's information.");
                    return;
                }

                policyID = database.getPolicyID(clientID);
                if (policyID == null) {
                    System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_YELLOW + "\t>> " + ArkaCustom.ANSI_RESET);
                    System.out.println("No policy found for Client ID: " + clientID);
                    return;
                }

                break;
            }

            System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_PURPLE + "> " + ArkaCustom.ANSI_RESET);
            System.out.print("Enter the most recent Payment ID: ");
            String paymentID = scanner.nextLine();

            boolean isValidPayment = database.validateMostRecentPayment(policyID, paymentID);
            
            if (isValidPayment) {
                LocalDate nextPaymentDate = database.getNextPaymentDate(policyID);
                LocalDate currentDate = LocalDate.now();

                if (nextPaymentDate != null && !currentDate.isAfter(nextPaymentDate)) {
                    System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_CYAN + "\t>> " + ArkaCustom.ANSI_RESET);
                    System.out.println("Next payment date has not passed yet. Payment cannot be made before " + nextPaymentDate + ".");
                    return;
                }

                ArkaPolicy clientPolicy = database.getPolicyDetails(policyID);
                
                if (clientPolicy != null) {
                    System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_PURPLE + "\n> " + ArkaCustom.ANSI_RESET);
                    System.out.println("Updating payment for Policy ID: " + policyID);

                    double premiumAmount = database.getPremiumAmount(policyID);
                    int paymentPeriod = database.getPaymentPeriod(policyID);

                    System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_PURPLE + "> " + ArkaCustom.ANSI_RESET);
                    System.out.println("Premium Amount: Php " + premiumAmount);
                    System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_PURPLE + "> " + ArkaCustom.ANSI_RESET);
                    System.out.println("Payment Period: " + paymentPeriod + " years");

                    double newPaymentAmount = promptForPaymentAmount(premiumAmount);
                    if (newPaymentAmount == -1) return;
                    
                    String paymentFrequency = "Annually";

                    ArkaPayment.collectAndProcessPayment(scanner, client, agentID, clientPolicy, newPaymentAmount, paymentFrequency, paymentPeriod);

                    System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_CYAN + "\t>> " + ArkaCustom.ANSI_RESET);
                    System.out.println("Payment updated and processed successfully!");
                } else {
                    System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_YELLOW + "\t>> " + ArkaCustom.ANSI_RESET);
                    System.out.println("No policy found for Policy ID: " + policyID);
                }
            } else {
                System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_YELLOW + "\t>> " + ArkaCustom.ANSI_RESET);
                System.out.println("Invalid or outdated Payment ID provided.");
            }
        } catch (Exception e) {
            System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_YELLOW + "\t>> " + ArkaCustom.ANSI_RESET);
            System.out.println("An error occurred while updating the payment: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private double promptForPaymentAmount(double expectedPremium) {
        while (true) {
            try {
                System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_PURPLE + "\n> " + ArkaCustom.ANSI_RESET);
                System.out.print("Enter payment amount (exact amount): ");
                double newPaymentAmount = scanner.nextDouble();
                scanner.nextLine();

                if (newPaymentAmount != expectedPremium) {
                    System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_YELLOW + "\t>> " + ArkaCustom.ANSI_RESET);
                    System.out.println("Error: Payment must be Php " + expectedPremium);
                } else {
                    return newPaymentAmount;
                }

            } catch (InputMismatchException e) {
                System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_YELLOW + "\t>> " + ArkaCustom.ANSI_RESET);
                System.out.println("Invalid input for payment amount.");
                scanner.nextLine();
            }
        }
    }
}