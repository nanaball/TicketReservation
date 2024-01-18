CREATE TABLE tbl_member(
	mNum INT PRIMARY KEY auto_increment,
	mName VARCHAR(50),
	mId VARCHAR(20) NOT NULL UNIQUE,
	mPw VARCHAR(30) NOT NULL,
	reg BIGINT DEFAULT 0
);

INSERT INTO tbl_member(mName,mId,mPw)
VALUES('관리자','root','root');

SELECT * FROM tbl_member;

commit;

-- 탈퇴한 회원의 정보를 임시 저장할 back_up_table 생성
CREATE TABLE back_up_member LIKE tbl_member;

DESCRIBE back_up_member;


-- 회원 탈퇴 시간에 대한 정보 추가 
ALTER TABLE back_up_member
ADD COLUMN deleteDate TIMESTAMP DEFAULT now();

DESC back_up_member;


/*
 == 로그인 == 
사용자 정보를 입력해주세요.
아이디를 입력해주세요 > 
id001
비밀번호를 입력해 주세요 > 
pw001
 */
SELECT * FROM tbl_member WHERE mId= '2' AND mPw = '2';

-- mId : 2' -- 
-- mPw : sldjfhlskajdfhskdf
SELECT * FROM tbl_member WHERE mId= '2' -- ' AND mPw = 'sldjfhlskajdfhskdf';

/*
	==================================
	SQL Injection
	사용자가 입력할 수 있는 영역을 활용해서 개발자가 의도하지 않은 SQL문을 삽입하여 공격하는 방법
	(아이디만 알면 비번이 다르거나 몰라도 로그인 가능)
 */

-- PREPARE EXECUTE
PREPARE 
 mQuery
FROM 'SELECT * FROM tbl_member WHERE mId = ? AND mPw = ?';

-- 개발자 @ 시스템 @@
SET @mId = 'root' -- 
SET @password = '123456123456';

EXECUTE mQuery USING @mId, @password;