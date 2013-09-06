package demo.jz2013.sqlfileexecutor;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.ReflectionUtils;

/**
 * Utility to execute sql-files intended for oracle sqlplus, with options for
 * translating the statements to hsqldb sql-syntax on the fly.
 * Procedural sql is not supported, but defines and invoking other scripts with @directory/script.sql; will work.
 *
 * @author Eirik Maus
 */
public class OracleSqlFileExecutor {


    private static final String SCHEMA_URL_PROPERTY = "bbs.korreksjon.schema-url";

    static final String CHECK_FOR_SCHEMA_SQL = "select count(*) from korreksjon";


    private String schemaUrl = getDefaultSchemaUrl();
    private String checkForSchemaSql = CHECK_FOR_SCHEMA_SQL;
    private final HashMap<String, String> variables = new HashMap<String, String>();

    // log flags
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private boolean logSelectStatements = false;
    private boolean logInsertStatements = false;
    private boolean logUpdateStatements = false;
    private boolean logDeleteStatements = false;
    private boolean logDropStatements = false;
    private boolean logCreateStatements = false;
    private boolean logAlterStatements = false;
    private boolean throwSqlExceptions = true;
    private boolean ignoreExceptionsOnDropStatements;

    public OracleSqlFileExecutor(String schemaUrl) {
        setSchemaUrl(schemaUrl);
    }

    public OracleSqlFileExecutor() {
        this("./sql/setup.sql");
    }

    public void setOracleSqlVariable(String varName, String value) {
        this.variables.put(varName, value);
    }

    public void setLogSelectStatements(boolean logSelectStatements) {
        this.logSelectStatements = logSelectStatements;
    }

    public void setLogInsertStatements(boolean logInsertStatements) {
        this.logInsertStatements = logInsertStatements;
    }

    public void setLogUpdateStatements(boolean logUpdateStatements) {
        this.logUpdateStatements = logUpdateStatements;
    }

    public void setLogDeleteStatements(boolean logDeleteStatements) {
        this.logDeleteStatements = logDeleteStatements;
    }

    public void setLogDropStatements(boolean logDropStatements) {
        this.logDropStatements = logDropStatements;
    }

    public void setLogCreateStatements(boolean logCreateStatements) {
        this.logCreateStatements = logCreateStatements;
    }

    public void setThrowSqlExceptions(boolean throwSqlExceptions) {
        this.throwSqlExceptions = throwSqlExceptions;
    }

    public void setSchemaUrl(String schemaUrl) {
        this.schemaUrl = schemaUrl;
    }

    public void setLogAlterStatements(boolean logAlterStatements) {
        this.logAlterStatements = logAlterStatements;
    }

    private static String getDefaultSchemaUrl() {
        return System.getProperty(SCHEMA_URL_PROPERTY, "./main/sql/setup.sql");
    }

    public void setCheckForSchemaSql(String checkForSchemaSql) {
        this.checkForSchemaSql = checkForSchemaSql;
    }

    public void setIgnoreExceptionsOnDropStatements(boolean ignoreExceptionsOnDropStatements) {
        this.ignoreExceptionsOnDropStatements = ignoreExceptionsOnDropStatements;
    }

    public Map<String, Exception> runSql(DataSource dataSource) throws Exception {
        return reInstallDatabaseSchema(dataSource);
    }

    public Map<String, Exception> ensureSchemaIsPresent(DataSource dataSource) throws Exception {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        try {
            jdbcTemplate.queryForInt(checkForSchemaSql);
        } catch (DataAccessException e) {
            return reInstallDatabaseSchema(dataSource);
        }
        // verify that this doesn't throw exception
        // (or else: let the exception pass through as a test error)
        jdbcTemplate.queryForInt(checkForSchemaSql);
        return Collections.emptyMap();
    }


    public Map<String, Exception> reInstallDatabaseSchema(DataSource dataSource) throws Exception {
        URL url = new URL(schemaUrl);
        return installDatabaseSchema(dataSource, new URL[]{url});
    }


