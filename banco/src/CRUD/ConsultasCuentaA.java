package crud;

import conexion.conexion;
import entidades.CuentaAhorro;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConsultasCuentaA {

// 1. Método de apertura con ID auto-generado y Número de Cuenta con ceros
    public int registrarCuenta(CuentaAhorro c) {
        String sqlInsert = "INSERT INTO cuenta_ahorro (id_cliente, saldo) VALUES (?, ?)";
        try (Connection con = conexion.getConexion(); 
             PreparedStatement ps = con.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS)) {
            
            ps.setInt(1, c.getIdCliente());
            ps.setDouble(2, c.getSaldo());
            
            int filasAfectadas = ps.executeUpdate();
            
            if (filasAfectadas > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        int idGenerado = rs.getInt(1); // MySQL nos da el ID real (ej. 1)
                        
                        // === NUEVO: GUARDAR LOS CEROS EN LA BASE DE DATOS ===
                        String numeroConCeros = String.format("%010d", idGenerado);
                        String sqlUpdate = "UPDATE cuenta_ahorro SET numero_cuenta = ? WHERE id_cuenta = ?";
                        try (PreparedStatement psUpdate = con.prepareStatement(sqlUpdate)) {
                            psUpdate.setString(1, numeroConCeros);
                            psUpdate.setInt(2, idGenerado);
                            psUpdate.executeUpdate(); // Se guardan los ceros en MySQL
                        }
                        // =====================================================
                        
                        return idGenerado;
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al registrar cuenta: " + e.getMessage());
        }
        return -1; 
    }

    // --- NUEVAS VALIDACIONES DE SEGURIDAD ---
    public boolean existeCliente(int idCliente) {
        String sql = "SELECT id_cliente FROM cliente WHERE id_cliente = ?";
        try (Connection con = conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idCliente);
            ResultSet rs = ps.executeQuery();
            return rs.next(); // true si el cliente existe
        } catch (Exception e) {
            System.out.println("Error al verificar cliente: " + e.getMessage());
            return false;
        }
    }

    public boolean tieneCuenta(int idCliente) {
        String sql = "SELECT id_cliente FROM cuenta_ahorro WHERE id_cliente = ?";
        try (Connection con = conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idCliente);
            ResultSet rs = ps.executeQuery();
            return rs.next(); // true si ya tiene cuenta
        } catch (Exception e) {
            return false;
        }
    }
    // ----------------------------------------

    // 2. Método de seguridad para eliminar
    public boolean eliminarCuenta(int idCuenta) {
        String sql = "DELETE FROM cuenta_ahorro WHERE id_cuenta = ?";
        try (Connection con = conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idCuenta);
            return ps.executeUpdate() > 0; // Solo retorna true si realmente borró algo
        } catch (SQLException e) {
            System.out.println("Error al eliminar cuenta: " + e.getMessage());
            return false;
        }
    }

    // --- MÉTODOS DEL CAJERO ---
    public boolean iniciarSesion(String numCuenta) {
        String sql = "SELECT id_cuenta FROM cuenta_ahorro WHERE id_cuenta = ?";
        try (Connection con = conexion.getConexion(); 
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, Integer.parseInt(numCuenta));
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (Exception e) {
            return false;
        }
    }

    public int obtenerIdCuenta(String numCuenta) {
        try {
            return Integer.parseInt(numCuenta);
        } catch (Exception e) {
            return -1;
        }
    }

    public void depositar(int idCuenta, double monto) {
        String sql = "UPDATE cuenta_ahorro SET saldo = saldo + ? WHERE id_cuenta = ?";
        try (Connection con = conexion.getConexion(); 
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDouble(1, monto);
            ps.setInt(2, idCuenta);
            ps.executeUpdate();
            new ConsultasMovimiento().registrarMovimiento(idCuenta, "Depósito", monto);
        } catch (Exception e) {
            System.out.println("Error al depositar: " + e.getMessage());
        }
    }

    public void retirar(int idCuenta, double monto) {
        String sqlSelect = "SELECT saldo FROM cuenta_ahorro WHERE id_cuenta = ?";
        try (Connection con = conexion.getConexion(); 
             PreparedStatement psSelect = con.prepareStatement(sqlSelect)) {
            psSelect.setInt(1, idCuenta);
            ResultSet rs = psSelect.executeQuery();
            if (rs.next()) {
                double saldo = rs.getDouble("saldo");
                if (monto <= saldo) {
                    String sqlUpdate = "UPDATE cuenta_ahorro SET saldo = saldo - ? WHERE id_cuenta = ?";
                    try (PreparedStatement psUpdate = con.prepareStatement(sqlUpdate)) {
                        psUpdate.setDouble(1, monto);
                        psUpdate.setInt(2, idCuenta);
                        psUpdate.executeUpdate();
                        new ConsultasMovimiento().registrarMovimiento(idCuenta, "Retiro", monto);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error al retirar: " + e.getMessage());
        }
    }

    public double consultarSaldo(int idCuenta) {
        String sql = "SELECT saldo FROM cuenta_ahorro WHERE id_cuenta = ?";
        try (Connection con = conexion.getConexion(); 
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idCuenta);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getDouble("saldo");
            }
        } catch (Exception e) {
            System.out.println("Error al consultar saldo: " + e.getMessage());
        }
        return 0.0;
    }

    public void exportarCuentasTXT() {
        String sql = "SELECT id_cuenta, id_cliente, saldo FROM cuenta_ahorro";
        try (Connection con = conexion.getConexion(); 
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery();
             FileWriter writer = new FileWriter("cuentas_banco.txt")) {
            
            while (rs.next()) {
                String numCuenta = String.format("%010d", rs.getInt("id_cuenta"));
                writer.write("N. Cuenta: " + numCuenta + 
                             " | ID Cliente: " + rs.getInt("id_cliente") + 
                             " | Saldo: $" + rs.getDouble("saldo") + "\n");
            }
            
            java.io.File archivo = new java.io.File("cuentas_banco.txt");
            if (archivo.exists() && java.awt.Desktop.isDesktopSupported()) {
                java.awt.Desktop.getDesktop().open(archivo); 
            }
            
        } catch (Exception e) {
            System.out.println("Error al exportar o abrir el archivo: " + e.getMessage());
        }
    }
}