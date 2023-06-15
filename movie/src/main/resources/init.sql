drop database if exists PMRS;

create database PMRS;

use PMRS;

CREATE TABLE `Account`
(
    `userId`   INT       NOT NULL AUTO_INCREMENT,
    `password` CHAR(255) NOT NULL,
    `email`    CHAR(255) NOT NULL,
    `userName` CHAR(255) NOT NULL,
    PRIMARY KEY (`userId`)
);

# INSERT INTO `Account` (`userName`, `email`, `password`)
# VALUES ('user1', 'user1@example.com', 'password1');

CREATE TABLE `Movie`
(
    `movieId`     INT       NOT NULL AUTO_INCREMENT,
    `movieName`   CHAR(255) NOT NULL,
    `overview`    CHAR(255),
    `director`    CHAR(255),
    `producer`    CHAR(255),
    `rating`      FLOAT,
    `releaseDate` DATE,
    PRIMARY KEY (`movieId`)
);

CREATE TABLE `KeyWord`
(
    `keyId`   INT       NOT NULL AUTO_INCREMENT,
    `keyName` CHAR(255) NOT NULL,
    PRIMARY KEY (`keyId`)
);

CREATE TABLE `KeyWordInter`
(
    `keyWordInterId` INT       NOT NULL AUTO_INCREMENT,
    `keyId`          INT       NOT NULL,
    `keyName`        CHAR(255) NOT NULL,
    `movieId`        INT       NOT NULL,
    PRIMARY KEY (`keyWordInterId`),
    FOREIGN KEY (`keyId`) REFERENCES `KeyWord` (`keyId`) ON DELETE CASCADE,
    FOREIGN KEY (`movieId`) REFERENCES `Movie` (`movieId`) ON DELETE CASCADE
);

CREATE TABLE `Actor`
(
    `actorId`   INT       NOT NULL AUTO_INCREMENT,
    `actorName` CHAR(255) NOT NULL,
    PRIMARY KEY (`actorId`)
);

CREATE TABLE `ActorInter`
(
    `actorInterId` INT       NOT NULL AUTO_INCREMENT,
    `actorId`      INT       NOT NULL,
    `movieId`      INT       NOT NULL,
    `actorName`    CHAR(255) NOT NULL,
    PRIMARY KEY (`actorInterId`),
    FOREIGN KEY (`actorId`) REFERENCES `Actor` (`actorId`) ON DELETE CASCADE,
    FOREIGN KEY (`movieId`) REFERENCES `Movie` (`movieId`) ON DELETE CASCADE
);

CREATE TABLE `Action`
(
    `actionId`  INT       NOT NULL AUTO_INCREMENT,
    `userId`    INT       NOT NULL,
    `movieId`   INT       NOT NULL,
    `actorName` CHAR(255) NOT NULL,
    PRIMARY KEY (`actionId`),
    FOREIGN KEY (`userId`) REFERENCES `Account` (`userId`) ON DELETE CASCADE,
    FOREIGN KEY (`movieId`) REFERENCES `Movie` (`movieId`) ON DELETE CASCADE
);

CREATE TABLE `Content`
(
    `contentId` INT NOT NULL AUTO_INCREMENT,
    `userId`    INT NOT NULL,
    `movieId`   INT NOT NULL,
    `timestamp` DATE,
    `content`   CHAR(255),
    PRIMARY KEY (`contentId`),
    FOREIGN KEY (`userId`) REFERENCES `Account` (`userId`) ON DELETE CASCADE,
    FOREIGN KEY (`movieId`) REFERENCES `Movie` (`movieId`) ON DELETE CASCADE
);

CREATE TABLE `Rating`
(
    `contentId` INT NOT NULL AUTO_INCREMENT,
    `userId`    INT NOT NULL,
    `movieId`   INT NOT NULL,
    `timestamp` DATE,
    `rating`    FLOAT,
    PRIMARY KEY (`contentId`),
    FOREIGN KEY (`userId`) REFERENCES `Account` (`userId`) ON DELETE CASCADE,
    FOREIGN KEY (`movieId`) REFERENCES `Movie` (`movieId`) ON DELETE CASCADE
);


CREATE TABLE `Transaction`
(
    `searchId`  INT AUTO_INCREMENT,
    `userId`    INT       NOT NULL,
    `timestamp` DATE      NOT NULL,
    `keywords`  CHAR(255) NOT NULL,
    `results`   CHAR(255),
    `clickeds`  INT       NOT NULL,
    PRIMARY KEY (`searchId`),
    FOREIGN KEY (`userId`) REFERENCES `Account` (`userId`) ON DELETE CASCADE
);

CREATE TABLE `OuterMovieDb`
(
    `movieId` INT,
    `ImdbId`  INT,
    `tmdbId`  INT,
    FOREIGN KEY (`movieId`) REFERENCES `Movie` (`movieId`) ON DELETE CASCADE
#     FOREIGN KEY (`imdbId`) REFERENCES `IMDB` (`imdbId`) ON DELETE CASCADE,
#     FOREIGN KEY (`tmdbId`) REFERENCES `TMDB` (`tmdbId`) ON DELETE CASCADE
);

CREATE TABLE `MovieSimilarity`
(
    `movieId`       INT,
    `similarMovies` INT,
    FOREIGN KEY (`movieId`) REFERENCES `Movie` (`movieId`)
);

CREATE TABLE `UserSimilarity`
(
    `userId`       INT,
    `similarUsers` INT,
    FOREIGN KEY (`userId`) REFERENCES `Account` (`userId`)
);