WHENEVER SQLERROR EXIT SQL.SQLCODE

alter table KORREKSJON disable constraint FK_CURR_TILSTAND;

INSERT INTO KORREKSJON K1 (
    K1.ID  ,
    K1.OBJECT_TYPE ,
    K1.SOURCE  ,
    K1.BESTILLINGSNUMMER  ,
    K1.BESTILLINGSDATO ,
    K1.KORREKSJON_TYPE  ,
    K1.STATUS ,
    K1.VERSION,
    K1.CURRENT_TILSTAND_ID
    )
 SELECT
    K2.ORIGINAL_ID  ,
    'KORREKSJON',
    K2.SOURCE  ,
    K2.BESTILLINGSNUMMER  ,
    K2.BESTILLINGSDATO ,
    K2.KORREKSJON_TYPE  ,
    K2.STATUS,
    1,
    K2.ID
   FROM KORREKSJON_BACKUP_VERSJON_10 K2
   WHERE K2.NESTE_ID IS NULL
    ;

INSERT INTO KORR_TILSTANDER K1   (
    K1.ID                   ,
    K1.KORREKSJON_ID        ,
    K1.SOURCE               ,
    K1.KORREKSJON_TYPE      ,
    K1.BESTILLINGSNUMMER    ,
    K1.BESTILLINGSDATO      ,
    K1.CREATED_AT           ,
    K1.CREATED_BY_USER      ,
    K1.STATUS               ,
    K1.DEBET_ACCOUNT        ,
    K1.CREDIT_ACCOUNT       ,
    K1.DEBET_NAVN               ,
    K1.AMOUNT               ,
    K1.OPPRINNELIG_DATO     ,
    K1.CAUSE                ,
    K1.BLANKETTNUMMER       ,
    K1.FRITEKST             ,
    K1.GEBYR_BANKREGNUMMER  ,
    K1.ARKIVREF             ,
    K1.BESTILLING_STATUS_UPDATE_PATH     ,
    K1.BUNTNUMMER           ,
    K1.KID_NUMMER                  ,
    K1.TRANSTEKST           ,
    K1.NIBE_BUS_KODE        ,
    K1.NIBE_ALC_KODE        ,
    K1.BELOP_KREDIT_SUMPOST ,
    K1.IDX
     )
 SELECT
    K2.ID                   ,
    (SELECT K4.ID FROM KORREKSJON_BACKUP_VERSJON_10 K4
    WHERE K2.BESTILLINGSNUMMER = K4.BESTILLINGSNUMMER AND K4.FORRIGE_ID IS NULL),
    K2.SOURCE               ,
    K2.KORREKSJON_TYPE      ,
    K2.BESTILLINGSNUMMER    ,
    K2.BESTILLINGSDATO      ,
    K2.CREATED_AT           ,
    K2.CREATED_BY_USER      ,
    K2.STATUS               ,
    K2.DEBET_ACCOUNT        ,
    K2.CREDIT_ACCOUNT       ,
    NULL                    ,
    K2.AMOUNT               ,
    K2.OPPRINNELIG_DATO     ,
    K2.CAUSE                ,
    K2.BLANKETTNUMMER       ,
    K2.FRITEKST             ,
    K2.GEBYR_BANKREGNUMMER  ,
    K2.ARKIVREF             ,
    K2.BEST_STATUS_PATH     ,
    K2.BUNTNUMMER           ,
    K2.KID                  ,
    K2.TRANSTEKST           ,
    K2.NIBE_BUS_KODE        ,
    K2.NIBE_ALC_KODE        ,
    K2.BELOP_KREDIT_SUMPOST ,
    (SELECT (COUNT(ID) -1) FROM KORREKSJON_BACKUP_VERSJON_10 K3
    WHERE K2.BESTILLINGSNUMMER = K3.BESTILLINGSNUMMER
     AND  K3.ID <=  K2.ID )
 FROM KORREKSJON_BACKUP_VERSJON_10 K2 ORDER BY K2.ID
    ;

alter table KORREKSJON ENABLE constraint FK_CURR_TILSTAND;

