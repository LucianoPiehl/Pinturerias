package com.pinturerias.general.servicio;

import java.util.List;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import com.pinturerias.general.dto.ColorBaseDTO;
import com.pinturerias.general.entidad.ColorBase;
import com.pinturerias.general.repositorio.ColorBaseRepository;
import com.pinturerias.excepciones.RecursoNoEncontradoException;

@Service
@RequiredArgsConstructor
public class ColorBaseService {

    private final ColorBaseRepository repository;

    public List<ColorBaseDTO> findAll() {
        return repository.findAll().stream().map(this::toDTO).toList();
    }

    public ColorBaseDTO findById(Long id) {
    return toDTO(repository.findById(id)
            .orElseThrow(() -> new RecursoNoEncontradoException("TipoPintura no encontrado")));
    }

    public ColorBaseDTO save(ColorBaseDTO dto) {
        ColorBase entity = toEntity(dto);
        return toDTO(repository.save(entity));
    }

    public ColorBaseDTO update(Long id, ColorBaseDTO dto) {
        ColorBase entity = repository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("ColorBase no encontrado"));

        entity.setNombre(dto.getNombre());
        entity.setFormula(dto.getFormula());
        entity.setValorFijoPorLitro(dto.getValorFijoPorLitro());

        return toDTO(repository.save(entity));
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    private ColorBaseDTO toDTO(ColorBase e) {
        ColorBaseDTO dto = new ColorBaseDTO();
        dto.setId(e.getId());
        dto.setNombre(e.getNombre());
        dto.setFormula(e.getFormula());
        dto.setValorFijoPorLitro(e.getValorFijoPorLitro());
        return dto;
    }

    private ColorBase toEntity(ColorBaseDTO dto) {
        ColorBase e = new ColorBase();
        e.setNombre(dto.getNombre());
        e.setFormula(dto.getFormula());
        e.setValorFijoPorLitro(dto.getValorFijoPorLitro());
        return e;
    }
}



