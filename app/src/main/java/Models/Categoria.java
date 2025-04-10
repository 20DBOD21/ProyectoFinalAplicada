package Models;

public class Categoria {
    protected String nombre;
    protected String descripcion;
    protected String icono;

    public Categoria() {
    }

    public Categoria(String nombre) {
        this.nombre = nombre;
        descripcion = "";
        icono = "";
    }

    public Categoria(String nombre, String descripcion, String icono) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.icono = icono;
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

    public String getIcono() {
        return icono;
    }

    public void setIcono(String icono) {
        this.icono = icono;
    }
}
