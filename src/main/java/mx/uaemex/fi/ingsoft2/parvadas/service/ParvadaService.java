package mx.uaemex.fi.ingsoft2.parvadas.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import mx.uaemex.fi.ingsoft2.parvadas.entity.ParvadaEntity;
import mx.uaemex.fi.ingsoft2.parvadas.repository.ParvadaRepository;

@Service
public class ParvadaService {

    @Autowired
    private ParvadaRepository parvadaRepository;

    public List<ParvadaEntity> listarTodas() {
        return parvadaRepository.getAll();
    }

    public Optional<ParvadaEntity> buscarPorId(int id) {
        return parvadaRepository.getById(id);
    }

    public ParvadaEntity guardar(ParvadaEntity parvada) {
        return parvadaRepository.save(parvada);
    }

    public boolean darDeBaja(int id) {
        return parvadaRepository.getById(id).map(parvada -> {
            parvada.setActiva(false); // Baja lógica según caso de uso
            parvadaRepository.save(parvada);
            return true;
        }).orElse(false);
    }
}