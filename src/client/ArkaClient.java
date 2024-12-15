package client;

import java.time.LocalDate;
import java.util.regex.Pattern;

import models.ArkaPolicy;

public class ArkaClient {
    protected String clientID;
    protected String lastName;
    protected String firstName;
    protected String middleName;
    protected String honorific;
    protected String sex;
    protected LocalDate dateOfBirth;
    protected String civilStatus;
    protected String placeOfBirth;
    protected String citizenship;
    protected String nationality;
    protected String country;
    protected String province;
    protected String city;
    protected String barangay;
    protected String street;
    protected String contactNumber;
    protected String emailAddress;
    protected String occupation;
    protected String companyName;
    protected int annualIncome;
    protected String sourceIncome;
    protected String policyID;
    protected ArkaPolicy policy;
    protected String agentID;

    public ArkaClient() {
    }

    public ArkaClient(String clientID, String agentID, String lastName, String firstName, String middleName, String honorific,
                  String sex, LocalDate dateOfBirth, String civilStatus, String placeOfBirth, String citizenship,
                  String nationality, String country, String province, String city, String barangay, String street,
                  String contactNumber, String emailAddress, String occupation, String companyName, int annualIncome,
                  String sourceIncome) {
        if (clientID == null || clientID.isEmpty()) {
            throw new IllegalArgumentException("Client ID cannot be empty.");
        }
        if (lastName == null || lastName.isEmpty()) {
            throw new IllegalArgumentException("Last Name cannot be empty.");
        }
        if (firstName == null || firstName.isEmpty()) {
            throw new IllegalArgumentException("First Name cannot be empty.");
        }
        if (!isValidEmail(emailAddress)) {
            throw new IllegalArgumentException("Invalid email address format.");
        }
        if (annualIncome <= 0) {
            throw new IllegalArgumentException("Annual income must be a positive value.");
        }
        if (contactNumber == null || contactNumber.isEmpty()) {
            throw new IllegalArgumentException("Contact number cannot be empty.");
        }

        if (agentID == null || agentID.isEmpty()) {
            throw new IllegalArgumentException("Agent ID cannot be null or empty.");
        }

        this.clientID = clientID;
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.honorific = honorific;
        this.sex = sex;
        this.dateOfBirth = dateOfBirth;
        this.civilStatus = civilStatus;
        this.placeOfBirth = placeOfBirth;
        this.citizenship = citizenship;
        this.nationality = nationality;
        this.country = country;
        this.province = province;
        this.city = city;
        this.barangay = barangay;
        this.street = street;
        this.contactNumber = contactNumber;
        this.emailAddress = emailAddress;
        this.occupation = occupation;
        this.companyName = companyName;
        this.annualIncome = annualIncome;
        this.sourceIncome = sourceIncome;
        this.agentID = agentID;
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }

    public String getAgentID() {
        return agentID;
    }

    public String getClientID() {
        return clientID;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public ArkaPolicy getPolicy() {
        return policy;
    }

    public String getPolicyID() {
        return policyID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public String getHonorific() {
        return honorific;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public int getAnnualIncome() {
        return annualIncome;
    }

    public void setAgentID(String agentID) {
        this.agentID = agentID;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public void setHonorific(String honorific) {
        this.honorific = honorific;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setClientID(String clientID) {
        this.clientID = clientID;
    }

    public void setPolicyID(String policyID) {
        this.policyID = policyID;
    }
    
    public void setPolicy(ArkaPolicy policy) {
        this.policy = policy;
    }

    public void setAnnualIncome(int annualIncome) {
        if (annualIncome <= 0) {
            throw new IllegalArgumentException("Annual income must be a positive value.");
        }
        this.annualIncome = annualIncome;
    }
}
