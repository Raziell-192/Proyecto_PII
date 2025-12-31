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
public class ProductsConnection {

    //Instanciar conexión
    Conexion cn = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    //Registrar productos
    public boolean registrarInsumoQuery(Products insumo) {
        String query = "INSERT INTO insumos(codigo, nombre, descripcion, presentacion, total_piezas, precio_unitario) VALUES (?,?,?,?,?,?)";
        try {
            con = cn.conectar();
            ps = con.prepareStatement(query);
            ps.setInt(1, insumo.getCodigo()); 
            ps.setString(2, insumo.getNombre());
            ps.setString(3, insumo.getDescripcion());
            ps.setString(4, insumo.getPresentacion());
            ps.setDouble(5, insumo.getTotal_piezas());
            ps.setDouble(6, insumo.getPrecio_unitario());
            ps.execute();
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al registrar insumo: " + ex);
            return false;
        } finally {
            cerrarRecursos();
        }
    }
    
    public List<Products> obtenerTodosLosInsumos() {
        List<Products> listaInsumos = new ArrayList<>();
        String query = "SELECT * FROM insumos ORDER BY nombre ASC";
        
        try {
            con = cn.conectar();
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                Products insumo = new Products(
                    rs.getInt("id_insumo"),
                    rs.getInt("codigo"),
                    rs.getString("nombre"),
                    rs.getString("descripcion"),
                    rs.getString("presentacion"),
                    rs.getDouble("total_piezas"),
                    rs.getDouble("precio_unitario")
                );
                listaInsumos.add(insumo);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al obtener insumos: " + ex);
        } finally {
            cerrarRecursos();
        }
        return listaInsumos;
    }
    
    public List<Products> buscarInsumos(String criterio) {
        List<Products> listaInsumos = new ArrayList<>();
        String query = "SELECT * FROM insumos WHERE nombre ILIKE ? OR codigo::text ILIKE ? OR descripcion ILIKE ? ORDER BY nombre ASC";
        
        try {
            con = cn.conectar();
            ps = con.prepareStatement(query);
            String likeCriterio = "%" + criterio + "%";
            ps.setString(1, likeCriterio);
            ps.setString(2, likeCriterio);
            ps.setString(3, likeCriterio);
            
            rs = ps.executeQuery();
            
            while (rs.next()) {
                Products insumo = new Products(
                    rs.getInt("id_insumo"),
                    rs.getInt("codigo"),
                    rs.getString("nombre"),
                    rs.getString("descripcion"),
                    rs.getString("presentacion"),
                    rs.getDouble("total_piezas"),
                    rs.getDouble("precio_unitario")
                );
                listaInsumos.add(insumo);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al buscar insumos: " + ex);
        } finally {
            cerrarRecursos();
        }
        return listaInsumos;
    }
    
    public Products obtenerInsumoPorId(int id) {
        String query = "SELECT * FROM insumos WHERE id_insumo = ?";
        Products insumo = null;
        
        try {
            con = cn.conectar();
            ps = con.prepareStatement(query);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                insumo = new Products(
                    rs.getInt("id_insumo"),
                    rs.getInt("codigo"),
                    rs.getString("nombre"),
                    rs.getString("descripcion"),
                    rs.getString("presentacion"),
                    rs.getDouble("total_piezas"),
                    rs.getDouble("precio_unitario")
                );
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al obtener insumo: " + ex);
        } finally {
            cerrarRecursos();
        }
        return insumo;
    }
    
    public boolean actualizarInsumoQuery(Products insumo) {
        String query = "UPDATE insumos SET codigo = ?, nombre = ?, descripcion = ?, "
                     + "presentacion = ?, total_piezas = ?, precio_unitario = ? WHERE id_insumo = ?";
        
        try {
            con = cn.conectar();
            ps = con.prepareStatement(query);
            ps.setInt(1, insumo.getCodigo()); 
            ps.setString(2, insumo.getNombre());
            ps.setString(3, insumo.getDescripcion());
            ps.setString(4, insumo.getPresentacion());
            ps.setDouble(5, insumo.getTotal_piezas());
            ps.setDouble(6, insumo.getPrecio_unitario());
            ps.setInt(7, insumo.getId_insumo());
            
            int resultado = ps.executeUpdate();
            return resultado > 0;
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al actualizar insumo: " + ex);
            return false;
        } finally {
            cerrarRecursos();
        }
    }
    
    public boolean eliminarInsumoQuery(int idInsumo) {
        String query = "DELETE FROM insumos WHERE id_insumo = ?";
        
        try {
            con = cn.conectar();
            ps = con.prepareStatement(query);
            ps.setInt(1, idInsumo);
            
            int resultado = ps.executeUpdate();
            return resultado > 0;
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al eliminar insumo: " + ex);
            return false;
        } finally {
            cerrarRecursos();
        }
    }
    
    public boolean verificarCodigoExistente(int codigo, int idExcluir) {
        String query = "SELECT COUNT(*) FROM insumos WHERE codigo = ? AND id_insumo != ?";
        
        try {
            con = cn.conectar();
            ps = con.prepareStatement(query);
            ps.setInt(1, codigo); 
            ps.setInt(2, idExcluir);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al verificar código: " + ex);
        } finally {
            cerrarRecursos();
        }
        return false;
    }
    
    private void cerrarRecursos() {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (con != null) con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}