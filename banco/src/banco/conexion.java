package banco;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class conexion {

    private static final String URL= "jdbc:mysql://localhost:3306/Banco?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection getConexion() throws SQLException {

        Connection con = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            con = DriverManager.getConnection(URL, USER, PASSWORD);

            System.out.println("Conexion exitosa");

        } catch (ClassNotFoundException e) {
            System.out.println("Driver no encontrado");
            e.printStackTrace();

        } catch (SQLException e) {
            System.out.println("Error de conexion");
            e.printStackTrace();
        }

        return con;
    }

}
