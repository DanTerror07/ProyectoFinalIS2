package mx.uaemex.fi.ingsoft2.parvadas.crud;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import mx.uaemex.fi.ingsoft2.parvadas.entity.UsuarioEntity;

public interface UsuarioCrud extends CrudRepository<UsuarioEntity, Integer> {
    // Spring Boot arma automáticamente el SQL con el nombre del método
    Optional<UsuarioEntity> findByCorreo(String correo);
    Optional<UsuarioEntity> findByCelular(String celular);
}