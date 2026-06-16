package mx.uaemex.fi.ingsoft2.parvadas.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "usuarios")
public class UsuarioEntity {

    @Id
    @Column(name = "id_usuario")
    private int idUsuario;

    @Column(name = "primer_apellido", length = 50, nullable = false)
    private String primerApellido;

    @Column(name = "segundo_apellido", length = 50, nullable = true) // Opcional según PDF
    private String segundoApellido;

    @Column(name = "nombres", length = 60, nullable = false)
    private String nombres;

    @Column(name = "correo", length = 50, nullable = false, unique = true)
    private String correo;

    @Column(name = "celular", length = 15, nullable = false, unique = true)
    private String celular;

    @Column(name = "contrasena", length = 100, nullable = false)
    private String contrasena;

    @Column(name = "activo", nullable = false)
    private boolean activo = true; // Por defecto inicia activo

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_registro", nullable = false, updatable = false)
    private Date fechaRegistro = new Date(); // Almacena la fecha del registro

    // CORRECCIÓN PARA EL CASO DE USO 1.0 (Asignar más de un tipo de usuario)
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "usuarios_tipos_de_usuario",
        joinColumns = @JoinColumn(name = "id_usuario"),
        inverseJoinColumns = @JoinColumn(name = "id_tipo_usuario")
    )
    private List<TipoDeUsuarioEntity> tiposUsuario = new ArrayList<>();

    // --- GETTERS Y SETTERS ---
    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }

    public String getPrimerApellido() { return primerApellido; }
    public void setPrimerApellido(String primerApellido) { this.primerApellido = primerApellido; }

    public String getSegundoApellido() { return segundoApellido; }
    public void setSegundoApellido(String segundoApellido) { this.segundoApellido = segundoApellido; }

    public String getNombres() { return nombres; }
    public void setNombres(String nombres) { this.nombres = nombres; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getCelular() { return celular; }
    public void setCelular(String celular) { this.celular = celular; }

    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }

    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }

    public Date getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(Date fechaRegistro) { this.fechaRegistro = fechaRegistro; }

    public List<TipoDeUsuarioEntity> getTiposUsuario() { return tiposUsuario; }
    public void setTiposUsuario(List<TipoDeUsuarioEntity> tiposUsuario) { this.tiposUsuario = tiposUsuario; }
}