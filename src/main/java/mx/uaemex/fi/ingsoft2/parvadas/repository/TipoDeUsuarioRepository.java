package mx.uaemex.fi.ingsoft2.parvadas.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import mx.uaemex.fi.ingsoft2.parvadas.crud.TipoDeUsuarioCrud;
import mx.uaemex.fi.ingsoft2.parvadas.entity.TipoDeUsuarioEntity;

@Repository
public class TipoDeUsuarioRepository {

    @Autowired
    private TipoDeUsuarioCrud tipoDeUsuarioCrud;

    // Buscar todos los registros
    public List<TipoDeUsuarioEntity> getAll() {
        return (List<TipoDeUsuarioEntity>) tipoDeUsuarioCrud.findAll();
    }

    // Buscar un registro por ID
    public Optional<TipoDeUsuarioEntity> getById(int id) {
        return tipoDeUsuarioCrud.findById(id);
    }

    // Guardar o actualizar un registro
    public TipoDeUsuarioEntity save(TipoDeUsuarioEntity tipoUsuario) {
        return tipoDeUsuarioCrud.save(tipoUsuario);
    }

    // Eliminar un registro
    public void delete(TipoDeUsuarioEntity tipoUsuario) {
        tipoDeUsuarioCrud.delete(tipoUsuario);
    }
}