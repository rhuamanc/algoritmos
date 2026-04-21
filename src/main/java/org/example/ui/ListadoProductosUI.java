package org.example.ui;

import org.example.service.ListadoProductoService;

import javax.swing.*;
import java.awt.*;

public class ListadoProductosUI extends JInternalFrame {

    private JTextArea txtListado;
    private JButton btnAceptar;
    private ListadoProductoService productoService;

    public ListadoProductosUI() {
        super("Listado de Productos", true, true, true, true);
        setSize(420,420);
        setLayout(new BorderLayout());
        productoService = new ListadoProductoService();
        initUI();
    }

    private void initUI() {
        initializeComponents();
        configureLayout();
        cargarListado();
    }

    private void initializeComponents() {
        txtListado = new JTextArea();
        txtListado.setFont(new Font("Monospaced", Font.PLAIN, 13));
        txtListado.setEditable(false);

        btnAceptar = new JButton("ACEPTAR");
    }

    private void configureLayout() {

        JPanel panelPrincipal = new JPanel(new BorderLayout(10,10));
        panelPrincipal.setBorder(new javax.swing.border.EmptyBorder(10, 15, 10, 15));

        JScrollPane scroll = new JScrollPane(txtListado);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        panelPrincipal.add(scroll, BorderLayout.CENTER);

        JPanel panelBoton = new JPanel();
        panelBoton.add(btnAceptar);
        panelPrincipal.add(panelBoton, BorderLayout.SOUTH);

        setContentPane(panelPrincipal);
    }

    public void cargarListado() {
        txtListado.setText(productoService.generarListadoTexto());
    }
}