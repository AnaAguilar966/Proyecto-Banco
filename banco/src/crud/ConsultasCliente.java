package crud;

import conexion.conexion;
import entidades.cliente;
import java.sql.*;

public class ConsultasCliente {

    public int registrarCliente(cliente c) {
        String sql = "INSERT INTO cliente(nombre, telefono, correo) VALUES (?, ?, ?)";

        try (Connection con = conexion.getConexion(); PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, c.getNombre());
            ps.setString(2, c.getTelefono());
            ps.setString(3, c.getCorreo());
            ps.executeUpdate();

            // Esto obtiene el ID que MySQL le asignó al cliente
            ResultSet rs = ps.getGeneratedKeys();

            if (rs.next()) {
                int idGenerado = rs.getInt(1);
                System.out.println("Cliente registrado con ID: " + idGenerado);
                return idGenerado;
            }

        } catch (Exception e) {
            System.out.println("Error al registrar cliente: " + e.getMessage());
        }

        return -1; // Retorna -1 si hubo un error
    }
}
