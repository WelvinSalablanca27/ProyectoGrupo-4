/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

import java.util.Date;

/**
 *
 * @author welvin
 */
public class DetalleCompra {

    private int id_DetalleCompra;
    private int id_compra;
    private int id_Producto;
    private float precio;
    private int cantidad;

    public DetalleCompra() {
    }

    public DetalleCompra(int id_DetalleCompra, int id_compra, int id_Producto, float precio, int cantidad) {
        this.id_DetalleCompra = id_DetalleCompra;
        this.id_compra = id_compra;
        this.id_Producto = id_Producto;
        this.precio = precio;
        this.cantidad = cantidad;
    }

    public int getId_DetalleCompra() {
        return id_DetalleCompra;
    }

    public void setId_DetalleCompra(int id_DetalleCompra) {
        this.id_DetalleCompra = id_DetalleCompra;
    }

    public int getId_compra() {
        return id_compra;
    }

    public void setId_compra(int id_compra) {
        this.id_compra = id_compra;
    }

    public int getId_Producto() {
        return id_Producto;
    }

    public void setId_Producto(int id_Producto) {
        this.id_Producto = id_Producto;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    
}
