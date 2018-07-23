package utils;

import java.sql.*;

public class SqlManager {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/test_db";
    private static final String USER_NAME = "root";
    private static final String PASSWORD = "2010";


    private SqlManager() {
        addMySQLToClassPath();
    }

    public static SqlManager instance() {
        return Singleton.INSTANCE.sqlManager;
    }


    private void addMySQLToClassPath() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
    }

    public void execute(String sql) {
        if (!sql.isEmpty()) {
            try (Connection dbConnection = getConnection();
                 PreparedStatement statement = dbConnection.prepareStatement(sql)) {
                statement.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean verifyTableIsEmpty(String tableName) {
        int countRow = 0;
        try (Connection dbConnection = getConnection();
             PreparedStatement statement = dbConnection.prepareStatement("SELECT COUNT(*) FROM " + tableName)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                countRow = resultSet.getInt(1);
                ;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return countRow == 0;
    }

    private enum Singleton {
        INSTANCE;
        private final SqlManager sqlManager;

        Singleton() {
            sqlManager = new SqlManager();
        }
    }
}
