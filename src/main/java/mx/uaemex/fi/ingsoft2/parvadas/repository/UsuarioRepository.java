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

    // 1. Obtener todos los usuarios (Orden original por ID)
    public List<UsuarioEntity> getAll() {
        return (List<UsuarioEntity>) usuarioCrud.findAll();
    }

    // 2. Guardar o actualizar un usuario
    public UsuarioEntity save(UsuarioEntity usuario) {
        return usuarioCrud.save(usuario);
    }

    // 3. Buscar un usuario por su ID primario
    public Optional<UsuarioEntity> getById(int id) {
        return usuarioCrud.findById(id);
    }

    // 4. Buscar un usuario por correo electrónico exacto
    public Optional<UsuarioEntity> getByCorreo(String correo) {
        return usuarioCrud.findByCorreo(correo);
    }

    // 5. Buscar un usuario por número de celular exacto
    public Optional<UsuarioEntity> getByCelular(String celular) {
        return usuarioCrud.findByCelular(celular);
    }

    // 6. REGLA DEL PDF: Listar todos ordenados alfabéticamente por apellido
    public List<UsuarioEntity> listarAlfabeticamente() {
        return usuarioCrud.findByOrderByPrimerApellidoAscNombresAsc();
    }

    // 7. CORREGIDO: Enviar el criterio de coincidencia parcial a los 3 campos mapeados en el CRUD
    public List<UsuarioEntity> buscarPorParteNombre(String parteNombre) {
        return usuarioCrud.findByNombresContainingIgnoreCaseOrPrimerApellidoContainingIgnoreCaseOrSegundoApellidoContainingIgnoreCase(
            parteNombre, 
            parteNombre, 
            parteNombre
        );
    }
}