# banking-service

## About
- This is a banking service to perform common banking functions
    - create account for a user
    - get account details for an account number
    - update kyc details for an account
    - perform deposit and withdrawal transactions for an account
    - get list of transactions for an account

- This is a demo project with some users and transaction rules pre-configured in DB
    - you can create account for user with id betweeen 1 and 5 only
    - there are rules subject to account type for withdrawl and deposit due to which the transaction may get failed or get charged
    - these rules are configured in the database at the starting of application.

## PRD
- https://docs.google.com/document/d/1wokIOGg4QM-cWD8S4Tt_PmEJwTGCiU9xEvFEAFownnU/edit?usp=sharing

## Setup
- please ensure you have docker installed on your machine.
- navigate to the project folder
- build project 
```
./gradlew clean build 
```
- run project 
```
docker-compose up --build 
```
- please wait as it might take a couple of minutes for containers to be up
- play around with swagger UI: http://localhost:8000/swagger-ui.html

## Architecture
- [HLD] ![image](https://github.com/priyanshuvats/banking-service/assets/64020180/5fabc4e8-2c86-4ba3-adcc-2464d0124cbc)


- [LLD] ![image](https://github.com/priyanshuvats/banking-service/assets/64020180/efc27cce-3603-4ed7-83c2-cad3280d033b)


