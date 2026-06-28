package Vistas;

import javax.swing.*;

public class MenuPrincipal extends JFrame {

    public MenuPrincipal() {

        setTitle("Sistema Bancario");
        setSize(400,300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JButton btnCliente = new JButton("Clientes");
        JButton btnCuenta = new JButton("Cuentas");

        btnCliente.addActionListener(e -> {
            new FormularioCliente().setVisible(true);
        });

        btnCuenta.addActionListener(e -> {
            new FormularioCuenta().setVisible(true);
        });

        JPanel p = new JPanel();
        p.add(btnCliente);
        p.add(btnCuenta);

        add(p);
    }
}