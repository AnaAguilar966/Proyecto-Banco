package vistas;

import crud.ConsultasCliente;
import entidades.cliente;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class FrmClientes extends JFrame {

    private JTextField txtNombre, txtTelefono, txtCorreo;
    private JButton btnGuardar, btnRegresar;

    public FrmClientes() {
        setTitle("Registro de Clientes");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 20));
        panel.setBackground(new Color(30, 39, 46));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        setContentPane(panel);

        Color textoColor = Color.WHITE;

        JLabel lbl1 = new JLabel("Nombre Completo:");
        lbl1.setForeground(textoColor);
        JLabel lbl2 = new JLabel("Teléfono:");
        lbl2.setForeground(textoColor);
        JLabel lbl3 = new JLabel("Correo Electrónico:");
        lbl3.setForeground(textoColor);

        txtNombre = new JTextField();
        txtTelefono = new JTextField();
        txtCorreo = new JTextField();

        btnGuardar = new JButton("Guardar Cliente");
        btnGuardar.setBackground(new Color(11, 232, 129));

        btnRegresar = new JButton("Regresar");
        btnRegresar.setBackground(new Color(72, 84, 96));
        btnRegresar.setForeground(Color.WHITE);

        panel.add(lbl1);
        panel.add(txtNombre);
        panel.add(lbl2);
        panel.add(txtTelefono);
        panel.add(lbl3);
        panel.add(txtCorreo);
        panel.add(btnGuardar);
        panel.add(btnRegresar);

        btnGuardar.addActionListener(e -> guardarCliente());
        btnRegresar.addActionListener(e -> dispose());
    }

    private void guardarCliente() {
        String nombre = txtNombre.getText();
        String telefono = txtTelefono.getText();
        String correo = txtCorreo.getText();

        if (nombre.isEmpty() || telefono.isEmpty() || correo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor llena todos los campos.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        cliente c = new cliente(0, nombre, telefono, correo);
        int idGenerado = new ConsultasCliente().registrarCliente(c);

        if (idGenerado > 0) {
            JOptionPane.showMessageDialog(this, "Cliente registrado.\nSu ID para crear cuentas es: " + idGenerado, "Éxito", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Error al guardar en base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
