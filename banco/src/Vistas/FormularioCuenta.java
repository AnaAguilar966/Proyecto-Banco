package Vistas;

import javax.swing.*;
import CRUD.ConsultasCuentaA;

public class FormularioCuenta extends JFrame {

    JTextField txtCliente, txtNumero, txtSaldo;
    JButton btnCrear;

    public FormularioCuenta() {

        setTitle("Cuenta");
        setSize(300,200);
        setLocationRelativeTo(null);

        txtCliente = new JTextField(10);
        txtNumero = new JTextField(10);
        txtSaldo = new JTextField(10);

        btnCrear = new JButton("Crear");

        btnCrear.addActionListener(e -> crear());

        JPanel p = new JPanel();
        p.add(new JLabel("ID Cliente"));
        p.add(txtCliente);
        p.add(new JLabel("Numero"));
        p.add(txtNumero);
        p.add(new JLabel("Saldo"));
        p.add(txtSaldo);
        p.add(btnCrear);

        add(p);
    }

    private void crear() {
        new ConsultasCuentaA().crearCuenta(
                Integer.parseInt(txtCliente.getText()),
                txtNumero.getText(),
                Double.parseDouble(txtSaldo.getText())
        );

        JOptionPane.showMessageDialog(this,"Cuenta creada");
    }
}