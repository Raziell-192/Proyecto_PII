package models;

import controllers.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 * Clase DAO (Data Access Object) para la gestión de tratamientos.
 * <p>
 * Esta clase se encarga de realizar las operaciones CRUD (Crear, Leer,
 * Actualizar y Eliminar) sobre la tabla {@code tratamientos} en la base de
 * datos.
 * </p>
 *
 * Utiliza {@link PreparedStatement} para ejecutar consultas SQL de forma segura
 * y la clase {@link Conexion} para la conexión con la base de datos.
 *
 * @author Jakim
 */
public class TratamientosDAO {

    Conexion cn = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    /**
     * REGISTRAR TRATAMIENTO
     * <p>
     * Registra un nuevo tratamiento en la base de datos.
     * </p>
     *
     * @param tratamiento Objeto {@link Tratamiento} con la información del
     * tratamiento a registrar.
     * @return {@code true} si el tratamiento se registra correctamente,
     * {@code false} en caso de error.
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
     * LISTAR TRATAMIENTOS
     * <p>
     * Obtiene la lista completa de tratamientos registrados.
     * </p>
     *
     * @return Lista de objetos {@link Tratamiento} obtenidos desde la base de
     * datos.
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
     * BUSCAR TRATAMIENTOS
     * <p>
     * Busca tratamientos por código o nombre.
     * </p>
     *
     * @param valor Texto a buscar dentro del código o nombre del tratamiento.
     * @return Lista de tratamientos que coinciden con el criterio de búsqueda.
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
     * ACTUALIZAR TRATAMIENTO
     * <p>
     * Actualiza la información de un tratamiento existente.
     * </p>
     *
     * @param tratamiento Objeto {@link Tratamiento} con los datos actualizados
     * del tratamiento.
     * @return {@code true} si la actualización se realiza correctamente,
     * {@code false} si ocurre algún error.
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
     * ELIMINAR TRATAMIENTO
     * <p>
     * Elimina un tratamiento de la base de datos.
     * </p>
     *
     * @param idTratamiento Identificador del tratamiento a eliminar.
     * @return {@code true} si el tratamiento se elimina correctamente,
     * {@code false} si el tratamiento tiene relaciones o ocurre un error.
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
     * CERRAR CONEXIONES
     * <p>
     * Cierra los recursos de base de datos utilizados.
     * </p>
     * <p>
     * Este método libera el {@link ResultSet}, {@link PreparedStatement} y
     * {@link Connection} para evitar fugas de memoria.
     * </p>
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
