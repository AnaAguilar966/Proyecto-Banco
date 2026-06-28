package crud;

import conexion.conexion;
import java.sql.*;

public class ConsultasCredito {

    public void solicitarCredito(int idCuenta, double monto) {
        String sqlInsert = "INSERT INTO credito(id_cuenta, monto, saldo_pendiente, estado) VALUES (?, ?, ?, ?)";
        try (Connection con = conexion.getConexion(); PreparedStatement psInsert = con.prepareStatement(sqlInsert)) {

            psInsert.setInt(1, idCuenta);
            psInsert.setDouble(2, monto);
            psInsert.setDouble(3, monto);
            psInsert.setString(4, "Activo");
            psInsert.executeUpdate();

        } catch (Exception e) {
            System.out.println("Error al solicitar credito: " + e.getMessage());
        }
    }

    public void pagarCredito(int idCuenta, double pago) {
        String sqlBuscar = "SELECT id_credito, saldo_pendiente FROM credito WHERE id_cuenta = ? AND estado = 'Activo'";
        try (Connection con = conexion.getConexion(); PreparedStatement psBuscar = con.prepareStatement(sqlBuscar)) {

            psBuscar.setInt(1, idCuenta);
            ResultSet rs = psBuscar.executeQuery();

            if (rs.next()) {
                int idCredito = rs.getInt("id_credito");
                double saldoPendiente = rs.getDouble("saldo_pendiente");

                double nuevoSaldo = saldoPendiente - pago;
                String nuevoEstado = (nuevoSaldo <= 0) ? "Finalizado" : "Activo";

                String sqlUpdate = "UPDATE credito SET saldo_pendiente = ?, estado = ? WHERE id_credito = ?";
                try (PreparedStatement psUpdate = con.prepareStatement(sqlUpdate)) {
                    psUpdate.setDouble(1, nuevoSaldo);
                    psUpdate.setString(2, nuevoEstado);
                    psUpdate.setInt(3, idCredito);
                    psUpdate.executeUpdate();
                }
            }
        } catch (Exception e) {
            System.out.println("Error al pagar credito: " + e.getMessage());
        }
    }

    public boolean tieneCreditoActivo(int idCuenta) {
        String sql = "SELECT COUNT(*) FROM credito WHERE id_cuenta = ? AND estado = 'Activo'";
        try (Connection con = conexion.getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idCuenta);
            ResultSet rs = ps.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                return true;
            }
        } catch (Exception e) {
        }
        return false;
    }

    public double obtenerDeuda(int idCuenta) {
        String sql = "SELECT saldo_pendiente FROM credito WHERE id_cuenta = ? AND estado = 'Activo'";
        try (Connection con = conexion.getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idCuenta);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getDouble("saldo_pendiente");
            }
        } catch (Exception e) {
        }
        return 0.0;
    }
}
