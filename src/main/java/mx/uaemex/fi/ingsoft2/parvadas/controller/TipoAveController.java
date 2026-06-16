package mx.uaemex.fi.ingsoft2.parvadas.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import mx.uaemex.fi.ingsoft2.parvadas.entity.TipoAveEntity;
import mx.uaemex.fi.ingsoft2.parvadas.service.TipoAveService;

@RestController
@RequestMapping("/tipos-ave")
public class TipoAveController {

    @Autowired
    private TipoAveService tipoAveService;

    // Listar todos secuencial
    @GetMapping("/listar")
    public List<TipoAveEntity> listar() {
        return tipoAveService.listarTodos();
    }

    // Listar ordenado alfabéticamente por nombre
    @GetMapping("/listar/alfabetico")
    public List<TipoAveEntity> listarAlfabeticamente() {
        return tipoAveService.listarAlfabeticamente();
    }

    // Guardar / Alta de tipo de ave
    @PostMapping("/guardar")
    public ResponseEntity<?> guardar(@RequestBody TipoAveEntity tipoAve) {
        try {
            TipoAveEntity guardado = tipoAveService.guardar(tipoAve);
            return new ResponseEntity<>(guardado, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // Consulta flexible (Por ID o fragmento de Nombre)
    @GetMapping("/consultar")
    public ResponseEntity<?> consultarFlexible(@RequestParam("criterio") String criterio) {
        try {
            List<TipoAveEntity> resultados = tipoAveService.consultarFlexible(criterio);
            return new ResponseEntity<>(resultados, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Buscar por ID exacto
    @GetMapping("/{id}")
    public ResponseEntity<TipoAveEntity> buscarPorId(@PathVariable("id") int id) {
        return tipoAveService.buscarPorId(id)
                .map(tipo -> new ResponseEntity<>(tipo, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Actualización de datos de tipo de ave
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> actualizar(@PathVariable("id") int id, @RequestBody TipoAveEntity datosNuevos) {
        try {
            TipoAveEntity actualizado = tipoAveService.actualizar(id, datosNuevos);
            return new ResponseEntity<>(actualizado, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // Dar de baja (Inhabilitación lógica)
    @DeleteMapping("/borrar/{id}")
    public ResponseEntity<String> eliminar(@PathVariable("id") int id) {
        try {
            tipoAveService.inhabilitarTipoAve(id);
            return ResponseEntity.ok("Tipo de ave inhabilitado con exito conforme al caso de uso.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}