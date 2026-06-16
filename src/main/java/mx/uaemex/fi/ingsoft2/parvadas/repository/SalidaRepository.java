package mx.uaemex.fi.ingsoft2.parvadas.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import mx.uaemex.fi.ingsoft2.parvadas.crud.SalidaCrud;
import mx.uaemex.fi.ingsoft2.parvadas.entity.SalidaEntity;

@Repository
public class SalidaRepository {
    @Autowired private SalidaCrud salidaCrud;
    public SalidaEntity save(SalidaEntity s) { return salidaCrud.save(s); }
}