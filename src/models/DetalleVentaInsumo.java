package models;

/**
 * Modelo que representa el detalle de insumos vendidos en una venta.
 * Corresponde a la tabla detalle_venta_insumos.
 *
 * @author Jakim
 */
public class DetalleVentaInsumo {

    private int idDetalleVentaInsumo;
    private int idVenta;
    private int idInsumo;
    private double cantidad;
    private double precioUnitario;
    private double total;

    public DetalleVentaInsumo() {
    }

    public DetalleVentaInsumo(int idDetalleVentaInsumo, int idVenta, int idInsumo, double cantidad, double precioUnitario, double total) {
        this.idDetalleVentaInsumo = idDetalleVentaInsumo;
        this.idVenta = idVenta;
        this.idInsumo = idInsumo;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.total = total;
    }

    public int getIdDetalleVentaInsumo() {
        return idDetalleVentaInsumo;
    }

    public void setIdDetalleVentaInsumo(int idDetalleVentaInsumo) {
        this.idDetalleVentaInsumo = idDetalleVentaInsumo;
    }

    public int getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    public int getIdInsumo() {
        return idInsumo;
    }

    public void setIdInsumo(int idInsumo) {
        this.idInsumo = idInsumo;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
