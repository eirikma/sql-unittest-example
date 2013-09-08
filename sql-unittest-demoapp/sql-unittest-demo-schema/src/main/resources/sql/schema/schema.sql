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

create table filcontent (
    fil_id number(19,0) not null,
    content blob,
    compression varchar2(255),
    constraint PK_FIL_ID primary key(fil_id)
);

CREATE TABLE KORR_TILSTANDER (
   ID number(19,0) PRIMARY KEY NOT NULL,
   KORREKSJON_ID number(19,0) NOT NULL,
   SOURCE varchar2(255 char) NOT NULL,
   KORREKSJON_TYPE varchar2(255 char),
   BESTILLINGSNUMMER varchar2(255 char) NOT NULL,
   BESTILLINGSDATO date NOT NULL,
   CREATED_AT timestamp NOT NULL,
   CREATED_BY_USER varchar2(255) NOT NULL,
   STATUS varchar2(255 char) NOT NULL,
   DEBET_ACCOUNT varchar2(255 char),
   CREDIT_ACCOUNT varchar2(255 char),
   DEBET_NAVN varchar2(255 char),
   AMOUNT number(19,2),
   OPPRINNELIG_DATO date,
   CAUSE varchar2(255 char),
   BLANKETTNUMMER varchar2(255 char),
   FRITEKST varchar2(255 char),
   GEBYR_BANKREGNUMMER varchar2(255 char),
   ARKIVREF varchar2(255 char),
   BESTILLING_STATUS_UPDATE_PATH varchar2(255 char),
   BUNTNUMMER varchar2(255 char),
   KID_NUMMER varchar2(255 char),
   TRANSTEKST varchar2(255 char),
   NIBE_BUS_KODE varchar2(15 char),
   NIBE_ALC_KODE varchar2(15 char),
   BELOP_KREDIT_SUMPOST number(19,2),
   IDX number(10,0)
);
ALTER TABLE KORR_TILSTANDER ADD CONSTRAINT UNIQUE_KORR_TILSTANDER unique (KORREKSJON_TYPE, SOURCE, BESTILLINGSNUMMER, BESTILLINGSDATO, CAUSE, STATUS);

CREATE INDEX KORR_TILSTANDER_STATUS_IDX ON KORR_TILSTANDER(STATUS);
CREATE INDEX KORR_TILSTANDER_TYPE_IDX ON KORR_TILSTANDER(KORREKSJON_TYPE);

CREATE TABLE KORREKSJON (
   ID number(19,0) PRIMARY KEY NOT NULL,
   OBJECT_TYPE varchar2(255) NOT NULL,
   SOURCE varchar2(255) NOT NULL,
   BESTILLINGSNUMMER varchar2(255) NOT NULL,
   BESTILLINGSDATO date NOT NULL,
   KORREKSJON_TYPE varchar2(255),
   STATUS varchar2(255) NOT NULL,
   VERSION number (10,0) DEFAULT 0,
   CURRENT_TILSTAND_ID number(19,0),
   REVERSERING_ID number(19,0),
   OVERFORING_ID number(19,0),
   BESTILLING_ID number(19,0),
   REVERSERING_MED_FULLMAKT_ID NUMBER(19,0),
   OVERFORING_MED_FULLMAKT_ID NUMBER(19,0),
   FULLMAKTSBREV_FIL_ID NUMBER(19,0),
   PURREBREV_FIL_ID NUMBER(19,0)
);
alter table KORREKSJON  add constraint FK_CURR_TILSTAND foreign key (CURRENT_TILSTAND_ID) REFERENCING KORR_TILSTANDER(ID);
alter table KORREKSJON  add constraint FK_OVERFORING foreign key (OVERFORING_ID) REFERENCING KORREKSJON(ID);
alter table KORREKSJON  add constraint FK_REVERSERING foreign key (REVERSERING_ID) REFERENCING KORREKSJON(ID);
alter table KORREKSJON  add constraint FK_REVERSERING_FULLMAKT foreign key (REVERSERING_MED_FULLMAKT_ID) REFERENCING KORREKSJON(ID);
alter table KORREKSJON  add constraint FK_OVERFORING_FULLMAKT foreign key (OVERFORING_MED_FULLMAKT_ID) REFERENCING KORREKSJON(ID);
alter table KORREKSJON  add constraint FK_FULLMAKTSBREV foreign key (FULLMAKTSBREV_FIL_ID) REFERENCING FIL(ID);
alter table KORREKSJON  add constraint FK_PURREBREV foreign key (PURREBREV_FIL_ID) REFERENCING FIL(ID);

