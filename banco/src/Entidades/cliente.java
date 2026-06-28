package entidades;

public class cliente extends Abstracta {

    private int id;
    private String nombre;
    private String telefono;
    private String correo;

    // 1. Constructor vacío
    public cliente() {
    }

    // 2. Constructor con parámetros
    public cliente(int id, String nombre, String telefono, String correo) {
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.correo = correo;
    }

    // Getters y Setters
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

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    // Implementación obligatoria del método de la clase Abstracta
    @Override
    public String obtenerDetalles() {
        return "Cliente: " + nombre + " - Correo: " + correo;
    }
}
