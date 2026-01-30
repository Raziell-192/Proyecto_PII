package models;

import java.sql.Timestamp;

/**
 * Modelo que representa una cita médica.
 *
 * @author Jakim
 */
public class Cita {

    private int idCita;
    private int idPaciente;
    private int idTratamiento;
    private Timestamp fechaHora;

    public Cita() {
    }

    public Cita(int idCita, int idPaciente, int idTratamiento, Timestamp fechaHora) {
        this.idCita = idCita;
        this.idPaciente = idPaciente;
        this.idTratamiento = idTratamiento;
        this.fechaHora = fechaHora;
    }

    public int getIdCita() {
        return idCita;
    }

    public void setIdCita(int idCita) {
        this.idCita = idCita;
    }

    public int getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(int idPaciente) {
        this.idPaciente = idPaciente;
    }

    public int getIdTratamiento() {
        return idTratamiento;
    }

    public void setIdTratamiento(int idTratamiento) {
        this.idTratamiento = idTratamiento;
    }

    public Timestamp getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(Timestamp fechaHora) {
        this.fechaHora = fechaHora;
    }
}
