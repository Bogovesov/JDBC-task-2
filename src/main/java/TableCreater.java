import java.util.List;

public class TableCreater {

    private TableCreater() {

    }
    public static TableCreater instance() {
        return Singleton.INSTANCE.tableCreater;
    }


    public void build(String sqlText){

    }

    private enum Singleton {
        INSTANCE;
        private final TableCreater tableCreater;

        Singleton() {
            tableCreater = new TableCreater();
        }
    }
}
