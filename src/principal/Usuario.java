package principal;

public class Usuario {

    private String nombre, apellidoP, apellidoM, matricula, licenciatura;
  
    public Usuario(String nombre, String apellidoP, String apellidoM, String matricula, String licenciatura){
        this.nombre = nombre;
        this.apellidoP = apellidoP;
        this.apellidoM = apellidoM;
        this.matricula = matricula;
        this.licenciatura = licenciatura;
    }
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoP() {
        return apellidoP;
    }

    public void setApellidoP(String apellidoP) {
        this.apellidoP = apellidoP;
    }

    public String getApellidoM() {
        return apellidoM;
    }

    public void setApellidoM(String apellidoM) {
        this.apellidoM = apellidoM;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getLicenciatura() {
        return licenciatura;
    }

    public void setLicenciatura(String licenciatura) {
        this.licenciatura = licenciatura;
    }

    @Override
    public String toString() {
        return "Usuario{" + "nombre=" + nombre + " " + apellidoP + " " + apellidoM + ", matricula=" + matricula + ", licenciatura=" + licenciatura + '}';
    }

}
