package models;

/**
 *
 * @author Raz
 */
public class Paciente {
    private int idPaciente;
    private String nombres;
    private String apellidos;
    private String telefono;
    private String tipoAfiliacion;
    
    public Paciente() {}
    
    public Paciente(String nombres, String apellidos, String telefono, String tipoAfiliacion) {
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.telefono = telefono;
        this.tipoAfiliacion = tipoAfiliacion;
    }
    
    public int getIdPaciente() {
        return idPaciente;
    }
    
    public void setIdPaciente(int idPaciente) {
        this.idPaciente = idPaciente;
    }
    
    public String getNombres() {
        return nombres;
    }
    
    public void setNombres(String nombres) {
        this.nombres = nombres;
    }
    
    public String getApellidos() {
        return apellidos;
    }
    
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }
    
    public String getTelefono() {
        return telefono;
    }
    
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    
    public String getTipoAfiliacion() {
        return tipoAfiliacion;
    }
    
    public void setTipoAfiliacion(String tipoAfiliacion) {
        this.tipoAfiliacion = tipoAfiliacion;
    }
    
    @Override
    public String toString() {
        return nombres + " " + apellidos;
    }
}