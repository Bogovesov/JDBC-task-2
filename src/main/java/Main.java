import exceptions.UnexpectedDirectoryFormat;

public class Main {
    public static void main(String[] args) {
        try {
            Application.instance().run(args);
        } catch (UnexpectedDirectoryFormat e) {
            e.printStackTrace();
        }
    }
}
