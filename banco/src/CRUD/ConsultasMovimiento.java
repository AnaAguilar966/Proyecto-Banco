<<<<<<<< HEAD:banco/src/crud/ConsultasMovimiento.java
package crud;
========
package CRUD;
>>>>>>>> ede572577921217e9399ff9535256ce09c1143f9:banco/src/CRUD/ConsultasMovimiento.java

import conexion.conexion;
import java.sql.*;

public class ConsultasMovimiento {

    public void registrarMovimiento(int idCuenta, String tipo, double monto) {
        String sql = "INSERT INTO movimientos(id_cuenta, tipo, monto) VALUES (?, ?, ?)";

        try (Connection con = conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idCuenta);
            ps.setString(2, tipo);
            ps.setDouble(3, monto);
            ps.executeUpdate();

        } catch (Exception e) {
            System.out.println("Error al registrar movimiento: " + e.getMessage());
        }
    }
}