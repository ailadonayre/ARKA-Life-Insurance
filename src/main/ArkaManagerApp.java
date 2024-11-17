package main;

import java.util.InputMismatchException;
import java.util.Scanner;

import agent.ArkaAgentManager;
import manager.ArkaMenu;

public class ArkaManagerApp {
    private ArkaAgentManager agentManager;
    private ArkaMenu arkaMenu;
    private Scanner scanner;

    public ArkaManagerApp() {
        agentManager = new ArkaAgentManager();
        arkaMenu = new ArkaMenu();
        scanner = new Scanner(System.in);
    }

    public void start() {
        boolean exit = false;

        while (!exit) {
            System.out.println("\n1. Sign Up\n2. Sign In\n3. Exit");
            System.out.print("Choose an option: ");
            int choice = 0;

            try {
                choice = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a number.");
                scanner.nextLine();
                continue;
            }

            switch (choice) {
                case 1:
                    signUp();
                    break;
                case 2:
                    boolean isSignedIn = signIn();
                    if (isSignedIn) {
                        String loggedInAgentID = agentManager.getLoggedInAgentID();
                        if (loggedInAgentID != null) {
                            arkaMenu.showMenu(loggedInAgentID);
                        }
                    } else {
                        System.out.println("Sign In failed. Please check your credentials.");
                    }
                    break;
                case 3:
                    exit = true;
                    System.out.println("Exiting program.");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }

        scanner.close();
    }

    private void signUp() {
        agentManager.signUp();
    }

    private boolean signIn() {
        return agentManager.signIn();
    }

    public static void main(String[] args) {
        ArkaManagerApp app = new ArkaManagerApp();
        app.start();
    }
}