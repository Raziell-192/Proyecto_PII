package models;

/**
 *
 * @author jakim
 */
public class Employee {
    private int id;
    private String nombreCompleto;
    private String nombreDeUsuario;
    private String direccion;
    private String telefono;
    private String email;
    private String contrasenya;
    private String rol;
    private String created;
    private String update;

    public Employee() {
    }

    public Employee(int id, String nombreCompleto, String nombreDeUsuario, String direccion, String telefono, String email, String contrasenya, String rol, String created, String update) {
        this.id = id;
        this.nombreCompleto = nombreCompleto;
        this.nombreDeUsuario = nombreDeUsuario;
        this.direccion = direccion;
        this.telefono = telefono;
        this.email = email;
        this.contrasenya = contrasenya;
        this.rol = rol;
        this.created = created;
        this.update = update;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getNombreDeUsuario() {
        return nombreDeUsuario;
    }

    public void setNombreDeUsuario(String nombreDeUsuario) {
        this.nombreDeUsuario = nombreDeUsuario;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContrasenya() {
        return contrasenya;
    }

    public void setContrasenya(String contrasenya) {
        this.contrasenya = contrasenya;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getUpdate() {
        return update;
    }

    public void setUpdate(String update) {
        this.update = update;
    }
    
}

