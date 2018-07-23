package utils.foreign_key;

import utils.Splitter;

import java.util.List;

public class OneToOneFK extends ForeignKey {

    public OneToOneFK(String[] input) {
        super(input);
    }

    @Override
    public List<String> buildSql() {
        String firstTableName = getFirstParams().getTableName();
        String secondTableName = getSecondParams().getTableName();
        String columnName = getFirstParams().getColumnName();
        String keyName = "FK_" + firstTableName + "_to_" + secondTableName;

        String sql = "ALTER TABLE " + firstTableName +
                " ADD CONSTRAINT " + keyName + " FOREIGN KEY (" + columnName + ")" +
                " REFERENCES " + secondTableName + "(" + columnName + ");";

        addSql(sql);
        return getSql();
    }
}
