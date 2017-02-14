CREATE TABLE IDENTITIES
    (IDENTITY_ID INT NOT NULL GENERATED ALWAYS AS IDENTITY CONSTRAINT IDENTITY_PK PRIMARY KEY, 
    IDENTITY_DISPLAYNAME VARCHAR(255),
    IDENTITY_EMAIL VARCHAR(255),
    IDENTITY_BIRTHDATE DATE
    );
