package banco;

import java.sql.*;

public class ConsultasCliente{

    public void registrarCliente(cliente c) {
        String sql = "INSERT INTO cliente(nombre, telefono, correo) VALUES (?, ?, ?)";

        try (Connection con = conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, c.getNombre());
            ps.setString(2, c.getTelefono());
            ps.setString(3, c.getCorreo());
            ps.executeUpdate();

            System.out.println("Cliente registrado correctamente.");

        } catch (Exception e) {
            System.out.println("Error al registrar cliente: " + e.getMessage());
        }
    }
}