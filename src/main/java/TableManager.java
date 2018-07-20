public class TableManager {

    private TableManager() {

    }
    public static TableManager instance() {
        return Singleton.INSTANCE.tableManager;
    }


    public void build(String sqlText){

    }

    private enum Singleton {
        INSTANCE;
        private final TableManager tableManager;

        Singleton() {
            tableManager = new TableManager();
        }
    }
}
