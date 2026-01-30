package models;

import controllers.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 * DAO encargado de gestionar el historial de tratamientos de los pacientes.
 *
 * @author Jakim
 */
public class HistorialTratamientoDAO {

    Conexion cn = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    /**
     * Registra un tratamiento realizado en el historial clínico.
     */
    public boolean registrarHistorial(HistorialTratamiento historial) {
        String query = "INSERT INTO historial_tratamientos "
                + "(id_paciente, id_detalle_venta_tratamiento, fecha_realizacion) "
                + "VALUES (?, ?, ?)";

        try {
            con = cn.conectar();
            ps = con.prepareStatement(query);
            ps.setInt(1, historial.getIdPaciente());
            ps.setInt(2, historial.getIdDetalleVentaTratamiento());
            ps.setDate(3, historial.getFechaRealizacion());
            ps.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al registrar historial: " + e.getMessage());
            return false;
        }
    }

    /**
     * Obtiene el historial de tratamientos de un paciente.
     */
    public List<HistorialTratamiento> listarPorPaciente(int idPaciente) {
        List<HistorialTratamiento> lista = new ArrayList<>();
        String query = "SELECT * FROM historial_tratamientos WHERE id_paciente = ? ORDER BY fecha_realizacion DESC";

        try {
            con = cn.conectar();
            ps = con.prepareStatement(query);
            ps.setInt(1, idPaciente);
            rs = ps.executeQuery();

            while (rs.next()) {
                HistorialTratamiento historial = new HistorialTratamiento();
                historial.setIdHistorial(rs.getInt("id_historial"));
                historial.setIdPaciente(rs.getInt("id_paciente"));
                historial.setIdDetalleVentaTratamiento(
                        rs.getInt("id_detalle_venta_tratamiento")
                );
                historial.setFechaRealizacion(rs.getDate("fecha_realizacion"));
                lista.add(historial);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al listar historial: " + e.getMessage());
        }
        return lista;
    }
}
