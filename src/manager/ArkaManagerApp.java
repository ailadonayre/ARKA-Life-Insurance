package manager;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import agent.ArkaAgentManager;
import utils.ArkaCustom;

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

        LocalDate currentDate = LocalDate.now();
        String formattedDate = currentDate.format(DateTimeFormatter.ofPattern("MMMM dd, yyyy"));

        while (!exit) {
            System.out.println(ArkaCustom.ANSI_BOLD + "\n------------------------------------------------------------------------------------------\n" + ArkaCustom.ANSI_RESET);
            System.out.println(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_PURPLE + "ARKA: " + ArkaCustom.ANSI_RESET + ArkaCustom.ANSI_PURPLE + "Arangkada Life Insurance" + ArkaCustom.ANSI_RESET);
            System.out.println(ArkaCustom.ANSI_CYAN + formattedDate + ArkaCustom.ANSI_RESET);
            System.out.println("\n1. Sign Up\n2. Sign In\n3. Exit");
            System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_PURPLE + "\n> " + ArkaCustom.ANSI_RESET);
            System.out.print("Choose an option (press 'X' to cancel): " + ArkaCustom.ANSI_RESET);

            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("X")) {
                System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_CYAN + "\t>> " + ArkaCustom.ANSI_RESET);
                System.out.println("Operation canceled. Returning to the main menu.");
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
                        System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_YELLOW + "\t>> " + ArkaCustom.ANSI_RESET);
                        System.out.println("Sign in failed. Please check your log-in credentials.");
                    }
                    break;
                case 3:
                    exit = true;
                    System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_CYAN + "\t>> " + ArkaCustom.ANSI_RESET);
                    System.out.println("Exiting program...");
                    break;
                default:
                    System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_YELLOW + "\t>> " + ArkaCustom.ANSI_RESET);
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