# ARKA: Arangkada Life Insurance
**ARKA: Arangkada Life Insurance** is the lifetime safety net of middle to low-income Filipino families with insurance policies of varying 
benefits marked at a fair price.

## :ledger: Index

- [About](#beginner-about)
  - [Objectives](#clipboard-objectives)
  - [Sustainable Development Goals (SDGs)](#seedling-sustainable-development-goals-sdgs)
- [Project Development](#hammer_and_wrench-project-development)
  - [Program Features](#memo-program-features)
  - [Build](#hammer-build)  
  - [File Structure](#file_folder-file-structure)
  - [Classes](#bookmark-classes)  
- [Object-Oriented Programming Principles](#wrench-object-oriented-programming-principles)
  - [Encapsulation](#lock_with_ink_pen-encapsulation)
  - [Inheritance](#envelope_with_arrow-inheritance)
  - [Polymorphism](#card_index_dividers-polymorphism)
  - [Abstraction](#key-abstraction)  
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

  ###  :clipboard: Objectives
  This program was materialized as a means to fulfill the following objectives:
  - Promote quality treatment and services to the disadvantaged and vulnerable members of society
  - Provide protection against large, unprecedented risks and accidental losses
  - Ensure financial security and stability in the long run
  - Guaranteed recompense to listed beneficiaries in unforeseen events
  - Benefits coverage extends from post-hospitalization fees to palliative care compensation

  ### :seedling: Sustainable Development Goals (SDGs)
  In compliance with the United Nations' (UN) 2030 agenda for sustainable development, I built my program around the pursuits of the following goals:
  - <b>SDG 1: No Poverty</b> - description
  - <b>SDG 3: Good Health and Well-being</b> - description
  - <b>SDG 8: Decent Work and Economic Growth</b> - description
  - <b>SDG 9: Industry, Innovation, and Infrastructure</b> - description
  - <b>SDG 11: Sustainable Cities and Communities</b> - description

##  :hammer_and_wrench: Project Development
Add a detailed introduction about the project here, everything you want the reader to know.

  ###  :memo: Program Features
  In ARKA, the features are well thought of to endorse ease of navigation and general convenience for the agents who are expected to operate the program. 
  Listed below are the functionalities of the program:
  - Menu System
    - Display, add, remove, search, and update methods
  - User Management
    - Sign up, sign in, and log out methods
    - Unique and random ID generation for agents, clients, policies, and payments
  - Agent Management
  - Client Management
  - Policy Management
  - Payment Management
  - Input and Data Validation
  - Arithmetic Computations
  - Logical Computations
  - Database Management System (MySQL)
    - Database Connection
    - Data Storage
    - CRUD Operations
  - Formatted Console
  - Exception Handling

  ###  :hammer: Build
    If you want other people to contribute to this project, this is the section, make sure you always add this.
  
  ###  :file_folder: File Structure
  Add a file structure here with the basic details about files, below is an example.
  
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
  ###  :bookmark: Classes
  
  | No | Class Name | Purpose 
  |----|------------|-------|
  | 1  | Main | Entry point
  | 2  | ArkaDatabase | Contains the database connection and direct manipulation of tables
  | 3  | ArkaCustom | Separate class for console formatting
  | 4  | ArkaAgent | Includes the attributes of agent
  | 5  | ArkaAgentManager | Contains the methods for signing up and signing in
  | 6  | ArkaClient | Includes the attributes of client
  | 7  | ArkaClientManager | Composed of all the methods in correlation with client
  | 8  | ArkaPolicy | Abstract class containing the shared attributes of the policies
  | 9  | ArkaGintoPlan | Contains information about the Ginto Plan; a concrete subclass of ```ArkaPolicy```
  | 10  | ArkaPilakPlan | Contains information about the Pilak Plan; a concrete subclass of ```ArkaPolicy```
  | 11  | ArkaTansoPlan | Contains information about the Tanso Plan; a concrete subclass of ```ArkaPolicy```
  | 12  | ArkaManagerApp | Initial menu (sign up and sign in)
  | 13  | ArkaMenu | Agent menu (menu navigation for the main functionalities)
  | 14  | ArkaClientInput | Encompasses the inputs of the client's biodata
  | 15  | ArkaEligibility | Includes the calculation of client's eligibility score and financial validation
  | 16  | ArkaPolicyRecommender | Obtains data from ```ArkaEligibility``` to form policy recommendations
  | 17  | ArkaBeneficiary | Collects the beneficiary details of the client
  | 18  | ArkaStatement | Composes the proposal statement of the client
  | 19  | ArkaPayment | Processes the insurance payment of the client
  | 20  | ArkaUpdatePayment | Updates the client's insurance statement upon validating the next payment date
  
##  :wrench: Object-Oriented Programming Principles
Object-oriented programming is one of the programming paradigms that is distinguished by objects and classes. It implements principles such as 
**encapsulation, inheritance, polymorphism,** and **abstraction** among others to systematically arrange a collection of data into one central module known 
as a class.

  ### :lock_with_ink_pen: Encapsulation
  Encapsulation is one of the core principles in object-oriented programming as it the categorization of similar attributes into a class; it is essentially the
  foundation of OOP. Through access modifiers (```public, private,``` and ```protected```), the accessibility of certain fields within a class is explicitly defined.
  <br/><br/>
  In **```ArkaClient```**, the data members like ```clientID``` and ```fistName``` are set to ```protected``` so as to restrict its reachability to only methods within the
  the said class, the same package, or in the inheritance hierarchy. 

  ```
  public class ArkaClient {
    protected String clientID;
    protected String lastName;
    protected String firstName;
  ```

  However, there are some instances wherein these attributes must be called from a different package or class,  whereof it surpasses the set access modifier. In this regard, 
  getter and setter methods were used to allow controlled access to the protected fields in **```ArkaClient```**. 

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
  In OOP, inheritance refers to the authority given to a derived class to inherit the data members and methods from a base class or parent class. This principle allows for 
  **code reusability, effective polymorphism,** and **program organization** without tradeoffs in the quality of code composition.

  ```
  public abstract class ArkaPolicy {
  ```
  
  In this project, ```ArkaPolicy``` is the base class or the blueprint of all insurance policies. Whereas ```ArkaGintoPlan```, ```ArkaPilakPlan```, and ```ArkaTansoPlan``` 
  are the derived classes - each illustrating a distinct policy plan - that inherit from the parent class. 
  
  ```
  public class ArkaGintoPlan extends ArkaPolicy {
  ```

  **```ArkaPolicy```** encompasses attributes such as ```policyID```, ```premiumAmount```, and ```paymentPeriod```, and methods like ```getPlanName()```, ```getCoverageAmount()```,
  ```getPremiumAmount()```, and ```displayPlan()``` which are all inherited and referenced in its derived classes. In this manner, data redundancy is reduced and common behaviors are definitively 
  reused in the inheritance hierarchy. 
  <br/><br/>
  Moreover, certain methods (e.g., ```displayPlan()```) can perform distinctively in accordance with its method definition in the derived classes – which is, in essence, another object-oriented 
  programming principle in play called polymorphism.
  
  ###  :card_index_dividers: Polymorphism
  Write about setting up the working environment for your project.
  - How to download the project...
  - How to install dependencies...
  
  ###  :key: Abstraction
  Write about setting up the working environment for your project.
  - How to download the project...
  - How to install dependencies...
  
  ```
  public abstract class ArkaPolicy {
  ```

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
