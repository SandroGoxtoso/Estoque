package br.com.Projeto.Estock.Model;

public class Usuarios {
    private String id;
    private String usuario;
    private String email;
    private String foto;

    public Usuarios() {
    }

    public Usuarios(String id, String usuario, String email, String foto) {
        this.id = id;
        this.usuario = usuario;
        this.foto = foto;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}
