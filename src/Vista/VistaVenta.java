/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Vista;

import Controlador.VentaControlador;
import Entidades.Venta;
import Controlador.DetalleVentaControlador;
import Entidades.Detalles_Venta;
import Controlador.ClienteControlador;
import Entidades.Cliente;
import Controlador.ProductoControlador;
import Entidades.Producto;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import java.util.Date;
import java.util.TimeZone;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.Timer;

/**
 *
 * @author welvi
 */
public class VistaVenta extends javax.swing.JPanel {

    private final VentaControlador ventaControlador;
    private final DetalleVentaControlador detalleVentaControlador;
    private final ClienteControlador clienteControlador;
    private final ProductoControlador productoControlador;
    private Integer idClienteSeleccionado = null;
    private Integer idProductoSeleccionado = null;
    private Timer timer; // Variable de instancia para el Timer
    private boolean horabd = false;

    /**
     * Creates new form VistaVentas
     */
    public VistaVenta() {
        initComponents();
        this.ventaControlador = new VentaControlador();
        this.detalleVentaControlador = new DetalleVentaControlador();
        this.clienteControlador = new ClienteControlador();
        this.productoControlador = new ProductoControlador();
        eventoComboProductos();

        selectorfechaVenta.setDate(new Date());
        ((JTextField) selectorfechaVenta.getDateEditor().getUiComponent()).setEditable(false);

        // Limpiar las filas vacías de tablaDetalles
        DefaultTableModel modelDetalles = (DefaultTableModel) tablaDetalles.getModel();
        modelDetalles.setRowCount(0);

        cargarDatosTablaVenta();
        cargarCliente();
        cargarProductos();
        actualizarHora();
    }

    private void limpiar() {
        textBuscar.setText("");
        idClienteSeleccionado = null;
        selectorfechaVenta.setDate(new Date());

        // Limpiar la tabla de detalles
        tablaDetalles.setModel(new DefaultTableModel(new Object[][]{}, new String[]{"ID Producto", "Producto", "Precio ", "Cantidad", "Subtotal"}));

        cargarDatosTablaVenta();
        cargarCliente();
        cargarProductos();

        btnEliminar.setEnabled(true);
        btnGuardar.setEnabled(true);

        horabd = false; // Restablece para mostrar hora actual
        actualizarHora(); // Vuelve a iniciar el timer
    }

