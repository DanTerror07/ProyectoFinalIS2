package mx.uaemex.fi.ingsoft2.parvadas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import mx.uaemex.fi.ingsoft2.parvadas.entity.MortalidadEntity;
import mx.uaemex.fi.ingsoft2.parvadas.entity.ParvadaEntity;
import mx.uaemex.fi.ingsoft2.parvadas.entity.VacunacionEntity;
import mx.uaemex.fi.ingsoft2.parvadas.repository.MortalidadRepository;
import mx.uaemex.fi.ingsoft2.parvadas.repository.ParvadaRepository;
import mx.uaemex.fi.ingsoft2.parvadas.repository.VacunacionRepository;

@Service
public class OperacionesService {

    @Autowired
    private VacunacionRepository vacunacionRepository;

    @Autowired
    private MortalidadRepository mortalidadRepository;

    @Autowired
    private ParvadaRepository parvadaRepository;

    // Caso de Uso: Registrar Vacunación
    public VacunacionEntity registrarVacunacion(VacunacionEntity vacunacion) {
        return vacunacionRepository.save(vacunacion);
    }

    // Caso de Uso: Registrar Diario de Mortalidad (Con lógica de descuento)
    @Transactional
    public MortalidadEntity registrarMortalidad(MortalidadEntity mortalidad) throws Exception {
        // 1. Validar que la parvada exista
        ParvadaEntity parvada = parvadaRepository.getById(mortalidad.getParvada().getIdParvada())
                .orElseThrow(() -> new Exception("La parvada especificada no existe."));

        // 2. Validar que no se descuenten más aves de las que hay vivas
        if (parvada.getCantidadAves() < mortalidad.getBajas()) {
            throw new Exception("Error: El numero de bajas supera la cantidad de aves vivas en la parvada.");
        }

        // 3. Regla de negocio: Actualizar el inventario de la parvada
        int nuevaCantidad = parvada.getCantidadAves() - mortalidad.getBajas();
        parvada.setCantidadAves(nuevaCantidad);
        parvadaRepository.save(parvada); // Guarda la parvada actualizada

        // 4. Guardar el registro en el histórico de mortalidad
        mortalidad.setParvada(parvada);
        return mortalidadRepository.save(mortalidad);
    }
}