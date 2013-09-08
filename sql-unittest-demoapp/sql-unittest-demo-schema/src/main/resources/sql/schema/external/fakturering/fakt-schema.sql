
    create table fakthendelse (
        id number(19,0) PRIMARY KEY,
        version number(10,0) not null,
        uuid varchar2(255 char),
        hendelsestidspunkt timestamp,
        kollektortidspunkt timestamp,
        varenummer number(10,2),
        sporingsid varchar2(255 char),
        sporingsclass varchar2(255 char),
        varebetaler_id varchar2(255 char),
        varebetaler_type varchar2(255 char),
        vareforbruker_id varchar2(255 char),
        vareforbruker_type varchar2(255 char),
        belopstype char,
        status number(10,2),
        created_by varchar2(255 char)
    );

    create table kvantifisert_fakthendelse (
        id number(19,0) primary key,
        version number(10,0) not null,
        uuid varchar2(255 char),
        hendelsestidspunkt timestamp,
        kollektortidspunkt timestamp,
        varenummer number(10,2),
        kvantitet number(10,2),
        sporingsid varchar2(255 char),
        sporingsclass varchar2(255 char),
        varebetaler_id varchar2(255 char),
        varebetaler_type varchar2(255 char),
        vareforbruker_id varchar2(255 char),
        vareforbruker_type varchar2(255 char),
        belopstype char,
        status number(10,2),
        created_by varchar2(255 char),
        transaksjon_id varchar2(255 char)
    );

    create sequence SEQ_FAKTHENDELSE;

