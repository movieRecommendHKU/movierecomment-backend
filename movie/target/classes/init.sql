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

CREATE TABLE `Movie`
(
    `movieId`     INT       NOT NULL AUTO_INCREMENT,
    `movieName`   CHAR(255) NOT NULL,
    `overview`    blob,
    `director`    CHAR(255),
    `producer`    CHAR(255),
    `rating`      double,
    `releaseDate` CHAR(255),
    `popularity`  double,
    `posterPath`  CHAR(255),
    `voteCount`   INT,
    PRIMARY KEY (`movieId`)
);

CREATE TABLE `Genre`
(
    `genreId`   INT       NOT NULL AUTO_INCREMENT,
    `genreName` CHAR(255) NOT NULL,
    PRIMARY KEY (`genreId`)
);

CREATE TABLE `MovieGenre`
(
    `id`   INT       NOT NULL AUTO_INCREMENT,
    `genreId`   INT       NOT NULL,
    `genreName` CHAR(255) NOT NULL,
    `movieId` INT       NOT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`genreId`) REFERENCES `Genre` (`genreId`) ON DELETE CASCADE,
    FOREIGN KEY (`movieId`) REFERENCES `Movie` (`movieId`) ON DELETE CASCADE
);

CREATE TABLE `Preference`
(
    `userId` INT NOT NULL,
    `genreId`  INT NOT NULL,
    FOREIGN KEY (`genreId`) REFERENCES `Genre` (`genreId`) ON DELETE CASCADE,
    FOREIGN KEY (`userId`) REFERENCES `Account` (`userId`) ON DELETE CASCADE
);

CREATE TABLE `Cast`
(
    `castId`   INT       NOT NULL AUTO_INCREMENT,
    `castName` CHAR(255) NOT NULL,
    `gender`    INT,
    `profilePath` CHAR(255),
    PRIMARY KEY (`castId`)
);

CREATE TABLE MovieCast
(
    `id`        INT       NOT NULL AUTO_INCREMENT,
    `movieId`   INT       NOT NULL,
    `castId`   INT        NOT NULL,
    `castOrder` INT,
    `character` CHAR(255),
    `castName`  CHAR(255),
    PRIMARY KEY(`id`),
    FOREIGN KEY (`movieId`) REFERENCES `Movie` (`movieId`) ON DELETE CASCADE,
    FOREIGN KEY (`castId`) REFERENCES `Cast` (`actorId`) ON DELETE CASCADE
);

CREATE TABLE Collect
(
    collectId INT NOT NULL AUTO_INCREMENT,
    `userId`  INT NOT NULL,
    `movieId` INT NOT NULL,
    PRIMARY KEY (collectId),
    FOREIGN KEY (`userId`) REFERENCES `Account` (`userId`) ON DELETE CASCADE,
    FOREIGN KEY (`movieId`) REFERENCES `Movie` (`movieId`) ON DELETE CASCADE
);

CREATE TABLE Dislike
(
    dislikeId INT NOT NULL AUTO_INCREMENT,
    `userId`  INT NOT NULL,
    `movieId` INT NOT NULL,
    PRIMARY KEY (dislikeId),
    FOREIGN KEY (`userId`) REFERENCES `Account` (`userId`) ON DELETE CASCADE,
    FOREIGN KEY (`movieId`) REFERENCES `Movie` (`movieId`) ON DELETE CASCADE
);

CREATE TABLE Comment
(
    commentId   INT NOT NULL AUTO_INCREMENT,
    `userId`    INT NOT NULL,
    `movieId`   INT NOT NULL,
    `timestamp` DATE,
    `content`   blob,
    PRIMARY KEY (commentId),
    FOREIGN KEY (`userId`) REFERENCES `Account` (`userId`) ON DELETE CASCADE,
    FOREIGN KEY (`movieId`) REFERENCES `Movie` (`movieId`) ON DELETE CASCADE
);

CREATE TABLE `Rating`
(
    `ratingId`  INT NOT NULL AUTO_INCREMENT,
    `userId`    INT NOT NULL,
    `movieId`   INT NOT NULL,
    `timestamp` DATE,
    `rating`    double,
    PRIMARY KEY (ratingId),
    FOREIGN KEY (`userId`) REFERENCES `Account` (`userId`) ON DELETE CASCADE,
    FOREIGN KEY (`movieId`) REFERENCES `Movie` (`movieId`) ON DELETE CASCADE
);


CREATE TABLE SearchHistory
(
    `searchId`    INT AUTO_INCREMENT,
    `userId`      INT       NOT NULL,
    `timestamp`   DATE      NOT NULL,
    `searchType`  INT       NOT NULL,
    `searchWords` CHAR(255) NOT NULL,
    PRIMARY KEY (`searchId`),
    FOREIGN KEY (`userId`) REFERENCES `Account` (`userId`) ON DELETE CASCADE
);

CREATE TABLE SearchClickedHistory
(
    `searchId`  INT  NOT NULL,
    `clickedId` INT  NOT NULL,
    `userId`    INT  NOT NULL,
    `timestamp` DATE NOT NULL,
    FOREIGN KEY (`userId`) REFERENCES `Account` (`userId`) ON DELETE CASCADE,
    FOREIGN KEY (`searchId`) REFERENCES `SearchHistory` (`searchId`) ON DELETE CASCADE
);

CREATE TABLE `OuterMovieDb`
(
    `movieId` INT,
    `ImdbId`  INT,
    `tmdbId`  INT,
    FOREIGN KEY (`movieId`) REFERENCES `Movie` (`movieId`) ON DELETE CASCADE
);

CREATE TABLE `MovieSimilarity`
(
    `movieId`         INT,
    `similarMovieIds` char(255),
    FOREIGN KEY (`movieId`) REFERENCES `Movie` (`movieId`) ON DELETE CASCADE
);

CREATE TABLE `UserSimilarity`
(
    `userId`         INT,
    `similarUserIds` char(255),
    FOREIGN KEY (`userId`) REFERENCES `Account` (`userId`) ON DELETE CASCADE
);