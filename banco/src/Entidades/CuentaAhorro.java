package entidades;

public class CuentaAhorro extends Abstracta {

    private int idCuenta;
    private int idCliente;
    private String numeroCuenta;
    private double saldo;

    // 1. Constructor vacío
    public CuentaAhorro() {
    }

    // 2. Constructor con parámetros
    public CuentaAhorro(int idCuenta, int idCliente, String numeroCuenta, double saldo) {
        this.idCuenta = idCuenta;
        this.idCliente = idCliente;
        this.numeroCuenta = numeroCuenta;
        this.saldo = saldo;
    }

    // Getters y Setters
    public int getIdCuenta() {
        return idCuenta;
    }

    public void setIdCuenta(int idCuenta) {
        this.idCuenta = idCuenta;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    // Implementación obligatoria del método de la clase Abstracta
    @Override
    public String obtenerDetalles() {
        return "Cuenta No: " + numeroCuenta + " - Saldo: $" + saldo;
    }
}
