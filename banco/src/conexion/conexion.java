package conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class conexion {

    // Configuración de la base de datos
    private static final String URL = "jdbc:mysql://localhost:3306/Banco?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = ""; // Contraseña vacía como indicaste

    public static Connection getConexion() throws SQLException {

        Connection con = null;

        try {
            // Cargar el driver de MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Establecer la conexión
            con = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conexión exitosa a la base de datos.");

        } catch (ClassNotFoundException e) {
            System.out.println("Error: Driver no encontrado.");
            e.printStackTrace();

        } catch (SQLException e) {
            System.out.println("Error de conexión con MySQL.");
            e.printStackTrace();
        }

        return con;
    }
}