package mx.uaemex.fi.ingsoft2.parvadas.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import mx.uaemex.fi.ingsoft2.parvadas.entity.TipoDeUsuarioEntity;
import mx.uaemex.fi.ingsoft2.parvadas.service.TipoDeUsuarioService;

@RestController
@RequestMapping("/roles")
public class TipoDeUsuarioController {

    @Autowired
    private TipoDeUsuarioService tipoDeUsuarioService;

    // 1. Obtener todos los roles -> GET http://localhost:8080/roles/roles
    @GetMapping("/roles")
    public List<TipoDeUsuarioEntity> listar() {
        return tipoDeUsuarioService.listarTodos();
    }

    // 2. Obtener un rol por ID -> GET http://localhost:8080/roles/{id}
    @GetMapping("/{id}")
    public ResponseEntity<TipoDeUsuarioEntity> buscarPorId(@PathVariable("id") int id) {
        return tipoDeUsuarioService.buscarPorId(id)
                .map(tipo -> new ResponseEntity<>(tipo, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // 3. Guardar un nuevo rol -> POST http://localhost:8080/roles/guardar
    @PostMapping("/guardar")
    public ResponseEntity<TipoDeUsuarioEntity> guardar(@RequestBody TipoDeUsuarioEntity tipoUsuario) {
        return new ResponseEntity<>(tipoDeUsuarioService.guardar(tipoUsuario), HttpStatus.CREATED);
    }

    // 4. Borrar un rol -> DELETE http://localhost:8080/roles/borrar
    // Nota: Se pasa el cuerpo en JSON tal como lo indica la presentación de la materia
    @DeleteMapping("/borrar")
    public ResponseEntity<Void> eliminar(@RequestBody TipoDeUsuarioEntity tipoUsuario) {
        if (tipoDeUsuarioService.eliminar(tipoUsuario.getIdTipoUsuario())) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}