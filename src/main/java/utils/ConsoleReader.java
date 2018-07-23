package utils;

import java.util.Scanner;

public class ConsoleReader {

    private final String MODE_READ_FROM_FILE = "1";
    private final String MODE_READ_FROM_CONSOLE = "2";
    private final String MODE_EXIT = "3";

    private ConsoleReader() {

    }

    private void printInfo() {
        System.out.println("1 - Read from file");
        System.out.println("2 - Read from console");
        System.out.println("3 - Exit");
        System.out.println("Please choice: ");
    }

    public void read() {
        Scanner scanner = new Scanner(System.in);
        printInfo();

//        while (!scanner.nextLine().equals(MODE_EXIT)) {
        while (scanner.hasNextLine()) {
            String mode = scanner.nextLine();
            if (MODE_READ_FROM_FILE.equals(mode)) {
                System.out.println("Enter file location:");
                String fileName = scanner.nextLine();
            } else if (MODE_READ_FROM_CONSOLE.equals(mode)) {
                System.out.println("Enter sql script:");
                String sql = scanner.nextLine();
                if (!sql.isEmpty()) {
                    SqlManager.instance().execute(sql);
                }
            } else if (MODE_EXIT.equals(mode)) {
                break;
            } else {
                System.out.println("Wrong mode!");
            }
            printInfo();
        }
    }

    public static ConsoleReader instance() {
        return Singleton.INSTANCE.consoleReader;
    }


    private enum Singleton {
        INSTANCE;
        private final ConsoleReader consoleReader;

        Singleton() {
            consoleReader = new ConsoleReader();
        }
    }
}
