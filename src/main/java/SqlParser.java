import java.util.ArrayList;
import java.util.List;

public class SqlParser {
    public static final char QUOTES_START_TYPE = '(';
    public static final char QUOTES_ENT_TYPE = ')';
    public static final int INDEX_HEAD = 0;
    public static final String CSV_EXT = ".csv";
    public static final String VARCHAR = "varchar";
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
        String column = buildColumnsListWithoutType(lines.get(INDEX_HEAD).split(SEPARATOR));
        String sql = "INSERT INTO " + parseTableName(tableName) + "(" + column + ")" + " VALUES";
        for (int i = 0; i < lines.size(); i++) {
            if (i != INDEX_HEAD) {
                sql += "(" + lines.get(i) + ")";
                if (i != lines.size() - 1) {
                    sql += ',';
                }
            }
        }
        sql += ";";
        return sql;
    }

    private String buildColumnsListWithoutType(String[] columns) {
        String result = "";
        for (int j = 0; j < columns.length; j++) {
            if ((columns[j].indexOf(QUOTES_START_TYPE) >= 0) && (columns[j].indexOf(QUOTES_ENT_TYPE) >= 0)) {
                String columnName = "";
                Boolean isType = false;
                for (int k = 0; k < columns[j].length() - 1; k++) {
                    char letter = columns[j].charAt(k);
                    if (letter == QUOTES_START_TYPE) {
                        isType = true;
                    }
                    if (!isType) {
                        columnName += letter;
                    }
                }
                result += columnName + ',';
            }
        }
        return result.substring(0, result.length() - 1);
    }

    private List<TableColumn> makeListColumns(String[] columns) {
        List<TableColumn> tableColumns = new ArrayList<>();
        for (int j = 0; j < columns.length; j++) {
            if ((columns[j].indexOf(QUOTES_START_TYPE) >= 0) && (columns[j].indexOf(QUOTES_ENT_TYPE) >= 0)) {
                String columnName = "", columnType = "";
                Boolean isType = false;
                for (int k = 0; k < columns[j].length() - 1; k++) {
                    char letter = columns[j].charAt(k);

                    if (letter == QUOTES_START_TYPE) {
                        isType = true;
                    }
                    if (!isType) {
                        columnName += letter;
                    } else if ((!(letter == QUOTES_START_TYPE)) && !(letter == QUOTES_ENT_TYPE)) {
                        columnType += letter;
                    }
                }
                tableColumns.add(new TableColumn(columnName,columnType));
            }
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
