package mx.uaemex.fi.ingsoft2.parvadas.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import mx.uaemex.fi.ingsoft2.parvadas.entity.UsuarioEntity;
import mx.uaemex.fi.ingsoft2.parvadas.entity.TipoDeUsuarioEntity;
import mx.uaemex.fi.ingsoft2.parvadas.repository.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TipoDeUsuarioService tipoDeUsuarioService;

    // 1. Listar todos los usuarios
    public List<UsuarioEntity> listarTodos() {
        return usuarioRepository.getAll();
    }

    // 2. Buscar un usuario por su ID
    public Optional<UsuarioEntity> buscarPorId(int id) {
        return usuarioRepository.getById(id);
    }

    // 3. Registrar un usuario por primera vez (Alta de nuevo usuario - Soporta múltiples roles)
    public UsuarioEntity guardar(UsuarioEntity usuario) throws Exception {
        // Validación de campos obligatorios e inclusión de la lista de tipos de usuario
        if (usuario.getPrimerApellido() == null || usuario.getPrimerApellido().isBlank() ||
            usuario.getNombres() == null || usuario.getNombres().isBlank() ||
            usuario.getCorreo() == null || usuario.getCorreo().isBlank() ||
            usuario.getCelular() == null || usuario.getCelular().isBlank() ||
            usuario.getContrasena() == null || usuario.getContrasena().isBlank() ||
            usuario.getTiposUsuario() == null || usuario.getTiposUsuario().isEmpty()) {
            throw new Exception("Error: El administrador ha omitido informacion requerida obligatoria.");
        }

        if(usuarioRepository.getByCorreo(usuario.getCorreo()).isPresent()) {
            throw new Exception("El correo electronico ya se encuentra previamente registrado.");
        }
        
        if(usuarioRepository.getByCelular(usuario.getCelular()).isPresent()) {
            throw new Exception("El numero de celular ya se encuentra previamente registrado.");
        }
        
        // Carga y validación de los roles del catálogo para evitar transients
        List<TipoDeUsuarioEntity> rolesAsignados = new ArrayList<>();
        for (TipoDeUsuarioEntity rol : usuario.getTiposUsuario()) {
            TipoDeUsuarioEntity rolBD = tipoDeUsuarioService.buscarPorId(rol.getIdTipoUsuario())
                    .orElseThrow(() -> new Exception("Error: El tipo de usuario (" + rol.getIdTipoUsuario() + ") no existe en el catalogo."));
            rolesAsignados.add(rolBD);
        }
        usuario.setTiposUsuario(rolesAsignados);

        // El sistema almacena la fecha del registro actual automáticamente según el PDF
        usuario.setFechaRegistro(new java.util.Date());
        
        return usuarioRepository.save(usuario);
    }

    // 4. Actualización de datos de usuario (Soporta modificación de múltiples roles)
    public UsuarioEntity actualizar(int id, UsuarioEntity datosNuevos) throws Exception {
        // 1. Recuperamos el usuario original directo de la base de datos
        UsuarioEntity usuarioExistente = usuarioRepository.getById(id)
                .orElseThrow(() -> new Exception("El usuario especificado no existe."));

        // 2. Validación de campos obligatorios vacíos conforme al PDF
        if (datosNuevos.getPrimerApellido() == null || datosNuevos.getPrimerApellido().isBlank() ||
            datosNuevos.getNombres() == null || datosNuevos.getNombres().isBlank() ||
            datosNuevos.getCorreo() == null || datosNuevos.getCorreo().isBlank() ||
            datosNuevos.getCelular() == null || datosNuevos.getCelular().isBlank() ||
            datosNuevos.getTiposUsuario() == null || datosNuevos.getTiposUsuario().isEmpty()) {
            throw new Exception("Error: No se pueden guardar cambios con campos obligatorios vacios.");
        }

        // 3. Restricción: Validar correo duplicado solo si se intentó cambiar el actual
        if (!usuarioExistente.getCorreo().equals(datosNuevos.getCorreo())) {
            if (usuarioRepository.getByCorreo(datosNuevos.getCorreo()).isPresent()) {
                throw new Exception("Error de restriccion: El correo electronico ya esta registrado por otro usuario.");
            }
        }

        // 4. Restricción: Validar celular duplicado solo si se intentó cambiar el actual
        if (!usuarioExistente.getCelular().equals(datosNuevos.getCelular())) {
            if (usuarioRepository.getByCelular(datosNuevos.getCelular()).isPresent()) {
                throw new Exception("Error de restriccion: El numero de celular ya esta registrado por otro usuario.");
            }
        }

        // 5. Si el administrador envía una contraseña en la actualización, se procesa; si no, se preserva la anterior
        if (datosNuevos.getContrasena() != null && !datosNuevos.getContrasena().isBlank()) {
            usuarioExistente.setContrasena(datosNuevos.getContrasena());
        }

        // 6. Actualizamos los tipos de datos primitivos de la entidad persistente
        usuarioExistente.setPrimerApellido(datosNuevos.getPrimerApellido());
        usuarioExistente.setSegundoApellido(datosNuevos.getSegundoApellido());
        usuarioExistente.setNombres(datosNuevos.getNombres());
        usuarioExistente.setCorreo(datosNuevos.getCorreo());
        usuarioExistente.setCelular(datosNuevos.getCelular());
        usuarioExistente.setActivo(datosNuevos.isActivo());
        
        // 7. Mapeo seguro de la lista de múltiples roles nuevos
        List<TipoDeUsuarioEntity> rolesCargados = new ArrayList<>();
        for (TipoDeUsuarioEntity rol : datosNuevos.getTiposUsuario()) {
            TipoDeUsuarioEntity rolPersistido = tipoDeUsuarioService.buscarPorId(rol.getIdTipoUsuario())
                    .orElseThrow(() -> new Exception("Error: El tipo de usuario " + rol.getIdTipoUsuario() + " no existe en el catalogo."));
            rolesCargados.add(rolPersistido);
        }
        
        usuarioExistente.setTiposUsuario(rolesCargados);
        
        return usuarioRepository.save(usuarioExistente);
    }

    // 5. Dar de baja un usuario (Inhabilitación lógica / Soft Delete)
    public boolean inhabilitarUsuario(int id) throws Exception {
        UsuarioEntity usuario = usuarioRepository.getById(id)
                .orElseThrow(() -> new Exception("El usuario especificado no existe."));

        usuario.setActivo(false); 
        usuarioRepository.save(usuario);
        return true;
    }

    // 6. Listar ordenado alfabéticamente
    public List<UsuarioEntity> listarAlfabeticamente() {
        return usuarioRepository.listarAlfabeticamente();
    }

    // 7. Búsqueda flexible por coincidencia (Correo, Celular o Nombres/Apellidos)
    public List<UsuarioEntity> consultarFlexible(String criterio) throws Exception {
        List<UsuarioEntity> resultados = new ArrayList<>();

        usuarioRepository.getByCorreo(criterio).ifPresent(resultados::add);

        if (resultados.isEmpty()) {
            usuarioRepository.getByCelular(criterio).ifPresent(resultados::add);
        }

        if (resultados.isEmpty()) {
            resultados.addAll(usuarioRepository.buscarPorParteNombre(criterio));
        }

        if (resultados.isEmpty()) {
            throw new Exception("No hay registros que correspondan a ese usuario.");
        }

        return resultados;
    }
}