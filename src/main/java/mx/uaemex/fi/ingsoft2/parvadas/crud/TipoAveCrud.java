package mx.uaemex.fi.ingsoft2.parvadas.crud;

import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import mx.uaemex.fi.ingsoft2.parvadas.entity.TipoAveEntity;

public interface TipoAveCrud extends CrudRepository<TipoAveEntity, Integer> {

    // Para listar ordenado alfabéticamente
    List<TipoAveEntity> findByOrderByNombreAsc();

    // Para evitar nombres de categorías duplicadas
    Optional<TipoAveEntity> findByNombre(String nombre);

    // Para el buscador flexible
    List<TipoAveEntity> findByNombreContainingIgnoreCase(String palabraClave);
}