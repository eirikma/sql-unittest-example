-- tillegg for core-proxy appkonsoll

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
    add constraint FK_menu_parent_ref
    foreign key (parent)
    references menu_element;

create sequence menuelement_id_sequence;

-- Persistent property oppdatering for shared-config appkonsoll

alter table persistent_property
add DESCRIPTION varchar2(1024 char);

alter table persistent_property
add PERSISTENT_PROPERTY_GROUP number(19,0);

CREATE
  TABLE persistent_prop_endringslogg
  (
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

CREATE
  TABLE persistent_property_group
  (
    id        NUMBER(19) CONSTRAINT nn_pers_prop_grp_id NOT NULL,
    navn      VARCHAR2(255 CHAR) CONSTRAINT nn_pers_prop_grp_navn NOT NULL,
    sortering NUMBER(19,0),
    CONSTRAINT pk_pers_prop_grp PRIMARY KEY (id) USING INDEX(CREATE UNIQUE
    INDEX ix_pers_prop_grp_pk ON persistent_property_group(id))
  );

ALTER TABLE persistent_property ADD CONSTRAINT fk_persistent_property_grp
FOREIGN KEY(persistent_property_group) REFERENCES persistent_property_group;

CREATE sequence pers_prop_endrlogg_id_sequence;






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

