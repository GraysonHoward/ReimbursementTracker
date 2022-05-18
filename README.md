# Reimbursement Tracker

## Project Description

Create a REST API for an expense reimbursement system. The system allows a company to track expenses and analyze spending. The core functionality revolves around two entities: Employee and Expense.

## Timeline of deliverables
- TUE 4/19
  - Skeleton code in GitHub repository
  - Interfaces and ERD approved by me
- MON 4/25
  - Employee functionality complete
  - Employee code functiionality on GitHub
- MON 5/2
  - App runs locally
- WED 5/4
  - ***DUE***
  - Will be presenting to the batch

## Requirments
- All expenses have a single employee as the issuer
- Expenses start as pending and must then be approved or denied
  - Once approved or denied they CANNOT be deleted or edited
- Negative expenses are not allowed
- JUnit tests for all DAO methods
- Postman tests for each endpoint
- Deployed on Elastic Bean for your presentation
- The database should be a PostgreSQL on an AWS RDS

## Technologies Used

* Java - SE 8
* Spring Boot - version 2.6.7
* AWS RDS
