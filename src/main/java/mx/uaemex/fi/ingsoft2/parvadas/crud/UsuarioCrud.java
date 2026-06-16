package mx.uaemex.fi.ingsoft2.parvadas.crud;

import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import mx.uaemex.fi.ingsoft2.parvadas.entity.UsuarioEntity;

public interface UsuarioCrud extends CrudRepository<UsuarioEntity, Integer> {

    // 1. Listar todos ordenados alfabéticamente por primer apellido y nombres
    List<UsuarioEntity> findByOrderByPrimerApellidoAscNombresAsc();

    // 2. Buscar por correo electrónico exacto
    Optional<UsuarioEntity> findByCorreo(String correo);

    // 3. Buscar por celular exacto
    Optional<UsuarioEntity> findByCelular(String celular);

    // 4. CORREGIDO: Buscar por coincidencia parcial en nombre, primer apellido o segundo apellido
    List<UsuarioEntity> findByNombresContainingIgnoreCaseOrPrimerApellidoContainingIgnoreCaseOrSegundoApellidoContainingIgnoreCase(
        String nombres, 
        String primerApellido, 
        String segundoApellido
    );
}