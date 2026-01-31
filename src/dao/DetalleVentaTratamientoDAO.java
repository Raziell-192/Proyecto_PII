package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import models.DetalleVentaTratamiento;

/**
 * DAO para gestionar los detalles de venta de tratamientos.
 *
 * @author Jakim
 */
public class DetalleVentaTratamientoDAO {

    Conexion cn = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    /**
     * Registra un detalle de venta de tratamiento.
     */
    public boolean registrarDetalle(DetalleVentaTratamiento detalle) {
        String query = "INSERT INTO detalle_venta_tratamientos "
                + "(id_venta, id_tratamiento, cantidad, precio_unitario, total) "
                + "VALUES (?, ?, ?, ?, ?)";

        try {
            con = cn.conectar();
            ps = con.prepareStatement(query);
            ps.setInt(1, detalle.getIdVenta());
            ps.setInt(2, detalle.getIdTratamiento());
            ps.setInt(3, detalle.getCantidad());
            ps.setDouble(4, detalle.getPrecioUnitario());
            ps.setDouble(5, detalle.getTotal());
            ps.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al registrar detalle de tratamiento: " + e.getMessage());
            return false;
        }
    }

    /**
     * Lista los tratamientos asociados a una venta.
     */
    public List<DetalleVentaTratamiento> listarPorVenta(int idVenta) {
        List<DetalleVentaTratamiento> lista = new ArrayList<>();
        String query = "SELECT * FROM detalle_venta_tratamientos WHERE id_venta = ?";

        try {
            con = cn.conectar();
            ps = con.prepareStatement(query);
            ps.setInt(1, idVenta);
            rs = ps.executeQuery();

            while (rs.next()) {
                DetalleVentaTratamiento detalle = new DetalleVentaTratamiento();
                detalle.setIdDetalleVentaTratamiento(rs.getInt("id_detalle_venta_tratamiento"));
                detalle.setIdVenta(rs.getInt("id_venta"));
                detalle.setIdTratamiento(rs.getInt("id_tratamiento"));
                detalle.setCantidad(rs.getInt("cantidad"));
                detalle.setPrecioUnitario(rs.getDouble("precio_unitario"));
                detalle.setTotal(rs.getDouble("total"));
                lista.add(detalle);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al listar detalles: " + e.getMessage());
        }
        return lista;
    }
}
