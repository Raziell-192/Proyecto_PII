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

    public List<Cita> buscarCitasILike(String criterio) {
        List<Cita> lista = new ArrayList<>();
        String sql = """
                SELECT 
                    id_cita,
                    id_paciente,
                    id_tratamiento,
                    fecha_hora
                FROM citas
                WHERE CAST(fecha_hora AS TEXT) ILIKE ?
                   OR CAST(id_cita AS TEXT) ILIKE ?
                ORDER BY fecha_hora DESC
            """;
        // SQL para buscar por nombre del paciente o tratamiento  con JOIN
//        String query = """
//               SELECT c.id_cita, c.id_paciente, c.id_tratamiento, c.fecha_hora
//                FROM citas c
//                JOIN pacientes p ON c.id_paciente = p.id_paciente
//                JOIN tratamientos t ON c.id_tratamiento = t.id_tratamiento
//                WHERE p.nombres ILIKE ?
//                   OR p.apellidos ILIKE ?
//                   OR t.nombre ILIKE ?
//                   OR CAST(c.fecha_hora AS TEXT) ILIKE ?
//                ORDER BY c.fecha_hora DESC
//            """;

        try {
            con = cn.conectar();
            ps = con.prepareStatement(sql);

            String filtro = "%" + criterio + "%";
            ps.setString(1, filtro);
            ps.setString(2, filtro);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Cita c = new Cita();
                    c.setIdCita(rs.getInt("id_cita"));
                    c.setIdPaciente(rs.getInt("id_paciente"));
                    c.setIdTratamiento(rs.getInt("id_tratamiento"));
                    c.setFechaHora(rs.getTimestamp("fecha_hora"));

                    lista.add(c);
                }
            }

        } catch (Exception e) {
            System.err.println("Error al buscar citas: " + e.getMessage());
        } finally {
            cerrarRecursos();
        }

        return lista;
    }

}