    private void actualizarHora() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("America/Managua"));

        // Detener el timer anterior si existe
        if (timer != null) {
            timer.stop();
        }

        if (horabd) {
            return; // No actualiza la hora si está cargada desde la base de datos
        }

        timer = new Timer(1000, e -> {
            Date now = new Date();
            hora.setText(sdf.format(now));
        });
        timer.start();
    }

    private void cargarDatosTablaVenta() {
        List<Venta> ventas = ventaControlador.obtenerTodasVentas();

        if (ventas != null) {
            DefaultTableModel model = (DefaultTableModel) tablaVenta.getModel();
            model.setRowCount(0);

            for (Venta v : ventas) {
                Cliente cliente = clienteControlador.obtenerClientePorId(v.getId_Cliente());
                String NombreCliente = cliente.getNombre1() + " " + cliente.getApellido1();

                Object[] row = {
                    v.getId_Ventas(),
                    v.getFe_Venta(),
                    NombreCliente,
                    v.getTotal_Venta()
                };
                model.addRow(row);
            }
        }
    }

    private void cargarCliente() {
        try {
            // Obtener las categorías desde el controlador
            List<Cliente> cliente = clienteControlador.obtenerTodosCliente();

            // Limpiar el combo box por si tiene datos
            comboClientes.removeAllItems();

            // Agregar cada categoría al combo box
            for (Cliente c : cliente) {
                comboClientes.addItem(c.getNombre1() + " " + c.getApellido1()); // Mostrar el nombre
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error al cargar los clientes: " + e.getMessage());
        }
    }

    private void cargarProductos() {
        try {
            // Obtener las categorías desde el controlador
            List<Producto> productos = productoControlador.obtenerTodosProductos();

            // Limpiar el combo box por si tiene datos
            comboProducto.removeAllItems();

            // Agregar cada categoría al combo box
            for (Producto p : productos) {
                comboProducto.addItem(p.getNombre_prod()); // Mostrar el nombre
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error al cargar los productos: " + e.getMessage());
        }
    }

    private void eventoComboProductos() {
        comboProducto.addActionListener(e -> {
            // Obtener el índice seleccionado
            int indiceSeleccionado = comboProducto.getSelectedIndex();

            if (indiceSeleccionado >= 0) { // Verificar que se haya seleccionado algo
                try {
                    // Obtener la lista de categorías desde el controlador o memoria
                    List<Producto> productos = productoControlador.obtenerTodosProductos();

                    // Obtener el objeto de categoría correspondiente al índice seleccionado
                    Producto productoSeleccionado = productos.get(indiceSeleccionado);

                    // Actualizar la variable global con el ID de la categoría seleccionada
                    idProductoSeleccionado = productoSeleccionado.getId_producto();

                    // Actualizar el campo textPrecio con el precio unitario del producto
                    textPrecio.setText(String.valueOf(productoSeleccionado.getPrecio_Costo()));
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Error al seleccionar categoría: " + ex.getMessage());
                }
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        textBuscar = new javax.swing.JTextField();
        btnLimpiar = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnActualizar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaDetalles = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        hora = new javax.swing.JLabel();
        comboClientes = new javax.swing.JComboBox<>();
        comboProducto = new javax.swing.JComboBox<>();
        textCantidad = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        textPrecio = new javax.swing.JTextField();
        btnAgregarDetalle = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaVenta = new javax.swing.JTable();
        selectorfechaVenta = new com.toedter.calendar.JDateChooser();
        jLabel2 = new javax.swing.JLabel();

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 153, 51));
        jLabel3.setText("Fecha ");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(279, 6, -1, -1));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 153, 51));
        jLabel4.setText("Cantidad ");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(557, 6, -1, -1));

        textBuscar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        textBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                textBuscarKeyTyped(evt);
            }
        });
        jPanel1.add(textBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 210, 330, 30));

        btnLimpiar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnLimpiar.setForeground(new java.awt.Color(0, 51, 255));
        btnLimpiar.setText("Buscar");
        btnLimpiar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiaraccionBotonLimpiar(evt);
            }
        });
        jPanel1.add(btnLimpiar, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 410, 100, 31));

        btnGuardar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnGuardar.setForeground(new java.awt.Color(0, 51, 255));
        btnGuardar.setText("Guardar");
        btnGuardar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardaraccionBotonGuardar(evt);
            }
        });
        jPanel1.add(btnGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 410, 100, 31));

        btnEliminar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnEliminar.setForeground(new java.awt.Color(255, 0, 0));
        btnEliminar.setText("Eliminar");
        btnEliminar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminaraccionBotonEliminar(evt);
            }
        });
        jPanel1.add(btnEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 410, 94, 31));

        btnActualizar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnActualizar.setForeground(new java.awt.Color(0, 51, 255));
        btnActualizar.setText("Actualizar");
        btnActualizar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizaraccionBotonActualizar(evt);
            }
        });
        jPanel1.add(btnActualizar, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 410, 106, 32));

        tablaDetalles.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID Producto", "Producto", "Precio", "Cantidad", "Sutbtotal"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class, java.lang.Float.class, java.lang.Float.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tablaDetalles);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 88, 742, 109));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 153, 51));
        jLabel6.setText("Cliente");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(34, 6, -1, -1));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 153, 51));
        jLabel1.setText("Producto");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(161, 6, -1, -1));

        hora.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        hora.setForeground(new java.awt.Color(0, 153, 51));
        hora.setText("00:00:00");
        jPanel1.add(hora, new org.netbeans.lib.awtextra.AbsoluteConstraints(344, 6, -1, -1));

        comboClientes.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        comboClientes.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(comboClientes, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 122, 30));

        comboProducto.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        comboProducto.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(comboProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 40, 107, 30));

        textCantidad.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(textCantidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(538, 38, 102, 33));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 153, 51));
        jLabel5.setText("Precio");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(455, 6, -1, -1));

        textPrecio.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(textPrecio, new org.netbeans.lib.awtextra.AbsoluteConstraints(425, 38, 95, 33));

        btnAgregarDetalle.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnAgregarDetalle.setForeground(new java.awt.Color(0, 153, 51));
        btnAgregarDetalle.setText("Agregar");
        btnAgregarDetalle.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnAgregarDetalle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                accionBotonDetalleAgregar(evt);
            }
        });
        jPanel1.add(btnAgregarDetalle, new org.netbeans.lib.awtextra.AbsoluteConstraints(652, 37, 96, 33));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 51, 255));
        jLabel7.setText("Buscar");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 220, 56, -1));

        jButton2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 0, 0));
        jButton2.setText("Quitar Detalle");
        jButton2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(644, 214, 100, 30));

        tablaVenta.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID Ventas", "Fecha/Hora", "Cliente", "Total"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Object.class, java.lang.Integer.class, java.lang.Float.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaVenta.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaVentaMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tablaVenta);

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 258, 742, 140));

        selectorfechaVenta.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(selectorfechaVenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 40, 140, 30));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagen/291474364_116090381133197_1246855093686710006_n (1)-Photoroom (1).jpg"))); // NOI18N
        jLabel2.setText("jLabel2");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(-270, 0, 1110, 490));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 788, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 480, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void textBuscarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textBuscarKeyTyped
        String textoBusqueda = textBuscar.getText().trim().toLowerCase();
        List<Venta> ventas = ventaControlador.obtenerTodasVentas();

        DefaultTableModel modelo = (DefaultTableModel) tablaVenta.getModel();
        modelo.setRowCount(0);

        if (ventas != null) {
            for (Venta v : ventas) {
                Cliente cliente = clienteControlador.obtenerClientePorId(v.getId_Cliente());
                String nombreCliente = cliente.getNombre1() + " " + cliente.getApellido1();

                if (textoBusqueda.isEmpty()
                        || nombreCliente.toLowerCase().contains(textoBusqueda)) {
                    Object[] fila = {
                        v.getId_Ventas(),
                        v.getFe_Venta(),
                        nombreCliente,
                        v.getTotal_Venta()
                    };
                    modelo.addRow(fila);
                }
            }
        }  // TODO add your handling code here:
    }//GEN-LAST:event_textBuscarKeyTyped

    private void btnLimpiaraccionBotonLimpiar(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiaraccionBotonLimpiar
        limpiar();
    }//GEN-LAST:event_btnLimpiaraccionBotonLimpiar

    private void btnGuardaraccionBotonGuardar(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardaraccionBotonGuardar
        try {
            int indiceCliente = comboClientes.getSelectedIndex();
            if (indiceCliente < 0) {
                JOptionPane.showMessageDialog(this, "Seleccione un cliente.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            List<Cliente> clientes = clienteControlador.obtenerTodosCliente();
            int id_Cliente = clientes.get(indiceCliente).getId_Cliente();

            Date Fe_Venta = selectorfechaVenta.getDate();
            if (Fe_Venta == null) {
                JOptionPane.showMessageDialog(this, "Seleccione una fecha.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            DefaultTableModel modelDetalles = (DefaultTableModel) tablaDetalles.getModel();
            int rowCount = modelDetalles.getRowCount();
            if (rowCount == 0) {
                JOptionPane.showMessageDialog(this, "Agregue al menos un producto a la venta.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            List<Detalles_Venta> detalles = new ArrayList<>();
            float total_Venta = 0;
            for (int i = 0; i < rowCount; i++) {
                int idProducto = (int) modelDetalles.getValueAt(i, 0);
                double Precio_venta = ((Number) modelDetalles.getValueAt(i, 2)).floatValue();
                int cantidad = (int) modelDetalles.getValueAt(i, 3);
                float subtotal = ((Number) modelDetalles.getValueAt(i, 4)).floatValue();

                Detalles_Venta detalle = new Detalles_Venta();
                detalle.setId_Producto(idProducto);
                detalle.setCantidad_Producto(cantidad);
                detalle.setPrecio_venta(Precio_venta);
                detalles.add(detalle);

                total_Venta += subtotal;
            }

            ventaControlador.crearVenta(id_Cliente, Fe_Venta, total_Venta, detalles);

            limpiar();
            cargarDatosTablaVenta();

            JOptionPane.showMessageDialog(this, "Venta guardada exitosamente.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al guardar la venta: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnGuardaraccionBotonGuardar

    private void btnEliminaraccionBotonEliminar(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminaraccionBotonEliminar
        try {
            // Obtener el índice de la fila seleccionada en tablaVentas
            int filaSeleccionada = tablaVenta.getSelectedRow();
            if (filaSeleccionada == -1) {
                JOptionPane.showMessageDialog(this, "Seleccione una venta para eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Obtener el idVenta de la fila seleccionada
            DefaultTableModel model = (DefaultTableModel) tablaVenta.getModel();
            int idVenta = (int) model.getValueAt(filaSeleccionada, 0);

            // Confirmar con el usuario antes de eliminar
            int confirmacion = JOptionPane.showConfirmDialog(this,
                    "¿Está seguro de que desea eliminar la venta con ID " + idVenta + "?",
                    "Confirmar Eliminación",
                    JOptionPane.YES_NO_OPTION);
            if (confirmacion == JOptionPane.YES_OPTION) {
                // Eliminar la venta
                ventaControlador.eliminarVenta(idVenta);

                // Recargar la tabla de ventas
                cargarDatosTablaVenta();
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al eliminar la venta: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnEliminaraccionBotonEliminar

    private void btnActualizaraccionBotonActualizar(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizaraccionBotonActualizar
        try {
            int filaSeleccionada = tablaVenta.getSelectedRow();
            if (filaSeleccionada == -1) {
                JOptionPane.showMessageDialog(this, "Seleccione una venta para actualizar.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            DefaultTableModel modelVentas = (DefaultTableModel) tablaVenta.getModel();
            int idVenta = (int) modelVentas.getValueAt(filaSeleccionada, 0);

            int indiceCliente = comboClientes.getSelectedIndex();
            if (indiceCliente < 0) {
                JOptionPane.showMessageDialog(this, "Seleccione un cliente.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            List<Cliente> clientes = clienteControlador.obtenerTodosCliente();
            int idCliente = clientes.get(indiceCliente).getId_Cliente();

            Date Fe_Venta = selectorfechaVenta.getDate();
            if (Fe_Venta == null) {
                JOptionPane.showMessageDialog(this, "Seleccione una fecha.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            DefaultTableModel modelDetalles = (DefaultTableModel) tablaDetalles.getModel();
            int rowCount = modelDetalles.getRowCount();
            if (rowCount == 0) {
                JOptionPane.showMessageDialog(this, "Agregue al menos un producto a la venta.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            float totalVenta = 0;
            for (int i = 0; i < rowCount; i++) {
                totalVenta += ((Number) modelDetalles.getValueAt(i, 4)).floatValue();
            }

            // Actualizar la venta principal
            ventaControlador.actualizarVenta(idVenta, idCliente, Fe_Venta, totalVenta);

            // Eliminar los detalles anteriores
            List<Detalles_Venta> detallesAntiguos = detalleVentaControlador.obtenerTodosDetalleVenta();
            for (Detalles_Venta detalle : detallesAntiguos) {
                if (detalle.getId_Venta() == idVenta) {
                    detalleVentaControlador.eliminarDetalleVenta(detalle.getId_DetalleVenta());
                }
            }

            // Insertar nuevos detalles
            for (int i = 0; i < rowCount; i++) {
                int idProducto = (int) modelDetalles.getValueAt(i, 0);
                float precio = ((Number) modelDetalles.getValueAt(i, 2)).floatValue();
                int cantidad = (int) modelDetalles.getValueAt(i, 3);

                detalleVentaControlador.crearDetalleVenta(idVenta, idProducto, precio, cantidad);
            }

            limpiar();
            cargarDatosTablaVenta();

            JOptionPane.showMessageDialog(this, "Venta actualizada correctamente.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al actualizar la venta: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnActualizaraccionBotonActualizar

    private void tablaVentaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaVentaMouseClicked
        if (evt.getClickCount() == 2) {
            try {
                btnEliminar.setEnabled(false);
                btnGuardar.setEnabled(false);

                // Obtener el índice de la fila seleccionada en tablaVentas
                int filaSeleccionada = tablaVenta.getSelectedRow();
                if (filaSeleccionada == -1) {
                    return; // No hacer nada si no hay fila seleccionada
                }

                // Obtener el idVenta de la fila seleccionada
                DefaultTableModel modelVentas = (DefaultTableModel) tablaVenta.getModel();
                int idVenta = (int) modelVentas.getValueAt(filaSeleccionada, 0);

                // Obtener la venta seleccionada para acceder a sus datos
                List<Venta> ventas = ventaControlador.obtenerTodasVentas();
                Venta ventaSeleccionada = null;
                for (Venta v : ventas) {
                    if (v.getId_Ventas() == idVenta) {
                        ventaSeleccionada = v;
                        break;
                    }
                }
                if (ventaSeleccionada == null) {
                    JOptionPane.showMessageDialog(this, "Venta no encontrada.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Cargar cliente en comboClientes
                List<Cliente> cliente = clienteControlador.obtenerTodosCliente();
                int indiceCliente = -1;
                for (int i = 0; i < cliente.size(); i++) {
                    if (cliente.get(i).getId_Cliente() == ventaSeleccionada.getId_Cliente()) {
                        indiceCliente = i;
                        break;
                    }
                }
                if (indiceCliente != -1) {
                    idClienteSeleccionado = ventaSeleccionada.getId_Cliente();
                    comboClientes.setSelectedIndex(indiceCliente);
                } else {
                    JOptionPane.showMessageDialog(this, "Cliente no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Detener el timer actual
                if (timer != null) {
                    timer.stop();
                }

                // Asignar la hora al label
                horabd = true;
                java.text.SimpleDateFormat horaFormato = new java.text.SimpleDateFormat("HH:mm:ss");
                String horaVenta = horaFormato.format(ventaSeleccionada.getFe_Venta());
                hora.setText(horaVenta); // Ajusta 'horaLabel' al nombre real de tu JLabel

                // Cargar la fecha en selectorfechaContratacion
                selectorfechaVenta.setDate(ventaSeleccionada.getFe_Venta());

                // Limpiar y cargar los detalles en tablaDetalles
                DefaultTableModel modelDetalles = (DefaultTableModel) tablaDetalles.getModel();
                modelDetalles.setRowCount(0);

                List<Detalles_Venta> detalles = detalleVentaControlador.obtenerTodosDetalleVenta();
                if (detalles != null) {
                    for (Detalles_Venta detalle : detalles) {
                        if (detalle.getId_Venta() == idVenta) {
                            Producto producto = productoControlador.obtenerProductoPorId(detalle.getId_Producto());
                            String nombreProducto = (producto != null) ? producto.getNombre_prod() : "Desconocido";

                            Object[] row = {
                                detalle.getId_Producto(),
                                nombreProducto,
                                detalle.getPrecio_venta(),
                                detalle.getCantidad_Producto(),
                                detalle.getPrecio_venta() * detalle.getCantidad_Producto()// Subtotal
                            };
                            modelDetalles.addRow(row);
                        }
                    }
                }

            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error al cargar los datos de la venta: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_tablaVentaMouseClicked

    private void accionBotonDetalleAgregar(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_accionBotonDetalleAgregar
        try {
            // Obtener el índice seleccionado del comboProductos
            int indiceSeleccionado = comboProducto.getSelectedIndex();
            if (indiceSeleccionado < 0) {
                JOptionPane.showMessageDialog(this, "Seleccione un producto.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Obtener la lista de productos
            List<Producto> productos = productoControlador.obtenerTodosProductos();
            Producto productoSeleccionado = productos.get(indiceSeleccionado);

            // Obtener el precio unitario del producto
            double precioUnitario = productoSeleccionado.getPrecio_Costo();

            // Obtener la cantidad ingresada
            String cantidadStr = textCantidad.getText().trim();
            if (cantidadStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Ingrese una cantidad.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int cantidad;
            try {
                cantidad = Integer.parseInt(cantidadStr);
                if (cantidad <= 0) {
                    JOptionPane.showMessageDialog(this, "La cantidad debe ser mayor a 0.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "La cantidad debe ser un número entero válido.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Calcular el subtotal
            double subtotal = precioUnitario * cantidad;

            // Agregar los datos a la tabla tablaDetalles
            DefaultTableModel model = (DefaultTableModel) tablaDetalles.getModel();
            Object[] row = {
                productoSeleccionado.getId_producto(),
                productoSeleccionado.getNombre_prod(),
                precioUnitario,
                cantidad,
                subtotal
            };
            model.addRow(row);

            // Limpiar los campos después de agregar
            textCantidad.setText("");
            textPrecio.setText("");
            cargarProductos();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al agregar el producto: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_accionBotonDetalleAgregar


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnAgregarDetalle;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JComboBox<String> comboClientes;
    private javax.swing.JComboBox<String> comboProducto;
    private javax.swing.JLabel hora;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private com.toedter.calendar.JDateChooser selectorfechaVenta;
    private javax.swing.JTable tablaDetalles;
    private javax.swing.JTable tablaVenta;
    private javax.swing.JTextField textBuscar;
    private javax.swing.JTextField textCantidad;
    private javax.swing.JTextField textPrecio;
    // End of variables declaration//GEN-END:variables
}
