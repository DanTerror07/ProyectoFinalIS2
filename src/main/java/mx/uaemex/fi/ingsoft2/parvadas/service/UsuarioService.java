package mx.uaemex.fi.ingsoft2.parvadas.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import mx.uaemex.fi.ingsoft2.parvadas.entity.UsuarioEntity;
import mx.uaemex.fi.ingsoft2.parvadas.repository.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // 1. Listar todos los usuarios
    public List<UsuarioEntity> listarTodos() {
        return usuarioRepository.getAll();
    }

    // 2. Buscar un usuario por su ID
    public Optional<UsuarioEntity> buscarPorId(int id) {
        return usuarioRepository.getById(id);
    }

    // 3. Registrar un usuario por primera vez (Alta de nuevo usuario)
    public UsuarioEntity guardar(UsuarioEntity usuario) throws Exception {
        // Validación: Flujo excepcional por correo duplicado
        if(usuarioRepository.getByCorreo(usuario.getCorreo()).isPresent()) {
            throw new Exception("El correo electronico ya se encuentra previamente registrado.");
        }
        // Validación: Flujo excepcional por celular duplicado
        if(usuarioRepository.getByCelular(usuario.getCelular()).isPresent()) {
            throw new Exception("El numero de celular ya se encuentra previamente registrado.");
        }
        return usuarioRepository.save(usuario);
    }

    // 4. Actualización de datos de usuario (Caso de Uso: Modificar datos)
    public UsuarioEntity actualizar(int id, UsuarioEntity datosNuevos) throws Exception {
        // Verificar que el usuario que se quiere modificar realmente exista en la BD
        UsuarioEntity usuarioExistente = usuarioRepository.getById(id)
                .orElseThrow(() -> new Exception("El usuario especificado no existe."));

        // Validación del PDF: Si cambia el correo, verificar que no lo tenga alguien más
        if (!usuarioExistente.getCorreo().equals(datosNuevos.getCorreo())) {
            if (usuarioRepository.getByCorreo(datosNuevos.getCorreo()).isPresent()) {
                throw new Exception("Error: El correo electronico ya esta registrado por otro usuario.");
            }
        }

        // Validación del PDF: Si cambia el celular, verificar que no lo tenga alguien más
        if (!usuarioExistente.getCelular().equals(datosNuevos.getCelular())) {
            if (usuarioRepository.getByCelular(datosNuevos.getCelular()).isPresent()) {
                throw new Exception("Error: El numero de celular ya esta registrado por otro usuario.");
            }
        }

        // Mapear los cambios permitidos por la especificación
        usuarioExistente.setPrimerApellido(datosNuevos.getPrimerApellido());
        usuarioExistente.setSegundoApellido(datosNuevos.getSegundoApellido());
        usuarioExistente.setNombres(datosNuevos.getNombres());
        usuarioExistente.setCorreo(datosNuevos.getCorreo());
        usuarioExistente.setCellular(datosNuevos.getCelular());
        usuarioExistente.setActivo(datosNuevos.isActivo());
        usuarioExistente.setTipoUsuario(datosNuevos.getTipoUsuario());
        
        // NOTA: Mantenemos la contraseña intacta tal como pide el PDF, no se machaca aquí
        
        return usuarioRepository.save(usuarioExistente);
    }

    // 5. Dar de baja un usuario (Inhabilitación lógica / Soft Delete)
    public boolean inhabilitarUsuario(int id) {
        // En lugar de hacer delete físico, aplicamos el caso de uso "Dar de baja" (Inhabilitar)
        return usuarioRepository.getById(id).map(usuario -> {
            usuario.setActivo(false); // Cambia el estatus a inactivo
            usuarioRepository.save(usuario);
            return true;
        }).orElse(false);
    }
}