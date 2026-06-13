package banco;

import java.sql.Connection;
import java.sql.DriverManager;

public class conexion {

    private static final String URL = "jdbc:mysql://localhost:3306/Banco?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    public static Connection getConexion() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}