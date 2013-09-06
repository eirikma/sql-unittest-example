
-- readonly user
create role korreksjon_select_role;

-- sysadm: readonly privileges on data tables + additional grants on configuration tables
create role korreksjon_sysadm_role;
grant korreksjon_select_role to korreksjon_sysadm_role;


-- create select user

create user korreksjon_select_user identified by korreksjon_select_user
  default tablespace users
  temporary tablespace temp ;

grant create synonym to korreksjon_select_user;
grant create session to korreksjon_select_user;
grant resource to korreksjon_select_user;

grant korreksjon_select_role to korreksjon_select_user;


-- create sysadm user

create user korreksjon_sysadm_user identified by korreksjon_sysadm_user
  default tablespace users
  temporary tablespace temp ;


grant create synonym to korreksjon_sysadm_user;
grant create session to korreksjon_sysadm_user;
grant resource to korreksjon_sysadm_user;

grant korreksjon_select_role to korreksjon_sysadm_user;
grant korreksjon_sysadm_role to korreksjon_sysadm_user;
