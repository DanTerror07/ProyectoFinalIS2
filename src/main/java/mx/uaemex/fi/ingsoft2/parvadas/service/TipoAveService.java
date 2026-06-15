package mx.uaemex.fi.ingsoft2.parvadas.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import mx.uaemex.fi.ingsoft2.parvadas.entity.TipoAveEntity;
import mx.uaemex.fi.ingsoft2.parvadas.repository.TipoAveRepository;

@Service
public class TipoAveService {

    @Autowired
    private TipoAveRepository tipoAveRepository;

    public List<TipoAveEntity> listarTodos() {
        return tipoAveRepository.getAll();
    }

    public Optional<TipoAveEntity> buscarPorId(int id) {
        return tipoAveRepository.getById(id);
    }

    public TipoAveEntity guardar(TipoAveEntity tipoAve) {
        return tipoAveRepository.save(tipoAve);
    }

    public boolean eliminar(int id) {
        return tipoAveRepository.getById(id).map(tipo -> {
            tipoAveRepository.delete(tipo);
            return true;
        }).orElse(false);
    }
}