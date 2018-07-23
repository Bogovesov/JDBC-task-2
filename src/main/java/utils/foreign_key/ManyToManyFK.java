package utils.foreign_key;

import java.util.List;

public class ManyToManyFK extends ForeignKey {


    public ManyToManyFK(String[] input) {
        super(input);
    }

    @Override
    public List<String> buildSql() {
        final String firstTable = getFirstParams().getTableName();
        final String secondTable = getSecondParams().getTableName();

        final String firstColumn = getFirstParams().getColumnName();
        final String secondColumn = getSecondParams().getColumnName();

        final String relationTable = getTableName();

        final String nameKey1 = "FK_" + relationTable + "_to_" + firstTable;
        final String sql1 = buildSql(nameKey1, relationTable, firstTable, firstColumn);
        addSql(sql1);

        final String nameKey2 = "FK_" + relationTable + "_to_" + secondTable;
        final String sql2 = buildSql(nameKey2, relationTable, secondTable, secondColumn);
        addSql(sql2);

        return getSql();
    }

    private String buildSql(String keyName, String tableRel, String table, String column) {
        return "ALTER TABLE " + tableRel +
                " ADD CONSTRAINT " + keyName + " FOREIGN KEY (" + column + ")" +
                " REFERENCES " + table + "(" + column + ");";
    }
}
