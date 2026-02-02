package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import models.TipoPrecio;

/**
 * DAO encargado de gestionar el tipo de precios.
 *
 * @author Jakim
 */
public class TipoPrecioDAO {

    Conexion cn = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    // Insertar nuevo tipo de precio
    public boolean insertarTipoPrecio(TipoPrecio tipoPrecio) {
        String query = "INSERT INTO tipos_precio (nombre, codigo) VALUES (?, ?)";
        
        try {
            con = cn.conectar();
            ps = con.prepareStatement(query);
            ps.setString(1, tipoPrecio.getNombre());
            ps.setString(2, tipoPrecio.getCodigo().toUpperCase());
            
            int resultado = ps.executeUpdate();
            return resultado > 0;
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al insertar tipo de precio: " + e.getMessage());
            return false;
        } finally {
            cerrarRecursos();
        }
    }

    // Obtener todos los tipos de precio
    public List<TipoPrecio> listarTiposPrecio() {
        List<TipoPrecio> lista = new ArrayList<>();
        String query = "SELECT * FROM tipos_precio ORDER BY nombre";

        try {
            con = cn.conectar();
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
                TipoPrecio tipo = new TipoPrecio();
                tipo.setIdTipoPrecio(rs.getInt("id_tipo_precio"));
                tipo.setNombre(rs.getString("nombre"));
                tipo.setCodigo(rs.getString("codigo"));
                lista.add(tipo);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al listar tipos de precio: " + e.getMessage());
        } finally {
            cerrarRecursos();
        }
        return lista;
    }
    
    // Obtener por ID
    public TipoPrecio obtenerTipoPrecioPorId(int id) {
        TipoPrecio tipoPrecio = null;
        String query = "SELECT * FROM tipos_precio WHERE id_tipo_precio = ?";
        
        try {
            con = cn.conectar();
            ps = con.prepareStatement(query);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                tipoPrecio = new TipoPrecio();
                tipoPrecio.setIdTipoPrecio(rs.getInt("id_tipo_precio"));
                tipoPrecio.setNombre(rs.getString("nombre"));
                tipoPrecio.setCodigo(rs.getString("codigo"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al obtener tipo de precio: " + e.getMessage());
        } finally {
            cerrarRecursos();
        }
        return tipoPrecio;
    }
    
    // Buscar tipos de precio
    public List<TipoPrecio> buscarTiposPrecio(String criterio) {
        List<TipoPrecio> lista = new ArrayList<>();
        String query = "SELECT * FROM tipos_precio WHERE nombre ILIKE ? OR codigo ILIKE ? ORDER BY nombre";
        
        try {
            con = cn.conectar();
            ps = con.prepareStatement(query);
            String likeCriterio = "%" + criterio + "%";
            ps.setString(1, likeCriterio);
            ps.setString(2, likeCriterio);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                TipoPrecio tipo = new TipoPrecio();
                tipo.setIdTipoPrecio(rs.getInt("id_tipo_precio"));
                tipo.setNombre(rs.getString("nombre"));
                tipo.setCodigo(rs.getString("codigo"));
                lista.add(tipo);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al buscar tipos de precio: " + e.getMessage());
        } finally {
            cerrarRecursos();
        }
        return lista;
    }

    // Actualizar tipo de precio
    public boolean actualizarTipoPrecio(TipoPrecio tipoPrecio) {
        String query = "UPDATE tipos_precio SET nombre = ?, codigo = ? WHERE id_tipo_precio = ?";
        
        try {
            con = cn.conectar();
            ps = con.prepareStatement(query);
            ps.setString(1, tipoPrecio.getNombre());
            ps.setString(2, tipoPrecio.getCodigo().toUpperCase());
            ps.setInt(3, tipoPrecio.getIdTipoPrecio());
            
            int resultado = ps.executeUpdate();
            return resultado > 0;
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar tipo de precio: " + e.getMessage());
            return false;
        } finally {
            cerrarRecursos();
        }
    }

    // Eliminar tipo de precio
    public boolean eliminarTipoPrecio(int id) {
        String query = "DELETE FROM tipos_precio WHERE id_tipo_precio = ?";
        
        try {
            con = cn.conectar();
            ps = con.prepareStatement(query);
            ps.setInt(1, id);
            
            int resultado = ps.executeUpdate();
            return resultado > 0;
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar tipo de precio: " + e.getMessage());
            return false;
        } finally {
            cerrarRecursos();
        }
    }
    
    // Verificar si existe código
    public boolean existeCodigo(String codigo, int idExcluir) {
        String query = "SELECT COUNT(*) FROM tipos_precio WHERE codigo = ? AND id_tipo_precio != ?";
        
        try {
            con = cn.conectar();
            ps = con.prepareStatement(query);
            ps.setString(1, codigo.toUpperCase());
            ps.setInt(2, idExcluir);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al verificar código: " + e.getMessage());
        } finally {
            cerrarRecursos();
        }
        return false;
    }
    
    // Método para cerrar recursos
    private void cerrarRecursos() {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (con != null) con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}