ALTER TABLE KORREKSJON ADD CONSTRAINT UNIQUE_KORREKSJON unique (KORREKSJON_TYPE, SOURCE, BESTILLINGSNUMMER, BESTILLINGSDATO);

CREATE INDEX KORRE_BEST_NR_IDX ON KORREKSJON(BESTILLINGSNUMMER);
CREATE INDEX KORRE_DATO_IDX ON KORREKSJON(BESTILLINGSDATO);
CREATE INDEX KORREKSJON_STATUS_IDX ON KORREKSJON(STATUS);
CREATE INDEX KORREKSJON_OBJECT_TYPE_IDX ON KORREKSJON(OBJECT_TYPE);
CREATE INDEX KORREKSJON_TYPE_IDX ON KORREKSJON(KORREKSJON_TYPE);

create table SCHEMA_VERSION (
    VERSION number(10,0) not null,
    MIGRATION_DATE date not null,
    unique (VERSION)
);
create table SCHEMA_VERSION_INFO (
    id number(19,0) not null,
    installed timestamp,
    current_version varchar2(255 char),
    previous_version varchar2(255 char),
    schema_owner varchar2(255 char),
    constraint PK_SCHEMA_VERSION_INFO primary key(id)
);

create table PERSISTENT_PROPERTY (
    KEY varchar(255) not null,
    VALUE varchar(255) not null,
    SERVER varchar(255) default 'all',
    DESCRIPTION varchar2(1024 char),
    PERSISTENT_PROPERTY_GROUP number(19,0),
    unique (KEY, SERVER)
);

CREATE TABLE persistent_prop_endringslogg (
    id       NUMBER(19) CONSTRAINT nn_pers_prop_endringslogg_id NOT NULL,
    KEY      VARCHAR2(255 CHAR),
    server   VARCHAR2(255 CHAR),
    oldvalue VARCHAR2(255 CHAR),
    newvalue VARCHAR2(255 CHAR),
    tid TIMESTAMP(6),
    username VARCHAR2(255 CHAR),
    CONSTRAINT pk_pers_prop_endringslogg PRIMARY KEY (id) USING INDEX(CREATE
    UNIQUE INDEX ix_pers_prop_endringslogg_pk ON persistent_prop_endringslogg(
    id) )
  );

CREATE TABLE persistent_property_group (
    id        NUMBER(19) CONSTRAINT nn_pers_prop_grp_id NOT NULL,
    navn      VARCHAR2(255 CHAR) CONSTRAINT nn_pers_prop_grp_navn NOT NULL,
    sortering NUMBER(19,0),
    CONSTRAINT pk_pers_prop_grp PRIMARY KEY (id) USING INDEX(CREATE UNIQUE
    INDEX ix_pers_prop_grp_pk ON persistent_property_group(id))
  );

ALTER TABLE persistent_property ADD CONSTRAINT fk_persistent_property_grp
FOREIGN KEY(persistent_property_group) REFERENCES persistent_property_group;

CREATE sequence pers_prop_endrlogg_id_sequence;


