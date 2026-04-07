package com.pinturerias.com.pinturerias.general.service;

import java.util.List;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import com.pinturerias.com.pinturerias.general.dto.TipoPinturaDTO;
import com.pinturerias.com.pinturerias.general.entity.TipoPintura;
import com.pinturerias.com.pinturerias.general.repository.TipoPinturaRepository;
import com.pinturerias.excepciones.RecursoNoEncontradoException;
@Service
@RequiredArgsConstructor
public class TipoPinturaService {

    private final TipoPinturaRepository repository;

    public List<TipoPinturaDTO> findAll() {
        return repository.findAll().stream().map(this::toDTO).toList();
    }
    public TipoPinturaDTO findById(Long id) {
        TipoPintura e = repository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("TipoPintura no encontrado"));

        return toDTO(e);
    }
    public TipoPinturaDTO save(TipoPinturaDTO dto) {
        return toDTO(repository.save(toEntity(dto)));
    }

    public TipoPinturaDTO update(Long id, TipoPinturaDTO dto) {
        TipoPintura e = repository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("TipoPintura no encontrado"));

        e.setPorcentajeAumento(dto.getPorcentajeAumento());
        e.setRendimientoMT2(dto.getRendimientoMT2());

        return toDTO(repository.save(e));
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    private TipoPinturaDTO toDTO(TipoPintura e) {
        TipoPinturaDTO dto = new TipoPinturaDTO();
        dto.setId(e.getId());
        dto.setPorcentajeAumento(e.getPorcentajeAumento());
        dto.setRendimientoMT2(e.getRendimientoMT2());
        return dto;
    }

    private TipoPintura toEntity(TipoPinturaDTO dto) {
        TipoPintura e = new TipoPintura();
        e.setPorcentajeAumento(dto.getPorcentajeAumento());
        e.setRendimientoMT2(dto.getRendimientoMT2());
        return e;
    }


}