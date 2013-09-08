

--  references ID of first row for this korreksjon in order to provide
-- a database-secured non-business key to track which rows that
-- are versions of the same Korreksjon.
-- For the first row of a Korreksjon, this should be set to its own ID.
-- For each row of a Korreksjon, this should be set to the predecessors ID.
-- For each row of a Korreksjon, this should be set to the next korreksjon ID.
-- For last row of a Korreksjon, this should be set to null.
alter table KORREKSJON add ORIGINAL_ID number(19,0) not null;
alter table KORREKSJON add FORRIGE_ID number(19,0) null;
alter table KORREKSJON add NESTE_ID number(19,0) null;

-- add foreign key constraints
alter table KORREKSJON add constraint FK_ORIGINAL_KORR foreign key (ORIGINAL_ID) references KORREKSJON(ID);
alter table KORREKSJON add constraint FK_FORRIGE_KORR foreign key (FORRIGE_ID) references KORREKSJON(ID);
alter table KORREKSJON add constraint FK_NESTE_KORR foreign key (NESTE_ID) references KORREKSJON(ID);

-- add unique constraint to prevent dirty updates
alter table KORREKSJON add constraint UNIQUE_KORR_ID unique(ORIGINAL_ID, FORRIGE_ID);


-- VALGT DISSE BORT til fordel for dobbelt lenket liste -----
--------------------------------------------------------------
-- "version number" for this korreksjon
-- enables unique key constraint on (orig_id, status_number)
-- that ensures two people cannot accidently perform simultaneous editing on the same row.
--	alter table KORREKSJON add STATUS_NUMBER number(7,0) not null;
--
-- set this to the row that replaces the current status.
--  Will provide support for optimistic locking of rows in the database
--  AND support non-modifiability in the application:
--  boolean isModifiable() { return getReplacedById() == null;  }
--	alter table KORREKSJON add REPLACED_BY_ID number(19,0) ;
--------------------------------------------------------------



