package mx.uaemex.fi.ingsoft2.parvadas.crud;

import org.springframework.data.repository.CrudRepository;
import mx.uaemex.fi.ingsoft2.parvadas.entity.VacunacionEntity;

public interface VacunacionCrud extends CrudRepository<VacunacionEntity, Integer> {
}