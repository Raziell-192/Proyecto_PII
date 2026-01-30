package models;

import controllers.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 * DAO para gestionar los detalles de venta de insumos.
 *
 * @author Jakim
 */
public class DetalleVentaInsumoDAO {

    Conexion cn = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    /**
     * Registra un detalle de venta de insumo.
     */
    public boolean registrarDetalle(DetalleVentaInsumo detalle) {
        String query = "INSERT INTO detalle_venta_insumos "
                + "(id_venta, id_insumo, cantidad, precio_unitario, total) "
                + "VALUES (?, ?, ?, ?, ?)";

        try {
            con = cn.conectar();
            ps = con.prepareStatement(query);
            ps.setInt(1, detalle.getIdVenta());
            ps.setInt(2, detalle.getIdInsumo());
            ps.setDouble(3, detalle.getCantidad());
            ps.setDouble(4, detalle.getPrecioUnitario());
            ps.setDouble(5, detalle.getTotal());
            ps.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al registrar detalle de insumo: " + e.getMessage());
            return false;
        }
    }

    /**
     * Lista los insumos asociados a una venta.
     */
    public List<DetalleVentaInsumo> listarPorVenta(int idVenta) {
        List<DetalleVentaInsumo> lista = new ArrayList<>();
        String query = "SELECT * FROM detalle_venta_insumos WHERE id_venta = ?";

        try {
            con = cn.conectar();
            ps = con.prepareStatement(query);
            ps.setInt(1, idVenta);
            rs = ps.executeQuery();

            while (rs.next()) {
                DetalleVentaInsumo detalle = new DetalleVentaInsumo();
                detalle.setIdDetalleVentaInsumo(rs.getInt("id_detalle_venta_insumo"));
                detalle.setIdVenta(rs.getInt("id_venta"));
                detalle.setIdInsumo(rs.getInt("id_insumo"));
                detalle.setCantidad(rs.getDouble("cantidad"));
                detalle.setPrecioUnitario(rs.getDouble("precio_unitario"));
                detalle.setTotal(rs.getDouble("total"));
                lista.add(detalle);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al listar insumos: " + e.getMessage());
        }
        return lista;
    }
}
