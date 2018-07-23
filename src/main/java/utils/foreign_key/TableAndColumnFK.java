package utils.foreign_key;

public class TableAndColumnFK {
    private String tableName;
    private String columnName;

    public TableAndColumnFK(String tableName, String columnName) {
        this.tableName = tableName;
        this.columnName = columnName;
    }

    public String getTableName() {
        return tableName;
    }

    public String getColumnName() {
        return columnName;
    }
}
