package mx.uaemex.fi.ingsoft2.parvadas.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import mx.uaemex.fi.ingsoft2.parvadas.crud.MedicacionCrud;
import mx.uaemex.fi.ingsoft2.parvadas.entity.MedicacionEntity;

@Repository
public class MedicacionRepository {
    @Autowired private MedicacionCrud medicacionCrud;
    public MedicacionEntity save(MedicacionEntity m) { return medicacionCrud.save(m); }
}