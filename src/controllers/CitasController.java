package controllers;

import views.SystemViewResponsive;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import models.Cita;
import dao.CitaDAO;
import models.Paciente;
import dao.PacienteDAO;
import models.Tratamiento;
import dao.TratamientosDAO;

/**
 * Controlador para el CRUD de citas
 *
 * @author Raz
 */
public class CitasController implements ActionListener {

    private Cita cita;
    private CitaDAO citaDAO;
    private PacienteDAO pacienteDAO;
    private TratamientosDAO tratamientoDAO;
    private SystemViewResponsive vista;
    private DefaultTableModel modelo;

    private Cita citaSeleccionada;
    private List<Paciente> listaPacientes;
    private List<Tratamiento> listaTratamientos;

    public CitasController(Cita cita, CitaDAO citaDAO, PacienteDAO pacienteDAO,
            TratamientosDAO tratamientoDAO, SystemViewResponsive vista) {
        this.cita = cita;
        this.citaDAO = citaDAO;
        this.pacienteDAO = pacienteDAO;
        this.tratamientoDAO = tratamientoDAO;
        this.vista = vista;

        // Tabla de citas
        this.modelo = (DefaultTableModel) vista.tblCitas.getModel();

        // Configurar listeners de botones
        this.vista.btnRegistrarCita.addActionListener(this);
        this.vista.btnModificarCita.addActionListener(this);
        this.vista.btnEliminarCita.addActionListener(this);
        this.vista.btnCancelarCita.addActionListener(this);
        this.vista.jButton1.addActionListener(this); // Botón de búsqueda

        // Cargar pacientes y tratamientos en ComboBox
        cargarPacientesEnComboBox();
        cargarTratamientosEnComboBox();

        configurarTabla();
        cargarCitas();
        deshabilitarBotonesEdicion();

        // Limpiar campos al inicio
        limpiarCampos();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.btnRegistrarCita) {
            registrarCita();
        } else if (e.getSource() == vista.btnModificarCita) {
            modificarCita();
        } else if (e.getSource() == vista.btnEliminarCita) {
            eliminarCita();
        } else if (e.getSource() == vista.btnCancelarCita) {
            limpiarCampos();
        } else if (e.getSource() == vista.jButton1) {
            buscarCitas();
        }
    }

    private void configurarTabla() {
        vista.tblCitas.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        vista.tblCitas.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                seleccionarCitaTabla();
            }
        });
    }

    private void cargarPacientesEnComboBox() {
        try {
            listaPacientes = pacienteDAO.obtenerTodosLosPacientes();
            vista.ComboBoxPacientes.removeAllItems();

            // Agregar opción vacía al inicio
            vista.ComboBoxPacientes.addItem("-- Seleccione paciente --");

            for (Paciente p : listaPacientes) {
                vista.ComboBoxPacientes.addItem(p.getNombres() + " " + p.getApellidos());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(vista, "Error al cargar pacientes: " + e.getMessage());
        }
    }

    private void cargarTratamientosEnComboBox() {
        try {
            listaTratamientos = tratamientoDAO.listarTratamientosQuery();
            vista.ComboBoxTratamientos.removeAllItems();

            // Agregar opción vacía al inicio
            vista.ComboBoxTratamientos.addItem("-- Seleccione tratamiento --");

            for (Tratamiento t : listaTratamientos) {
                vista.ComboBoxTratamientos.addItem(t.getNombre());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(vista, "Error al cargar tratamientos: " + e.getMessage());
        }
    }

    private int obtenerIdPacienteSeleccionado() {
        String nombreCompleto = (String) vista.ComboBoxPacientes.getSelectedItem();

        if (nombreCompleto == null || nombreCompleto.equals("-- Seleccione paciente --")) {
            return 0;
        }

        for (Paciente p : listaPacientes) {
            String nombrePaciente = p.getNombres() + " " + p.getApellidos();
            if (nombrePaciente.equals(nombreCompleto)) {
                return p.getIdPaciente();
            }
        }
        return 0;
    }

    private int obtenerIdTratamientoSeleccionado() {
        String nombreTratamiento = (String) vista.ComboBoxTratamientos.getSelectedItem();

        if (nombreTratamiento == null || nombreTratamiento.equals("-- Seleccione tratamiento --")) {
            return 0;
        }

        for (Tratamiento t : listaTratamientos) {
            if (t.getNombre().equals(nombreTratamiento)) {
                return t.getIdTratamiento();
            }
        }
        return 0;
    }

    private void seleccionarPacienteEnComboBox(int idPaciente) {
        for (Paciente p : listaPacientes) {
            if (p.getIdPaciente() == idPaciente) {
                String nombreCompleto = p.getNombres() + " " + p.getApellidos();
                vista.ComboBoxPacientes.setSelectedItem(nombreCompleto);
                return;
            }
        }
        vista.ComboBoxPacientes.setSelectedIndex(0);
    }

    private void seleccionarTratamientoEnComboBox(int idTratamiento) {
        for (Tratamiento t : listaTratamientos) {
            if (t.getIdTratamiento() == idTratamiento) {
                vista.ComboBoxTratamientos.setSelectedItem(t.getNombre());
                return;
            }
        }
        vista.ComboBoxTratamientos.setSelectedIndex(0);
    }

    private void cargarCitas() {
        modelo.setRowCount(0);
        List<Cita> citas = citaDAO.listarCitas();

        for (Cita c : citas) {
            Object[] fila = {
                c.getIdCita(),
                obtenerNombrePaciente(c.getIdPaciente()),
                obtenerNombreTratamiento(c.getIdTratamiento()),
                c.getFechaHora()
            };
            modelo.addRow(fila);
        }
    }

    private String obtenerNombrePaciente(int idPaciente) {
        if (listaPacientes != null) {
            for (Paciente p : listaPacientes) {
                if (p.getIdPaciente() == idPaciente) {
                    return p.getNombres() + " " + p.getApellidos();
                }
            }
        }
        return String.valueOf(idPaciente);
    }

    private String obtenerNombreTratamiento(int idTratamiento) {
        if (listaTratamientos != null) {
            for (Tratamiento t : listaTratamientos) {
                if (t.getIdTratamiento() == idTratamiento) {
                    return t.getNombre();
                }
            }
        }
        return String.valueOf(idTratamiento);
    }

    private void seleccionarCitaTabla() {
        int filaSeleccionada = vista.tblCitas.getSelectedRow();
        if (filaSeleccionada >= 0) {
            int idCita = (int) modelo.getValueAt(filaSeleccionada, 0);
            citaSeleccionada = obtenerCitaPorId(idCita);

            if (citaSeleccionada != null) {
                seleccionarPacienteEnComboBox(citaSeleccionada.getIdPaciente());
                seleccionarTratamientoEnComboBox(citaSeleccionada.getIdTratamiento());

                // Convertir Timestamp a String para el campo de búsqueda
//                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                String fechaStr = sdf.format(citaSeleccionada.getFechaHora());
//                vista.jTextField3.setText(fechaStr);
                vista.btnRegistrarCita.setEnabled(false);
                vista.btnModificarCita.setEnabled(true);
                vista.btnEliminarCita.setEnabled(true);
            }
        }
    }

    private Cita obtenerCitaPorId(int id) {
        return citaDAO.obtenerCitaPorId(id);
    }

    private void registrarCita() {
        if (validarCampos()) {
            Cita nuevaCita = new Cita();

            int idPaciente = obtenerIdPacienteSeleccionado();
            int idTratamiento = obtenerIdTratamientoSeleccionado();

            if (idPaciente == 0) {
                JOptionPane.showMessageDialog(vista,
                        "Seleccione un paciente válido.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (idTratamiento == 0) {
                JOptionPane.showMessageDialog(vista,
                        "Seleccione un tratamiento válido.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            nuevaCita.setIdPaciente(idPaciente);
            nuevaCita.setIdTratamiento(idTratamiento);
//            nuevaCita.setFechaHora(obtenerFechaHoraDesdeTexto());
            nuevaCita.setFechaHora(obtenerFechaHoraSistema());

            if (citaDAO.registrarCita(nuevaCita)) {
                JOptionPane.showMessageDialog(vista, "Cita registrada exitosamente.");
                limpiarCampos();
                cargarCitas();
            } else {
                JOptionPane.showMessageDialog(vista, "Error al registrar cita.");
            }
        }
    }

    private void modificarCita() {
        if (citaSeleccionada == null) {
            JOptionPane.showMessageDialog(vista, "Seleccione una cita de la tabla para modificar.");
            return;
        }

        if (validarCampos()) {
            int idPaciente = obtenerIdPacienteSeleccionado();
            int idTratamiento = obtenerIdTratamientoSeleccionado();

            if (idPaciente == 0) {
                JOptionPane.showMessageDialog(vista,
                        "Seleccione un paciente válido.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (idTratamiento == 0) {
                JOptionPane.showMessageDialog(vista,
                        "Seleccione un tratamiento válido.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            citaSeleccionada.setIdPaciente(idPaciente);
            citaSeleccionada.setIdTratamiento(idTratamiento);

            // Obtener la fecha y validar
//            Timestamp fechaHora = obtenerFechaHoraDesdeTexto();
//            if (fechaHora == null) {
//                return;
//            }
//            citaSeleccionada.setFechaHora(fechaHora);
            citaSeleccionada.setFechaHora(obtenerFechaHoraSistema());

            if (citaDAO.actualizarCita(citaSeleccionada)) {
                JOptionPane.showMessageDialog(vista, "Cita actualizada exitosamente.");
                limpiarCampos();
                cargarCitas();
            } else {
                JOptionPane.showMessageDialog(vista, "Error al actualizar cita.");
            }
        }
    }

//    private Timestamp obtenerFechaHoraDesdeTexto() {
//        String fechaStr = vista.jTextField3.getText().trim();
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//
//        try {
//            java.util.Date fechaUtil = sdf.parse(fechaStr);
//            return new Timestamp(fechaUtil.getTime());
//        } catch (ParseException e) {
//            JOptionPane.showMessageDialog(vista,
//                    "Formato de fecha incorrecto. Use: yyyy-MM-dd HH:mm:ss",
//                    "Error", JOptionPane.ERROR_MESSAGE);
//            return null;
//        }
//    }
    private Timestamp obtenerFechaHoraSistema() {
        return new Timestamp(System.currentTimeMillis());
    }

    private boolean validarCampos() {
        // Validar paciente seleccionado
        if (vista.ComboBoxPacientes.getSelectedIndex() <= 0) {
            JOptionPane.showMessageDialog(vista,
                    "Seleccione un paciente.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Validar tratamiento seleccionado
        if (vista.ComboBoxTratamientos.getSelectedIndex() <= 0) {
            JOptionPane.showMessageDialog(vista,
                    "Seleccione un tratamiento.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Validar fecha y hora
//        if (vista.jTextField3.getText().trim().isEmpty()) {
//            JOptionPane.showMessageDialog(vista,
//                    "El campo Fecha y Hora es obligatorio.",
//                    "Error", JOptionPane.ERROR_MESSAGE);
//            vista.jTextField3.requestFocus();
//            return false;
//        }
        // Validar formato de fecha
//        try {
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            sdf.parse(vista.jTextField3.getText().trim());
//        } catch (ParseException e) {
//            JOptionPane.showMessageDialog(vista,
//                    "Formato de fecha incorrecto. Use: yyyy-MM-dd HH:mm:ss",
//                    "Error", JOptionPane.ERROR_MESSAGE);
//            vista.jTextField3.requestFocus();
//            return false;
//        }
        return true;
    }

    private void eliminarCita() {
        if (citaSeleccionada == null) {
            JOptionPane.showMessageDialog(vista, "Seleccione una cita de la tabla para eliminar.");
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(vista,
                "¿Está seguro de eliminar esta cita?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION);

        if (confirmacion == JOptionPane.YES_OPTION) {
            if (citaDAO.eliminarCita(citaSeleccionada.getIdCita())) {
                JOptionPane.showMessageDialog(vista, "Cita eliminada exitosamente.");
                limpiarCampos();
                cargarCitas();
            } else {
                JOptionPane.showMessageDialog(vista, "Error al eliminar cita.");
            }
        }
    }

    private void buscarCitas() {
        String criterio = vista.jTextField3.getText().trim();
        if (!criterio.isEmpty()) {
            modelo.setRowCount(0);
            List<Cita> citas = citaDAO.listarCitas();
            for (Cita c : citas) {
                String nombrePaciente = obtenerNombrePaciente(c.getIdPaciente());
                String nombreTratamiento = obtenerNombreTratamiento(c.getIdTratamiento());
                String fechaStr = c.getFechaHora().toString();

                if (nombrePaciente.toLowerCase().contains(criterio.toLowerCase())
                        || nombreTratamiento.toLowerCase().contains(criterio.toLowerCase())
                        || fechaStr.contains(criterio)) {

                    Object[] fila = {
                        c.getIdCita(),
                        nombrePaciente,
                        nombreTratamiento,
                        c.getFechaHora()
                    };
                    modelo.addRow(fila);
                }
            }

            if (modelo.getRowCount() == 0) {
                JOptionPane.showMessageDialog(vista, "No se encontraron citas con ese criterio.");
                cargarCitas();
            }
        } else {
            cargarCitas();
        }
    }
    
//    private void buscarCitas() {
//        String criterio = vista.jTextField3.getText().trim();
//
//        modelo.setRowCount(0);
//
//        List<Cita> citas = criterio.isEmpty()
//                ? citaDAO.listarCitas()
//                : citaDAO.buscarCitasILike(criterio);
//
//        for (Cita c : citas) {
//            Object[] fila = {
//                c.getIdCita(),
//                obtenerNombrePaciente(c.getIdPaciente()),
//                obtenerNombreTratamiento(c.getIdTratamiento()),
//                c.getFechaHora()
//            };
//            modelo.addRow(fila);
//        }
//
//        if (!criterio.isEmpty() && modelo.getRowCount() == 0) {
//            JOptionPane.showMessageDialog(vista,
//                    "No se encontraron citas con ese criterio.");
//        }
//    }

    private void limpiarCampos() {
        vista.ComboBoxPacientes.setSelectedIndex(0);
        vista.ComboBoxTratamientos.setSelectedIndex(0);
        vista.jTextField3.setText("");

        if (vista.tblCitas.getSelectedRow() >= 0) {
            vista.tblCitas.clearSelection();
        }

        citaSeleccionada = null;
        deshabilitarBotonesEdicion();
    }

    private void deshabilitarBotonesEdicion() {
        vista.btnRegistrarCita.setEnabled(true);
        vista.btnModificarCita.setEnabled(false);
        vista.btnEliminarCita.setEnabled(false);
    }
}
