package vistas;

import crud.ConsultasCuentaA;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class MenuPrincipal extends JFrame {

    public MenuPrincipal() {
        setTitle("Sistema de Gestión Bancaria");
        setSize(550, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        Color colorFondo = new Color(30, 39, 46);
        Color colorBotones = new Color(72, 84, 96);
        Color colorTexto = Color.WHITE;
        Color colorAcento = new Color(11, 232, 129);

        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(colorFondo);
        panelPrincipal.setBorder(new EmptyBorder(20, 20, 20, 20));
        setContentPane(panelPrincipal);

        JLabel lblTitulo = new JLabel("Banca Digital UNSIJ", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblTitulo.setForeground(colorAcento);
        lblTitulo.setBorder(new EmptyBorder(0, 0, 25, 0));
        panelPrincipal.add(lblTitulo, BorderLayout.NORTH);

        JPanel panelBotones = new JPanel(new GridLayout(4, 2, 15, 15));
        panelBotones.setBackground(colorFondo);

        JButton btnClientes = crearBotonElegante("1. Registrar Cliente", colorBotones, colorTexto);
        JButton btnCrearC = crearBotonElegante("2. Aperturar Cuenta", colorBotones, colorTexto);
        JButton btnCajero = crearBotonElegante("3. Cajero (Depósito/Retiro)", colorBotones, colorTexto);
        JButton btnSolicitarC = crearBotonElegante("4. Solicitar Préstamo", colorBotones, colorTexto);
        JButton btnPagarC = crearBotonElegante("5. Pagar Préstamo", colorBotones, colorTexto);
        JButton btnReportes = crearBotonElegante("6. Exportar Reporte", colorBotones, colorTexto);
        JButton btnEliminarC = crearBotonElegante("7. Cancelar Cuenta", colorBotones, colorTexto);
        JButton btnSalir = crearBotonElegante("8. Salir del Sistema", new Color(255, 71, 87), colorTexto);

        panelBotones.add(btnClientes);
        panelBotones.add(btnCrearC);
        panelBotones.add(btnCajero);
        panelBotones.add(btnSolicitarC);
        panelBotones.add(btnPagarC);
        panelBotones.add(btnReportes);
        panelBotones.add(btnEliminarC);
        panelBotones.add(btnSalir);

        panelPrincipal.add(panelBotones, BorderLayout.CENTER);

        // Enlaces a las ventanas
        btnClientes.addActionListener(e -> new FrmClientes().setVisible(true));
        btnCrearC.addActionListener(e -> new FrmCrearCuenta().setVisible(true));
        btnCajero.addActionListener(e -> new FrmTransacciones().setVisible(true));
        btnSolicitarC.addActionListener(e -> new FrmSolicitarCredito().setVisible(true));
        btnPagarC.addActionListener(e -> new FrmAbonarCredito().setVisible(true));
        btnEliminarC.addActionListener(e -> new FrmEliminarCuenta().setVisible(true));

        btnReportes.addActionListener(e -> {
            new ConsultasCuentaA().exportarCuentasTXT();
            JOptionPane.showMessageDialog(this, "Archivo 'cuentas_banco.txt' generado exitosamente.", "Reporte Exportado", JOptionPane.INFORMATION_MESSAGE);
        });

        btnSalir.addActionListener(e -> {
            if (JOptionPane.showConfirmDialog(this, "¿Seguro que desea salir?", "Confirmar", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });
    }

    private JButton crearBotonElegante(String texto, Color fondo, Color textoColor) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Segoe UI", Font.BOLD, 13));
        boton.setBackground(fondo);
        boton.setForeground(textoColor);
        boton.setFocusPainted(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return boton;
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> new MenuPrincipal().setVisible(true));
    }
}
