package controllers;

import views_temp.SystemViewResponsive ;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import models.Insumo;
import dao.InsumosDAO;

/**
 *
 * @author Raz
 */
public class ProductsController implements ActionListener {
    
    private Insumo producto;
    private InsumosDAO productoConnection;
    private SystemViewResponsive  vista;
    private DefaultTableModel modelo;
    
    private Insumo productoSeleccionado;
    
    public ProductsController(Insumo producto, InsumosDAO productoConnection, SystemViewResponsive  vista) {
        this.producto = producto;
        this.productoConnection = productoConnection;
        this.vista = vista;
        
        // Tabla de productos
        this.modelo = (DefaultTableModel) vista.tblInsumos.getModel();
        
        this.vista.jButton6.addActionListener(this); // Registrar
        this.vista.jButton9.addActionListener(this); // Modificar
        this.vista.jButton8.addActionListener(this); // Eliminar
        this.vista.jButton7.addActionListener(this); // Cancelar/Limpiar
        
        configurarTabla();
        cargarProductos();
        deshabilitarBotonesEdicion();
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.jButton6) {
            registrarProducto();
        } else if (e.getSource() == vista.jButton9) {
            modificarProducto();
        } else if (e.getSource() == vista.jButton8) {
            eliminarProducto();
        } else if (e.getSource() == vista.jButton7) {
            limpiarCampos();
        }
    }
    
    private void configurarTabla() {
        vista.tblInsumos.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        
        vista.tblInsumos.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                seleccionarProductoTabla();
            }
        });
    }
    
    private void cargarProductos() {
        modelo.setRowCount(0);
        List<Insumo> productos = productoConnection.obtenerTodosLosInsumos();
        for (Insumo p : productos) {
            Object[] fila = {
                p.getId_insumo(),
                p.getCodigo(),
                p.getNombre(),
                p.getDescripcion(),
                p.getPresentacion(),
                p.getTotal_piezas(),
                p.getPrecio_unitario()
            };
            modelo.addRow(fila);
        }
    }
    
private void seleccionarProductoTabla() {
    int filaSeleccionada = vista.tblInsumos.getSelectedRow();
    if (filaSeleccionada >= 0) {
        int idProducto = (int) modelo.getValueAt(filaSeleccionada, 0);
        productoSeleccionado = productoConnection.obtenerInsumoPorId(idProducto);
        
        if (productoSeleccionado != null) {
            vista.jTextField41.setText(String.valueOf(productoSeleccionado.getId_insumo()));
            vista.jTextField38.setText(String.valueOf(productoSeleccionado.getCodigo()));
            vista.jTextField39.setText(productoSeleccionado.getNombre());
            vista.jTextField40.setText(productoSeleccionado.getDescripcion());
            vista.jTextField13.setText(productoSeleccionado.getPresentacion());
            vista.jTextField11.setText(String.valueOf(productoSeleccionado.getTotal_piezas()));
            vista.jTextField12.setText(String.valueOf(productoSeleccionado.getPrecio_unitario()));
            
            vista.jButton6.setEnabled(false);
            vista.jButton9.setEnabled(true);
            vista.jButton8.setEnabled(true);
        }
    }
}
    
