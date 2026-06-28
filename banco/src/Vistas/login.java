package Vistas;

import javax.swing.*;
import CRUD.ConsultasCuentaA;

public class login extends JFrame {

    JTextField txtCuenta;
    JButton btnEntrar;

    public login() {
        setTitle("Login Banco");
        setSize(300,200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        txtCuenta = new JTextField(15);
        btnEntrar = new JButton("Entrar");

        btnEntrar.addActionListener(e -> login());

        JPanel p = new JPanel();
        p.add(new JLabel("No. Cuenta:"));
        p.add(txtCuenta);
        p.add(btnEntrar);

        add(p);
    }

    private void login() {
        String cuenta = txtCuenta.getText();

        if(cuenta.isEmpty()){
            JOptionPane.showMessageDialog(this,"Campo vacío");
            return;
        }

        ConsultasCuentaA dao = new ConsultasCuentaA();

        if(dao.iniciarSesion(cuenta)){
            new MenuPrincipal().setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this,"Cuenta no existe");
        }
    }
}