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
public class TratamientosDAO {

    Conexion cn = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    /**
     * ========================= 
     * REGISTRAR TRATAMIENTO 
     * =========================
     * @param tratamiento
     * @return 
     */
    public boolean registrarTratamientoQuery(Tratamiento tratamiento) {
        String query = "INSERT INTO tratamientos (id_categoria, codigo, nombre, descripcion) VALUES (?,?,?,?)";

        try {
            con = cn.conectar();
            ps = con.prepareStatement(query);

            ps.setInt(1, tratamiento.getIdCategoria());
            ps.setString(2, tratamiento.getCodigo());
            ps.setString(3, tratamiento.getNombre());
            ps.setString(4, tratamiento.getDescripcion());

            ps.execute();
            return true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al registrar tratamiento: " + e);
            return false;
        } finally {
            cerrarConexion();
        }
    }

    /**
     * ========================= 
     * LISTAR TRATAMIENTOS 
     * =========================
     * @return 
     */
    public List<Tratamiento> listarTratamientosQuery() {
        List<Tratamiento> lista = new ArrayList<>();
        String query = "SELECT * FROM tratamientos ORDER BY id_tratamiento ASC";

        try {
            con = cn.conectar();
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
                Tratamiento tratamiento = new Tratamiento();
                tratamiento.setIdTratamiento(rs.getInt("id_tratamiento"));
                tratamiento.setIdCategoria(rs.getInt("id_categoria"));
                tratamiento.setCodigo(rs.getString("codigo"));
                tratamiento.setNombre(rs.getString("nombre"));
                tratamiento.setDescripcion(rs.getString("descripcion"));

                lista.add(tratamiento);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al listar tratamientos: " + e);
        } finally {
            cerrarConexion();
        }
        return lista;
    }

    /**
     * ========================= 
     * BUSCAR TRATAMIENTOS 
     * =========================
     * @param valor
     * @return 
     */
    public List<Tratamiento> buscarTratamientosQuery(String valor) {
        List<Tratamiento> lista = new ArrayList<>();
        String query = "SELECT * FROM tratamientos "
                + "WHERE codigo ILIKE ? OR nombre ILIKE ? ORDER BY id_tratamiento ASC";

        try {
            con = cn.conectar();
            ps = con.prepareStatement(query);

            String buscar = "%" + valor + "%";
            ps.setString(1, buscar);
            ps.setString(2, buscar);

            rs = ps.executeQuery();

            while (rs.next()) {
                Tratamiento tratamiento = new Tratamiento();
                tratamiento.setIdTratamiento(rs.getInt("id_tratamiento"));
                tratamiento.setIdCategoria(rs.getInt("id_categoria"));
                tratamiento.setCodigo(rs.getString("codigo"));
                tratamiento.setNombre(rs.getString("nombre"));
                tratamiento.setDescripcion(rs.getString("descripcion"));

                lista.add(tratamiento);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al buscar tratamientos: " + e);
        } finally {
            cerrarConexion();
        }
        return lista;
    }

    /**
     * ========================= 
     * ACTUALIZAR TRATAMIENTO
     * =========================
     * @param tratamiento
     * @return 
     */
    public boolean actualizarTratamientoQuery(Tratamiento tratamiento) {
        String query = "UPDATE tratamientos SET id_categoria = ?, codigo = ?, nombre = ?, descripcion = ? "
                + "WHERE id_tratamiento = ?";

        try {
            con = cn.conectar();
            ps = con.prepareStatement(query);

            ps.setInt(1, tratamiento.getIdCategoria());
            ps.setString(2, tratamiento.getCodigo());
            ps.setString(3, tratamiento.getNombre());
            ps.setString(4, tratamiento.getDescripcion());
            ps.setInt(5, tratamiento.getIdTratamiento());

            ps.execute();
            return true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar tratamiento: " + e);
            return false;
        } finally {
            cerrarConexion();
        }
    }

    /**
     * ========================= 
     * ELIMINAR TRATAMIENTO 
     * =========================
     * @param idTratamiento
     * @return 
     */
    public boolean eliminarTratamientoQuery(int idTratamiento) {
        String query = "DELETE FROM tratamientos WHERE id_tratamiento = ?";

        try {
            con = cn.conectar();
            ps = con.prepareStatement(query);
            ps.setInt(1, idTratamiento);

            int resultado = ps.executeUpdate();
            return resultado > 0;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "No se puede eliminar el tratamiento porque está relacionado con otras tablas.");
            return false;
        } finally {
            cerrarConexion();
        }
    }

    /**
     * ========================= 
     * CERRAR CONEXIONES 
     * =========================
     */
    private void cerrarConexion() {
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
}
