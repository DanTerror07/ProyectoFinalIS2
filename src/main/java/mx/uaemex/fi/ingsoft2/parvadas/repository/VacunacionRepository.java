package mx.uaemex.fi.ingsoft2.parvadas.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import mx.uaemex.fi.ingsoft2.parvadas.crud.VacunacionCrud;
import mx.uaemex.fi.ingsoft2.parvadas.entity.VacunacionEntity;

@Repository
public class VacunacionRepository {

    @Autowired
    private VacunacionCrud vacunacionCrud;

    public VacunacionEntity save(VacunacionEntity vacunacion) {
        return vacunacionCrud.save(vacunacion);
    }
}