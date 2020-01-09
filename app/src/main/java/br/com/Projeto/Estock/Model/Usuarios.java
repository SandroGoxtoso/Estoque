package br.com.Projeto.Estock.Model;

public class Usuarios {
    private String id;
    private String usuario;
    private String email;
    private String foto;
    private String permissao;

    public Usuarios() {
    }

    public Usuarios(String id, String usuario, String email, String foto, String permissao) {
        this.id = id;
        this.usuario = usuario;
        this.foto = foto;
        this.email = email;
        this.permissao = permissao;
    }

    public String getPermissao() {
        return permissao;
    }

    public void setPermissao(String permissao) {
        this.permissao = permissao;
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
