package org.example.dao;

import org.example.app.Venta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class VentaDAO {

    private static final List<Venta> ventas = new ArrayList<>();

    public void guardar(Venta venta) {
        ventas.add(venta);
    }

    public List<Venta> obtenerTodas() {
        return Collections.unmodifiableList(ventas);
    }
}
