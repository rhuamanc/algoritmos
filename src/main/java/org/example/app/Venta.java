package org.example.app;

import org.example.ui.VentasUI;

import java.util.List;

public class Venta {

    private final String numeroBoleta;
    private final String fecha;
    private final List<VentasUI.ItemVenta> items;
    private final double total;
    private final String boletaTexto;

    public Venta(String numeroBoleta, String fecha, List<VentasUI.ItemVenta> items, double total, String boletaTexto) {
        this.numeroBoleta = numeroBoleta;
        this.fecha = fecha;
        this.items = items;
        this.total = total;
        this.boletaTexto = boletaTexto;
    }

    public String getNumeroBoleta() {
        return numeroBoleta;
    }

    public String getFecha() {
        return fecha;
    }

    public List<VentasUI.ItemVenta> getItems() {
        return items;
    }

    public double getTotal() {
        return total;
    }

    public String getBoletaTexto() {
        return boletaTexto;
    }
}
