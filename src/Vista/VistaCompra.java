/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Vista;

import Controlador.CompraControlador;
import Entidades.Compra;
import Controlador.DetalleCompraControlador;
import Entidades.DetalleCompra;
import Controlador.ProveedorControlador;
import Entidades.Proveedor;
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
public class VistaCompra extends javax.swing.JPanel {

    private final CompraControlador compraControlador;
    private final DetalleCompraControlador detalleCompraControlador;
    private final ProveedorControlador proveedorControlador;
    private final ProductoControlador productoControlador;

    private Integer idProveedordoSeleccionado = null;
    private Integer idProductoSeleccionado = null;

    private Timer timer; // Variable de instancia para el Timer
    private boolean horabd = false;

    public VistaCompra() {
        initComponents();
        this.compraControlador = new CompraControlador();
        this.detalleCompraControlador = new DetalleCompraControlador();
        this.proveedorControlador = new ProveedorControlador();
        this.productoControlador = new ProductoControlador();
        eventoComboProductos();

        selectorfechaCompra.setDate(new Date());
        ((JTextField) selectorfechaCompra.getDateEditor().getUiComponent()).setEditable(false);

        // Limpiar las filas vacías de tablaDetalles
        DefaultTableModel modelDetalles = (DefaultTableModel) tablaDetalles.getModel();
        modelDetalles.setRowCount(0);

        cargarDatosTablaCompras();
        cargarProveedor();
        cargarProductos();
        actualizarHora();
    }

