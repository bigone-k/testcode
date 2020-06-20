INSERT INTO User (userId, password, name, provider)
VALUES ('bigone', '{bcrypt}$2a$10$sZuNHYyGq0AAzcc4F/dEwOX07ZahrxND3qdPVMKGEoJ/fCtFykVea', 'bigone', null);

INSERT INTO ProcessStepType (stepType, stepDesc)
VALUES (1, '신분증인증'),
       (2, '이체인증'),
       (3, '인증단어인증');

INSERT INTO StateCode (stateCode, stateDesc)
VALUES (1, 'wait'),
       (2, 'success'),
       (3, 'timeout'),
       (4, 'failure');

INSERT INTO ProcessStep(userNo, stepType, stateCode)
VALUES (1, 1, 2),
       (1, 1, 2),
       (1, 1, 2),
       (1, 1, 2),
       (1, 1, 2),
       (1, 1, 2),
       (1, 1, 2),
       (1, 1, 2),
       (1, 1, 4),
       (1, 1, 4),
       (1, 1, 4),
       (1, 1, 4);

INSERT INTO ProcessStep(userNo, stepType, stateCode)
VALUES (1, 2, 2),
       (1, 2, 2),
       (1, 2, 2),
       (1, 2, 2),
       (1, 2, 2),
       (1, 2, 2),
       (1, 2, 2),
       (1, 2, 2),
       (1, 2, 4),
       (1, 2, 4),
       (1, 2, 4),
       (1, 2, 4);

INSERT INTO ProcessStep(userNo, stepType, stateCode)
VALUES (1, 3, 2),
       (1, 3, 2),
       (1, 3, 2),
       (1, 3, 2),
       (1, 3, 2),
       (1, 3, 2),
       (1, 3, 2),
       (1, 3, 2),
       (1, 3, 4),
       (1, 3, 4),
       (1, 3, 4),
       (1, 3, 4);

INSERT INTO ProcessStepHistory(mstSeqNo, stepType, stateCode)
VALUES (1, 1, 2),
       (2, 1, 2),
       (3, 1, 2),
       (4, 1, 2),
       (5, 1, 2),
       (6, 1, 2),
       (7, 1, 2),
       (8, 1, 2),
       (9, 1, 4),
       (10, 1, 4),
       (11, 1, 4),
       (12, 1, 4);

INSERT INTO ProcessStepHistory(mstSeqNo, stepType, stateCode)
VALUES (13, 2, 2),
       (14, 2, 2),
       (15, 2, 2),
       (16, 2, 2),
       (17, 2, 2),
       (18, 2, 2),
       (19, 2, 2),
       (20, 2, 2),
       (21, 2, 4),
       (22, 2, 4),
       (23, 2, 4),
       (24, 2, 4);

INSERT INTO ProcessStepHistory(mstSeqNo, stepType, stateCode)
VALUES (25, 3, 2),
       (26, 3, 2),
       (27, 3, 2),
       (28, 3, 2),
       (29, 3, 2),
       (30, 3, 2),
       (31, 3, 2),
       (32, 3, 2),
       (33, 3, 4),
       (34, 3, 4),
       (35, 3, 4),
       (36, 3, 4);

INSERT INTO PREAGREE ( USERNO, AGREE, ACCOUNTPASSWORD )
VALUES (1, 1, '5212');
