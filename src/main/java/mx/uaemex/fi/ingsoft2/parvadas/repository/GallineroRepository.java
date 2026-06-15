package mx.uaemex.fi.ingsoft2.parvadas.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import mx.uaemex.fi.ingsoft2.parvadas.crud.GallineroCrud;
import mx.uaemex.fi.ingsoft2.parvadas.entity.GallineroEntity;

@Repository
public class GallineroRepository {

    @Autowired
    private GallineroCrud gallineroCrud;

    public List<GallineroEntity> getAll() {
        return (List<GallineroEntity>) gallineroCrud.findAll();
    }

    public Optional<GallineroEntity> getById(int id) {
        return gallineroCrud.findById(id);
    }

    public GallineroEntity save(GallineroEntity gallinero) {
        return gallineroCrud.save(gallinero);
    }

    public void delete(GallineroEntity gallinero) {
        gallineroCrud.delete(gallinero);
    }
}