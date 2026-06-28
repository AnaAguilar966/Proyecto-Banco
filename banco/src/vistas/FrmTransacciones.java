package vistas;

import crud.ConsultasCuentaA;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class FrmTransacciones extends JFrame {

    private JTextField txtNumCuenta, txtMonto;
    private JButton btnIniciar, btnDepositar, btnRetirar, btnSaldo, btnRegresar;
    private int idCuentaActiva = -1;
    private ConsultasCuentaA dao = new ConsultasCuentaA();

    public FrmTransacciones() {
        setTitle("Cajero Automático");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 15));
        panel.setBackground(new Color(30, 39, 46));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        setContentPane(panel);

        Color textoColor = Color.WHITE;

        panel.add(new JLabel(" Número de Cuenta:")).setForeground(textoColor);
        txtNumCuenta = new JTextField();
        panel.add(txtNumCuenta);

        btnIniciar = new JButton("Iniciar Sesión");
        btnIniciar.setBackground(new Color(72, 84, 96));
        btnIniciar.setForeground(textoColor);
        panel.add(btnIniciar);
        panel.add(new JLabel(""));

        panel.add(new JLabel(" Monto de Operación:")).setForeground(textoColor);
        txtMonto = new JTextField();
        txtMonto.setEnabled(false);
        panel.add(txtMonto);

        btnDepositar = new JButton("Depositar");
        btnDepositar.setBackground(new Color(11, 232, 129));
        btnDepositar.setEnabled(false);
        btnRetirar = new JButton("Retirar");
        btnRetirar.setBackground(new Color(255, 165, 2));
        btnRetirar.setEnabled(false);
        panel.add(btnDepositar);
        panel.add(btnRetirar);

        btnSaldo = new JButton("Consultar Saldo");
        btnSaldo.setBackground(new Color(0, 151, 230));
        btnSaldo.setForeground(textoColor);
        btnSaldo.setEnabled(false);
        panel.add(btnSaldo);

        btnRegresar = new JButton("Regresar");
        btnRegresar.setBackground(new Color(255, 71, 87));
        btnRegresar.setForeground(textoColor);
        panel.add(btnRegresar);

        btnIniciar.addActionListener(e -> iniciarSesion());
        btnDepositar.addActionListener(e -> procesarTransaccion("deposito"));
        btnRetirar.addActionListener(e -> procesarTransaccion("retiro"));
        btnSaldo.addActionListener(e -> consultarSaldo());
        btnRegresar.addActionListener(e -> dispose());
    }

    private void iniciarSesion() {
        String num = txtNumCuenta.getText();
        if (dao.iniciarSesion(num)) {
            idCuentaActiva = dao.obtenerIdCuenta(num);
            JOptionPane.showMessageDialog(this, "Sesión iniciada. Ya puedes operar.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            txtNumCuenta.setEnabled(false);
            btnIniciar.setEnabled(false);
            txtMonto.setEnabled(true);
            btnDepositar.setEnabled(true);
            btnRetirar.setEnabled(true);
            btnSaldo.setEnabled(true);
        } else {
            JOptionPane.showMessageDialog(this, "La cuenta no existe.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void procesarTransaccion(String tipo) {
        try {
            double monto = Double.parseDouble(txtMonto.getText());
            if (monto <= 0) {
                throw new Exception();
            }

            if (tipo.equals("deposito")) {
                dao.depositar(idCuentaActiva, monto);
                JOptionPane.showMessageDialog(this, "Depósito procesado.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                dao.retirar(idCuentaActiva, monto);
                JOptionPane.showMessageDialog(this, "Retiro procesado. Si el saldo no cambió, no tenías fondos suficientes.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            }
            txtMonto.setText("");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ingresa un monto válido mayor a 0.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void consultarSaldo() {
        double saldo = dao.consultarSaldo(idCuentaActiva);
        JOptionPane.showMessageDialog(this, "Tu saldo actual es: $" + saldo, "Saldo", JOptionPane.INFORMATION_MESSAGE);
    }
}
