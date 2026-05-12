package com.pinturerias.sucursal.servicio;

import com.pinturerias.compartidos.dto.etiqueta.EtiquetaCreateDTO;
import com.pinturerias.compartidos.dto.etiqueta.EtiquetaDTO;
import com.pinturerias.compartidos.enumeracion.Contexto;
import com.pinturerias.compartidos.servicio.NormalizadorEtiquetaService;
import com.pinturerias.compartidos.servicio.ValidadorDuplicidadEtiquetaService;
import com.pinturerias.excepciones.RecursoNoEncontradoException;
import com.pinturerias.compartidos.entidad.shared.Etiqueta;
import com.pinturerias.sucursal.repositorio.EtiquetaSucursalRepository;
import com.pinturerias.sucursal.repositorio.ProductoEtiquetaSucursalRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class EtiquetaSucursalService {

    private final EtiquetaSucursalRepository repository;
    private final NormalizadorEtiquetaService normalizadorEtiquetaService;
    private final ValidadorDuplicidadEtiquetaService validadorDuplicidadEtiquetaService;
    private final ProductoEtiquetaSucursalRepository productoEtiquetaSucursalRepository;

    @Transactional
    public List<EtiquetaDTO> listarLocales() {
        return repository.findAll().stream()
                .map(this::toDTO)
                .toList();
    }

    @Transactional
    public EtiquetaDTO crear(EtiquetaCreateDTO dto) {
        String valorVisible = normalizadorEtiquetaService.normalizarValorVisible(dto.getValor());
        String claveNormalizada = normalizadorEtiquetaService.generarClaveNormalizada(valorVisible);
        validadorDuplicidadEtiquetaService.validarClaveLibreSucursal(claveNormalizada);

        Etiqueta etiqueta = new Etiqueta();
        etiqueta.setValor(valorVisible);
        etiqueta.setClaveNormalizada(claveNormalizada);

        return toDTO(repository.save(etiqueta));
    }

    @Transactional
    public void eliminar(Long id) {
        if (!repository.existsById(id)) {
            throw new RecursoNoEncontradoException("Etiqueta de sucursal no encontrada");
        }
        // eliminar TODAS las relaciones
        productoEtiquetaSucursalRepository.deleteByEtiquetaIdAndContexto(id, Contexto.SUCURSAL);

        // eliminar la etiqueta
        repository.deleteById(id);
    }

    @Transactional
    private EtiquetaDTO toDTO(Etiqueta etiqueta) {
        return new EtiquetaDTO(
                etiqueta.getId(),
                etiqueta.getValor(),
                Contexto.SUCURSAL,
                etiqueta.getHabilitado()
        );
    }
}
