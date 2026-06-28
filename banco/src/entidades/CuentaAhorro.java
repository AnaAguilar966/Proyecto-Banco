package entidades;

public class CuentaAhorro extends Abstracta {
    private int idCuenta;
    private int idCliente;
    private String numeroCuenta;
    private double saldo;

    public CuentaAhorro() {}

    public CuentaAhorro(int idCuenta, int idCliente, String numeroCuenta, double saldo) {
        this.idCuenta = idCuenta;
        this.idCliente = idCliente;
        this.numeroCuenta = numeroCuenta;
        this.saldo = saldo;
    }

    public int getIdCuenta() { return idCuenta; }
    public void setIdCuenta(int idCuenta) { this.idCuenta = idCuenta; }

    public int getIdCliente() { return idCliente; }
    public void setIdCliente(int idCliente) { this.idCliente = idCliente; }

    public String getNumeroCuenta() { return numeroCuenta; }
    public void setNumeroCuenta(String numeroCuenta) { this.numeroCuenta = numeroCuenta; }

    public double getSaldo() { return saldo; }
    public void setSaldo(double saldo) { this.saldo = saldo; }

    @Override
    public void mostrarDatos() {
        System.out.println("===== CUENTA DE AHORRO =====");
        System.out.println("ID Cuenta:      " + idCuenta);
        System.out.println("ID Cliente:     " + idCliente);
        System.out.println("Num. Cuenta:    " + numeroCuenta);
        System.out.println("Saldo:          " + saldo);
    }
}