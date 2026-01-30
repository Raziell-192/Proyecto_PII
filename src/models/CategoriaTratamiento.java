package models;

/**
 * Modelo que representa una categoría de tratamientos odontológicos.
 * <p>
 * Se utiliza para clasificar los tratamientos en grupos, facilitando su
 * organización y gestión dentro del sistema.
 * </p>
 *
 * Corresponde a la tabla {@code categorias_tratamiento} de la base de datos.
 *
 * @author Jakim
 */
public class CategoriaTratamiento {

    private int idCategoria;
    private String nombre;
    private String codigo;

    public CategoriaTratamiento() {
    }

    public CategoriaTratamiento(int idCategoria, String nombre, String codigo) {
        this.idCategoria = idCategoria;
        this.nombre = nombre;
        this.codigo = codigo;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

}
