package models;

import controllers.Conexion;
import java.sql.*;
import javax.swing.JOptionPane;

/**
 *
 * @author jakim
 */
public class EmployeesConnection {
    Conexion cn = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    
    //Variables para enviar datos entre interfaces
    public static int idUsuario = 0;
    public static String nombreCompletoUsuario = "";
    public static String usernameUsuario = "";
    public static String direccionUsuario = "";
    public static String telefonoUsuario = "";
    public static String emailUsuario = "";
    public static String rolUsuario = "";
    
    //Método Login
    public Employee ingresarConsulta(String usuario, String contrasenya){
        String query = "SELECT * FROM empleados WHERE username = ? AND password = ?";
        Employee empleado = new Employee();
        try{
            con = cn.conectar();
            ps = con.prepareStatement(query);
            
            //Enviar parámetros
            ps.setString(1, usuario);
            ps.setString(2, contrasenya);
            
            rs = ps.executeQuery();
            
            if(rs.next()){
                empleado.setId(rs.getInt("id"));
                idUsuario = empleado.getId();
                empleado.setNombreCompleto(rs.getString("nombreCompleto"));
                nombreCompletoUsuario = empleado.getNombreCompleto();
                empleado.setNombreDeUsuario(rs.getString("username"));
                usernameUsuario = empleado.getNombreDeUsuario();
                empleado.setDireccion(rs.getString("direccion"));
                direccionUsuario = empleado.getDireccion();
                empleado.setTelefono(rs.getString("telefono"));
                telefonoUsuario = empleado.getTelefono();
                empleado.setEmail(rs.getString("email"));
                emailUsuario = empleado.getEmail();
                empleado.setRol(rs.getString("rol"));
                rolUsuario = empleado.getRol();
            }
        }
        catch(Exception e) {
            JOptionPane.showMessageDialog(null, "Error al obtner el empleado. "+e);
        }
        return empleado;
    }
    
}