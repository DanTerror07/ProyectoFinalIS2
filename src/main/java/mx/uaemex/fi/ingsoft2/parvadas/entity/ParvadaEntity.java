package mx.uaemex.fi.ingsoft2.parvadas.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.util.Date;

@Entity
@Table(name = "parvadas")
public class ParvadaEntity {

    @Id
    @Column(name = "id_parvada")
    private int idParvada;

    @Column(name = "cantidad_aves", nullable = false)
    private int cantidadAves;

    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_nacimiento", nullable = false)
    private Date fechaNacimiento;

    @Column(name = "activa", nullable = false)
    private boolean activa = true; // Para el caso de uso "Dar de baja una parvada"

    // Relación 1: Muchas parvadas pueden ser del mismo Tipo de Ave
    @ManyToOne
    @JoinColumn(name = "id_tipo_ave", nullable = false)
    private TipoAveEntity tipoAve;

    // Relación 2: Muchas parvadas pueden alojarse en el mismo Gallinero
    @ManyToOne
    @JoinColumn(name = "id_gallinero", nullable = false)
    private GallineroEntity gallinero;

    // --- GETTERS Y SETTERS ---
    public int getIdParvada() { return idParvada; }
    public void setIdParvada(int idParvada) { this.idParvada = idParvada; }

    public int getCantidadAves() { return cantidadAves; }
    public void setCantidadAves(int cantidadAves) { this.cantidadAves = cantidadAves; }

    public Date getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(Date fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }

    public boolean isActiva() { return activa; }
    public void setActiva(boolean activa) { this.activa = activa; }

    public TipoAveEntity getTipoAve() { return tipoAve; }
    public void setTipoAve(TipoAveEntity tipoAve) { this.tipoAve = tipoAve; }

    public GallineroEntity getGallinero() { return gallinero; }
    public void setGallinero(GallineroEntity gallinero) { this.gallinero = gallinero; }
}