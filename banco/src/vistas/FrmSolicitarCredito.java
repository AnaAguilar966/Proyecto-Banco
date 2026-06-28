package vistas;

import crud.ConsultasCredito;
import crud.ConsultasCuentaA;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class FrmSolicitarCredito extends JFrame {

    private JTextField txtNumCuenta, txtMonto;
    private JButton btnIniciar, btnSolicitar, btnRegresar;
    private int idCuentaActiva = -1;
    private ConsultasCredito daoCredito = new ConsultasCredito();

    public FrmSolicitarCredito() {
        setTitle("Solicitar Préstamo");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 15));
        panel.setBackground(new Color(30, 39, 46));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        setContentPane(panel);

        JLabel lbl1 = new JLabel("Número de Cuenta:");
        lbl1.setForeground(Color.WHITE);
        txtNumCuenta = new JTextField();

        btnIniciar = new JButton("Buscar Cuenta");
        btnIniciar.setBackground(new Color(72, 84, 96));
        btnIniciar.setForeground(Color.WHITE);

        JLabel lbl2 = new JLabel("Monto a Solicitar:");
        lbl2.setForeground(Color.WHITE);
        txtMonto = new JTextField();
        txtMonto.setEnabled(false);

        btnSolicitar = new JButton("Solicitar");
        btnSolicitar.setBackground(new Color(11, 232, 129));
        btnSolicitar.setEnabled(false);

        btnRegresar = new JButton("Regresar");
        btnRegresar.setBackground(new Color(255, 71, 87));
        btnRegresar.setForeground(Color.WHITE);

        panel.add(lbl1);
        panel.add(txtNumCuenta);
        panel.add(btnIniciar);
        panel.add(new JLabel(""));
        panel.add(lbl2);
        panel.add(txtMonto);
        panel.add(btnSolicitar);
        panel.add(btnRegresar);

        btnIniciar.addActionListener(e -> iniciarSesion());

        btnSolicitar.addActionListener(e -> {
            try {
                double monto = Double.parseDouble(txtMonto.getText());
                if (monto <= 0) {
                    throw new Exception();
                }

                if (daoCredito.tieneCreditoActivo(idCuentaActiva)) {
                    JOptionPane.showMessageDialog(this, "Solo puede tener un crédito activo. Pague el anterior primero.", "Denegado", JOptionPane.WARNING_MESSAGE);
                } else {
                    daoCredito.solicitarCredito(idCuentaActiva, monto);
                    JOptionPane.showMessageDialog(this, "Crédito aprobado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Ingrese un monto válido.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnRegresar.addActionListener(e -> dispose());
    }

    private void iniciarSesion() {
        String num = txtNumCuenta.getText();
        ConsultasCuentaA daoCuenta = new ConsultasCuentaA();
        if (daoCuenta.iniciarSesion(num)) {
            idCuentaActiva = daoCuenta.obtenerIdCuenta(num);
            JOptionPane.showMessageDialog(this, "Cuenta encontrada.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            txtNumCuenta.setEnabled(false);
            btnIniciar.setEnabled(false);
            txtMonto.setEnabled(true);
            btnSolicitar.setEnabled(true);
        } else {
            JOptionPane.showMessageDialog(this, "La cuenta no existe.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
