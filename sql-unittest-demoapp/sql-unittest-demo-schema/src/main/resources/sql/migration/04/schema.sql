alter table KORREKSJON rename column BBS_DATO to OPPRINNELIG_DATO;

drop sequence NIBEUTVEKSLING_SEQUENCE;
create sequence NIBEUTVEKSLING_SEQUENCE;
