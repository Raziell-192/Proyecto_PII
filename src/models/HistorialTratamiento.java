package models;

import java.sql.Date;

/**
 * Modelo que representa el historial de tratamientos realizados a un paciente.
 * Permite llevar el control clínico de los tratamientos aplicados.
 *
 * @author Jakim
 */
public class HistorialTratamiento {

    private int idHistorial;
    private int idPaciente;
    private int idDetalleVentaTratamiento;
    private Date fechaRealizacion;

    public HistorialTratamiento() {
    }

    public HistorialTratamiento(int idHistorial, int idPaciente, int idDetalleVentaTratamiento, Date fechaRealizacion) {
        this.idHistorial = idHistorial;
        this.idPaciente = idPaciente;
        this.idDetalleVentaTratamiento = idDetalleVentaTratamiento;
        this.fechaRealizacion = fechaRealizacion;
    }

    public int getIdHistorial() {
        return idHistorial;
    }

    public void setIdHistorial(int idHistorial) {
        this.idHistorial = idHistorial;
    }

    public int getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(int idPaciente) {
        this.idPaciente = idPaciente;
    }

    public int getIdDetalleVentaTratamiento() {
        return idDetalleVentaTratamiento;
    }

    public void setIdDetalleVentaTratamiento(int idDetalleVentaTratamiento) {
        this.idDetalleVentaTratamiento = idDetalleVentaTratamiento;
    }

    public Date getFechaRealizacion() {
        return fechaRealizacion;
    }

    public void setFechaRealizacion(Date fechaRealizacion) {
        this.fechaRealizacion = fechaRealizacion;
    }
}
