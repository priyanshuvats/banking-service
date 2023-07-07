SET GLOBAL TRANSACTION ISOLATION LEVEL SERIALIZABLE;

INSERT INTO test.user (name, email) VALUES
("Priyanshu", "priyanshu@gmail.com"),
("Kanishk", "kanishk@gmail.com"),
("Yash", "yash@gmail.com"),
("Deepika", "deepika@gmail.com"),
("Ridhi", "ridhi@gmail.com");

INSERT INTO test.transaction_restriction (transaction_type, account_type, type, value, actions)
VALUES
("WITHDRAWAL", "ZERO_BALANCE", "MONTHLY_WITHDRAWAL_COUNT", 4, '{"actionType":"BLOCK"}'),
("WITHDRAWAL", "STUDENT", "MONTHLY_WITHDRAWAL_COUNT", 4, '{"actionType":"ALLOW", "charge":10}'),
("WITHDRAWAL", "REGULAR_SAVINGS", "MONTHLY_WITHDRAWAL_COUNT", 10, '{"actionType":"ALLOW", "charge":5}'),
("WITHDRAWAL", "STUDENT", "MINIMUM_BALANCE", 1000, '{"actionType":"BLOCK"}'),
("DEPOSIT", "ZERO_BALANCE", "SINGLE_DEPOSIT_AMOUNT", 10000, '{"actionType":"BLOCK"}'),
("DEPOSIT", "STUDENT", "SINGLE_DEPOSIT_AMOUNT", 10000, '{"actionType":"BLOCK"}'),
("DEPOSIT", "REGULAR_SAVINGS", "SINGLE_DEPOSIT_AMOUNT", 10000, '{"actionType":"BLOCK"}'),
("DEPOSIT", "ZERO_BALANCE", "NO_KYC_MONTHLY_DEPOSIT_SUM", 50000, '{"actionType":"BLOCK"}'),
("DEPOSIT", "STUDENT", "NO_KYC_MONTHLY_DEPOSIT_SUM", 50000, '{"actionType":"BLOCK"}'),
("DEPOSIT", "REGULAR_SAVINGS", "NO_KYC_MONTHLY_DEPOSIT_SUM", 50000, '{"actionType":"BLOCK"}');