    private void limpiar() {
        textBuscar.setText("");
        idProveedordoSeleccionado = null;
        selectorfechaCompra.setDate(new Date());

        // Limpiar la tabla de detalles
        tablaDetalles.setModel(new DefaultTableModel(new Object[][]{}, new String[]{"ID Producto", "Producto", "Precio ", "Cantidad", "Subtotal"}));

        cargarDatosTablaCompras();
        cargarProveedor();
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

    private void cargarDatosTablaCompras() {
        List<Compra> compras = compraControlador.obtenerTodasCompras();

        if (compras != null) {

            DefaultTableModel model = (DefaultTableModel) tablaCompras.getModel();
            model.setRowCount(0);

            for (Compra c : compras) {

                Proveedor proveedor = proveedorControlador.obtenerProveedorPorId(c.getId_Proveedor());
                String nombreProveedor = proveedor.getNombre_Proveedor()+ " " + proveedor.getTipo_distribuidor();

                Object[] row = {
                    c.getId_compra(),
                    c.getFe_compra(),
                    nombreProveedor,
                    c.getTotal_Compra()
                };
                model.addRow(row);
            }
        }
    }

    private void cargarProveedor() {
        try {
            // Obtener las categorías desde el controlador
            List<Proveedor> proveedor = proveedorControlador.obtenerTodosProveedores();

            // Limpiar el combo box por si tiene datos
            comboProveedor.removeAllItems();

            // Agregar cada categoría al combo box
            for (Proveedor e : proveedor) {
                comboProveedor.addItem(e.getNombre_Proveedor() + " " + e.getTipo_distribuidor()); // Mostrar el nombre
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error al cargar los empleados: " + e.getMessage());
        }
    }

    private void cargarProductos() {
        try {
            // Obtener las categorías desde el controlador
            List<Producto> productos = productoControlador.obtenerTodosProductos();

            // Limpiar el combo box por si tiene datos
            comboProductos.removeAllItems();

            // Agregar cada categoría al combo box
            for (Producto p : productos) {
                comboProductos.addItem(p.getNombre_prod()); // Mostrar el nombre
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error al cargar los productos: " + e.getMessage());
        }
    }

    private void eventoComboProductos() {
        comboProductos.addActionListener(e -> {
            // Obtener el índice seleccionado
            int indiceSeleccionado = comboProductos.getSelectedIndex();

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
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaDetalles = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        comboProveedor = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        textPrecio = new javax.swing.JTextField();
        btnGuardar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnActualizar = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        hora = new javax.swing.JLabel();
        comboProductos = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        textCantidad = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaCompras = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();
        selectorfechaCompra = new com.toedter.calendar.JDateChooser();
        textBuscar = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID  Producto", "Producto", "Precio ", "Cantidad", "Subtotal"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class, java.lang.Float.class, java.lang.Float.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaDetalles.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaDetallesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tablaDetalles);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 770, 110));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("Proveedor");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 0, -1, -1));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setText("Fecha");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 0, -1, -1));

        comboProveedor.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20" }));
        comboProveedor.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(comboProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, 107, 30));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setText("Precio");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 0, -1, 20));

        textPrecio.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(textPrecio, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 30, 80, 30));

        btnGuardar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnGuardar.setForeground(new java.awt.Color(0, 51, 255));
        btnGuardar.setText("Guardar");
        btnGuardar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardaraccionBotonGuardar(evt);
            }
        });
        jPanel1.add(btnGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 430, 130, 30));

        btnEliminar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnEliminar.setForeground(new java.awt.Color(255, 0, 0));
        btnEliminar.setText("Eliminar");
        btnEliminar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminaraccionBotonEliminar(evt);
            }
        });
        jPanel1.add(btnEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(339, 430, 130, 30));

        btnActualizar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnActualizar.setForeground(new java.awt.Color(0, 51, 255));
        btnActualizar.setText("Actualizar");
        btnActualizar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizaraccionBotonActualizar(evt);
            }
        });
        jPanel1.add(btnActualizar, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 430, 130, 30));

        btnLimpiar.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnLimpiar.setForeground(new java.awt.Color(0, 51, 255));
        btnLimpiar.setText("Limpiar");
        btnLimpiar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiaraccionBotonLimpiar(evt);
            }
        });
        jPanel1.add(btnLimpiar, new org.netbeans.lib.awtextra.AbsoluteConstraints(489, 430, 130, 30));

        hora.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        hora.setText("00:00:00");
        jPanel1.add(hora, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 0, -1, -1));

        comboProductos.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        comboProductos.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(comboProductos, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 30, 100, 30));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setText("Producto");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 0, -1, -1));

        textCantidad.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(textCantidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 30, 100, 30));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel6.setText("Cantidad");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 0, -1, -1));

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton1.setForeground(new java.awt.Color(0, 51, 255));
        jButton1.setText("Agregar");
        jButton1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1accionBotonAgregar(evt);
            }
        });
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 30, 90, 30));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setText("Buscar");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 190, 70, 30));

        tablaCompras.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID Compra", "Fecha/Hora", "Proveedor", "Total"
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
        jScrollPane2.setViewportView(tablaCompras);

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 240, 760, 170));

        jButton2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton2.setText("Quitar Detalle");
        jButton2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2accionBotonQuitarDetalle(evt);
            }
        });
        jPanel1.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 190, 100, 30));

        selectorfechaCompra.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(selectorfechaCompra, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 30, 130, 30));

        textBuscar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        textBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                textBuscarKeyTyped(evt);
            }
        });
        jPanel1.add(textBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 190, 240, 30));

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagen/291474364_116090381133197_1246855093686710006_n (1)-Photoroom (1).jpg"))); // NOI18N
        jLabel7.setText("jLabel7");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(-240, -50, 1060, 540));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tablaDetallesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaDetallesMouseClicked
        // Verificar si es un doble clic
        if (evt.getClickCount() == 2) {
            try {

                btnEliminar.setEnabled(false);
                btnGuardar.setEnabled(false);

                // Obtener el índice de la fila seleccionada en tablaCompras
                int filaSeleccionada = tablaCompras.getSelectedRow();
                if (filaSeleccionada == -1) {
                    return; // No hacer nada si no hay fila seleccionada
                }

                // Obtener el idVenta de la fila seleccionada
                DefaultTableModel modelVentas = (DefaultTableModel) tablaCompras.getModel();
                int idCompra = (int) modelVentas.getValueAt(filaSeleccionada, 0);

                // Obtener la venta seleccionada para acceder a sus datos
                List<Compra> compras = compraControlador.obtenerTodasCompras();
                Compra compraSeleccionada = null;
                for (Compra c : compras) {
                    if (c.getId_compra()== idCompra) {
                        compraSeleccionada = c;
                        break;
                    }
                }
                if (compraSeleccionada == null) {
                    JOptionPane.showMessageDialog(this, "Compra no encontrada.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Cargar empleado en comboEmpleados
             List<Proveedor> proveedor = proveedorControlador.obtenerTodosProveedores();
                int indiceProveedor = -1;
                for (int i = 0; i < proveedor.size(); i++) {
                    if (proveedor.get(i).getId_Proveedor()== compraSeleccionada.getId_Proveedor()) {
                        indiceProveedor = i;
                        break;
                    }
                }
                if (indiceProveedor != -1) {
                    idProveedordoSeleccionado = compraSeleccionada.getId_Proveedor();
                    comboProveedor.setSelectedIndex(indiceProveedor);
                } else {
                    JOptionPane.showMessageDialog(this, "Empleado no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Detener el timer actual
                if (timer != null) {
                    timer.stop();
                }

                // Asignar la hora al label
                horabd = true;
                java.text.SimpleDateFormat horaFormato = new java.text.SimpleDateFormat("HH:mm:ss");
                String horaCompra = horaFormato.format(compraSeleccionada.getFe_compra());
                hora.setText(horaCompra); // Ajusta 'horaLabel' al nombre real de tu JLabel

                // Cargar la fecha en selectorfechaContratacion
                selectorfechaCompra.setDate(compraSeleccionada.getFe_compra());

                // Limpiar y cargar los detalles en tablaDetalles
                DefaultTableModel modelDetalles = (DefaultTableModel) tablaDetalles.getModel();
                modelDetalles.setRowCount(0);

                List<DetalleCompra> detalles = detalleCompraControlador.obtenerTodosDetallesCompra();
                if (detalles != null) {
                    for (DetalleCompra detalle : detalles) {
                        if (detalle.getId_compra()== idCompra) {
                            Producto producto = productoControlador.obtenerProductoPorId(detalle.getId_Producto());
                            String nombreProducto = (producto != null) ? producto.getNombre_prod(): "Desconocido";

                            Object[] row = {
                                detalle.getId_Producto(),
                                nombreProducto,
                                detalle.getPrecio(),
                                detalle.getCantidad(),
                                detalle.getPrecio()* detalle.getCantidad() // Subtotal
                            };
                            modelDetalles.addRow(row);
                        }
                    }
                }

            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error al cargar los datos de la compra: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_tablaDetallesMouseClicked

    private void btnGuardaraccionBotonGuardar(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardaraccionBotonGuardar
        try {
            // Obtener el índice seleccionado de empleados
            int indiceProveedor = comboProveedor.getSelectedIndex();
            if (indiceProveedor < 0) {
                JOptionPane.showMessageDialog(this, "Seleccione un empleado.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Obtener la lista de  empleados
            List<Proveedor> proveedores = proveedorControlador.obtenerTodosProveedores();
            int idProveedor = proveedores.get(indiceProveedor).getId_Proveedor();

            // Obtener la fecha seleccionada
            Date fechaVenta = selectorfechaCompra.getDate();
            if (fechaVenta == null) {
                JOptionPane.showMessageDialog(this, "Seleccione una fecha.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Obtener los detalles de la tabla tablaDetalles
            DefaultTableModel modelDetalles = (DefaultTableModel) tablaDetalles.getModel();
            int rowCount = modelDetalles.getRowCount();
            if (rowCount == 0) {
                JOptionPane.showMessageDialog(this, "Agregue al menos un producto a la compra.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Crear lista de detalles y calcular total
            List<DetalleCompra> detalles = new ArrayList<>();
            float totalVenta = 0;
            for (int i = 0; i < rowCount; i++) {
                int idProducto = (int) modelDetalles.getValueAt(i, 0); // ID Producto como Integer
                float precio = ((Number) modelDetalles.getValueAt(i, 2)).floatValue(); // Precio Unitario como Float
                int cantidad = (int) modelDetalles.getValueAt(i, 3); // Cantidad como Integer
                float subtotal = ((Number) modelDetalles.getValueAt(i, 4)).floatValue(); // Subtotal como Float

                // Crear objeto DetalleCompra
                DetalleCompra detalle = new DetalleCompra();
                detalle.setId_Producto(idProducto);
                detalle.setCantidad(cantidad);
                detalle.setPrecio(precio);
                detalles.add(detalle);

                totalVenta += subtotal;
            }

            // Crear y guardar la compra
            compraControlador.crearCompra(idProveedor, fechaVenta, totalVenta, detalles);

            limpiar();

            // Recargar la tabla de compras
            cargarDatosTablaCompras();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al guardar la compra: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnGuardaraccionBotonGuardar

    private void btnEliminaraccionBotonEliminar(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminaraccionBotonEliminar
        try {
            // Obtener el índice de la fila seleccionada en tablaVentas
            int filaSeleccionada = tablaCompras.getSelectedRow();
            if (filaSeleccionada == -1) {
                JOptionPane.showMessageDialog(this, "Seleccione una venta para eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Obtener el idVenta de la fila seleccionada
            DefaultTableModel model = (DefaultTableModel) tablaCompras.getModel();
            int idCompra = (int) model.getValueAt(filaSeleccionada, 0);

            // Confirmar con el usuario antes de eliminar
            int confirmacion = JOptionPane.showConfirmDialog(this,
                "¿Está seguro de que desea eliminar la venta con ID " + idCompra + "?",
                "Confirmar Eliminación",
                JOptionPane.YES_NO_OPTION);
            if (confirmacion == JOptionPane.YES_OPTION) {
                // Eliminar la venta
                compraControlador.eliminarCompra(idCompra);

                // Recargar la tabla de ventas
                cargarDatosTablaCompras();
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al eliminar la venta: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnEliminaraccionBotonEliminar

    private void btnActualizaraccionBotonActualizar(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizaraccionBotonActualizar
        try {
            // Obtener el índice de la fila seleccionada en tablaVentas
            int filaSeleccionada = tablaCompras.getSelectedRow();
            if (filaSeleccionada == -1) {
                JOptionPane.showMessageDialog(this, "Seleccione una compra para actualizar.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Obtener el idVenta de la fila seleccionada
            DefaultTableModel modelVentas = (DefaultTableModel) tablaCompras.getModel();
            int idCompra = (int) modelVentas.getValueAt(filaSeleccionada, 0);

            // Obtener el índice seleccionado de empleados
            int indiceProveedor = comboProveedor.getSelectedIndex();
            if ( indiceProveedor < 0) {
                JOptionPane.showMessageDialog(this, "Seleccione un empleado.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Obtener la lista de empleados
            List<Proveedor> proveedores = proveedorControlador.obtenerTodosProveedores();
            int idProveedor = proveedores.get(indiceProveedor).getId_Proveedor();

            // Obtener la fecha seleccionada
            Date fechaVenta = selectorfechaCompra.getDate();
            if (fechaVenta == null) {
                JOptionPane.showMessageDialog(this, "Seleccione una fecha.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Obtener los detalles de la tabla tablaDetalles
            DefaultTableModel modelDetalles = (DefaultTableModel) tablaDetalles.getModel();
            int rowCount = modelDetalles.getRowCount();
            if (rowCount == 0) {
                JOptionPane.showMessageDialog(this, "Agregue al menos un producto a la compra.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Calcular el total de la compra
            float totalCompra = 0;
            for (int i = 0; i < rowCount; i++) {
                totalCompra += ((Number) modelDetalles.getValueAt(i, 4)).floatValue(); // Suma los subtotales
            }

            // Actualizar la compra principal
            compraControlador.actualizarCompra(idCompra, idProveedor, fechaVenta, totalCompra);

            // Eliminar los detalles antiguos de la compra
            List<DetalleCompra> detallesAntiguos = detalleCompraControlador.obtenerTodosDetallesCompra();
            if (detallesAntiguos != null) {
                for (DetalleCompra detalle : detallesAntiguos) {
                    if (detalle.getId_compra()== idCompra) {
                        detalleCompraControlador.eliminarDetalleCompra(detalle.getId_DetalleCompra());
                    }
                }
            }

            // Insertar los nuevos detalles
            for (int i = 0; i < rowCount; i++) {
                int idProducto = (int) modelDetalles.getValueAt(i, 0);
                float precio = ((Number) modelDetalles.getValueAt(i, 2)).floatValue();
                int cantidad = (int) modelDetalles.getValueAt(i, 3);

                // Crear y guardar el nuevo detalle
                DetalleCompra detalle = new DetalleCompra();
                detalle.setId_compra(idCompra);
                detalle.setId_Producto(idProducto);
                detalle.setCantidad(cantidad);
                detalle.setPrecio(precio);
                detalleCompraControlador.crearDetalleCompra(idCompra, idProducto, precio, cantidad);
            }

            // Limpiar la tabla de detalles y el formulario
            tablaDetalles.setModel(new DefaultTableModel(new Object[][]{}, new String[]{"ID Producto", "Producto", "Precio ", "Cantidad", "Subtotal"}));
            limpiar();

            // Recargar la tabla de compras
            cargarDatosTablaCompras();

            // Habilitar botones nuevamente
            btnEliminar.setEnabled(true);
            btnGuardar.setEnabled(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al actualizar la compra: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnActualizaraccionBotonActualizar

    private void btnLimpiaraccionBotonLimpiar(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiaraccionBotonLimpiar
        limpiar();
    }//GEN-LAST:event_btnLimpiaraccionBotonLimpiar

    private void jButton1accionBotonAgregar(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1accionBotonAgregar
        try {
            // Obtener el índice seleccionado del comboProductos
            int indiceSeleccionado = comboProductos.getSelectedIndex();
            if (indiceSeleccionado < 0) {
                JOptionPane.showMessageDialog(this, "Seleccione un producto.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Obtener la lista de productos
            List<Producto> productos = productoControlador.obtenerTodosProductos();
            Producto productoSeleccionado = productos.get(indiceSeleccionado);

            // Obtener el precio unitario del producto
            Double precioUnitario = productoSeleccionado.getPrecio_Costo();

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
            Double subtotal = precioUnitario * cantidad;

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
    }//GEN-LAST:event_jButton1accionBotonAgregar

    private void jButton2accionBotonQuitarDetalle(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2accionBotonQuitarDetalle
        try {
            // Obtener el índice de la fila seleccionada en tablaDetalles
            int filaSeleccionada = tablaDetalles.getSelectedRow();
            if (filaSeleccionada == -1) {
                JOptionPane.showMessageDialog(this, "Seleccione un detalle para quitar.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Eliminar la fila seleccionada del modelo de la tabla
            DefaultTableModel model = (DefaultTableModel) tablaDetalles.getModel();
            model.removeRow(filaSeleccionada);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al quitar el detalle: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButton2accionBotonQuitarDetalle

    private void textBuscarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_textBuscarKeyTyped
           String textoBusqueda = textBuscar.getText().trim().toLowerCase();
        List<Compra> compras = compraControlador.obtenerTodasCompras();

        DefaultTableModel modelo = (DefaultTableModel) tablaCompras.getModel();
        modelo.setRowCount(0);

        if (compras != null) {
            for (Compra c : compras) {
                Proveedor proveedor = proveedorControlador.obtenerProveedorPorId(c.getId_Proveedor());
                String nombrProveedor = proveedor.getNombre_Proveedor()+ " " + proveedor.getTipo_distribuidor();

                if (textoBusqueda.isEmpty() ||
                    nombrProveedor.toLowerCase().contains(textoBusqueda)) {
                    Object[] fila = {
                        c.getFe_compra(),
                        c.getFe_compra(),
                        nombrProveedor,
                        c.getTotal_Compra()
                    };
                    modelo.addRow(fila);
                }
            }
        }

    }//GEN-LAST:event_textBuscarKeyTyped


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JComboBox<String> comboProductos;
    private javax.swing.JComboBox<String> comboProveedor;
    private javax.swing.JLabel hora;
    private javax.swing.JButton jButton1;
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
    private com.toedter.calendar.JDateChooser selectorfechaCompra;
    private javax.swing.JTable tablaCompras;
    private javax.swing.JTable tablaDetalles;
    private javax.swing.JTextField textBuscar;
    private javax.swing.JTextField textCantidad;
    private javax.swing.JTextField textPrecio;
    // End of variables declaration//GEN-END:variables
}