-- Oppdater innholdet i CAUSE - kolonnen
update korr_tilstander set cause='OVERFORING' where cause='Overføring';
update korr_tilstander set cause='KREDIT' where cause='Kredit';
update korr_tilstander set cause='KREDIT_MED_FULLMAKT' where cause='Kredit m/fullmakt';
update korr_tilstander set cause='DEBET' where cause='Debet';
update korr_tilstander set cause='DEBET_MED_FULLMAKT' where cause='Debet m/fullmakt';
update korr_tilstander set cause='DOBBEL_REGISTRERING' where cause='Dobb.reg.';
update korr_tilstander set cause='BELOEP' where cause='Beløp';
update korr_tilstander set cause='FEIL_BANK' where cause='Feil bank';
update korr_tilstander set cause='ANNET' where cause='Annet';

-- Oppdater innholdet i STATUS - kolonnen for KORREKSJON
update korreksjon set status='FERDIGBEHANDLET' where status='Ferdigbehandlet';
update korreksjon set status='KLAR_TIL_VERIFISERING' where status='Klar til verifisering';
update korreksjon set status='KORRIGERT' where status='Korrigert';
update korreksjon set status='SLETTET' where status='Slettet';
update korreksjon set status='KLAR' where status='Klar';
update korreksjon set status='I_ARBEID' where status='I arbeid';
update korreksjon set status='VENTER_FULLMAKT' where status='Venter på fullmakt';

-- Oppdater innholdet i STATUS - kolonnen for KORR_TILSTANDER
update korr_tilstander set status='FERDIGBEHANDLET' where status='Ferdigbehandlet';
update korr_tilstander set status='KLAR_TIL_VERIFISERING' where status='Klar til verifisering';
update korr_tilstander set status='KORRIGERT' where status='Korrigert';
update korr_tilstander set status='SLETTET' where status='Slettet';
update korr_tilstander set status='KLAR' where status='Klar';
update korr_tilstander set status='I_ARBEID' where status='I arbeid';
update korr_tilstander set status='VENTER_FULLMAKT' where status='Venter på fullmakt';

-- Oppdater innholdet i KORREKSJON_TYPE - kolonnen for KORREKSJON og KORR_TILSTANDER
update korreksjon set korreksjon_type='KORREKSJON' where korreksjon_type='Korreksjon';
update korr_tilstander set korreksjon_type='KORREKSJON' where korreksjon_type='Korreksjon';

-- Legg inn nye innstilling: defaults = prod
Insert into PERSISTENT_PROPERTY (KEY,VALUE,SERVER) values ('nibe.hoved.intervalStart','14:30','all');
Insert into PERSISTENT_PROPERTY (KEY,VALUE,SERVER) values ('nibe.hoved.intervalStop','15:25','all');
Insert into PERSISTENT_PROPERTY (KEY,VALUE,SERVER) values ('bbsonlineBestillingServer','http://vm-papp-3-ops:30692/bestilling/korreksjon/ubehandlede','all');
Insert into PERSISTENT_PROPERTY (KEY,VALUE,SERVER) values ('nibe.intervalFrequency','5','all');
Insert into PERSISTENT_PROPERTY (KEY,VALUE,SERVER) values ('nibe.morgen.intervalStart','01:00','all');
Insert into PERSISTENT_PROPERTY (KEY,VALUE,SERVER) values ('nibe.morgen.intervalStop','05:00','all');
Insert into PERSISTENT_PROPERTY (KEY,VALUE,SERVER) values ('testconfiguration.bestillingService','false',null);
INSERT INTO PERSISTENT_PROPERTY (KEY, VALUE, server) VALUES ('arkiv.url', 'https://arkiv.bbs.no/arkiv-rest/bbs/blankett/giro_meldinger/', 'all');
INSERT INTO PERSISTENT_PROPERTY (KEY, VALUE, SERVER) VALUES ('arkiv.keystore.location', 'blankett_181209_075344_11013-EK-1-1155176000_Auth.p12', 'all');
INSERT INTO PERSISTENT_PROPERTY (KEY, VALUE, SERVER) VALUES ('arkiv.keystore.password', 'uok62l1s', 'all');
INSERT INTO PERSISTENT_PROPERTY (KEY, VALUE, SERVER) VALUES ('operatorDetails', 'laila:laila.ronning@nets.eu:97507953,eirik:eirik.maus@nets.eu:40211945,finn:finn.furevik@nets.eu:95157277', 'all');

-- sett inn eller overskriv med environment-spesifikke verdier
@data-&&environment.sql;

insert into SCHEMA_VERSION(VERSION, MIGRATION_DATE) values (13, (select sysdate from dual));
commit;
