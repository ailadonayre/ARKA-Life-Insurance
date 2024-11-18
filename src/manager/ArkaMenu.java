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
import utils.ArkaColors;

public class ArkaMenu {
    private ArkaClientManager clientManager = new ArkaClientManager();
    private Scanner scanner = new Scanner(System.in);

    public void showMenu(String agentID) {
        boolean logout = false;

        while (!logout) {
            System.out.println(ArkaColors.ANSI_BOLD + "\n------------------------------------------------------------------------------------------\n" + ArkaColors.ANSI_RESET);
            System.out.println(ArkaColors.ANSI_BOLD + ArkaColors.ANSI_PURPLE + "ARKA " + ArkaColors.ANSI_RESET + ArkaColors.ANSI_PURPLE + "Agent Menu" + ArkaColors.ANSI_RESET);
            System.out.println(ArkaColors.ANSI_YELLOW + "\r\n" + //
                                "░█████╗░██████╗░██╗░░██╗░█████╗░\r\n" + //
                                "██╔══██╗██╔══██╗██║░██╔╝██╔══██╗\r\n" + //
                                "███████║██████╔╝█████═╝░███████║\r\n" + //
                                "██╔══██║██╔══██╗██╔═██╗░██╔══██║\r\n" + //
                                "██║░░██║██║░░██║██║░╚██╗██║░░██║\r\n" + //
                                "╚═╝░░╚═╝╚═╝░░╚═╝╚═╝░░╚═╝╚═╝░░╚═╝" + ArkaColors.ANSI_RESET);
            System.out.print(ArkaColors.ANSI_BOLD + ArkaColors.ANSI_PURPLE + "\nARKA: " + ArkaColors.ANSI_RESET + ArkaColors.ANSI_PURPLE + "Arangkada Life Insurance" + ArkaColors.ANSI_RESET);
            System.out.println(" is developed to grant middle to low-income Filipino \nfamilies a lifetime safety net with insurance policies of varying benefits marked at a \nfair price.");
            System.out.println("\n1. Display Insurance Plans");
            System.out.println("2. View Clients");
            System.out.println("3. Add a Client");
            System.out.println("4. Search for Client");
            System.out.println("5. Update Insurance Payment");
            System.out.println("6. Log Out");
            System.out.print(ArkaColors.ANSI_BOLD + ArkaColors.ANSI_PURPLE + "\n> " + ArkaColors.ANSI_RESET);
            System.out.print("Choose an option (press 'X' to cancel): " + ArkaColors.ANSI_RESET);

            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("X")) {
                System.out.print(ArkaColors.ANSI_BOLD + ArkaColors.ANSI_CYAN + "\t>> " + ArkaColors.ANSI_RESET);
                System.out.println("Operation canceled. Returning to the agent menu.");
                continue;
            }

            int choice = 0;
            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.print(ArkaColors.ANSI_BOLD + ArkaColors.ANSI_YELLOW + "\t>> " + ArkaColors.ANSI_RESET);
                System.out.println("Invalid input. Please enter a number or 'X' to cancel.");
                continue;
            }

