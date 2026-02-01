package controllers;

import views.SystemViewResponsive;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import models.Tratamiento;
import dao.TratamientosDAO;
import models.CategoriaTratamiento;
import dao.CategoriasTratamientoDAO;

/**
 * Controlador para el CRUD de tratamientos odontológicos con ComboBox para categorías
 * @author Raz
 */
public class TratamientosController implements ActionListener {

    private Tratamiento tratamiento;
    private TratamientosDAO tratamientoDAO;
    private CategoriasTratamientoDAO categoriaDAO;
    private SystemViewResponsive vista;
    private DefaultTableModel modelo;
    
    private Tratamiento tratamientoSeleccionado;
    private List<CategoriaTratamiento> listaCategorias;

    public TratamientosController(Tratamiento tratamiento, TratamientosDAO tratamientoDAO, 
                                  CategoriasTratamientoDAO categoriaDAO, SystemViewResponsive vista) {
        this.tratamiento = tratamiento;
        this.tratamientoDAO = tratamientoDAO;
        this.categoriaDAO = categoriaDAO;
        this.vista = vista;
        
        // Tabla de tratamientos
        this.modelo = (DefaultTableModel) vista.tblTratamientos.getModel();
        
        // Configurar listeners de botones
        this.vista.btnRegistrarTratamiento.addActionListener(this);
        this.vista.btnModificarTratamiento.addActionListener(this);
        this.vista.btnEliminarTratamiento.addActionListener(this);
        this.vista.btnCancelarTratamiento.addActionListener(this);
        
        // Cargar categorías en ComboBox
        cargarCategoriasEnComboBox();
        
        configurarTabla();
        cargarTratamientos();
        deshabilitarBotonesEdicion();
        
        // Limpiar campos al inicio
        limpiarCampos();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.btnRegistrarTratamiento) {
            registrarTratamiento();
        } else if (e.getSource() == vista.btnModificarTratamiento) {
            modificarTratamiento();
        } else if (e.getSource() == vista.btnEliminarTratamiento) {
            eliminarTratamiento();
        } else if (e.getSource() == vista.btnCancelarTratamiento) {
            limpiarCampos();
        }
    }

    private void configurarTabla() {
        vista.tblTratamientos.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        
        vista.tblTratamientos.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                seleccionarTratamientoTabla();
            }
        });
    }

    private void cargarCategoriasEnComboBox() {
        try {
            listaCategorias = categoriaDAO.listarCategorias();
            vista.cmbCategoriaTratamiento.removeAllItems();
            
            // Agregar opción vacía al inicio
            vista.cmbCategoriaTratamiento.addItem("-- Seleccione categoría --");
            
            for (CategoriaTratamiento cat : listaCategorias) {
                vista.cmbCategoriaTratamiento.addItem(cat.getNombre());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(vista, "Error al cargar categorías: " + e.getMessage());
        }
    }
    
    private int obtenerIdCategoriaSeleccionada() {
        String nombreCategoria = (String) vista.cmbCategoriaTratamiento.getSelectedItem();
        
        if (nombreCategoria == null || nombreCategoria.equals("-- Seleccione categoría --")) {
            return 0;
        }
        
        for (CategoriaTratamiento cat : listaCategorias) {
            if (cat.getNombre().equals(nombreCategoria)) {
                return cat.getIdCategoria();
            }
        }
        return 0;
    }
    
    private void seleccionarCategoriaEnComboBox(int idCategoria) {
        for (CategoriaTratamiento cat : listaCategorias) {
            if (cat.getIdCategoria() == idCategoria) {
                vista.cmbCategoriaTratamiento.setSelectedItem(cat.getNombre());
                return;
            }
        }
        vista.cmbCategoriaTratamiento.setSelectedIndex(0);
    }

    private void cargarTratamientos() {
        modelo.setRowCount(0);
        List<Tratamiento> tratamientos = tratamientoDAO.listarTratamientosQuery();
        
        for (Tratamiento t : tratamientos) {
            Object[] fila = {
                t.getIdTratamiento(),
                obtenerNombreCategoria(t.getIdCategoria()),
                t.getCodigo(),
                t.getNombre(),
                (t.getDescripcion() != null ? t.getDescripcion() : "")
            };
            modelo.addRow(fila);
        }
    }
    
    private String obtenerNombreCategoria(int idCategoria) {
        if (listaCategorias != null) {
            for (CategoriaTratamiento cat : listaCategorias) {
                if (cat.getIdCategoria() == idCategoria) {
                    return cat.getNombre();
                }
            }
        }
        return String.valueOf(idCategoria);
    }

    private void seleccionarTratamientoTabla() {
        int filaSeleccionada = vista.tblTratamientos.getSelectedRow();
        if (filaSeleccionada >= 0) {
            int idTratamiento = (int) modelo.getValueAt(filaSeleccionada, 0);
            tratamientoSeleccionado = obtenerTratamientoPorId(idTratamiento);
            
            if (tratamientoSeleccionado != null) {
                vista.jTextField44.setText(String.valueOf(tratamientoSeleccionado.getIdTratamiento()));
                seleccionarCategoriaEnComboBox(tratamientoSeleccionado.getIdCategoria());
                vista.jTextField47.setText(tratamientoSeleccionado.getCodigo());
                vista.jTextField43.setText(tratamientoSeleccionado.getNombre());
                vista.jTextField45.setText(tratamientoSeleccionado.getDescripcion());
                
                vista.btnRegistrarTratamiento.setEnabled(false);
                vista.btnModificarTratamiento.setEnabled(true);
                vista.btnEliminarTratamiento.setEnabled(true);
            }
        }
    }
    
    private Tratamiento obtenerTratamientoPorId(int id) {
        List<Tratamiento> tratamientos = tratamientoDAO.listarTratamientosQuery();
        for (Tratamiento t : tratamientos) {
            if (t.getIdTratamiento() == id) {
                return t;
            }
        }
        return null;
    }

    private void registrarTratamiento() {
        if (validarCampos()) {
            Tratamiento nuevoTratamiento = new Tratamiento();
            
            int idCategoria = obtenerIdCategoriaSeleccionada();
            if (idCategoria == 0) {
                JOptionPane.showMessageDialog(vista, 
                    "Seleccione una categoría válida.",
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            nuevoTratamiento.setIdCategoria(idCategoria);
            nuevoTratamiento.setCodigo(vista.jTextField47.getText().trim());
            nuevoTratamiento.setNombre(vista.jTextField43.getText().trim());
            nuevoTratamiento.setDescripcion(vista.jTextField45.getText().trim());
            
            if (tratamientoDAO.registrarTratamientoQuery(nuevoTratamiento)) {
                JOptionPane.showMessageDialog(vista, "Tratamiento registrado exitosamente.");
                limpiarCampos();
                cargarTratamientos();
            } else {
                JOptionPane.showMessageDialog(vista, "Error al registrar tratamiento.");
            }
        }
    }

    private void modificarTratamiento() {
        if (tratamientoSeleccionado == null) {
            JOptionPane.showMessageDialog(vista, "Seleccione un tratamiento de la tabla para modificar.");
            return;
        }
        
        if (validarCampos()) {
            int idCategoria = obtenerIdCategoriaSeleccionada();
            if (idCategoria == 0) {
                JOptionPane.showMessageDialog(vista, 
                    "Seleccione una categoría válida.",
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            tratamientoSeleccionado.setIdCategoria(idCategoria);
            tratamientoSeleccionado.setCodigo(vista.jTextField47.getText().trim());
            tratamientoSeleccionado.setNombre(vista.jTextField43.getText().trim());
            tratamientoSeleccionado.setDescripcion(vista.jTextField45.getText().trim());
            
            if (tratamientoDAO.actualizarTratamientoQuery(tratamientoSeleccionado)) {
                JOptionPane.showMessageDialog(vista, "Tratamiento actualizado exitosamente.");
                limpiarCampos();
                cargarTratamientos();
            } else {
                JOptionPane.showMessageDialog(vista, "Error al actualizar tratamiento.");
            }
        }
    }

    private boolean validarCampos() {
        // Validar categoría seleccionada
        if (vista.cmbCategoriaTratamiento.getSelectedIndex() <= 0) {
            JOptionPane.showMessageDialog(vista,
                "Seleccione una categoría.",
                "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        // Validar código
        if (vista.jTextField47.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(vista,
                "El campo Código es obligatorio.",
                "Error", JOptionPane.ERROR_MESSAGE);
            vista.jTextField47.requestFocus();
            return false;
        }
        
        // Validar nombre
        if (vista.jTextField43.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(vista,
                "El campo Nombre es obligatorio.",
                "Error", JOptionPane.ERROR_MESSAGE);
            vista.jTextField43.requestFocus();
            return false;
        }
        
        return true;
    }

    private void eliminarTratamiento() {
        if (tratamientoSeleccionado == null) {
            JOptionPane.showMessageDialog(vista, "Seleccione un tratamiento de la tabla para eliminar.");
            return;
        }
        
        int confirmacion = JOptionPane.showConfirmDialog(vista,
            "¿Está seguro de eliminar el tratamiento: " + tratamientoSeleccionado.getNombre() + "?",
            "Confirmar eliminación",
            JOptionPane.YES_NO_OPTION);
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            if (tratamientoDAO.eliminarTratamientoQuery(tratamientoSeleccionado.getIdTratamiento())) {
                JOptionPane.showMessageDialog(vista, "Tratamiento eliminado exitosamente.");
                limpiarCampos();
                cargarTratamientos();
            } else {
                JOptionPane.showMessageDialog(vista, 
                    "Error al eliminar tratamiento. Puede tener relaciones con otras tablas.");
            }
        }
    }

    private void limpiarCampos() {
        vista.jTextField44.setText("");
        vista.cmbCategoriaTratamiento.setSelectedIndex(0);
        vista.jTextField47.setText("");
        vista.jTextField43.setText("");
        vista.jTextField45.setText("");
        
        if (vista.tblTratamientos.getSelectedRow() >= 0) {
            vista.tblTratamientos.clearSelection();
        }
        
        tratamientoSeleccionado = null;
        deshabilitarBotonesEdicion();
    }

    private void deshabilitarBotonesEdicion() {
        vista.btnRegistrarTratamiento.setEnabled(true);
        vista.btnModificarTratamiento.setEnabled(false);
        vista.btnEliminarTratamiento.setEnabled(false);
    }
}