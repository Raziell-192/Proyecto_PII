package models;

import controllers.Conexion;
import java.sql.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author jakim
 */
public class SalesConnection {

    //Instanciar conexión:
    Conexion cn = new Conexion();
    Connection con = null;
    PreparedStatement ps;
    ResultSet rs;

    //Registrar venta
    public boolean registrarVentaQuery(int idPaciente, int idTipoPrecio, double total) {
        String query = "INSERT INTO ventas(id_paciente, id_tipo_precio, total, fecha_venta) VALUES (?, ?, ?, ?);";
        //Timestamp fecha = new Timestamp(new Date().getTime());
        LocalDateTime now = LocalDateTime.now();
        Timestamp sqlTimestamp = Timestamp.valueOf(now);
        try {
            con = cn.conectar();
            ps = con.prepareStatement(query);
            ps.setInt(1, idPaciente);
            ps.setInt(2, idTipoPrecio);
            ps.setDouble(3, total);
            ps.setTimestamp(4, sqlTimestamp);
            ps.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return false;
        }
    }

    //Registrar detalles de venta de tratamientos: detalle_venta_tratamientos
    public boolean registrarDetallesVentaTratamientosQuery(int idVenta, int idTratamiento, int cantidad, double precioUnitario, double total) {
        String query = "INSERT INTO detalle_venta_tratamientos(id_venta, id_tratamiento, cantidad, precio_unitario, total) VALUES (?, ?, ?, ?, ?);";
        try {
            con = cn.conectar();
            ps = con.prepareStatement(query);
            ps.setInt(1, idVenta);
            ps.setInt(2, idTratamiento);
            ps.setInt(3, cantidad);
            ps.setDouble(4, precioUnitario);
            ps.setDouble(4, total);
            ps.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return false;
        }
    }

    //Registrar detalles de venta de insumos: detalle_venta_insumos
    public boolean registrarDetallesVentaInsumosQuery(int idVenta, int idInsumo, double cantidad, double precioUnitario, double total) {
        String query = "INSERT INTO detalle_venta_tratamientos(id_venta, id_insumo, cantidad, precio_unitario, total) VALUES (?, ?, ?, ?, ?);";
        try {
            con = cn.conectar();
            ps = con.prepareStatement(query);
            ps.setInt(1, idVenta);
            ps.setInt(2, idInsumo);
            ps.setDouble(3, cantidad);
            ps.setDouble(4, precioUnitario);
            ps.setDouble(4, total);
            ps.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return false;
        }
    }

    public int idVenta() {
        int id = 0;
        String query = "SELECT MAX(id_venta) AS id_venta FROM ventas";
        try {
            con = cn.conectar();
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            if (rs.next()) {
                id = rs.getInt("id_venta");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return id;
    }

    public List listarTodasLasVentasQuery() {
        List<Sales> listaVentas = new ArrayList();
        String query = "SELECT v.id_venta   AS factura, p.nombres AS paciente, u.nombres AS usuarios, v.total, v.fecha_venta FROM ventas s INNER JOIN pacientes p on v.id_paciente = p.id_paciente INNER JOIN usuarios u on v.id_usuario = u.id_usuario ORDER BY v.id_venta ASC";
        try {
            con = cn.conectar();
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                Sales venta = new Sales();
                venta.setIdVenta(rs.getInt("factura"));
                venta.setIdPaciente(rs.getInt("paciente"));
                venta.setNombrePaciente(rs.getString("paciente"));
                venta.setNombreUsuario(rs.getString("usuario"));
                venta.setTotal(rs.getDouble("total"));
                venta.setFechaVenta(rs.getString("fecha_venta"));
                listaVentas.add(venta);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return listaVentas;
    }

}
