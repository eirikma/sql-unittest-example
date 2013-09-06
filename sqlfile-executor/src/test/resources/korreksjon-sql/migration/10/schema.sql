
drop sequence BESTILLING_NUMMER_SEQUENCE;
-- create sequence BESTILLING_NUMMER_SEQUENCE NOCACHE;
create sequence BESTILLING_NUMMER_SEQUENCE NOCACHE start with 101 increment by 1 nocycle minvalue 1 maxvalue 999999999999999;
