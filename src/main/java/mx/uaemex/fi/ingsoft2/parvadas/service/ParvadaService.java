package mx.uaemex.fi.ingsoft2.parvadas.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import mx.uaemex.fi.ingsoft2.parvadas.entity.ParvadaEntity;
import mx.uaemex.fi.ingsoft2.parvadas.entity.GallineroEntity;
import mx.uaemex.fi.ingsoft2.parvadas.entity.UsuarioEntity;
import mx.uaemex.fi.ingsoft2.parvadas.repository.ParvadaRepository;
import mx.uaemex.fi.ingsoft2.parvadas.repository.GallineroRepository;
import mx.uaemex.fi.ingsoft2.parvadas.repository.UsuarioRepository;

@Service
public class ParvadaService {

    @Autowired
    private ParvadaRepository parvadaRepository;

    @Autowired
    private GallineroRepository gallineroRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<ParvadaEntity> listarTodas() {
        return parvadaRepository.getAll();
    }

    public Optional<ParvadaEntity> buscarPorId(int id) {
        return parvadaRepository.getById(id);
    }

    public ParvadaEntity guardar(ParvadaEntity parvada) throws Exception {
        GallineroEntity gallinero = gallineroRepository.getById(parvada.getGallinero().getIdGallinero())
                .orElseThrow(() -> new Exception("Error: El gallinero especificado no existe."));

        usuarioRepository.getById(parvada.getResponsable().getIdUsuario())
                .orElseThrow(() -> new Exception("Error: El usuario responsable especificado no existe."));

        if (parvada.getCantidadAves() > gallinero.getCapacidadMaxima()) {
            throw new Exception("Error de sobrepoblacion: Supera la capacidad del gallinero.");
        }

        if (parvada.getTipoAve().getIdTipoAve() != gallinero.getTipoAve().getIdTipoAve()) {
            throw new Exception("Error de consistencia: El tipo de ave no corresponde al gallinero.");
        }

        return parvadaRepository.save(parvada);
    }

    public ParvadaEntity actualizar(int id, ParvadaEntity datosNuevos) throws Exception {
        ParvadaEntity parvadaExistente = parvadaRepository.getById(id)
                .orElseThrow(() -> new Exception("Error: La parvada especificada no existe."));

        GallineroEntity gallinero = gallineroRepository.getById(datosNuevos.getGallinero().getIdGallinero())
                .orElseThrow(() -> new Exception("Error: El gallinero especificado no existe."));

        UsuarioEntity responsable = usuarioRepository.getById(datosNuevos.getResponsable().getIdUsuario())
                .orElseThrow(() -> new Exception("Error: El responsable especificado no existe."));

        if (datosNuevos.getCantidadAves() > gallinero.getCapacidadMaxima()) {
            throw new Exception("Error de sobrepoblacion: Supera la capacidad del gallinero.");
        }

        if (datosNuevos.getTipoAve().getIdTipoAve() != gallinero.getTipoAve().getIdTipoAve()) {
            throw new Exception("Error de consistencia: El tipo de ave no coincide.");
        }

        parvadaExistente.setCantidadAves(datosNuevos.getCantidadAves());
        parvadaExistente.setFechaNacimiento(datosNuevos.getFechaNacimiento());
        parvadaExistente.setActiva(datosNuevos.isActiva());
        parvadaExistente.setTipoAve(datosNuevos.getTipoAve());
        parvadaExistente.setGallinero(gallinero);
        parvadaExistente.setResponsable(responsable);

        return parvadaRepository.save(parvadaExistente);
    }

    // CASO DE USO ESPECÍFICO 1: Cambio de gallinero (Mover parvada)
    public ParvadaEntity cambiarGallinero(int idParvada, int idNuevoGallinero) throws Exception {
        ParvadaEntity parvada = parvadaRepository.getById(idParvada)
                .orElseThrow(() -> new Exception("Error: La parvada no existe."));

        GallineroEntity nuevoGallinero = gallineroRepository.getById(idNuevoGallinero)
                .orElseThrow(() -> new Exception("Error: El nuevo gallinero no existe."));

        // Validar que quepan en el nuevo gallinero
        if (parvada.getCantidadAves() > nuevoGallinero.getCapacidadMaxima()) {
            throw new Exception("Error: El nuevo gallinero no tiene capacidad suficiente para albergar esta parvada.");
        }

        // Validar que el tipo de ave coincida
        if (parvada.getTipoAve().getIdTipoAve() != nuevoGallinero.getTipoAve().getIdTipoAve()) {
            throw new Exception("Error: El nuevo gallinero no está habilitado para este tipo de ave.");
        }

        parvada.setGallinero(nuevoGallinero);
        return parvadaRepository.save(parvada);
    }

    // CASO DE USO ESPECÍFICO 2: Cambio de responsable de parvada
    public ParvadaEntity cambiarResponsable(int idParvada, int idNuevoResponsable) throws Exception {
        ParvadaEntity parvada = parvadaRepository.getById(idParvada)
                .orElseThrow(() -> new Exception("Error: La parvada no existe."));

        UsuarioEntity nuevoResponsable = usuarioRepository.getById(idNuevoResponsable)
                .orElseThrow(() -> new Exception("Error: El usuario asignado como nuevo responsable no existe."));

        parvada.setResponsable(nuevoResponsable);
        return parvadaRepository.save(parvada);
    }

    public boolean darDeBaja(int id) {
        return parvadaRepository.getById(id).map(parvada -> {
            parvada.setActiva(false);
            parvadaRepository.save(parvada);
            return true;
        }).orElse(false);
    }
}