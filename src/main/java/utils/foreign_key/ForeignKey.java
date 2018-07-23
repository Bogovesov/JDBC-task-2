package utils.foreign_key;

import utils.Splitter;

import java.util.ArrayList;
import java.util.List;

public abstract class ForeignKey  implements IForeignKey {

    protected  final int INDEX_FIRST_PARAM = 0;
    protected  final int INDEX_RELATION = 1;
    protected  final int INDEX_SECOND_PARAM = 2;
    protected  final int INDEX_TABLE_NAME = 3;

    private TableAndColumnFK firstParams;
    private TableAndColumnFK secondParams;
    private String tableName;
    private List<String> sql = new ArrayList<>();
    protected String[] input;

    public ForeignKey(String[] input) {
        this.input = input;

        for (int i = 0; i < input.length; i++) {
            String val = input[i];
            if (i == INDEX_FIRST_PARAM) {
                setFirstParams(Splitter.instance().parseTableAndColumn(val));
            } else if (i == INDEX_RELATION) {

            } else if (i == INDEX_SECOND_PARAM) {
                setSecondParams(Splitter.instance().parseTableAndColumn(val));
            } else if (i == INDEX_TABLE_NAME) {
                setTableName(val);
            }
        }
    }

    public List<String> getSql() {
        return sql;
    }

    public void addSql(String sql) {
        this.sql.add(sql);
    }

    public TableAndColumnFK getFirstParams() {
        return firstParams;
    }

    public TableAndColumnFK getSecondParams() {
        return secondParams;
    }

    public String getTableName() {
        return tableName;
    }

    public void setFirstParams(TableAndColumnFK firstParams) {
        this.firstParams = firstParams;
    }

    public void setSecondParams(TableAndColumnFK secondParams) {
        this.secondParams = secondParams;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}
