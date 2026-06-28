package CRUD;

import conexion.conexion;
import java.sql.*;

public class ConsultasCredito {

    public void solicitarCredito(int idCuenta, double monto) {
        if (monto <= 0) {
            System.out.println("El monto del credito debe ser mayor a 0.");
            return;
        }

        String sqlCheck  = "SELECT COUNT(*) FROM credito WHERE id_cuenta = ? AND estado = 'Activo'";
        String sqlInsert = "INSERT INTO credito(id_cuenta, monto, saldo_pendiente, estado) VALUES (?, ?, ?, ?)";

        try (Connection con = conexion.getConexion();
             PreparedStatement psCheck = con.prepareStatement(sqlCheck)) {

            psCheck.setInt(1, idCuenta);
            ResultSet rs = psCheck.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {
                System.out.println("Error: Ya tienes un credito activo actualmente.");
                return;
            }

            try (PreparedStatement psInsert = con.prepareStatement(sqlInsert)) {
                psInsert.setInt(1, idCuenta);
                psInsert.setDouble(2, monto);
                psInsert.setDouble(3, monto);
                psInsert.setString(4, "Activo");
                psInsert.executeUpdate();

                System.out.println("Credito solicitado correctamente.");
            }

        } catch (Exception e) {
            System.out.println("Error al solicitar credito: " + e.getMessage());
        }
    }

    public void pagarCredito(int idCuenta, double pago) {
        if (pago <= 0) {
            System.out.println("El monto a pagar debe ser mayor a 0.");
            return;
        }

        String sqlBuscar = "SELECT id_credito, saldo_pendiente FROM credito WHERE id_cuenta = ? AND estado = 'Activo'";

        try (Connection con = conexion.getConexion();
             PreparedStatement psBuscar = con.prepareStatement(sqlBuscar)) {

            psBuscar.setInt(1, idCuenta);
            ResultSet rs = psBuscar.executeQuery();

            if (rs.next()) {
                int    idCredito      = rs.getInt("id_credito");
                double saldoPendiente = rs.getDouble("saldo_pendiente");

                if (pago > saldoPendiente) {
                    System.out.println("El pago excede el saldo pendiente (" + saldoPendiente + "). Se ajustara para liquidar la cuenta.");
                    pago = saldoPendiente;
                }

                double nuevoSaldo  = saldoPendiente - pago;
                String nuevoEstado = (nuevoSaldo == 0) ? "Finalizado" : "Activo";

                String sqlUpdate = "UPDATE credito SET saldo_pendiente = ?, estado = ? WHERE id_credito = ?";

                try (PreparedStatement psUpdate = con.prepareStatement(sqlUpdate)) {
                    psUpdate.setDouble(1, nuevoSaldo);
                    psUpdate.setString(2, nuevoEstado);
                    psUpdate.setInt(3, idCredito);
                    psUpdate.executeUpdate();

                    System.out.println("Pago de credito realizado correctamente.");
                    System.out.println("Saldo pendiente actual: " + nuevoSaldo);

                    if (nuevoSaldo == 0) {
                        System.out.println("Credito totalmente liquidado. Estado: Finalizado.");
                    }
                }

            } else {
                System.out.println("No se encontro ningun credito activo para esta cuenta.");
            }

        } catch (Exception e) {
            System.out.println("Error al pagar credito: " + e.getMessage());
        }
    }
}