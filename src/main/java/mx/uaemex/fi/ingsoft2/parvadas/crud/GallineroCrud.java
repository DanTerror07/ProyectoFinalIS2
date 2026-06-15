package mx.uaemex.fi.ingsoft2.parvadas.crud;

import org.springframework.data.repository.CrudRepository;
import mx.uaemex.fi.ingsoft2.parvadas.entity.GallineroEntity;

public interface GallineroCrud extends CrudRepository<GallineroEntity, Integer> {
}