            switch (choice) {
                case 1:
                    try {
                        ArkaPolicy ginto = new ArkaGintoPlan(clientManager.getPolicyID());
                        ArkaPolicy pilak = new ArkaPilakPlan(clientManager.getPolicyID());
                        ArkaPolicy tanso = new ArkaTansoPlan(clientManager.getPolicyID());

                        System.out.println(ArkaColors.ANSI_BOLD + "\n------------------------------------------------------------------------------------------\n" + ArkaColors.ANSI_RESET);
                        System.out.println(ArkaColors.ANSI_BOLD + ArkaColors.ANSI_PURPLE + "ARKA: " + ArkaColors.ANSI_RESET + ArkaColors.ANSI_PURPLE + "Display Insurance Plans" + ArkaColors.ANSI_RESET);

                        ginto.displayPlan();
                        pilak.displayPlan();
                        tanso.displayPlan();
                    } catch (Exception e) {
                        System.out.print(ArkaColors.ANSI_BOLD + ArkaColors.ANSI_YELLOW + "\t>> " + ArkaColors.ANSI_RESET);
                        System.out.println("Error displaying insurance plans: " + e.getMessage());
                    }
                    break;
                case 2:
                    System.out.println(ArkaColors.ANSI_BOLD + "\n------------------------------------------------------------------------------------------\n" + ArkaColors.ANSI_RESET);
                    System.out.println(ArkaColors.ANSI_BOLD + ArkaColors.ANSI_PURPLE + "ARKA: " + ArkaColors.ANSI_RESET + ArkaColors.ANSI_PURPLE + "View Clients" + ArkaColors.ANSI_RESET);
                    
                    try {
                        List<ArkaClient> clients = clientManager.getClientsByAgentID(agentID);
                        clientManager.viewClients(clients);
                    } catch (Exception e) {
                        System.out.print(ArkaColors.ANSI_BOLD + ArkaColors.ANSI_YELLOW + "\t>> " + ArkaColors.ANSI_RESET);
                        System.out.println("Error retrieving clients: " + e.getMessage());
                    }
                    break;
                case 3:
                System.out.println(ArkaColors.ANSI_BOLD + "\n------------------------------------------------------------------------------------------\n" + ArkaColors.ANSI_RESET);
                System.out.println(ArkaColors.ANSI_BOLD + ArkaColors.ANSI_PURPLE + "ARKA: " + ArkaColors.ANSI_RESET + ArkaColors.ANSI_PURPLE + "Add a Client" + ArkaColors.ANSI_RESET);
                    
                    try {                
                        ArkaClientInput inputHandler = new ArkaClientInput(scanner, agentID);
                        ArkaClient newClient = inputHandler.collectClientDetails();
                
                        if (newClient == null) {
                            System.out.print(ArkaColors.ANSI_BOLD + ArkaColors.ANSI_YELLOW + "\t>> " + ArkaColors.ANSI_RESET);
                            System.out.println("Error: Client details could not be collected.");
                            return;
                        }
                
                        if (agentID == null || agentID.isEmpty()) {
                            System.out.print(ArkaColors.ANSI_BOLD + ArkaColors.ANSI_YELLOW + "\t>> " + ArkaColors.ANSI_RESET);
                            System.out.println("Error: Invalid agent ID.");
                            return;
                        }
                
                        clientManager.addClient(newClient, agentID);
                
                        ArkaEligibility eligibility = new ArkaEligibility(scanner);
                        eligibility.checkEligibility(newClient);
                
                        ArkaPolicyRecommender policyRecommender = new ArkaPolicyRecommender(scanner);
                        policyRecommender.recommendPolicy(newClient);
                        
                        System.out.print(ArkaColors.ANSI_BOLD + ArkaColors.ANSI_CYAN + "\t>> " + ArkaColors.ANSI_RESET);
                        System.out.println("Client registration and setup complete. Returning to main menu...");
                    } catch (Exception e) {
                        System.out.print(ArkaColors.ANSI_BOLD + ArkaColors.ANSI_YELLOW + "\t>> " + ArkaColors.ANSI_RESET);
                        System.out.println("Error during client registration: " + e.getMessage());
                        e.printStackTrace();
                    }
                    break;               
                case 4:
                    System.out.println(ArkaColors.ANSI_BOLD + "\n------------------------------------------------------------------------------------------\n" + ArkaColors.ANSI_RESET);
                    System.out.println(ArkaColors.ANSI_BOLD + ArkaColors.ANSI_PURPLE + "ARKA: " + ArkaColors.ANSI_RESET + ArkaColors.ANSI_PURPLE + "Search for Client" + ArkaColors.ANSI_RESET);
                    
                    try {
                        clientManager.searchClient(scanner, agentID);
                    } catch (Exception e) {
                        System.out.print(ArkaColors.ANSI_BOLD + ArkaColors.ANSI_YELLOW + "\t>> " + ArkaColors.ANSI_RESET);
                        System.out.println("Error searching for client: " + e.getMessage());
                    }
                    break;
                case 5:
                    System.out.println(ArkaColors.ANSI_BOLD + "\n------------------------------------------------------------------------------------------\n" + ArkaColors.ANSI_RESET);
                    System.out.println(ArkaColors.ANSI_BOLD + ArkaColors.ANSI_PURPLE + "ARKA: " + ArkaColors.ANSI_RESET + ArkaColors.ANSI_PURPLE + "Update Insurance Payment" + ArkaColors.ANSI_RESET);

                    try {
                        ArkaUpdatePayment updatePaymentHandler = new ArkaUpdatePayment();
                        updatePaymentHandler.updateClientPayment(agentID);
                    } catch (Exception e) {
                        System.out.print(ArkaColors.ANSI_BOLD + ArkaColors.ANSI_YELLOW + "\t>> " + ArkaColors.ANSI_RESET);
                        System.out.println("Error updating payment: " + e.getMessage());
                    }
                    break;
                case 6:
                    logout = true;
                    System.out.print(ArkaColors.ANSI_BOLD + ArkaColors.ANSI_CYAN + "\t>> " + ArkaColors.ANSI_RESET);
                    System.out.println("Logging out...");
                    break;
                default:
                    System.out.print(ArkaColors.ANSI_BOLD + ArkaColors.ANSI_YELLOW + "\t>> " + ArkaColors.ANSI_RESET);
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}