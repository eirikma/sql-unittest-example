-- grant access to tbabank_bank for user bkad-korreksjon

grant select on bkad.tbabank_bank to bkad_korreksjon;
create or replace synonym bkad_korreksjon.tbabank_bank for bkad.tbabank_bank;
