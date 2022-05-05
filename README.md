# Banking Application

## Project Description:

Leveraging Java 8, I created a Restful API for a bank.

## Requirements:

Technologies used:

-   Java 8
-   Javalin to handle HTTP requests
-   JDBC for data connectivity
-   Log4j for logging
-   PostgreSQL for data persistence
-   Postman to display the functionality of your end product
-   Gradle for dependency management in Java

Functional Requirements / Minimum Viable Product:

-   Ability to create users with different functionalities

    -   Creating a user should result in said user being persisted to the database
    -   Example of users with different functionalities:
        -   User: Ability to signup for bank accounts, and add, subtract, and transfer money between accounts, etc...
        -   Manager: Ability to view all banks accounts, approve/deny accounts, close out accounts, etc...

-   Ability for all users to login with their username and password

    -   Be sure to only allow the correct functionalities depending on what user type is logged in
    -   Example: Regular user cannot access functionalities of a mananger

-   The API should include banking operations for the Users

    -   Example of banking operations:
        -   Deposit: adding money to specified bank accounts
        -   Withdraw: removing money from specified bank accounts
        -   Transfer: send money bewtween two accounts

-   When a banking operation occurs, the resulting state of the bank account should be persisted in the database

-   Implement some validation that the banking operations are legal, prevent those of which would be considered against the rules

    -   Examples:
        -   Trying to deposit negative money
        -   Trying to withdraw more money than what is held in the account
        -   etc...

-   Transactions that occur on and between bank accounts should be persisted in the database

Design Requirements:

-   Postgres tables should be in 3rd normal form
    -   Should contain at least three entity tables
        -   At least one for User, BankAccount, and Transaction
    -   Tables should have correct relationships between them
-   The application should follow the layered architecture that has been show in training
    -   This includes:
        -   Dao layer
        -   Models layer
        -   Service layer
-   The application should have at least 70% test coverage of the service layer
-   The user interactions with the application will be simulated via HTTP messaging through Postman
