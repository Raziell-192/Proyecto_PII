package models;

import java.sql.Timestamp;

/**
 * Modelo que representa una venta realizada en la clínica de odontología.
 * <p>
 * Una venta puede incluir tratamientos y/o insumos asociados a un paciente bajo
 * un tipo de precio específico.
 * </p>
 *
 * Corresponde directamente a la tabla {@code ventas}.
 *
 * @author Jakim
 */
public class Venta {

    // Datos principales de la venta
    private int idVenta;
    private String numeroVenta;
    private int idPaciente;
    private int idTipoPrecio;
    private Timestamp fechaVenta;
    private double total;

    public Venta() {
    }

    public Venta(int idVenta, String numeroVenta, int idPaciente, int idTipoPrecio, Timestamp fechaVenta, double total) {
        this.idVenta = idVenta;
        this.numeroVenta = numeroVenta;
        this.idPaciente = idPaciente;
        this.idTipoPrecio = idTipoPrecio;
        this.fechaVenta = fechaVenta;
        this.total = total;
    }

    public int getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    public String getNumeroVenta() {
        return numeroVenta;
    }

    public void setNumeroVenta(String numeroVenta) {
        this.numeroVenta = numeroVenta;
    }

    public int getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(int idPaciente) {
        this.idPaciente = idPaciente;
    }

    public int getIdTipoPrecio() {
        return idTipoPrecio;
    }

    public void setIdTipoPrecio(int idTipoPrecio) {
        this.idTipoPrecio = idTipoPrecio;
    }

    public Timestamp getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(Timestamp fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

}
