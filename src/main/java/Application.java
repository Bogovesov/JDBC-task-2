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
            Map<String, List<String>> tables = DirectoryParser.instance().read(directory);
            for (Map.Entry<String, List<String>> entry : tables.entrySet()) {
                String sqlCreateTable = SqlParser.instance().getSqlCreateTable(entry.getKey(), entry.getValue());
                SqlManager.instance().execute(sqlCreateTable);
                String sqlInsertData = SqlParser.instance().getSqlInsert(entry.getKey(), entry.getValue());
                SqlManager.instance().execute(sqlInsertData);
            }
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
