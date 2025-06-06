/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import DAO.DAOCompra;
import DAO.DAODetalleCompra;
import Entidades.Compra;
import Entidades.DetalleCompra;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author welvi
 */
public class CompraControlador {

    private final DAOCompra DAOcompra;
    private final DAODetalleCompra detalleCompraDAO;

    public CompraControlador() {
        this.DAOcompra = new DAOCompra();
        this.detalleCompraDAO = new DAODetalleCompra();

    }

    // Método para crear una nueva compra con sus detalles
    public void crearCompra(int id_Proveedor, Date Fe_compra, float total_Compra, List<DetalleCompra> detalles) {
        try {
            Compra compra = new Compra();
            compra.setId_Proveedor(id_Proveedor);
            compra.setFe_compra(Fe_compra);
            compra.setTotal_Compra(total_Compra);
            int id_compra = DAOcompra.crearCompra(compra);

            if (id_compra == -1) {
                throw new SQLException("No se pudo obtener el ID de la compra.");
            }
// Asignar el id_compra a cada detalle y guardarlos
            for (DetalleCompra detalle : detalles) {
                detalle.setId_compra(id_compra);
                detalleCompraDAO.crearDetalleCompra(detalle);
            }
            JOptionPane.showMessageDialog(null, "Compra y detalles creados exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al crear la compra: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método para obtener todas las compras
    public List<Compra> obtenerTodasCompras() {
        try {
            return DAOcompra.leerTodasCompras();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al leer las compras: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    // Método para actualizar una compra existente
    public void actualizarCompra(int id_compra, int id_Proveedor, Date Fe_compra, float total_Compra) {
        try {
            Compra compra = new Compra();
            compra.setId_compra(id_compra);
            compra.setId_Proveedor(id_Proveedor);
            compra.setFe_compra(Fe_compra);
            compra.setTotal_Compra(total_Compra);
            DAOcompra.actualizarCompra(compra);
            JOptionPane.showMessageDialog(null, "Compra actualizada exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar la compra: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método para eliminar una compra
    public void eliminarCompra(int id_compra) {
        try {
            DAOcompra.eliminarCompra(id_compra);
            JOptionPane.showMessageDialog(null, "Compra eliminada exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar la compra: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método main para pruebas
    public static void main(String[] args) {
        CompraControlador controlador = new CompraControlador();

        // Crear una lista de detalles de compra
        List<DetalleCompra> detalles = new ArrayList<>();
        DetalleCompra detalle = new DetalleCompra();
        detalle.setId_DetalleCompra(3); // ID existente
        detalle.setId_compra(1);
        detalle.setPrecio(80);
        detalle.setCantidad(30);
        detalles.add(detalle);

        // Crear una compra con detalles
        controlador.crearCompra(0, new Date(), 0, detalles);

        // Leer todas las compras
        List<Compra> compras = controlador.obtenerTodasCompras();
        if (compras != null) {
            System.out.println("Lista de compras:");
            for (Compra comp : compras) {
                System.out.println("ID: " + comp.getId_compra()
                        + ", Proveedor ID: " + comp.getId_Proveedor()
                        + ", Fe_compra: " + comp.getFe_compra()
                        + ", Total: " + comp.getTotal_Compra());
            }
        }

        // Actualizar una compra (suponiendo que ID 1 existe)
        controlador.actualizarCompra(0, 0, new Date(), 0);

        // Eliminar una compra
        controlador.eliminarCompra(1);
    }
}
