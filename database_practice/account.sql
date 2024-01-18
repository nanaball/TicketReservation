DROP TABLE IF EXISTS tbl_account;

-- 
CREATE TABLE IF NOT EXISTS tbl_account(
	ano VARCHAR(30) NOT NULL UNIQUE,
    owner VARCHAR(20) NOT NULL,
    balance INT default 0,
    password VARCHAR(50) NOT NULL 
);

INSERT INTO tbl_account
VALUES('111-111','최기근',20000000,'12345');

SELECT * FROM tbl_account;
commit;
























