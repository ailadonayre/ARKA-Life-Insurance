package agent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import database.ArkaDatabase;

public class ArkaAgentManager {
    private Scanner scanner = new Scanner(System.in);
    private String loggedInAgentID;

    // Function to generate agentID
    public String generateAgentID() {
        String yearSuffix = String.valueOf(java.time.Year.now().getValue()).substring(2);
        String randomNumbers = String.format("%05d", (int) (Math.random() * 100000));
        return yearSuffix + "-" + randomNumbers;
    }

    public void signUp() {
        String username = null;
        String password = null;

        try {
            System.out.print("Enter username: ");
            username = scanner.nextLine();
            if (username.trim().isEmpty()) {
                throw new IllegalArgumentException("Username cannot be empty.");
            }

            System.out.print("Enter password: ");
            password = scanner.nextLine();
            if (password.trim().isEmpty()) {
                throw new IllegalArgumentException("Password cannot be empty.");
            }

            String agentID = generateAgentID();

            try (Connection conn = ArkaDatabase.getConnection()) {
                String sql = "INSERT INTO agent (agentID, username, password) VALUES (?, ?, ?)";
                try (PreparedStatement statement = conn.prepareStatement(sql)) {
                    statement.setString(1, agentID);
                    statement.setString(2, username);
                    statement.setString(3, password);

                    int rowsInserted = statement.executeUpdate();
                    if (rowsInserted > 0) {
                        System.out.println("You have successfully signed up!");
                        System.out.println("Agent ID: " + agentID);
                    }
                } catch (SQLException e) {
                    System.out.println("Database error occurred while signing up.");
                    e.printStackTrace();
                }
            } catch (SQLException e) {
                System.out.println("Error establishing a database connection during sign-up.");
                e.printStackTrace();
            }

        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred during sign-up.");
            e.printStackTrace();
        }
    }

    public boolean signIn() {
        String identifier = null;
        String password = null;

        try {
            System.out.print("Enter username or agent ID: ");
            identifier = scanner.nextLine();
            if (identifier.trim().isEmpty()) {
                throw new IllegalArgumentException("Username/Agent ID cannot be empty.");
            }

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
                        System.out.println("You have successfully signed in as " + resultSet.getString("username") + "!");
                        return true;
                    } else {
                        System.out.println("Invalid username/agent ID or password. Please try again.");
                        return false;
                    }
                } catch (SQLException e) {
                    System.out.println("Database error occurred during sign-in.");
                    e.printStackTrace();
                    return false;
                }
            } catch (SQLException e) {
                System.out.println("Error establishing a database connection during sign-in.");
                e.printStackTrace();
                return false;
            }

        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        } catch (Exception e) {
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