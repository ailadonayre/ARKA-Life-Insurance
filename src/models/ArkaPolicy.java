package models;

public abstract class ArkaPolicy {
    private String policyID;
    private double premiumAmount;
    private int paymentPeriod;

    public ArkaPolicy(String policyID) {
        try {
            if (policyID == null || policyID.trim().isEmpty()) {
                throw new IllegalArgumentException("Policy ID cannot be null or empty.");
            }
            this.policyID = policyID;
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
            this.policyID = "Unknown";
        } catch (Exception e) {
            System.out.println("An unexpected error occurred while setting the Policy ID.");
            e.printStackTrace();
        }
    }

    public String getPolicyID() {
        return policyID;
    }

    public abstract String getPlanName();

    public abstract int getCoverageAmount();

    public abstract double getPremiumAmount(int years);

    public abstract void displayPlan();

    public void setPremiumAmount(double premiumAmount) {
        this.premiumAmount = premiumAmount;
    }

    public void setPaymentPeriod(int paymentPeriod) {
        this.paymentPeriod = paymentPeriod;
    }

    public double getPremiumAmount() {
        return premiumAmount;
    }

    public int getPaymentPeriod() {
        return paymentPeriod;
    }
}
