package mx.uaemex.fi.ingsoft2.parvadas.crud;

import org.springframework.data.repository.CrudRepository;
import mx.uaemex.fi.ingsoft2.parvadas.entity.ParvadaEntity;

public interface ParvadaCrud extends CrudRepository<ParvadaEntity, Integer> {
}