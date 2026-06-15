package mx.uaemex.fi.ingsoft2.parvadas.entity;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "registro_vacunacion")
public class VacunacionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Autoincrementable para el historial
    @Column(name = "id_vacunacion")
    private int idVacunacion;

    @Column(name = "nombre_vacuna", length = 50, nullable = false)
    private String nombreVacuna;

    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_aplicacion", nullable = false)
    private Date fechaAplicacion = new Date();

    @ManyToOne
    @JoinColumn(name = "id_parvada", nullable = false)
    private ParvadaEntity parvada;

    // --- GETTERS Y SETTERS ---
    public int getIdVacunacion() { return idVacunacion; }
    public void setIdVacunacion(int idVacunacion) { this.idVacunacion = idVacunacion; }

    public String getNombreVacuna() { return nombreVacuna; }
    public void setNombreVacuna(String nombreVacuna) { this.nombreVacuna = nombreVacuna; }

    public Date getFechaAplicacion() { return fechaAplicacion; }
    public void setFechaAplicacion(Date fechaAplicacion) { this.fechaAplicacion = fechaAplicacion; }

    public ParvadaEntity getParvada() { return parvada; }
    public void setParvada(ParvadaEntity parvada) { this.parvada = parvada; }
}