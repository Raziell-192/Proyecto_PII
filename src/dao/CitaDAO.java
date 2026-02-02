package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import models.Cita;

/**
 * DAO encargado de gestionar las citas de los pacientes.
 *
 * @author Jakim
 */
public class CitaDAO {

    Conexion cn = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    //MÉTODO PARA CERRAR RECURSOS(Me estaba generando problemas hasta que lo busque)
    private void cerrarRecursos() {
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

    public boolean registrarCita(Cita cita) {
        String query = "INSERT INTO citas (id_paciente, id_tratamiento, fecha_hora) VALUES (?, ?, ?)";

        try {
            con = cn.conectar();
            ps = con.prepareStatement(query);
            ps.setInt(1, cita.getIdPaciente());
            ps.setInt(2, cita.getIdTratamiento());
            ps.setTimestamp(3, cita.getFechaHora());
            ps.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al registrar cita: " + e.getMessage());
            return false;
        } finally {
            cerrarRecursos();
        }
    }

    public List<Cita> listarCitas() {
        List<Cita> lista = new ArrayList<>();
        String query = "SELECT * FROM citas ORDER BY fecha_hora";

        try {
            con = cn.conectar();
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
                Cita cita = new Cita();
                cita.setIdCita(rs.getInt("id_cita"));
                cita.setIdPaciente(rs.getInt("id_paciente"));
                cita.setIdTratamiento(rs.getInt("id_tratamiento"));
                cita.setFechaHora(rs.getTimestamp("fecha_hora"));
                lista.add(cita);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al listar citas: " + e.getMessage());
        } finally {
            cerrarRecursos();
        }
        return lista;
    }

    public boolean actualizarCita(Cita cita) {
        String query = "UPDATE citas SET id_paciente = ?, id_tratamiento = ?, fecha_hora = ? WHERE id_cita = ?";

        try {
            con = cn.conectar();
            ps = con.prepareStatement(query);
            ps.setInt(1, cita.getIdPaciente());
            ps.setInt(2, cita.getIdTratamiento());
            ps.setTimestamp(3, cita.getFechaHora());
            ps.setInt(4, cita.getIdCita());

            int resultado = ps.executeUpdate();
            return resultado > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar cita: " + e.getMessage());
            return false;
        } finally {
            cerrarRecursos();
        }
    }

    public boolean eliminarCita(int idCita) {
        String query = "DELETE FROM citas WHERE id_cita = ?";

        try {
            con = cn.conectar();
            ps = con.prepareStatement(query);
            ps.setInt(1, idCita);

            int resultado = ps.executeUpdate();
            return resultado > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar cita: " + e.getMessage());
            return false;
        } finally {
            cerrarRecursos();
        }
    }

    public Cita obtenerCitaPorId(int idCita) {
        String query = "SELECT * FROM citas WHERE id_cita = ?";
        Cita cita = null;

        try {
            con = cn.conectar();
            ps = con.prepareStatement(query);
            ps.setInt(1, idCita);
            rs = ps.executeQuery();

            if (rs.next()) {
                cita = new Cita();
                cita.setIdCita(rs.getInt("id_cita"));
                cita.setIdPaciente(rs.getInt("id_paciente"));
                cita.setIdTratamiento(rs.getInt("id_tratamiento"));
                cita.setFechaHora(rs.getTimestamp("fecha_hora"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al obtener cita: " + e.getMessage());
        } finally {
            cerrarRecursos();
        }
        return cita;
    }
}
