package demo.jz2013.korreksjon.schema;

import demo.jz2013.sqlfileexecutor.OracleSqlFileExecutor;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
import org.hsqldb.jdbc.JDBCDriver;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.io.File;
import java.net.URL;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;

/**
 * Test class to verify that sql scripts are reasonably complete.
 * will only run the scripts against
 * <p/>
 */
public class SqlSchemaScriptsTest {

    private static HsqlTestDataSource migrationDataSource;
    private static HsqlTestDataSource schemaDataSource;
    private static HsqlTestDataSource maintenanceDataSource;
    private static String origHbm2ddl;

    public static class HsqlTestDataSource extends DriverManagerDataSource {
        public HsqlTestDataSource(String catalog) {
            super("jdbc:hsqldb:mem:" + catalog, "sa", "");
            setDriverClassName(JDBCDriver.class.getName());
        }

        public String getDriverClassName() {
            return JDBCDriver.class.getName();
        }

        public Logger getParentLogger() {
            return Logger.getLogger("hva.bryr.det.deg.egentlig");
        }
    }





    @BeforeClass
	public static void setUpBefore() throws Exception {
		migrationDataSource = new HsqlTestDataSource("migrations");
		schemaDataSource = new HsqlTestDataSource("schema");
		maintenanceDataSource = new HsqlTestDataSource("maintenance");

		createMigrationsSchemaIfNotExists();
		createDefinedSchemaIfNotExists();
		//--backup hibernate prop
		origHbm2ddl = System.getProperty("hibernate.hbm2ddl.auto", "validate");
		System.setProperty("hibernate.hbm2ddl.auto", "validate");
	}

    @AfterClass
    public static void tearDown() throws Exception {
        //--restore original hibernate property
        System.setProperty("hibernate.hbm2ddl.auto", origHbm2ddl);
    }

    @Test
    public void ensureMigratedSchemaCanStartApplication() throws Exception {
//        bindDataSourceInJndi("jdbc/primaryDs", migrationDataSource);
//        bindDataSourceInJndi("jdbc/bkadDs", migrationDataSource);
//        bindDataSourceInJndi("jdbc/blankettDs", migrationDataSource);
//        bindDataSourceInJndi("jdbc/maintenanceDs", maintenanceDataSource);
//
//        ClassPathXmlApplicationContext appCtx = new ClassPathXmlApplicationContext(new String[]{
//                "moduleContext-korreksjon.xml",
//                "applicationContext-korreksjon.xml",
//                "applicationContext-korreksjon-test-overrides.xml",
//        });
//
//        appCtx.start();
//
//        EntityRepository entityRepository = (EntityRepository) appCtx.getBean("entityRepository");
//        assertNotNull("entityRepository", entityRepository);
//        assertEquals("expected empty database", 0, entityRepository.count(new TypeHibernateFinderSpecification(Korreksjon.class)));
//
//        appCtx.stop();
    }

    @Test
    public void ensureDefinedSchemaCanRunApplication() throws Exception {
//        bindDataSourceInJndi("jdbc/primaryDs", schemaDataSource);
//        bindDataSourceInJndi("jdbc/bkadDs", schemaDataSource);
//        bindDataSourceInJndi("jdbc/blankettDs", schemaDataSource);
//        bindDataSourceInJndi("jdbc/maintenanceDs", maintenanceDataSource);
//
//        ClassPathXmlApplicationContext appCtx = new ClassPathXmlApplicationContext(new String[]{
//                "moduleContext-korreksjon.xml",
//                "applicationContext-korreksjon.xml",
//                "applicationContext-korreksjon-test-overrides.xml",
//        });
//
//        appCtx.start();
//
//        EntityRepository entityRepository = (EntityRepository) appCtx.getBean("entityRepository");
//        assertNotNull("entityRepository", entityRepository);
//
//        assertEquals("expected empty database", 0, entityRepository.count(new TypeHibernateFinderSpecification(Korreksjon.class)));
//        appCtx.stop();
    }

