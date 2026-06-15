package mx.uaemex.fi.ingsoft2.parvadas.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tipos_de_usurio")
public class TipoDeUsuarioEntity {

    @Id
    @Column(name = "id_tipo_usuario")
    private int idTipoUsuario;

    @Column(name = "nombre", length = 25, nullable = false)
    private String nombre;

    // --- GETTERS Y SETTERS ---
    public int getIdTipoUsuario() {
        return idTipoUsuario;
    }

    public void setIdTipoUsuario(int idTipoUsuario) {
        this.idTipoUsuario = idTipoUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}