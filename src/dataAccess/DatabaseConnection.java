package dataAccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    public static DatabaseConnection instance = null;
    private Connection connection = null;
    private static final String URL = "jdbc:postgresql://localhost:5432/tourism_agency";
    private static final String USER = "postgres";
    private static final String PASSWORD = "1234";
    private DatabaseConnection(){
        try {
            this.connection = DriverManager.getConnection(URL,USER,PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public Connection getConnection() {
        return connection;
    }
    public static Connection getInstance() {
        try {
            if (instance == null || instance.getConnection().isClosed()) {
                instance = new DatabaseConnection();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return instance.getConnection();
    }

}


