SET search_path TO day05;

SET TIME ZONE 'Europe/Moscow';

INSERT INTO users (login, password)
    VALUES ('RapidGoose', 'qwerty'),
           ('FalseFarmer', '123'),
           ('AdorableRice', 'Bmux6z'),
           ('ChivalrousPlant', '4hSANAnGSu'),
           ('PoliticalGhost', 'gfhjkm');

INSERT INTO chatrooms (name, owner_id)
    VALUES ('General', 1),
           ('Off Topic', 1),
           ('Movies', 1),
           ('Books', 4),
           ('News', 2);

INSERT INTO messages (author_id, room_id, text)
    VALUES (1, 1, 'Welcome to General'),
           (1, 2, 'Please be civil'),
           (2, 1, 'Yay! New friends'),
           (4, 4, 'Let''s talk about latest book by author Bob'),
           (3, 2, 'BOOOOOOOOO!!!!!!');

INSERT INTO users_chatrooms
    VALUES (1, 1), (1, 2),
           (2, 1), (2, 2), (2, 3), (2, 4), (2, 5),
           (3, 1), (3, 2),
           (4, 4),
           (5, 5);