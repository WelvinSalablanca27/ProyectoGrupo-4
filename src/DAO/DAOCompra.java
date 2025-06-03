/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Entidades.Compra;
import Util.ConexionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author welvin
 */
public class DAOCompra {

    public int crearCompra(Compra compra) throws SQLException {
        String sql = """
        INSERT INTO Compra (
           id_Proveedor,
           Fe_compra,
           total_compra
          ) VALUES (?, ?, ?)""";
        int generatedId = -1;

        try (Connection c = ConexionBD.getConnection(); PreparedStatement stmt = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, compra.getId_Proveedor());
            stmt.setDate(2, new java.sql.Date(compra.getFe_compra().getTime()));
             stmt.setFloat(3, compra.getTotalCompra());
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

    public List<Compra> leerTodasCompras() throws SQLException {
        String sql = "SELECT * FROM Compra";
        List<Compra> compras = new ArrayList<>();

        try (Connection c = ConexionBD.getConnection(); PreparedStatement stmt = c.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Compra compra = new Compra();
                compra.setId_compra(rs.getInt("id_compra"));
                compra.setId_Proveedor(rs.getInt("id_Proveedor"));
                compra.setFe_compra(rs.getDate("Fe_compra"));
                compra.setTotalCompra(rs.getFloat("total_compra"));
                compras.add(compra);
            }
        }
        return compras;
    }

    public void actualizarCompra(Compra compra) throws SQLException {
        String sql = "UPDATE Compra SET  id_Proveedor = ?, Fe_compra = ? ,total_compra = ? WHERE id_compra = ?";

        try (Connection c = ConexionBD.getConnection(); PreparedStatement stmt = c.prepareStatement(sql)) {
            stmt.setInt(1, compra.getId_Proveedor());
            stmt.setDate(2, new java.sql.Date(compra.getFe_compra().getTime()));
            stmt.setFloat(3, compra.getTotalCompra());
            stmt.setInt(4, compra.getId_compra()); // <-- Agregado
            stmt.executeUpdate();
        }
    }

// Método para eliminar una compra
    public void eliminarCompra(int id_compra) throws SQLException {
        String sql = "DELETE FROM Compra WHERE id_compra= ?";

        try (Connection c = ConexionBD.getConnection(); PreparedStatement stmt = c.prepareStatement(sql)) {
            stmt.setInt(1, id_compra);
            stmt.executeUpdate();
        }
    }

// Método Main
    public static void main(String[] args) {
        try {
            DAOCompra dao = new DAOCompra();

            // Actualizar una compra
            Compra compra = new Compra();
            compra.setId_compra(1); // ID existente
            compra.setId_Proveedor(1);
            compra.setFe_compra(new java.util.Date());
            compra.setTotalCompra(1500.50f);
            dao.actualizarCompra(compra);
            System.out.println("Compra actualizada.");

            // Eliminar una compra
            dao.eliminarCompra(1); // ID a eliminar
            System.out.println("Compra eliminada.");

            // Leer y mostrar todas las compras para verificar
            List<Compra> compras = dao.leerTodasCompras();
            System.out.println("Lista de compras:");
            for (Compra comp : compras) {
                System.out.println("ID: " + comp.getId_compra()
                        + ", Proveedor ID: " + comp.getId_Proveedor()
                        + ", Fe_compra: " + comp.getFe_compra()
                        + ", Total: " + comp.getTotalCompra());

            }
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

}
