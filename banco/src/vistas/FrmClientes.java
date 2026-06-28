package vistas;

import crud.ConsultasCliente;
import entidades.cliente;
import javax.swing.*;
import java.awt.*;

public class FrmClientes extends JFrame {
    
    // Elementos de la ventana
    private JTextField txtNombre;
    private JTextField txtTelefono;
    private JTextField txtCorreo;
    private JButton btnGuardar;
    private JButton btnRegresar;

    public FrmClientes() {
        // Configuración básica de la ventana
        setTitle("Gestión de Clientes");
        setSize(350, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Cierra solo esta ventana
        setLocationRelativeTo(null); // Centra la ventana en la pantalla
        setLayout(new GridLayout(5, 2, 10, 10)); // Cuadrícula para acomodar todo

        // Agregando los textos y las cajas de texto
        add(new JLabel(" Nombre Completo:"));
        txtNombre = new JTextField();
        add(txtNombre);

        add(new JLabel(" Teléfono:"));
        txtTelefono = new JTextField();
        add(txtTelefono);

        add(new JLabel(" Correo Electrónico:"));
        txtCorreo = new JTextField();
        add(txtCorreo);

        // Agregando los botones
        btnGuardar = new JButton("Guardar Cliente");
        btnRegresar = new JButton("Regresar al Menú");

        add(btnGuardar);
        add(btnRegresar);

        // Funcionalidad del botón Guardar
        btnGuardar.addActionListener(e -> guardarCliente());

        // Funcionalidad del botón Regresar
        btnRegresar.addActionListener(e -> dispose());
    }

    // Método que se ejecuta al darle clic a "Guardar Cliente"
    private void guardarCliente() {
        String nombre = txtNombre.getText();
        String telefono = txtTelefono.getText();
        String correo = txtCorreo.getText();

        // Validar que no haya campos vacíos
        if (nombre.isEmpty() || telefono.isEmpty() || correo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Crear el objeto cliente y mandarlo a la base de datos
        cliente nuevoCliente = new cliente(0, nombre, telefono, correo);
        ConsultasCliente dao = new ConsultasCliente();
        
        int idGenerado = dao.registrarCliente(nuevoCliente);
        
        if (idGenerado != -1) {
            JOptionPane.showMessageDialog(this, "Cliente registrado con éxito.\nTu ID es: " + idGenerado, "Éxito", JOptionPane.INFORMATION_MESSAGE);
            // Limpiar las cajas para registrar otro
            txtNombre.setText("");
            txtTelefono.setText("");
            txtCorreo.setText("");
        } else {
            JOptionPane.showMessageDialog(this, "Error al registrar en la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}