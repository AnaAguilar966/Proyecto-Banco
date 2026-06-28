package entidades;

public class Credito extends Abstracta {

    private int idCredito;
    private int idCuenta;
    private double monto;
    private double saldoPendiente;
    private String estado;

    // 1. Constructor vacío
    public Credito() {
    }

    // 2. Constructor con parámetros
    public Credito(int idCredito, int idCuenta, double monto, double saldoPendiente, String estado) {
        this.idCredito = idCredito;
        this.idCuenta = idCuenta;
        this.monto = monto;
        this.saldoPendiente = saldoPendiente;
        this.estado = estado;
    }

    // Getters y Setters
    public int getIdCredito() {
        return idCredito;
    }

    public void setIdCredito(int idCredito) {
        this.idCredito = idCredito;
    }

    public int getIdCuenta() {
        return idCuenta;
    }

    public void setIdCuenta(int idCuenta) {
        this.idCuenta = idCuenta;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public double getSaldoPendiente() {
        return saldoPendiente;
    }

    public void setSaldoPendiente(double saldoPendiente) {
        this.saldoPendiente = saldoPendiente;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    // Implementación obligatoria del método de la clase Abstracta
    @Override
    public String obtenerDetalles() {
        return "Crédito de $" + monto + " - Estado: " + estado;
    }
}
