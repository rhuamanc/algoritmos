package org.example.service;

import org.example.app.Producto;
import org.example.dao.ProductoDAO;

public class ListadoProductoService {

    public String generarListadoTexto() {
        ProductoDAO productoDAO = new ProductoDAO();
        StringBuilder sb = new StringBuilder();

        if (productoDAO.obtenerTodos().isEmpty()) {
            return "No hay productos registrados.";
        }

        for (Producto p : productoDAO.obtenerTodos()) {
            sb.append("Marca: ").append(p.getMarca()).append("\n");
            sb.append("Modelo: ").append(p.getModelo()).append("\n");
            sb.append("Precio: $").append(p.getPrecio()).append("\n");
            sb.append("Dimensiones: ").append(p.getDimensiones()).append("\n");
            sb.append("Stock Disponible: ").append(p.getStockDisponible()).append(" unidades\n");
            sb.append("Stock de Almacén: ").append(p.getStockAlmacen()).append(" unidades\n");
            sb.append("---------------------------------------\n\n");
        }

        return sb.toString();
    }
}