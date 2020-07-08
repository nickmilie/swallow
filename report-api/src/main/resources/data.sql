DROP TABLE IF EXISTS RESULT;
DROP TABLE IF EXISTS REPORT;



create table REPORT
(
    REPORT_ID                       INT auto_increment,
    QUERY_CRITERIA_CHARACTER_PHRASE VARCHAR,
    QUERY_CRITERIA_PLANET_NAME      VARCHAR,
    constraint REPORT_PK
        primary key (REPORT_ID)
);

create unique index REPORT_REPORT_ID_UINDEX
    on REPORT (REPORT_ID);

create table RESULT
(
    RESULT_ID      INT auto_increment,
    FILM_ID        INT,
    CHARACTER_ID   INT,
    PLANET_ID      INT,
    FILM_NAME      VARCHAR,
    CHARACTER_NAME VARCHAR,
    PLANET_NAME    VARCHAR,
    REPORT_ID      INT,
    constraint RESULT_PK
        primary key (RESULT_ID),
    constraint RESULT_REPORT_FK
        foreign key (REPORT_ID) references REPORT (REPORT_ID)
);

create unique index RESULT_RESULT_ID_UINDEX
    on RESULT (RESULT_ID);

