package org.example.ui;

import org.example.app.Venta;
import org.example.dao.VentaDAO;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class HistorialVentasUI extends JInternalFrame {

    private final VentaDAO ventaDAO;
    private JTextArea txtHistorial;

    public HistorialVentasUI() {
        super("Historial de Ventas", true, true, true, true);
        this.ventaDAO = new VentaDAO();
        setSize(700, 500);
        setLayout(new BorderLayout());
        initUI();
        cargarHistorial();
    }

    private void initUI() {
        JPanel panelPrincipal = new JPanel(new BorderLayout(8, 8));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel lblTitulo = new JLabel("Historial de Boletas", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 18));

        txtHistorial = new JTextArea();
        txtHistorial.setEditable(false);
        txtHistorial.setFont(new Font("Monospaced", Font.PLAIN, 12));

        JButton btnActualizar = new JButton("Actualizar");
        btnActualizar.addActionListener(e -> cargarHistorial());

        panelPrincipal.add(lblTitulo, BorderLayout.NORTH);
        panelPrincipal.add(new JScrollPane(txtHistorial), BorderLayout.CENTER);
        panelPrincipal.add(btnActualizar, BorderLayout.SOUTH);

        add(panelPrincipal);
    }

    private void cargarHistorial() {
        List<Venta> ventas = ventaDAO.obtenerTodas();
        if (ventas.isEmpty()) {
            txtHistorial.setText("No hay ventas registradas.");
            return;
        }

        StringBuilder sb = new StringBuilder();
        for (int i = ventas.size() - 1; i >= 0; i--) {
            Venta venta = ventas.get(i);
            sb.append(venta.getBoletaTexto()).append("\n\n");
        }

        txtHistorial.setText(sb.toString());
        txtHistorial.setCaretPosition(0);
    }
}
