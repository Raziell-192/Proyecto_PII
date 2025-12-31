package models;

/**
 *
 * @author jakim
 */
public class Products {
    private int id_insumo;
    private int codigo;
    private String nombre;
    private String descripcion;
    private String presentacion;
    private double total_piezas;
    private double precio_unitario;

    public Products() {
    }

    public Products(int id_insumo, int codigo, String nombre, String descripcion, String presentacion, double total_piezas, double precio_unitario) {
        this.id_insumo = id_insumo;
        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.presentacion = presentacion;
        this.total_piezas = total_piezas;
        this.precio_unitario = precio_unitario;
    }

    public int getId_insumo() {
        return id_insumo;
    }

    public void setId_insumo(int id_insumo) {
        this.id_insumo = id_insumo;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
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

    public String getPresentacion() {
        return presentacion;
    }

    public void setPresentacion(String presentacion) {
        this.presentacion = presentacion;
    }

    public double getTotal_piezas() {
        return total_piezas;
    }

    public void setTotal_piezas(double total_piezas) {
        this.total_piezas = total_piezas;
    }

    public double getPrecio_unitario() {
        return precio_unitario;
    }

    public void setPrecio_unitario(double precio_unitario) {
        this.precio_unitario = precio_unitario;
    }
    
    @Override
    public String toString() {
        return nombre + " (Código: " + codigo + ")";
    }
}