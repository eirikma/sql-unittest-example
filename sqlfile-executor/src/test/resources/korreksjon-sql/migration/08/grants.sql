
-- grant permissions to select user and sysadm user


-- select user can read from all tables
grant select on FIL to korreksjon_select_role;
grant select on FILCONTENT to korreksjon_select_role;
grant select on KORREKSJON to korreksjon_select_role;
grant select on PERSISTENT_PROPERTY to korreksjon_select_role;
grant select on PERSISTENT_PROP_ENDRINGSLOGG to korreksjon_select_role;
grant select on QOS_DATA to korreksjon_select_role;
grant select on SCHEMA_VERSION to korreksjon_select_role;
grant select on SCHEMA_VERSION_INFO to korreksjon_select_role;
grant select on SEQUENCE_NUMBER to korreksjon_select_role;
grant select on STATUS to korreksjon_select_role;
grant select on STATUS_HISTORY to korreksjon_select_role;
grant select on STATUS_MESSAGE to korreksjon_select_role;
grant select on WORK_ITEM to korreksjon_select_role;
grant select on WORK_QUEUE to korreksjon_select_role;
grant select on WORK_QUEUE_GROUP to korreksjon_select_role;


-- sysadm can not modify business data, but can control the
-- setup and the execution of tasks and items
grant insert, update, delete on SCHEMA_VERSION to korreksjon_sysadm_role;
grant insert, update, delete on SCHEMA_VERSION_INFO to korreksjon_sysadm_role;

grant insert, update, delete on PERSISTENT_PROPERTY to korreksjon_sysadm_role;
grant insert, update on PERSISTENT_PROP_ENDRINGSLOGG to korreksjon_sysadm_role;

grant insert, update, delete on WORK_QUEUE_GROUP to korreksjon_sysadm_role;
grant insert, update, delete on WORK_QUEUE to korreksjon_sysadm_role;
grant insert, update, delete on WORK_ITEM to korreksjon_sysadm_role;


