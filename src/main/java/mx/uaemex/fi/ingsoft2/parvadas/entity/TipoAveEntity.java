package mx.uaemex.fi.ingsoft2.parvadas.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.util.Date;

@Entity
@Table(name = "tipos_de_ave")
public class TipoAveEntity {

    @Id
    @Column(name = "id_tipo_ave")
    private int idTipoAve;

    @Column(name = "nombre", length = 50, nullable = false, unique = true)
    private String nombre;

    @Column(name = "descripcion", length = 150, nullable = false) // Requerido según CU
    private String descripcion;

    @Column(name = "activo", nullable = false)
    private boolean activo = true; // Baja lógica

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_registro", nullable = false, updatable = false)
    private Date fechaRegistro = new Date(); // Fecha automática

    // --- GETTERS Y SETTERS ---
    public int getIdTipoAve() { return idTipoAve; }
    public void setIdTipoAve(int idTipoAve) { this.idTipoAve = idTipoAve; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }

    public Date getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(Date fechaRegistro) { this.fechaRegistro = fechaRegistro; }
}