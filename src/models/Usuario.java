package models;

/**
 *
 * @author jakim
 */
public class Usuario {
    private int id;
    private String nombre;
    private String apellido;
    private String nombreDeUsuario;
    private String email;
    private String contrasenya;
    private String rol;
//    private String created;
//    private String update;

    public Usuario() {
    }

    public Usuario(String nombre, String apellido, String nombreDeUsuario, String email, String contrasenya, String rol/*, String created, String update*/) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.nombreDeUsuario = nombreDeUsuario;
        this.email = email;
        this.contrasenya = contrasenya;
        this.rol = rol;
//        this.created = created;
//        this.update = update;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
    
    public String getNombreDeUsuario() {
        return nombreDeUsuario;
    }

    public void setNombreDeUsuario(String nombreDeUsuario) {
        this.nombreDeUsuario = nombreDeUsuario;
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

//    public String getCreated() {
//        return created;
//    }
//
//    public void setCreated(String created) {
//        this.created = created;
//    }
//
//    public String getUpdate() {
//        return update;
//    }
//
//    public void setUpdate(String update) {
//        this.update = update;
//    } 
    
}
