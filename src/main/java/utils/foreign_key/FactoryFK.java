package utils.foreign_key;

import utils.SqlManager;

import java.util.ArrayList;
import java.util.List;

public class FactoryFK {
    private final int INDEX_RELATION = 1;
    private final String MANY_TO_MANY = "ManyToMany";
    private final String MANY_TO_ONE = "ManyToOne";
    private final String ONE_TO_MANY = "OneToMany";
    private final String ONE_TO_ONE = "OneToOne";

    private FactoryFK() {
    }

    public static FactoryFK instance() {
        return Singleton.INSTANCE.factory;
    }


    public void buildForeignKey(List<String> input) {
        List<String> listSql = new ArrayList<>();
        for (String s : input) {
            final String[] lines = s.split(" ");
            final String relation = lines[INDEX_RELATION].toLowerCase();

            ForeignKey foreignKey = null;
            if (relation.equals(MANY_TO_MANY.toLowerCase())) {
                foreignKey = new ManyToManyFK(lines);
            } else if ((relation.equals(MANY_TO_ONE.toLowerCase())) || (relation.equals(ONE_TO_MANY.toLowerCase()))) {
                foreignKey = new ManyToOneFK(lines);
            } else if (relation.equals(ONE_TO_ONE.toLowerCase())) {
                foreignKey = new OneToOneFK(lines);
            }
            if (foreignKey != null) {
                listSql.addAll(foreignKey.buildSql());
            }
        }
        for (String sql : listSql) {
            SqlManager.instance().execute(sql);
        }
    }

    private enum Singleton {
        INSTANCE;
        private final FactoryFK factory;

        Singleton() {
            factory = new FactoryFK();
        }
    }
}
