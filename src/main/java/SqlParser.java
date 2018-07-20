import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        return makeSqlCreateTable(parseTableName(tableName), buildColumns(lines.get(INDEX_HEAD).split(SEPARATOR)));
    }

    public String getSqlInsert(String tableName, List<String> lines) {
        String column = buildColumnsList(lines.get(INDEX_HEAD).split(SEPARATOR));
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

    private String buildColumnsList(String[] columns) {
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

    private Map<String, String> buildColumns(String[] columns) {
        Map<String, String> mapColumn = new HashMap<>();
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
                mapColumn.put(columnName, columnType);
            }
        }
        return mapColumn;
    }

    private String makeSqlCreateTable(String tableName, Map<String, String> columns) {
        String sql = "CREATE TABLE IF NOT EXISTS " + tableName + "(";
        String primarykey = "";
        for (Map.Entry<String, String> entry : columns.entrySet()) {
            if (primarykey.isEmpty()) {
                primarykey = entry.getKey();
            }
            String typeColumn = entry.getValue();
            if (VARCHAR.equals(typeColumn)) {
                typeColumn = entry.getValue() + "(255)";
            }
            sql += entry.getKey() + " " + typeColumn + " NOT NULL,";
        }
        sql += "PRIMARY KEY (" + primarykey + ") )";
        return sql;
    }

    private String parseTableName(String tableName) {
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
