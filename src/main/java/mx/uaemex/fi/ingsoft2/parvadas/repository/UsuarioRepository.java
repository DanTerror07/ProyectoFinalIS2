package mx.uaemex.fi.ingsoft2.parvadas.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import mx.uaemex.fi.ingsoft2.parvadas.crud.UsuarioCrud;
import mx.uaemex.fi.ingsoft2.parvadas.entity.UsuarioEntity;

@Repository
public class UsuarioRepository {

    @Autowired
    private UsuarioCrud usuarioCrud;

    public List<UsuarioEntity> getAll() {
        return (List<UsuarioEntity>) usuarioCrud.findAll();
    }

    public UsuarioEntity save(UsuarioEntity usuario) {
        return usuarioCrud.save(usuario);
    }

    public Optional<UsuarioEntity> getById(int id) {
        return usuarioCrud.findById(id);
    }

    public Optional<UsuarioEntity> getByCorreo(String correo) {
        return usuarioCrud.findByCorreo(correo);
    }

    public Optional<UsuarioEntity> getByCelular(String celular) {
        return usuarioCrud.findByCelular(celular);
    }
}