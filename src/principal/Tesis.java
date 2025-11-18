package principal;

public class Tesis {
    String codigo, titulo, autor, director, grado, universidad;

    public Tesis(String codigo, String titulo, String autor, String director, String grado, String universidad) {
        this.codigo = codigo;
        this.titulo = titulo;
        this.autor = autor;
        this.director = director;
        this.grado = grado;
        this.universidad = universidad;
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

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getGrado() {
        return grado;
    }

    public void setGrado(String grado) {
        this.grado = grado;
    }

    public String getUniversidad() {
        return universidad;
    }

    public void setUniversidad(String universidad) {
        this.universidad = universidad;
    }
    
    @Override
    public String toString() {
        return "Tesis{" + "codigo=" + codigo + ", titulo=" + titulo + ", autor=" + autor + ", director="
                + director + ", grado=" + grado + ", universidad=" + universidad + '}';
    }
    
}
