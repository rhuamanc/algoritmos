package org.example.ui;

import org.example.app.Producto;
import org.example.app.Venta;
import org.example.dao.ProductoDAO;
import org.example.dao.VentaDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class VentasUI extends JInternalFrame {

    private final ProductoDAO productoDAO;
    private final VentaDAO ventaDAO;

    private JComboBox<String> cmbModelo;
    private JTextField txtCantidad;
    private JTextField txtPrecioUnitario;
    private JLabel lblTotal;
    private JTextArea txtBoleta;
    private JTable tabla;

    private DefaultTableModel tablaModel;
    private final List<ItemVenta> carrito;

    private final DecimalFormat money = new DecimalFormat("0.00");

    public VentasUI() {
        super("Modulo de Ventas", true, true, true, true);
        this.productoDAO = new ProductoDAO();
        this.ventaDAO = new VentaDAO();
        this.carrito = new ArrayList<>();
        setSize(760, 520);
        setLayout(new BorderLayout());
        initUI();
    }

    private void initUI() {
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel panelEntrada = new JPanel(new GridLayout(2, 4, 8, 8));

        cmbModelo = new JComboBox<>();
        recargarModelos();
        cmbModelo.addActionListener(e -> actualizarPrecio());

        txtCantidad = new JTextField();
        txtPrecioUnitario = new JTextField();
        txtPrecioUnitario.setEditable(false);

        JButton btnAgregar = new JButton("Agregar al carrito");
        JButton btnQuitar = new JButton("Quitar seleccionado");
        JButton btnGuardarVenta = new JButton("Guardar venta");

        btnAgregar.addActionListener(e -> agregarAlCarrito());
        btnQuitar.addActionListener(e -> quitarSeleccionado());
        btnGuardarVenta.addActionListener(e -> guardarVenta());

        panelEntrada.add(new JLabel("Modelo:"));
        panelEntrada.add(cmbModelo);
        panelEntrada.add(new JLabel("Cantidad:"));
        panelEntrada.add(txtCantidad);
        panelEntrada.add(new JLabel("Precio unitario:"));
        panelEntrada.add(txtPrecioUnitario);
        panelEntrada.add(btnAgregar);
        panelEntrada.add(btnQuitar);

        tablaModel = new DefaultTableModel(new Object[]{"Modelo", "Precio", "Cantidad", "Subtotal"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabla = new JTable(tablaModel);
        JScrollPane scrollTabla = new JScrollPane(tabla);

        JPanel panelInferior = new JPanel(new BorderLayout(8, 8));
        lblTotal = new JLabel("Total: $0.00");
        lblTotal.setFont(new Font("SansSerif", Font.BOLD, 15));

        txtBoleta = new JTextArea(8, 30);
        txtBoleta.setEditable(false);
        txtBoleta.setFont(new Font("Monospaced", Font.PLAIN, 12));

        JPanel panelAcciones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelAcciones.add(lblTotal);
        panelAcciones.add(btnGuardarVenta);

        panelInferior.add(new JScrollPane(txtBoleta), BorderLayout.CENTER);
        panelInferior.add(panelAcciones, BorderLayout.SOUTH);

        panelPrincipal.add(panelEntrada, BorderLayout.NORTH);
        panelPrincipal.add(scrollTabla, BorderLayout.CENTER);
        panelPrincipal.add(panelInferior, BorderLayout.SOUTH);

        add(panelPrincipal);
        actualizarPrecio();
    }

    public void recargarModelos() {
        cmbModelo.removeAllItems();
        for (String modelo : productoDAO.obtenerModelos()) {
            cmbModelo.addItem(modelo);
        }
    }

    private void actualizarPrecio() {
        String modelo = (String) cmbModelo.getSelectedItem();
        if (modelo == null) {
            txtPrecioUnitario.setText("");
            return;
        }

        Producto producto = productoDAO.buscarPorModelo(modelo);
        if (producto == null) {
            txtPrecioUnitario.setText("");
            return;
        }

        txtPrecioUnitario.setText(money.format(producto.getPrecio()));
    }

    private void agregarAlCarrito() {
        String modelo = (String) cmbModelo.getSelectedItem();
        if (modelo == null || modelo.isBlank()) {
            JOptionPane.showMessageDialog(this, "No hay productos para vender.");
            return;
        }

        Producto producto = productoDAO.buscarPorModelo(modelo);
        if (producto == null) {
            JOptionPane.showMessageDialog(this, "Producto no encontrado.");
            return;
        }

        int cantidad;
        try {
            cantidad = Integer.parseInt(txtCantidad.getText().trim());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Ingrese cantidad numerica valida.");
            return;
        }

        if (cantidad <= 0) {
            JOptionPane.showMessageDialog(this, "La cantidad debe ser mayor que 0.");
            return;
        }

        int yaEnCarrito = 0;
        for (ItemVenta item : carrito) {
            if (item.modelo.equalsIgnoreCase(modelo)) {
                yaEnCarrito += item.cantidad;
            }
        }

        if (cantidad + yaEnCarrito > producto.getStockDisponible()) {
            JOptionPane.showMessageDialog(this, "Stock insuficiente para ese producto.");
            return;
        }

        double subtotal = producto.getPrecio() * cantidad;
        ItemVenta item = new ItemVenta(modelo, producto.getPrecio(), cantidad, subtotal);
        carrito.add(item);
        tablaModel.addRow(new Object[]{
                item.modelo,
                money.format(item.precio),
                item.cantidad,
                money.format(item.subtotal)
        });

        txtCantidad.setText("");
        recalcularTotal();
    }

    private void quitarSeleccionado() {
        int row = tabla.getSelectedRow();

        if (row < 0 || row >= carrito.size()) {
            JOptionPane.showMessageDialog(this, "Seleccione una fila para quitar.");
            return;
        }

        carrito.remove(row);
        tablaModel.removeRow(row);
        recalcularTotal();
    }

    private void recalcularTotal() {
        double total = 0;
        for (ItemVenta item : carrito) {
            total += item.subtotal;
        }
        lblTotal.setText("Total: $" + money.format(total));
    }

    private void guardarVenta() {
        if (carrito.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Agregue productos al carrito antes de guardar.");
            return;
        }

        for (ItemVenta item : carrito) {
            boolean ok = productoDAO.registrarVenta(item.modelo, item.cantidad);
            if (!ok) {
                JOptionPane.showMessageDialog(this, "Error al descontar stock de " + item.modelo + ".");
                return;
            }
        }

        double total = 0;
        StringBuilder detalle = new StringBuilder();
        for (ItemVenta item : carrito) {
            total += item.subtotal;
            detalle.append(item.modelo)
                    .append(" | ")
                    .append(item.cantidad)
                    .append(" x $")
                    .append(money.format(item.precio))
                    .append(" = $")
                    .append(money.format(item.subtotal))
                    .append("\n");
        }

        String numero = "B" + System.currentTimeMillis();
        String fecha = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
        String boletaTexto = "BOLETA " + numero + "\n"
                + "Fecha: " + fecha + "\n"
                + "--------------------------------\n"
                + detalle
                + "--------------------------------\n"
                + "TOTAL: $" + money.format(total);

        Venta venta = new Venta(numero, fecha, new ArrayList<>(carrito), total, boletaTexto);
        ventaDAO.guardar(venta);

        txtBoleta.setText(boletaTexto);
        JOptionPane.showMessageDialog(this, "Venta guardada correctamente.");

        carrito.clear();
        tablaModel.setRowCount(0);
        recalcularTotal();
        recargarModelos();
    }

    public static class ItemVenta {
        private final String modelo;
        private final double precio;
        private final int cantidad;
        private final double subtotal;

        public ItemVenta(String modelo, double precio, int cantidad, double subtotal) {
            this.modelo = modelo;
            this.precio = precio;
            this.cantidad = cantidad;
            this.subtotal = subtotal;
        }

        public String getModelo() {
            return modelo;
        }

        public double getPrecio() {
            return precio;
        }

        public int getCantidad() {
            return cantidad;
        }

        public double getSubtotal() {
            return subtotal;
        }
    }
}
