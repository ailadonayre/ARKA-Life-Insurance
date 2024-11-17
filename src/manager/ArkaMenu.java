package manager;

import java.util.InputMismatchException;
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

public class ArkaMenu {
    private ArkaClientManager clientManager = new ArkaClientManager();
    private Scanner scanner = new Scanner(System.in);

    public void showMenu(String agentID) {
        boolean logout = false;

        while (!logout) {
            System.out.println("\n1. View Insurance Plans");
            System.out.println("2. View Clients");
            System.out.println("3. Add Client");
            System.out.println("4. Search for Client");
            System.out.println("5. Update Client Payment");
            System.out.println("6. Log Out");
            System.out.print("Choose an option: ");
            int choice = 0;

            try {
                choice = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a valid number.");
                scanner.nextLine();
                continue;
            }

            switch (choice) {
                case 1:
                    try {
                        ArkaPolicy ginto = new ArkaGintoPlan(clientManager.getPolicyID());
                        ArkaPolicy pilak = new ArkaPilakPlan(clientManager.getPolicyID());
                        ArkaPolicy tanso = new ArkaTansoPlan(clientManager.getPolicyID());
                        System.out.println("\n--- ARKA Insurance Plans ---");
                        ginto.displayPlan();
                        pilak.displayPlan();
                        tanso.displayPlan();
                    } catch (Exception e) {
                        System.out.println("Error displaying insurance plans: " + e.getMessage());
                    }
                    break;
                case 2:
                    try {
                        List<ArkaClient> clients = clientManager.getClientsByAgentID(agentID);
                        clientManager.viewClients(clients);
                    } catch (Exception e) {
                        System.out.println("Error retrieving clients: " + e.getMessage());
                    }
                    break;
                case 3:
                    try {
                        System.out.println("\nEnter client details...");
                
                        ArkaClientInput inputHandler = new ArkaClientInput(scanner, agentID);
                        ArkaClient newClient = inputHandler.collectClientDetails();
                
                        if (newClient == null) {
                            System.out.println("Error: Client details could not be collected.");
                            return;
                        }
                
                        if (agentID == null || agentID.isEmpty()) {
                            System.out.println("Error: Invalid agent ID.");
                            return;
                        }
                
                        clientManager.addClient(newClient, agentID);
                
                        ArkaEligibility eligibility = new ArkaEligibility(scanner);
                        eligibility.checkEligibility(newClient);
                
                        ArkaPolicyRecommender policyRecommender = new ArkaPolicyRecommender(scanner);
                        policyRecommender.recommendPolicy(newClient);
                
                        System.out.println("Client registration and setup complete. Returning to main menu...");
                    } catch (Exception e) {
                        System.out.println("Error during client registration: " + e.getMessage());
                        e.printStackTrace();
                    }
                    break;               
                case 4:
                    try {
                        clientManager.searchClient(scanner, agentID);
                    } catch (Exception e) {
                        System.out.println("Error searching for client: " + e.getMessage());
                    }
                    break;
                case 5:
                    try {
                        ArkaUpdatePayment updatePaymentHandler = new ArkaUpdatePayment();
                        updatePaymentHandler.updateClientPayment(agentID);
                    } catch (Exception e) {
                        System.out.println("Error updating payment: " + e.getMessage());
                    }
                    break;
                case 6:
                    logout = true;
                    System.out.println("Logging out...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}