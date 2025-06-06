/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Date;
import java.util.List;

/**
 *
 * @author welvin
 */
public class Compra {

    private int id_compra;
    private int id_Proveedor;
    private Date Fe_compra;
    private float total_Compra;
    private List<DetalleCompra> detalles;

    public Compra() {
    }

    public Compra(int id_compra, int id_Proveedor, Date Fe_compra, float total_Compra, List<DetalleCompra> detalles) {
        this.id_compra = id_compra;
        this.id_Proveedor = id_Proveedor;
        this.Fe_compra = Fe_compra;
        this.total_Compra = total_Compra;
        this.detalles = detalles;
    }

    public int getId_compra() {
        return id_compra;
    }

    public void setId_compra(int id_compra) {
        this.id_compra = id_compra;
    }

    public int getId_Proveedor() {
        return id_Proveedor;
    }

    public void setId_Proveedor(int id_Proveedor) {
        this.id_Proveedor = id_Proveedor;
    }

    public Date getFe_compra() {
        return Fe_compra;
    }

    public void setFe_compra(Date Fe_compra) {
        this.Fe_compra = Fe_compra;
    }

    public float getTotal_Compra() {
        return total_Compra;
    }

    public void setTotal_Compra(float total_Compra) {
        this.total_Compra = total_Compra;
    }

    public List<DetalleCompra> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleCompra> detalles) {
        this.detalles = detalles;
    }

}
