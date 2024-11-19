package manager;

import java.util.List;
import java.util.Scanner;

import client.ArkaClient;
import client.ArkaClientInput;
import client.ArkaClientManager;
import client.payment.ArkaUpdatePayment;
import client.policy.ArkaEligibility;
import client.policy.ArkaPolicyRecommender;
import models.ArkaGintoPlan;
import models.ArkaPilakPlan;
import models.ArkaPolicy;
import models.ArkaTansoPlan;
import utils.ArkaCustom;

public class ArkaMenu {
    private ArkaClientManager clientManager = new ArkaClientManager();
    private Scanner scanner = new Scanner(System.in);

    public void showMenu(String agentID) {
        boolean logout = false;

        while (!logout) {
            System.out.println(ArkaCustom.ANSI_BOLD + "\n------------------------------------------------------------------------------------------\n" + ArkaCustom.ANSI_RESET);
            System.out.println(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_PURPLE + "ARKA " + ArkaCustom.ANSI_RESET + ArkaCustom.ANSI_PURPLE + "Agent Menu" + ArkaCustom.ANSI_RESET);
            System.out.println(ArkaCustom.ANSI_YELLOW + "\r\n" + //
                                "░█████╗░██████╗░██╗░░██╗░█████╗░\r\n" + //
                                "██╔══██╗██╔══██╗██║░██╔╝██╔══██╗\r\n" + //
                                "███████║██████╔╝█████═╝░███████║\r\n" + //
                                "██╔══██║██╔══██╗██╔═██╗░██╔══██║\r\n" + //
                                "██║░░██║██║░░██║██║░╚██╗██║░░██║\r\n" + //
                                "╚═╝░░╚═╝╚═╝░░╚═╝╚═╝░░╚═╝╚═╝░░╚═╝" + ArkaCustom.ANSI_RESET);
            System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_PURPLE + "\nARKA: " + ArkaCustom.ANSI_RESET + ArkaCustom.ANSI_PURPLE + "Arangkada Life Insurance" + ArkaCustom.ANSI_RESET);
            System.out.println(" is developed to grant middle to low-income Filipino \nfamilies a lifetime safety net with insurance policies of varying benefits marked at a \nfair price.");
            System.out.println("\n1. Display Insurance Plans");
            System.out.println("2. View Clients");
            System.out.println("3. Add a Client");
            System.out.println("4. Search for Client");
            System.out.println("5. Update Insurance Payment");
            System.out.println("6. Log Out");
            System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_PURPLE + "\n> " + ArkaCustom.ANSI_RESET);
            System.out.print("Choose an option (press 'X' to cancel): " + ArkaCustom.ANSI_RESET);

            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("X")) {
                System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_CYAN + "\t>> " + ArkaCustom.ANSI_RESET);
                System.out.println("Operation canceled. Returning to the agent menu.");
                continue;
            }

            int choice = 0;
            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_YELLOW + "\t>> " + ArkaCustom.ANSI_RESET);
                System.out.println("Invalid input. Please enter a number or 'X' to cancel.");
                continue;
            }

            switch (choice) {
                case 1:
                    boolean keepDisplayRunning = true;

                    while (keepDisplayRunning) {
                        try {
                            ArkaPolicy ginto = new ArkaGintoPlan(clientManager.getPolicyID());
                            ArkaPolicy pilak = new ArkaPilakPlan(clientManager.getPolicyID());
                            ArkaPolicy tanso = new ArkaTansoPlan(clientManager.getPolicyID());

                            System.out.println(ArkaCustom.ANSI_BOLD + "\n------------------------------------------------------------------------------------------\n" + ArkaCustom.ANSI_RESET);
                            System.out.println(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_PURPLE + "ARKA: " + ArkaCustom.ANSI_RESET + ArkaCustom.ANSI_PURPLE + "Display Insurance Plans" + ArkaCustom.ANSI_RESET);

                            ginto.displayPlan();
                            pilak.displayPlan();
                            tanso.displayPlan();

                            System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_PURPLE + "\n> " + ArkaCustom.ANSI_RESET);
                            System.out.print("Would you like to go back to the menu? (Y/N): ");
                            String displayChoice = scanner.nextLine().trim();

                            if (displayChoice.equalsIgnoreCase("Y")) {
                                keepDisplayRunning = false;
                            } else if (displayChoice.equalsIgnoreCase("N")) {
                            } else {
                                System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_YELLOW + "\t>> " + ArkaCustom.ANSI_RESET);
                                System.out.println("Invalid input. Please enter 'Y' or 'N'.");
                            }

                        } catch (Exception e) {
                            System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_YELLOW + "\t>> " + ArkaCustom.ANSI_RESET);
                            System.out.println("Error displaying insurance plans: " + e.getMessage());
                            e.printStackTrace();
                        }
                    }
                    break;
                case 2:
                    boolean keepViewRunning = true;  // Variable to control the loop
                
                    while (keepViewRunning) {
                        System.out.println(ArkaCustom.ANSI_BOLD + "\n------------------------------------------------------------------------------------------\n" + ArkaCustom.ANSI_RESET);
                        System.out.println(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_PURPLE + "ARKA: " + ArkaCustom.ANSI_RESET + ArkaCustom.ANSI_PURPLE + "View Clients" + ArkaCustom.ANSI_RESET);
                
                        try {
                            List<ArkaClient> clients = clientManager.getClientsByAgentID(agentID);
                            clientManager.viewClients(clients);
                            
                            System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_PURPLE + "\n> " + ArkaCustom.ANSI_RESET);
                            System.out.print("Would you like to go back to the menu? (Y/N): ");
                            String viewChoice = scanner.nextLine().trim();
                
                            if (viewChoice.equalsIgnoreCase("Y")) {
                                keepViewRunning = false;
                            } else if (viewChoice.equalsIgnoreCase("N")) {
                            } else {
                                System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_YELLOW + "\t>> " + ArkaCustom.ANSI_RESET);
                                System.out.println("Invalid input. Please enter 'Y' or 'N'.");
                            }
                
                        } catch (Exception e) {
                            System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_YELLOW + "\t>> " + ArkaCustom.ANSI_RESET);
                            System.out.println("Error retrieving clients: " + e.getMessage());
                            e.printStackTrace();
                        }
                    }
                    break;                
                case 3:
                    System.out.println(ArkaCustom.ANSI_BOLD + "\n------------------------------------------------------------------------------------------\n" + ArkaCustom.ANSI_RESET);
                    System.out.println(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_PURPLE + "ARKA: " + ArkaCustom.ANSI_RESET + ArkaCustom.ANSI_PURPLE + "Add a Client" + ArkaCustom.ANSI_RESET);
                    
                    try {                
                        ArkaClientInput inputHandler = new ArkaClientInput(scanner, agentID);
                        ArkaClient newClient = inputHandler.collectClientDetails();
                
                        if (newClient == null) {
                            System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_YELLOW + "\t>> " + ArkaCustom.ANSI_RESET);
                            System.out.println("Error: Client details could not be collected.");
                            return;
                        }
                
                        if (agentID == null || agentID.isEmpty()) {
                            System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_YELLOW + "\t>> " + ArkaCustom.ANSI_RESET);
                            System.out.println("Error: Invalid agent ID.");
                            return;
                        }
                
                        clientManager.addClient(newClient, agentID);
                
                        ArkaEligibility eligibility = new ArkaEligibility(scanner);
                        eligibility.checkEligibility(newClient);
                
                        ArkaPolicyRecommender policyRecommender = new ArkaPolicyRecommender(scanner);
                        policyRecommender.recommendPolicy(newClient);
                    } catch (Exception e) {
                        System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_YELLOW + "\t>> " + ArkaCustom.ANSI_RESET);
                        System.out.println("Error during client registration: " + e.getMessage());
                        e.printStackTrace();
                    }
                    break;               
                case 4:
                    boolean keepSearchRunning = true;
                
                    while (keepSearchRunning) {
                        System.out.println(ArkaCustom.ANSI_BOLD + "\n------------------------------------------------------------------------------------------\n" + ArkaCustom.ANSI_RESET);
                        System.out.println(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_PURPLE + "ARKA: " + ArkaCustom.ANSI_RESET + ArkaCustom.ANSI_PURPLE + "Search for Client" + ArkaCustom.ANSI_RESET);
                
                        try {
                            clientManager.searchClient(scanner, agentID);
                            
                            System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_PURPLE + "\n> " + ArkaCustom.ANSI_RESET);
                            System.out.print("Would you like to go back to the menu? (Y/N): ");
                            String searchChoice = scanner.nextLine().trim();
                
                            if (searchChoice.equalsIgnoreCase("Y")) {
                                keepSearchRunning = false;
                            } else if (searchChoice.equalsIgnoreCase("N")) {
                            } else {
                                System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_YELLOW + "\t>> " + ArkaCustom.ANSI_RESET);
                                System.out.println("Invalid input. Please enter 'Y' or 'N'.");
                            }
                
                        } catch (Exception e) {
                            System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_YELLOW + "\t>> " + ArkaCustom.ANSI_RESET);
                            System.out.println("Error searching for client: " + e.getMessage());
                            e.printStackTrace();
                        }
                    }
                    break;                
                case 5:
                    System.out.println(ArkaCustom.ANSI_BOLD + "\n------------------------------------------------------------------------------------------\n" + ArkaCustom.ANSI_RESET);
                    System.out.println(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_PURPLE + "ARKA: " + ArkaCustom.ANSI_RESET + ArkaCustom.ANSI_PURPLE + "Update Insurance Payment" + ArkaCustom.ANSI_RESET);

                    try {
                        ArkaUpdatePayment updatePaymentHandler = new ArkaUpdatePayment();
                        updatePaymentHandler.updateClientPayment(agentID);
                    } catch (Exception e) {
                        System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_YELLOW + "\t>> " + ArkaCustom.ANSI_RESET);
                        System.out.println("Error updating payment: " + e.getMessage());
                    }
                    break;
                case 6:
                    logout = true;
                    System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_CYAN + "\t>> " + ArkaCustom.ANSI_RESET);
                    System.out.println("Logging out...");
                    break;
                default:
                    System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_YELLOW + "\t>> " + ArkaCustom.ANSI_RESET);
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}