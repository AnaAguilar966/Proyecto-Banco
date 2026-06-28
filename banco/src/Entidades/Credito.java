<<<<<<<< HEAD:banco/src/entidades/Credito.java
package entidades;
========
package Entidades;

import CRUD.Abstracta;
>>>>>>>> ede572577921217e9399ff9535256ce09c1143f9:banco/src/Entidades/Credito.java

public class Credito extends Abstracta {

    private int idCredito;
    private int idCuenta;
    private double monto;
    private double saldoPendiente;
    private String estado;

    public Credito() {
    }

    public Credito(int idCredito, int idCuenta, double monto, double saldoPendiente, String estado) {
        this.idCredito = idCredito;
        this.idCuenta = idCuenta;
        this.monto = monto;
        this.saldoPendiente = saldoPendiente;
        this.estado = estado;
    }

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

    @Override
    public void mostrarDatos() {
        System.out.println("===== CREDITO =====");
        System.out.println("ID Credito:      " + idCredito);
        System.out.println("ID Cuenta:       " + idCuenta);
        System.out.println("Monto:           " + monto);
        System.out.println("Saldo Pendiente: " + saldoPendiente);
        System.out.println("Estado:          " + estado);
    }
}
