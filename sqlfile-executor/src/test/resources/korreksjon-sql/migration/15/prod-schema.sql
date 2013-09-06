-- Denne skal BARE kjøres i prod (og muligens preprod)

-- todo: grant faktureringsrolle til applikasjonsbrukeren
--  og fakturering readonly til korreksjon-sysadm-brukeren

alter session set current_schema = 'KORREKSJON_APP'


create or replace synonym KVANTIFISERT_FAKTHENDELSE for FAKT.KVANTIFISERT_FAKTHENDELSE;
create or replace synonym for FAKT.FAKTHENDELSE;
create or replace synonym SEQ_FAKTHENDELSE for FAKT.SEQ_FAKTHENDELSE;
