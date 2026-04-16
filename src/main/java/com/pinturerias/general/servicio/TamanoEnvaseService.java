package com.pinturerias.general.servicio;

import java.util.List;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import com.pinturerias.general.dto.TamanoEnvaseDTO;
import com.pinturerias.general.entidad.TamanoEnvase;
import com.pinturerias.general.repositorio.TamanoEnvaseRepository;
import com.pinturerias.excepciones.RecursoNoEncontradoException;
@Service
@RequiredArgsConstructor
public class TamanoEnvaseService {

    private final TamanoEnvaseRepository repository;

    public List<TamanoEnvaseDTO> findAll() {
        return repository.findAll().stream().map(this::toDTO).toList();
    }
    public TamanoEnvaseDTO findById(Long id) {
        TamanoEnvase e = repository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("TamanoEnvase no encontrado"));

        return toDTO(e);
    }
    public TamanoEnvaseDTO save(TamanoEnvaseDTO dto) {
        return toDTO(repository.save(toEntity(dto)));
    }

    public TamanoEnvaseDTO update(Long id, TamanoEnvaseDTO dto) {
        TamanoEnvase e = repository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("TamanoEnvase no encontrado"));

        e.setNombre(dto.getNombre());
        e.setCapacidad(dto.getCapacidad());
        e.setPorcentajeAumento(dto.getPorcentajeAumento());

        return toDTO(repository.save(e));
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    private TamanoEnvaseDTO toDTO(TamanoEnvase e) {
        TamanoEnvaseDTO dto = new TamanoEnvaseDTO();
        dto.setId(e.getId());
        dto.setNombre(e.getNombre());
        dto.setCapacidad(e.getCapacidad());
        dto.setPorcentajeAumento(e.getPorcentajeAumento());
        return dto;
    }

    private TamanoEnvase toEntity(TamanoEnvaseDTO dto) {
        TamanoEnvase e = new TamanoEnvase();
        e.setNombre(dto.getNombre());
        e.setCapacidad(dto.getCapacidad());
        e.setPorcentajeAumento(dto.getPorcentajeAumento());
        return e;
    }

 
}



