package models;

import java.util.List;

/**
 *
 * @author jakim
 */
public class Sales {
    //Variables:
    private int idVenta;
    private String numeroVenta;
    private int idPaciente;
    private String nombrePaciente;
    private int idUsuario;
    private String nombreUsuario;
    private double tipoPrecio;
    private String fechaVenta;
    private double total;

    // Relaciones
    private List<String> tratamientos;
    private List<Double> insumos;

    public Sales() {
    }

    public Sales(int idVenta, String numeroVenta, int idPaciente, double tipoPrecio, String fechaVenta, double total) {
        this.idVenta = idVenta;
        this.numeroVenta = numeroVenta;
        this.idPaciente = idPaciente;
        this.tipoPrecio = tipoPrecio;
        this.fechaVenta = fechaVenta;
        this.total = total;
    }

    public Sales(int idVenta, String numeroVenta, int idPaciente, String nombrePaciente, int idUsuario, String nombreUsuario, double tipoPrecio, String fechaVenta, double total) {
        this.idVenta = idVenta;
        this.numeroVenta = numeroVenta;
        this.idPaciente = idPaciente;
        this.nombrePaciente = nombrePaciente;
        this.idUsuario = idUsuario;
        this.nombreUsuario = nombreUsuario;
        this.tipoPrecio = tipoPrecio;
        this.fechaVenta = fechaVenta;
        this.total = total;
    }

    public Sales(int idVenta, String numeroVenta, int idPaciente, String nombrePaciente, int idUsuario, String nombreUsuario, double tipoPrecio, String fechaVenta, double total, List<String> tratamientos, List<Double> insumos) {
        this.idVenta = idVenta;
        this.numeroVenta = numeroVenta;
        this.idPaciente = idPaciente;
        this.nombrePaciente = nombrePaciente;
        this.idUsuario = idUsuario;
        this.nombreUsuario = nombreUsuario;
        this.tipoPrecio = tipoPrecio;
        this.fechaVenta = fechaVenta;
        this.total = total;
        this.tratamientos = tratamientos;
        this.insumos = insumos;
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

    public String getNombrePaciente() {
        return nombrePaciente;
    }

    public void setNombrePaciente(String nombrePaciente) {
        this.nombrePaciente = nombrePaciente;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public double getTipoPrecio() {
        return tipoPrecio;
    }

    public void setTipoPrecio(double tipoPrecio) {
        this.tipoPrecio = tipoPrecio;
    }

    public String getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(String fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public List<String> getTratamientos() {
        return tratamientos;
    }

    public void setTratamientos(List<String> tratamientos) {
        this.tratamientos = tratamientos;
    }

    public List<Double> getInsumos() {
        return insumos;
    }

    public void setInsumos(List<Double> insumos) {
        this.insumos = insumos;
    }
    
}
