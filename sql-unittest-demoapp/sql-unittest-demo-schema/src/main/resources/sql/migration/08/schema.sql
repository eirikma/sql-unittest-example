

alter table KORREKSJON add SOURCE varchar2(255 char) default 'KORREKSJON_WEB' not null ;
alter table KORREKSJON add BEST_STATUS_PATH varchar2(255 char);
alter table KORREKSJON add BUNTNUMMER varchar2(255 char);
alter table KORREKSJON add KID varchar2(255 char);
alter table KORREKSJON add TRANSTEKST varchar2(255 char);
alter table KORREKSJON add NIBE_BUS_KODE varchar2(15 char);
alter table KORREKSJON add NIBE_ALC_KODE varchar2(15 char);
alter table KORREKSJON add BELOP_KREDIT_SUMPOST number(19,2);


