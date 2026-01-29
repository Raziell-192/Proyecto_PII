package models;

import java.sql.Timestamp;

/**
 *
 * @author jakim
 */
public class Cita {

    private int idCita;
    private int idPaciente;
    private int idTratamiento;
    private Timestamp fecha_hora;

    public Cita() {
    }

    public Cita(int idCita, int idPaciente, int idTratamiento, Timestamp fecha_hora) {
        this.idCita = idCita;
        this.idPaciente = idPaciente;
        this.idTratamiento = idTratamiento;
        this.fecha_hora = fecha_hora;
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

    public Timestamp getFecha_hora() {
        return fecha_hora;
    }

    public void setFecha_hora(Timestamp fecha_hora) {
        this.fecha_hora = fecha_hora;
    }

}
