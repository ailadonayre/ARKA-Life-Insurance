package client.policy;

import java.util.InputMismatchException;
import java.util.Scanner;

import client.ArkaClient;
import client.ArkaClientManager;
import client.payment.ArkaStatement;
import models.ArkaPolicy;
import utils.ArkaCustom;

public class ArkaBeneficiary {
    private Scanner scanner;
    private ArkaPolicy chosenPolicy;
    private String beneficiaryName;
    private String relationship;
    private int paymentPeriod;
    private ArkaClientManager clientManager;

    public ArkaBeneficiary(Scanner scanner, ArkaPolicy chosenPolicy, ArkaClientManager clientManager) {
        this.scanner = scanner;
        this.chosenPolicy = chosenPolicy;
        this.clientManager = clientManager;
    }

    public void collectBeneficiaryDetails(ArkaClient client) {
        System.out.println(ArkaCustom.ANSI_BOLD + "\n------------------------------------------------------------------------------------------\n" + ArkaCustom.ANSI_RESET);
        System.out.println(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_PURPLE + "ARKA: " + ArkaCustom.ANSI_RESET + ArkaCustom.ANSI_PURPLE + "Collect Policy Details" + ArkaCustom.ANSI_RESET);

        try {
            if (chosenPolicy == null) {
                System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_YELLOW + "\t>> " + ArkaCustom.ANSI_RESET);
                System.out.println("Error: Policy is not initialized.");
                return;
            }

            if (client == null) {
                System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_YELLOW + "\t>> " + ArkaCustom.ANSI_RESET);
                System.out.println("Error: Client is null.");
                return;
            }

            if (client.getFullName() == null) {
                System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_YELLOW + "\t>> " + ArkaCustom.ANSI_RESET);
                System.out.println("Error: Client's full name is not available.");
                return;
            }

            while (true) {
                try {
                    System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_PURPLE + "\n> " + ArkaCustom.ANSI_RESET);
                    System.out.print("Beneficiary Name: ");
                    this.beneficiaryName = scanner.nextLine();

                    if (beneficiaryName == null || beneficiaryName.trim().isEmpty()) {
                        throw new IllegalArgumentException("Beneficiary name cannot be empty.");
                    }
                    break;
                } catch (IllegalArgumentException e) {
                    System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_YELLOW + "\t>> " + ArkaCustom.ANSI_RESET);
                    System.out.println("Error: " + e.getMessage());
                }
            }

            while (true) {
                try {
                    System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_PURPLE + "> " + ArkaCustom.ANSI_RESET);
                    System.out.print("Relationship with Beneficiary: ");
                    this.relationship = scanner.nextLine();

                    if (relationship == null || relationship.trim().isEmpty()) {
                        throw new IllegalArgumentException("Relationship with beneficiary cannot be empty.");
                    }
                    break;
                } catch (IllegalArgumentException e) {
                    System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_YELLOW + "\t>> " + ArkaCustom.ANSI_RESET);
                    System.out.println("Error: " + e.getMessage());
                }
            }

            while (true) {
                try {
                    System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_PURPLE + "> " + ArkaCustom.ANSI_RESET);
                    System.out.print("Enter payment period (10, 20, or 35 years): ");
                    this.paymentPeriod = scanner.nextInt();
                    scanner.nextLine();

                    if (paymentPeriod != 10 && paymentPeriod != 20 && paymentPeriod != 35) {
                        throw new IllegalArgumentException("Invalid payment period. Must be 10, 20, or 35 years.");
                    }
                    break;
                } catch (IllegalArgumentException e) {
                    System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_YELLOW + "\t>> " + ArkaCustom.ANSI_RESET);
                    System.out.println("Error: " + e.getMessage());
                } catch (InputMismatchException e) {
                    System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_YELLOW + "\t>> " + ArkaCustom.ANSI_RESET);
                    System.out.println("Error: Invalid input. Please enter a number for the payment period.");
                    scanner.nextLine();
                }
            }

            System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_CYAN + "\t>> " + ArkaCustom.ANSI_RESET);
            System.out.println("Client's beneficiary details collected successfully.");

            ArkaStatement statement = new ArkaStatement(scanner, clientManager);
            statement.generateStatement(chosenPolicy, beneficiaryName, relationship, client, paymentPeriod);

        } catch (IllegalArgumentException e) {
            System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_YELLOW + "\t>> " + ArkaCustom.ANSI_RESET);
            System.out.println("Error: " + e.getMessage());
        } catch (InputMismatchException e) {
            System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_YELLOW + "\t>> " + ArkaCustom.ANSI_RESET);
            System.out.println("Invalid input provided. Please ensure you're entering the correct data.");
            scanner.nextLine();
        } catch (Exception e) {
            System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_YELLOW + "\t>> " + ArkaCustom.ANSI_RESET);
            System.out.println("An unexpected error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public String getBeneficiaryName() {
        return beneficiaryName;
    }

    public String getRelationship() {
        return relationship;
    }

    public int getPaymentPeriod() {
        return paymentPeriod;
    }
}