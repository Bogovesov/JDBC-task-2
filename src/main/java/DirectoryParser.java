import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DirectoryParser {

    private final String EXT_CSV = ".csv";
    private final String EXT_TXT = ".txt";

    private DirectoryParser() {

    }

    public Map<String, List<String>> readCsvFile(String directoryName) {
        File[] listOfFiles = getFiles(directoryName);
        Map<String, List<String>> tables = new HashMap<>();
        MyFileReader myFileReader = new MyFileReader();
        for (File file : listOfFiles) {
            if (file.isFile() && (file.getName().indexOf(EXT_CSV) > 0)) {
                tables.put(file.getName(), myFileReader.readFile(directoryName + File.separator + file.getName()));
            }
        }
        return tables;
    }

    private File[] getFiles(String directoryName) {
        File folder = new File(directoryName);
        return folder.listFiles();
    }

    public List<String> readIndex(String directoryName) {
        File[] listOfFiles = getFiles(directoryName);
        List<String> strings = new ArrayList<>();
        MyFileReader myFileReader = new MyFileReader();
        for (File file : listOfFiles) {
            if (file.isFile() && (file.getName().indexOf(EXT_TXT) > 0)) {
                strings = myFileReader.readFile(directoryName + File.separator + file.getName());
                break;
            }
        }
        return strings;
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
