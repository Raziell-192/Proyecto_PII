package models;

/**
 *
 * @author jakim
 */
public class TipoPrecio {

    private int idTipoPrecio;
    private String nombre;
    private String codigo;

    public TipoPrecio() {
    }

    public TipoPrecio(int idTipoPrecio, String nombre, String codigo) {
        this.idTipoPrecio = idTipoPrecio;
        this.nombre = nombre;
        this.codigo = codigo;
    }

    public int getIdTipoPrecio() {
        return idTipoPrecio;
    }

    public void setIdTipoPrecio(int idTipoPrecio) {
        this.idTipoPrecio = idTipoPrecio;
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
