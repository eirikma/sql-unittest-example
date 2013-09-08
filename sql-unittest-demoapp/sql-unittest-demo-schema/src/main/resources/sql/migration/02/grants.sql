grant select, alter on KORREKSJON_SEQUENCE to KORREKSJON_APPLICATION_ROLE;
grant select, alter on HILO_SEQUENCE to KORREKSJON_APPLICATION_ROLE;
grant select, alter on HIBERNATE_SEQUENCE to KORREKSJON_APPLICATION_ROLE;
grant select, alter on WORK_ITEM_ID_SEQUENCE to KORREKSJON_APPLICATION_ROLE;
grant select, alter on WORK_QUEUE_ID_SEQUENCE to KORREKSJON_APPLICATION_ROLE;
grant select, alter on WORK_QUEUE_GROUP_ID_SEQUENCE to KORREKSJON_APPLICATION_ROLE;

grant select, update, insert on WORK_ITEM to KORREKSJON_APPLICATION_ROLE;
grant select, update, insert on WORK_QUEUE to KORREKSJON_APPLICATION_ROLE;
grant select, update, insert on WORK_QUEUE_GROUP to KORREKSJON_APPLICATION_ROLE;
grant select, update, insert on FIL to KORREKSJON_APPLICATION_ROLE;
grant select, update, insert on FILCONTENT to KORREKSJON_APPLICATION_ROLE;
grant select, insert on SCHEMA_VERSION_INFO to KORREKSJON_APPLICATION_ROLE;
grant select, update, insert, delete on QOS_DATA to KORREKSJON_APPLICATION_ROLE;
grant select, update, insert on STATUS to KORREKSJON_APPLICATION_ROLE;
grant select, update, insert on STATUS_HISTORY to KORREKSJON_APPLICATION_ROLE;
grant select, update, insert on STATUS_MESSAGE to KORREKSJON_APPLICATION_ROLE;