    @Test
    public void ensureDefinedSchemaIsIdenticalToMigrations() throws Exception {
        try {
            JdbcTemplate migrations = new JdbcTemplate(migrationDataSource);
            List<Map<String, Object>> mtables = migrations.queryForList("SELECT * FROM INFORMATION_SCHEMA.TABLES "
                    + "WHERE TABLE_SCHEMA = 'PUBLIC' "
                    + "ORDER BY TABLE_NAME ");

            List<Map<String, Object>> mcolumns = migrations.queryForList(
                    "SELECT * FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = 'PUBLIC'"
                            + " ORDER BY TABLE_NAME, COLUMN_NAME ");


            JdbcTemplate schema = new JdbcTemplate(schemaDataSource);
            List<Map<String, Object>> stables = schema.queryForList("SELECT * FROM INFORMATION_SCHEMA.TABLES "
                    + "WHERE TABLE_SCHEMA = 'PUBLIC' "
                    + "ORDER BY TABLE_NAME ");

            List<Map<String, Object>> scolumns = schema.queryForList(
                    "SELECT * FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = 'PUBLIC'"
                            + " ORDER BY TABLE_NAME, COLUMN_NAME ");


            Transformer tableNameExtractor = new SchemaFeatureExtractor("TABLE_NAME");
            assertEquals("Not the same set of tables (expected = fixed schema)",
                    CollectionUtils.collect(stables, tableNameExtractor),
                    removeBackupObjects(CollectionUtils.collect(mtables, tableNameExtractor)));

            Transformer columnNameExtractor = new SchemaFeatureExtractor("TABLE_NAME", "COLUMN_NAME");
            assertEquals("Not the same set of columns (expected = fixed schema)",
                    CollectionUtils.collect(scolumns, columnNameExtractor),
                    removeBackupObjects(CollectionUtils.collect(mcolumns, columnNameExtractor)));

            Transformer columnTypeExtractor = new SchemaFeatureExtractor("TABLE_NAME", "COLUMN_NAME", "DATA_TYPE");
            assertEquals("Not the same set of columns types (expected = fixed schema)",
                    CollectionUtils.collect(scolumns, columnTypeExtractor),
                    removeBackupObjects((CollectionUtils.collect(mcolumns, columnTypeExtractor))));


            Transformer sequenceExtractor = new SchemaFeatureExtractor("SEQUENCE_NAME");
            List<Map<String, Object>> msequences = migrations.queryForList(
                    "SELECT * FROM INFORMATION_SCHEMA.SEQUENCES "
                            + "WHERE SEQUENCE_SCHEMA = 'PUBLIC'"
                            + " ORDER BY SEQUENCE_NAME ");
            List<Map<String, Object>> ssequences = schema.queryForList(
                    "SELECT * FROM INFORMATION_SCHEMA.SEQUENCES "
                            + "WHERE SEQUENCE_SCHEMA = 'PUBLIC'"
                            + " ORDER BY SEQUENCE_NAME ");

            Collection<String> sSeqList = CollectionUtils.collect(ssequences, sequenceExtractor);
            Collection<String> mSeqList = CollectionUtils.collect(msequences, sequenceExtractor);
            assertEquals("Not the same set of sequences (expected = fixed schema)", sSeqList, mSeqList);

        } catch (DataAccessException e) {
            boolean breakpoint = true;
        }
    }

    private static void createDefinedSchemaIfNotExists() throws Exception {
            JdbcTemplate schema = new JdbcTemplate(schemaDataSource);
            List<Map<String, Object>> mtables = schema.queryForList("SELECT * FROM INFORMATION_SCHEMA.TABLES "
                    + "WHERE TABLE_SCHEMA = 'PUBLIC' "
                    + "ORDER BY TABLE_NAME ");
            assertEquals("expected no tables before test", 0, mtables.size());

            OracleSqlFileExecutor sqlFileExecutor = new OracleSqlFileExecutor();
            sqlFileExecutor.setOracleSqlVariable("environment", "utv");
            sqlFileExecutor.setThrowSqlExceptions(true);
            Map<String, Exception> exceptionMap = sqlFileExecutor.installDatabaseSchema(schemaDataSource, new URL[]{new URL(getSqlDirUrl() + "provision-singleuser.sql")});

            assertCreateStatementsHaventFailed(exceptionMap);
            assertInsertStatementsHaventFailed(exceptionMap);
    }

