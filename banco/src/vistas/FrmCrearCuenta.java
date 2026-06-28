package vistas;

import crud.ConsultasCuentaA;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class FrmCrearCuenta extends JFrame {

    private JTextField txtIdCliente, txtNumCuenta, txtSaldo;
    private JButton btnCrear, btnRegresar;

    public FrmCrearCuenta() {
        setTitle("Apertura de Cuenta");
        setSize(400, 320);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 20));
        panel.setBackground(new Color(30, 39, 46));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        setContentPane(panel);

        Color textoColor = Color.WHITE;

        JLabel lbl1 = new JLabel("ID del Cliente (Dueño):");
        lbl1.setForeground(textoColor);
        JLabel lbl2 = new JLabel("<html>Cree un Número de Cuenta<br>(Ej: 123456789):</html>");
        lbl2.setForeground(textoColor);
        JLabel lbl3 = new JLabel("Saldo Inicial ($):");
        lbl3.setForeground(textoColor);

        txtIdCliente = new JTextField();
        txtNumCuenta = new JTextField();
        txtSaldo = new JTextField();

        btnCrear = new JButton("Crear Cuenta");
        btnCrear.setBackground(new Color(11, 232, 129));

        btnRegresar = new JButton("Regresar");
        btnRegresar.setBackground(new Color(72, 84, 96));
        btnRegresar.setForeground(Color.WHITE);

        panel.add(lbl1);
        panel.add(txtIdCliente);
        panel.add(lbl2);
        panel.add(txtNumCuenta);
        panel.add(lbl3);
        panel.add(txtSaldo);
        panel.add(btnCrear);
        panel.add(btnRegresar);

        btnCrear.addActionListener(e -> crearCuenta());
        btnRegresar.addActionListener(e -> dispose());
    }

    private void crearCuenta() {
        try {
            int idCliente = Integer.parseInt(txtIdCliente.getText());
            String numCuenta = txtNumCuenta.getText();
            double saldo = Double.parseDouble(txtSaldo.getText());

            new ConsultasCuentaA().crearCuenta(idCliente, numCuenta, saldo);
            JOptionPane.showMessageDialog(this, "Cuenta aperturada.\nEl número oficial de esta cuenta es: " + numCuenta, "Éxito", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Verifica los datos ingresados.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
