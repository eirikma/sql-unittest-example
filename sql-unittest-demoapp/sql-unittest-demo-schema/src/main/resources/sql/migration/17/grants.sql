


grant select, update, insert on menu_element to KORREKSJON_APPLICATION_ROLE;
grant select, update, insert on menu_element to korreksjon_sysadm_role;
grant select on menu_element to korreksjon_select_role;

grant select, alter on menuelement_id_sequence to korreksjon_select_role;
grant select, alter on menuelement_id_sequence to KORREKSJON_APPLICATION_ROLE;



grant select, update, insert on persistent_prop_endringslogg to KORREKSJON_APPLICATION_ROLE;
grant select, update, insert on persistent_prop_endringslogg to korreksjon_sysadm_role;
grant select on persistent_prop_endringslogg to korreksjon_select_role;

grant select, update, insert on persistent_property_group to KORREKSJON_APPLICATION_ROLE;
grant select, update, insert on persistent_property_group to korreksjon_sysadm_role;
grant select on persistent_property_group to korreksjon_select_role;

grant select, alter on pers_prop_endrlogg_id_sequence to korreksjon_select_role;
grant select, alter on pers_prop_endrlogg_id_sequence to KORREKSJON_APPLICATION_ROLE;


grant select, update, insert on MANAGED_JOBS to KORREKSJON_APPLICATION_ROLE;
grant select, update, insert on MANAGED_JOBS to korreksjon_sysadm_role;
grant select on managed_jobs to korreksjon_select_role;


