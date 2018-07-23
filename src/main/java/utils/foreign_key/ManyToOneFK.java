package utils.foreign_key;

import java.util.List;

public class ManyToOneFK extends ForeignKey {

    public ManyToOneFK(String[] input) {
        super(input);
    }

    @Override
    public List<String> buildSql() {
        final String firstTableName = getFirstParams().getTableName();
        final String secondTableName = getSecondParams().getTableName();
        final String columnNameFirst = getFirstParams().getColumnName();
        final String columnNameSecond = getSecondParams().getColumnName();

        final String keyName = "FK_" + firstTableName + "_to_" + secondTableName;

        String tmpTable1 = "", tmpTable2 = "", columName = "";
        if (!columnNameFirst.isEmpty()) {
            tmpTable1 = firstTableName;
            tmpTable2 = secondTableName;
            columName = columnNameFirst;
        } else if (!columnNameSecond.isEmpty()) {
            tmpTable1 = secondTableName;
            tmpTable2 = firstTableName;
            columName = columnNameSecond;
        }

        final String sql = "ALTER TABLE " + tmpTable1 +
                " ADD CONSTRAINT " + keyName + " FOREIGN KEY (" + columName + ")" +
                " REFERENCES " + tmpTable2 + "(" + columName + ");";

        addSql(sql);
        return getSql();
    }
}
