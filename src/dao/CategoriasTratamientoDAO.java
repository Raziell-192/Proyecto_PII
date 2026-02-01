package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import models.CategoriaTratamiento;

/**
 * Clase DAO para la gestión de categorías de tratamientos.
 * <p>
 * Proporciona las operaciones CRUD sobre la tabla
 * {@code categorias_tratamiento}.
 * </p>
 *
 * Permite registrar, listar, actualizar y eliminar categorías utilizadas por
 * los tratamientos odontológicos.
 *
 * @author Jakim
 */
public class CategoriasTratamientoDAO {

    Conexion cn = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    /**
     * REGISTRAR CATEGORÍA
     * <p>
     * Registra una nueva categoría de tratamiento.
     * </p>
     *
     * @param categoria Objeto {@link CategoriaTratamiento} con la información
     * de la categoría.
     * @return {@code true} si la categoría se registra correctamente,
     * {@code false} en caso de error.
     */
    public boolean registrarCategoria(CategoriaTratamiento categoria) {
        String query = "INSERT INTO categorias_tratamiento (nombre, codigo) VALUES (?, ?)";

        try {
            con = cn.conectar();
            ps = con.prepareStatement(query);
            ps.setString(1, categoria.getNombre());
            ps.setString(2, categoria.getCodigo());
            ps.execute();
            return true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al registrar categoría: " + e);
            return false;
        } finally {
            cerrarConexion();
        }
    }

    /**
     * LISTAR CATEGORÍAS
     * <p>
     * Obtiene todas las categorías de tratamientos registradas.
     * </p>
     *
     * @return Lista de objetos {@link CategoriaTratamiento}.
     */
    public List<CategoriaTratamiento> listarCategorias() {
    List<CategoriaTratamiento> lista = new ArrayList<>();
    String query = "SELECT * FROM categorias_tratamiento ORDER BY id_categoria ASC";
    
    try {
        con = cn.conectar();
        ps = con.prepareStatement(query);
        rs = ps.executeQuery();
        
        while (rs.next()) {
            CategoriaTratamiento categoria = new CategoriaTratamiento();
            categoria.setIdCategoria(rs.getInt("id_categoria"));
            categoria.setNombre(rs.getString("nombre"));
            categoria.setCodigo(rs.getString("codigo"));
            lista.add(categoria);
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Error al listar categorías: " + e);
    } finally {
        cerrarConexion();
    }
    return lista;
}

    /**
     * ACTUALIZAR CATEGORÍA
     * <p>
     * Actualiza la información de una categoría existente.
     * </p>
     *
     * @param categoria Objeto {@link CategoriaTratamiento} con los datos
     * actualizados.
     * @return {@code true} si la actualización es exitosa, {@code false} en
     * caso de error.
     */
    public boolean actualizarCategoria(CategoriaTratamiento categoria) {
        String query = "UPDATE categorias_tratamiento SET nombre = ?, codigo = ? WHERE id_categoria = ?";

        try {
            con = cn.conectar();
            ps = con.prepareStatement(query);
            ps.setString(1, categoria.getNombre());
            ps.setString(2, categoria.getCodigo());
            ps.setInt(3, categoria.getIdCategoria());
            ps.execute();
            return true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar categoría: " + e);
            return false;
        } finally {
            cerrarConexion();
        }
    }

    /**
     * ELIMINAR CATEGORÍA
     * <p>
     * Elimina una categoría de tratamiento.
     * </p>
     *
     * @param idCategoria Identificador de la categoría a eliminar.
     * @return {@code true} si la eliminación se realiza correctamente,
     * {@code false} si la categoría tiene tratamientos asociados.
     */
    public boolean eliminarCategoria(int idCategoria) {
        String query = "DELETE FROM categorias_tratamiento WHERE id_categoria = ?";

        try {
            con = cn.conectar();
            ps = con.prepareStatement(query);
            ps.setInt(1, idCategoria);

            int resultado = ps.executeUpdate();
            return resultado > 0;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "No se puede eliminar la categoría porque está relacionada con tratamientos.");
            return false;
        } finally {
            cerrarConexion();
        }
    }

    /**
     * CERRAR CONEXIONES
     * <p>
     * Cierra los recursos de conexión utilizados en la clase DAO.
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
