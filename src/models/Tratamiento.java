package models;

/**
 * Modelo que representa un tratamiento odontológico.
 * <p>
 * Esta clase se utiliza para mapear la tabla {@code tratamientos} de la base de
 * datos y encapsula la información básica de cada tratamiento que ofrece la
 * clínica de odontología.
 * </p>
 *
 * Contiene datos como:
 * <ul>
 * <li>Identificador del tratamiento</li>
 * <li>Categoría a la que pertenece</li>
 * <li>Código del tratamiento</li>
 * <li>Nombre</li>
 * <li>Descripción</li>
 * </ul>
 *
 * @author Jakim
 */
public class Tratamiento {

    private int idTratamiento;
    private int idCategoria;
    private String codigo;
    private String nombre;
    private String descripcion;

    public Tratamiento() {
    }

    public Tratamiento(int idTratamiento, int idCategoria, String codigo, String nombre, String descripcion) {
        this.idTratamiento = idTratamiento;
        this.idCategoria = idCategoria;
        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public int getIdTratamiento() {
        return idTratamiento;
    }

    public void setIdTratamiento(int idTratamiento) {
        this.idTratamiento = idTratamiento;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
