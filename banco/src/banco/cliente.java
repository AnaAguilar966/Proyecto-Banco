package banco;

public class cliente extends Persona {

    private String telefono;
    private String correo;

    public cliente() {
    }

    public cliente(int id, String nombre, String telefono, String correo) {
        super(id, nombre);
        this.telefono = telefono;
        this.correo = correo;
    }

    @Override
    public void mostrarDatos() {
        System.out.println("Cliente: " + nombre);
        System.out.println("Teléfono: " + telefono);
        System.out.println("Correo: " + correo);
    }

    public String getNombre() {
        return nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getCorreo() {
        return correo;
    }
}