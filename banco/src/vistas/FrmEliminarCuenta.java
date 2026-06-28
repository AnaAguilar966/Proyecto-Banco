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
        setTitle("🗑️ Cancelación de Cuenta");
        setSize(350, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 20));
        panel.setBackground(new Color(30, 39, 46));
        panel.setBorder(new EmptyBorder(30, 20, 30, 20));
        setContentPane(panel);

        JLabel lbl1 = new JLabel("ID de la Cuenta:");
        lbl1.setForeground(Color.WHITE);
        txtIdCuenta = new JTextField();

        btnEliminar = new JButton("Eliminar Definitivamente");
        btnEliminar.setBackground(new Color(255, 71, 87)); // Rojo
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
            int id = Integer.parseInt(txtIdCuenta.getText());
            ConsultasCredito daoCredito = new ConsultasCredito();

            // 1. REGLA DE NEGOCIO: Validar si debe dinero
            if (daoCredito.tieneCreditoActivo(id)) {
                JOptionPane.showMessageDialog(this, 
                    "⚠️ ALERTA: Antes de eliminar la cuenta, necesita pagar su crédito activo.", 
                    "Crédito Pendiente", JOptionPane.WARNING_MESSAGE);
                
                // Abre la ventana de pagar créditos automáticamente
                new FrmAbonarCredito().setVisible(true);
                this.dispose(); // Cierra esta ventana
                return;
            }

            // 2. Si no debe nada, confirmar y eliminar
            int confirm = JOptionPane.showConfirmDialog(this, "¿Seguro que desea borrar esta cuenta y sus movimientos?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                new ConsultasCuentaA().eliminarCuenta(id);
                JOptionPane.showMessageDialog(this, "Cuenta eliminada del sistema.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Ingresa un ID numérico válido.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}