import java.util.List;

public class SqlParser {


    private SqlParser() {

    }
    public static SqlParser instance() {
        return Singleton.INSTANCE.sqlParser;
    }


    public String parse(List<String> lines){

        return "";
    }

    private enum Singleton {
        INSTANCE;
        private final SqlParser sqlParser;

        Singleton() {
            sqlParser = new SqlParser();
        }
    }
}