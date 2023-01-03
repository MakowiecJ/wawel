DROP TABLE IF EXISTS roles;
CREATE TABLE roles
(
    id   INTEGER     NOT NULL AUTO_INCREMENT,
    name VARCHAR(60) NOT NULL,
    PRIMARY KEY (id)
);

DROP TABLE IF EXISTS users;
CREATE TABLE users
(
    id       BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(60) NOT NULL,
    password VARCHAR(60) NOT NULL,
    email    VARCHAR(60) NOT NULL,
    PRIMARY KEY (id)
);

DROP TABLE IF EXISTS user_roles;
CREATE TABLE user_roles
(
    user_id INTEGER NOT NULL AUTO_INCREMENT,
    role_id INTEGER NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (role_id) REFERENCES roles (id)
);

DROP TABLE IF EXISTS cinema;
CREATE TABLE cinema
(
    id           BIGINT PRIMARY KEY AUTO_INCREMENT,
    city         VARCHAR(255) NOT NULL,
    address      VARCHAR(255) NOT NULL,
    phone_number INT          NOT NULL
);

INSERT INTO cinema (city, address, phone_number)
VALUES ('KRAKOW', 'ul. Karmelicka 45', 123321643),
       ('KATOWICE', 'ul. Korfantego 123', 368982615),
       ('OPOLE', 'ul. Oleska 45', 118827625),
       ('WROCLAW', 'ul. Oławska 89', 898142765),
       ('LUBAN', 'ul. Słowackiego 67', 121224987);

DROP TABLE IF EXISTS repertoire;
CREATE TABLE repertoire
(
    id         BIGINT PRIMARY KEY AUTO_INCREMENT,
    cinema_id  BIGINT NOT NULL,
    start_date DATE   NOT NULL,
    end_date   DATE   NOT NULL,
    FOREIGN KEY (cinema_id) REFERENCES cinema (id)
);
INSERT INTO repertoire (cinema_id, start_date, end_date)
VALUES (1, '2023-01-01', '2023-01-31'),
       (2, '2023-01-01', '2023-01-31'),
       (3, '2023-01-01', '2023-01-31'),
       (4, '2023-01-01', '2023-01-31'),
       (5, '2023-01-01', '2023-01-31');

DROP TABLE IF EXISTS movies;
CREATE TABLE movies
(
    id          BIGINT PRIMARY KEY AUTO_INCREMENT,
    title       VARCHAR(60)   NOT NULL,
    genre       VARCHAR(60)   NOT NULL,
    min_age     INTEGER       NOT NULL,
    duration    INTEGER       NOT NULL,
    description VARCHAR(1000) NOT NULL,
    PRIMARY KEY (id)
);

INSERT INTO roles (name)
VALUES ('role_admin');
INSERT INTO roles (name)
VALUES ('role_user');

INSERT INTO users (username, password, email)
VALUES ('admin', '$2a$10$uuXzvyevIXLJWmkA7WC2e.xM8xZZHJT0v3qdmcolz2Y3G.p2oEGjW', 'admin@gmail.com'),
       ('user', '$2a$10$/XapBAlZcXurPdFgjlKkIOwCAr0WgTt5C09hq6xcQ/X.4GsWpfUQ.', 'user@gmail.com'),
       ('user2', '$2a$10$/XapBAlZcXurPdFgjlKkIOwCAr0WgTt5C09hq6xcQ/X.4GsWpfUQ.', 'user2@gmail.com'),
       ('user3', '$2a$10$/XapBAlZcXurPdFgjlKkIOwCAr0WgTt5C09hq6xcQ/X.4GsWpfUQ.', 'user3@gmail.com'),
       ('user4', '$2a$10$/XapBAlZcXurPdFgjlKkIOwCAr0WgTt5C09hq6xcQ/X.4GsWpfUQ.', 'user4@gmail.com'),
       ('user5', '$2a$10$/XapBAlZcXurPdFgjlKkIOwCAr0WgTt5C09hq6xcQ/X.4GsWpfUQ.', 'user5@gmail.com');

INSERT INTO user_roles(user_id, role_id)
VALUES (1, 1),
       (2, 2);

