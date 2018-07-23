import exceptions.UnexpectedDirectoryFormat;
import utils.ConsoleReader;
import utils.SqlManager;
import utils.foreign_key.FactoryFK;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Application {

    private Application() {

    }

    public static Application instance() {
        return Singleton.INSTANCE.application;
    }

    public void run(String[] args) throws UnexpectedDirectoryFormat {
       /* if (args.length < 1) {
            throw new IllegalArgumentException("Wrong parameters");
        }
        final String directory = args[0];
        final File file = new File(directory);
        if (file.isDirectory()) {
            Map<String, List<String>> tables = DirectoryParser.instance().readCsvFile(directory);
            for (Map.Entry<String, List<String>> entry : tables.entrySet()) {
                final String tableName = SqlParser.instance().parseTableName(entry.getKey());

                final String sqlCreateTable = SqlParser.instance().getSqlCreateTable(tableName, entry.getValue());
                SqlManager.instance().execute(sqlCreateTable);

                if (SqlManager.instance().verifyTableIsEmpty(tableName)) {
                    final String sqlInsertData = SqlParser.instance().getSqlInsert(tableName, entry.getValue());
                    SqlManager.instance().execute(sqlInsertData);
                }
            }
            final List<String> fileIndex = DirectoryParser.instance().readIndexFile(directory);
            FactoryFK.instance().buildForeignKey(fileIndex);
        } else {
            throw new UnexpectedDirectoryFormat();
        }*/
        ConsoleReader.instance().read();
    }

    private enum Singleton {
        INSTANCE;
        private final Application application;

        Singleton() {
            application = new Application();
        }
    }
}
