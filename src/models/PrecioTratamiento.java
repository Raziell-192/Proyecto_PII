package models;

/**
 * Modelo que representa el precio de un tratamiento odontológico.
 * <p>
 * Permite asociar un tratamiento con un tipo de precio y su valor
 * correspondiente.
 * </p>
 *
 * Corresponde a la tabla {@code precios_tratamiento}.
 *
 * @author Jakim
 */
public class PrecioTratamiento {

    private int idPrecio;
    private int idTratamiento;
    private int idTipoPrecio;
    private double precio;

    public PrecioTratamiento() {
    }

    public PrecioTratamiento(int idPrecio, int idTratamiento, int idTipoPrecio, double precio) {
        this.idPrecio = idPrecio;
        this.idTratamiento = idTratamiento;
        this.idTipoPrecio = idTipoPrecio;
        this.precio = precio;
    }

    public int getIdPrecio() {
        return idPrecio;
    }

    public void setIdPrecio(int idPrecio) {
        this.idPrecio = idPrecio;
    }

    public int getIdTratamiento() {
        return idTratamiento;
    }

    public void setIdTratamiento(int idTratamiento) {
        this.idTratamiento = idTratamiento;
    }

    public int getIdTipoPrecio() {
        return idTipoPrecio;
    }

    public void setIdTipoPrecio(int idTipoPrecio) {
        this.idTipoPrecio = idTipoPrecio;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

}
