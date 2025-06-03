/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Entidades.Venta;
import Util.ConexionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Statement;

public class DAOVenta {

    public int crearVenta(Venta venta) throws SQLException {
        String sql = """
                    INSERT INTO Venta (
                        id_Cliente, 
                        Fe_venta,
                        total_venta
                    ) VALUES (?, ?, ?, ?)""";
        int generatedId = -1;

        try (Connection c = ConexionBD.getConnection(); PreparedStatement stmt = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, venta.getId_Cliente());
            stmt.setTimestamp(2, new java.sql.Timestamp(venta.getFe_Venta().getTime()));
            stmt.setFloat(3, venta.getTotalVenta());
            stmt.executeUpdate();

            // Obtener el ID generado
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    generatedId = rs.getInt(1);
                }
            }
        }
        return generatedId;
    }

    public List<Venta> leerTodasVentas() throws SQLException {
        String sql = "SELECT * FROM Venta";
        List<Venta> ventas = new ArrayList<>();

        try (Connection c = ConexionBD.getConnection(); PreparedStatement stmt = c.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Venta venta = new Venta();
                venta.setId_Ventas(rs.getInt("id_ventas"));
                venta.setId_Cliente(rs.getInt("id_Cliente"));
                venta.setFe_Venta(rs.getTimestamp("Fe_venta"));
                venta.setTotalVenta(rs.getFloat("total_venta"));
                ventas.add(venta);
            }
        }
        return ventas;
    }

    public void actualizarVenta(Venta venta) throws SQLException {
        String sql = "UPDATE Venta SET id_Cliente = ?, Fe_venta, total_venta = ? WHERE id_ventas = ?";

        try (Connection c = ConexionBD.getConnection(); PreparedStatement stmt = c.prepareStatement(sql)) {
            stmt.setInt(1, venta.getId_Cliente());
            stmt.setTimestamp(2, new java.sql.Timestamp(venta.getFe_Venta().getTime()));
            stmt.setFloat(3, venta.getTotalVenta());
            stmt.setInt(4, venta.getId_Ventas());
            stmt.executeUpdate();
        }
    }

    public void eliminarVenta(int idVentas) throws SQLException {
        String sql = "DELETE FROM Venta WHERE id_ventas = ?";

        try (Connection c = ConexionBD.getConnection(); PreparedStatement stmt = c.prepareStatement(sql)) {
            stmt.setInt(1, idVentas);
            stmt.executeUpdate();
        }
    }

    public static void main(String[] args) {
        try {
            DAOVenta dao = new DAOVenta();

            // Actualizar una venta
            Venta venta = new Venta();
            venta.setId_Ventas(1); // ID existente
            venta.setId_Cliente(1);
            venta.setFe_Venta(new java.util.Date());
            venta.setTotalVenta(500.0f);
            dao.actualizarVenta(venta);
            System.out.println("Venta actualizada.");

            // Eliminar una venta
            dao.eliminarVenta(2); // ID a eliminar
            System.out.println("Venta eliminada.");

            // Leer y mostrar todas las ventas para verificar
            List<Venta> ventas = dao.leerTodasVentas();
            System.out.println("Lista de ventas:");
            for (Venta ven : ventas) {
                System.out.println("ID Venta: " + ven.getId_Ventas()
                        + ", Cliente ID: " + ven.getId_Cliente()
                        + ", Fecha: " + ven.getFe_Venta()
                        +  ", Total: " + ven.getTotalVenta());
            }
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