INSERT INTO movies(title, genre, min_age, duration, description)
VALUES ('Avatar: Istota wody', 'Sci-Fi', 13, 193,
        'Akcja filmu Avatar: Istota wody rozgrywa się ponad dziesięć lat po wydarzeniach z pierwszej części. To opowieść o rodzinie Jake’a i Neytiri oraz ich staraniach, by zapewnić bezpieczeństwo sobie i swoim dzieciom, mimo tragedii, których wspólnie doświadczają i bitew, które muszą stoczyć, aby przeżyć.* Drodzy widzowie w filmie Avatar: Istota wody znajduje się kilka scen z dynamicznymi efektami świetlnymi, które mogą powodować dyskomfort u widzów wrażliwych na światło i wpływać na osoby z epilepsją fotogenną.'),
       ('Listy do M. 5', 'Komedia Romantyczna', 13, 117,
        '"Listy do M." powracają z 5. częścią wigilijnej opowieści. W kolejnej odsłonie zobaczymy świąteczne perypetie ulubionych bohaterów. Z czym zmierzą się tym razem? Melowi jak zwykle nic nie wychodzi. Zbieg okoliczności sprawia, że staje się bohaterem mimo woli, a jego nie zawsze kryształowy charakter znowu zostaje wystawiony na próbę. Wojciech, który nie czuje wszechobecnej radosnej atmosfery, spotyka na swojej drodze kogoś, kto zmienia jego świąteczne plany. Z kolei Karina i Szczepan uwikłają się w walkę o spadek, który może poróżnić nawet najbliższych. Przekonają się czy z rodziną rzeczywiście dobrze wychodzi się tylko na zdjęciach. To oczywiście nie wszystko! W tej części pojawią się też nowi bohaterowie i ich zaskakujące historie. "Listy do M. 5" skupią się na uniwersalnych wartościach, takich jak miłość, bliskość czy życzliwość, które obecnie są najistotniejsze.'),
       ('Dzika Noc', 'Akcja', 15, 112, 'Producenci filmów ”Nikt”, „John Wick”, „Deadpool 2” realizują dla studia Universal mroczny thriller „Dzika Noc”.

Zobaczymy między innymi gwiazdę serialu "Stranger Things” Davida Harboura oraz zdobywcę nagrody Emmy- Johnego Leguizamo oraz innych: Edi Pattersona, Cam Gigandet Alexa Hassella i Beverly D''Angelo.

Film wyreżyserowany będzie przez norweskiego reżysera Tommy''ego Wirkolę ("Hansel & Gretel: Łowca czarownic” na podstawie scenariusza Pata Caseya i Josha Millera (Sonic 2.Szybki jak błyskawica).

Kiedy pewna, bogata rodzina zostaje zakładnikami w Wigilię, przestępcy nie są przygotowani na niespodziewanego bojownika. Święty Mikołaj (w tej roli David Harbour, serial Stranger Things) jest na miejscu i ma zamiar pokazać, dlaczego Mikołaj nie będzie taki święty.');

DROP TABLE IF EXISTS reviews;
CREATE TABLE reviews
(
    id          BIGINT PRIMARY KEY AUTO_INCREMENT,
    movie_id    BIGINT       NOT NULL,
    user_id     BIGINT       NOT NULL,
    rating      INT          NOT NULL,
    review_text VARCHAR(255) NOT NULL,
    FOREIGN KEY (movie_id) REFERENCES movies (id),
    FOREIGN KEY (user_id) REFERENCES users (id)
);

INSERT INTO reviews (movie_id, user_id, rating, review_text)
VALUES (1, 2, 5, 'Great movie!'),
       (2, 3, 4, 'Really enjoyed it!'),
       (3, 4, 3, 'It was okay.'),
       (1, 5, 2, 'Not my favorite.'),
       (2, 6, 1, 'Hated it!');

DROP TABLE IF EXISTS screen;
CREATE TABLE screen
(
    id          BIGINT PRIMARY KEY AUTO_INCREMENT,
    cinema_id   BIGINT       NOT NULL,
    screen_name VARCHAR(255) NOT NULL,
    capacity    INT          NOT NULL,
    FOREIGN KEY (cinema_id) REFERENCES cinema (id)
);
INSERT INTO screen (cinema_id, screen_name, capacity)
VALUES (1, 'SALA1', 161),
       (1, 'SALA2', 161),
       (1, 'SALA3', 161),
       (1, 'SALA4', 161),
       (2, 'SALA1', 161),
       (2, 'SALA2', 161),
       (2, 'SALA3', 161),
       (2, 'SALA4', 161),
       (3, 'SALA1', 161),
       (3, 'SALA2', 161),
       (3, 'SALA3', 161),
       (3, 'SALA4', 161),
       (4, 'SALA1', 161),
       (4, 'SALA2', 161),
       (4, 'SALA3', 161),
       (4, 'SALA4', 161),
       (5, 'SALA1', 161),
       (5, 'SALA2', 161),
       (5, 'SALA3', 161),
       (5, 'SALA4', 161);


DROP TABLE IF EXISTS screenings;
CREATE TABLE screenings
(
    id               BIGINT PRIMARY KEY AUTO_INCREMENT,
    repertoire_id    BIGINT         NOT NULL,
    screen_id        BIGINT         NOT NULL,
    movie_id         BIGINT         NOT NULL,
    date             DATE           NOT NULL,
    start_time       TIME           NOT NULL,
    movie_type       VARCHAR(8)     NOT NULL,
    movie_sound_type VARCHAR(12)    NOT NULL,
    seats            VARCHAR(12000) NOT NULL,
    FOREIGN KEY (repertoire_id) REFERENCES repertoire (id),
    FOREIGN KEY (screen_id) REFERENCES screen (id),
    FOREIGN KEY (movie_id) REFERENCES movies (id)
);
INSERT INTO screenings (repertoire_id, screen_id, movie_id, date, start_time, movie_type, movie_sound_type, seats)
VALUES (1, 1, 1, '2023-01-10', '12:00:00', 'D2', 'DUBBING',
        '[["WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","ZAJETE","ZAJETE","ZAJETE","WOLNE","WOLNE","NIE_ISTNIEJE","NIE_ISTNIEJE","NIE_ISTNIEJE","NIE_ISTNIEJE","NIE_ISTNIEJE"],["WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","NIE_ISTNIEJE","NIE_ISTNIEJE","NIE_ISTNIEJE","NIE_ISTNIEJE","NIE_ISTNIEJE"],["WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","NIE_ISTNIEJE","NIE_ISTNIEJE","NIE_ISTNIEJE","NIE_ISTNIEJE","NIE_ISTNIEJE"],["WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","NIE_ISTNIEJE","NIE_ISTNIEJE","NIE_ISTNIEJE","NIE_ISTNIEJE","NIE_ISTNIEJE"],["WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","NIE_ISTNIEJE","NIE_ISTNIEJE","NIE_ISTNIEJE","NIE_ISTNIEJE","NIE_ISTNIEJE"],["WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","NIE_ISTNIEJE","NIE_ISTNIEJE","NIE_ISTNIEJE","NIE_ISTNIEJE","NIE_ISTNIEJE"],["WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","NIE_ISTNIEJE","NIE_ISTNIEJE","NIE_ISTNIEJE","NIE_ISTNIEJE","NIE_ISTNIEJE"],["WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","NIE_ISTNIEJE","NIE_ISTNIEJE","NIE_ISTNIEJE","NIE_ISTNIEJE","NIE_ISTNIEJE"],["WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","NIE_ISTNIEJE","NIE_ISTNIEJE","NIE_ISTNIEJE","WOLNE","WOLNE"],["WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","NIE_ISTNIEJE","NIE_ISTNIEJE","NIE_ISTNIEJE","WOLNE","WOLNE"],["WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","ZAJETE","ZAJETE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","NIE_ISTNIEJE","NIE_ISTNIEJE","NIE_ISTNIEJE","WOLNE","WOLNE"],["WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","NIE_ISTNIEJE","NIE_ISTNIEJE","NIE_ISTNIEJE","WOLNE","WOLNE"]]'),
       (1, 1, 1, '2023-01-10', '15:00:00', 'D2', 'DUBBING',
        '[["WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","ZAJETE","ZAJETE","ZAJETE","WOLNE","WOLNE","NIE_ISTNIEJE","NIE_ISTNIEJE","NIE_ISTNIEJE","NIE_ISTNIEJE","NIE_ISTNIEJE"],["WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","NIE_ISTNIEJE","NIE_ISTNIEJE","NIE_ISTNIEJE","NIE_ISTNIEJE","NIE_ISTNIEJE"],["WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","NIE_ISTNIEJE","NIE_ISTNIEJE","NIE_ISTNIEJE","NIE_ISTNIEJE","NIE_ISTNIEJE"],["WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","NIE_ISTNIEJE","NIE_ISTNIEJE","NIE_ISTNIEJE","NIE_ISTNIEJE","NIE_ISTNIEJE"],["WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","NIE_ISTNIEJE","NIE_ISTNIEJE","NIE_ISTNIEJE","NIE_ISTNIEJE","NIE_ISTNIEJE"],["WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","NIE_ISTNIEJE","NIE_ISTNIEJE","NIE_ISTNIEJE","NIE_ISTNIEJE","NIE_ISTNIEJE"],["WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","NIE_ISTNIEJE","NIE_ISTNIEJE","NIE_ISTNIEJE","NIE_ISTNIEJE","NIE_ISTNIEJE"],["WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","NIE_ISTNIEJE","NIE_ISTNIEJE","NIE_ISTNIEJE","NIE_ISTNIEJE","NIE_ISTNIEJE"],["WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","NIE_ISTNIEJE","NIE_ISTNIEJE","NIE_ISTNIEJE","WOLNE","WOLNE"],["WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","NIE_ISTNIEJE","NIE_ISTNIEJE","NIE_ISTNIEJE","WOLNE","WOLNE"],["WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","ZAJETE","ZAJETE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","NIE_ISTNIEJE","NIE_ISTNIEJE","NIE_ISTNIEJE","WOLNE","WOLNE"],["WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","NIE_ISTNIEJE","NIE_ISTNIEJE","NIE_ISTNIEJE","WOLNE","WOLNE"]]'),
       (1, 1, 1, '2023-01-10', '18:00:00', 'D2', 'DUBBING',
        '[["WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","ZAJETE","ZAJETE","ZAJETE","WOLNE","WOLNE","NIE_ISTNIEJE","NIE_ISTNIEJE","NIE_ISTNIEJE","NIE_ISTNIEJE","NIE_ISTNIEJE"],["WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","NIE_ISTNIEJE","NIE_ISTNIEJE","NIE_ISTNIEJE","NIE_ISTNIEJE","NIE_ISTNIEJE"],["WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","NIE_ISTNIEJE","NIE_ISTNIEJE","NIE_ISTNIEJE","NIE_ISTNIEJE","NIE_ISTNIEJE"],["WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","NIE_ISTNIEJE","NIE_ISTNIEJE","NIE_ISTNIEJE","NIE_ISTNIEJE","NIE_ISTNIEJE"],["WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","NIE_ISTNIEJE","NIE_ISTNIEJE","NIE_ISTNIEJE","NIE_ISTNIEJE","NIE_ISTNIEJE"],["WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","NIE_ISTNIEJE","NIE_ISTNIEJE","NIE_ISTNIEJE","NIE_ISTNIEJE","NIE_ISTNIEJE"],["WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","NIE_ISTNIEJE","NIE_ISTNIEJE","NIE_ISTNIEJE","NIE_ISTNIEJE","NIE_ISTNIEJE"],["WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","NIE_ISTNIEJE","NIE_ISTNIEJE","NIE_ISTNIEJE","NIE_ISTNIEJE","NIE_ISTNIEJE"],["WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","NIE_ISTNIEJE","NIE_ISTNIEJE","NIE_ISTNIEJE","WOLNE","WOLNE"],["WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","NIE_ISTNIEJE","NIE_ISTNIEJE","NIE_ISTNIEJE","WOLNE","WOLNE"],["WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","ZAJETE","ZAJETE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","NIE_ISTNIEJE","NIE_ISTNIEJE","NIE_ISTNIEJE","WOLNE","WOLNE"],["WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","WOLNE","NIE_ISTNIEJE","NIE_ISTNIEJE","NIE_ISTNIEJE","WOLNE","WOLNE"]]');

DROP TABLE IF EXISTS ticket;
CREATE TABLE ticket
(
    id           BIGINT PRIMARY KEY AUTO_INCREMENT,
    screening_id BIGINT      NOT NULL,
    seat_row     INT         NOT NULL,
    seat_number  INT         NOT NULL,
    ticket_type  VARCHAR(32) NOT NULL,
    FOREIGN KEY (screening_id) REFERENCES screenings (id)
);

INSERT INTO ticket (screening_id, seat_row, seat_number, ticket_type)
VALUES (1, 0, 8, 'ULGOWY'),
       (1, 0, 9, 'NORMALNY'),
       (1, 0, 10, 'SENIOR');

