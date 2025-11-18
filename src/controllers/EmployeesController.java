package controllers;

import Views.SystemView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
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

//    public EmployeesController(Employee empleado, EmployeesConnection empleadoConexion, SystemView vista) {
//        this.empleado = empleado;
//        this.empleadoConexion = empleadoConexion;
//        this.vista = vista;
//        this.vista.btnRegistrarEmpleado.addActionListener(this);
//    }
//
//    @Override
//    public void actionPerformed(ActionEvent e) {
//        if(e.getSource() == vista.btnRegistrarEmpleado){
//            if(vista.txtIdEmpleado.getText().equals("")
//                    || vista.txtNombreEmpleado.getText().equals("")
//                    || vista.txtUsernameEmpleado.getText().equals("")
//                    || vista.txtDireccionEmpleado.getText().equals("")
//                    || vista.txtTelefonoEmpleado.getText().equals("")
//                    || vista.txtEmailEmpleado.getText().equals("")
//                    || vista.txtRolEmpleado.getSelectedItem().toString().equals("")
//                    || String.valueOf(vista.passwordEmpleado.getPassword()).equals(""))){
//            
//                JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios.");
//            }else{
//                    empleado.setId(Integer.parseInt(vista.txtIdEmpleado.getText().trim()));
//                    empleado.setNombreCompleto(vista.txtNombreEmpleado.getText().trim());
//                    /**/
//                    empleado.setContrasenya(String.valueOf(vista.passwordEmpleado.getPassword()));
//                    empleado.setRol(vista.cmbRol.getSelectedItem().toString());
//                    }
//            if(empleadoConexion.registrarEmpleadoQuery(empleado)){
//                JOptionPane.showMessageDialog(null, "Empleado registrado con éxito.");
//            }else{
//                JOptionPane.showMessageDialog(null, "Ocurrió un error al registrar empleado.");
//            }
//        }
//    }

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    
}
