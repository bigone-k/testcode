DROP TABLE IF EXISTS User;

CREATE TABLE User (
    userNo    BIGINT PRIMARY KEY AUTO_INCREMENT
    ,userId   VARCHAR(100)
    ,password VARCHAR(50)
    ,name     VARCHAR(100)
    ,provider VARCHAR(100)
);