    public Map<String, Exception> installDatabaseSchema(DataSource dataSource, URL[] sqlResources)
            throws Exception {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        String database = findDatabaseTypeFromDatasourceUrl(dataSource);
        Map<String, Exception> exceptions = new HashMap<String, Exception>();
        for (URL sqlResource : sqlResources) {
            String sqlFileAsStringFromUrl = getSqlFileAsStringFromUrl(sqlResource);
            List<String> ddlStatements = splitSqlStatementsAndInlineScripts(sqlResource, sqlFileAsStringFromUrl);
            if (database.contains("hsql") || database.contains("h2")) {
                for (int j = 0; j < ddlStatements.size(); j++) {
                    final String oracleSql = ddlStatements.get(j);
                    final String hsqldbSql = OracleToHsqlSqlTranslator.translateOracleToHsqlDdl(oracleSql);
                    ddlStatements.set(j, hsqldbSql);
                }
            }
            exceptions.putAll(executeDdlStatements(jdbcTemplate, ddlStatements));
        }
        return exceptions;
    }

    private static String getSqlFileAsStringFromUrl(URL resource) throws IOException {
        InputStream inputStream = resource.openStream();
        Validate.notNull(inputStream, "No input stream found for resource URL: " + resource
                                      + ". Classloader returned 'null'");
        return IOUtils.toString(inputStream);
    }

    private static String findDatabaseTypeFromDatasourceUrl(DataSource dataSource) {
        String database = "oracle";
        try {
            // virtually all jdbc DataSource implementations have a getUrl() method.
            Method method = ReflectionUtils.findMethod(dataSource.getClass(), "getUrl");
            if (method != null) {
                method.setAccessible(true);
                String result = (String) method.invoke(dataSource);
                if (result != null && result.startsWith("jdbc:")) {
                    database = result.substring(5, result.indexOf(':', 7));
                }
            }
        } catch (Exception e) {
            //ignored
        }
        return database;
    }

    private List<String> splitSqlStatementsAndInlineScripts(URL resourceUrl, String sqlStringRaw)
            throws URISyntaxException, IOException {
        String sqlString = fixWindowsNewlines(sqlStringRaw);
        sqlString = removeSqlMultilineComments(sqlString);
        sqlString = removeSqlLineComments(sqlString);
        sqlString = removeNewLines(sqlString);
        sqlString = truncateWhitespace(sqlString);
        ArrayList<String> ddlStatements = new ArrayList<String>(Arrays.asList(splitIntoSqlStatements(sqlString)));
        for (int i = 0; i < ddlStatements.size(); i++) {

            String sql = ddlStatements.get(i);
            // have no idea why some line comments survive
            sql = removeAnyRemainingLineComment(sql);
            sql = captureDefinedVariablesInOracleSqlAndSwallowStatement(sql);
            sql = pasteReferencedVariableValuesInOracleSql(sql);
            ddlStatements.set(i, sql);

            i = inlineReferencedScriptIfAnyAtPos(ddlStatements, resourceUrl, i);
        }
        return ddlStatements;
    }

    private String removeSqlLineComments(String sqlString) {
        StringBuilder retval = new StringBuilder(sqlString.length());
        String[] lines = sqlString.split("\\n");
        for (String line : lines) {
            if (line.contains("--")) {
                line = line.substring(0, line.indexOf("--"));
            }
            retval.append(line).append('\n');
        }
        // also trim consecutive blank lines
        return retval.toString().replaceAll("\\n[\\s\\n]+", "\n");
    }

    private String removeAnyRemainingLineComment(String sql) {
        if (sql.contains("--")) {
            sql = sql.substring(0, sql.indexOf("--"));
        }
        return sql;
    }

    private String removeNewLines(String sqlString) {
        return sqlString.replaceAll("\\n", " ").trim();
    }

    private String truncateWhitespace(String sqlString) {
        return sqlString.replaceAll("\\s+", " ").trim();
    }

    private String removeSqlMultilineComments(String sql) {
        Pattern pattern = Pattern.compile("/\\*.*\\*/", Pattern.MULTILINE | Pattern.DOTALL);
        sql = pattern.matcher(sql).replaceAll(" ");
        return sql;
    }

    private String fixWindowsNewlines(String sqlStringRaw) {
        return sqlStringRaw.replaceAll("\\r", " ").trim();
    }

