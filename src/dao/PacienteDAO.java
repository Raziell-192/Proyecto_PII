package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import models.Paciente;

/**
 *
 * @author Raz
 */
public class PacienteDAO {
    
    Conexion cn = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    
    public static int idPaciente=0;
    public static String nombres="";
    public static String apellidos="";
    public static String telefono="";
    public static String tipoAfiliacion="";
    
    public boolean registrarPacienteQuery(Paciente paciente) {
        String query = "INSERT INTO pacientes (nombres, apellidos, telefono, tipo_afiliacion) VALUES (?, ?, ?, ?)";
        
        try {
            con = cn.conectar();
            ps = con.prepareStatement(query);
            ps.setString(1, paciente.getNombres());
            ps.setString(2, paciente.getApellidos());
            ps.setString(3, paciente.getTelefono());
            ps.setString(4, paciente.getTipoAfiliacion());
            
            ps.execute();
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al registrar paciente: " + ex);
            return false;
        } finally {
            cerrarRecursos();
        }
    }
    
    public Paciente obtenerPacientePorId(int idPaciente) {
        String query = "SELECT * FROM pacientes WHERE id_paciente = ?";
        Paciente paciente = null;
        
        try {
            con = cn.conectar();
            ps = con.prepareStatement(query);
            ps.setInt(1, idPaciente);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                paciente = new Paciente();
                paciente.setIdPaciente(rs.getInt("id_paciente"));
                paciente.setNombres(rs.getString("nombres"));
                paciente.setApellidos(rs.getString("apellidos"));
                paciente.setTelefono(rs.getString("telefono"));
                paciente.setTipoAfiliacion(rs.getString("tipo_afiliacion"));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al obtener paciente: " + ex);
        } finally {
            cerrarRecursos();
        }
        return paciente;
    }
    
    public List<Paciente> obtenerTodosLosPacientes() {
        List<Paciente> listaPacientes = new ArrayList<>();
        String query = "SELECT * FROM pacientes ORDER BY apellidos, nombres";
        
        try {
            con = cn.conectar();
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                Paciente paciente = new Paciente();
                paciente.setIdPaciente(rs.getInt("id_paciente"));
                paciente.setNombres(rs.getString("nombres"));
                paciente.setApellidos(rs.getString("apellidos"));
                paciente.setTelefono(rs.getString("telefono"));
                paciente.setTipoAfiliacion(rs.getString("tipo_afiliacion"));
                
                listaPacientes.add(paciente);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al obtener pacientes: " + ex);
        } finally {
            cerrarRecursos();
        }
        return listaPacientes;
    }
    
    public List<Paciente> buscarPacientes(String criterio) {
        List<Paciente> listaPacientes = new ArrayList<>();
        String query = "SELECT * FROM pacientes WHERE nombres ILIKE ? OR apellidos ILIKE ? OR telefono ILIKE ? ORDER BY apellidos, nombres";
        
        try {
            con = cn.conectar();
            ps = con.prepareStatement(query);
            String likeCriterio = "%" + criterio + "%";
            ps.setString(1, likeCriterio);
            ps.setString(2, likeCriterio);
            ps.setString(3, likeCriterio);
            
            rs = ps.executeQuery();
            
            while (rs.next()) {
                Paciente paciente = new Paciente();
                paciente.setIdPaciente(rs.getInt("id_paciente"));
                paciente.setNombres(rs.getString("nombres"));
                paciente.setApellidos(rs.getString("apellidos"));
                paciente.setTelefono(rs.getString("telefono"));
                paciente.setTipoAfiliacion(rs.getString("tipo_afiliacion"));
                
                listaPacientes.add(paciente);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al buscar pacientes: " + ex);
        } finally {
            cerrarRecursos();
        }
        return listaPacientes;
    }
    
    public boolean actualizarPacienteQuery(Paciente paciente) {
        String query = "UPDATE pacientes SET nombres = ?, apellidos = ?, telefono = ?, tipo_afiliacion = ? WHERE id_paciente = ?";
        
        try {
            con = cn.conectar();
            ps = con.prepareStatement(query);
            ps.setString(1, paciente.getNombres());
            ps.setString(2, paciente.getApellidos());
            ps.setString(3, paciente.getTelefono());
            ps.setString(4, paciente.getTipoAfiliacion());
            ps.setInt(5, paciente.getIdPaciente());
            
            int resultado = ps.executeUpdate();
            return resultado > 0;
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al actualizar paciente: " + ex);
            return false;
        } finally {
            cerrarRecursos();
        }
    }
    
    public boolean eliminarPacienteQuery(int idPaciente) {
        String query = "DELETE FROM pacientes WHERE id_paciente = ?";
        
        try {
            con = cn.conectar();
            ps = con.prepareStatement(query);
            ps.setInt(1, idPaciente);
            
            int resultado = ps.executeUpdate();
            return resultado > 0;
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al eliminar paciente: " + ex);
            return false;
        } finally {
            cerrarRecursos();
        }
    }
    
    private void cerrarRecursos() {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (con != null) con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}