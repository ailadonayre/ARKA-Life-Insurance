package client.policy;

import java.util.InputMismatchException;
import java.util.Scanner;

import client.ArkaClient;

public class ArkaEligibility {
    private Scanner scanner;

    public ArkaEligibility(Scanner scanner) {
        this.scanner = scanner;
    }

    public void checkEligibility(ArkaClient client) {
        System.out.println("\nChecking eligibility for client: " + client.getFirstName() + " " + client.getLastName());

        try {
            if (client.getAnnualIncome() >= 15000) {
                System.out.println("Client is eligible for insurance.");
            } else {
                System.out.println("Client does not meet the minimum income requirement for insurance.");
            }
        } catch (Exception e) {
            System.out.println("Error checking eligibility: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public double calculateEligibilityScore(ArkaClient client) {
        double monthlyIncome = client.getAnnualIncome() / 12;

        double monthlyDebt = 0, monthlySavings = 0, monthlyEmergencyFund = 0;
        String investmentOrBusiness = "", isFirstTime = "";

        try {
            System.out.print("\nMonthly Debt: ");
            monthlyDebt = scanner.nextDouble();
            scanner.nextLine();

            System.out.print("Monthly Savings: ");
            monthlySavings = scanner.nextDouble();
            scanner.nextLine();

            System.out.print("Monthly Emergency Fund: ");
            monthlyEmergencyFund = scanner.nextDouble();
            scanner.nextLine();

            System.out.print("Do you have any investments or businesses? (Y/N): ");
            investmentOrBusiness = scanner.nextLine();

            System.out.print("Is this your first time purchasing an insurance policy? (Y/N): ");
            isFirstTime = scanner.nextLine();

        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter numeric values for debt, savings, and emergency fund.");
            scanner.nextLine();
            return 0;
        } catch (Exception e) {
            System.out.println("An error occurred while calculating the eligibility score.");
            e.printStackTrace();
            return 0;
        }

        double debtScore = calculateDebtScore(monthlyDebt, monthlyIncome);
        double savingsScore = calculateSavingsScore(monthlySavings, monthlyIncome);
        double emergencyFundScore = calculateEmergencyFundScore(monthlyEmergencyFund, monthlyIncome);
        double investmentScore = calculateInvestmentScore(investmentOrBusiness);
        double firstTimeScore = calculateFirstTimeScore(isFirstTime);

        return debtScore + savingsScore + emergencyFundScore + investmentScore + firstTimeScore;
    }

    private double calculateDebtScore(double monthlyDebt, double monthlyIncome) {
        if (monthlyDebt == 0) return 40.0;
        double debtPercentage = (monthlyDebt / monthlyIncome) * 100;
        if (debtPercentage < 30) {
            return 40 - (debtPercentage / 30) * 40;
        } else if (debtPercentage >= 30 && debtPercentage < 100) {
            return 40 - ((debtPercentage - 30) / 70) * 40;
        } else {
            return 0;
        }
    }

    private double calculateSavingsScore(double monthlySavings, double monthlyIncome) {
        double savingsPercentage = (monthlySavings / monthlyIncome) * 100;
        if (savingsPercentage >= 20) {
            return 30.0;
        } else {
            return (savingsPercentage / 20) * 30;
        }
    }

    private double calculateEmergencyFundScore(double monthlyEmergencyFund, double monthlyIncome) {
        double emergencyFundPercentage = (monthlyEmergencyFund / monthlyIncome) * 100;
        if (emergencyFundPercentage >= 10) {
            return 20.0;
        } else {
            return (emergencyFundPercentage / 10) * 20;
        }
    }

    private double calculateInvestmentScore(String investmentOrBusiness) {
        return investmentOrBusiness.equalsIgnoreCase("Y") ? 5.0 : 0.0;
    }

    private double calculateFirstTimeScore(String isFirstTime) {
        return isFirstTime.equalsIgnoreCase("Y") ? 0.0 : 5.0;
    }
}
