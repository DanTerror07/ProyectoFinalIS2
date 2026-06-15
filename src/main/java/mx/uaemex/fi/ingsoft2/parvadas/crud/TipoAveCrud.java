package mx.uaemex.fi.ingsoft2.parvadas.crud;

import org.springframework.data.repository.CrudRepository;
import mx.uaemex.fi.ingsoft2.parvadas.entity.TipoAveEntity;

public interface TipoAveCrud extends CrudRepository<TipoAveEntity, Integer> {
}