package banco;

import java.sql.*;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class ConsultasCuentaA {

    public void crearCuenta(int idCliente, String numeroCuenta, double saldo) {
        String sql = "INSERT INTO cuenta_ahorro(id_cliente, numero_cuenta, saldo) VALUES (?, ?, ?)";

        try (Connection con = conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idCliente);
            ps.setString(2, numeroCuenta);
            ps.setDouble(3, saldo);
            ps.executeUpdate();

            System.out.println("Cuenta creada correctamente.");

        } catch (Exception e) {
            System.out.println("Error al crear cuenta: " + e.getMessage());
        }
    }

    public boolean iniciarSesion(String numeroCuenta) {
        String sql = "SELECT * FROM cuenta_ahorro WHERE numero_cuenta = ?";

        try (Connection con = conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, numeroCuenta);
            ResultSet rs = ps.executeQuery();

            return rs.next();

        } catch (Exception e) {
            System.out.println("Error al iniciar sesion: " + e.getMessage());
            return false;
        }
    }

    public int obtenerIdCuenta(String numeroCuenta) {
        String sql = "SELECT id_cuenta FROM cuenta_ahorro WHERE numero_cuenta = ?";

        try (Connection con = conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, numeroCuenta);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("id_cuenta");
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        return -1;
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

        return 0;
    }

    public void depositar(int idCuenta, double monto) {
        if (monto <= 0) {
            System.out.println("ERROR--El monto debe ser mayor a 0.");
            return;
        }

        String sql = "UPDATE cuenta_ahorro SET saldo = saldo + ? WHERE id_cuenta = ?";

        try (Connection con = conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setDouble(1, monto);
            ps.setInt(2, idCuenta);
            ps.executeUpdate();

            new ConsultasMovimiento().registrarMovimiento(idCuenta, "Deposito", monto);
            System.out.println("Deposito realizado con exito.");

        } catch (Exception e) {
            System.out.println("Error al depositar: " + e.getMessage());
        }
    }

    public void retirar(int idCuenta, double monto) {
        double saldo = consultarSaldo(idCuenta);

        if (monto <= 0) {
            System.out.println("El monto debe ser mayor a 0.");
            return;
        }

        if (monto > saldo) {
            System.out.println("Saldo insuficiente.");
            return;
        }

        String sql = "UPDATE cuenta_ahorro SET saldo = saldo - ? WHERE id_cuenta = ?";

        try (Connection con = conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setDouble(1, monto);
            ps.setInt(2, idCuenta);
            ps.executeUpdate();

            new ConsultasMovimiento().registrarMovimiento(idCuenta, "Retiro", monto);
            System.out.println("Retiro realizado.");

        } catch (Exception e) {
            System.out.println("Error al retirar: " + e.getMessage());
        }
    }

    public void eliminarCuenta(int idCuenta) {
        String sql = "DELETE FROM cuenta_ahorro WHERE id_cuenta = ?";

        try (Connection con = conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idCuenta);
            ps.executeUpdate();

            System.out.println("Cuenta eliminada correctamente.");

        } catch (Exception e) {
            System.out.println("Error al eliminar cuenta: " + e.getMessage());
        }
    }

    public void exportarCuentasTXT() {
        String sql = "SELECT * FROM cuenta_ahorro";

        List<String> cuentas = new ArrayList<>();

        try (Connection con = conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String linea = "ID Cuenta: " + rs.getInt("id_cuenta")
                        + ", Cliente: "  + rs.getInt("id_cliente")
                        + ", Numero: "   + rs.getString("numero_cuenta")
                        + ", Saldo: "    + rs.getDouble("saldo");

                cuentas.add(linea);
            }

            FileWriter fw = new FileWriter("cuentas_banco.txt");

            for (String cuenta : cuentas) {
                fw.write(cuenta + "\n");
            }

            fw.close();

            System.out.println("Archivo TXT generado correctamente.");

        } catch (Exception e) {
            System.out.println("Error al exportar: " + e.getMessage());
        }
    }
}