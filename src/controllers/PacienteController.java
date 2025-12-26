package controllers;

import Views.SystemView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import models.Paciente;
import models.PacienteConnection;

/**
 *
 * @author Raz
 */
public class PacienteController implements ActionListener {
    
    private Paciente paciente;
    private PacienteConnection pacienteConexion;
    private SystemView vista;
    private DefaultTableModel modelo;
    
    private Paciente pacienteSeleccionado;
    
    public PacienteController(Paciente paciente, PacienteConnection pacienteConexion, SystemView vista) {
        this.paciente = paciente;
        this.pacienteConexion = pacienteConexion;
        this.vista = vista;
        
        this.modelo = (DefaultTableModel) vista.jTable3.getModel();
        
        this.vista.btnBuscarPaciente.addActionListener(this);
        this.vista.btnEditarPaciente.addActionListener(this);
        this.vista.btnEliminarPaciente.addActionListener(this);
        this.vista.btnMostrarPaciente.addActionListener(this);
        this.vista.btnRegistrarPaciente.addActionListener(this);         
        configurarTabla();
        cargarPacientes();        
        vista.btnEditarPaciente.setEnabled(false);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.btnBuscarPaciente) {
            buscarPacientes();
        } else if (e.getSource() == vista.btnEditarPaciente) {
            editarPaciente();
        } else if (e.getSource() == vista.btnEliminarPaciente) {
            eliminarPaciente();
        } else if (e.getSource() == vista.btnMostrarPaciente) {
            cargarPacientes();
        } else if (e.getSource() == vista.btnRegistrarPaciente) {
            guardarPaciente(); 
        }
    }
    
    private void configurarTabla() {
        vista.jTable3.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);        
        vista.jTable3.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                seleccionarPacienteTabla();
            }
        });
    }
    
    private void cargarPacientes() {
        modelo.setRowCount(0);
        List<Paciente> pacientes = pacienteConexion.obtenerTodosLosPacientes();
        for (Paciente p : pacientes) {
            Object[] fila = {
                p.getIdPaciente(),
                p.getNombres(),
                p.getApellidos(),
                p.getTelefono(),
                p.getTipoAfiliacion()
            };
            modelo.addRow(fila);
        }
        limpiarSeleccion();
    }
    
    private void buscarPacientes() {
        String criterio = JOptionPane.showInputDialog(vista, "Ingrese nombre, apellido o teléfono a buscar:");
        if (criterio != null && !criterio.trim().isEmpty()) {
            modelo.setRowCount(0); 
            List<Paciente> pacientes = pacienteConexion.buscarPacientes(criterio.trim());
            for (Paciente p : pacientes) {
                Object[] fila = {
                    p.getIdPaciente(),
                    p.getNombres(),
                    p.getApellidos(),
                    p.getTelefono(),
                    p.getTipoAfiliacion()
                };
                modelo.addRow(fila);
            }
            
            if (pacientes.isEmpty()) {
                JOptionPane.showMessageDialog(vista, "No se encontraron pacientes con ese criterio.");
            }
            limpiarSeleccion();
        }
    }
    
    private void seleccionarPacienteTabla() {
        int filaSeleccionada = vista.jTable3.getSelectedRow();
        if (filaSeleccionada >= 0) {
            int idPaciente = (int) modelo.getValueAt(filaSeleccionada, 0);
            pacienteSeleccionado = pacienteConexion.obtenerPacientePorId(idPaciente);
            
            if (pacienteSeleccionado != null) {
                vista.jTextField1.setText(pacienteSeleccionado.getNombres());
                vista.jTextField2.setText(pacienteSeleccionado.getApellidos());
                vista.jTextField4.setText(pacienteSeleccionado.getTelefono());                
                String afiliacion = pacienteSeleccionado.getTipoAfiliacion();
                if (afiliacion != null) {
                    if (afiliacion.equals("General")) {
                        vista.jComboBox2.setSelectedIndex(0);
                    } else if (afiliacion.equals("Comunidad UNSIS")) {
                        vista.jComboBox2.setSelectedIndex(1);
                    } else {
                        vista.jComboBox2.setSelectedItem(afiliacion);
                    }
                }                
                vista.btnEditarPaciente.setEnabled(true);
                vista.btnRegistrarPaciente.setEnabled(false);
                vista.btnEliminarPaciente.setEnabled(true);
            }
        }
    }
    
    private void guardarPaciente() {
        if (validarCampos()) {
            Paciente nuevoPaciente = new Paciente();
            nuevoPaciente.setNombres(vista.jTextField1.getText().trim());
            nuevoPaciente.setApellidos(vista.jTextField2.getText().trim());
            nuevoPaciente.setTelefono(vista.jTextField4.getText().trim());
            nuevoPaciente.setTipoAfiliacion(vista.jComboBox2.getSelectedItem().toString());
            if (pacienteConexion.registrarPacienteQuery(nuevoPaciente)) {
                JOptionPane.showMessageDialog(vista, "Paciente registrado exitosamente.");
                limpiarCampos();
                cargarPacientes();
            } else {
                JOptionPane.showMessageDialog(vista, "Error al registrar paciente.");
            }
        }
    }
    
    private void editarPaciente() {
        if (pacienteSeleccionado == null) {
            JOptionPane.showMessageDialog(vista, "Seleccione un paciente de la tabla para editar.");
            return;
        }
        
        if (validarCampos()) {
            pacienteSeleccionado.setNombres(vista.jTextField1.getText().trim());
            pacienteSeleccionado.setApellidos(vista.jTextField2.getText().trim());
            pacienteSeleccionado.setTelefono(vista.jTextField4.getText().trim());
            pacienteSeleccionado.setTipoAfiliacion(vista.jComboBox2.getSelectedItem().toString());
            if (pacienteConexion.actualizarPacienteQuery(pacienteSeleccionado)) {
                JOptionPane.showMessageDialog(vista, "Paciente actualizado exitosamente.");
                limpiarCampos();
                cargarPacientes();
            } else {
                JOptionPane.showMessageDialog(vista, "Error al actualizar paciente.");
            }
        }
    }
    
    private void eliminarPaciente() {
        if (pacienteSeleccionado == null) {
            JOptionPane.showMessageDialog(vista, "Seleccione un paciente de la tabla para eliminar.");
            return;
        }
        
        int confirmacion = JOptionPane.showConfirmDialog(vista, 
            "¿Está seguro de eliminar al paciente: " + 
            pacienteSeleccionado.getNombres() + " " + 
            pacienteSeleccionado.getApellidos() + "?", 
            "Confirmar eliminación", 
            JOptionPane.YES_NO_OPTION);
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            if (pacienteConexion.eliminarPacienteQuery(pacienteSeleccionado.getIdPaciente())) {
                JOptionPane.showMessageDialog(vista, "Paciente eliminado exitosamente.");
                limpiarCampos();
                cargarPacientes();
            } else {
                JOptionPane.showMessageDialog(vista, "Error al eliminar paciente.");
            }
        }
    }
    
    private boolean validarCampos() {
        if (vista.jTextField1.getText().trim().isEmpty() || 
            vista.jTextField2.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Los campos Nombres y Apellidos son obligatorios.");
            return false;
        }
        return true;
    }
    
    private void limpiarCampos() {
        vista.jTextField1.setText("");
        vista.jTextField2.setText("");
        vista.jTextField4.setText("");
        vista.jComboBox2.setSelectedIndex(0);
        
        if (vista.jTable3.getSelectedRow() >= 0) {
            vista.jTable3.clearSelection();
        }
        pacienteSeleccionado = null;
        vista.btnRegistrarPaciente.setEnabled(true);
        vista.btnEditarPaciente.setEnabled(false);
        vista.btnEliminarPaciente.setEnabled(false);
    }
    
    private void limpiarSeleccion() {
        if (vista.jTable3.getSelectedRow() >= 0) {
            vista.jTable3.clearSelection();
        }
        
        if (pacienteSeleccionado == null) {
            vista.jTextField1.setText("");
            vista.jTextField2.setText("");
            vista.jTextField4.setText("");
            vista.jComboBox2.setSelectedIndex(0);
        }        
        vista.btnRegistrarPaciente.setEnabled(true);
        vista.btnEditarPaciente.setEnabled(false);
        vista.btnEliminarPaciente.setEnabled(false);
    }
}