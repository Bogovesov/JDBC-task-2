package utils;

import utils.foreign_key.TableAndColumnFK;

public class Splitter {

    private final char QUOTES_START = '(';
    private final char QUOTES_ENT = ')';

    private Splitter() {
    }

    public static Splitter instance() {
        return Singleton.INSTANCE.splitter;
    }

    public TableColumn parseTableColumn(String input) {
        String columnName = "", columnType = "";
        if ((input.indexOf(QUOTES_START) >= 0) && (input.indexOf(QUOTES_ENT) >= 0)) {
            Boolean isType = false;
            for (int i = 0; i < input.length() - 1; i++) {
                char letter = input.charAt(i);

                if (letter == QUOTES_START) {
                    isType = true;
                }
                if (!isType) {
                    columnName += letter;
                } else if ((!(letter == QUOTES_START)) && !(letter == QUOTES_ENT)) {
                    columnType += letter;
                }
            }
        }
        return new TableColumn(columnName, columnType);
    }

    public TableAndColumnFK parseTableAndColumn(String input) {
        String tableName = "", columnName = "";
        if ((input.indexOf(QUOTES_START) >= 0) && (input.indexOf(QUOTES_ENT) >= 0)) {
            Boolean isColumn = false;
            for (int i = 0; i < input.length() - 1; i++) {
                char letter = input.charAt(i);
                if (letter == QUOTES_START) {
                    isColumn = true;
                }
                if (!isColumn) {
                    tableName += letter;
                } else if ((!(letter == QUOTES_START)) && !(letter == QUOTES_ENT)) {
                    columnName += letter;
                }
            }
        } else {
            tableName = input;
        }
        return new TableAndColumnFK(tableName, columnName);
    }


    private enum Singleton {
        INSTANCE;
        private final Splitter splitter;

        Singleton() {
            splitter = new Splitter();
        }
    }
}
