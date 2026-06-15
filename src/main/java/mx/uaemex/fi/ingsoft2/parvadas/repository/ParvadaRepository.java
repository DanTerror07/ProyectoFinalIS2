package mx.uaemex.fi.ingsoft2.parvadas.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import mx.uaemex.fi.ingsoft2.parvadas.crud.ParvadaCrud;
import mx.uaemex.fi.ingsoft2.parvadas.entity.ParvadaEntity;

@Repository
public class ParvadaRepository {

    @Autowired
    private ParvadaCrud parvadaCrud;

    public List<ParvadaEntity> getAll() {
        return (List<ParvadaEntity>) parvadaCrud.findAll();
    }

    public Optional<ParvadaEntity> getById(int id) {
        return parvadaCrud.findById(id);
    }

    public ParvadaEntity save(ParvadaEntity parvada) {
        return parvadaCrud.save(parvada);
    }
}