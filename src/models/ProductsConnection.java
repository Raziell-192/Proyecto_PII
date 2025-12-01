package models;

import controllers.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    public boolean registrarInsumosQuery(Products insumo) {
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
            JOptionPane.showMessageDialog(null, "Error al registrar insumo " + ex);
            return false;
        }
    }
}
