-- PREPROD: har ikke FAKT-bruker forelopig

alter session set current_schema = 'KORREKSJON'
grant select, insert on FAKTHENDELSE to KORREKSJON_APPLICATION_ROLE;
grant select, insert on KVANTIFISERT_FAKTHENDELSE to KORREKSJON_APPLICATION_ROLE;
grant select, alter on SEQ_FAKTHENDELSE to KORREKSJON_APPLICATION_ROLE;

alter session set current_schema = 'KORREKSJON_APP'

create or replace synonym KVANTIFISERT_FAKTHENDELSE for KORREKSJON.KVANTIFISERT_FAKTHENDELSE;
create or replace synonym FAKTHENDELSE for KORREKSJON.FAKTHENDELSE;
create or replace synonym SEQ_FAKTHENDELSE for KORREKSJON.SEQ_FAKTHENDELSE;


