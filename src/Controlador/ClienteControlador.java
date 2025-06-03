/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import DAO.DAOCliente;
import Entidades.Cliente;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Edith Cruz
 */
public class ClienteControlador {
    private final DAOCliente DAOcliente;

    public ClienteControlador() {
        this.DAOcliente = new DAOCliente();
    }

    // Método para crear un nuevo cliente
    public void crearCliente( String Nombre1, String Nombre2, String Apellido1,
            String Apellido2, String Direccion, String Telefono) {
        try {
            Cliente cliente = new Cliente();
            cliente.setNombre1(Nombre1);
            cliente.setNombre2(Nombre2);
            cliente.setApellido1(Apellido1);
            cliente.setApellido2(Apellido2);
            cliente.setDireccion(Direccion);
            cliente.setTelefono(Telefono);
            DAOcliente.crearCliente(cliente);
            JOptionPane.showMessageDialog(null, "Cliente creado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al crear el cliente: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método para obtener todos los clientes
    public List<Cliente> obtenerTodosCliente() {
        try {
            return DAOcliente.leerTodosClientes();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al leer los clientes: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
public Cliente obtenerClientePorId(int idCliente) {
        try {
            return DAOcliente.obtenerClientePorId(idCliente);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al obtener el cliente: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    // Método para actualizar un cliente existente
    public void actualizarCliente(int id_Cliente, String Nombre1, String Nombre2, String Apellido1,
            String Apellido2, String Direccion, String Telefono) {
        try {
            Cliente cliente = new Cliente();        
            cliente.setId_Cliente(id_Cliente);
            cliente.setNombre1(Nombre1);
            cliente.setNombre2(Nombre2);
            cliente.setApellido1(Apellido1);
            cliente.setApellido2(Apellido2);
            cliente.setDireccion(Direccion);
            cliente.setTelefono(Telefono);
            DAOcliente.actualizarCliente(cliente);
            JOptionPane.showMessageDialog(null, "Cliente actualizado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar el cliente: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método para eliminar un cliente
    public void eliminarCliente(int id_Cliente) {
        try {
            DAOcliente.eliminarCliente(id_Cliente);
            JOptionPane.showMessageDialog(null, "Cliente eliminado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar el cliente: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método main para pruebas
    public static void main(String[] args) {
        ClienteControlador controlador = new ClienteControlador();

        // Crear un cliente
         controlador.crearCliente("Juan", "Carlos", "Pérez", "Gómez", "Calle 123", "12345678");

        // Leer todos los clientes
        List<Cliente> clientes = controlador.obtenerTodosCliente();
        if (clientes != null) {
            System.out.println("Lista de clientes:");
            for (Cliente cli : clientes) {
                   System.out.println("ID: " + cli.getId_Cliente()
                                      + ", Nombre1: " + cli.getNombre1()
                                      + ", Nombre2: " + cli.getNombre2()
                                      + ", Apellido1: " + cli.getApellido1()
                                      + " , Apellido2:" + cli.getApellido2()
                                      + ", Dirección: " + cli.getDireccion()
                                      +  ", Telefono: " + cli.getTelefono());
        }
        }

        // Actualizar un cliente (suponiendo que ID 1 existe)
        controlador.actualizarCliente(1, "Juan", "Carlos", "Pérez", "López", "Calle 456", "12345678");

        // Eliminar un cliente
        controlador.eliminarCliente(1);
    }
}
