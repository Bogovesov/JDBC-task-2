import exceptions.UnexpectedDirectoryFormat;

import java.io.File;
import java.util.List;
import java.util.Map;

public class Application {

    private Application() {

    }

    public static Application instance() {
        return Singleton.INSTANCE.application;
    }

    public void run(String[] args) throws UnexpectedDirectoryFormat {
        if (args.length < 1) {
            throw new IllegalArgumentException("Wrong parameters");
        }
        final String directory = args[0];
        final File file = new File(directory);
        if (file.isDirectory()) {
            Map<String, List<String>> tables = DirectoryParser.instance().readCsvFile(directory);
            for (Map.Entry<String, List<String>> entry : tables.entrySet()) {
                String tableName = SqlParser.instance().parseTableName(entry.getKey());

                String sqlCreateTable = SqlParser.instance().getSqlCreateTable(tableName, entry.getValue());
                SqlManager.instance().execute(sqlCreateTable);

                if (SqlManager.instance().verifyTableIsEmpty(tableName)) {
                    String sqlInsertData = SqlParser.instance().getSqlInsert(tableName, entry.getValue());
                    SqlManager.instance().execute(sqlInsertData);
                }
            }

            List<String> fileIndex = DirectoryParser.instance().readIndex(directory);
        } else {
            throw new UnexpectedDirectoryFormat();
        }
    }

    private enum Singleton {
        INSTANCE;
        private final Application application;

        Singleton() {
            application = new Application();
        }
    }
}
