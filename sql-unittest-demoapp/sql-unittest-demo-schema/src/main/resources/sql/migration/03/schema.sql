alter table KORREKSJON drop column VERSION;

alter table SEQUENCE_NUMBER rename column SEQUENCE_NUMBER to SEQ_NUMBER;
alter table SEQUENCE_NUMBER rename column SEQUENCE_KEY to SEQ_KEY;

alter table KORREKSJON add ARKIVREF varchar2(255 char) not null;
