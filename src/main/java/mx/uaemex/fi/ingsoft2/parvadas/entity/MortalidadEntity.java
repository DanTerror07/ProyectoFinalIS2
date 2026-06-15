package mx.uaemex.fi.ingsoft2.parvadas.entity;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "diario_mortalidad")
public class MortalidadEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_mortalidad")
    private int idMortalidad;

    @Column(name = "bajas", nullable = false)
    private int bajas; // Cantidad de aves muertas

    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_reporte", nullable = false)
    private Date fechaReporte = new Date();

    @ManyToOne
    @JoinColumn(name = "id_parvada", nullable = false)
    private ParvadaEntity parvada;

    // --- GETTERS Y SETTERS ---
    public int getIdMortalidad() { return idMortalidad; }
    public void setIdMortalidad(int idMortalidad) { this.idMortalidad = idMortalidad; }

    public int getBajas() { return bajas; }
    public void setBajas(int bajas) { this.bajas = bajas; }

    public Date getFechaReporte() { return fechaReporte; }
    public void setFechaReporte(Date fechaReporte) { this.fechaReporte = fechaReporte; }

    public ParvadaEntity getParvada() { return parvada; }
    public void setParvada(ParvadaEntity parvada) { this.parvada = parvada; }
}