package mx.uaemex.fi.ingsoft2.parvadas.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import mx.uaemex.fi.ingsoft2.parvadas.crud.MortalidadCrud;
import mx.uaemex.fi.ingsoft2.parvadas.entity.MortalidadEntity;

@Repository
public class MortalidadRepository {

    @Autowired
    private MortalidadCrud mortalidadCrud;

    public MortalidadEntity save(MortalidadEntity mortalidad) {
        return mortalidadCrud.save(mortalidad);
    }
}