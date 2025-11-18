package models;

import controllers.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
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
    public static String nombreUsuario = "";
    public static String apellidoUsuario = "";
    public static String usernameUsuario = "";
    public static String direccionUsuario = "";
    public static String telefonoUsuario = "";
    public static String emailUsuario = "";
    public static String rolUsuario = "";
    
    //Método Login
    public Employee consultarEmpleado(String usuario, String contrasenya){
        String query = "SELECT * FROM empleados WHERE username = ? AND contrasenya = ?";
        Employee empleado = new Employee();
        try{
            con = cn.conectar();
            ps = con.prepareStatement(query);

            //Enviar parámetros
            ps.setString(1, usuario);
            ps.setString(2, contrasenya);
            
            rs = ps.executeQuery();
            
            if(rs.next()){
                empleado.setId(rs.getInt("\"idEmpleado\""));
                idUsuario = empleado.getId();
                empleado.setNombre(rs.getString("nombre"));
                nombreUsuario = empleado.getNombre();
                empleado.setApellido(rs.getString("apellido"));
                apellidoUsuario = empleado.getApellido();
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
    
    //Registrar empleado
    public boolean registrarEmpleadoQuery(Employee empleado) {
        String query = "INSERT INTO empleados (\"idEmpleado\", nombre, apellido, username, direccion, telefono, email, contrasenya, rol) VALUES (?,?,?,?,?,?,?,?,?)";
        //Timestamp dateTime = new Timestamp(new Date().getTime());
        try {
            con = cn.conectar();
            ps = con.prepareStatement(query);
            ps.setInt(1, empleado.getId());
            ps.setString(2, empleado.getNombre());
            ps.setString(3, empleado.getApellido());
            ps.setString(4, empleado.getNombreDeUsuario());
            ps.setString(5, empleado.getDireccion());
            ps.setString(6, empleado.getTelefono());
            ps.setString(7, empleado.getEmail());
            ps.setString(8, empleado.getContrasenya());
            ps.setString(9, empleado.getRol());

//            ps.setTimestamp(9, dateTime);
//            ps.setTimestamp(10, dateTime);
            ps.execute();

            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al registrar empleado: " + e);
            return false;
        }
    }

    //Método listar empleado
//    public List listaEmpleadosQuery(String valor) {
//        List<Employee> listaEmpleados = new ArrayList();
//        String query = "SELECT * FROM emplados ORDER BY rol ASC";
//        String queryBuscarEmpleado = "SELECT * FROM empleados WHERE id LIKE '%" + valor + "%'";
//        try {
//            con = cn.conectar();
//            if (valor.equalsIgnoreCase("")) {
//                ps = con.prepareStatement(query);
//                rs = ps.executeQuery();
//            } else {
//                ps = con.prepareStatement(queryBuscarEmpleado);
//                rs = ps.executeQuery();
//            }
//            while (rs.next()) {
//                Employee empleado = new Employee();
//                empleado.setId(rs.getInt("\"idEmpleado\""));
//                empleado.setNombre(rs.getString("nombre"));
//                empleado.setApellido(rs.getString("apellido"));
//                empleado.setNombreDeUsuario(rs.getString("username"));
//                empleado.setDireccion(rs.getString("direccion"));
//                empleado.setTelefono(rs.getString("telefono"));
//                empleado.setEmail(rs.getString("email"));
//                empleado.setRol(rs.getString("rol"));
//
//                listaEmpleados.add(empleado);
//
//            }
//        } catch (SQLException e) {
//            JOptionPane.showMessageDialog(null, e.toString());
//        }
//        return listaEmpleados;
//    }

    //Modificar empleado
    public boolean actualizarEmpleadoQuery(Employee empleado) {
        String query = "UPDATE empleados SET nombre = ?, apellido = ?, username = ?, "
                + "direccion = ?, telefono= ?, email = ?, rol = ? WHERE \"idEmpleado\" = ?";
        try {
            con = cn.conectar();
            ps = con.prepareStatement(query);

            ps.setString(1, empleado.getNombre());
            ps.setString(2, empleado.getApellido());
            ps.setString(3, empleado.getNombreDeUsuario());
            ps.setString(4, empleado.getDireccion());
            ps.setString(5, empleado.getTelefono());
            ps.setString(6, empleado.getEmail());
            ps.setString(7, empleado.getContrasenya());
            ps.setString(8, empleado.getRol());
            ps.setInt(9, empleado.getId());

            ps.execute();

            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al modificar empleado: " + e);
            return false;
        }
    }

    //Eliminar empleado
    public boolean eliminarEmpleadoQuery(int id) {
        String query = "DELETE FROM empleados WHERE idEmpleado = " + id;
        try {
            con = cn.conectar();
            ps = con.prepareStatement(query);
            ps.execute();

            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "No se puede eleimnar un empleado que tenga relacion con otra tabla " + e);
            return false;
        }
    }

    //Cambiar contraseña
    public boolean cambiarEmpleadoContrasenya(Employee empleado) {
        String query = "UPDATE empleados SET contrasenya = ? WHERE username = '" + usernameUsuario + "'";
        try{
            con = cn.conectar();
            ps = con.prepareStatement(query);
            ps.setString(1, empleado.getContrasenya());
            ps.execute();
            return true;
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error al intentar modificar la contraseña: " + e);
            return false;
        }
    }

}