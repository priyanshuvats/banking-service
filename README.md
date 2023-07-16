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

## Transaction Rules
- Withdrawal rules:
    - zero_balance account allows only 4 withdrawals in a month, further withdrawals are blocked
    - student account allows only 4 withdrawals in a month, charge of 10 per transaction 
    - student account needs min balance of 1000 rupees , 
    - regular_saving account allows only 10 withdrawals in a month further withdrawals are charged at 5 rupees per withdrawal
- Deposit rules:
    - Depositing more than 10,000 rupees in single transaction on all accounts is not allowed. there is no restriction on total balance.
    - In month if you  can deposit more than 50k, if  kyc flag true else reject deposit


## Setup
- please ensure you have docker installed on your machine.
- navigate to the project folder
- build and run project 
```
docker-compose up --build 
```
- please wait as it might take a couple of minutes for containers to be up
- play around with swagger UI: http://localhost:8000/swagger-ui.html

## Architecture
- [HLD] ![image](https://github.com/priyanshuvats/banking-service/assets/64020180/5fabc4e8-2c86-4ba3-adcc-2464d0124cbc)


- [LLD] ![image](https://github.com/priyanshuvats/banking-service/assets/64020180/efc27cce-3603-4ed7-83c2-cad3280d033b)


