package controllers;

import views.SystemViewResponsive;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import models.Venta;
import dao.VentasDAO;
import dao.PacienteDAO;
import dao.TipoPrecioDAO;
import models.Paciente;
import models.TipoPrecio;

/**
 * Controlador para el CRUD de Ventas
 * @author Raz
 */
public class VentasController implements ActionListener {

    private Venta venta;
    private VentasDAO ventaDAO;
    private PacienteDAO pacienteDAO;
    private TipoPrecioDAO tipoPrecioDAO;
    private SystemViewResponsive vista;
    private DefaultTableModel modelo;
    
    private Venta ventaSeleccionada;
    private List<Paciente> listaPacientes;
    private List<TipoPrecio> listaTiposPrecio;

    public VentasController(Venta venta, VentasDAO ventaDAO, PacienteDAO pacienteDAO, 
                           TipoPrecioDAO tipoPrecioDAO, SystemViewResponsive vista) {
        this.venta = venta;
        this.ventaDAO = ventaDAO;
        this.pacienteDAO = pacienteDAO;
        this.tipoPrecioDAO = tipoPrecioDAO;
        this.vista = vista;
        
        // Tabla de ventas
        this.modelo = (DefaultTableModel) vista.tblVentas.getModel();
        
        // Configurar listeners de botones
        this.vista.btnRegistrarVenta.addActionListener(this);
        this.vista.btnModificarVenta.addActionListener(this);
        this.vista.btnEliminarVenta.addActionListener(this);
        this.vista.btnCancelarVenta.addActionListener(this);
        
        configurarTabla();
        cargarVentas();
        deshabilitarBotonesEdicion();
        limpiarCampos();
        
        // Configurar fecha actual por defecto
        configurarFechaActual();
        
        // Generar número de venta automático al inicio
        generarNumeroVentaAutomatico();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.btnRegistrarVenta) {
            registrarVenta();
        } else if (e.getSource() == vista.btnModificarVenta) {
            modificarVenta();
        } else if (e.getSource() == vista.btnEliminarVenta) {
            eliminarVenta();
        } else if (e.getSource() == vista.btnCancelarVenta) {
            limpiarCampos();
        }
    }

    private void configurarTabla() {
        vista.tblVentas.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        
        vista.tblVentas.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                seleccionarVentaTabla();
            }
        });
    }
    
    private void configurarFechaActual() {
        try {
            LocalDateTime ahora = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            vista.jTextField53.setText(ahora.format(formatter));
        } catch (Exception e) {
            System.out.println("Error al configurar fecha: " + e.getMessage());
        }
    }

    private void cargarVentas() {
        modelo.setRowCount(0);
        List<Venta> ventas = ventaDAO.listarVentas();
        
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        
        for (Venta v : ventas) {
            String fechaFormateada = "";
            if (v.getFechaVenta() != null) {
                fechaFormateada = v.getFechaVenta().toLocalDateTime().format(dateFormatter);
            }
            
            Object[] fila = {
                v.getIdVenta(),
                v.getNumeroVenta(),
                v.getIdPaciente(),
                v.getIdTipoPrecio(),
                fechaFormateada,
                String.format("%.2f", v.getTotal())
            };
            modelo.addRow(fila);
        }
    }

    private void seleccionarVentaTabla() {
        int filaSeleccionada = vista.tblVentas.getSelectedRow();
        if (filaSeleccionada >= 0) {
            int idVenta = (int) modelo.getValueAt(filaSeleccionada, 0);
            ventaSeleccionada = ventaDAO.obtenerVentaPorId(idVenta);
            
            if (ventaSeleccionada != null) {
                vista.jTextField49.setText(String.valueOf(ventaSeleccionada.getIdVenta()));
                vista.jTextField51.setText(ventaSeleccionada.getNumeroVenta());
                vista.jTextField52.setText(String.valueOf(ventaSeleccionada.getIdPaciente()));
                vista.jTextField55.setText(String.valueOf(ventaSeleccionada.getIdTipoPrecio()));
                
                // Formatear fecha para mostrar
                if (ventaSeleccionada.getFechaVenta() != null) {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    vista.jTextField53.setText(ventaSeleccionada.getFechaVenta().toLocalDateTime().format(formatter));
                }
                
                vista.jTextField54.setText(String.format("%.2f", ventaSeleccionada.getTotal()));
                
                vista.btnRegistrarVenta.setEnabled(false);
                vista.btnModificarVenta.setEnabled(true);
                vista.btnEliminarVenta.setEnabled(true);
            }
        }
    }

    private void registrarVenta() {
        if (validarCampos()) {
            // Verificar si el número de venta ya existe
            if (ventaDAO.verificarNumeroVentaExistente(vista.jTextField51.getText().trim(), 0)) {
                JOptionPane.showMessageDialog(vista, 
                    "El número de venta ya existe. Por favor, use otro número.",
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Verificar que el paciente existe
            int idPaciente = Integer.parseInt(vista.jTextField52.getText().trim());
            Paciente paciente = pacienteDAO.obtenerPacientePorId(idPaciente);
            if (paciente == null) {
                JOptionPane.showMessageDialog(vista, 
                    "El paciente con ID " + idPaciente + " no existe.",
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            Venta nuevaVenta = new Venta();
            nuevaVenta.setNumeroVenta(vista.jTextField51.getText().trim());
            
            try {
                nuevaVenta.setIdPaciente(idPaciente);
                nuevaVenta.setIdTipoPrecio(Integer.parseInt(vista.jTextField55.getText().trim()));
                nuevaVenta.setTotal(Double.parseDouble(vista.jTextField54.getText().trim()));
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(vista, 
                    "Error en los campos numéricos: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Configurar fecha
            try {
                String fechaTexto = vista.jTextField53.getText().trim();
                if (!fechaTexto.isEmpty()) {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    LocalDateTime fecha = LocalDateTime.parse(fechaTexto, formatter);
                    nuevaVenta.setFechaVenta(Timestamp.valueOf(fecha));
                } else {
                    nuevaVenta.setFechaVenta(Timestamp.valueOf(LocalDateTime.now()));
                }
            } catch (DateTimeParseException e) {
                JOptionPane.showMessageDialog(vista,
                    "Formato de fecha inválido. Use: yyyy-MM-dd HH:mm:ss",
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (ventaDAO.registrarVenta(nuevaVenta)) {
                JOptionPane.showMessageDialog(vista, "Venta registrada exitosamente.");
                limpiarCampos();
                cargarVentas();
            } else {
                JOptionPane.showMessageDialog(vista, "Error al registrar venta.");
            }
        }
    }

    private void modificarVenta() {
        if (ventaSeleccionada == null) {
            JOptionPane.showMessageDialog(vista, "Seleccione una venta de la tabla para modificar.");
            return;
        }
        
        if (validarCampos()) {
            // Verificar si el número de venta ya existe (excluyendo la venta actual)
            if (ventaDAO.verificarNumeroVentaExistente(vista.jTextField51.getText().trim(), ventaSeleccionada.getIdVenta())) {
                JOptionPane.showMessageDialog(vista, 
                    "El número de venta ya existe. Por favor, use otro número.",
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Verificar que el paciente existe
            int idPaciente = Integer.parseInt(vista.jTextField52.getText().trim());
            Paciente paciente = pacienteDAO.obtenerPacientePorId(idPaciente);
            if (paciente == null) {
                JOptionPane.showMessageDialog(vista, 
                    "El paciente con ID " + idPaciente + " no existe.",
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            ventaSeleccionada.setNumeroVenta(vista.jTextField51.getText().trim());
            
            try {
                ventaSeleccionada.setIdPaciente(idPaciente);
                ventaSeleccionada.setIdTipoPrecio(Integer.parseInt(vista.jTextField55.getText().trim()));
                ventaSeleccionada.setTotal(Double.parseDouble(vista.jTextField54.getText().trim()));
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(vista, 
                    "Error en los campos numéricos: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Configurar fecha
            try {
                String fechaTexto = vista.jTextField53.getText().trim();
                if (!fechaTexto.isEmpty()) {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    LocalDateTime fecha = LocalDateTime.parse(fechaTexto, formatter);
                    ventaSeleccionada.setFechaVenta(Timestamp.valueOf(fecha));
                }
            } catch (DateTimeParseException e) {
                JOptionPane.showMessageDialog(vista,
                    "Formato de fecha inválido. Use: yyyy-MM-dd HH:mm:ss",
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (ventaDAO.actualizarVenta(ventaSeleccionada)) {
                JOptionPane.showMessageDialog(vista, "Venta actualizada exitosamente.");
                limpiarCampos();
                cargarVentas();
            } else {
                JOptionPane.showMessageDialog(vista, "Error al actualizar venta.");
            }
        }
    }

    private boolean validarCampos() {
        // Validar número de venta
        String numeroVenta = vista.jTextField51.getText().trim();
        if (numeroVenta.isEmpty()) {
            JOptionPane.showMessageDialog(vista,
                "El campo Número de Venta es obligatorio.",
                "Error", JOptionPane.ERROR_MESSAGE);
            vista.jTextField51.requestFocus();
            return false;
        }
        
        if (numeroVenta.length() > 50) {
            JOptionPane.showMessageDialog(vista,
                "El Número de Venta no puede exceder 50 caracteres.",
                "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        // Validar ID paciente
        String idPacienteStr = vista.jTextField52.getText().trim();
        if (idPacienteStr.isEmpty()) {
            JOptionPane.showMessageDialog(vista,
                "El campo ID Paciente es obligatorio.",
                "Error", JOptionPane.ERROR_MESSAGE);
            vista.jTextField52.requestFocus();
            return false;
        }
        
        try {
            int idPaciente = Integer.parseInt(idPacienteStr);
            if (idPaciente <= 0) {
                JOptionPane.showMessageDialog(vista,
                    "ID Paciente debe ser un número positivo.",
                    "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(vista,
                "ID Paciente debe ser un número válido.",
                "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        // Validar ID tipo precio
        String idTipoPrecioStr = vista.jTextField55.getText().trim();
        if (idTipoPrecioStr.isEmpty()) {
            JOptionPane.showMessageDialog(vista,
                "El campo ID Tipo Precio es obligatorio.",
                "Error", JOptionPane.ERROR_MESSAGE);
            vista.jTextField55.requestFocus();
            return false;
        }
        
        try {
            int idTipoPrecio = Integer.parseInt(idTipoPrecioStr);
            if (idTipoPrecio <= 0) {
                JOptionPane.showMessageDialog(vista,
                    "ID Tipo Precio debe ser un número positivo.",
                    "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(vista,
                "ID Tipo Precio debe ser un número válido.",
                "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        // Validar total
        String totalStr = vista.jTextField54.getText().trim();
        if (totalStr.isEmpty()) {
            JOptionPane.showMessageDialog(vista,
                "El campo Total es obligatorio.",
                "Error", JOptionPane.ERROR_MESSAGE);
            vista.jTextField54.requestFocus();
            return false;
        }
        
        try {
            double total = Double.parseDouble(totalStr);
            if (total < 0) {
                JOptionPane.showMessageDialog(vista,
                    "Total debe ser un valor positivo o cero.",
                    "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            
            // Validar formato decimal
            if (totalStr.contains(".")) {
                String[] partes = totalStr.split("\\.");
                if (partes.length > 1 && partes[1].length() > 2) {
                    JOptionPane.showMessageDialog(vista,
                        "Total no puede tener más de 2 decimales.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(vista,
                "Total debe ser un número válido.",
                "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        // Validar fecha (opcional)
        String fechaTexto = vista.jTextField53.getText().trim();
        if (!fechaTexto.isEmpty()) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime.parse(fechaTexto, formatter);
            } catch (DateTimeParseException e) {
                JOptionPane.showMessageDialog(vista,
                    "Formato de fecha inválido. Use: yyyy-MM-dd HH:mm:ss",
                    "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        
        return true;
    }

    private void eliminarVenta() {
        if (ventaSeleccionada == null) {
            JOptionPane.showMessageDialog(vista, "Seleccione una venta de la tabla para eliminar.");
            return;
        }
        
        int confirmacion = JOptionPane.showConfirmDialog(vista,
            "¿Está seguro de eliminar la venta: " + ventaSeleccionada.getNumeroVenta() + "?\n" +
            "Esta acción también eliminará todos los detalles de tratamiento e insumos asociados.",
            "Confirmar eliminación",
            JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            if (ventaDAO.eliminarVenta(ventaSeleccionada.getIdVenta())) {
                JOptionPane.showMessageDialog(vista, 
                    "Venta y sus detalles eliminados exitosamente.",
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
                limpiarCampos();
                cargarVentas();
            } else {
                JOptionPane.showMessageDialog(vista, 
                    "Error al eliminar venta.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void limpiarCampos() {
        vista.jTextField49.setText("");
        vista.jTextField51.setText("");
        vista.jTextField52.setText("");
        vista.jTextField55.setText("");
        vista.jTextField54.setText("0.00");
        configurarFechaActual();
        
        // Generar número de venta automático
        generarNumeroVentaAutomatico();
        
        if (vista.tblVentas.getSelectedRow() >= 0) {
            vista.tblVentas.clearSelection();
        }
        
        ventaSeleccionada = null;
        deshabilitarBotonesEdicion();
    }

    private void deshabilitarBotonesEdicion() {
        vista.btnRegistrarVenta.setEnabled(true);
        vista.btnModificarVenta.setEnabled(false);
        vista.btnEliminarVenta.setEnabled(false);
    }
    
    private void generarNumeroVentaAutomatico() {
        String numeroVenta = ventaDAO.generarNumeroVenta();
        vista.jTextField51.setText(numeroVenta);
    }
    
    public void buscarVentas() {
        String criterio = JOptionPane.showInputDialog(vista, 
            "Ingrese número de venta, ID de paciente o nombre para buscar:");
        
        if (criterio != null && !criterio.trim().isEmpty()) {
            modelo.setRowCount(0);
            List<Venta> ventas = ventaDAO.buscarVentas(criterio.trim());
            
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            
            for (Venta v : ventas) {
                String fechaFormateada = "";
                if (v.getFechaVenta() != null) {
                    fechaFormateada = v.getFechaVenta().toLocalDateTime().format(dateFormatter);
                }
                
                Object[] fila = {
                    v.getIdVenta(),
                    v.getNumeroVenta(),
                    v.getIdPaciente(),
                    v.getIdTipoPrecio(),
                    fechaFormateada,
                    String.format("%.2f", v.getTotal())
                };
                modelo.addRow(fila);
            }
            
            if (ventas.isEmpty()) {
                JOptionPane.showMessageDialog(vista, 
                    "No se encontraron ventas con ese criterio.",
                    "Información", JOptionPane.INFORMATION_MESSAGE);
            }
            
            limpiarSeleccion();
        }
    }
    
    private void limpiarSeleccion() {
        if (vista.tblVentas.getSelectedRow() >= 0) {
            vista.tblVentas.clearSelection();
        }
        
        if (ventaSeleccionada == null) {
            vista.jTextField49.setText("");
            vista.jTextField51.setText("");
            vista.jTextField52.setText("");
            vista.jTextField55.setText("");
            vista.jTextField54.setText("0.00");
            configurarFechaActual();
        }
        
        vista.btnRegistrarVenta.setEnabled(true);
        vista.btnModificarVenta.setEnabled(false);
        vista.btnEliminarVenta.setEnabled(false);
    }
    
}