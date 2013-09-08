-- create user bkad-korreksjon for select of various items

create user bkad_korreksjon identified by bkad_korreksjon
default tablespece users temporary tablespace temp;

grant resource to bkad_korreksjon;
grant create session  to bkad_korreksjon;
grant create synonym to  bkad_korreksjon;

grant select on BKAD.TBABANK_BANK to bkad_korreksjon;
grant select on BKAD.TCUROLE_CUST_ROLE to bkad_korreksjon;
grant select on BKAD.TAGAGDE_AGR_DET to bkad_korreksjon;
grant select on BKAD.TAGCUAG_CUST_AGR to bkad_korreksjon;
grant select on BKAD.TCUCUST_CUSTOMER to bkad_korreksjon;
grant select on BKAD.TCUADRS_ADDRESS to bkad_korreksjon;
grant select on BKAD.TCUPADR_POSTAL_ADR to bkad_korreksjon;

create or replace synonym BKAD_KORREKSJON.TBABANK_BANK for BKAD.TBABANK_BANK;
create or replace synonym BKAD_KORREKSJON.TCUROLE_CUST_ROLE for BKAD.TCUROLE_CUST_ROLE;
create or replace synonym BKAD_KORREKSJON.TAGAGDE_AGR_DET for BKAD.TAGAGDE_AGR_DET;
create or replace synonym BKAD_KORREKSJON.TAGCUAG_CUST_AGR for BKAD.TAGCUAG_CUST_AGR;
create or replace synonym BKAD_KORREKSJON.TCUCUST_CUSTOMER for BKAD.TCUCUST_CUSTOMER;
create or replace synonym BKAD_KORREKSJON.TCUADRS_ADDRESS for BKAD.TCUADRS_ADDRESS;
create or replace synonym BKAD_KORREKSJON.TCUPADR_POSTAL_ADR for BKAD.TCUPADR_POSTAL_ADR;

-- TODO Are more required?
-- grant select on BKAD.?? to bkad_korreksjon;
