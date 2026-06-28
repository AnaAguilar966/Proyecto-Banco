package vistas;

import crud.ConsultasCuentaA;
import javax.swing.*;
import java.awt.*;

public class FrmTransacciones extends JFrame {
    
    // Elementos de la ventana
    private JTextField txtNumCuenta;
    private JTextField txtMonto;
    private JButton btnIniciar;
    private JButton btnDepositar;
    private JButton btnRetirar;
    private JButton btnSaldo;
    private JButton btnRegresar;
    
    // Variables para controlar la sesión
    private int idCuentaActiva = -1;
    private ConsultasCuentaA dao;

    public FrmTransacciones() {
        dao = new ConsultasCuentaA();
        
        setTitle("Cajero y Transacciones");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(6, 2, 10, 10));

        // 1. Fila de Inicio de Sesión
        add(new JLabel(" Ingrese Número de Cuenta:"));
        txtNumCuenta = new JTextField();
        add(txtNumCuenta);

        btnIniciar = new JButton("Iniciar Sesión");
        add(btnIniciar);
        add(new JLabel(" (Requiere inicio de sesión)"));

        // 2. Fila del Monto
        add(new JLabel(" Monto a Transaccionar:"));
        txtMonto = new JTextField();
        txtMonto.setEnabled(false); // Bloqueado hasta que inicie sesión
        add(txtMonto);

        // 3. Botones de transacciones (Bloqueados por defecto)
        btnDepositar = new JButton("Depositar");
        btnRetirar = new JButton("Retirar");
        btnDepositar.setEnabled(false);
        btnRetirar.setEnabled(false);
        add(btnDepositar);
        add(btnRetirar);

        // 4. Botones finales
        btnSaldo = new JButton("Consultar Saldo");
        btnSaldo.setEnabled(false);
        add(btnSaldo);
        
        btnRegresar = new JButton("Regresar al Menú");
        add(btnRegresar);

        // --- ASIGNACIÓN DE EVENTOS ---
        btnIniciar.addActionListener(e -> iniciarSesion());
        
        btnDepositar.addActionListener(e -> {
            if (validarMonto()) {
                double monto = Double.parseDouble(txtMonto.getText());
                dao.depositar(idCuentaActiva, monto);
                JOptionPane.showMessageDialog(this, "Depósito procesado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                txtMonto.setText(""); // Limpiar la caja
            }
        });
        
        btnRetirar.addActionListener(e -> {
            if (validarMonto()) {
                double monto = Double.parseDouble(txtMonto.getText());
                dao.retirar(idCuentaActiva, monto);
                JOptionPane.showMessageDialog(this, "Retiro procesado. Revisa tu saldo para confirmar.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                txtMonto.setText(""); // Limpiar la caja
            }
        });
        
        btnSaldo.addActionListener(e -> {
            double saldo = dao.consultarSaldo(idCuentaActiva);
            JOptionPane.showMessageDialog(this, "Tu saldo actual es: $" + saldo, "Consulta de Saldo", JOptionPane.INFORMATION_MESSAGE);
        });
        
        btnRegresar.addActionListener(e -> dispose());
    }

    // Método para validar que la cuenta exista
    private void iniciarSesion() {
        String num = txtNumCuenta.getText();
        
        if (dao.iniciarSesion(num)) {
            idCuentaActiva = dao.obtenerIdCuenta(num);
            JOptionPane.showMessageDialog(this, "Sesión iniciada. Ya puedes realizar operaciones.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            
            // Bloquear caja de cuenta para que no la cambien
            txtNumCuenta.setEnabled(false);
            btnIniciar.setEnabled(false);
            
            // Desbloquear los controles del cajero
            txtMonto.setEnabled(true);
            btnDepositar.setEnabled(true);
            btnRetirar.setEnabled(true);
            btnSaldo.setEnabled(true);
        } else {
            JOptionPane.showMessageDialog(this, "La cuenta no existe en la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método para evitar que metan letras o números negativos
    private boolean validarMonto() {
        try {
            double m = Double.parseDouble(txtMonto.getText());
            if (m <= 0) {
                JOptionPane.showMessageDialog(this, "El monto debe ser mayor a 0.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                return false;
            }
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ingresa un monto numérico válido.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
}