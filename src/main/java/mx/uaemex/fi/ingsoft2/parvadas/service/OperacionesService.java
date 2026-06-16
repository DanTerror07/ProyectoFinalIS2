package mx.uaemex.fi.ingsoft2.parvadas.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import mx.uaemex.fi.ingsoft2.parvadas.entity.*;
import mx.uaemex.fi.ingsoft2.parvadas.repository.*;
import mx.uaemex.fi.ingsoft2.parvadas.crud.ParvadaCrud; // Para usar consultas específicas si las requieres

@Service
public class OperacionesService {

    @Autowired private VacunacionRepository vacunacionRepository;
    @Autowired private MortalidadRepository mortalidadRepository;
    @Autowired private MedicacionRepository medicacionRepository;
    @Autowired private SalidaRepository salidaRepository;
    @Autowired private ParvadaRepository parvadaRepository;

    // 1. Caso de Uso: Consulta de parvadas asignadas (Filtrado por Responsable Activo)
    public List<ParvadaEntity> listarAsignadas(int idResponsable) {
        // Filtramos de la lista general aquellas que corresponden al usuario y siguen vigentes
        return parvadaRepository.getAll().stream()
                .filter(p -> p.getResponsable().getIdUsuario() == idResponsable && p.isActiva())
                .toList();
    }

    // 2. Caso de Uso: Registrar Vacunación
    public VacunacionEntity registrarVacunacion(VacunacionEntity vacunacion) {
        return vacunacionRepository.save(vacunacion);
    }

    // 3. Caso de Uso: Registrar Medicación
    public MedicacionEntity registrarMedicacion(MedicacionEntity medicacion) {
        return medicacionRepository.save(medicacion);
    }

    // 4. Caso de Uso: Registrar Diario de Mortalidad
    @Transactional
    public MortalidadEntity registrarMortalidad(MortalidadEntity mortalidad) throws Exception {
        ParvadaEntity parvada = parvadaRepository.getById(mortalidad.getParvada().getIdParvada())
                .orElseThrow(() -> new Exception("La parvada especificada no existe."));

        if (!parvada.isActiva()) {
            throw new Exception("Error: No se pueden registrar movimientos en una parvada dada de baja.");
        }

        if (parvada.getCantidadAves() < mortalidad.getBajas()) {
            throw new Exception("Error: El numero de bajas supera la cantidad de aves vivas.");
        }

        parvada.setCantidadAves(parvada.getCantidadAves() - mortalidad.getBajas());
        parvadaRepository.save(parvada);

        mortalidad.setParvada(parvada);
        return mortalidadRepository.save(mortalidad);
    }

    // 5. Caso de Uso: Registro de baja de aves por salida (Descuento y Cierre automático)
    @Transactional
    public SalidaEntity registrarSalida(SalidaEntity salida) throws Exception {
        ParvadaEntity parvada = parvadaRepository.getById(salida.getParvada().getIdParvada())
                .orElseThrow(() -> new Exception("La parvada especificada no existe."));

        if (!parvada.isActiva()) {
            throw new Exception("Error: No se pueden registrar salidas de una parvada inactiva.");
        }

        if (parvada.getCantidadAves() < salida.getCantidadSalida()) {
            throw new Exception("Error: La cantidad de salida supera las aves disponibles en la parvada.");
        }

        int remanente = parvada.getCantidadAves() - salida.getCantidadSalida();
        parvada.setCantidadAves(remanente);

        // Si ya no quedan aves vivas en el lote, se da de baja la parvada automáticamente
        if (remanente == 0) {
            parvada.setActiva(false);
        }
        parvadaRepository.save(parvada);

        salida.setParvada(parvada);
        return salidaRepository.save(salida);
    }
}