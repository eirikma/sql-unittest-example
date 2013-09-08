-- connect as korreksjon
grant select, update, insert on KORR_TILSTANDER to KORREKSJON_APPLICATION_ROLE;
grant select, update, insert on KORREKSJON to KORREKSJON_APPLICATION_ROLE;
grant select on KORREKSJON to korreksjon_select_role;
grant select on KORR_TILSTANDER to korreksjon_select_role;

grant select on schema_version to korreksjon_select_role;
grant select, insert on SCHEMA_VERSION_INFO to KORREKSJON_APPLICATION_ROLE;
