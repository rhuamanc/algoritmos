package org.example.ui;

import javax.swing.*;

public class MenuPrincipalUI extends JFrame {
    private JDesktopPane desktopPane;

    public MenuPrincipalUI() {
        setTitle("Menu Principal");
        setSize(360, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        desktopPane = new JDesktopPane();
        setContentPane(desktopPane);
        initUI();
    }

    private void initUI() {
        // Crear la barra de menú horizontal
        JMenuBar menuBar = new JMenuBar();

        // Menú Productos
        JMenu menuProductos = new JMenu("Productos");
        JMenuItem itemListar = new JMenuItem("Listar productos");
        JMenuItem itemModificar = new JMenuItem("Modificar o registrar producto");
        menuProductos.add(itemListar);
        menuProductos.add(itemModificar);

        // Menú Ventas
        JMenu menuVentas = new JMenu("Ventas");
        JMenuItem itemVentas = new JMenuItem("Realizar venta");
        JMenuItem itemHistorial = new JMenuItem("Historial de ventas");
        menuVentas.add(itemVentas);
        menuVentas.add(itemHistorial);

        // Menú Salir
        JMenu menuSalir = new JMenu("Salir");
        JMenuItem itemSalir = new JMenuItem("Salir");
        menuSalir.add(itemSalir);

        // Agregar menús a la barra
        menuBar.add(menuProductos);
        menuBar.add(menuVentas);
        menuBar.add(menuSalir);
        setJMenuBar(menuBar);

        // Acciones de los ítems
        itemListar.addActionListener(e -> abrirInternalFrame(new ListadoProductosUI()));
        itemModificar.addActionListener(e -> abrirInternalFrame(new ModificarProductoUI()));
        itemVentas.addActionListener(e -> abrirInternalFrame(new VentasUI()));
        itemHistorial.addActionListener(e -> abrirInternalFrame(new HistorialVentasUI()));
        itemSalir.addActionListener(e -> System.exit(0));
    }

    private void abrirInternalFrame(JInternalFrame frame) {
        for (JInternalFrame f : desktopPane.getAllFrames()) {
            if (f.getClass().equals(frame.getClass())) {
                try {
                    f.setSelected(true);
                } catch (Exception ex) {}
                return;
            }
        }
        desktopPane.add(frame);
        frame.setVisible(true);
    }
}
