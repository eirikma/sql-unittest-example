
--Create new user
CREATE USER korreksjon_maintenance IDENTIFIED BY "korreksjon_maintenance";
GRANT CREATE SESSION TO korreksjon_maintenance;

--Grant privileges (korreksjon tablespace)
ALTER SESSION SET CURRENT_SCHEMA=korreksjon;

GRANT SELECT ON Korreksjon to korreksjon_maintenance;
GRANT SELECT ON Korr_tilstander to korreksjon_maintenance;

GRANT DELETE Korreksjon to korreksjon_maintenance;
GRANT DELETE Korr_tilstander to korreksjon_maintenance;

--Create synonyms (korreksjon_maintenance tablespace)
ALTER SESSION SET CURRENT_SCHEMA=korreksjon_maintenance;

CREATE SYNONYM Korreksjon for korreksjon.Korreksjon;
CREATE SYNONYM Korr_tilstander for korreksjon.Korr_tilstander;

CREATE SYNONYM Schema_version for korreksjon.Schema_version;

---------------------------------------------------------------------------------

--Update schema version (korreksjon tablespace)
ALTER SESSION SET CURRENT_SCHEMA=korreksjon;
INSERT INTO Schema_version(version, migration_date) VALUES (19, (SELECT sysdate FROM dual));


