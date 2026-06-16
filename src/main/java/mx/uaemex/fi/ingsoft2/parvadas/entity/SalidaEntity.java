package mx.uaemex.fi.ingsoft2.parvadas.entity;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "registro_salidas")
public class SalidaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_salida")
    private int idSalida;

    @Column(name = "cantidad_salida", nullable = false)
    private int cantidadSalida; // Aves que salen a la venta/descarte

    @Column(name = "motivo", length = 50, nullable = false) // "Venta", "Descarte", etc.
    private String motivo;

    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_salida", nullable = false)
    private Date fechaSalida = new Date();

    @ManyToOne
    @JoinColumn(name = "id_parvada", nullable = false)
    private ParvadaEntity parvada;

    // Getters y Setters
    public int getIdSalida() { return idSalida; }
    public void setIdSalida(int idSalida) { this.idSalida = idSalida; }
    public int getCantidadSalida() { return cantidadSalida; }
    public void setCantidadSalida(int cantidadSalida) { this.cantidadSalida = cantidadSalida; }
    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }
    public Date getFechaSalida() { return fechaSalida; }
    public void setFechaSalida(Date fechaSalida) { this.fechaSalida = fechaSalida; }
    public ParvadaEntity getParvada() { return parvada; }
    public void setParvada(ParvadaEntity parvada) { this.parvada = parvada; }
}