/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

/**
 *
 * @author welvi
 */
public class Proveedor {

    private int id_Proveedor;
    private String Nombre_Proveedor;
    private String Telefono;
    private String Tipo_distribuidor;

    public Proveedor() {
    }

    public Proveedor(int id_Proveedor, String Nombre_Proveedor, String Telefono, String Tipo_distribuidor) {
        this.id_Proveedor = id_Proveedor;
        this.Nombre_Proveedor = Nombre_Proveedor;
        this.Telefono = Telefono;
        this.Tipo_distribuidor = Tipo_distribuidor;
    }

    public int getId_Proveedor() {
        return id_Proveedor;
    }

    public void setId_Proveedor(int id_Proveedor) {
        this.id_Proveedor = id_Proveedor;
    }

    public String getNombre_Proveedor() {
        return Nombre_Proveedor;
    }

    public void setNombre_Proveedor(String Nombre_Proveedor) {
        this.Nombre_Proveedor = Nombre_Proveedor;
    }

    public String getTelefono() {
        return Telefono;
    }

    public void setTelefono(String Telefono) {
        this.Telefono = Telefono;
    }

    public String getTipo_distribuidor() {
        return Tipo_distribuidor;
    }

    public void setTipo_distribuidor(String Tipo_distribuidor) {
        this.Tipo_distribuidor = Tipo_distribuidor;
    }

}
