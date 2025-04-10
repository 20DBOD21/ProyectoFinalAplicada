package Models;

public class Usuario {
    protected String nombre;
    protected String apellido;
    protected String nickname;
    protected String password;
    protected String email;
    protected String rol;
    private boolean sincNube;

    public Usuario() {
    }

    //CONSTRUCTOR PARA VALIDACIÓN
    public Usuario(String nickname, String password, String rol) {
        this.nickname = nickname;
        this.password = password;
        this.rol = rol;
    }

    public Usuario(String nombre, String apellido, String nickname, String password, String email, String rol) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.nickname = nickname;
        this.password = password;
        this.email = email;
        this.rol = rol;
    }

    public Usuario(String nombre, String apellido, String nickname, String password, String email, String rol, boolean sincNube) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.nickname = nickname;
        this.password = password;
        this.email = email;
        this.rol = rol;
        this.sincNube = sincNube;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public boolean isSincNube() {
        return sincNube;
    }

    public void setSincNube(boolean sincNube) {
        this.sincNube = sincNube;
    }
}
