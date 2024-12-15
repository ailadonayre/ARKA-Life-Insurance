package agent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import db.ArkaDatabase;
import utils.ArkaCustom;

public class ArkaAgentManager {
    private Scanner scanner = new Scanner(System.in);
    private String loggedInAgentID;

    public String generateAgentID() {
        String yearSuffix = String.valueOf(java.time.Year.now().getValue()).substring(2);
        String randomNumbers = String.format("%05d", (int) (Math.random() * 100000));
        return yearSuffix + "-" + randomNumbers;
    }

    public void signUp() {
        String username = null;
        String password = null;
    
        try {
            System.out.println(ArkaCustom.ANSI_BOLD + "\n------------------------------------------------------------------------------------------\n" + ArkaCustom.ANSI_RESET);
            System.out.println(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_PURPLE + "ARKA: " + ArkaCustom.ANSI_RESET + ArkaCustom.ANSI_PURPLE + "Create an account" + ArkaCustom.ANSI_RESET);
            System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_PURPLE + "\n> " + ArkaCustom.ANSI_RESET);
            System.out.print("Enter username (must have at least four characters): ");
            username = scanner.nextLine();
            if (username.trim().isEmpty()) {
                throw new IllegalArgumentException("Username cannot be empty.");
            }
            if (username.length() < 4) {
                throw new IllegalArgumentException("Username must be at least 4 characters long.");
            }
    
            System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_PURPLE + "> " + ArkaCustom.ANSI_RESET);
            System.out.print("Enter password (must have at least eight characters): ");
            password = scanner.nextLine();
            if (password.trim().isEmpty()) {
                throw new IllegalArgumentException("Password cannot be empty.");
            }
            if (password.length() < 8) {
                throw new IllegalArgumentException("Password must be at least 8 characters long.");
            }
    
            try (Connection conn = ArkaDatabase.getConnection()) {
                String checkSql = "SELECT COUNT(*) FROM agent WHERE username = ?";
                try (PreparedStatement checkStatement = conn.prepareStatement(checkSql)) {
                    checkStatement.setString(1, username);
                    ResultSet resultSet = checkStatement.executeQuery();
                    if (resultSet.next() && resultSet.getInt(1) > 0) {
                        System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_YELLOW + "\t>> " + ArkaCustom.ANSI_RESET);
                        System.out.println("Error: Username already exists. Please choose a different username.");
                        return;
                    }
                }
    
                String agentID = generateAgentID();
                String sql = "INSERT INTO agent (agentID, username, password) VALUES (?, ?, ?)";
                try (PreparedStatement statement = conn.prepareStatement(sql)) {
                    statement.setString(1, agentID);
                    statement.setString(2, username);
                    statement.setString(3, password);
    
                    int rowsInserted = statement.executeUpdate();
                    if (rowsInserted > 0) {
                        System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_CYAN + "\t>> " + ArkaCustom.ANSI_RESET);
                        System.out.println("You have successfully signed up!");
                        System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_CYAN + "\t>> " + ArkaCustom.ANSI_RESET);
                        System.out.println("Agent ID: " + ArkaCustom.ANSI_BOLD + agentID + ArkaCustom.ANSI_RESET);
    
                    }
                } catch (SQLException e) {
                    System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_YELLOW + "\t>> " + ArkaCustom.ANSI_RESET);
                    System.out.println("Database error occurred while signing up.");
                    e.printStackTrace();
                }
            } catch (SQLException e) {
                System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_YELLOW + "\t>> " + ArkaCustom.ANSI_RESET);
                System.out.println("Error establishing a database connection during sign-up.");
                e.printStackTrace();
            }
    
        } catch (IllegalArgumentException e) {
            System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_YELLOW + "\t>> " + ArkaCustom.ANSI_RESET);
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_YELLOW + "\t>> " + ArkaCustom.ANSI_RESET);
            System.out.println("An unexpected error occurred during sign-up.");
            e.printStackTrace();
        }
    }    

    public boolean signIn() {
        String identifier = null;
        String password = null;

        try {
            System.out.println(ArkaCustom.ANSI_BOLD + "\n------------------------------------------------------------------------------------------\n" + ArkaCustom.ANSI_RESET);
            System.out.println(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_PURPLE + "ARKA: " + ArkaCustom.ANSI_RESET + ArkaCustom.ANSI_PURPLE + "Sign in to your Account" + ArkaCustom.ANSI_RESET);
            System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_PURPLE + "\n> " + ArkaCustom.ANSI_RESET);
            System.out.print("Enter username or agent ID: ");
            identifier = scanner.nextLine();
            if (identifier.trim().isEmpty()) {
                throw new IllegalArgumentException("Username/Agent ID cannot be empty.");
            }

            System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_PURPLE + "> " + ArkaCustom.ANSI_RESET);
            System.out.print("Enter password: ");
            password = scanner.nextLine();
            if (password.trim().isEmpty()) {
                throw new IllegalArgumentException("Password cannot be empty.");
            }

            try (Connection conn = ArkaDatabase.getConnection()) {
                String sql = "SELECT * FROM agent WHERE (username = ? OR agentID = ?) AND password = ?";
                try (PreparedStatement statement = conn.prepareStatement(sql)) {
                    statement.setString(1, identifier);
                    statement.setString(2, identifier);
                    statement.setString(3, password);

                    ResultSet resultSet = statement.executeQuery();
                    if (resultSet.next()) {
                        loggedInAgentID = resultSet.getString("agentID");
                        System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_CYAN + "\t>> " + ArkaCustom.ANSI_RESET);
                        System.out.println("You have successfully signed in as " + ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_CYAN + resultSet.getString("username") + ArkaCustom.ANSI_RESET + "!");
                        return true;
                    } else {
                        System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_YELLOW + "\t>> " + ArkaCustom.ANSI_RESET);
                        System.out.println("Invalid username/agent ID or password. Please try again.");
                        return false;
                    }
                } catch (SQLException e) {
                    System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_YELLOW + "\t>> " + ArkaCustom.ANSI_RESET);
                    System.out.println("Database error occurred during sign-in.");
                    e.printStackTrace();
                    return false;
                }
            } catch (SQLException e) {
                System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_YELLOW + "\t>> " + ArkaCustom.ANSI_RESET);
                System.out.println("Error establishing a database connection during sign-in.");
                e.printStackTrace();
                return false;
            }

        } catch (IllegalArgumentException e) {
            System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_YELLOW + "\t>> " + ArkaCustom.ANSI_RESET);
            System.out.println("Error: " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_YELLOW + "\t>> " + ArkaCustom.ANSI_RESET);
            System.out.println("An unexpected error occurred during sign-in.");
            e.printStackTrace();
            return false;
        }
    }

    public String getLoggedInAgentID() {
        return loggedInAgentID;
    }

    public static void main(String[] args) {
        ArkaAgentManager agentManager = new ArkaAgentManager();

        agentManager.signUp();
        agentManager.signIn();
    }
}