    private static void createMigrationsSchemaIfNotExists() throws Exception {
            JdbcTemplate migrations = new JdbcTemplate(migrationDataSource);
            List<Map<String, Object>> mtables = migrations.queryForList("select * from INFORMATION_SCHEMA.TABLES "
            														  + "where TABLE_SCHEMA = 'PUBLIC' "
            														  + "order by TABLE_NAME ");
            assertEquals("expected no tables before test (in-memory db must be empty)", 0, mtables.size());

            OracleSqlFileExecutor sqlFileExecutor = new OracleSqlFileExecutor();
            sqlFileExecutor.setOracleSqlVariable("environment", "utv");
             sqlFileExecutor.setIgnoreExceptionsOnDropStatements(true);

            // this file will set up a database by applying all migrations
            Map<String, Exception> exceptionMap = sqlFileExecutor.installDatabaseSchema(migrationDataSource, new URL[]{new URL(getSqlDirUrl() + "provision-st-all-migrations.sql")});

            assertCreateStatementsHaventFailed(exceptionMap);
            assertInsertStatementsHaventFailed(exceptionMap);
    }

    private Collection<String> removeBackupObjects(Collection<String> mTableList) {
        Collection<String> ignoreList = new LinkedList<String>();
        for (String tableName : mTableList) {
            if (tableName.toLowerCase().contains("_backup_")) {
                ignoreList.add(tableName);
            }
        }
        mTableList.removeAll(ignoreList);
        return mTableList;
    }


    private static void assertInsertStatementsHaventFailed(Map<String, Exception> exceptionMap) {
        assertStatementsOfTypeHaventFailed("insert ", exceptionMap);
    }

    private static void assertCreateStatementsHaventFailed(Map<String, Exception> exceptionMap) {
        assertStatementsOfTypeHaventFailed("create ", exceptionMap);
    }

    private static void assertStatementsOfTypeHaventFailed(String statementType, Map<String, Exception> exceptionMap) {
        for (String key : exceptionMap.keySet()) {
        	String executedStatement = key.substring(key.indexOf(":") + 1); //--the key contains a kind of sql script row index such as "nnn: sql_statement"
            if (executedStatement.trim().toLowerCase().startsWith(statementType)) {
                fail("create statement failed: " + key + " : " + exceptionMap.get(key).toString());
            }
        }
    }


    private static String getSqlDirUrl() {
        return new File("./src/main/resources/sql/").toURI().toASCIIString();
    }

//    private void bindDataSourceInJndi(String jndiNameToBindTo, DataSource dataSourceToBind) {
//        if (System.getProperty(Context.INITIAL_CONTEXT_FACTORY) == null) {
//            try {
//                Class.forName("org.mortbay.naming.NamingContext");
//                // Jetty naming is available, we use that
//            } catch (ClassNotFoundException e) {
//                System.setProperty(Context.INITIAL_CONTEXT_FACTORY, SimpleJndiContextFactory.class.getName());
//            }
//        }
//
//        String jndiName = "java:comp/env/" + jndiNameToBindTo;
//        try {
//            Context context = new InitialContext();
//            Name name = context.getNameParser(jndiName).parse(jndiName);
//
//            for (int i = 0; i < name.size() - 1; i++) {
//                try {
//                    context = (Context) context.lookup(name.get(i));
//                } catch (NamingException e) {
//                    context = context.createSubcontext(name.get(i));
//                }
//            }
//            new InitialContext().rebind(jndiName, dataSourceToBind);
//        } catch (NamingException e) {
//            System.err.println("Warning: failed to bind resource " + jndiName);
//            e.printStackTrace();
//        }
//
//    }

    /**
     * hjelpeklasse for å hente ut data fra hver rad i metadata-tabellene.
     */
    private static class SchemaFeatureExtractor implements Transformer {
        private String[] featureNames;

        private SchemaFeatureExtractor(String... featureNames) {
            this.featureNames = featureNames;
        }

        @Override
        public Object transform(Object input) {
            // row:  map  column_name -> value
            Map<String, Object> row = (Map<String, Object>) input;
            // join values from specified columns to a dot-separated string
            StringBuilder sb = new StringBuilder();
            for (String featureName : featureNames) {
                sb.append('.').append(row.get(featureName));
            }
            return sb.toString().substring(1);
        }
    }
}
