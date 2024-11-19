# ARKA: Arangkada Life Insurance
<b>ARKA: Arangkada Life Insurance</b> is developed to grant middle to low-income Filipino families a lifetime safety net with insurance policies of varying benefits marked at a fair price.

## :ledger: Index

- [About](#beginner-about)
- [Usage](#zap-usage)
  - [Installation](#electric_plug-installation)
  - [Commands](#package-commands)
- [Development](#wrench-development)
  - [Pre-Requisites](#notebook-pre-requisites)
  - [Developmen Environment](#nut_and_bolt-development-environment)
  - [File Structure](#file_folder-file-structure)
  - [Build](#hammer-build)  
  - [Deployment](#rocket-deployment)  
- [Community](#cherry_blossom-community)
  - [Contribution](#fire-contribution)
  - [Branches](#cactus-branches)
  - [Guideline](#exclamation-guideline)  
- [FAQ](#question-faq)
- [Resources](#page_facing_up-resources)
- [Gallery](#camera-gallery)
- [Credit/Acknowledgment](#star2-creditacknowledgment)
- [License](#lock-license)

##  :beginner: About
Add a detailed introduction about the project here, everything you want the reader to know.

  ###  :clipboard: Objectives
  Add a detailed introduction about the project here, everything you want the reader to know.
  
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
  | 2  | ArkaDatabase | Entry point
  | 3  | ArkaCustom | Entry point
  | 4  | ArkaAgent | Entry point
  | 5  | ArkaAgentManager | Entry point
  | 6  | ArkaClient | Entry point
  | 7  | ArkaClientManager | Entry point
  | 8  | ArkaPolicy | Entry point
  | 9  | ArkaGintoPlan | Entry point
  | 10  | ArkaPilakPlan | Entry point
  | 11  | ArkaTansoPlan | Entry point
  | 12  | ArkaManagerApp | Contains the sign up, sign in, and log out menu
  | 13  | ArkaMenu | Entry point
  | 14  | ArkaClientInput | Entry point
  | 15  | ArkaEligibility | Entry point
  | 16  | ArkaPolicyRecommender | Entry point
  | 17  | ArkaBeneficiary | Entry point
  | 18  | ArkaStatement | Entry point
  | 19  | ArkaPayment | Entry point
  | 20  | ArkaUpdatePayment | Entry point
  
  ###  :hammer: Build Instructions
  If you want other people to contribute to this project, this is the section, make sure you always add this.

##  :wrench: Object-Oriented Programming Principles
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
Credit the authors here.
