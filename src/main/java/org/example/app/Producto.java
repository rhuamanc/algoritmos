package org.example.app;

public class Producto {

    private String marca;
    private String modelo;
    private double precio;
    private String dimensiones;
    private int stockDisponible;
    private int stockAlmacen;

    public Producto(String marca, String modelo, double precio,
                    String dimensiones, int stockDisponible, int stockAlmacen) {
        this.marca = marca;
        this.modelo = modelo;
        this.precio = precio;
        this.dimensiones = dimensiones;
        this.stockDisponible = stockDisponible;
        this.stockAlmacen = stockAlmacen;
    }

    public String getMarca() { return marca; }
    public String getModelo() { return modelo; }
    public double getPrecio() { return precio; }
    public String getDimensiones() { return dimensiones; }
    public int getStockDisponible() { return stockDisponible; }
    public int getStockAlmacen() { return stockAlmacen; }

    public void setMarca(String marca) { this.marca = marca; }
    public void setModelo(String modelo) { this.modelo = modelo; }
    public void setPrecio(double precio) { this.precio = precio; }
    public void setDimensiones(String dimensiones) { this.dimensiones = dimensiones; }
    public void setStockDisponible(int stockDisponible) { this.stockDisponible = stockDisponible; }
    public void setStockAlmacen(int stockAlmacen) { this.stockAlmacen = stockAlmacen; }
}