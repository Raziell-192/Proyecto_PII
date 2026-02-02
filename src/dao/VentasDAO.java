package dao;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import models.Venta;
import models.DetalleVentaTratamiento;
import models.DetalleVentaInsumo;

/**
 * DAO encargado de la gestión de operaciones CRUD relacionadas con las ventas,
 * así como el registro de los detalles de venta de tratamientos e insumos.
 *
 * @author Jakim
 */
public class VentasDAO {

    // Conexión a la base de datos
    Conexion cn = new Conexion();
    Connection con = null;
    PreparedStatement ps;
    ResultSet rs;

    /**
     * Registra una nueva venta en la base de datos.
     *
     * @param sale Objeto Venta con la información de la venta
     * @return true si se registra correctamente
     */
    public boolean registrarVenta(Venta sale) {
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
            
            if (sale.getFechaVenta() != null) {
                ps.setTimestamp(4, sale.getFechaVenta());
            } else {
                ps.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
            }
            
            ps.setDouble(5, sale.getTotal());

            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al registrar venta: " + e.getMessage());
            return false;
        }
    }

    /**
     * Actualiza una venta existente.
     */
    public boolean actualizarVenta(Venta sale) {
        String sql = """
            UPDATE ventas 
            SET numero_venta = ?, id_paciente = ?, id_tipo_precio = ?, 
                fecha_venta = ?, total = ?
            WHERE id_venta = ?
        """;

        try {
            con = cn.conectar();
            ps = con.prepareStatement(sql);

            ps.setString(1, sale.getNumeroVenta());
            ps.setInt(2, sale.getIdPaciente());
            ps.setInt(3, sale.getIdTipoPrecio());
            ps.setTimestamp(4, sale.getFechaVenta());
            ps.setDouble(5, sale.getTotal());
            ps.setInt(6, sale.getIdVenta());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar venta: " + e.getMessage());
            return false;
        }
    }

    /**
     * Verifica si un número de venta ya existe (para evitar duplicados).
     */
    public boolean verificarNumeroVentaExistente(String numeroVenta, int idExcluir) {
        String sql = "SELECT COUNT(*) FROM ventas WHERE numero_venta = ? AND id_venta != ?";
        
        try {
            con = cn.conectar();
            ps = con.prepareStatement(sql);
            ps.setString(1, numeroVenta);
            ps.setInt(2, idExcluir);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al verificar número de venta: " + e.getMessage());
        }
        return false;
    }

    /**
     * Busca ventas por número de venta o ID de paciente.
     */
    public List<Venta> buscarVentas(String criterio) {
        List<Venta> lista = new ArrayList<>();
        String sql = """
            SELECT v.id_venta, v.numero_venta, v.id_paciente, v.id_tipo_precio, 
                   v.fecha_venta, v.total, 
                   p.nombres || ' ' || p.apellidos as nombre_paciente
            FROM ventas v
            LEFT JOIN pacientes p ON v.id_paciente = p.id_paciente
            WHERE v.numero_venta LIKE ? OR 
                  CAST(v.id_paciente AS TEXT) LIKE ? OR
                  p.nombres LIKE ? OR p.apellidos LIKE ?
            ORDER BY v.id_venta DESC
        """;

        try {
            con = cn.conectar();
            ps = con.prepareStatement(sql);
            
            String likeCriterio = "%" + criterio + "%";
            ps.setString(1, likeCriterio);
            ps.setString(2, likeCriterio);
            ps.setString(3, likeCriterio);
            ps.setString(4, likeCriterio);
            
            rs = ps.executeQuery();

            while (rs.next()) {
                Venta sale = new Venta();
                sale.setIdVenta(rs.getInt("id_venta"));
                sale.setNumeroVenta(rs.getString("numero_venta"));
                sale.setIdPaciente(rs.getInt("id_paciente"));
                sale.setIdTipoPrecio(rs.getInt("id_tipo_precio"));
                sale.setFechaVenta(rs.getTimestamp("fecha_venta"));
                sale.setTotal(rs.getDouble("total"));
                lista.add(sale);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al buscar ventas: " + e.getMessage());
        }
        return lista;
    }

    /**
     * Obtiene una venta por su ID.
     */
    public Venta obtenerVentaPorId(int idVenta) {
        String sql = "SELECT * FROM ventas WHERE id_venta = ?";
        Venta sale = null;

        try {
            con = cn.conectar();
            ps = con.prepareStatement(sql);
            ps.setInt(1, idVenta);
            rs = ps.executeQuery();

            if (rs.next()) {
                sale = new Venta();
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
    public List<Venta> listarVentas() {
        List<Venta> lista = new ArrayList<>();

        String sql = """
            SELECT v.id_venta, v.numero_venta, v.id_paciente, v.id_tipo_precio, 
                   v.fecha_venta, v.total,
                   p.nombres || ' ' || p.apellidos as nombre_paciente,
                   tp.nombre as tipo_precio
            FROM ventas v
            LEFT JOIN pacientes p ON v.id_paciente = p.id_paciente
            LEFT JOIN tipos_precio tp ON v.id_tipo_precio = tp.id_tipo_precio
            ORDER BY v.id_venta DESC
        """;

        try {
            con = cn.conectar();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Venta sale = new Venta();
                sale.setIdVenta(rs.getInt("id_venta"));
                sale.setNumeroVenta(rs.getString("numero_venta"));
                sale.setIdPaciente(rs.getInt("id_paciente"));
                sale.setIdTipoPrecio(rs.getInt("id_tipo_precio"));
                sale.setFechaVenta(rs.getTimestamp("fecha_venta"));
                sale.setTotal(rs.getDouble("total"));
                lista.add(sale);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al listar ventas: " + e.getMessage());
        }
        return lista;
    }

    /**
     * Elimina una venta por su ID (con eliminación en cascada de detalles).
     */
    public boolean eliminarVenta(int idVenta) {
        Connection conn = null;
        try {
            conn = cn.conectar();
            conn.setAutoCommit(false); // Iniciar transacción
            
            // Primero eliminar detalles de venta de tratamientos
            String sqlDetalleTratamientos = "DELETE FROM detalle_venta_tratamientos WHERE id_venta = ?";
            try (PreparedStatement ps1 = conn.prepareStatement(sqlDetalleTratamientos)) {
                ps1.setInt(1, idVenta);
                ps1.executeUpdate();
            }
            
            // Eliminar detalles de venta de insumos
            String sqlDetalleInsumos = "DELETE FROM detalle_venta_insumos WHERE id_venta = ?";
            try (PreparedStatement ps2 = conn.prepareStatement(sqlDetalleInsumos)) {
                ps2.setInt(1, idVenta);
                ps2.executeUpdate();
            }
            
            // Finalmente eliminar la venta
            String sqlVenta = "DELETE FROM ventas WHERE id_venta = ?";
            try (PreparedStatement ps3 = conn.prepareStatement(sqlVenta)) {
                ps3.setInt(1, idVenta);
                int result = ps3.executeUpdate();
                
                conn.commit(); // Confirmar transacción
                return result > 0;
            }
            
        } catch (SQLException e) {
            try {
                if (conn != null) {
                    conn.rollback(); // Revertir en caso de error
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            JOptionPane.showMessageDialog(null, "Error al eliminar venta: " + e.getMessage());
            return false;
        } finally {
            try {
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
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
     * Genera un número de venta automático.
     */
    public String generarNumeroVenta() {
        try {
            // Contar ventas del día actual
            String sql = "SELECT COUNT(*) + 1 as secuencia FROM ventas "
                    + "WHERE DATE(fecha_venta) = CURRENT_DATE";
            con = cn.conectar();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            int secuencia = 1;
            if (rs.next()) {
                secuencia = rs.getInt("secuencia");
            }

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            String fecha = LocalDateTime.now().format(formatter);

            return String.format("VEN-%s-%03d", fecha, secuencia);

        } catch (SQLException e) {
            // Número simple basado en timestamp
            return "VEN-" + System.currentTimeMillis();
        }
    }

    /**
     * Obtiene estadísticas de ventas.
     */
    public double obtenerTotalVentasPorPeriodo(String fechaInicio, String fechaFin) {
        String sql = "SELECT COALESCE(SUM(total), 0) FROM ventas WHERE fecha_venta BETWEEN ? AND ?";
        
        try {
            con = cn.conectar();
            ps = con.prepareStatement(sql);
            ps.setString(1, fechaInicio);
            ps.setString(2, fechaFin);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                return rs.getDouble(1);
            }
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al obtener total de ventas: " + e.getMessage());
        }
        return 0.0;
    }

    /**
     * Registra un detalle de venta de tratamiento.
     */
    public boolean registrarDetalleTratamiento(int idVenta, int idTratamiento, int cantidad, double precioUnitario, double total) {
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
    public boolean registrarDetalleInsumo(int idVenta, int idInsumo, double cantidad, double precioUnitario, double total) {
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
    
    private void cerrarRecursos() {
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
    
    public List<DetalleVentaTratamiento> obtenerDetallesTratamientosPorVenta(int idVenta) {
        List<DetalleVentaTratamiento> detalles = new ArrayList<>();
        String sql = """
        SELECT dvt.*, t.nombre as nombre_tratamiento 
        FROM detalle_venta_tratamientos dvt
        LEFT JOIN tratamientos t ON dvt.id_tratamiento = t.id_tratamiento
        WHERE dvt.id_venta = ?
        ORDER BY dvt.id_detalle_venta_tratamiento
    """;

        try {
            con = cn.conectar();
            ps = con.prepareStatement(sql);
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
                detalle.setNombreTratamiento(rs.getString("nombre_tratamiento"));
                detalles.add(detalle);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al obtener detalles de tratamientos: " + e.getMessage());
        } finally {
            cerrarRecursos();
        }
        return detalles;
    }

    public List<DetalleVentaInsumo> obtenerDetallesInsumosPorVenta(int idVenta) {
        List<DetalleVentaInsumo> detalles = new ArrayList<>();
        String sql = """
        SELECT dvi.*, i.nombre as nombre_insumo 
        FROM detalle_venta_insumos dvi
        LEFT JOIN insumos i ON dvi.id_insumo = i.id_insumo
        WHERE dvi.id_venta = ?
        ORDER BY dvi.id_detalle_venta_insumo
    """;

        try {
            con = cn.conectar();
            ps = con.prepareStatement(sql);
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
                detalle.setNombreInsumo(rs.getString("nombre_insumo"));
                detalles.add(detalle);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al obtener detalles de insumos: " + e.getMessage());
        } finally {
            cerrarRecursos();
        }
        return detalles;
    }
}