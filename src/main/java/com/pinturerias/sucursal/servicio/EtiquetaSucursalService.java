package com.pinturerias.sucursal.servicio;

import com.pinturerias.compartidos.dto.EtiquetaCreateDTO;
import com.pinturerias.compartidos.dto.EtiquetaDTO;
import com.pinturerias.compartidos.enumeracion.Contexto;
import com.pinturerias.excepciones.ExcepcionApi;
import com.pinturerias.excepciones.RecursoNoEncontradoException;
import com.pinturerias.sucursal.entidad.EtiquetaSucursal;
import com.pinturerias.sucursal.repositorio.EtiquetaSucursalRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EtiquetaSucursalService {

    private final EtiquetaSucursalRepository repository;

    public EtiquetaSucursalService(EtiquetaSucursalRepository repository) {
        this.repository = repository;
    }

    public List<EtiquetaDTO> listarLocales() {
        return repository.findAll().stream()
                .map(this::toDTO)
                .toList();
    }

    public EtiquetaDTO crear(EtiquetaCreateDTO dto) {
        String valorNormalizado = normalizar(dto.getValor());

        if (repository.existsByValorIgnoreCase(valorNormalizado)) {
            throw new ExcepcionApi(400, "Ya existe una etiqueta de sucursal con ese valor");
        }

        EtiquetaSucursal etiqueta = new EtiquetaSucursal();
        etiqueta.setValor(valorNormalizado);

        return toDTO(repository.save(etiqueta));
    }

    public void eliminar(Long id) {
        if (!repository.existsById(id)) {
            throw new RecursoNoEncontradoException("Etiqueta de sucursal no encontrada");
        }
        repository.deleteById(id);
    }

    private EtiquetaDTO toDTO(EtiquetaSucursal etiqueta) {
        return new EtiquetaDTO(
                etiqueta.getId(),
                etiqueta.getValor(),
                Contexto.SUCURSAL
        );
    }

    private String normalizar(String valor) {
        if (valor == null || valor.isBlank()) {
            throw new ExcepcionApi(400, "El valor de la etiqueta no puede estar vacío");
        }
        return valor.trim();
    }
}