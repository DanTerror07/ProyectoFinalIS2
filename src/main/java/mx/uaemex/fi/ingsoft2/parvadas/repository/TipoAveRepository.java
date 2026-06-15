package mx.uaemex.fi.ingsoft2.parvadas.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import mx.uaemex.fi.ingsoft2.parvadas.crud.TipoAveCrud;
import mx.uaemex.fi.ingsoft2.parvadas.entity.TipoAveEntity;

@Repository
public class TipoAveRepository {

    @Autowired
    private TipoAveCrud tipoAveCrud;

    public List<TipoAveEntity> getAll() {
        return (List<TipoAveEntity>) tipoAveCrud.findAll();
    }

    public Optional<TipoAveEntity> getById(int id) {
        return tipoAveCrud.findById(id);
    }

    public TipoAveEntity save(TipoAveEntity tipoAve) {
        return tipoAveCrud.save(tipoAve);
    }

    public void delete(TipoAveEntity tipoAve) {
        tipoAveCrud.delete(tipoAve);
    }
}