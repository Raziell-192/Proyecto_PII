package models;

import controllers.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 * Clase DAO encargada de la gestión de precios de tratamientos.
 * <p>
 * Realiza las operaciones CRUD sobre la tabla {@code precios_tratamiento}.
 * </p>
 *
 * Permite manejar diferentes tipos de precios para un mismo tratamiento.
 *
 * @author Jakim
 */
public class PreciosTratamientoDAO {

    Conexion cn = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    /**
     * REGISTRAR PRECIO
     * <p>
     * Registra un nuevo precio para un tratamiento.
     * </p>
     *
     * @param precioTratamiento Objeto {@link PrecioTratamiento} que contiene la
     * información del precio.
     * @return {@code true} si el precio se registra correctamente,
     * {@code false} en caso de error.
     */
    public boolean registrarPrecio(PrecioTratamiento precioTratamiento) {
        String query = "INSERT INTO precios_tratamiento (id_tratamiento, id_tipo_precio, precio) VALUES (?, ?, ?)";

        try {
            con = cn.conectar();
            ps = con.prepareStatement(query);
            ps.setInt(1, precioTratamiento.getIdTratamiento());
            ps.setInt(2, precioTratamiento.getIdTipoPrecio());
            ps.setDouble(3, precioTratamiento.getPrecio());
            ps.execute();
            return true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al registrar precio del tratamiento: " + e);
            return false;
        } finally {
            cerrarConexion();
        }
    }

    /**
     * LISTAR PRECIOS
     * <p>
     * Obtiene la lista de precios de tratamientos registrados.
     * </p>
     *
     * @return Lista de objetos {@link PrecioTratamiento}.
     */
    public List<PrecioTratamiento> listarPrecios() {
        List<PrecioTratamiento> lista = new ArrayList<>();
        String query = "SELECT * FROM precios_tratamiento ORDER BY id_precio ASC";

        try {
            con = cn.conectar();
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
                PrecioTratamiento precio = new PrecioTratamiento();
                precio.setIdPrecio(rs.getInt("id_precio"));
                precio.setIdTratamiento(rs.getInt("id_tratamiento"));
                precio.setIdTipoPrecio(rs.getInt("id_tipo_precio"));
                precio.setPrecio(rs.getDouble("precio"));
                lista.add(precio);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al listar precios de tratamientos: " + e);
        } finally {
            cerrarConexion();
        }
        return lista;
    }

    /**
     * ACTUALIZAR PRECIO
     * <p>
     * Actualiza un precio de tratamiento existente.
     * </p>
     *
     * @param precioTratamiento Objeto {@link PrecioTratamiento} con los datos
     * actualizados.
     * @return {@code true} si la actualización es exitosa, {@code false} en
     * caso de error.
     */
    public boolean actualizarPrecio(PrecioTratamiento precioTratamiento) {
        String query = "UPDATE precios_tratamiento SET id_tratamiento = ?, id_tipo_precio = ?, precio = ? "
                + "WHERE id_precio = ?";

        try {
            con = cn.conectar();
            ps = con.prepareStatement(query);
            ps.setInt(1, precioTratamiento.getIdTratamiento());
            ps.setInt(2, precioTratamiento.getIdTipoPrecio());
            ps.setDouble(3, precioTratamiento.getPrecio());
            ps.setInt(4, precioTratamiento.getIdPrecio());
            ps.execute();
            return true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar precio del tratamiento: " + e);
            return false;
        } finally {
            cerrarConexion();
        }
    }

    /**
     * ELIMINAR PRECIO
     * <p>
     * Elimina un precio de tratamiento de la base de datos.
     * </p>
     *
     * @param idPrecio Identificador del precio a eliminar.
     * @return {@code true} si el precio se elimina correctamente, {@code false}
     * en caso de error.
     */
    public boolean eliminarPrecio(int idPrecio) {
        String query = "DELETE FROM precios_tratamiento WHERE id_precio = ?";

        try {
            con = cn.conectar();
            ps = con.prepareStatement(query);
            ps.setInt(1, idPrecio);

            int resultado = ps.executeUpdate();
            return resultado > 0;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar precio del tratamiento: " + e);
            return false;
        } finally {
            cerrarConexion();
        }
    }

    /**
     * CERRAR CONEXIONES
     * <p>
     * Cierra los recursos de base de datos utilizados por la clase DAO.
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
