drop database if exists movierecommed;
create database movierecommend;

use movierecommend;

create table user
(
    usrId    int auto_increment,
    usrName  varchar(20),
    password varchar(255),
    phoneNum varchar(20),
    primary key (usrId)
);

create table movie
(
    movieId    int auto_increment,
    movieName  varchar(50),
    keywords varchar(50),
    overview blob,
    rating double default 0,
    characters varchar(255),
    directors varchar(255),
    producer varchar(50),
    primary key (movieId)
);

create table dislike
(
    movieId int,
    usrId int,
    primary key (movieId,usrId),
    foreign key (movieId) references movie(movieId),
    foreign key (usrId) references user(usrId)
);

create table collect
(
    movieId int,
    usrId int,
    primary key (movieId,usrId),
    foreign key (movieId) references movie(movieId),
    foreign key (usrId) references user(usrId)
);

create table comment
(
    commentId int auto_increment,
    movieId int,
    usrId int,
    content blob,
    primary key (commentId),
    foreign key (movieId) references movie(movieId),
    foreign key (usrId) references user(usrId)
);

create table rate
(
    movieId int,
    usrId int,
    rate double,
    primary key (movieId,usrId),
    foreign key (movieId) references movie(movieId),
    foreign key (usrId) references user(usrId)
);