create table work_item (
    id number(19,0) not null,
    entity_id_long_part number(19,0),
    entity_id_discriminator_part number(19,0),
    processing_state number(10,0),
    created_by_server_id varchar2(255 char),
    processing_server_id varchar2(255 char),
    created_time timestamp,
    start_time timestamp,
    finished_time timestamp,
    earliest_execution_time timestamp,
    thread_id varchar2(255 char),
    error_message varchar2(3999 char),
    number_of_retries number(19,0),
    work_queue number(19,0),
    work_queue_name varchar2(255 char),
    entity_description varchar2(255 char),
    CONSTRAINT PK_work_item PRIMARY KEY (id)
);

create table work_queue (
    id number(19,0) not null,
    entity_service_name varchar2(255 char) not null ,
    consumer_id varchar2(255 char) not null,
    status number(10,0),
    start_time timestamp,
    sort number(19,0),
    beskrivelse varchar2(255 char),
    work_queue_group number(19,0),
    CONSTRAINT PK_work_queue PRIMARY KEY (id),
    unique (entity_service_name)
);

create table work_queue_group (
    id number(19,0) not null,
    group_name varchar2(255 char) not null ,
    sort number(19,0) not null,
    CONSTRAINT PK_work_queue_group PRIMARY KEY (id),
    unique (group_name)
);

create index work_item_work_queue_index on work_item (work_queue);
create index work_item_index2 on work_item (processing_state, earliest_execution_time);

alter table work_item
    add constraint fk_work_item_work_queue
    foreign key (work_queue)
    references work_queue;

alter table work_queue
    add constraint fk_work_queue_work_group
    foreign key (work_queue_group)
    references work_queue_group;

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

create table status_history (
    id number(19,0) not null,
    entity_key number(19,0),
    entity_class varchar2(255 char),
    current_status_event number(10,0),
    constraint PK_STATUS_HISTORY primary key(id)
);

create table status_message (
    status_id number(19,0) not null,
    message_code varchar2(255 char),
    detail1 varchar2(255 char),
    detail2 varchar2(255 char),
    detail3 varchar2(255 char),
    line_number number(10,0)
);


create table menu_element (
    id number(19,0) not null,
    menu_id number(10,0),
    title varchar2(255 char),
    url varchar2(255 char),
    exernal_server varchar2(255 char),
    sortindex number(10,0),
    resource_name varchar2(255 char),
    namespace varchar2(255 char),
    action varchar2(255 char),
    parent number(19,0),
    CONSTRAINT PK_menu_element PRIMARY KEY (id)
);
    alter table menu_element
        add constraint FKE9260C1CE4F6E4F3
        foreign key (parent)
        references menu_element;



create sequence BESTILLING_NUMMER_SEQUENCE NOCACHE start with 101 increment by 1 nocycle minvalue 1 maxvalue 999999999999999;
create sequence NIBEUTVEKSLING_SEQUENCE;
create sequence work_item_id_sequence;
create sequence work_queue_id_sequence;
create sequence work_queue_group_id_sequence;
create sequence hilo_sequence;
create sequence hibernate_sequence;

create sequence menuelement_id_sequence;




CREATE TABLE MANAGED_JOBS (
   ID number(19,0) PRIMARY KEY NOT NULL,
   JOBB_TYPE varchar2(255 char) NOT NULL,
   DATO date NOT NULL,
   CREATED_TIME timestamp NOT NULL,
   STARTED_TIME timestamp,
   FINISHED_TIME timestamp,
   STATUS varchar2(255 char) NOT NULL,
   ANTALL_TOTALT number(10,0),
   ANTALL_BEHANDLET number(10,0),
   NAVN varchar2(255 char),
   FIL_ID number(19,0)
);

alter table MANAGED_JOBS
    add constraint FK_MNG_JOB_FIL
    foreign key (FIL_ID)
    references FIL;

CREATE INDEX MANAGED_JOBS_DATO_IDX ON MANAGED_JOBS(DATO);
CREATE INDEX MANAGED_JOBS_TYPE_IDX ON MANAGED_JOBS(JOBB_TYPE);
CREATE INDEX MANAGED_JOBS_STATUS_IDX ON MANAGED_JOBS(STATUS);

