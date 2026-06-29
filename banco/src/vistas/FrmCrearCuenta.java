package vistas;

import crud.ConsultasCuentaA;
import entidades.CuentaAhorro;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class FrmCrearCuenta extends JFrame {

    private JTextField txtIdCliente, txtSaldo;
    private JButton btnCrear, btnRegresar;

    public FrmCrearCuenta() {
        setTitle("Apertura de Cuenta");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 20));
        panel.setBackground(new Color(30, 39, 46));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        setContentPane(panel);

        Color textoColor = Color.WHITE;

        JLabel lbl1 = new JLabel("ID del Cliente (Dueño):");
        lbl1.setForeground(textoColor);
        JLabel lbl3 = new JLabel("Saldo Inicial ($):");
        lbl3.setForeground(textoColor);

        txtIdCliente = new JTextField();
        txtSaldo = new JTextField();

        btnCrear = new JButton("Crear Cuenta");
        btnCrear.setBackground(new Color(11, 232, 129));

        btnRegresar = new JButton("Regresar");
        btnRegresar.setBackground(new Color(72, 84, 96));
        btnRegresar.setForeground(Color.WHITE);

        panel.add(lbl1);
        panel.add(txtIdCliente);
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
            double saldo = Double.parseDouble(txtSaldo.getText());

            if (saldo < 0) {
                JOptionPane.showMessageDialog(this, "El saldo inicial no puede ser negativo.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            ConsultasCuentaA daoCuenta = new ConsultasCuentaA();

            // === REGLA 1: Validar si el cliente existe ===
            if (!daoCuenta.existeCliente(idCliente)) {
                JOptionPane.showMessageDialog(this, 
                    "ERROR: El Cliente con ID " + idCliente + " NO existe.\nRegistre al cliente primero en el Módulo de Clientes.", 
                    "Cliente No Encontrado", JOptionPane.ERROR_MESSAGE);
                return; 
            }

            // === REGLA 2: Validar si ya tiene cuenta ===
            if (daoCuenta.tieneCuenta(idCliente)) {
                JOptionPane.showMessageDialog(this, 
                    "ERROR: Este cliente ya tiene una cuenta de ahorro activa.", 
                    "Cuenta Duplicada", JOptionPane.ERROR_MESSAGE);
                return; 
            }

            // === SI PASA LAS VALIDACIONES, SE CREA LA CUENTA ===
            CuentaAhorro nuevaCuenta = new CuentaAhorro();
            nuevaCuenta.setIdCliente(idCliente);
            nuevaCuenta.setSaldo(saldo);

            int nuevoIdCuenta = daoCuenta.registrarCuenta(nuevaCuenta);

            if (nuevoIdCuenta != -1) {
                // Formato autoincrementable visual
                String numeroCuentaFormateado = String.format("%010d", nuevoIdCuenta);
                String mensaje = "¡Cuenta Aperturada Exitosamente!\n\n"
                               + "ID Cliente (Propietario): " + idCliente + "\n"
                               + "NÚMERO DE CUENTA ASIGNADO: " + numeroCuentaFormateado + "\n\n"
                               + "Por favor, guarde este número para operaciones en cajero.";
                               
                JOptionPane.showMessageDialog(this, mensaje, "Operación Exitosa", JOptionPane.INFORMATION_MESSAGE);
                
                txtIdCliente.setText("");
                txtSaldo.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Error al registrar la cuenta. Verifique la base de datos.", "Error BD", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese valores numéricos válidos.", "Error de Formato", JOptionPane.WARNING_MESSAGE);
        }
    }
}