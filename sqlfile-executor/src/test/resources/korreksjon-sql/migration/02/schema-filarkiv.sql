drop sequence hilo_sequence;
create sequence hilo_sequence;

drop sequence hibernate_sequence;
create sequence hibernate_sequence;

drop table schema_version_info cascade constraints;
create table schema_version_info (
    id number(19,0) not null,
    installed timestamp,
    current_version varchar2(255 char),
    previous_version varchar2(255 char),
    schema_owner varchar2(255 char),
    constraint PK_SCHEMA_VERSION_INFO primary key(id)
);

drop table qos_data cascade constraints;
create table qos_data (
    id number(19,0) not null,
    referance_entity_class varchar2(255 char),
    referance_entity_id number(19,0),
    qos_status_event_code varchar2(255 char),
    time_received timestamp,
    time_sent timestamp,
    system_of_origin varchar2(255 char),
    nim_qos_table_name varchar2(255 char),
    nim_qos_data varchar2(255 char),
    constraint PK_QOS_DATA primary key(id)
);

drop table status cascade constraints;
create table status (
    id number(19,0) not null,
    idx number(10,0),
    message_code varchar2(255 char),
    message_detail_1 varchar2(255 char),
    message_detail_2 varchar2(255 char),
    message_detail_3 varchar2(255 char),
    status_history number(19,0) not null,
    Status_code varchar2(255 char) not null,
    time_occurred timestamp,
    constraint PK_STATUS primary key(id)
);

drop table status_history cascade constraints;
create table status_history (
    id number(19,0) not null,
    entity_key number(19,0),
    entity_class varchar2(255 char),
    current_status_event number(10,0),
    constraint PK_STATUS_HISTORY primary key(id)
);

drop table status_message cascade constraints;
create table status_message (
    status_id number(19,0) not null,
    message_code varchar2(255 char),
    detail1 varchar2(255 char),
    detail2 varchar2(255 char),
    detail3 varchar2(255 char),
    line_number number(10,0)
);

drop table fil cascade constraints;
create table fil (
    id number(19,0) not null,
    content_length number(19,0),
    file_name varchar2(255 char),
    file_type varchar2(255 char),
    encoding varchar2(255 char),
    status_code varchar2(255 char),
    status_history number(19,0),
    constraint PK_FIL PRIMARY KEY(id),
    constraint UNIQUE_STATUS_HISTORY unique (status_history)
);

drop table filcontent cascade constraints;
create table filcontent (
    fil_id number(19,0) not null,
    content blob,
    compression varchar2(255),
    constraint PK_FIL_ID primary key(fil_id)
);
