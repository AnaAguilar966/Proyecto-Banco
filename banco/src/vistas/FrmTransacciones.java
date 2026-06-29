package vistas;

import crud.ConsultasCuentaA;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class FrmTransacciones extends JFrame {

    private JTextField txtNumCuenta, txtMonto;
    private JButton btnIniciar, btnDepositar, btnRetirar, btnRegresar;
    private JLabel lblSaldo; // NUEVO: Etiqueta para mostrar el saldo dinámicamente
    private int idCuentaActiva = -1;
    private ConsultasCuentaA dao = new ConsultasCuentaA();

    public FrmTransacciones() {
        setTitle("Cajero Automático");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Ajustamos la cuadrícula a 5 filas y 2 columnas para que quede perfecto
        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 15));
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
        
        // Aquí agregamos la etiqueta del saldo en lugar de un botón
        lblSaldo = new JLabel(""); 
        lblSaldo.setForeground(new Color(11, 232, 129)); // Color verde brillante
        lblSaldo.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(lblSaldo);

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

        // Rellenamos el espacio vacío y ponemos el botón de regresar
        panel.add(new JLabel("")); 

        btnRegresar = new JButton("Regresar");
        btnRegresar.setBackground(new Color(255, 71, 87));
        btnRegresar.setForeground(textoColor);
        panel.add(btnRegresar);

        btnIniciar.addActionListener(e -> iniciarSesion());
        btnDepositar.addActionListener(e -> procesarTransaccion("deposito"));
        btnRetirar.addActionListener(e -> procesarTransaccion("retiro"));
        btnRegresar.addActionListener(e -> dispose());
    }

    private void iniciarSesion() {
        String num = txtNumCuenta.getText();
        if (dao.iniciarSesion(num)) {
            idCuentaActiva = dao.obtenerIdCuenta(num);
            
            // LÓGICA NUEVA: Consultamos el saldo automáticamente
            double saldoActual = dao.consultarSaldo(idCuentaActiva);
            lblSaldo.setText(" Saldo: $" + saldoActual); // Lo ponemos en pantalla
            
            // Lo mostramos en el mensaje de bienvenida
            JOptionPane.showMessageDialog(this, "Sesión iniciada.\nTu saldo disponible es: $" + saldoActual, "Bienvenido", JOptionPane.INFORMATION_MESSAGE);
            
            txtNumCuenta.setEnabled(false);
            btnIniciar.setEnabled(false);
            txtMonto.setEnabled(true);
            btnDepositar.setEnabled(true);
            btnRetirar.setEnabled(true);
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
                // Actualizamos el saldo visualmente
                double nuevoSaldo = dao.consultarSaldo(idCuentaActiva);
                lblSaldo.setText(" Saldo: $" + nuevoSaldo);
                JOptionPane.showMessageDialog(this, "Depósito procesado.\nTu nuevo saldo es: $" + nuevoSaldo, "Éxito", JOptionPane.INFORMATION_MESSAGE);
            
            } else { // Si es retiro
                double saldoAntes = dao.consultarSaldo(idCuentaActiva);
                dao.retirar(idCuentaActiva, monto);
                double saldoDespues = dao.consultarSaldo(idCuentaActiva);
                
                // Validación para saber si realmente se retiró el dinero
                if (saldoAntes == saldoDespues) {
                    JOptionPane.showMessageDialog(this, "Fondos insuficientes para retirar $" + monto, "Error de Fondos", JOptionPane.ERROR_MESSAGE);
                } else {
                    lblSaldo.setText(" Saldo: $" + saldoDespues);
                    JOptionPane.showMessageDialog(this, "Retiro procesado.\nTu nuevo saldo es: $" + saldoDespues, "Éxito", JOptionPane.INFORMATION_MESSAGE);
                }
            }
            txtMonto.setText(""); // Limpiamos la caja de texto
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ingresa un monto válido mayor a 0.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
        }
    }
}