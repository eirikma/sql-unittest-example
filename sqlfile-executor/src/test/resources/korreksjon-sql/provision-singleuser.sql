
-- create the schema for the current user

-- @schema/drop-schema.sql;

@schema/schema.sql;
@schema/data-common.sql;
-- TODO workaround for environment substition gone wrong - user runs this script for ST-testing anyway
-- @schema/data-&&environment.sql;
@schema/data-st.sql;

@schema/external/fakturering/fakt-schema.sql;

commit;
