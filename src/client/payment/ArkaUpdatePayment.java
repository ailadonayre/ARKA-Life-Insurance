package client.payment;

import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.Scanner;

import client.ArkaClient;
import client.ArkaClientManager;
import database.ArkaDatabase;
import models.ArkaPolicy;

public class ArkaUpdatePayment {
    private ArkaDatabase database = new ArkaDatabase();
    private ArkaClientManager clientManager = new ArkaClientManager();
    private Scanner scanner = new Scanner(System.in);

    public void updateClientPayment(String agentID) {
        try {
            System.out.print("\nEnter the Client ID to update payment: ");
            String clientID = scanner.nextLine();

            ArkaClient client = clientManager.getClientByID(clientID);
            if (client == null) {
                System.out.println("Client not found for Client ID: " + clientID);
                return;
            }

            if (!client.getAgentID().equals(agentID)) {
                System.out.println("You do not have access to this client's information.");
                return;
            }

            String policyID = database.getPolicyID(clientID);
            if (policyID == null) {
                System.out.println("No policy found for Client ID: " + clientID);
                return;
            }

            System.out.print("Enter the most recent Payment ID: ");
            String paymentID = scanner.nextLine();

            boolean isValidPayment = database.validateMostRecentPayment(policyID, paymentID);

            if (isValidPayment) {
                LocalDate nextPaymentDate = database.getNextPaymentDate(policyID);
                LocalDate currentDate = LocalDate.now();

                if (nextPaymentDate != null && !currentDate.isAfter(nextPaymentDate)) {
                    System.out.println("Next payment date has not passed yet. Payment cannot be made before " + nextPaymentDate + ".");
                    return;
                }

                ArkaPolicy clientPolicy = database.getPolicyDetails(policyID);

                if (clientPolicy != null) {
                    System.out.println("\nUpdating payment for Policy ID: " + policyID);

                    double premiumAmount = database.getPremiumAmount(policyID);
                    int paymentPeriod = database.getPaymentPeriod(policyID);

                    System.out.println("Premium Amount: Php " + premiumAmount);
                    System.out.println("Payment Period: " + paymentPeriod + " years");

                    double newPaymentAmount = promptForPaymentAmount(premiumAmount);
                    if (newPaymentAmount == -1) return;

                    String paymentFrequency = "Annually";
                    ArkaPayment.collectAndProcessPayment(scanner, client, clientPolicy, newPaymentAmount, paymentFrequency, paymentPeriod);

                    System.out.println("Payment updated and processed successfully!");
                } else {
                    System.out.println("No policy found for Policy ID: " + policyID);
                }
            } else {
                System.out.println("Invalid or outdated Payment ID provided.");
            }
        } catch (Exception e) {
            System.out.println("An error occurred while updating the payment: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private double promptForPaymentAmount(double expectedPremium) {
        try {
            System.out.print("Enter payment amount (exact amount): ");
            double newPaymentAmount = scanner.nextDouble();
            scanner.nextLine();

            if (newPaymentAmount != expectedPremium) {
                System.out.println("Error: Payment must be Php " + expectedPremium);
                return -1;
            }

            return newPaymentAmount;
        } catch (InputMismatchException e) {
            System.out.println("Invalid input for payment amount.");
            scanner.nextLine();
            return -1;
        }
    }
}