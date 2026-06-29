package vistas;

import crud.ConsultasCredito;
import crud.ConsultasCuentaA;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class FrmEliminarCuenta extends JFrame {
    private JTextField txtIdCuenta;
    private JButton btnEliminar, btnRegresar;

    public FrmEliminarCuenta() {
        setTitle("Cancelación de Cuenta");
        setSize(350, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 20));
        panel.setBackground(new Color(30, 39, 46));
        panel.setBorder(new EmptyBorder(30, 20, 30, 20));
        setContentPane(panel);

        JLabel lbl1 = new JLabel("No. de Cuenta a Borrar:");
        lbl1.setForeground(Color.WHITE);
        txtIdCuenta = new JTextField();

        btnEliminar = new JButton("Eliminar Definitivamente");
        btnEliminar.setBackground(new Color(255, 71, 87)); 
        btnEliminar.setForeground(Color.WHITE);
        
        btnRegresar = new JButton("Regresar");
        btnRegresar.setBackground(new Color(72, 84, 96));
        btnRegresar.setForeground(Color.WHITE);

        panel.add(lbl1); panel.add(txtIdCuenta);
        panel.add(btnEliminar); panel.add(btnRegresar);

        btnEliminar.addActionListener(e -> validarYEliminar());
        btnRegresar.addActionListener(e -> dispose());
    }

    private void validarYEliminar() {
        try {
            int idInput = Integer.parseInt(txtIdCuenta.getText());
            ConsultasCredito daoCredito = new ConsultasCredito();
            ConsultasCuentaA daoCuenta = new ConsultasCuentaA();

            // REGLA 1: Validar si tiene un crédito pendiente
            if (daoCredito.tieneCreditoActivo(idInput)) {
                JOptionPane.showMessageDialog(this, 
                    "ALERTA: Antes de cancelar la cuenta, necesita pagar su crédito activo.", 
                    "Crédito Pendiente", JOptionPane.WARNING_MESSAGE);
                
                new FrmAbonarCredito().setVisible(true); // Redirige a pagar
                this.dispose(); 
                return;
            }

            // REGLA 2 (NUEVA): Validar si la cuenta aún tiene dinero
            double saldoActual = daoCuenta.consultarSaldo(idInput);
            if (saldoActual > 0) {
                JOptionPane.showMessageDialog(this, 
                    "ALERTA: La cuenta aún tiene un saldo de $" + saldoActual + ".\n"
                  + "Por políticas del banco, debes retirar todos los fondos antes de cancelar la cuenta.", 
                    "Fondos Disponibles", JOptionPane.WARNING_MESSAGE);
                
                new FrmTransacciones().setVisible(true); // Redirige al cajero
                this.dispose(); 
                return;
            }

            // REGLA 3: Confirmación final si pasó las dos pruebas anteriores
            int confirm = JOptionPane.showConfirmDialog(this, 
                "¿Está seguro que desea eliminar la cuenta " + String.format("%010d", idInput) + " de forma permanente?", 
                "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
            
            if (confirm == JOptionPane.YES_OPTION) {
                boolean eliminado = daoCuenta.eliminarCuenta(idInput);
                if (eliminado) {
                    JOptionPane.showMessageDialog(this, "Cuenta eliminada del sistema correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Error: No se encontró ninguna cuenta con ese número.", "No Encontrada", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Ingresa un número de cuenta válido.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}