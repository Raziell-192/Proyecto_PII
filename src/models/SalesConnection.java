package models;

import controllers.Conexion;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 * DAO encargado de la gestión de operaciones CRUD relacionadas con las ventas,
 * así como el registro de los detalles de venta de tratamientos e insumos.
 *
 * @author Jakim
 */
public class SalesConnection {

    // Conexión a la base de datos
    Conexion cn = new Conexion();
    Connection con = null;
    PreparedStatement ps;
    ResultSet rs;

    /**
     * Registra una nueva venta en la base de datos.
     *
     * @param sale Objeto Sales con la información de la venta
     * @return true si se registra correctamente
     */
    public boolean registrarVenta(Sales sale) {
        String sql = """
            INSERT INTO ventas (numero_venta, id_paciente, id_tipo_precio, fecha_venta, total)
            VALUES (?, ?, ?, ?, ?)
        """;

        try {
            con = cn.conectar();
            ps = con.prepareStatement(sql);

            ps.setString(1, sale.getNumeroVenta());
            ps.setInt(2, sale.getIdPaciente());
            ps.setInt(3, sale.getIdTipoPrecio());
            ps.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
            ps.setDouble(5, sale.getTotal());

            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al registrar venta: " + e.getMessage());
            return false;
        }
    }

    /**
     * Registra un detalle de venta de tratamiento.
     */
    public boolean registrarDetalleTratamiento(
            int idVenta, int idTratamiento, int cantidad,
            double precioUnitario, double total) {

        String sql = """
            INSERT INTO detalle_venta_tratamientos
            (id_venta, id_tratamiento, cantidad, precio_unitario, total)
            VALUES (?, ?, ?, ?, ?)
        """;

        try {
            con = cn.conectar();
            ps = con.prepareStatement(sql);

            ps.setInt(1, idVenta);
            ps.setInt(2, idTratamiento);
            ps.setInt(3, cantidad);
            ps.setDouble(4, precioUnitario);
            ps.setDouble(5, total);

            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al registrar detalle de tratamiento: " + e.getMessage());
            return false;
        }
    }

    /**
     * Registra un detalle de venta de insumo.
     */
    public boolean registrarDetalleInsumo(
            int idVenta, int idInsumo, double cantidad,
            double precioUnitario, double total) {

        String sql = """
            INSERT INTO detalle_venta_insumos
            (id_venta, id_insumo, cantidad, precio_unitario, total)
            VALUES (?, ?, ?, ?, ?)
        """;

        try {
            con = cn.conectar();
            ps = con.prepareStatement(sql);

            ps.setInt(1, idVenta);
            ps.setInt(2, idInsumo);
            ps.setDouble(3, cantidad);
            ps.setDouble(4, precioUnitario);
            ps.setDouble(5, total);

            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al registrar detalle de insumo: " + e.getMessage());
            return false;
        }
    }

    /**
     * Obtiene el último ID de venta registrado.
     */
    public int obtenerUltimoIdVenta() {
        String sql = "SELECT MAX(id_venta) FROM ventas";
        int id = 0;

        try {
            con = cn.conectar();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            if (rs.next()) {
                id = rs.getInt(1);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al obtener ID de venta: " + e.getMessage());
        }
        return id;
    }

    /**
     * Obtiene una venta por su ID.
     */
    public Sales obtenerVentaPorId(int idVenta) {
        String sql = "SELECT * FROM ventas WHERE id_venta = ?";
        Sales sale = null;

        try {
            con = cn.conectar();
            ps = con.prepareStatement(sql);
            ps.setInt(1, idVenta);
            rs = ps.executeQuery();

            if (rs.next()) {
                sale = new Sales();
                sale.setIdVenta(rs.getInt("id_venta"));
                sale.setNumeroVenta(rs.getString("numero_venta"));
                sale.setIdPaciente(rs.getInt("id_paciente"));
                sale.setIdTipoPrecio(rs.getInt("id_tipo_precio"));
                sale.setFechaVenta(rs.getTimestamp("fecha_venta"));
                sale.setTotal(rs.getDouble("total"));
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al obtener venta: " + e.getMessage());
        }
        return sale;
    }

    /**
     * Lista todas las ventas registradas.
     */
    public List<Sales> listarVentas() {
        List<Sales> lista = new ArrayList<>();

        String sql = """
            SELECT v.id_venta, v.numero_venta,
                   p.nombres AS paciente,
                   v.total, v.fecha_venta
            FROM ventas v
            INNER JOIN pacientes p ON v.id_paciente = p.id_paciente
            ORDER BY v.id_venta
        """;

        try {
            con = cn.conectar();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Sales sale = new Sales();
                sale.setIdVenta(rs.getInt("id_venta"));
                sale.setNumeroVenta(rs.getString("numero_venta"));
//                sale.setNombrePaciente(rs.getString("paciente"));
                sale.setTotal(rs.getDouble("total"));
                sale.setFechaVenta(rs.getTimestamp("fecha_venta"));
                lista.add(sale);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al listar ventas: " + e.getMessage());
        }
        return lista;
    }

    /**
     * Actualiza el total de una venta.
     */
    public boolean actualizarTotalVenta(int idVenta, double total) {
        String sql = "UPDATE ventas SET total = ? WHERE id_venta = ?";

        try {
            con = cn.conectar();
            ps = con.prepareStatement(sql);
            ps.setDouble(1, total);
            ps.setInt(2, idVenta);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar venta: " + e.getMessage());
            return false;
        }
    }

    /**
     * Elimina una venta por su ID.
     */
    public boolean eliminarVenta(int idVenta) {
        String sql = "DELETE FROM ventas WHERE id_venta = ?";

        try {
            con = cn.conectar();
            ps = con.prepareStatement(sql);
            ps.setInt(1, idVenta);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar venta: " + e.getMessage());
            return false;
        }
    }
}
