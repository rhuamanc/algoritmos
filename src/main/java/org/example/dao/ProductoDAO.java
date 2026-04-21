package org.example.dao;

import org.example.app.Producto;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProductoDAO {

    private static final List<Producto> lista = new ArrayList<>();

    static {
        lista.add(new Producto(
            "Samsung","TV Samsung 55",
                550.00,"60x65x170 cm",15,50));

        lista.add(new Producto(
            "LG","Celular Lg 60",
                420.00,"60x60x695 cm",10,40));

        lista.add(new Producto(
            "Panasonic","TV Panasonic 50",
                120.00,"45x35x28 cm",20,60));
    }

    public List<Producto> obtenerTodos() {
        return Collections.unmodifiableList(lista);
    }

    public void agregar(Producto p) {
        lista.add(p);
    }

    public Producto buscarPorModelo(String modelo) {
        for (Producto p : lista) {
            if (p.getModelo().equalsIgnoreCase(modelo)) {
                return p;
            }
        }
        return null;
    }

    public boolean existeModelo(String modelo) {
        return buscarPorModelo(modelo) != null;
    }

    public boolean actualizar(String modeloOriginal, Producto actualizado) {
        Producto existente = buscarPorModelo(modeloOriginal);
        if (existente == null) {
            return false;
        }

        if (!modeloOriginal.equalsIgnoreCase(actualizado.getModelo())
                && existeModelo(actualizado.getModelo())) {
            return false;
        }

        existente.setMarca(actualizado.getMarca());
        existente.setModelo(actualizado.getModelo());
        existente.setPrecio(actualizado.getPrecio());
        existente.setDimensiones(actualizado.getDimensiones());
        existente.setStockDisponible(actualizado.getStockDisponible());
        existente.setStockAlmacen(actualizado.getStockAlmacen());
        return true;
    }

    public boolean registrarVenta(String modelo, int cantidad) {
        if (cantidad <= 0) {
            return false;
        }

        Producto producto = buscarPorModelo(modelo);
        if (producto == null || producto.getStockDisponible() < cantidad) {
            return false;
        }

        producto.setStockDisponible(producto.getStockDisponible() - cantidad);
        return true;
    }

    public List<String> obtenerModelos() {
        List<String> modelos = new ArrayList<>();
        for (Producto producto : lista) {
            modelos.add(producto.getModelo());
        }
        return modelos;
    }
}