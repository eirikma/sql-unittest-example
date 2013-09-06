
-- skal kjøres fra schema korreksjon_app

alter session set current_schema=KORREKSJON_APP;

create or replace synonym menu_element for korreksjon.menu_element;
create or replace synonym menuelement_id_sequence for korreksjon.menuelement_id_sequence;
create or replace synonym persistent_prop_endringslogg for korreksjon.persistent_prop_endringslogg;
create or replace synonym persistent_property_group for korreksjon.persistent_property_group;
create or replace synonym pers_prop_endrlogg_id_sequence for korreksjon.pers_prop_endrlogg_id_sequence;
create or replace synonym MANAGED_JOBS for korreksjon.MANAGED_JOBS;

