package mx.uaemex.fi.ingsoft2.parvadas.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "gallineros")
public class GallineroEntity {

    @Id
    @Column(name = "id_gallinero")
    private int idGallinero;

    @Column(name = "nombre", length = 50, nullable = false, unique = true)
    private String nombre;

    @Column(name = "capacidad_maxima", nullable = false)
    private int capacidadMaxima;

    @Column(name = "sanitizado", nullable = false)
    private boolean sanitizado = true; // Atributo clave para el Caso de Uso de sanitización

    @Column(name = "activo", nullable = false)
    private boolean activo = true; // Atributo necesario para el "Dar de baja un gallinero" de forma lógica

    // RELACIÓN: Mapea qué tipo de ave aloja este gallinero
    @ManyToOne
    @JoinColumn(name = "id_tipo_ave", nullable = false)
    private TipoAveEntity tipoAve;

    // --- GETTERS Y SETTERS ---
    public int getIdGallinero() { return idGallinero; }
    public void setIdGallinero(int idGallinero) { this.idGallinero = idGallinero; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public int getCapacidadMaxima() { return capacidadMaxima; }
    public void setCapacidadMaxima(int capacidadMaxima) { this.capacidadMaxima = capacidadMaxima; }

    public boolean isSanitizado() { return sanitizado; }
    public void setSanitizado(boolean sanitizado) { this.sanitizado = sanitizado; }

    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }

    public TipoAveEntity getTipoAve() { return tipoAve; }
    public void setTipoAve(TipoAveEntity tipoAve) { this.tipoAve = tipoAve; }
}