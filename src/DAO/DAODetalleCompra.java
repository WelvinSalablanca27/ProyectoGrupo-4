/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Entidades.DetalleCompra;
import Util.ConexionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author welvi
 */
public class DAODetalleCompra {

    public void crearDetalleCompra(DetalleCompra detalle) throws SQLException {
        String sql = """
        INSERT INTO DetalleCompra (
             id_compra,
             id_Producto,
             Precio, 
             cantidad
        ) VALUES (?, ?, ?, ?)""";

        try (Connection c = ConexionBD.getConnection(); PreparedStatement stmt = c.prepareStatement(sql)) {
            stmt.setInt(1, detalle.getId_compra());
            stmt.setInt(2, detalle.getId_Producto());
            stmt.setFloat(3, detalle.getPrecio());
            stmt.setInt(4, detalle.getCantidad());
            stmt.executeUpdate();

        }
    }

    public List<DetalleCompra> leerTodosDetallesCompra() throws SQLException {
        String sql = "SELECT * FROM DetalleCompra";
        List<DetalleCompra> detalles = new ArrayList<>();

        try (Connection c = ConexionBD.getConnection(); PreparedStatement stmt = c.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                DetalleCompra detalle = new DetalleCompra();
                detalle.setId_DetalleCompra(rs.getInt("id_DetalleCompra"));
                detalle.setId_compra(rs.getInt("id_compra"));
                detalle.setId_Producto(rs.getInt("id_Producto"));
                detalle.setPrecio(rs.getFloat("Precio"));
                detalle.setCantidad(rs.getInt("cantidad"));
                detalles.add(detalle);
            }
        }
        return detalles;
    }

    public void actualizarDetalleCompra(DetalleCompra detalle) throws SQLException {
        String sql = "UPDATE DetalleCompra SET id_compra = ?, id_producto = ?,   Precio = ?,  cantidad  = ? WHERE id_DetalleCompra = ?";

        try (Connection c = ConexionBD.getConnection(); PreparedStatement stmt = c.prepareStatement(sql)) {
            stmt.setInt(1, detalle.getId_compra());
            stmt.setInt(2, detalle.getId_Producto());
            stmt.setFloat(3, detalle.getPrecio());
            stmt.setInt(4, detalle.getCantidad());
            stmt.setInt(5, detalle.getId_DetalleCompra()); // <-- Faltaba
            stmt.executeUpdate();
        }
    }

// Método para eliminar un detalle de compra
    public void eliminarDetalleCompra(int id_DetalleCompra) throws SQLException {
        String sql = "DELETE FROM DetalleCompra WHERE id_DetalleCompra = ?";

        try (Connection c = ConexionBD.getConnection(); PreparedStatement stmt = c.prepareStatement(sql)) {
            stmt.setInt(1, id_DetalleCompra);
            stmt.executeUpdate();
        }
    }

// Método Main
    public static void main(String[] args) {
        try {
            DAODetalleCompra dao = new DAODetalleCompra();

            // Actualizar un detalle de compra
            DetalleCompra detalle = new DetalleCompra();
            detalle.setId_DetalleCompra(1); // ID existente
            detalle.setId_compra(1);
            detalle.setId_Producto(1);
            detalle.setPrecio(70);
            detalle.setCantidad(5);
            dao.actualizarDetalleCompra(detalle);
            System.out.println("Detalle de compra actualizado.");

            // Eliminar un detalle de compra
            dao.eliminarDetalleCompra(2); // ID a eliminar
            System.out.println("Detalle de compra eliminado.");

            // Leer y mostrar  los detalles de compra para verificar
            List<DetalleCompra> detalles = dao.leerTodosDetallesCompra();
            System.out.println("Lista de detallesCompra:");
            for (DetalleCompra det : detalles) {
                System.out.println("ID: " + det.getId_DetalleCompra()
                        + ", Compra ID: " + det.getId_compra()
                        + ", Producto ID: " + det.getId_Producto()
                        + ", precio: " + det.getPrecio()
                        + ", Cantidad: " + det.getCantidad());
            }
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
