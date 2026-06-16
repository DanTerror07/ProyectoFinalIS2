package mx.uaemex.fi.ingsoft2.parvadas.entity;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "registro_medicacion")
public class MedicacionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_medicacion")
    private int idMedicacion;

    @Column(name = "medicamento", length = 60, nullable = false)
    private String medicamento;

    @Column(name = "dosis", length = 30, nullable = false)
    private String dosis;

    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_medicacion", nullable = false)
    private Date fechaMedicacion = new Date();

    @ManyToOne
    @JoinColumn(name = "id_parvada", nullable = false)
    private ParvadaEntity parvada;

    // Getters y Setters
    public int getIdMedicacion() { return idMedicacion; }
    public void setIdMedicacion(int idMedicacion) { this.idMedicacion = idMedicacion; }
    public String getMedicamento() { return medicamento; }
    public void setMedicamento(String medicamento) { this.medicamento = medicamento; }
    public String getDosis() { return dosis; }
    public void setDosis(String dosis) { this.dosis = dosis; }
    public Date getFechaMedicacion() { return fechaMedicacion; }
    public void setFechaMedicacion(Date fechaMedicacion) { this.fechaMedicacion = fechaMedicacion; }
    public ParvadaEntity getParvada() { return parvada; }
    public void setParvada(ParvadaEntity parvada) { this.parvada = parvada; }
}