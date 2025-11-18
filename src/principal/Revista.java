package principal;

public class Revista {

    private String codigo, titulo, editorial, numEdicion, mesPublicacion, tema;

    public Revista(String codigo, String titulo, String editorial) {
        this.codigo = codigo;
        this.titulo = titulo;
        this.editorial = editorial;
    }
    
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    
    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }
    
    public String getNumEdicion() {
        return numEdicion;
    }

    public void setNumEdicion(String numEdicion) {
        this.numEdicion = numEdicion;
    }

    public String getMesPublicacion() {
        return mesPublicacion;
    }

    public void setMesPublicacion(String mesPublicacion) {
        this.mesPublicacion = mesPublicacion;
    }

    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }
    
    @Override
    public String toString() {
        return "Revista{" + "codigo=" + codigo + ", titulo=" + titulo + ", editorial="
                + editorial + '}';
    }
    
//    @Override
//    public String toString() {
//        return "Revista{" + "codigo=" + codigo + ", titulo=" + titulo + ", editorial="
//                + editorial + "numero de edición="+ numEdicion + "mes de publicación=" 
//                + mesPublicacion + "tema=" + tema +'}';
//    }
    
}
