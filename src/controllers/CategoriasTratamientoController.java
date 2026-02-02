package controllers;

import views.SystemViewResponsive;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import models.CategoriaTratamiento;
import dao.CategoriasTratamientoDAO;

/**
 * Controlador para el CRUD de categorías de tratamientos
 * @author Raz
 */
public class CategoriasTratamientoController implements ActionListener {

    private CategoriaTratamiento categoria;
    private CategoriasTratamientoDAO categoriaDAO;
    private SystemViewResponsive vista;
    private DefaultTableModel modelo;
    
    private CategoriaTratamiento categoriaSeleccionada;

    public CategoriasTratamientoController(CategoriaTratamiento categoria, 
                                           CategoriasTratamientoDAO categoriaDAO, 
                                           SystemViewResponsive vista) {
        this.categoria = categoria;
        this.categoriaDAO = categoriaDAO;
        this.vista = vista;
        
        // Tabla de categorías en DialogCategorias
        this.modelo = (DefaultTableModel) vista.tablaTratamientos.getModel();
        
        // Configurar listeners de botones del diálogo
        this.vista.btnRegistrar.addActionListener(this);
        this.vista.btnModificar.addActionListener(this);
        this.vista.btnEliminar.addActionListener(this);
        this.vista.btnCancelar.addActionListener(this);
        this.vista.btnBuscar.addActionListener(this);
        
        configurarTabla();
        cargarCategorias();
        deshabilitarBotonesEdicion();
        
        // Limpiar campos al inicio
        limpiarCampos();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.btnRegistrar) {
            registrarCategoria();
        } else if (e.getSource() == vista.btnModificar) {
            modificarCategoria();
        } else if (e.getSource() == vista.btnEliminar) {
            eliminarCategoria();
        } else if (e.getSource() == vista.btnCancelar) {
            limpiarCampos();
        } else if (e.getSource() == vista.btnBuscar) {
            buscarCategoria();
        }
    }

    private void configurarTabla() {
        vista.tablaTratamientos.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        
        vista.tablaTratamientos.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                seleccionarCategoriaTabla();
            }
        });
    }

    private void cargarCategorias() {
        modelo.setRowCount(0);
        List<CategoriaTratamiento> categorias = categoriaDAO.listarCategorias();
        
        for (CategoriaTratamiento cat : categorias) {
            Object[] fila = {
                cat.getIdCategoria(),
                cat.getNombre(),
                cat.getCodigo()
            };
            modelo.addRow(fila);
        }
    }
    
    private void buscarCategoria() {
        String textoBusqueda = vista.textefieldBuscar.getText().trim();
        
        if (textoBusqueda.isEmpty()) {
            cargarCategorias();
            return;
        }
        
        modelo.setRowCount(0);
        List<CategoriaTratamiento> categorias = categoriaDAO.listarCategorias();
        
        for (CategoriaTratamiento cat : categorias) {
            // Buscar por nombre o código
            if (cat.getNombre().toLowerCase().contains(textoBusqueda.toLowerCase()) ||
                cat.getCodigo().toLowerCase().contains(textoBusqueda.toLowerCase())) {
                
                Object[] fila = {
                    cat.getIdCategoria(),
                    cat.getNombre(),
                    cat.getCodigo()
                };
                modelo.addRow(fila);
            }
        }
    }

    private void seleccionarCategoriaTabla() {
        int filaSeleccionada = vista.tablaTratamientos.getSelectedRow();
        if (filaSeleccionada >= 0) {
            int idCategoria = (int) modelo.getValueAt(filaSeleccionada, 0);
            categoriaSeleccionada = obtenerCategoriaPorId(idCategoria);
            
            if (categoriaSeleccionada != null) {
                vista.textfieldIdentificador.setText(String.valueOf(categoriaSeleccionada.getIdCategoria()));
                vista.txtfieldNombre.setText(categoriaSeleccionada.getNombre());
                vista.textfieldCodigo.setText(categoriaSeleccionada.getCodigo());
                
                vista.btnRegistrar.setEnabled(false);
                vista.btnModificar.setEnabled(true);
                vista.btnEliminar.setEnabled(true);
            }
        }
    }
    
    private CategoriaTratamiento obtenerCategoriaPorId(int id) {
        List<CategoriaTratamiento> categorias = categoriaDAO.listarCategorias();
        for (CategoriaTratamiento cat : categorias) {
            if (cat.getIdCategoria() == id) {
                return cat;
            }
        }
        return null;
    }

    private void registrarCategoria() {
        if (validarCampos()) {
            CategoriaTratamiento nuevaCategoria = new CategoriaTratamiento();
            
            nuevaCategoria.setNombre(vista.txtfieldNombre.getText().trim());
            nuevaCategoria.setCodigo(vista.textfieldCodigo.getText().trim());
            
            if (categoriaDAO.registrarCategoria(nuevaCategoria)) {
                JOptionPane.showMessageDialog(vista.DialogCategorias, 
                    "Categoría registrada exitosamente.",
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
                limpiarCampos();
                cargarCategorias();
            } else {
                JOptionPane.showMessageDialog(vista.DialogCategorias, 
                    "Error al registrar categoría. Verifique que el código no esté duplicado.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void modificarCategoria() {
        if (categoriaSeleccionada == null) {
            JOptionPane.showMessageDialog(vista.DialogCategorias, 
                "Seleccione una categoría de la tabla para modificar.",
                "Atención", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (validarCampos()) {
            categoriaSeleccionada.setNombre(vista.txtfieldNombre.getText().trim());
            categoriaSeleccionada.setCodigo(vista.textfieldCodigo.getText().trim());
            
            if (categoriaDAO.actualizarCategoria(categoriaSeleccionada)) {
                JOptionPane.showMessageDialog(vista.DialogCategorias, 
                    "Categoría actualizada exitosamente.",
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
                limpiarCampos();
                cargarCategorias();
            } else {
                JOptionPane.showMessageDialog(vista.DialogCategorias, 
                    "Error al actualizar categoría.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private boolean validarCampos() {
        // Validar nombre
        if (vista.txtfieldNombre.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(vista.DialogCategorias,
                "El campo Nombre es obligatorio.",
                "Error", JOptionPane.ERROR_MESSAGE);
            vista.txtfieldNombre.requestFocus();
            return false;
        }
        
        // Validar código
        if (vista.textfieldCodigo.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(vista.DialogCategorias,
                "El campo Código es obligatorio.",
                "Error", JOptionPane.ERROR_MESSAGE);
            vista.textfieldCodigo.requestFocus();
            return false;
        }
        
        return true;
    }

    private void eliminarCategoria() {
        if (categoriaSeleccionada == null) {
            JOptionPane.showMessageDialog(vista.DialogCategorias, 
                "Seleccione una categoría de la tabla para eliminar.",
                "Atención", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int confirmacion = JOptionPane.showConfirmDialog(vista.DialogCategorias,
            "¿Está seguro de eliminar la categoría: " + categoriaSeleccionada.getNombre() + "?\n" +
            "NOTA: Esta acción no se puede deshacer.",
            "Confirmar eliminación",
            JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            if (categoriaDAO.eliminarCategoria(categoriaSeleccionada.getIdCategoria())) {
                JOptionPane.showMessageDialog(vista.DialogCategorias, 
                    "Categoría eliminada exitosamente.",
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
                limpiarCampos();
                cargarCategorias();
            } else {
                JOptionPane.showMessageDialog(vista.DialogCategorias, 
                    "No se puede eliminar la categoría porque tiene tratamientos asociados.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void limpiarCampos() {
        vista.textfieldIdentificador.setText("");
        vista.txtfieldNombre.setText("");
        vista.textfieldCodigo.setText("");
        vista.textefieldBuscar.setText("");
        
        if (vista.tablaTratamientos.getSelectedRow() >= 0) {
            vista.tablaTratamientos.clearSelection();
        }
        
        categoriaSeleccionada = null;
        deshabilitarBotonesEdicion();
    }

    private void deshabilitarBotonesEdicion() {
        vista.btnRegistrar.setEnabled(true);
        vista.btnModificar.setEnabled(false);
        vista.btnEliminar.setEnabled(false);
    }
}