private void registrarProducto() {
    if (validarCampos()) {
        String codigo;
        try {
            codigo = vista.jTextField38.getText().trim();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(vista, "El código debe ser un número entero válido.", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (productoConnection.verificarCodigoExistente(codigo, 0)) {
            JOptionPane.showMessageDialog(vista, "El código ya existe. Por favor, use otro código.", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        Insumo nuevoProducto = new Insumo();
        nuevoProducto.setCodigo(codigo);
        nuevoProducto.setNombre(vista.jTextField39.getText().trim());
        nuevoProducto.setDescripcion(vista.jTextField40.getText().trim());
        nuevoProducto.setPresentacion(vista.jTextField13.getText().trim());
        nuevoProducto.setTotal_piezas(Double.parseDouble(vista.jTextField11.getText().trim()));
        nuevoProducto.setPrecio_unitario(Double.parseDouble(vista.jTextField12.getText().trim()));
        
        if (productoConnection.registrarInsumoQuery(nuevoProducto)) {
            JOptionPane.showMessageDialog(vista, "Producto registrado exitosamente.");
            limpiarCampos();
            cargarProductos();
        } else {
            JOptionPane.showMessageDialog(vista, "Error al registrar producto.");
        }
    }
}
private void modificarProducto() {
    if (productoSeleccionado == null) {
        JOptionPane.showMessageDialog(vista, "Seleccione un producto de la tabla para modificar.");
        return;
    }
    
    if (validarCampos()) {
        String codigo;
        try {
            codigo = vista.jTextField38.getText().trim();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(vista, "El código debe ser un número entero válido.", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (productoConnection.verificarCodigoExistente(codigo, productoSeleccionado.getId_insumo())) {
            JOptionPane.showMessageDialog(vista, "El código ya existe. Por favor, use otro código.", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        productoSeleccionado.setCodigo(codigo); 
        productoSeleccionado.setNombre(vista.jTextField39.getText().trim());
        productoSeleccionado.setDescripcion(vista.jTextField40.getText().trim());
        productoSeleccionado.setPresentacion(vista.jTextField13.getText().trim());
        productoSeleccionado.setTotal_piezas(Double.parseDouble(vista.jTextField11.getText().trim()));
        productoSeleccionado.setPrecio_unitario(Double.parseDouble(vista.jTextField12.getText().trim()));
        
        if (productoConnection.actualizarInsumoQuery(productoSeleccionado)) {
            JOptionPane.showMessageDialog(vista, "Producto actualizado exitosamente.");
            limpiarCampos();
            cargarProductos();
        } else {
            JOptionPane.showMessageDialog(vista, "Error al actualizar producto.");
        }
    }
}

private boolean validarCampos() {
    if (vista.jTextField38.getText().trim().isEmpty() || 
        vista.jTextField39.getText().trim().isEmpty() ||
        vista.jTextField11.getText().trim().isEmpty() ||
        vista.jTextField12.getText().trim().isEmpty()) {
        
        JOptionPane.showMessageDialog(vista, 
            "Los campos Código, Nombre, Total Piezas y Precio Unitario son obligatorios.", 
            "Error", JOptionPane.ERROR_MESSAGE);
        return false;
    }
    
    try {
        int codigo = Integer.parseInt(vista.jTextField38.getText().trim());
        if (codigo <= 0) {
            JOptionPane.showMessageDialog(vista, 
                "El código debe ser un número entero positivo.", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(vista, 
            "El código debe ser un número entero válido.", 
            "Error", JOptionPane.ERROR_MESSAGE);
        return false;
    }
    
    try {
        double totalPiezas = Double.parseDouble(vista.jTextField11.getText().trim());
        double precio = Double.parseDouble(vista.jTextField12.getText().trim());
        
        if (totalPiezas < 0) {
            JOptionPane.showMessageDialog(vista, 
                "Total Piezas debe ser un valor positivo o cero.", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        if (precio < 0) {
            JOptionPane.showMessageDialog(vista, 
                "Precio Unitario debe ser un valor positivo o cero.", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(vista, 
            "Total Piezas y Precio Unitario deben ser números válidos.", 
            "Error", JOptionPane.ERROR_MESSAGE);
        return false;
    }
    
    return true;
}
    
    private void eliminarProducto() {
        if (productoSeleccionado == null) {
            JOptionPane.showMessageDialog(vista, "Seleccione un producto de la tabla para eliminar.");
            return;
        }
        
        int confirmacion = JOptionPane.showConfirmDialog(vista, 
            "¿Está seguro de eliminar el producto: " + productoSeleccionado.getNombre() + "?", 
            "Confirmar eliminación", 
            JOptionPane.YES_NO_OPTION);
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            if (productoConnection.eliminarInsumoQuery(productoSeleccionado.getId_insumo())) {
                JOptionPane.showMessageDialog(vista, "Producto eliminado exitosamente.");
                limpiarCampos();
                cargarProductos();
            } else {
                JOptionPane.showMessageDialog(vista, "Error al eliminar producto.");
            }
        }
    }
    
    private void limpiarCampos() {
        vista.jTextField41.setText("");
        vista.jTextField38.setText("");
        vista.jTextField39.setText("");
        vista.jTextField40.setText("");
        vista.jTextField13.setText("");
        vista.jTextField11.setText("");
        vista.jTextField12.setText("");
        
        if (vista.tblInsumos.getSelectedRow() >= 0) {
            vista.tblInsumos.clearSelection();
        }
        
        productoSeleccionado = null;
        deshabilitarBotonesEdicion();
    }
    
    private void deshabilitarBotonesEdicion() {
        vista.jButton6.setEnabled(true);
        vista.jButton9.setEnabled(false);
        vista.jButton8.setEnabled(false);
    }
    
    private void buscarProductos() {
    String criterio = JOptionPane.showInputDialog(vista, "Ingrese nombre, código o descripción a buscar:");
    if (criterio != null && !criterio.trim().isEmpty()) {
        modelo.setRowCount(0);
        List<Insumo> productos = productoConnection.buscarInsumos(criterio.trim());
        for (Insumo p : productos) {
            Object[] fila = {
                p.getId_insumo(),
                p.getCodigo(),
                p.getNombre(),
                p.getDescripcion(),
                p.getPresentacion(),
                p.getTotal_piezas(),
                p.getPrecio_unitario()
            };
            modelo.addRow(fila);
        }
        
        if (productos.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "No se encontraron productos con ese criterio.");
        }
    }
}
}