package banco;

public class Movimiento extends Abstracta{

    private int idMovimiento;
    private int idCuenta;
    private String tipo;
    private double monto;
    private int fecha;

    public Movimiento() {
    }

    public Movimiento(int idMovimiento, int idCuenta, String tipo, double monto, int fecha) {
        this.idMovimiento = idMovimiento;
        this.idCuenta = idCuenta;
        this.tipo = tipo;
        this.monto = monto;
        this.fecha = fecha;
    }

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

    public int getFecha() {
        return fecha;
    }

    public void setFecha(int fecha) {
        this.fecha = fecha;
    }

    @Override
    public void mostrarDatos() {
        System.out.println("===== MOVIMIENTO =====");
        System.out.println("ID Movimiento: " + idMovimiento);
        System.out.println("ID Cuenta:     " + idCuenta);
        System.out.println("Tipo:          " + tipo);
        System.out.println("Monto:         " + monto);
        System.out.println("Fecha:         " + fecha);
    }

}
