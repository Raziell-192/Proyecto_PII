package controllers;

import Views.SystemView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import models.Employee;
import models.EmployeesConnection;
import static models.EmployeesConnection.rolUsuario;

/**
 *
 * @author jakim
 */
public class EmployeesController implements ActionListener{
    private Employee empleado;
    private EmployeesConnection empleadoConexion;
    private SystemView vista;
    String rol = rolUsuario;

    public EmployeesController(Employee empleado, EmployeesConnection empleadoConexion, SystemView vista) {
        this.empleado = empleado;
        this.empleadoConexion = empleadoConexion;
        this.vista = vista;
        
        // Agregar listeners
        this.vista.btnRegistrarEmpleado.addActionListener(this);
        this.vista.jButton3.addActionListener(this); // Buscar
        this.vista.jButton4.addActionListener(this); // Editar
        this.vista.jButton5.addActionListener(this); // Eliminar
        
        // Cargar datos iniciales
        cargarEmpleadosEnTabla();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == vista.btnRegistrarEmpleado){
            registrarEmpleado();
        } else if(e.getSource() == vista.jButton3) {
            buscarEmpleados();
        } else if(e.getSource() == vista.jButton4) {
            editarEmpleado();
        } else if(e.getSource() == vista.jButton5) {
            eliminarEmpleado();
        }
    }
    
    private void registrarEmpleado() {
        if(validarCampos()) {
            empleado.setId(Integer.parseInt(vista.txtIdEmpleado.getText().trim()));
            empleado.setNombre(vista.txtNombreEmpleado.getText().trim());
            empleado.setApellido(vista.txtApellidoEmpleado.getText().trim());
            empleado.setNombreDeUsuario(vista.txtUsernameEmpleado.getText().trim());
            empleado.setDireccion(vista.txtDireccionEmpleado.getText().trim());
            empleado.setTelefono(vista.txtTelefonoEmpleado.getText().trim());
            empleado.setEmail(vista.txtEmailEmpleado.getText().trim());
            empleado.setContrasenya(String.valueOf(vista.passwordEmpleado.getPassword()));
            empleado.setRol(vista.cmbRolEmpleado.getSelectedItem().toString());
            
            if(empleadoConexion.registrarEmpleadoQuery(empleado)){
                JOptionPane.showMessageDialog(null, "Empleado registrado con éxito.");
                limpiarCampos();
                cargarEmpleadosEnTabla();
            } else {
                JOptionPane.showMessageDialog(null, "Ocurrió un error al registrar empleado.");
            }
        }
    }
    
    private void buscarEmpleados() {
        String textoBusqueda = JOptionPane.showInputDialog("Ingrese ID, nombre o apellido a buscar:");
        if (textoBusqueda != null && !textoBusqueda.trim().isEmpty()) {
            List<Employee> empleados = empleadoConexion.buscarEmpleados(textoBusqueda.trim());
            cargarEmpleadosEnTabla(empleados);
        }
    }
    
    private void editarEmpleado() {
        int filaSeleccionada = vista.jTable1.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(null, "Por favor, seleccione un empleado de la tabla.");
            return;
        }
        
        int idEmpleado = (int) vista.jTable1.getValueAt(filaSeleccionada, 0);
        Employee empleadoEditar = obtenerEmpleadoPorId(idEmpleado);
        
        if (empleadoEditar.getId() != 0) {
            // Llenar los campos con los datos del empleado
            vista.txtIdEmpleado.setText(String.valueOf(empleadoEditar.getId()));
            vista.txtNombreEmpleado.setText(empleadoEditar.getNombre());
            vista.txtApellidoEmpleado.setText(empleadoEditar.getApellido());
            vista.txtUsernameEmpleado.setText(empleadoEditar.getNombreDeUsuario());
            vista.txtDireccionEmpleado.setText(empleadoEditar.getDireccion());
            vista.txtTelefonoEmpleado.setText(empleadoEditar.getTelefono());
            vista.txtEmailEmpleado.setText(empleadoEditar.getEmail());
            vista.cmbRolEmpleado.setSelectedItem(empleadoEditar.getRol());
            vista.passwordEmpleado.setText(empleadoEditar.getContrasenya());
            
            JOptionPane.showMessageDialog(null, "Datos cargados para editar. Modifique y haga clic en Registrar para actualizar.");
        } else {
            JOptionPane.showMessageDialog(null, "No se pudo cargar los datos del empleado.");
        }
    }
    
    private void eliminarEmpleado() {
        int filaSeleccionada = vista.jTable1.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(null, "Por favor, seleccione un empleado de la tabla.");
            return;
        }
        
        int idEmpleado = (int) vista.jTable1.getValueAt(filaSeleccionada, 0);
        String nombreEmpleado = (String) vista.jTable1.getValueAt(filaSeleccionada, 1);
        
        int confirmacion = JOptionPane.showConfirmDialog(null, 
            "¿Está seguro de eliminar al empleado: " + nombreEmpleado + "?", 
            "Confirmar eliminación", 
            JOptionPane.YES_NO_OPTION);
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            if (empleadoConexion.eliminarEmpleado(idEmpleado)) {
                JOptionPane.showMessageDialog(null, "Empleado eliminado con éxito.");
                cargarEmpleadosEnTabla();
            } else {
                JOptionPane.showMessageDialog(null, "Error al eliminar empleado.");
            }
        }
    }
    
    private void cargarEmpleadosEnTabla() {
        List<Employee> empleados = empleadoConexion.obtenerTodosLosEmpleados();
        cargarEmpleadosEnTabla(empleados);
    }
    
    private void cargarEmpleadosEnTabla(List<Employee> empleados) {
        DefaultTableModel modelo = (DefaultTableModel) vista.jTable1.getModel();
        modelo.setRowCount(0); // Limpiar tabla
        
        for (Employee emp : empleados) {
            Object[] fila = {
                emp.getId(),
                emp.getNombre(),
                emp.getApellido(),
                emp.getNombreDeUsuario(),
                emp.getDireccion(),
                emp.getTelefono(),
                emp.getEmail(),
                emp.getRol()
            };
            modelo.addRow(fila);
        }
    }
    
    private boolean validarCampos() {
        if(vista.txtIdEmpleado.getText().trim().isEmpty()
                || vista.txtNombreEmpleado.getText().trim().isEmpty()
                || vista.txtApellidoEmpleado.getText().trim().isEmpty()
                || vista.txtUsernameEmpleado.getText().trim().isEmpty()
                || vista.txtDireccionEmpleado.getText().trim().isEmpty()
                || vista.txtTelefonoEmpleado.getText().trim().isEmpty()
                || vista.txtEmailEmpleado.getText().trim().isEmpty()
                || vista.cmbRolEmpleado.getSelectedItem().toString().isEmpty()
                || String.valueOf(vista.passwordEmpleado.getPassword()).isEmpty()){
        
            JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios.");
            return false;
        }
        
        // Validar que el ID sea numérico
        try {
            Integer.parseInt(vista.txtIdEmpleado.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "El ID debe ser un número válido.");
            return false;
        }
        
        return true;
    }
    
    public Employee obtenerEmpleadoPorId(int id) {
        String query = "SELECT * FROM empleados WHERE \"idEmpleado\" = ?";
        Employee empleado = new Employee();
        
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            Conexion conexion = new Conexion();
            con = conexion.conectar();
            
            ps = con.prepareStatement(query);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                empleado.setId(rs.getInt("\"idEmpleado\""));
                empleado.setNombre(rs.getString("nombre"));
                empleado.setApellido(rs.getString("apellido"));
                empleado.setNombreDeUsuario(rs.getString("username"));
                empleado.setDireccion(rs.getString("direccion"));
                empleado.setTelefono(rs.getString("telefono"));
                empleado.setEmail(rs.getString("email"));
                empleado.setRol(rs.getString("rol"));
                empleado.setContrasenya(rs.getString("contrasenya"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al obtener empleado: " + e.toString());
        } finally {
            // Cerrar recursos
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return empleado;
    }
    
    private void limpiarCampos() {
        vista.txtIdEmpleado.setText("");
        vista.txtNombreEmpleado.setText("");
        vista.txtApellidoEmpleado.setText("");
        vista.txtUsernameEmpleado.setText("");
        vista.txtDireccionEmpleado.setText("");
        vista.txtTelefonoEmpleado.setText("");
        vista.txtEmailEmpleado.setText("");
        vista.passwordEmpleado.setText("");
        vista.cmbRolEmpleado.setSelectedIndex(0);
    }
}