

-- menu elements for core-proxy
insert into MENU_ELEMENT (ID,MENU_ID,TITLE,URL,EXERNAL_SERVER,SORTINDEX,RESOURCE_NAME,NAMESPACE,ACTION,PARENT) values (1,1,'Kø','/korreksjon-core-proxy/',null,1,'Ko','no.bbs.blankett.drift','read',null);
insert into MENU_ELEMENT (ID,MENU_ID,TITLE,URL,EXERNAL_SERVER,SORTINDEX,RESOURCE_NAME,NAMESPACE,ACTION,PARENT) values (2,2,'Oversikt','/korreksjon/managedjobs.do?maxCount=100',null,2,'Blankett','no.bbs.blankett.drift','read',null);
insert into MENU_ELEMENT (ID,MENU_ID,TITLE,URL,EXERNAL_SERVER,SORTINDEX,RESOURCE_NAME,NAMESPACE,ACTION,PARENT) values (3,3,'Konfigurasjon','/korreksjon-shared-config/',null,3,'Config','no.bbs.blankett.drift','read',null);
insert into MENU_ELEMENT (ID,MENU_ID,TITLE,URL,EXERNAL_SERVER,SORTINDEX,RESOURCE_NAME,NAMESPACE,ACTION,PARENT) values (4,4,'Helsestatus','/korreksjon/healthcheck.do',null,4,'Blankett','no.bbs.blankett.drift','read',null);

-- configuration for core-proxy and shared-config
insert into persistent_property (key, value) values ('environment', 'P');
insert into persistent_property (key, value) values ('core/proxy/war/environment', 'P');

insert into SCHEMA_VERSION(VERSION, MIGRATION_DATE) values (18, (select sysdate from dual));

commit;
