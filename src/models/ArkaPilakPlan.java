package models;

import utils.ArkaCustom;

public class ArkaPilakPlan extends ArkaPolicy {

    public static final int PREMIUM_TEN_YEARS = 96000;
    public static final int PREMIUM_TWENTY_YEARS = 48750;
    public static final int PREMIUM_THIRTY_FIVE_YEARS = 28000;

    public ArkaPilakPlan(String policyID) {
        super(policyID);
    }

    public void displayPlan() {
        try {
            System.out.println("\n2. " + ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_CYAN + "Pilak (Silver Plan)" + ArkaCustom.ANSI_RESET);
            System.out.println(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_YELLOW + "Primary Benefits" + ArkaCustom.ANSI_RESET);
            System.out.println("Face Amount: Php 1,000,000.00");
            System.out.println("Initial Life Insurance Coverage: Php 1,000,000.00");
            System.out.println("Specific Cancer Booster Benefit: Php 500,000.00");
            System.out.println("Guaranteed Cash Benefit: Php 50,000.00");
            System.out.println("Post Hospitalization Benefit: Php 5,000.00");
            System.out.println("Home Recovery Benefit: Php 620.00 per day");
            System.out.println("Palliative Care Benefit: Php 100,000.00");

            System.out.println(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_YELLOW + "\nSupplementary Benefits" + ArkaCustom.ANSI_RESET);
            System.out.println("Accidental Death Benefit (ADB): Php 1,000,000.00");
            System.out.println("Hospital Income Benefit (HIB): Php 1,250.00 per day");

            System.out.println(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_YELLOW + "\nAnnual Premium Amount" + ArkaCustom.ANSI_RESET);
            System.out.println("10 years to pay: Php " + PREMIUM_TEN_YEARS + ".00");
            System.out.println("20 years to pay: Php " + PREMIUM_TWENTY_YEARS + ".00");
            System.out.println("35 years to pay: Php " + PREMIUM_THIRTY_FIVE_YEARS + ".00");

        } catch (Exception e) {
            System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_YELLOW + "\t>> " + ArkaCustom.ANSI_RESET);
            System.out.println("An error occurred while displaying the Pilak plan details.");
            e.printStackTrace();
        }
    }

    @Override
    public String getPlanName() {
        return "Pilak Plan";
    }

    @Override
    public int getCoverageAmount() {
        return 1000000;
    }

    @Override
    public double getPremiumAmount(int years) {
        double premium = 0.0;

        try {
            if (years != 10 && years != 20 && years != 35) {
                System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_YELLOW + "\t>> " + ArkaCustom.ANSI_RESET);
                throw new IllegalArgumentException("Invalid number of years. Valid options are 10, 20, or 35.");
            }

            switch (years) {
                case 10:
                    premium = PREMIUM_TEN_YEARS;
                    break;
                case 20:
                    premium = PREMIUM_TWENTY_YEARS;
                    break;
                case 35:
                    premium = PREMIUM_THIRTY_FIVE_YEARS;
                    break;
                default:
                    System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_YELLOW + "\t>> " + ArkaCustom.ANSI_RESET);
                    throw new IllegalArgumentException("Invalid number of years. Valid options are 10, 20, or 35.");
            }

        } catch (IllegalArgumentException e) {
            System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_YELLOW + "\t>> " + ArkaCustom.ANSI_RESET);
            System.out.println("Error: " + e.getMessage());
            premium = 0.0;
        } catch (Exception e) {
            System.out.print(ArkaCustom.ANSI_BOLD + ArkaCustom.ANSI_YELLOW + "\t>> " + ArkaCustom.ANSI_RESET);
            System.out.println("An unexpected error occurred while calculating the premium amount.");
            e.printStackTrace();
        }

        return premium;
    }
}