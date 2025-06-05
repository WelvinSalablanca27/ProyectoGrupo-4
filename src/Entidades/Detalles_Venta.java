/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

/**
 *
 * @author welvi
 */
public class Detalles_Venta {
    
    private int id_DetalleVenta;
    private int id_Venta;
    private int id_Producto;
    private double Precio_venta;
    private int cantidad_Producto;

    public Detalles_Venta() {
    }

    public Detalles_Venta(int id_DetalleVenta, int id_Venta, int id_Producto, double Precio_venta, int cantidad_Producto) {
        this.id_DetalleVenta = id_DetalleVenta;
        this.id_Venta = id_Venta;
        this.id_Producto = id_Producto;
        this.Precio_venta = Precio_venta;
        this.cantidad_Producto = cantidad_Producto;
    }

    public int getId_DetalleVenta() {
        return id_DetalleVenta;
    }

    public void setId_DetalleVenta(int id_DetalleVenta) {
        this.id_DetalleVenta = id_DetalleVenta;
    }

    public int getId_Venta() {
        return id_Venta;
    }

    public void setId_Venta(int id_Venta) {
        this.id_Venta = id_Venta;
    }

    public int getId_Producto() {
        return id_Producto;
    }

    public void setId_Producto(int id_Producto) {
        this.id_Producto = id_Producto;
    }

    public double getPrecio_venta() {
        return Precio_venta;
    }

    public void setPrecio_venta(double Precio_venta) {
        this.Precio_venta = Precio_venta;
    }

    public int getCantidad_Producto() {
        return cantidad_Producto;
    }

    public void setCantidad_Producto(int cantidad_Producto) {
        this.cantidad_Producto = cantidad_Producto;
    }

    
}
