package models;

public class ArkaGintoPlan extends ArkaPolicy {

    public static final int PREMIUM_TEN_YEARS = 270000;
    public static final int PREMIUM_TWENTY_YEARS = 142500;
    public static final int PREMIUM_THIRTY_FIVE_YEARS = 82857;

    public ArkaGintoPlan(String policyID) {
        super(policyID);
    }

    public void displayPlan() {
        try {
            System.out.println("\n1. Ginto (Gold Plan)");
            System.out.println("Primary Benefits");
            System.out.println("Face Amount: Php 3,000,000.00");
            System.out.println("Initial Life Insurance Coverage: Php 3,000,000.00");
            System.out.println("Specific Cancer Booster Benefit: Php 1,000,000.00");
            System.out.println("Guaranteed Cash Benefit: Php 100,000.00");
            System.out.println("Post Hospitalization Benefit: Php 15,000.00");
            System.out.println("Home Recovery Benefit: Php 1,000.00 per day");
            System.out.println("Palliative Care Benefit: Php 250,000.00");

            System.out.println("\nSupplementary Benefits");
            System.out.println("Accidental Death Benefit (ADB): Php 3,000,000.00");
            System.out.println("Hospital Income Benefit (HIB): Php 2,000.00 per day");

            System.out.println("\nPremium Amount (10 years to pay)");
            System.out.println("Annually: Php " + PREMIUM_TEN_YEARS + ".00");

            System.out.println("\nPremium Amount (20 years to pay)");
            System.out.println("Annually: Php " + PREMIUM_TWENTY_YEARS + ".00");

            System.out.println("\nPremium Amount (35 years to pay)");
            System.out.println("Annually: Php " + PREMIUM_THIRTY_FIVE_YEARS + ".00");

        } catch (Exception e) {
            System.out.println("An error occurred while displaying the Ginto plan details.");
            e.printStackTrace();
        }
    }

    @Override
    public String getPlanName() {
        return "Ginto Plan";
    }

    @Override
    public int getCoverageAmount() {
        return 3000000;
    }

    @Override
    public double getPremiumAmount(int years) {
        double premium = 0.0;

        try {
            if (years != 10 && years != 20 && years != 35) {
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
                    throw new IllegalArgumentException("Invalid number of years. Valid options are 10, 20, or 35.");
            }

        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
            premium = 0.0;
        } catch (Exception e) {
            System.out.println("An unexpected error occurred while calculating the premium amount.");
            e.printStackTrace();
        }

        return premium;
    }
}