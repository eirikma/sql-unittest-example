
drop table QOS_DATA cascade constraints;
drop table STATUS_MESSAGE cascade constraints;
drop table STATUS cascade constraints;
drop table STATUS_HISTORY cascade constraints;
drop table FILCONTENT cascade constraints;
drop table FIL cascade constraints;
drop table WORK_ITEM cascade constraints;
drop table WORK_QUEUE cascade constraints;
drop table WORK_QUEUE_GROUP cascade constraints;
drop table SCHEMA_VERSION cascade constraints;
drop table SCHEMA_VERSION_INFO cascade constraints;
drop table PERSISTENT_PROPERTY cascade constraints;

drop table KORR_TILSTANDER cascade constraints;
drop table KORREKSJON cascade constraints;
drop table MANAGED_JOBS cascade constraints;


drop table menu_element cascade constraints;

drop TABLE persistent_property_group cascade constraints;
drop TABLE persistent_prop_endringslogg cascade constraints;

drop sequence BESTILLING_NUMMER_SEQUENCE;
drop sequence NIBEUTVEKSLING_SEQUENCE;
drop sequence work_item_id_sequence;
drop sequence work_queue_id_sequence;
drop sequence work_queue_group_id_sequence;
drop sequence hilo_sequence;
drop sequence hibernate_sequence;
drop sequence menuelement_id_sequence;
drop sequence pers_prop_endrlogg_id_sequence;
