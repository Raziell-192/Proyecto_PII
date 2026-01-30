package models;

/**
 * Modelo que representa el detalle de un tratamiento vendido en una venta.
 * Corresponde a la tabla detalle_venta_tratamientos.
 *
 * @author Jakim
 */
public class DetalleVentaTratamiento {

    private int idDetalleVentaTratamiento;
    private int idVenta;
    private int idTratamiento;
    private int cantidad;
    private double precioUnitario;
    private double total;

    public DetalleVentaTratamiento() {
    }

    public DetalleVentaTratamiento(int idDetalleVentaTratamiento, int idVenta, int idTratamiento, int cantidad, double precioUnitario, double total) {
        this.idDetalleVentaTratamiento = idDetalleVentaTratamiento;
        this.idVenta = idVenta;
        this.idTratamiento = idTratamiento;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.total = total;
    }

    public int getIdDetalleVentaTratamiento() {
        return idDetalleVentaTratamiento;
    }

    public void setIdDetalleVentaTratamiento(int idDetalleVentaTratamiento) {
        this.idDetalleVentaTratamiento = idDetalleVentaTratamiento;
    }

    public int getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    public int getIdTratamiento() {
        return idTratamiento;
    }

    public void setIdTratamiento(int idTratamiento) {
        this.idTratamiento = idTratamiento;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
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
