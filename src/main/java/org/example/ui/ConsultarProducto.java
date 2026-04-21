package org.example.ui;

import org.example.app.Producto;
import org.example.dao.ProductoDAO;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

public class ConsultarProducto extends JFrame {

    private final ProductoDAO productoDAO;
    private JTextField txtModelo;
    private JTextArea txtResultado;

    public ConsultarProducto() {
        this.productoDAO = new ProductoDAO();
        initUI();
    }

    private void initUI() {
        setTitle("Consulta de Producto");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BorderLayout());
        panelPrincipal.setBorder(new EmptyBorder(15, 20, 15, 20));
        panelPrincipal.setBackground(Color.WHITE);

        JLabel titulo = new JLabel("Consulta de Producto", SwingConstants.CENTER);
        titulo.setFont(new Font("Tahoma", Font.BOLD, 20));
        titulo.setBorder(new EmptyBorder(10, 0, 20, 0));
        panelPrincipal.add(titulo, BorderLayout.NORTH);

        JPanel panelBusqueda = new JPanel(new BorderLayout(8, 8));
        panelBusqueda.setBackground(Color.WHITE);

        txtModelo = new JTextField();
        JButton btnBuscar = new JButton("Buscar");
        btnBuscar.addActionListener(e -> buscar());

        panelBusqueda.add(new JLabel("Modelo:"), BorderLayout.WEST);
        panelBusqueda.add(txtModelo, BorderLayout.CENTER);
        panelBusqueda.add(btnBuscar, BorderLayout.EAST);

        txtResultado = new JTextArea();
        txtResultado.setEditable(false);
        txtResultado.setFont(new Font("Monospaced", Font.PLAIN, 13));

        JPanel centro = new JPanel(new BorderLayout(8, 8));
        centro.setBackground(Color.WHITE);
        centro.add(panelBusqueda, BorderLayout.NORTH);
        centro.add(new JScrollPane(txtResultado), BorderLayout.CENTER);

        panelPrincipal.add(centro, BorderLayout.CENTER);

        setContentPane(panelPrincipal);
    }

    private void buscar() {
        String modelo = txtModelo.getText().trim();
        if (modelo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese el modelo para consultar.");
            return;
        }

        Producto producto = productoDAO.buscarPorModelo(modelo);
        if (producto == null) {
            txtResultado.setText("No se encontró el producto con modelo: " + modelo);
            return;
        }

        txtResultado.setText(formatoProducto(producto));
    }

    public Producto seleccionarProducto(Component parent) {
        List<String> modelos = productoDAO.obtenerModelos();
        if (modelos.isEmpty()) {
            JOptionPane.showMessageDialog(parent, "No hay productos para consultar.");
            return null;
        }

        String modelo = (String) JOptionPane.showInputDialog(
                parent,
                "Seleccione un producto:",
                "Consultar producto",
                JOptionPane.PLAIN_MESSAGE,
                null,
                modelos.toArray(),
                modelos.get(0)
        );

        if (modelo == null) {
            return null;
        }

        Producto producto = productoDAO.buscarPorModelo(modelo);
        if (producto != null) {
            JOptionPane.showMessageDialog(parent, formatoProducto(producto), "Producto encontrado", JOptionPane.INFORMATION_MESSAGE);
        }
        return producto;
    }

    private String formatoProducto(Producto p) {
        StringBuilder sb = new StringBuilder();
        sb.append("Marca: ").append(p.getMarca()).append("\n");
        sb.append("Modelo: ").append(p.getModelo()).append("\n");
        sb.append("Precio: $").append(p.getPrecio()).append("\n");
        sb.append("Dimensiones: ").append(p.getDimensiones()).append("\n");
        sb.append("Stock Disponible: ").append(p.getStockDisponible()).append("\n");
        sb.append("Stock Almacen: ").append(p.getStockAlmacen());
        return sb.toString();
    }
}