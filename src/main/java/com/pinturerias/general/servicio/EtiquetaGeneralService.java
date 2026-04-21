package com.pinturerias.general.servicio;

import com.pinturerias.compartidos.dto.EtiquetaCreateDTO;
import com.pinturerias.compartidos.dto.EtiquetaDTO;
import com.pinturerias.compartidos.enumeracion.Contexto;
import com.pinturerias.excepciones.ExcepcionApi;
import com.pinturerias.excepciones.RecursoNoEncontradoException;
import com.pinturerias.general.entidad.EtiquetaGeneral;
import com.pinturerias.general.repositorio.EtiquetaGeneralRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EtiquetaGeneralService {

    private final EtiquetaGeneralRepository repository;

    public EtiquetaGeneralService(EtiquetaGeneralRepository repository) {
        this.repository = repository;
    }

    public List<EtiquetaDTO> listar() {
        return repository.findAll().stream()
                .map(this::toDTO)
                .toList();
    }

    public EtiquetaDTO crear(EtiquetaCreateDTO dto) {
        String valorNormalizado = normalizar(dto.getValor());

        if (repository.existsByValorIgnoreCase(valorNormalizado)) {
            throw new ExcepcionApi(400, "Ya existe una etiqueta general con ese valor");
        }

        EtiquetaGeneral etiqueta = new EtiquetaGeneral();
        etiqueta.setValor(valorNormalizado);

        return toDTO(repository.save(etiqueta));
    }

    public void eliminar(Long id) {
        if (!repository.existsById(id)) {
            throw new RecursoNoEncontradoException("Etiqueta general no encontrada");
        }
        repository.deleteById(id);
    }

    private EtiquetaDTO toDTO(EtiquetaGeneral etiqueta) {
        return new EtiquetaDTO(
                etiqueta.getId(),
                etiqueta.getValor(),
                Contexto.GENERAL
        );
    }

    private String normalizar(String valor) {
        if (valor == null || valor.isBlank()) {
            throw new ExcepcionApi(400, "El valor de la etiqueta no puede estar vacío");
        }
        return valor.trim();
    }
}