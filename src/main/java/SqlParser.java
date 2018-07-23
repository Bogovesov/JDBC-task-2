import utils.Splitter;
import utils.TableColumn;

import java.util.ArrayList;
import java.util.List;

public class SqlParser {
    private final char QUOTES_START_TYPE = '(';
    private final char QUOTES_ENT_TYPE = ')';
    private final int INDEX_HEAD = 0;
    private final String CSV_EXT = ".csv";
    private final String VARCHAR = "varchar";
    private final String SEPARATOR = ",";

    private SqlParser() {

    }

    public static SqlParser instance() {
        return Singleton.INSTANCE.sqlParser;
    }


    public String getSqlCreateTable(String tableName, List<String> lines) {
        return makeSqlCreateTable(parseTableName(tableName), makeListColumns(lines.get(INDEX_HEAD).split(SEPARATOR)));
    }

    public String getSqlInsert(String tableName, List<String> lines) {
        String sql = "";
        if (lines.size() > 1) {
            String column = buildColumnsListWithoutType(lines.get(INDEX_HEAD).split(SEPARATOR));
            sql = "INSERT INTO " + parseTableName(tableName) + "(" + column + ")" + " VALUES";
            for (int i = 0; i < lines.size(); i++) {
                if (i != INDEX_HEAD) {
                    sql += "(" + lines.get(i) + ")";
                    if (i != lines.size() - 1) {
                        sql += ',';
                    }
                }
            }
            sql += ";";
        }
        return sql;
    }

    private String buildColumnsListWithoutType(String[] columns) {
        String result = "";
        for (int j = 0; j < columns.length; j++) {
            result += Splitter.instance().parseTableColumn(columns[j]).getName() + ',';
        }
        return result.substring(0, result.length() - 1);
    }

    private List<TableColumn> makeListColumns(String[] columns) {
        List<TableColumn> tableColumns = new ArrayList<>();
        for (int j = 0; j < columns.length; j++) {
            tableColumns.add(Splitter.instance().parseTableColumn(columns[j]));
        }
        return tableColumns;
    }

    private String makeSqlCreateTable(String tableName, List<TableColumn> tableColumns) {
        String sql = "CREATE TABLE IF NOT EXISTS " + tableName + "(";
        String primarykey = "";
        for (int i = 0; i < tableColumns.size(); i++) {
            String columnType = tableColumns.get(i).getType();
            String colunName = tableColumns.get(i).getName();

            if (primarykey.isEmpty()) {
                primarykey = colunName;
            }
            if (VARCHAR.equals(columnType)) {
                columnType = tableColumns.get(i).getType() + "(255)";
            }
            sql += colunName + " " + columnType + " NOT NULL,";
        }
        sql += "PRIMARY KEY (" + primarykey + ") )";
        return sql;
    }

    public String parseTableName(String tableName) {
        if (tableName.contains(CSV_EXT)) {
            tableName = tableName.replace(".csv", "");
        }
        return tableName;
    }

    private enum Singleton {
        INSTANCE;
        private final SqlParser sqlParser;

        Singleton() {
            sqlParser = new SqlParser();
        }
    }
}
