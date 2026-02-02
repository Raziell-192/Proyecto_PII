package controllers;

import models.TipoPrecio;
import dao.TipoPrecioDAO;
import views.SystemViewResponsive;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.util.List;

/**
 * Controlador para la gestión de Tipos de Precio
 * 
 * @author Jakim
 */
public class TipoPrecioController {
    
    private TipoPrecio tipoPrecio;
    private TipoPrecioDAO tipoPrecioDAO;
    private SystemViewResponsive view;
    
    public TipoPrecioController(TipoPrecio tipoPrecio, TipoPrecioDAO tipoPrecioDAO, SystemViewResponsive view) {
        this.tipoPrecio = tipoPrecio;
        this.tipoPrecioDAO = tipoPrecioDAO;
        this.view = view;
        
        // Configurar listeners para los botones del diálogo
        configurarListeners();
        
        // Cargar datos iniciales
        cargarTiposPrecio();
    }
    
    private void configurarListeners() {
        // Botón Buscar
        view.jButton13.addActionListener(e -> buscarTipoPrecio());
        
        // Botón Registrar
        view.jButton14.addActionListener(e -> registrarTipoPrecio());
        
        // Botón Modificar
        view.jButton15.addActionListener(e -> modificarTipoPrecio());
        
        // Botón Eliminar
        view.jButton16.addActionListener(e -> eliminarTipoPrecio());
        
        // Botón Cancelar
        view.jButton17.addActionListener(e -> limpiarFormulario());
        
        // Configurar selección en la tabla
        view.jTable4.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                seleccionarTipoPrecio();
            }
        });
    }
    
    public void cargarTiposPrecio() {
        List<TipoPrecio> tiposPrecio = tipoPrecioDAO.listarTiposPrecio();
        DefaultTableModel model = (DefaultTableModel) view.jTable4.getModel();
        model.setRowCount(0); // Limpiar tabla
        
        for (TipoPrecio tipo : tiposPrecio) {
            Object[] fila = {
                tipo.getIdTipoPrecio(),
                tipo.getNombre(),
                tipo.getCodigo()
            };
            model.addRow(fila);
        }
    }
    
    private void buscarTipoPrecio() {
        String criterio = view.jTextField17.getText().trim();
        List<TipoPrecio> resultados = tipoPrecioDAO.buscarTiposPrecio(criterio);
        DefaultTableModel model = (DefaultTableModel) view.jTable4.getModel();
        model.setRowCount(0); // Limpiar tabla
        
        for (TipoPrecio tipo : resultados) {
            Object[] fila = {
                tipo.getIdTipoPrecio(),
                tipo.getNombre(),
                tipo.getCodigo()
            };
            model.addRow(fila);
        }
    }
    
    private void registrarTipoPrecio() {
        // Validar campos
        if (!validarCampos()) {
            return;
        }
        
        // Crear objeto TipoPrecio
        TipoPrecio nuevoTipo = new TipoPrecio();
        nuevoTipo.setNombre(view.jTextField19.getText().trim());
        nuevoTipo.setCodigo(view.jTextField20.getText().trim().toUpperCase());
        
        // Verificar si el código ya existe
        if (tipoPrecioDAO.existeCodigo(nuevoTipo.getCodigo(), 0)) {
            JOptionPane.showMessageDialog(view, 
                "El código '" + nuevoTipo.getCodigo() + "' ya existe.\n" +
                "Por favor, use un código diferente.",
                "Código Duplicado", 
                JOptionPane.WARNING_MESSAGE);
            view.jTextField20.requestFocus();
            return;
        }
        
        // Insertar en base de datos
        if (tipoPrecioDAO.insertarTipoPrecio(nuevoTipo)) {
            JOptionPane.showMessageDialog(view, 
                "Tipo de precio registrado exitosamente", 
                "Éxito", 
                JOptionPane.INFORMATION_MESSAGE);
            limpiarFormulario();
            cargarTiposPrecio();
        } else {
            JOptionPane.showMessageDialog(view, 
                "Error al registrar tipo de precio", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void modificarTipoPrecio() {
        // Verificar si hay una fila seleccionada
        int filaSeleccionada = view.jTable4.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(view, 
                "Seleccione un tipo de precio para modificar", 
                "Advertencia", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Obtener ID del tipo seleccionado
        int idTipoPrecio = (int) view.jTable4.getValueAt(filaSeleccionada, 0);
        
        // Validar campos
        if (!validarCampos()) {
            return;
        }
        
        // Verificar si el código ya existe 
        String nuevoCodigo = view.jTextField20.getText().trim().toUpperCase();
        if (tipoPrecioDAO.existeCodigo(nuevoCodigo, idTipoPrecio)) {
            JOptionPane.showMessageDialog(view, 
                "El código '" + nuevoCodigo + "' ya existe.\n" +
                "Por favor, use un código diferente.",
                "Código Duplicado", 
                JOptionPane.WARNING_MESSAGE);
            view.jTextField20.requestFocus();
            return;
        }
        
        // Crear objeto con los datos actualizados
        TipoPrecio tipoActualizado = new TipoPrecio();
        tipoActualizado.setIdTipoPrecio(idTipoPrecio);
        tipoActualizado.setNombre(view.jTextField19.getText().trim());
        tipoActualizado.setCodigo(nuevoCodigo);
        
        // Actualizar en base de datos
        if (tipoPrecioDAO.actualizarTipoPrecio(tipoActualizado)) {
            JOptionPane.showMessageDialog(view, 
                "Tipo de precio actualizado exitosamente", 
                "Éxito", 
                JOptionPane.INFORMATION_MESSAGE);
            limpiarFormulario();
            cargarTiposPrecio();
        } else {
            JOptionPane.showMessageDialog(view, 
                "Error al actualizar tipo de precio", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void eliminarTipoPrecio() {
        int filaSeleccionada = view.jTable4.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(view, 
                "Seleccione un tipo de precio para eliminar", 
                "Advertencia", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int idTipoPrecio = (int) view.jTable4.getValueAt(filaSeleccionada, 0);
        String nombreTipo = (String) view.jTable4.getValueAt(filaSeleccionada, 1);
        
        // Confirmar eliminación
        int confirmacion = JOptionPane.showConfirmDialog(view,
            "¿Está seguro de eliminar el tipo de precio:\n" +
            "'" + nombreTipo + "'?\n\n" +
            "Esta acción no se puede deshacer.",
            "Confirmar Eliminación",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            if (tipoPrecioDAO.eliminarTipoPrecio(idTipoPrecio)) {
                JOptionPane.showMessageDialog(view, 
                    "Tipo de precio eliminado exitosamente", 
                    "Éxito", 
                    JOptionPane.INFORMATION_MESSAGE);
                limpiarFormulario();
                cargarTiposPrecio();
            } else {
                JOptionPane.showMessageDialog(view, 
                    "Error al eliminar tipo de precio.\n" +
                    "Verifique que no esté siendo utilizado en ventas.", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void seleccionarTipoPrecio() {
        int filaSeleccionada = view.jTable4.getSelectedRow();
        if (filaSeleccionada != -1) {
            // Obtener datos de la fila seleccionada
            int idTipoPrecio = (int) view.jTable4.getValueAt(filaSeleccionada, 0);
            String nombre = (String) view.jTable4.getValueAt(filaSeleccionada, 1);
            String codigo = (String) view.jTable4.getValueAt(filaSeleccionada, 2);
            
            // Llenar campos del formulario
            view.textfieldIdentificador.setText(String.valueOf(idTipoPrecio));
            view.jTextField19.setText(nombre);
            view.jTextField20.setText(codigo);
        }
    }
    
    private void limpiarFormulario() {
        view.textfieldIdentificador.setText("");
        view.jTextField19.setText("");
        view.jTextField20.setText("");
        view.jTextField17.setText("");
        view.jTable4.clearSelection();
        view.jTextField19.requestFocus();
        cargarTiposPrecio();
    }
    
    private boolean validarCampos() {
        String nombre = view.jTextField19.getText().trim();
        String codigo = view.jTextField20.getText().trim();
        
        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(view, 
                "El nombre es obligatorio", 
                "Error de Validación", 
                JOptionPane.ERROR_MESSAGE);
            view.jTextField19.requestFocus();
            return false;
        }
        
        if (codigo.isEmpty()) {
            JOptionPane.showMessageDialog(view, 
                "El código es obligatorio", 
                "Error de Validación", 
                JOptionPane.ERROR_MESSAGE);
            view.jTextField20.requestFocus();
            return false;
        }
        
        if (codigo.length() > 20) {
            JOptionPane.showMessageDialog(view, 
                "El código no puede tener más de 20 caracteres", 
                "Error de Validación", 
                JOptionPane.ERROR_MESSAGE);
            view.jTextField20.requestFocus();
            return false;
        }
        
        return true;
    }
}