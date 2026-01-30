package models;

/**
 * Modelo que relaciona tratamientos con insumos requeridos.
 *
 * @author Jakim
 */
public class TratamientoInsumo {

    private int idTratamientoInsumo;
    private int idTratamiento;
    private int idInsumo;
    private double cantidadRequerida;

    public TratamientoInsumo() {
    }

    public TratamientoInsumo(int idTratamientoInsumo, int idTratamiento, int idInsumo, double cantidadRequerida) {
        this.idTratamientoInsumo = idTratamientoInsumo;
        this.idTratamiento = idTratamiento;
        this.idInsumo = idInsumo;
        this.cantidadRequerida = cantidadRequerida;
    }

    public int getIdTratamientoInsumo() {
        return idTratamientoInsumo;
    }

    public void setIdTratamientoInsumo(int idTratamientoInsumo) {
        this.idTratamientoInsumo = idTratamientoInsumo;
    }

    public int getIdTratamiento() {
        return idTratamiento;
    }

    public void setIdTratamiento(int idTratamiento) {
        this.idTratamiento = idTratamiento;
    }

    public int getIdInsumo() {
        return idInsumo;
    }

    public void setIdInsumo(int idInsumo) {
        this.idInsumo = idInsumo;
    }

    public double getCantidadRequerida() {
        return cantidadRequerida;
    }

    public void setCantidadRequerida(double cantidadRequerida) {
        this.cantidadRequerida = cantidadRequerida;
    }
}
