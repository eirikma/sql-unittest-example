package demo.jz2013.sqlfileexecutor;

/**
 * User: eim
 * Date: 15.02.11
 * Time: 13:45
 */
public class OracleToHsqlSqlTranslator {
    /**
     * rewrites Oracle SQL DDL statements to Hsql single-schema equivalents, mostly by removing
     * oracle-specific tuning instruction, ignoring changes in current schema, synonym definitions
     * etc. Will not work with any input, but works with some. Don't prefix any references with
     * schema name, because that will not work. Instead, use "alter session set current_schema ...",
     * which will be ignored.
     *
     * @param sql - a single SQL DDL statement, such as create table mytable ( ... );
     * @return the same statement rewritten to hsql-compatible syntax, or an empty string if
     *         the statement is to be ignored.
     */
    static String translateOracleToHsqlDdl(String sql) {

        sql = sql.trim();

        if (!(sql.toLowerCase().startsWith("insert")
                || sql.toLowerCase().startsWith("delete")
                || sql.toLowerCase().startsWith("update"))) {
            sql = sql.toLowerCase(); //Do not lowercase insert, delete and update queries, as this affects data values
        } else {
            // sysdate is called current_date and is a function, not a select
            if (sql.toLowerCase().contains("sysdate from dual")) {
                sql = sql.toLowerCase().replaceAll("\\(select sysdate from dual\\)", "CURRENT_DATE");
            }
            if (sql.toLowerCase().contains(".nextval") && sql.toLowerCase().contains("insert")) {
                sql = sql.replaceAll("(insert .*)([(,]+)\\s*([a-zA-Z_]*)\\.nextval(.*)", "$1 $2next value for $3 $4");
            }
            return sql;
        }

        //Note: from here on the SQL is in lowercase

        if (sql.startsWith("purge recyclebin")) {
            return "";
        }
        if (sql.startsWith("quit")) {
            return "";
        }

        // remove 'set' 'connect' 'alter session' etc
        if (sql.startsWith("set ")) {
            return "";
        }
        if (sql.startsWith("connect ")) {
            return "";
        }
        if (sql.startsWith("alter session ")) {
            return "";
        }


        // remove all PCTFREE stuff from the end of "create table" statements
        sql = sql.replaceAll("(.*create table[^;]*)pctfree[^;]*", "$1");

        // substitution for number:
        //  'bigint' where 'number' is used with number(19,0),
        //  'int' where 'number' is used with number(other-size,0),
        // use 'numeric'  for decimal types: number(size,not-zero).

        if (sql.contains(" number")) {
            sql = sql.replaceAll(" number\\s*\\(\\s*19+\\s*,\\s*0\\s*\\)", " bigint ");
            sql = sql.replaceAll(" number\\s*\\(\\s*10+\\s*,\\s*0\\s*\\)", " int ");
            sql = sql.replaceAll(" number\\s*\\(\\s*1\\s*,\\s*0\\s*\\)", " bit ");
            sql = sql.replaceAll(" number\\s*\\(", " numeric( ");
        }

        // use 'varchar' where 'varchar2' is used.:  varchar2(255) => varchar(255) ;  varchar2 => varchar(255) ;
        if (sql.contains("varchar")) {
            sql = sql.replaceAll("(\\W)varchar\\((\\d+)[^)]*\\)", "$1varchar($2) ");
            sql = sql.replaceAll("(\\W)varchar2\\((\\d+)[^)]*\\)", "$1varchar($2) ");
            sql = sql.replaceAll("(\\W)varchar[^(]", "$1varchar(255) ");
        }

        if (sql.contains(" char(")) {
            sql = sql.replaceAll("(\\W)char\\((\\d+)[^)]*\\)", "$1varchar($2) ");
        }
        // 'create or replace' must be only 'create'

        if (sql.contains("create or replace")) {
            sql = sql.replaceAll("(\\W)create or replace ", "$1create ");
        }
        if (sql.contains("create sequence")) {
            // delete advanced oracle-stuff from the end of create-sequence statements
            if (sql.toLowerCase().contains("nocycle") || sql.toLowerCase().contains("nocache") || sql.contains("cycle")) {
                sql = sql.replaceAll("(create sequence .*)nocycle[^;]*", "$1");
                sql = sql.replaceAll("(create sequence .*)nocache[^;]*", "$1");
                sql = sql.replaceAll("(create sequence .*)cycle[^;]*", "$1");
            }
            if (sql.contains("minvalue") || sql.contains("maxvalue")) {
                sql = sql.replaceAll("(create sequence .*)minvalue [0-9]*(.*)", "$1 $2");
                sql = sql.replaceAll("(create sequence .*)maxvalue [0-9]*(.*)", "$1 $2");
            }
            if (sql.contains("cache")) {
                sql = sql.replaceAll("(create sequence.*)cache [0-9]*(.*)", "$1 $2");
            }
        }

        // "...primary key using index ..." must be "... primary key" (no ...using index...)
        while (sql.contains("using index")) {
            // todo: huffada, får håpe det ikke skjer ....

            // skip everything from 'using index' up to the second ')' after that
            String substring = sql.substring(sql.indexOf(')', sql.indexOf("using index") + 11) + 1);
            sql = sql.substring(0, sql.indexOf("using index"))
                    + substring.substring(substring.indexOf(')') + 1);
        }

        // sysdate is called current_date and is a function, not a select
        if (sql.contains("sysdate from dual")) {
            sql = sql.replaceAll("\\(select sysdate from dual\\)", "CURRENT_DATE");
        }
        if (sql.contains("sysdate")) {
            sql = sql.replaceAll("sysdate", "CURRENT_DATE");
            return sql;
        }


        // alter table rename column <oldname> to <newname> => alter table alter column <oldname> rename  to <newname>
        if (sql.contains("rename column")) {
            sql = sql.replaceAll("alter table (\\w+) rename column (\\w+) to (\\w+)",
                    "alter table $1 alter column $2 rename to $3");
            return sql;
        }

        if (sql.startsWith("drop") && sql.endsWith("cascade constraints")) {
            sql = sql.substring(0, sql.length() - " constraints".length());
        }

        sql = sql.replaceAll(" referencing ", " references ");

        // don't create synonyms
        if (sql.startsWith("create synonym")) {
            return "";
        }
        if (sql.startsWith("create or replace synonym")) {
            return "";
        }

        if (sql.contains("store as securefile")) {
            sql = sql.replaceAll("lob\\s*\\(*[a-wA-W_]*\\)* store as securefile\\s*\\([a-zA-Z ]*\\)", "");
        }

        // don't grant to users
        if (sql.startsWith("grant")) {
            return "";
        }
        if (sql.startsWith("revoke")) {
            return "";
        }

        if (sql.startsWith("prompt")) {
            // just information to user, skip
            return "";
        }

        // strip all trailing partitioning stuff from partition to enable row movement:
        if (sql.startsWith("create table") && sql.contains("partition") && sql.contains("enable row movement")) {
            sql = sql.replaceAll("partition[ _a-z()',]*enable\\srow\\smovement", "");
        }

        // strip all trailing partitioning stuff from end of create table statements:
        // "create table (...) partition blah blah blah ..; " => "create table (..);"
        if (sql.startsWith("create table") && sql.contains(") partition by range")) {
            sql = sql.replaceAll("partition\\sby\\srange\\s*\\([a-z_]*\\)*\\s*[()a-z& _.0-9:,'-]+", "");
        }

        //Remove cast and decode on indexes. Ignores idexes with coalesce
        if (sql.startsWith("create index")) {
            if(sql.contains("cast(")){
                sql = sql.replaceAll("cast\\((\\w+) as \\w+\\)", "$1");
            }
            if(sql.contains("decode(")){
                sql = sql.replaceAll("decode\\((\\w+),.*?\\)", "$1");
            }
            if(sql.contains("coalesce")){
                return "";
            }
        }


        return sql;
    }
}
