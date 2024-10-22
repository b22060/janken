INSERT INTO users (userName) VALUES ('CPU');
INSERT INTO users (userName) VALUES ('ほんだ');
INSERT INTO users (userName) VALUES ('いがき');
INSERT INTO matches (user1, user2, user1Hand, user2Hand,result,isActive) VALUES (2, 1, 'Gu', 'Choki', '勝ち','FALSE');
INSERT INTO matches (user1, user2, user1Hand, user2Hand,result,isActive) VALUES (2, 1, 'Gu', 'Gu', 'あいこ','FALSE');
INSERT INTO matches (user1, user2, user1Hand, user2Hand,result,isActive) VALUES (2, 1, 'Gu', 'Pa', '負け','FALSE');
INSERT INTO matchinfo (user1, user2, user1Hand, isActive) VALUES (1, 2, 'Gu', 'FALSE');
INSERT INTO matchinfo (user1, user2, user1Hand, isActive) VALUES (2, 3, 'Gu', 'FALSE');
