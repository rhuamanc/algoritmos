package org.example.ui;

import org.example.app.Producto;
import org.example.dao.ProductoDAO;

import javax.swing.*;
import java.awt.*;

public class ModificarProductoUI extends JInternalFrame {

    private final ProductoDAO productoDAO;
    private final ConsultarProducto consultarProducto;

    private JTextField txtMarca;
    private JTextField txtModelo;
    private JTextField txtPrecio;
    private JTextField txtDimensiones;
    private JTextField txtStockDisponible;
    private JTextField txtStockAlmacen;

    private String modeloSeleccionado;

    public ModificarProductoUI() {
        super("Modificar o Registrar Producto", true, true, true, true);
        this.productoDAO = new ProductoDAO();
        this.consultarProducto = new ConsultarProducto();
        setSize(520, 420);
        setLayout(new BorderLayout());
        initUI();
    }

    private void initUI() {
        JPanel panelFormulario = new JPanel(new GridLayout(6, 2, 8, 8));
        panelFormulario.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        txtMarca = new JTextField();
        txtModelo = new JTextField();
        txtPrecio = new JTextField();
        txtDimensiones = new JTextField();
        txtStockDisponible = new JTextField();
        txtStockAlmacen = new JTextField();

        panelFormulario.add(new JLabel("Marca:"));
        panelFormulario.add(txtMarca);
        panelFormulario.add(new JLabel("Modelo:"));
        panelFormulario.add(txtModelo);
        panelFormulario.add(new JLabel("Precio:"));
        panelFormulario.add(txtPrecio);
        panelFormulario.add(new JLabel("Dimensiones:"));
        panelFormulario.add(txtDimensiones);
        panelFormulario.add(new JLabel("Stock disponible:"));
        panelFormulario.add(txtStockDisponible);
        panelFormulario.add(new JLabel("Stock almacen:"));
        panelFormulario.add(txtStockAlmacen);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnConsultar = new JButton("Consultar para modificar");
        JButton btnModificar = new JButton("Modificar y guardar");
        JButton btnNuevo = new JButton("Guardar nuevo");

        btnConsultar.addActionListener(e -> consultarYcargar());
        btnModificar.addActionListener(e -> modificar());
        btnNuevo.addActionListener(e -> guardarNuevo());

        panelBotones.add(btnConsultar);
        panelBotones.add(btnNuevo);
        panelBotones.add(btnModificar);

        add(panelFormulario, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);
    }

    private void consultarYcargar() {
        Producto producto = consultarProducto.seleccionarProducto(this);
        if (producto == null) {
            return;
        }

        modeloSeleccionado = producto.getModelo();
        txtMarca.setText(producto.getMarca());
        txtModelo.setText(producto.getModelo());
        txtPrecio.setText(String.valueOf(producto.getPrecio()));
        txtDimensiones.setText(producto.getDimensiones());
        txtStockDisponible.setText(String.valueOf(producto.getStockDisponible()));
        txtStockAlmacen.setText(String.valueOf(producto.getStockAlmacen()));
    }

    private void modificar() {
        if (modeloSeleccionado == null || modeloSeleccionado.isBlank()) {
            JOptionPane.showMessageDialog(this, "Primero use 'Consultar para modificar'.");
            return;
        }

        try {
            Producto actualizado = leerFormulario();
            boolean ok = productoDAO.actualizar(modeloSeleccionado, actualizado);

            if (!ok) {
                JOptionPane.showMessageDialog(this, "No se pudo modificar. El modelo ya existe o no se encontro.");
                return;
            }

            modeloSeleccionado = actualizado.getModelo();
            JOptionPane.showMessageDialog(this, "Producto modificado y guardado correctamente.");
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void guardarNuevo() {
        try {
            Producto nuevo = leerFormulario();
            if (productoDAO.existeModelo(nuevo.getModelo())) {
                JOptionPane.showMessageDialog(this, "Ya existe un producto con ese modelo.");
                return;
            }

            productoDAO.agregar(nuevo);
            JOptionPane.showMessageDialog(this, "Producto nuevo guardado correctamente.");
            limpiarFormulario();
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private Producto leerFormulario() {
        String marca = txtMarca.getText().trim();
        String modelo = txtModelo.getText().trim();
        String dimensiones = txtDimensiones.getText().trim();

        if (marca.isEmpty() || modelo.isEmpty() || dimensiones.isEmpty()) {
            throw new IllegalArgumentException("Complete todos los campos de texto.");
        }

        try {
            double precio = Double.parseDouble(txtPrecio.getText().trim());
            int stockDisponible = Integer.parseInt(txtStockDisponible.getText().trim());
            int stockAlmacen = Integer.parseInt(txtStockAlmacen.getText().trim());

            if (precio < 0 || stockDisponible < 0 || stockAlmacen < 0) {
                throw new IllegalArgumentException("Precio y stocks deben ser >= 0.");
            }

            return new Producto(marca, modelo, precio, dimensiones, stockDisponible, stockAlmacen);
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Precio y stocks deben ser numericos validos.");
        }
    }

    private void limpiarFormulario() {
        txtMarca.setText("");
        txtModelo.setText("");
        txtPrecio.setText("");
        txtDimensiones.setText("");
        txtStockDisponible.setText("");
        txtStockAlmacen.setText("");
        modeloSeleccionado = null;
    }
}
