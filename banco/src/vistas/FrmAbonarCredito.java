package vistas;

import crud.ConsultasCredito;
import crud.ConsultasCuentaA;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class FrmAbonarCredito extends JFrame {
    private JTextField txtNumCuenta, txtMonto;
    private JLabel lblDeudaInfo;
    private JButton btnIniciar, btnPagar, btnRegresar;
    private int idCuentaActiva = -1;
    private double deudaActual = 0.0;
    private ConsultasCredito daoCredito = new ConsultasCredito();

    public FrmAbonarCredito() {
        setTitle("💳 Pagar Crédito");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 15));
        panel.setBackground(new Color(30, 39, 46));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        setContentPane(panel);

        JLabel lbl1 = new JLabel("Número de Cuenta:"); lbl1.setForeground(Color.WHITE);
        txtNumCuenta = new JTextField();
        
        btnIniciar = new JButton("Buscar Deuda");
        btnIniciar.setBackground(new Color(72, 84, 96)); btnIniciar.setForeground(Color.WHITE);

        lblDeudaInfo = new JLabel("Deuda Actual: $0.0"); 
        lblDeudaInfo.setForeground(new Color(255, 165, 2)); 
        lblDeudaInfo.setFont(new Font("Arial", Font.BOLD, 14));

        JLabel lbl2 = new JLabel("Monto a Abonar:"); lbl2.setForeground(Color.WHITE);
        txtMonto = new JTextField(); txtMonto.setEnabled(false);

        btnPagar = new JButton("Abonar");
        btnPagar.setBackground(new Color(11, 232, 129)); btnPagar.setEnabled(false);
        
        btnRegresar = new JButton("Regresar");
        btnRegresar.setBackground(new Color(255, 71, 87)); btnRegresar.setForeground(Color.WHITE);

        panel.add(lbl1); panel.add(txtNumCuenta);
        panel.add(btnIniciar); panel.add(lblDeudaInfo); 
        panel.add(lbl2); panel.add(txtMonto);
        panel.add(btnPagar); panel.add(btnRegresar);

        btnIniciar.addActionListener(e -> iniciarSesionYPonerDeuda());
        btnPagar.addActionListener(e -> procesarPago());
        btnRegresar.addActionListener(e -> dispose());
    }

    private void iniciarSesionYPonerDeuda() {
        String num = txtNumCuenta.getText();
        ConsultasCuentaA daoCuenta = new ConsultasCuentaA();
        
        if (daoCuenta.iniciarSesion(num)) {
            idCuentaActiva = daoCuenta.obtenerIdCuenta(num);
            
            if (!daoCredito.tieneCreditoActivo(idCuentaActiva)) {
                JOptionPane.showMessageDialog(this, "Esta cuenta NO tiene créditos pendientes.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            
            deudaActual = daoCredito.obtenerDeuda(idCuentaActiva);
            lblDeudaInfo.setText("Deuda Actual: $" + deudaActual);
            
            txtNumCuenta.setEnabled(false); btnIniciar.setEnabled(false);
            txtMonto.setEnabled(true); btnPagar.setEnabled(true);
        } else {
            JOptionPane.showMessageDialog(this, "La cuenta no existe.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void procesarPago() {
        try {
            double pago = Double.parseDouble(txtMonto.getText());
            if (pago <= 0) throw new Exception();

            if (pago > deudaActual) {
                JOptionPane.showMessageDialog(this, "❌ Error: El abono ($" + pago + ") supera tu deuda ($" + deudaActual + ").\nNo puedes pagar de más.", "Pago Rechazado", JOptionPane.ERROR_MESSAGE);
                return;
            }

            daoCredito.pagarCredito(idCuentaActiva, pago);
            
            deudaActual = daoCredito.obtenerDeuda(idCuentaActiva);
            lblDeudaInfo.setText("Deuda Actual: $" + deudaActual);
            txtMonto.setText("");

            if (deudaActual == 0) {
                JOptionPane.showMessageDialog(this, "🎉 ¡Has liquidado tu crédito por completo!", "Crédito Finalizado", JOptionPane.INFORMATION_MESSAGE);
                dispose(); 
            } else {
                JOptionPane.showMessageDialog(this, "✅ Abono realizado.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            }
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Ingrese un monto numérico válido.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}