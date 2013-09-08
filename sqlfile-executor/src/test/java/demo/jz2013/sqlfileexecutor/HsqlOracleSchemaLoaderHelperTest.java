package demo.jz2013.sqlfileexecutor;


import static junit.framework.Assert.assertTrue;

import javax.sql.DataSource;
import java.io.File;
import java.net.URL;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;


/**
 * OracleSqlFileExecutor Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>11/24/2010</pre>
 */
public class HsqlOracleSchemaLoaderHelperTest {

    private URL SCHEMA_FILE_URL;

    private DataSource h2DataSource;
    private DataSource hsqlDataSource;


    @Before
    public void setUp() throws Exception {
        SCHEMA_FILE_URL = new File("src/test/resources/korreksjon-sql/provision-singleuser.sql").toURI().toURL();

        if (h2DataSource == null) {
            h2DataSource = new SingleConnectionDataSource("jdbc:h2:mem:" + getClass().getSimpleName(),
                    "sa", "", true);
        }
        if (hsqlDataSource == null) {
            hsqlDataSource = new SingleConnectionDataSource("jdbc:hsqldb:mem:" + getClass().getSimpleName(),
                    "sa", "", true);
        }
    }

    @After
    public void tearDown() throws Exception {
    }


    @Test
    public void ensureOracleToHsqlSeemsToWork() throws Exception {
        OracleSqlFileExecutor sqlFileExecutor = new OracleSqlFileExecutor();
        sqlFileExecutor.setOracleSqlVariable("environment", "utv");
        sqlFileExecutor.setSchemaUrl(getSqlDirUrl());

        Map<String, Exception> exceptionMap = sqlFileExecutor.ensureSchemaIsPresent(hsqlDataSource);

        for (String failedSql : exceptionMap.keySet()) {
            assertTrue("statement other than drop of nonexistent resource failed: " + failedSql,
                       failedSql.toLowerCase().trim().startsWith("drop "));
        }

    }

    @Test
    public void ensureOracleToH2databaseSeemsToWork() throws Exception {
        OracleSqlFileExecutor sqlFileExecutor = new OracleSqlFileExecutor();
        sqlFileExecutor.setOracleSqlVariable("environment", "utv");
        sqlFileExecutor.setSchemaUrl(getSqlDirUrl());

        Map<String, Exception> exceptionMap = sqlFileExecutor.ensureSchemaIsPresent(h2DataSource);

        for (String failedSql : exceptionMap.keySet()) {
            assertTrue("statement other than drop of nonexistent resource failed: " + failedSql,
                       failedSql.toLowerCase().trim().startsWith("drop "));
        }

    }


    private  String getSqlDirUrl() {
        return SCHEMA_FILE_URL.toExternalForm();
    }


}
