/************************* 시퀀스 *************************/
DROP SEQUENCE USER_SEQ;
DROP SEQUENCE ACCESS_HISTORY_SEQ;
DROP SEQUENCE LEAVE_USER_SEQ;
DROP SEQUENCE BBS_SEQ;
DROP SEQUENCE BLOG_SEQ;
DROP SEQUENCE COMMENT_SEQ;

CREATE SEQUENCE USER_SEQ NOCACHE;
CREATE SEQUENCE ACCESS_HISTORY_SEQ NOCACHE;
CREATE SEQUENCE LEAVE_USER_SEQ NOCACHE;
CREATE SEQUENCE BBS_SEQ NOCACHE;
CREATE SEQUENCE BLOG_SEQ NOCACHE;
CREATE SEQUENCE COMMENT_SEQ NOCACHE;

/************************* 테이블 *************************/
DROP TABLE COMMENT_T;
DROP TABLE BLOG_T;
DROP TABLE BBS_T;
DROP TABLE LEAVE_USER_T;
DROP TABLE ACCESS_HISTORY_T;
DROP TABLE USER_T;

-- 회원
CREATE TABLE USER_T (
  /* 회원번호(PK) */               USER_NO      NUMBER             NOT NULL,
  /* 이메일(인증수단) */           EMAIL        VARCHAR2(100 BYTE) NOT NULL UNIQUE,
  /* 암호화(SHA-256) */            PW           VARCHAR2(64 BYTE),
  /* 이름 */                       NAME         VARCHAR2(100 BYTE),
  /* 성별 */                       GENDER       VARCHAR2(5 BYTE),
  /* 휴대전화 */                   MOBILE       VARCHAR2(20 BYTE),
  /* 이벤트동의여부(0:미동의,1:동의) */        EVENT_AGREE  NUMBER,
  /* 가입형태(0:직접,1:네이버) */  SIGNUP_KIND  NUMBER,
  /* 비밀번호수정일 */             PW_MODIFY_DT DATE,
  /* 가입일 */                     SIGNUP_DT    DATE,
  CONSTRAINT PK_USER PRIMARY KEY(USER_NO)
);

/* 로그인 히스토리 */
CREATE TABLE ACCESS_HISTORY_T (
  ACCESS_HISTORY_NO NUMBER              NOT NULL,
  EMAIL             VARCHAR2(100 BYTE),
  IP                VARCHAR2(50 BYTE),
  USER_AGENT        VARCHAR2(150 BYTE),
  SESSION_ID        VARCHAR2(32 BYTE),
  SIGNIN_DT         DATE,
  SIGNOUT_DT        DATE,
  CONSTRAINT PK_ACCESS_HISTORY PRIMARY KEY(ACCESS_HISTORY_NO),
  CONSTRAINT FK_ACCESS_HISTORY_USER
    FOREIGN KEY(EMAIL) 
      REFERENCES USER_T(EMAIL)
        ON DELETE CASCADE
);

/* USER_T와 관계를 갖지 않는 테이블이기 때문에 생성/삭제의 순서가 없다. */
CREATE TABLE LEAVE_USER_T (
  LEAVE_USER_NO  NUMBER             NOT NULL,
  EMAIL          VARCHAR2(100 BYTE) NOT NULL UNIQUE,
  LEAVE_DT       DATE,
  CONSTRAINT PK_LEAVE_USER PRIMARY KEY(LEAVE_USER_NO)
);

-- 계층형 게시판 (답글과 원문을 하나에 테이블로) (N차 댓글)
CREATE TABLE BBS_T(
  BBS_NO      NUMBER              NOT NULL,
  CONTENTS    VARCHAR2(4000 BYTE) NOT NULL,
  USER_NO     NUMBER              NOT NULL,
  CREATE_DT   DATE                NULL,
  STATE       NUMBER              NULL,     -- 0:삭제, 1:정상
  DEPTH       NUMBER              NULL,     -- 0:원글, 1:답글, 2:답답글,  ...
  GROUP_NO   NUMBER              NULL,     -- 원글과 원글에 달린 모든 댓글들은 동일한 GROUP_NO를 가짐
  GROUP_ORDER NUMBER              NULL,     -- 같은 GROUP_NO 내부에서 표시할 순서
  CONSTRAINT PK_BBS PRIMARY KEY(BBS_NO),
  CONSTRAINT FK_BBS_USER 
    FOREIGN KEY(USER_NO)
      REFERENCES USER_T(USER_NO) ON DELETE CASCADE
);

