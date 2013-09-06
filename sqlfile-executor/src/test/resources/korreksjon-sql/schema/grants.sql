
-- role to role grants
grant &korreksjon_select_role to &korreksjon_sysadm_role;

-- user login grants
grant resource to &korreksjon_sysadm_user ;
grant resource to &korreksjon_select_user ;
grant resource to &korreksjon_app_user ;


-- user role grants
grant &korreksjon_select_role to &korreksjon_select_user;
grant &korreksjon_sysadm_role to &korreksjon_sysadm_user;
grant &korreksjon_app_role to &korreksjon_app_user;



-- table  grants

grant select on schema_version to &korreksjon_select_role;
grant select on schema_version_info to &korreksjon_select_role;
grant select on persistent_property to &korreksjon_select_role;

grant select on status_message to &korreksjon_select_role;
grant select on status to &korreksjon_select_role;
grant select on status_history to &korreksjon_select_role;
grant select on qos_data to &korreksjon_select_role;

grant select on fil to &korreksjon_select_role;
grant select on filcontent to &korreksjon_select_role;

grant select on korreksjon to &korreksjon_select_role;
grant select on korr_tilstander to &korreksjon_select_role;

grant select on work_queue to &korreksjon_select_role;
grant select on work_queue_group to &korreksjon_select_role;
grant select on work_item to &korreksjon_select_role;
grant select on work_item to &korreksjon_select_role;
grant select on menuelement_id_sequence to anvisning_application_role;
grant select on menu_element to anvisning_application_role;

grant select on managed_jobs to &korreksjon_select_role;


grant select, insert on SCHEMA_VERSION to KORREKSJON_APPLICATION_ROLE;
grant select, insert on SCHEMA_VERSION_INFO to KORREKSJON_APPLICATION_ROLE;
grant select, update, insert on PERSISTENT_PROPERTY to KORREKSJON_APPLICATION_ROLE;
grant select, insert on PERSISTENT_PROP_ENDRINGSLOGG to KORREKSJON_APPLICATION_ROLE;
grant select, update, insert on KORREKSJON to KORREKSJON_APPLICATION_ROLE;

grant select, update, insert on MANAGED_JOBS to KORREKSJON_APPLICATION_ROLE;
;


grant insert, update, delete on SCHEMA_VERSION to korreksjon_sysadm_role;
grant insert, update, delete on SCHEMA_VERSION_INFO to korreksjon_sysadm_role;

grant insert, update, delete on PERSISTENT_PROPERTY to korreksjon_sysadm_role;
grant insert, update on PERSISTENT_PROP_ENDRINGSLOGG to korreksjon_sysadm_role;

grant insert, update, delete on WORK_QUEUE_GROUP to korreksjon_sysadm_role;
grant insert, update, delete on WORK_QUEUE to korreksjon_sysadm_role;
grant insert, update, delete on WORK_ITEM to korreksjon_sysadm_role;

grant select, update, insert on MANAGED_JOBS to korreksjon_sysadm_role;
-- sequence grants


grant select, alter on KORREKSJON_SEQUENCE to KORREKSJON_APPLICATION_ROLE;
grant select, alter on KORREKSJON_SEQUENCE to KORREKSJON_APPLICATION_ROLE;
grant select, alter on HILO_SEQUENCE to KORREKSJON_APPLICATION_ROLE;
grant select, alter on HIBERNATE_SEQUENCE to KORREKSJON_APPLICATION_ROLE;
grant select, alter on WORK_ITEM_ID_SEQUENCE to KORREKSJON_APPLICATION_ROLE;
grant select, alter on WORK_QUEUE_ID_SEQUENCE to KORREKSJON_APPLICATION_ROLE;
grant select, alter on WORK_QUEUE_GROUP_ID_SEQUENCE to KORREKSJON_APPLICATION_ROLE;
grant select, alter on NIBEUTVEKSLING_SEQUENCE to KORREKSJON_APPLICATION_ROLE;
grant select, alter on BESTILLING_NUMMER_SEQUENCE to KORREKSJON_APPLICATION_ROLE;
grant select on BESTILLING_NUMMER_SEQUENCE to KORREKSJON_READONLY_ROLE;

grant select on menuelement_id_sequence to KORREKSJON_APPLICATION_ROLE;
grant select on menuelement_id_sequence to &korreksjon_select_role;
grant select, alter on menuelement_id_sequence to &korreksjon_sysadm_role;



--------- UT AV BOKSEN FOR BRUKER --------------

grant select, insert on SCHEMA_VERSION to KORREKSJON_APPLICATION_ROLE;
grant select, insert on SCHEMA_VERSION_INFO to KORREKSJON_APPLICATION_ROLE;
grant select, update, insert on PERSISTENT_PROPERTY to KORREKSJON_APPLICATION_ROLE;
grant select, update, insert on KORREKSJON to KORREKSJON_APPLICATION_ROLE;
grant select, update, insert on KORR_TILSTANDER to KORREKSJON_APPLICATION_ROLE;
grant select, insert, update on SCHEMA_VERSION to KORREKSJON_APPLICATION_ROLE;
grant select, insert, update on SCHEMA_VERSION_INFO to KORREKSJON_APPLICATION_ROLE;
grant select, insert, update on PERSISTENT_PROPERTY to KORREKSJON_APPLICATION_ROLE;
grant select, insert, update on WORK_QUEUE_GROUP to KORREKSJON_APPLICATION_ROLE;
grant select, insert, update on WORK_QUEUE to KORREKSJON_APPLICATION_ROLE;
grant select, insert, update on WORK_ITEM to KORREKSJON_APPLICATION_ROLE;
grant select, insert, update on FIL to KORREKSJON_APPLICATION_ROLE;
grant select, insert, update on FILCONTENT to KORREKSJON_APPLICATION_ROLE;
grant select, insert, update on QOS_DATA to KORREKSJON_APPLICATION_ROLE;
grant select, insert, update on STATUS to KORREKSJON_APPLICATION_ROLE;
grant select, insert, update on STATUS_HISTORY to KORREKSJON_APPLICATION_ROLE;
grant select, insert, update on STATUS_MESSAGE to KORREKSJON_APPLICATION_ROLE;

-- sequence grants -- 

grant select, alter on KORREKSJON_SEQUENCE to KORREKSJON_APPLICATION_ROLE;
grant select, alter on KORREKSJON_SEQUENCE to KORREKSJON_APPLICATION_ROLE;
grant select, alter on HILO_SEQUENCE to KORREKSJON_APPLICATION_ROLE;
grant select, alter on HIBERNATE_SEQUENCE to KORREKSJON_APPLICATION_ROLE;
grant select, alter on WORK_ITEM_ID_SEQUENCE to KORREKSJON_APPLICATION_ROLE;
grant select, alter on WORK_QUEUE_ID_SEQUENCE to KORREKSJON_APPLICATION_ROLE;
grant select, alter on WORK_QUEUE_GROUP_ID_SEQUENCE to KORREKSJON_APPLICATION_ROLE;
grant select, alter on NIBEUTVEKSLING_SEQUENCE to KORREKSJON_APPLICATION_ROLE;
grant select, alter on BESTILLING_NUMMER_SEQUENCE to KORREKSJON_APPLICATION_ROLE;
