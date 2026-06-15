package mx.uaemex.fi.ingsoft2.parvadas.crud;

import org.springframework.data.repository.CrudRepository;
import mx.uaemex.fi.ingsoft2.parvadas.entity.TipoDeUsuarioEntity;

public interface TipoDeUsuarioCrud extends CrudRepository<TipoDeUsuarioEntity, Integer> {
    // Hereda de forma automática los métodos findById, save, delete, etc.
}