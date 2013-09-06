-- i databasen p1bla_db

create user blankett_korreksjon identified by blankett_korreksjon default tablespace users temporary tablespace temp;

grant resource to blankett_korreksjon;
grant create session  to blankett_korreksjon;
grant create synonym to  blankett_korreksjon;

grant select on BLANKETT.BUTIKKAVTALE to blankett_korreksjon;

create or replace synonym BLANKETT_KORREKSJON.BUTIKKAVTALE for BLANKETT.BUTIKKAVTALE;

