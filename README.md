# Project 0: Banking Application

## Due: Wednesday May 4, 2022

## Project Description:

Leveraging Java 8, create a Restful API for the RevatureBank. You get to choose the exact functionality of the banking API, however it must comply with all of the functional requirements below.

## Requirements:

Technologies that MUST be used:

-   Java 8
-   Javalin to handle HTTP requests
-   JDBC for data connectivity
-   JUnit for unit testing
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

# Evaluation:

The project will be evaluated out of 100 points split between two main catagories: 75 points for the functionality and design of your project and 30 points for the presentation of your project during the project showcase. The evaluation will be further subdivided as follows:

**Project Score(75 points):**

-   15 points: Ability to persist User information/objects in the database then retrieve it when attempting to login
-   10 points: Ability to persist Account information/objects in the database then retreive the information when needed
-   15 points: Ability to update Account information in the database, and persist corresponding transactions which can be retrieved when needed
-   10 points: Proper database schema achieving 3rd normal form
-   10 points: Followed proper coding conventions, and a layered architecture
-   5 points: Proper use of DAO design pattern
-   5 points: Different user roles with different levels of access
-   5 points: 70% test coverage on the service layer

**Presentation Score(25 points):**

-   15 points: Clear, concise, logical, and professional communication during presentation
-   10 points: Ability to communicate clear answers to fully address questions asked about the project

# Presentations

Presentations will be held on Wednesday May 4, 2022

You will have EXACTLY 5 minutes to present all of your features

Make sure to take advantage of Postman's project functionality and organize your http methods

Make sure you have everything up and running so we are not wasting time before hand

Be prepared to answer questions at the end

We are hoping to spend less than 10 total minutes per project

## Presentation Dos and Don'ts

Do: Set a cut off date/code freeze so you don't break some functionalities that work during last minute coding sessions

Do: Practice the presentation several times before the due date

-   You only have 5 minutes, time will fly
-   This will help you find bugs, get you more comfortable, and know much more quickly you need to go

Do: Try your best, present what you do have on presentation day

Do: Be prepared to answer questions, show implementations, or your database diagram

Do: Play it cool, if things are not going your way, be sure to stay cool and calm, and just go with it

Don't: Show your code or your database before you present the project, we will ask if we want to see anything

Don't: Be unprofessional

-   Make sure you are wearing proper attire for the presentation
-   Make sure you are using concise technical language, NO SWEARING

# Basic Roadmap:

How I would go about developing this project. Keep in mind, this is only a suggestion, and you are at full discretion of how to implement the project

## Phase 1: Goals to hit by Monday April 25

Create the Model classes for the application (User, Account, Transaction)

Create Service classes that modify the model objects

Test that your services are working correctly (with JUnit)

## Phase 2: Goals to hit by Friday April 29

Create a new database

Create tables in the database that model your Java Model classes

Create the DAO classes for each of your models

-   Bare minimum of Create, Read, Update, and Delete for each of the models

Use the DAO in the service classes to persist the objects being created/modified in the service classes

Setup your Services to use your DAO methods

Update your tests to utilize Mockito so your tests do not call the database

## Phase 3: Goals to hit for the Project Presentation on Wednesday May 4

Set up the Javalin application

Create routes for each of the Services

Implement the handlers for each of these services

-   The handlers will call the Service methods

Test with Postman as you go
