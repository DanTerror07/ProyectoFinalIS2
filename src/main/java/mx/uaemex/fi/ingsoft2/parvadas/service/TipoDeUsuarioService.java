package mx.uaemex.fi.ingsoft2.parvadas.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import mx.uaemex.fi.ingsoft2.parvadas.entity.TipoDeUsuarioEntity;
import mx.uaemex.fi.ingsoft2.parvadas.repository.TipoDeUsuarioRepository;

@Service
public class TipoDeUsuarioService {

    @Autowired
    private TipoDeUsuarioRepository tipoDeUsuarioRepository;

    public List<TipoDeUsuarioEntity> listarTodos() {
        return tipoDeUsuarioRepository.getAll();
    }

    public Optional<TipoDeUsuarioEntity> buscarPorId(int id) {
        return tipoDeUsuarioRepository.getById(id);
    }

    public TipoDeUsuarioEntity guardar(TipoDeUsuarioEntity tipoUsuario) {
        return tipoDeUsuarioRepository.save(tipoUsuario);
    }

    public boolean eliminar(int id) {
        return tipoDeUsuarioRepository.getById(id).map(tipoUsuario -> {
            tipoDeUsuarioRepository.delete(tipoUsuario);
            return true;
        }).orElse(false);
    }
}