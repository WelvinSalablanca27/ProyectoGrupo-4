/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Entidades.Proveedor;
import Util.ConexionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Welvi
 */
public class DAOProveedor {

    public void crearProveedor(Proveedor proveedor) throws SQLException {
        String sql = """
        INSERT INTO  Proveedor (
           Nombre_Proveedor,
           Telefono,
           Tipo_distribuidor
        ) VALUES (?, ?, ?)""";

        try (Connection c = ConexionBD.getConnection(); PreparedStatement stmt = c.prepareStatement(sql)) {
            stmt.setString(1, proveedor.getNombre_Proveedor());
            stmt.setString(2, proveedor.getTelefono());
            stmt.setString(3, proveedor.getTipo_distribuidor());
            stmt.executeUpdate();
        }
    }

    public List<Proveedor> leerTodasProveedor() throws SQLException {
        String sql = "SELECT * FROM Proveedor";
        List<Proveedor> proveedores = new ArrayList<>();

        try (Connection c = ConexionBD.getConnection(); PreparedStatement stmt = c.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Proveedor proveedor = new Proveedor();
                proveedor.setId_Proveedor(rs.getInt("id_Proveedor"));
                proveedor.setNombre_Proveedor(rs.getString("Nombre_Proveedor"));
                proveedor.setTelefono(rs.getString("Telefono"));
                proveedor.setTipo_distribuidor(rs.getString("Tipo_distribuidor"));
                proveedores.add(proveedor);
            }
        }
        return proveedores;
    }

    public Proveedor obtenerProveedorPorId(int id_Proveedor) throws SQLException {
        String sql = "SELECT * FROM Proveedor WHERE id_Proveedor = ?";
        Proveedor proveedores = null;
        try (Connection c = ConexionBD.getConnection(); PreparedStatement stmt = c.prepareStatement(sql)) {
            stmt.setInt(1, id_Proveedor);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    proveedores = new Proveedor();
                    proveedores.setId_Proveedor(rs.getInt("id_Proveedor"));
                    proveedores.setNombre_Proveedor(rs.getString("Nombre_Proveedor"));
                    proveedores.setTelefono(rs.getString("Telefono"));
                    proveedores.setTipo_distribuidor(rs.getString("Tipo_distribuidor"));
                }
            }
        }

        return proveedores;
    }

    public void actualizarProveedor(Proveedor proveedor) throws SQLException {
        String sql = "UPDATE Proveedor SET Nombre_Proveedor = ?, Telefono = ?,Tipo_distribuidor = ? WHERE id_Proveedor = ?";

        try (Connection c = ConexionBD.getConnection(); PreparedStatement stmt = c.prepareStatement(sql)) {
            stmt.setString(1, proveedor.getNombre_Proveedor());
            stmt.setString(2, proveedor.getTelefono());
            stmt.setString(3, proveedor.getTipo_distribuidor());
            stmt.setInt(4, proveedor.getId_Proveedor()); // <-- Agregado
            stmt.executeUpdate();
        }
    }

    // Método para eliminar una Proveedor
    public void eliminarProveedor(int id_Proveedor) throws SQLException {
        String sql = "DELETE FROM Proveedor WHERE id_Proveedor = ?";

        try (Connection c = ConexionBD.getConnection(); PreparedStatement stmt = c.prepareStatement(sql)) {
            stmt.setInt(1, id_Proveedor);
            stmt.executeUpdate();
        }
    }

// Método Main
    public static void main(String[] args) {
        try {
            DAOProveedor dao = new DAOProveedor();

            // Actualizar una proveedor
            Proveedor proveedor = new Proveedor();
            proveedor.setId_Proveedor(1); // ID existente
            proveedor.setNombre_Proveedor("Almacen");
            proveedor.setTelefono("57247044");
            proveedor.setTipo_distribuidor("Mayorista");
            dao.actualizarProveedor(proveedor);
            System.out.println("Proveedor actualizada.");

            // Eliminar una Proveedor
            dao.eliminarProveedor(3); // ID a eliminar
            System.out.println("Proveedor eliminada.");

            // Leer y mostrar todas las compras para verificar
            List<Proveedor> proveedores = dao.leerTodasProveedor();
            System.out.println("Lista de proveedores:");
            for (Proveedor prov : proveedores) {
                System.out.println("ID: " + prov.getId_Proveedor()
                        + ",Nombre_Proveedor: " + prov.getNombre_Proveedor()
                        + ", Telefono: " + prov.getTelefono()
                        + ",  Tipo_distribuidor: " + prov.getTipo_distribuidor());

            }
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

}
