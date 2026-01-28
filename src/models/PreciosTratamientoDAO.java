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
public class PreciosTratamientoDAO {

    Conexion cn = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    /**
     * REGISTRAR PRECIO
     *
     * @param precioTratamiento El objeto de tipo «PrecioTratamiento» que se insertará en la base de datos
     * @return true si sí fue insertado en la base de datos o false si hubo un error
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
     *
     * @return la lista de precios de los tratamientos
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
     *
     * @param precioTratamiento El objeto de tipo «PrecioTratamiento» que se actualizará en la base de datos
     * @return true si el precio sí fue actualizado o false si hubo un error
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
     *
     * @param idPrecio El id del precio a eliminar
     * @return true si el precio sí fue eliminado o false hubo un error
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
