package conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class conexion {

    // Definimos los parámetros de la conexión a la base de datos 'Banco'
    private static final String URL = "jdbc:mysql://localhost:3306/Banco?useSSL=false&serverTimezone=UTC";
    private static final String USUARIO = "root";
    private static final String CONTRASENA = ""; // Cambia esto si tu MySQL tiene contraseña

    // Método estático para obtener la conexión desde cualquier otra clase
    public static Connection getConexion() {
        Connection con = null;
        try {
            // Cargar el driver de MySQL (asegúrate de tener el mysql-connector-java.jar agregado a tus Libraries)
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Establecer la conexión
            con = DriverManager.getConnection(URL, USUARIO, CONTRASENA);
            System.out.println("Conexion a la base de datos establecida con exito.");
        } catch (ClassNotFoundException e) {
            System.out.println("Error: No se encontro el driver de MySQL. " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Error de conexion a la base de datos: " + e.getMessage());
        }
        return con;
    }
}
