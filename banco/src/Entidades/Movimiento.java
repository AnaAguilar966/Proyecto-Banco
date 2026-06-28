package entidades;

public class Movimiento extends Abstracta {

    private int idMovimiento;
    private int idCuenta;
    private String tipo;
    private double monto;
    private String fecha;

    // 1. Constructor vacío
    public Movimiento() {
    }

    // 2. Constructor con parámetros
    public Movimiento(int idMovimiento, int idCuenta, String tipo, double monto, String fecha) {
        this.idMovimiento = idMovimiento;
        this.idCuenta = idCuenta;
        this.tipo = tipo;
        this.monto = monto;
        this.fecha = fecha;
    }

    // Getters y Setters
    public int getIdMovimiento() {
        return idMovimiento;
    }

    public void setIdMovimiento(int idMovimiento) {
        this.idMovimiento = idMovimiento;
    }

    public int getIdCuenta() {
        return idCuenta;
    }

    public void setIdCuenta(int idCuenta) {
        this.idCuenta = idCuenta;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    // Implementación obligatoria del método de la clase Abstracta
    @Override
    public String obtenerDetalles() {
        return "Movimiento: " + tipo + " por $" + monto + " el " + fecha;
    }
}
