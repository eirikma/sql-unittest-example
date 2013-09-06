DROP TABLE csm_due_transaction;
CREATE TABLE csm_due_transaction (
  id                           NUMBER(19)        CONSTRAINT nn_due_trans_id NOT NULL,
  message_id                   VARCHAR2(255 CHAR),
  message_name_id              VARCHAR2(35 CHAR),
  message_creation_date_time   TIMESTAMP,
  end_to_end_id                VARCHAR2(255 CHAR),
  transaction_id               VARCHAR2(255 CHAR),
  due_date                     DATE,
  processing_state             VARCHAR2(40 CHAR) CONSTRAINT nn_processing_state NOT NULL,
  amount                       NUMBER(19, 2),
  currency                     VARCHAR2(3 CHAR),
  creditor_account             VARCHAR2(30 CHAR),
  debtor_account               VARCHAR2(30 CHAR),
  source                       VARCHAR2(25 CHAR),
  clearing_target              VARCHAR2(25 CHAR),
  transaction_information      VARCHAR2(4000 CHAR),
  transaction_information_clob CLOB,
  settlement_cycle             NUMBER(19),
  CONSTRAINT pk_due_trans PRIMARY KEY (id)
    USING INDEX (CREATE UNIQUE INDEX ix_due_trans ON csm_due_transaction (id))
)
LOB (transaction_information_clob) STORE AS SECUREFILE (
CACHE
)
PARTITION BY LIST (processing_state) (
  PARTITION pactive
    VALUES ('WAITING_FOR_DUE_DATE', 'SETTLEMENT_IN_PROGRESS'),
  PARTITION pdone
    VALUES ('REJECTED', 'CANCELLED', 'SETTLED')
) ENABLE ROW MOVEMENT;


DROP TABLE csm_modified_due_transaction;
CREATE TABLE csm_modified_due_transaction (
  id                           NUMBER(19) CONSTRAINT nn_modified_due_trans_id NOT NULL,
  due_transaction_id           NUMBER(19),
  modification_time            TIMESTAMP,
  due_date                     DATE,
  amount                       NUMBER(19, 2),
  currency                     VARCHAR2(3 CHAR),
  creditor_account             VARCHAR2(30 CHAR),
  debtor_account               VARCHAR2(30 CHAR),
  source                       VARCHAR2(25 CHAR),
  transaction_information      VARCHAR2(4000 CHAR),
  transaction_information_clob CLOB,
  CONSTRAINT pk_modified_due_trans PRIMARY KEY (id)
    USING INDEX (CREATE UNIQUE INDEX ix_modified_due_trans_pk ON csm_modified_due_transaction (id))
)
LOB (transaction_information_clob) STORE AS SECUREFILE (
CACHE
);


DROP TABLE csm_nics_settlement_tx;
CREATE TABLE csm_nics_settlement_tx (
  id                      NUMBER(19) CONSTRAINT nn_csm_nics_settlement_tx_id NOT NULL,
  due_transaction         NUMBER(19),
  settlement_cycle        NUMBER(19),
  settlement_date         DATE,
  status                  VARCHAR2(40 CHAR),
  status_reason           VARCHAR2(255 CHAR),
  interchange_reference   VARCHAR2(40 CHAR),
  dko_reservation         NUMBER(19),
  debit_alc               CHAR(3 CHAR),
  credit_alc              CHAR(3 CHAR),
  kid                     VARCHAR2(25 CHAR),
  batch_booking_reference VARCHAR2(35 CHAR),
  remittance_status_xml VARCHAR2(4000 CHAR),
  remittance_status_lob CLOB,
  CONSTRAINT pk_csm_nics_settlement_tx PRIMARY KEY (id)
    USING INDEX (
      CREATE UNIQUE INDEX ix_csm_nics_settlement_id ON csm_nics_settlement_tx (id))
)
PARTITION BY RANGE (settlement_date)
(
PARTITION P&SHORT_DATE. VALUES LESS THAN (TO_DATE('&SHORT_DATE. 23:59:59', 'YYYYMMDD HH24:MI:SS'))
);
