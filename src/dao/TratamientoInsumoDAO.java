package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import models.TratamientoInsumo;

/**
 * DAO encargado de gestionar los tratamientos con los insumos requeridos.
 *
 * @author Jakim
 */
public class TratamientoInsumoDAO {

    Conexion cn = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    public boolean registrarRelacion(TratamientoInsumo ti) {
        String query = "INSERT INTO tratamiento_insumos (id_tratamiento, id_insumo, cantidad_requerida) VALUES (?, ?, ?)";

        try {
            con = cn.conectar();
            ps = con.prepareStatement(query);
            ps.setInt(1, ti.getIdTratamiento());
            ps.setInt(2, ti.getIdInsumo());
            ps.setDouble(3, ti.getCantidadRequerida());
            ps.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return false;
        }
    }

    public List<TratamientoInsumo> listarPorTratamiento(int idTratamiento) {
        List<TratamientoInsumo> lista = new ArrayList<>();
        String query = "SELECT * FROM tratamiento_insumos WHERE id_tratamiento = ?";

        try {
            con = cn.conectar();
            ps = con.prepareStatement(query);
            ps.setInt(1, idTratamiento);
            rs = ps.executeQuery();

            while (rs.next()) {
                TratamientoInsumo ti = new TratamientoInsumo();
                ti.setIdTratamientoInsumo(rs.getInt("id_tratamiento_insumo"));
                ti.setIdTratamiento(rs.getInt("id_tratamiento"));
                ti.setIdInsumo(rs.getInt("id_insumo"));
                ti.setCantidadRequerida(rs.getDouble("cantidad_requerida"));
                lista.add(ti);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return lista;
    }
}
