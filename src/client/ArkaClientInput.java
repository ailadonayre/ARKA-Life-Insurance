package client;

import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.Scanner;

import utils.ArkaCustom;

public class ArkaClientInput {
    private Scanner scanner;
    private String agentID;

    public ArkaClientInput(Scanner scanner, String agentID) {
        this.scanner = scanner;
        this.agentID = agentID;
    }

    public ArkaClient collectClientDetails() {
        ArkaClientManager clientManager = new ArkaClientManager();
        String clientID = clientManager.generateClientID(); 

        System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_PURPLE + "\n> " + ArkaCustom.ANSI_RESET);
        System.out.print("Last Name: ");
        String lastName = scanner.nextLine();

        System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_PURPLE + "> " + ArkaCustom.ANSI_RESET);
        System.out.print("First Name: ");
        String firstName = scanner.nextLine();

        System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_PURPLE + "> " + ArkaCustom.ANSI_RESET);
        System.out.print("Middle Name: ");
        String middleName = scanner.nextLine();

        System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_PURPLE + "> " + ArkaCustom.ANSI_RESET);
        System.out.print("Honorific (Mr./Ms./Mrs.): ");
        String honorific = scanner.nextLine();

        System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_PURPLE + "> " + ArkaCustom.ANSI_RESET);
        System.out.print("Sex: ");
        String sex = scanner.nextLine();

        LocalDate dateOfBirth = null;
        while (dateOfBirth == null) {
            try {
                System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_PURPLE + "> " + ArkaCustom.ANSI_RESET);
                System.out.print("Date of Birth (YYYY-MM-DD): ");
                dateOfBirth = LocalDate.parse(scanner.nextLine());
            } catch (Exception e) {
                System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_YELLOW + "\t>> " + ArkaCustom.ANSI_RESET);
                System.out.println("Invalid date format. Please use YYYY-MM-DD.");
            }
        }

        System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_PURPLE + "> " + ArkaCustom.ANSI_RESET);
        System.out.print("Civil Status: ");
        String civilStatus = scanner.nextLine();

        System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_PURPLE + "> " + ArkaCustom.ANSI_RESET);
        System.out.print("Place of Birth: ");
        String placeOfBirth = scanner.nextLine();

        System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_PURPLE + "> " + ArkaCustom.ANSI_RESET);
        System.out.print("Citizenship: ");
        String citizenship = scanner.nextLine();

        System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_PURPLE + "> " + ArkaCustom.ANSI_RESET);
        System.out.print("Nationality: ");
        String nationality = scanner.nextLine();

        System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_PURPLE + "> " + ArkaCustom.ANSI_RESET);
        System.out.print("Country: ");
        String country = scanner.nextLine();

        System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_PURPLE + "> " + ArkaCustom.ANSI_RESET);
        System.out.print("Province: ");
        String province = scanner.nextLine();

        System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_PURPLE + "> " + ArkaCustom.ANSI_RESET);
        System.out.print("City: ");
        String city = scanner.nextLine();

        System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_PURPLE + "> " + ArkaCustom.ANSI_RESET);
        System.out.print("Barangay: ");
        String barangay = scanner.nextLine();

        System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_PURPLE + "> " + ArkaCustom.ANSI_RESET);
        System.out.print("Street: ");
        String street = scanner.nextLine();

        System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_PURPLE + "> " + ArkaCustom.ANSI_RESET);
        System.out.print("Contact Number: ");
        String contactNumber = scanner.nextLine();

        System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_PURPLE + "> " + ArkaCustom.ANSI_RESET);
        System.out.print("Email Address: ");
        String emailAddress = scanner.nextLine();

        System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_PURPLE + "> " + ArkaCustom.ANSI_RESET);
        System.out.print("Occupation: ");
        String occupation = scanner.nextLine();

        System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_PURPLE + "> " + ArkaCustom.ANSI_RESET);
        System.out.print("Company Name (optional): ");
        String companyName = scanner.nextLine();

        int annualIncome = 0;
        while (true) {
            try {
                System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_PURPLE + "> " + ArkaCustom.ANSI_RESET);
                System.out.print("Annual Income: ");
                annualIncome = scanner.nextInt();
                scanner.nextLine();
                break;
            } catch (InputMismatchException e) {
                System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_YELLOW + "\t>> " + ArkaCustom.ANSI_RESET);
                System.out.println("Invalid input. Please enter a valid number for Annual Income.");
                scanner.nextLine();
            }
        }

        System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_PURPLE + "> " + ArkaCustom.ANSI_RESET);
        System.out.print("Source of Income: ");
        String sourceIncome = scanner.nextLine();

        return new ArkaClient(clientID, lastName, firstName, middleName, honorific, sex, dateOfBirth, civilStatus,
                placeOfBirth, citizenship, nationality, country, province, city, barangay, street, contactNumber,
                emailAddress, occupation, companyName, annualIncome, sourceIncome, agentID);
    }
}