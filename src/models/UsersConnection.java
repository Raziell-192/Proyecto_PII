package models;

import controllers.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author jakim
 */
public class UsersConnection {

    Conexion cn = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    //Variables para enviar datos entre interfaces
    public static int idUsuario = 0;
    public static String nombresUsuario = "";
    public static String apellidosUsuario = "";
    public static String usernameUsuario = "";
    public static String direccionUsuario = "";
    public static String telefonoUsuario = "";
    public static String emailUsuario = "";
    public static String rolUsuario = "";

    //Método Login
    public User consultarUsuario(String user, String password) {
        String query = "SELECT * FROM usuarios WHERE username = ? AND password = ?";
        User usuario = new User();
        try {
            con = cn.conectar();
            ps = con.prepareStatement(query);

            //Enviar parámetros
            ps.setString(1, user);
            ps.setString(2, password);

            rs = ps.executeQuery();

            if (rs.next()) {
                usuario.setId(rs.getInt("id_usuario"));
                idUsuario = usuario.getId();
                usuario.setNombre(rs.getString("nombres"));
                nombresUsuario = usuario.getNombre();
                usuario.setApellido(rs.getString("apellidos"));
                apellidosUsuario = usuario.getApellido();
                usuario.setNombreDeUsuario(rs.getString("username"));
                usernameUsuario = usuario.getNombreDeUsuario();
                usuario.setEmail(rs.getString("email"));
                emailUsuario = usuario.getEmail();
                usuario.setRol(rs.getString("rol"));
                rolUsuario = usuario.getRol();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al obtener el usuario. " + e);
        }
        return usuario;
    }

    //Registrar usuario
    public boolean registrarUsuarioQuery(User usuario) {
        String query = "INSERT INTO usuarios (nombres, apellidos, username, email, password, rol) VALUES (?,?,?,?,?,?)";
        //Timestamp dateTime = new Timestamp(new Date().getTime());
        try {
            con = cn.conectar();
            ps = con.prepareStatement(query);
            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getApellido());
            ps.setString(3, usuario.getNombreDeUsuario());
            ps.setString(4, usuario.getEmail());
            ps.setString(5, usuario.getContrasenya());
            ps.setString(6, usuario.getRol());
//            ps.setTimestamp(9, dateTime);
//            ps.setTimestamp(10, dateTime);
            ps.execute();

            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al registrar usuario: " + e);
            return false;
        }
    }

    //Método listar usuario
//    public List listaUsuariosQuery(String valor) {
//        List<Employee> listaUsuarios = new ArrayList();
//        String query = "SELECT * FROM emplados ORDER BY rol ASC";
//        String queryBuscarUsuario = "SELECT * FROM usuarios WHERE id LIKE '%" + valor + "%'";
//        try {
//            con = cn.conectar();
//            if (valor.equalsIgnoreCase("")) {
//                ps = con.prepareStatement(query);
//                rs = ps.executeQuery();
//            } else {
//                ps = con.prepareStatement(queryBuscarUsuario);
//                rs = ps.executeQuery();
//            }
//            while (rs.next()) {
//                User usuario = new User();
//                usuario.setId(rs.getInt("id_usuario"));
//                usuario.setNombre(rs.getString("nombres"));
//                usuario.setApellido(rs.getString("apellidos"));
//                usuario.setNombreDeUsuario(rs.getString("username"));
//                usuario.setDireccion(rs.getString("direccion"));
//                usuario.setTelefono(rs.getString("telefono"));
//                usuario.setEmail(rs.getString("email"));
//                usuario.setRol(rs.getString("rol"));
//
//                listaUsuarios.add(usuario);
//
//            }
//        } catch (SQLException e) {
//            JOptionPane.showMessageDialog(null, e.toString());
//        }
//        return listaUsuarios;
//    }
    //Modificar usuario
    public boolean actualizarUsuario(User usuario) {
        String query = "UPDATE usuarios SET nombres = ?, apellidos = ?, username = ?, "
                + "email = ?, rol = ?, password = ? WHERE id_usuario = ?";
        try {
            con = cn.conectar();
            ps = con.prepareStatement(query);

            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getApellido());
            ps.setString(3, usuario.getNombreDeUsuario());
            ps.setString(4, usuario.getEmail());
            ps.setString(5, usuario.getContrasenya());
            ps.setString(6, usuario.getRol());
            ps.setInt(7, usuario.getId());

            ps.execute();

            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al modificar usuario: " + e);
            return false;
        }
    }

    //Eliminar usuario
    public boolean eliminarUsuarioQuery(int id) {
        String query = "DELETE FROM usuarios WHERE id_usuario = " + id;
        try {
            con = cn.conectar();
            ps = con.prepareStatement(query);
            ps.execute();

            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "No se puede eleimnar un usuario que tenga relacion con otra tabla " + e);
            return false;
        }
    }

    //Cambiar contraseña
    public boolean cambiarUsuarioContrasenya(User usuario) {
        String query = "UPDATE usuarios SET password = ? WHERE username = '" + usernameUsuario + "'";
        try {
            con = cn.conectar();
            ps = con.prepareStatement(query);
            ps.setString(1, usuario.getContrasenya());
            ps.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Ha ocurrido un error al intentar modificar la contraseña: " + e);
            return false;
        }
    }

    public List<User> obtenerTodosLosUsuarios() {
        List<User> listaUsuarios = new ArrayList<>();
        String query = "SELECT * FROM usuarios ORDER BY id_usuario ASC";
        try {
            con = cn.conectar();
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                User usuario = new User();
                usuario.setId(rs.getInt("id_usuario"));
                usuario.setNombre(rs.getString("nombres"));
                usuario.setApellido(rs.getString("apellidos"));
                usuario.setNombreDeUsuario(rs.getString("username"));
                usuario.setEmail(rs.getString("email"));
                usuario.setRol(rs.getString("rol"));
                listaUsuarios.add(usuario);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al obtener usuarios: " + e.toString());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return listaUsuarios;
    }

    public List<User> buscarUsuarios(String valor) {
        List<User> listaUsuarios = new ArrayList<>();
        String query = "SELECT * FROM usuarios WHERE id_usuario::text LIKE ? OR nombres LIKE ? OR apellidos LIKE ? ORDER BY id_usuario ASC";

        try {
            con = cn.conectar();
            ps = con.prepareStatement(query);
            String likeValor = "%" + valor + "%";
            ps.setString(1, likeValor);
            ps.setString(2, likeValor);
            ps.setString(3, likeValor);

            rs = ps.executeQuery();
            while (rs.next()) {
                User usuario = new User();
                usuario.setId(rs.getInt("id_usuario"));
                usuario.setNombre(rs.getString("nombres"));
                usuario.setApellido(rs.getString("apellidos"));
                usuario.setNombreDeUsuario(rs.getString("username"));
                usuario.setEmail(rs.getString("email"));
                usuario.setRol(rs.getString("rol"));

                listaUsuarios.add(usuario);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al buscar usuarios: " + e.toString());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return listaUsuarios;
    }

    public boolean eliminarUsuario(int id) {
        String query = "DELETE FROM usuarios WHERE id_usuario = ?";
        try {
            con = cn.conectar();
            ps = con.prepareStatement(query);
            ps.setInt(1, id);

            int resultado = ps.executeUpdate();
            return resultado > 0;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar usuario: " + e.toString());
            return false;
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
