package mx.uaemex.fi.ingsoft2.parvadas.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import mx.uaemex.fi.ingsoft2.parvadas.entity.GallineroEntity;
import mx.uaemex.fi.ingsoft2.parvadas.repository.GallineroRepository;

@Service
public class GallineroService {

    @Autowired
    private GallineroRepository gallineroRepository;

    public List<GallineroEntity> listarTodos() {
        return gallineroRepository.getAll();
    }

    public Optional<GallineroEntity> buscarPorId(int id) {
        return gallineroRepository.getById(id);
    }

    public GallineroEntity guardar(GallineroEntity gallinero) {
        return gallineroRepository.save(gallinero);
    }

    public boolean eliminar(int id) {
        return gallineroRepository.getById(id).map(g -> {
            gallineroRepository.delete(g);
            return true;
        }).orElse(false);
    }

    // Método específico para cumplir con el CU: "Registrar sanitización de gallinero"
    public boolean registrarSanitizacion(int id) {
        return gallineroRepository.getById(id).map(g -> {
            g.setSanitizado(true); // Cambia el estado a sanitizado de forma explícita
            gallineroRepository.save(g);
            return true;
        }).orElse(false);
    }
}