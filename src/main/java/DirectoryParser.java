import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DirectoryParser {


    private DirectoryParser() {

    }

    public Map<String, List<String>> read(String directoryName) {
        File folder = new File(directoryName);
        File[] listOfFiles = folder.listFiles();

        Map<String, List<String>> tables = new HashMap<>();
        CsvReader csvReader = new CsvReader();
        for (File file : listOfFiles) {
            if (file.isFile()) {
                tables.put(file.getName(), csvReader.readFile(directoryName + File.separator + file.getName()));
            }
        }
        return tables;
    }

    public static DirectoryParser instance() {
        return Singleton.INSTANCE.directoryParser;
    }

    private enum Singleton {
        INSTANCE;
        private final DirectoryParser directoryParser;

        Singleton() {
            directoryParser = new DirectoryParser();
        }
    }

}
