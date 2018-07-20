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
//            SqlParser.instance().buildSqlCreateTable(tables.);
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