    private int inlineReferencedScriptIfAnyAtPos(ArrayList<String> ddlStatements, URL resourceUrl, int i)
            throws URISyntaxException, IOException {
        String ddlStatement = ddlStatements.get(i);
        // inline referenced scripts
        if (ddlStatement != null && ddlStatement.trim().startsWith("@")) {
            String scriptReference = ddlStatement.substring(ddlStatement.indexOf('@') + 1).trim();
            String currentUrl = resourceUrl.toURI().toString();
            String urlString = currentUrl.substring(0, currentUrl.lastIndexOf('/')) + "/" + scriptReference;
            URL newUrl = new URL(urlString);
            // recursively retrieve and repair / insert referenced scripts
            String sqlFileFromUrl = getSqlFileAsStringFromUrl(newUrl);
            List<String> strings = splitSqlStatementsAndInlineScripts(newUrl, sqlFileFromUrl);
            // and insert instead of reference to script
            ddlStatements.remove(i);
            ddlStatements.addAll(i, strings);
            i += (strings.size() == 0 ? 0 : strings.size() - 1);
        }
        return i;
    }


    private static String[] splitIntoSqlStatements(String sqlString) {
        return sqlString.split(";");
    }


    private String captureDefinedVariablesInOracleSqlAndSwallowStatement(String sql) {
        if (sql.trim().startsWith("define ")) {
            String[] strings = sql.substring(sql.indexOf("define ") + "define ".length()).split("=");
            if (strings.length == 2) {
                String variableName = strings[0];
                String variableValue = strings[1];
                if (variableName != null && variableValue != null) {
                    this.variables.put(variableName.trim(), variableValue.trim());
                }
            }
            sql = "";
        }
        return sql;
    }

    private String pasteReferencedVariableValuesInOracleSql(String sql) {
        // todo: tillat syntaks i oracle er ganske fleksibel på hvordan en sånn
        // variabel skal slutte: om variabelen er del av et ord (etterfulgt av bokstaver)
        // må den slutte med punktum, ellers slutter den ved slutten av et definert variabel-navn.
        // Her gjenstår det litt i koden nedenfor for å få dette til å funke likt med oracle

        if (sql.matches(".*&{1,2}\\w+\\.?.*")) {
            for (Map.Entry<String, String> var : variables.entrySet()) {
                String psqlVarExpr = "&{1,2}" + var.getKey() + "";
                Matcher matcher = Pattern.compile(".*" + psqlVarExpr + "\\.?.*").matcher(sql);
                if (matcher.matches()) {
                    sql = sql.replaceAll(psqlVarExpr, var.getValue());
                }
            }
        }
        return sql;
    }


    private Map<String, Exception> executeDdlStatements(JdbcTemplate jdbcTemplate, List<String> ddlStatements)
            throws Exception {
        Map<String, Exception> exceptionMap = new HashMap<String, Exception>();
        for (int i = 0; i < ddlStatements.size(); i++) {
            String ddlStatement = ddlStatements.get(i);
            if (throwSqlExceptions) {
                executeDdlStatement(jdbcTemplate, ddlStatement);
            } else {
                try {
                    executeDdlStatement(jdbcTemplate, ddlStatement);
                } catch (Exception e) {
                    exceptionMap.put(" " + (i + 1) + ": " + ddlStatement, e);
                }
            }
        }
        return exceptionMap;
    }

    private void executeDdlStatement(JdbcTemplate jdbcTemplate, String ddlStatement) {
        if (ddlStatement != null && ddlStatement.trim().length() > 0) {
            logStatementIfNecessary(ddlStatement);
            if (ddlStatement.startsWith("drop") && ignoreExceptionsOnDropStatements) {
                try {
                    jdbcTemplate.execute(ddlStatement);
                } catch (BadSqlGrammarException e) {
                    // okay
                }
            } else {
                jdbcTemplate.execute(ddlStatement);
            }
        }
    }

    private void logStatementIfNecessary(String sql) {
        logIfStartsWithAndFlagIsSet(logSelectStatements, "select", sql);
        logIfStartsWithAndFlagIsSet(logInsertStatements, "insert", sql);
        logIfStartsWithAndFlagIsSet(logUpdateStatements, "update", sql);
        logIfStartsWithAndFlagIsSet(logDeleteStatements, "delete", sql);
        logIfStartsWithAndFlagIsSet(logCreateStatements, "create", sql);
        logIfStartsWithAndFlagIsSet(logDropStatements, "drop", sql);
        logIfStartsWithAndFlagIsSet(logAlterStatements, "alter", sql);
    }

    private void logIfStartsWithAndFlagIsSet(boolean logFlag, String keyword, String sql) {
        if (logFlag && keyword.equals(sql.split("\\W+")[0])) {
            logger.info(sql);
        }
    }

}