-- 댓글형 게시판

CREATE TABLE BLOG_T(
  BLOG_NO   NUMBER               NOT NULL,
  TITLE     VARCHAR2(1000 BYTE)  NOT NULL,
  CONTENTS  VARCHAR2(4000 BYTE),
  HIT       NUMBER               DEFAULT 0,
  USER_NO   NUMBER               NOT NULL,
  CREATE_DT DATE,
  MODIFY_DT DATE,
  CONSTRAINT PK_BLOG PRIMARY KEY(BLOG_NO),
  CONSTRAINT FK_BLOG_USER
    FOREIGN KEY(USER_NO)
      REFERENCES USER_T(USER_NO)
        ON DELETE CASCADE
);

-- 블로그 댓글(댓글 게시판, 1차 답글만 가능)
-- 외래키는 운영할 때 NULL 이 좋다 (나중에 넣을 수 있기 때문에)
CREATE TABLE COMMENT_T (
  COMMENT_NO NUMBER                 NOT NULL,  -- 번호
  CONTENTS   VARCHAR2(4000 BYTE)    NOT NULL,  -- 내용 
  CREATE_DT  DATE                   NULL,      -- 작성일자
  DEPTH      NUMBER                 NULL,      -- 원글0, 답글1
  GROUP_NO   NUMBER                 NULL,      -- 원글에 달린 모든 답글은 동일한 GROUP_NO 를 가진다.
  USER_NO    NUMBER                 NULL,      -- 작성자 정보
  BLOG_NO    NUMBER                 NULL,      -- 블로그 외래키
  
  CONSTRAINT PK_COMMENT PRIMARY KEY(COMMENT_NO),
  CONSTRAINT FK_COMMENT_USER FOREIGN KEY(USER_NO) 
    REFERENCES USER_T(USER_NO) ON DELETE CASCADE,
  CONSTRAINT FK_COMMENT_BLOG FOREIGN KEY(BLOG_NO) 
    REFERENCES BLOG_T(BLOG_NO) ON DELETE CASCADE
);

-- 좋아요 테이블(누가 어디에 좋아요 표시를 했음)


-- 기초 데이터 (사용자)
INSERT INTO USER_T VALUES(USER_SEQ.NEXTVAL, 'admin@example.com', STANDARD_HASH('admin', 'SHA256'), '관리자', 'man', '010-1111-1111', 1, 0, CURRENT_DATE, CURRENT_DATE);
COMMIT;



/************************* 트리거 *************************/
/*
  1. DML (INSERT, UPDATE, DELETE) 작업 이후 자동으로 실행되는 데이터베이스 객체이다.
  2. 행 (ROW) 단위로 동작한다.
  3. 종류
    1) BEFORE : DML 동작 이전에 실행되는 트리거
    2) AFTER  : DML 동작 이후에 실행되는 트리거
  4. 형식
    CREATE [OR REPLACE] TRIGGER 트리거명
    BEFORE | AFTER
    INSERT OR UPDATE OR DELETE
    ON 테이블명
    FOR EACH ROW
    BEGIN
      트리거본문
    END;
*/

/* 
  USER_T 테이블에서 삭제된 회원정보를 LEAVE_USER_T 테이블에 자동으로 삽입하는
  LEAVE_TRIGGER 트리거 생성하기
  DROP TRIGGER는 필요 없다
*/
CREATE OR REPLACE TRIGGER LEAVE_TRIGGER
  AFTER
  DELETE
  ON USER_T
  FOR EACH ROW
BEGIN
  INSERT INTO LEAVE_USER_T (
      LEAVE_USER_NO
    , EMAIL
    , LEAVE_DT
  ) VALUES (
      LEAVE_USER_SEQ.NEXTVAL
    , :OLD.EMAIL
    , CURRENT_DATE
  );
  -- COMMIT; 트리거 내에서는 오류가 있으면 ROLLBACK, 없으면 COMMIT 한다.
END;

