package Vistas;

import javax.swing.*;
import CRUD.ConsultasCliente;
import Entidades.cliente;

public class FormularioCliente extends JFrame {

    JTextField txtNombre, txtTelefono, txtCorreo, txtId;
    JButton btnGuardar, btnActualizar, btnEliminar;

    public FormularioCliente() {

        setTitle("Clientes");
        setSize(400,300);
        setLocationRelativeTo(null);

        txtId = new JTextField(5);
        txtNombre = new JTextField(10);
        txtTelefono = new JTextField(10);
        txtCorreo = new JTextField(10);

        btnGuardar = new JButton("Guardar");

        btnGuardar.addActionListener(e -> guardar());

        JPanel p = new JPanel();
        p.add(new JLabel("ID"));
        p.add(txtId);
        p.add(new JLabel("Nombre"));
        p.add(txtNombre);
        p.add(new JLabel("Tel"));
        p.add(txtTelefono);
        p.add(new JLabel("Correo"));
        p.add(txtCorreo);

        p.add(btnGuardar);
        p.add(btnActualizar);
        p.add(btnEliminar);

        add(p);
    }

    private void guardar() {
        if(txtNombre.getText().isEmpty()){
            JOptionPane.showMessageDialog(this,"Nombre requerido");
            return;
        }

        cliente c = new cliente(0,
                txtNombre.getText(),
                txtTelefono.getText(),
                txtCorreo.getText());

        new ConsultasCliente().registrarCliente(c);
        JOptionPane.showMessageDialog(this,"Guardado");
    }

    }