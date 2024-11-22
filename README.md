<img src="images/ARKA banner.jpg" alt="ARKA Arangkada Life Insurance Banner">

# ARKA: Arangkada Life Insurance
**ARKA: Arangkada Life Insurance** is the lifetime safety net of middle to low-income Filipino families with insurance policies of varying 
benefits marked at a fair price.

## :ledger: Index

- [About](#beginner-about)
  - [Objectives](#dart-objectives)
  - [Sustainable Development Goals (SDGs)](#seedling-sustainable-development-goals-sdgs)
- [Project Development](#hammer_and_wrench-project-development)
  - [Prerequisites](#clipboard-prerequisites)
  - [Build Instructions](#hammer-build-instructions)
  - [File Structure](#file_folder-file-structure)
  - [Program Features](#memo-program-features)
  - [Classes](#bookmark-classes)
- [Object-Oriented Programming Principles](#wrench-object-oriented-programming-principles)
  - [Encapsulation](#lock_with_ink_pen-encapsulation)
  - [Inheritance](#envelope_with_arrow-inheritance)
  - [Polymorphism](#card_index_dividers-polymorphism)
  - [Abstraction](#key-abstraction)
- [Future Prospects](#hourglass_flowing_sand-future-prospects)
- [About the Developer](#cherry_blossom-about-the-developer)
- [Acknowledgment](#star2-acknowledgment)

##  :beginner: About
**ARKA Life Insurance**, from the Filipino word *arangkada* - meaning *"to accelerate"* - champions the progression and wellbeing of the general 
Filipino populace, with emphasis to the more marginalized sectors of the country. Low to lower-middle class families, minimum wage earners, and even 
students, are the prime target demographic of ARKA's insurance policies. 
<br/><br/>
This program is to be used specifically by insurance agents under ARKA as they record their client's personal information and financial standing. 
As it is imperative for clients to maintain the regularity of their payments, ARKA has also devised a criteria to ascertain the eligibility of individuals 
applying for an insurance plan by means of assessing their financial constraints. The obtained information then displays the most optimum policy 
recommendations aligned with the client's needs and capacity.

  ###  :dart: Objectives
  This program was materialized as a means to fulfill the following objectives:
  - Promote quality treatment and services to the disadvantaged and vulnerable members of society
  - Provide protection against large, unprecedented risks and accidental losses
  - Ensure financial security and stability in the long run
  - Guaranteed recompense to listed beneficiaries in unforeseen events
  - Benefits coverage extends from post-hospitalization fees to palliative care compensation

  ### :seedling: Sustainable Development Goals (SDGs)
  In compliance with the United Nations' (UN) 2030 agenda for sustainable development, I built my program around the pursuits of the following goals:
  
  | Sustainable Development Goals (SDG) | Significance 
  |-------------------------------------|--------------|
  | **SDG 1: No Poverty** | The primary purpose of ARKA is to supply financial benefits to individuals for unanticipated circumstances (e.g., accidents and medical emergencies) where budgetary liabilities are high. It prevents families from falling into debt and        impoverished conditions when their net income fails to suffice for medical charges.
  | **SDG 3: Good Health and Well-being** | ARKA's benefits largely revolve around medical plights as it aims to help with the preservation of life by means of financial compensation. Access to healthcare is also thereby championed while providing hospitalization,      home recovery, and palliative care benefits for the policyholder, and in some casses, even cancer booster benefits for critical complications.
  | **SDG 8: Decent Work and Economic Growth** | The implementation of ARKA will create new employment opportunities for insurance agents, as well as system and database administrators responsible for regular program maintenance and optimization. By offering fair       wages and a supportive working environment, ARKA will enhance career productivity, contributing to a more stable and progressive economy.
  | **SDG 10 - Reduced Inequalities** | By virtue of ARKA's structured and all-inclusive policies, families from all sorts of backgrounds and social footing are given a selection of lifetime plans without much discrepancies in financial security and healthcare access   between coverages. This ensures that all of our policyholders are well protected and tended to regardless of their chosen insurance policy.
  
##  :hammer_and_wrench: Project Development
ARKA: Arangkada Life Insurance is an insurance management system developed using **Java (JDK 8)** and integrated with a **MySQL server** for efficient database management. The project employed **Apache Maven**, 
a versatile build automation tool, to streamline source code compilation, manage dependencies, package binary artifacts, and facilitate the execution of test scripts, ensuring a smooth and reliable development 
process. Furthermore, the development environment for this project was **Visual Studio Code**, owing to its extensive plugin ecosystem and excellent support for Java development.

  ###  :clipboard: Prerequisites
  - **Code Editor or IDE** (e.g., Visual Studio Code)
  - **Java Development Kit (JDK)**
  - **MySQL Database**
    - Update the JDBC URL, username, and password in ```ArkaDatabase``` according to your credentials.
  - **MySQL Connector**
    - Download and install the MySQL JDBC Driver from the official [MySQL Community Downloads](https://dev.mysql.com/downloads/connector/j/).
  - **Apache Maven**
  - **Project Directory**
    - Observe proper [file structuring](#file_folder-file-structure) to avoid program errors.

  ### :hammer: Build Instructions
  1. **Open the project folder.** Download the .zip file of the project and extract it to your desired folder. Navigate to the project root where ```src``` is located.
  2. **Compile the program.** 

  ```
  javac -d out -cp lib/mysql-connector-java-9.1.0.jar $(find src -name "*.java")
  ```

  3. **Run the program.** Following successful compilation, run the program using the command below:

  ```
  java -cp out:lib/mysql-connector-java-9.1.0.jar main.Main
  ```
  
  ###  :file_folder: File Structure
  
  ```
  ├── src
  │   ├── agent
  │   │   ├── ArkaAgent.java
  │   │   └── ArkaAgentManager.java
  │   ├── client
  │   │   ├── payment
  │   │   │   ├── ArkaPayment.java
  │   │   │   ├── ArkaStatement.java
  │   │   │   └── ArkaUpdatePayment.java
  │   │   ├── policy
  │   │   │   ├── ArkaBeneficiary.java
  │   │   │   ├── ArkaEligibility.java
  │   │   │   └── ArkaPolicyRecommender.java
  │   │   ├── ArkaClient.java
  │   │   ├── ArkaClientInput.java
  │   │   └── ArkaClientManager.java
  │   ├── database
  │   │   └── ArkaDatabase.java
  │   ├── main
  │   │   └── Main.java
  │   ├── manager
  │   │   ├── ArkaManagerApp.java
  │   │   └── ArkaMenu.app
  │   ├── models
  │   │   ├── ArkaGintoPlan.java
  │   │   ├── ArkaPilakPlan.java
  │   │   ├── ArkaPolicy.java
  │   │   └── ArkaTansoPlan.java
  │   └── utils
  │       └── ArkaCustom.java
  ```

  ###  :memo: Program Features
  In ARKA, the features are well thought of to endorse ease of navigation and general convenience for the agents who are expected to operate the program. 
  Listed below are the functionalities of the program:
  - **Main Menu Navigation System**
  - **Agent Menu System**
    - Display, add, remove, search, and update methods
  - **User Management**
    - Sign up, sign in, and log out methods
    - Unique and random ID generation for agents, clients, policies, and payments
  - **Agent Management**
  - **Client Management**
  - **Policy Management**
  - **Payment Management**
  - **Input and Data Validation**
  - **Arithmetic Computations**
  - **Logical Computations**
  - **Database Management System (MySQL)**
    - Database Connection
    - Data Storage
    - CRUD (Create, Read, Update, and Delete) Operations
  - **Formatted Console**
  - **Exception Handling**

  ###  :bookmark: Classes
  
  | No | Class Name | Purpose 
  |----|------------|-------|
  | 1  | **Main** | Entry point
  | 2  | **ArkaDatabase** | Contains the database connection and direct manipulation of tables
  | 3  | **ArkaCustom** | Separate class for console formatting
  | 4  | **ArkaAgent** | Includes the attributes of agent
  | 5  | **ArkaAgentManager** | Contains the methods for signing up and signing in
  | 6  | **ArkaClient** | Includes the attributes of client
  | 7  | **ArkaClientManager** | Composed of all the methods in correlation with client
  | 8  | **ArkaPolicy** | Abstract class containing the shared attributes of the policies
  | 9  | **ArkaGintoPlan** | Contains information about the Ginto Plan; a concrete subclass of ```ArkaPolicy```
  | 10  | **ArkaPilakPlan** | Contains information about the Pilak Plan; a concrete subclass of ```ArkaPolicy```
  | 11  | **ArkaTansoPlan** | Contains information about the Tanso Plan; a concrete subclass of ```ArkaPolicy```
  | 12  | **ArkaManagerApp** | Initial menu (sign up and sign in)
  | 13  | **ArkaMenu** | Agent menu (menu navigation for the main functionalities)
  | 14  | **ArkaClientInput** | Encompasses the inputs of the client's biodata
  | 15  | **ArkaEligibility** | Includes the calculation of client's eligibility score and financial validation
  | 16  | **ArkaPolicyRecommender** | Obtains data from ```ArkaEligibility``` to form policy recommendations
  | 17  | **ArkaBeneficiary** | Collects the beneficiary details of the client
  | 18  | **ArkaStatement** | Composes the proposal statement of the client
  | 19  | **ArkaPayment** | Processes the insurance payment of the client
  | 20  | **ArkaUpdatePayment** | Updates the client's insurance statement upon validating the next payment date
  
##  :wrench: Object-Oriented Programming Principles
Object-oriented programming is one of the programming paradigms that is distinguished by objects and classes. It implements principles such as 
**encapsulation, inheritance, polymorphism,** and **abstraction** among others to systematically arrange a collection of data into one central module known 
as a class.

  ### :lock_with_ink_pen: Encapsulation
  Encapsulation is one of the core principles in object-oriented programming as it the categorization of similar attributes into a class; it is essentially the
  foundation of OOP. Through access modifiers (```public, private,``` and ```protected```), the accessibility of certain fields within a class is explicitly defined.
  <br/><br/>
  In ```ArkaClient```, the data members like ```clientID``` and ```fistName``` are set to ```protected``` so as to restrict its reachability to only methods within the
  the said class, the same package, or in the inheritance hierarchy. 

  ```
  public class ArkaClient {
    protected String clientID;
    protected String lastName;
    protected String firstName;
  ```

  However, there are some instances wherein these attributes must be called from a different package or class,  whereof it surpasses the set access modifier. In this regard, 
  getter and setter methods were used to allow controlled access to the protected fields in ```ArkaClient```. 

  ```
  public String getClientID() {
    return clientID;
  }
  ```

  ```
  public String getFirstName() {
    return firstName;
  }
  ```

  By virtue of this, **data integrity and security** is ultimately preserved in the program and confidential information are not susceptible to unwarranted manipulation.
  
  ###  :envelope_with_arrow: Inheritance
  In OOP, inheritance refers to the authority given to a derived class to inherit the data members and methods of a base class or parent class. This principle allows for 
  **code reusability, effective polymorphism,** and **program organization** without tradeoffs in the quality of code composition.

  ```
  public abstract class ArkaPolicy {
  ```
  
  In this project, ```ArkaPolicy``` is the base class or the blueprint of all insurance policies. Whereas ```ArkaGintoPlan```, ```ArkaPilakPlan```, and ```ArkaTansoPlan``` 
  are the derived classes - each illustrating a distinct policy plan - that inherit from the parent class. 
  
  ```
  public class ArkaGintoPlan extends ArkaPolicy {
  ```

  ```ArkaPolicy``` encompasses attributes such as ```policyID```, ```premiumAmount```, and ```paymentPeriod```, and methods like ```getPlanName()```, ```getCoverageAmount()```,
  ```getPremiumAmount()```, and ```displayPlan()``` which are all inherited and referenced in its derived classes. In this manner, data redundancy is reduced and common behaviors 
  are definitively reused in the inheritance hierarchy. 
  <br/><br/>
  Moreover, certain methods (e.g., ```displayPlan()```) can perform distinctively in accordance with its method definition in the derived classes – which is, in essence, another 
  object-oriented programming principle in play called polymorphism.
  
  ###  :card_index_dividers: Polymorphism
  The fourth OOP principle, polymorphism, enables methods to act in line with the object performing the function call. The characteristics of this principle allows for more 
  **dynamic and context-specific functions** that modify its behavior based on the runtime type of the object that invokes the method.
  <br/><br/>
  In ```ArkaPolicy```, there are several methods that were passed on to its subclasses with each implementation providing functionality specific to the derived class. 
  ```displayPlan()``` is one of the methods declared in the said class and consequently defined uniquely in the other policy classes. When creating an instance of ```ArkaGintoPlan``` 
  and calling ```displayPlan()``` from it, only the implementation defined in the ```ArkaGintoPlan``` class will execute. The method defined in ArkaPolicy is overridden in the 
  ```ArkaGintoPlan``` class, so the overridden version in ```ArkaGintoPlan``` takes precedence.

  ```
  // ArkaGintoPlan
  public void displayPlan() {
        System.out.println("Ginto Plan");
  ```

  ```
  // ArkaPilakPlan
  public void displayPlan() {
        System.out.println("Pilak Plan");
  ```

  ```
  // ArkaTansoPlan
  public void displayPlan() {
        System.out.println("Tanso Plan");
  ```

  ```
  ArkaPolicy ginto = new ArkaGintoPlan();
  ginto.displayPlan();
  ```

  Thus, if I were to call ```ginto.displayPlan()``` (assuming that ```ginto``` is an instance of the class ```ArkaGintoPlan```), only "Ginto Plan" will be displayed. This is because the 
  ```displayPlan()``` method in ```ArkaGintoPlan``` overrides the one in ```ArkaPolicy```. The methods in ```ArkaPilakPlan``` and ```ArkaTansoPlan``` are entirely irrelevant to this object 
  since they belong to other subclasses and are not part of the ```ginto``` instance.
  
  ###  :key: Abstraction
  Abstraction refers to the practice of exposing only the necessary functionality to the user while hiding implementation particulars of a program. Through the definition of ```ArkaPolicy``` as an 
  abstract class, it ensures that its common attributes are shared across all of its derived classes. 
  
  ```
  public abstract class ArkaPolicy {
  ```

  Since ```ArkaGintoPlan```, ```ArkaPilakPlan```, and ```ArkaTansoPlan``` are concrete subclasses of abstract super class ```ArkaPolicy```, the abstract methods defined in the latter must also be 
  implemented in its subclasses. This explains the mutual presence of ```getPlanName()```, ```getCoverageAmount()```, ```getPremiumAmount()```, and ```displayPlan()``` in all the derived classes that 
  extends from ```ArkaPolicy```. Although the aforementioned methods were defined in ```ArkaPolicy```, they were not implemented in the said class due to its ```abstract``` class configuration. Thus, it 
  is the concrete subclasses' position to provide the implementation of those methods.
  
  ```
  @Override
  public String getPlanName() {
        return "Ginto Plan";
  }

  @Override
  public int getCoverageAmount() {
        return 3000000;
  }
  ```
  
  By virtue of abstraction, **code reusability and scalability** are supported accross the entire inheritance hierarchy and a uniform structure is observed in the program instructions (especially in 
  ```ArkaPolicy``` and its derived classes), allowing ease of management, optimized code organization, and reduced method duplications that could compromise the quality of the program.

## :hourglass_flowing_sand: Future Prospects
The following are the future checkpoints after ARKA's initial deployment:

| No | Future Prospects | Purpose
|----|------------------|---------|
| 1  | **UI/UX Integration** | Create a user interface (UI) design for enhanced user-program interactions and ease of navigation
| 2  | **Web-based Platform Expansion** | Integrate a web-based platform to secure access to more upscaled features (e.g., profile creation, file uploading, etc.,)
| 3  | **Mobile Application Expansion** | Develop a mobile application of ARKA to encourage user engagement and increase general convenience

## :cherry_blossom: About the Developer
Hi! I am **Aila Roshiele C. Donayre**, the program developer and initiator behind ARKA: Arangkada Life Insurance. This is my first time fiddling with Java as a novice programmer, as the only 
programming languages that I have ever got my hands on were C++, and Python. ARKA holds a special place in my heart for it is one of my first individual projects and is definitely something that 
I will remember in retrospect as one of my starter programs. 
<br/><br/>
As a stakeholder in our university, I find most of my peers are not well-versed with the benefits of acquiring a lifetime insurance plan; even I have minimal knowledge about its administration despite 
having a policy plan under my name. Hence, during ideation, I made references to my very own insurance plan so as to gather sufficient information for the functionalities of the program. I also 
inquired my family members regarding their insurance contracts and the assessments implemented to apply for one. Furthermore, I researched common constraints observed in insurance companies and 
worked against those.
<br/><br/>
With these pieces of information, I managed to develop **ARKA: Arangkada Life Insurance** with consideration to the less privileged members of society due to their inaccessibility 
to such amenities and an easily navigated front-end structure for the agents that will primarily make use of the program.

## :star2: Acknowledgment
To **Miss Fatima Marie Agdon**, this project would not have proven its success without your transformative pedagogical methods and invaluable knowledge in Java programming and the principles 
of object-oriented programming. The accomplishment of this program is not only a fruit of all the hard work poured into its ideation and materialization, but also a testament of your excellent 
mentorship this semester. Thank you for your support and guidance. 
