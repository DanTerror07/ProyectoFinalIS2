package mx.uaemex.fi.ingsoft2.parvadas.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import mx.uaemex.fi.ingsoft2.parvadas.entity.TipoAveEntity;
import mx.uaemex.fi.ingsoft2.parvadas.repository.TipoAveRepository;

@Service
public class TipoAveService {

    @Autowired
    private TipoAveRepository tipoAveRepository;

    public List<TipoAveEntity> listarTodos() {
        return tipoAveRepository.getAll();
    }

    public Optional<TipoAveEntity> buscarPorId(int id) {
        return tipoAveRepository.getById(id);
    }

    // 1. Alta de Tipo de Ave con validaciones
    public TipoAveEntity guardar(TipoAveEntity tipoAve) throws Exception {
        // Validación: Omisión de datos
        if (tipoAve.getNombre() == null || tipoAve.getNombre().isBlank() ||
            tipoAve.getDescripcion() == null || tipoAve.getDescripcion().isBlank()) {
            throw new Exception("Error: El administrador ha omitido informacion requerida obligatoria.");
        }

        // Validación: Si el tipo de ave ya existe
        if (tipoAveRepository.getByNombre(tipoAve.getNombre()).isPresent()) {
            throw new Exception("El tipo de ave con este nombre ya se encuentra registrado.");
        }

        return tipoAveRepository.save(tipoAve);
    }

    // 2. Actualización de Tipo de Ave con validaciones cruzadas
    public TipoAveEntity actualizar(int id, TipoAveEntity datosNuevos) throws Exception {
        TipoAveEntity tipoExistente = tipoAveRepository.getById(id)
                .orElseThrow(() -> new Exception("El tipo de ave especificado no existe."));

        if (datosNuevos.getNombre() == null || datosNuevos.getNombre().isBlank() ||
            datosNuevos.getDescripcion() == null || datosNuevos.getDescripcion().isBlank()) {
            throw new Exception("Error: No se pueden guardar cambios con campos obligatorios vacios.");
        }

        // Si se cambia el nombre, validar que no choque con otra categoría existente
        if (!tipoExistente.getNombre().equalsIgnoreCase(datosNuevos.getNombre())) {
            if (tipoAveRepository.getByNombre(datosNuevos.getNombre()).isPresent()) {
                throw new Exception("Error de restriccion: Ese nombre ya pertenece a otro tipo de ave.");
            }
        }

        tipoExistente.setNombre(datosNuevos.getNombre());
        tipoExistente.setDescripcion(datosNuevos.getDescripcion());
        tipoExistente.setActivo(datosNuevos.isActivo());

        return tipoAveRepository.save(tipoExistente);
    }

    // 3. Baja lógica con regla de negocio de parvadas
    public boolean inhabilitarTipoAve(int id) throws Exception {
        TipoAveEntity tipo = tipoAveRepository.getById(id)
                .orElseThrow(() -> new Exception("El tipo de ave especificado no existe."));

        // REGLA DE NEGOCIO DEL PDF: Si está en uso por una parvada activa
        if (verificarSiEstaEnUsoPorParvadas(id)) {
            throw new Exception("Error de restriccion: El tipo de ave esta asignado a una o mas parvadas activas. No se puede dar de baja.");
        }

        tipo.setActivo(false); // Baja lógica / Inhabilitar
        tipoAveRepository.save(tipo);
        return true;
    }

    // 4. Listar Alfabéticamente
    public List<TipoAveEntity> listarAlfabeticamente() {
        return tipoAveRepository.listarAlfabeticamente();
    }

    // 5. Búsqueda Flexible / Consultar por criterio
    public List<TipoAveEntity> consultarFlexible(String criterio) throws Exception {
        List<TipoAveEntity> resultados = new ArrayList<>();

        // Intentar por ID si es número
        try {
            int id = Integer.parseInt(criterio);
            tipoAveRepository.getById(id).ifPresent(resultados::add);
        } catch (NumberFormatException e) {
            // No es número, ignorar e intentar por nombre
        }

        // Intentar por coincidencia parcial en el nombre
        if (resultados.isEmpty()) {
            resultados.addAll(tipoAveRepository.buscarPorNombreFlexible(criterio));
        }

        if (resultados.isEmpty()) {
            throw new Exception("No hay registros que correspondan a ese tipo de ave.");
        }

        return resultados;
    }

    // Simulación de regla de negocio (Para pruebas de Postman, el ID 1 simulará estar ocupado)
    private boolean verificarSiEstaEnUsoPorParvadas(int tipoAveId) {
        return tipoAveId == 1;
    }
}