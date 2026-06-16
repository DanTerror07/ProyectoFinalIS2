package mx.uaemex.fi.ingsoft2.parvadas.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import mx.uaemex.fi.ingsoft2.parvadas.entity.UsuarioEntity;
import mx.uaemex.fi.ingsoft2.parvadas.service.UsuarioService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // 1. Consultar todos los usuarios -> GET http://localhost:8080/usuarios/listar
    @GetMapping("/listar")
    public List<UsuarioEntity> listar() {
        return usuarioService.listarTodos();
    }

    // 2. Alta de nuevo usuario -> POST http://localhost:8080/usuarios/guardar
    @PostMapping("/guardar")
    public ResponseEntity<?> guardar(@RequestBody UsuarioEntity usuario) {
        try {
            UsuarioEntity guardado = usuarioService.guardar(usuario);
            return new ResponseEntity<>(guardado, HttpStatus.CREATED);
        } catch (Exception e) {
            // Retorna un error 400 Bad Request con el mensaje del flujo excepcional (Información omitida / Duplicados)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // 3. Consultar usuario por ID -> GET http://localhost:8080/usuarios/200
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioEntity> buscarPorId(@PathVariable("id") int id) {
        return usuarioService.buscarPorId(id)
                .map(usuario -> new ResponseEntity<>(usuario, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // 4. Actualización de datos de usuario -> PUT http://localhost:8080/usuarios/actualizar/200
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> actualizar(@PathVariable("id") int id, @RequestBody UsuarioEntity usuario) {
        try {
            UsuarioEntity actualizado = usuarioService.actualizar(id, usuario);
            return new ResponseEntity<>(actualizado, HttpStatus.OK);
        } catch (Exception e) {
            // Retorna un error 400 Bad Request si se violan las restricciones del caso de uso
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // 5. Dar de baja un usuario (Inhabilitar) -> DELETE http://localhost:8080/usuarios/borrar/200
    @DeleteMapping("/borrar/{id}")
    public ResponseEntity<String> darDeBaja(@PathVariable("id") int id) {
        try {
            usuarioService.inhabilitarUsuario(id);
            return ResponseEntity.ok("Usuario inhabilitado con exito conforme al caso de uso.");
        } catch (Exception e) {
            // FLUJO ALTERNO DEL PDF: Retorna un 400 Bad Request si viola la regla de negocio de la parvada huérfana
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // 6. REGLA DEL PDF: Listar ordenado alfabéticamente -> GET http://localhost:8080/usuarios/listar/alfabetico
    @GetMapping("/listar/alfabetico")
    public List<UsuarioEntity> listarAlfabeticamente() {
        return usuarioService.listarAlfabeticamente();
    }

    // 7. REGLA DEL PDF: Consulta flexible (Por correo, celular o parte del nombre) -> GET http://localhost:8080/usuarios/consultar?criterio=...
    @GetMapping("/consultar")
    public ResponseEntity<?> consultarUsuario(@RequestParam("criterio") String criterio) {
        try {
            List<UsuarioEntity> resultados = usuarioService.consultarFlexible(criterio);
            return new ResponseEntity<>(resultados, HttpStatus.OK);
        } catch (Exception e) {
            // FLUJO EXCEPCIONAL DEL PDF: Retorna un 404 con el mensaje de que no existen registros correspondientes
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}