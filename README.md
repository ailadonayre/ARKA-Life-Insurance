# ARKA: Arangkada Life Insurance
<b>ARKA: Arangkada Life Insurance</b> is the lifetime safety net of middle to low-income Filipino families with insurance policies of varying 
benefits marked at a fair price.

## :ledger: Index

- [About](#beginner-about)
  - [Objectives](#clipboard-objectives)
  - [Sustainable Development Goals (SDGs)](#seedling-sustainable-development-goals-sdgs)
- [Development](#hammer_and_wrench-development)
  - [Features](#memo-features)
  - [File Structure](#file_folder-file-structure)
  - [Classes](#bookmark-classes)  
  - [Build](#hammer-build)  
- [Object-Oriented Programming (OOP) Principles](#wrench-object-oriented-programming-oop-principles)
  - [Encapsulation](#lock_with_ink_pen-encapsulation)
  - [Inheritance](#envelope_with_arrow-inheritance)
  - [Polymorphism](#card_index_dividers-polymorphism)
  - [Abstraction](#key-abstraction)  
- [About the Developer](#cherry_blossom-about-the-developer)
- [Acknowledgment](#star2-acknowledgment)

##  :beginner: About
<b>ARKA Life Insurance</b>, from the Filipino word *arangkada* - meaning "to accelerate" - champions the progression and wellbeing of the general 
Filipino populace, with emphasis to the more marginalized sectors of the country. Low to lower-middle class families, minimum wage earners, and even 
students, are the prime target demographic of ARKA's insurance policies. 
<br/>
This program is to be used specifically by insurance agents under ARKA as they record their client's personal information and financial standing. 
As it is imperative for clients to maintain the regularity of their payments, ARKA has also devised a criteria to ascertain the eligibility of individuals 
applying for an insurance plan by means of assessing their financial constraints. The obtained information then displays the most optimum policy 
recommendations aligned with the client's needs and capacity.

  ###  :clipboard: Objectives
  - Promote quality treatment and services to the disadvantaged and vulnerable members of society
  - Provide protection against large, unprecedented risks and accidental losses
  - Ensure financial security and stability in the long run
  - Guaranteed recompense to listed beneficiaries in unforeseen events
  - Benefits coverage extends from property damages to post-hospitalization compensation

  ### :seedling: Sustainable Development Goals (SDGs)
  - <b>SDG 1: No Poverty</b> - description
  - <b>SDG 3: Good Health and Well-being</b> - description
  - <b>SDG 8: Decent Work and Economic Growth</b> - description
  - <b>SDG 9: Industry, Innovation, and Infrastructure</b> - description
  - <b>SDG 11: Sustainable Cities and Communities</b> - description

##  :hammer_and_wrench: Development
Add a detailed introduction about the project here, everything you want the reader to know.

  ###  :memo: Features
  If you want other people to contribute to this project, this is the section, make sure you always add this.
  - User Management
    - Sign up, sign in, and log out methods
    - Unique and random ID generation for agents, clients, policies, and payments
  - Menu System
    - Display, add, search, and update methods
  - Agent Management
  - Client Management
  - Policy Management
  - Payment Management
  - Input and Data Validation
  - Arithmetic Computations
  - Database Management System (MySQL)
    - Database Connection
    - Data Storage
    - CRUD Operations
  - Formatted Console
  - Exception Handling
  
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
  
  ###  :hammer: Build
  If you want other people to contribute to this project, this is the section, make sure you always add this.

##  :wrench: Object-Oriented Programming (OOP) Principles
If you want other people to contribute to this project, this is the section, make sure you always add this.

  ### :lock_with_ink_pen: Encapsulation
  List all the pre-requisites the system needs to develop this project.
  - A tool
  - B tool
  
  ###  :envelope_with_arrow: Inheritance
  Write about setting up the working environment for your project.
  - How to download the project...
  - How to install dependencies...
  
  ```
  public class ArkaGintoPlan extends ArkaPolicy {
  ```
  
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
If it's open-source, talk about the community here, ask social media links and other links.

## :star2: Acknowledgment
To <b>Miss Fatima Marie Agdon</b>, this project would not have proven its success without your transformative pedagogical methods and invaluable 
share of knowledge in Java programming and the principles of object-oriented programming. The accomplishment of this program is not only a fruit 
of all the hard work poured into its ideation and materialization, but also a testament of your excellent mentorship this semester. Thank you for 
your support and guidance. 