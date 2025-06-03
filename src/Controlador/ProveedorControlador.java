/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import DAO.DAOProveedor;
import Entidades.Proveedor;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author welvi
 */
public class ProveedorControlador {

    private final DAOProveedor DAOproveedor;

    public ProveedorControlador() {
        this.DAOproveedor = new DAOProveedor();
    }

    // Método para crear un nuevo cliente
    public void crearProveedor(String Nombre_Proveedor, String Telefono, String Tipo_distribuidor) {
        try {
            Proveedor proveedor = new Proveedor();
            proveedor.setNombre_Proveedor(Nombre_Proveedor);
            proveedor.setTelefono(Telefono);
            proveedor.setTipo_distribuidor(Tipo_distribuidor);
            DAOproveedor.crearProveedor(proveedor);
            JOptionPane.showMessageDialog(null, "Cliente creado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al crear el proveedor: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método para obtener todos los clientes
    public List<Proveedor> obtenerTodosProveedores() {
        try {
            return DAOproveedor.leerTodasProveedor();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al leer los proveedores: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    public Proveedor obtenerProveedorPorId(int idProveedor) {
        try {
            return DAOproveedor.obtenerProveedorPorId(idProveedor);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al obtener el cliente: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    public void actualizarProveedor(int id_Proveedor, String Nombre_Proveedor, String Telefono, String Tipo_distribuidor) {
        try {
            Proveedor proveedor = new Proveedor();
            proveedor.setId_Proveedor(id_Proveedor);
            proveedor.setNombre_Proveedor(Nombre_Proveedor);
            proveedor.setTelefono(Telefono);
            proveedor.setTipo_distribuidor(Tipo_distribuidor);
            DAOproveedor.actualizarProveedor(proveedor);
            JOptionPane.showMessageDialog(null, "Proveedor actualizado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar el proveedor: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método para eliminar un cliente
    public void eliminarProveedor(int id_Proveedor) {
        try {
            DAOproveedor.eliminarProveedor(id_Proveedor);
            JOptionPane.showMessageDialog(null, "Proveedor eliminado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar el proveedor: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método main para pruebas
    public static void main(String[] args) {
        ProveedorControlador controlador = new ProveedorControlador();

        // Crear un Provedor
        controlador.actualizarProveedor(1, " AgroRural", "11112222", "Mayorista");

        // Leer todos los clientes
        List<Proveedor> proveedores = controlador.obtenerTodosProveedores();
        if (proveedores != null) {
            System.out.println("Lista de proveedores:");
            for (Proveedor prov : proveedores) {
                System.out.println("ID: " + prov.getId_Proveedor()
                        + ",Nombre_Proveedor: " + prov.getNombre_Proveedor()
                        + ", Telefono: " + prov.getTelefono()
                        + ",  Tipo_distribuidor: " + prov.getTipo_distribuidor());
            }
        }

        // Actualizar un Proveedor (suponiendo que ID 1 existe)
        controlador.actualizarProveedor(1, " AgroRural", "11112222", "Mayorista");

        // Eliminar un cliente
        controlador.eliminarProveedor(3);
    }
}
