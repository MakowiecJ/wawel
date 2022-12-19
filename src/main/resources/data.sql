DROP TABLE IF EXISTS roles;
CREATE TABLE roles (
  ID INTEGER NOT NULL AUTO_INCREMENT,
  NAME VARCHAR(60) NOT NULL,
  PRIMARY KEY(ID)
);

DROP TABLE IF EXISTS users;
CREATE TABLE users (
                       ID INTEGER NOT NULL AUTO_INCREMENT,
                       USERNAME VARCHAR(60) NOT NULL,
                       PASSWORD VARCHAR(60) NOT NULL,
                       EMAIL VARCHAR(60) NOT NULL,
                       PRIMARY KEY(ID)
);

DROP TABLE IF EXISTS user_roles;
CREATE TABLE user_roles(
                       USER_ID INTEGER NOT NULL AUTO_INCREMENT,
                       ROLE_ID INTEGER NOT NULL,
                       FOREIGN KEY(USER_ID) REFERENCES users(ID),
                       FOREIGN KEY(ROLE_ID) REFERENCES roles(ID)
);

DROP TABLE IF EXISTS movies;
CREATE TABLE movies(
                           ID INTEGER NOT NULL AUTO_INCREMENT,
                           TITLE VARCHAR(60) NOT NULL,
                           GENRE VARCHAR(60) NOT NULL,
                           MIN_AGE INTEGER NOT NULL,
                           DURATION INTEGER NOT NULL,
                           DESCRIPTION VARCHAR(1000) NOT NULL,
                           PRIMARY KEY(ID)
);

INSERT INTO roles (NAME) VALUES ('ROLE_ADMIN');
INSERT INTO roles (NAME) VALUES ('ROLE_USER');

INSERT INTO users (USERNAME, PASSWORD, EMAIL) VALUES
('admin', '$2a$10$uuXzvyevIXLJWmkA7WC2e.xM8xZZHJT0v3qdmcolz2Y3G.p2oEGjW', 'admin@gmail.com'),
('user', '$2a$10$/XapBAlZcXurPdFgjlKkIOwCAr0WgTt5C09hq6xcQ/X.4GsWpfUQ.', 'user@gmail.com');

INSERT INTO user_roles(USER_ID, ROLE_ID) VALUES
(1, 1),
(2, 2);

INSERT INTO movies(TITLE, GENRE, MIN_AGE, DURATION, DESCRIPTION) VALUES
('Avatar: Istota wody', 'Sci-Fi', 13, 193, 'Akcja filmu Avatar: Istota wody rozgrywa się ponad dziesięć lat po wydarzeniach z pierwszej części. To opowieść o rodzinie Jake’a i Neytiri oraz ich staraniach, by zapewnić bezpieczeństwo sobie i swoim dzieciom, mimo tragedii, których wspólnie doświadczają i bitew, które muszą stoczyć, aby przeżyć.* Drodzy widzowie w filmie Avatar: Istota wody znajduje się kilka scen z dynamicznymi efektami świetlnymi, które mogą powodować dyskomfort u widzów wrażliwych na światło i wpływać na osoby z epilepsją fotogenną.'),
('Listy do M. 5', 'Komedia Romantyczna', 13, 117, '"Listy do M." powracają z 5. częścią wigilijnej opowieści. W kolejnej odsłonie zobaczymy świąteczne perypetie ulubionych bohaterów. Z czym zmierzą się tym razem? Melowi jak zwykle nic nie wychodzi. Zbieg okoliczności sprawia, że staje się bohaterem mimo woli, a jego nie zawsze kryształowy charakter znowu zostaje wystawiony na próbę. Wojciech, który nie czuje wszechobecnej radosnej atmosfery, spotyka na swojej drodze kogoś, kto zmienia jego świąteczne plany. Z kolei Karina i Szczepan uwikłają się w walkę o spadek, który może poróżnić nawet najbliższych. Przekonają się czy z rodziną rzeczywiście dobrze wychodzi się tylko na zdjęciach. To oczywiście nie wszystko! W tej części pojawią się też nowi bohaterowie i ich zaskakujące historie. "Listy do M. 5" skupią się na uniwersalnych wartościach, takich jak miłość, bliskość czy życzliwość, które obecnie są najistotniejsze.'),
('Dzika Noc', 'Akcja', 15, 112, 'Producenci filmów ”Nikt”, „John Wick”, „Deadpool 2” realizują dla studia Universal mroczny thriller „Dzika Noc”.

Zobaczymy między innymi gwiazdę serialu "Stranger Things” Davida Harboura oraz zdobywcę nagrody Emmy- Johnego Leguizamo oraz innych: Edi Pattersona, Cam Gigandet Alexa Hassella i Beverly D''Angelo.

Film wyreżyserowany będzie przez norweskiego reżysera Tommy''ego Wirkolę ("Hansel & Gretel: Łowca czarownic” na podstawie scenariusza Pata Caseya i Josha Millera (Sonic 2.Szybki jak błyskawica).

Kiedy pewna, bogata rodzina zostaje zakładnikami w Wigilię, przestępcy nie są przygotowani na niespodziewanego bojownika. Święty Mikołaj (w tej roli David Harbour, serial Stranger Things) jest na miejscu i ma zamiar pokazać, dlaczego Mikołaj nie będzie taki święty.');