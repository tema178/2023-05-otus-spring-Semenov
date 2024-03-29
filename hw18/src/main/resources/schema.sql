drop table IF EXISTS AUTHORS;
create TABLE AUTHORS
(
    ID   BIGINT PRIMARY KEY AUTO_INCREMENT,
    NAME VARCHAR(255)
);

drop table IF EXISTS GENRES;
create TABLE GENRES
(
    ID   BIGINT PRIMARY KEY AUTO_INCREMENT,
    NAME VARCHAR(255),

    CONSTRAINT GENRE_NAME_UNIQUE
    UNIQUE ( NAME )
);

drop table IF EXISTS BOOKS;
create TABLE BOOKS
(
    ID   BIGINT PRIMARY KEY AUTO_INCREMENT,
    NAME VARCHAR(255),
    AUTHOR_ID BIGINT NOT NULL,
    GENRE_ID BIGINT NOT NULL,

    foreign key (AUTHOR_ID) references AUTHORS(ID),
    foreign key (GENRE_ID) references GENRES(ID)

);

drop table IF EXISTS COMMENTS;
create TABLE COMMENTS
(
    ID BIGINT PRIMARY KEY AUTO_INCREMENT,
    BOOK_ID BIGINT references BOOKS(id) on delete cascade,
    BODY VARCHAR(2047)
);

drop table IF EXISTS users;
create table users
(
    username varchar_ignorecase(50) not null primary key,
    password varchar_ignorecase(500) not null,
    enabled boolean not null
);

drop table IF EXISTS authorities;
create table authorities
(
    id   bigint primary key auto_increment,
    username varchar_ignorecase(50) not null,
    authority varchar_ignorecase(50) not null,
    constraint fk_authorities_users foreign key(username) references users(username)
);

create unique index ix_auth_username on authorities (username,authority);