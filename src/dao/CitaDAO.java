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
            JOptionPane.showMessageDialog(null, e.getMessage());
            return false;
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
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return lista;
    }
}
