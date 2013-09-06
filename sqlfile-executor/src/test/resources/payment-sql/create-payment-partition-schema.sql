-- Partition setup;

-- Telepay;

DROP TABLE telepay_record;
CREATE TABLE telepay_record(
  id NUMBER(19, 0) CONSTRAINT nn_telepay_record NOT NULL,
  created_date DATE NOT NULL,
  line_number NUMBER(10, 0),
  interchange NUMBER(19, 0),
  transactioncode varchar2(8 CHAR),
  procedure_id varchar2(4 CHAR),
  telepay_line varchar2(400 CHAR),
  clearing_date DATE,
  belop NUMBER(19, 2),
  transaksjon_belop_kode  varchar2(60),
  return_codes_r1 varchar2(3999 CHAR),
  return_codes_r2 varchar2(3999 CHAR),
  rejected NUMBER(1) DEFAULT 0,
  reference_number varchar2(6 CHAR),
  serial_number NUMBER(19, 0),
  invoice_reference varchar2(35 CHAR),
  external_sequence_control varchar2(4 CHAR),
  external_divisjon varchar2(20 CHAR),
  forwarded_batch NUMBER(19, 0),
  ready_for_r1 NUMBER(1) DEFAULT 0,
  ready_for_r2 NUMBER(1) DEFAULT 0,
  r2_generated NUMBER(1) DEFAULT 0,
  telepay_record_status varchar2(30 CHAR),
  foretaksnummer varchar2(30 CHAR),
  divisjon varchar2(20 CHAR),
  debet_kontonummer varchar2(20 CHAR),
  transaction_date varchar2(4 CHAR),
  cancellation_code varchar2(1 CHAR),
  instruction_type varchar2(32 CHAR),
  end_to_end_id varchar2(35 CHAR),
  pmt_inf_id varchar2(35 CHAR),
  assignment_number varchar2(35 CHAR),
  transaction_type varchar2(1 CHAR),
  kid varchar2(30 CHAR),
  csm_interchange number(19, 0),
  CONSTRAINT pk_telepay_record PRIMARY KEY(id) USING index (
    CREATE UNIQUE index ix_telepay_record_pk ON telepay_record(id)
  )
)
partition BY range(created_date)
INTERVAL (NUMTODSINTERVAL(1,'day'))
(
  PARTITION p_first VALUES LESS THAN (TO_DATE('01-01-2013', 'DD-MM-YYYY'))
);

-- Dirrem;

DROP TABLE dirrem_record;
CREATE TABLE dirrem_record (
  id NUMBER(19, 0) CONSTRAINT dirrem_record_id NOT NULL,
  created_date DATE,
  record_type varchar2(20),
  interchange NUMBER(19, 0),
  rejected NUMBER(1, 0),
  line_number NUMBER(19, 0),
  content varchar2(200),
  error_codes varchar2(200),
  cancellation_code varchar2(100),
  transmission_number    VARCHAR2(10),
  assignment_number    VARCHAR2(10),
  transaction_number       VARCHAR2(10),
  assignment_account     VARCHAR2(11),
  agreement_id         VARCHAR2(9),
  data_sender      VARCHAR2(8),
  end_to_end_id     VARCHAR2(35 CHAR),
  payment_inf_id    VARCHAR2(35 CHAR),
  CONSTRAINT dirrem_record PRIMARY KEY(id) USING index (
    CREATE UNIQUE index ix_dirrem_record_pk ON dirrem_record(id)
  )
)
partition BY range(created_date)
INTERVAL (NUMTODSINTERVAL(1,'day'))
(
  PARTITION p_first VALUES LESS THAN (TO_DATE('01-01-2013', 'DD-MM-YYYY'))
);
