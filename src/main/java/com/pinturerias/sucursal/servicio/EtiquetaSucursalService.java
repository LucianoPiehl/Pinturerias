package com.pinturerias.sucursal.servicio;

import com.pinturerias.compartidos.dto.EtiquetaCreateDTO;
import com.pinturerias.compartidos.dto.EtiquetaDTO;
import com.pinturerias.compartidos.enumeracion.Contexto;
import com.pinturerias.compartidos.servicio.NormalizadorEtiquetaService;
import com.pinturerias.compartidos.servicio.ValidadorDuplicidadEtiquetaService;
import com.pinturerias.excepciones.RecursoNoEncontradoException;
import com.pinturerias.sucursal.entidad.EtiquetaSucursal;
import com.pinturerias.sucursal.repositorio.EtiquetaSucursalRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EtiquetaSucursalService {

    private final EtiquetaSucursalRepository repository;
    private final NormalizadorEtiquetaService normalizadorEtiquetaService;
    private final ValidadorDuplicidadEtiquetaService validadorDuplicidadEtiquetaService;

    public EtiquetaSucursalService(EtiquetaSucursalRepository repository,
                                   NormalizadorEtiquetaService normalizadorEtiquetaService,
                                   ValidadorDuplicidadEtiquetaService validadorDuplicidadEtiquetaService) {
        this.repository = repository;
        this.normalizadorEtiquetaService = normalizadorEtiquetaService;
        this.validadorDuplicidadEtiquetaService = validadorDuplicidadEtiquetaService;
    }

    public List<EtiquetaDTO> listarLocales() {
        return repository.findAll().stream()
                .map(this::toDTO)
                .toList();
    }

    public EtiquetaDTO crear(EtiquetaCreateDTO dto) {
        String valorVisible = normalizadorEtiquetaService.normalizarValorVisible(dto.getValor());
        String claveNormalizada = normalizadorEtiquetaService.generarClaveNormalizada(valorVisible);
        validadorDuplicidadEtiquetaService.validarClaveLibreEnTodoElSistema(claveNormalizada);

        EtiquetaSucursal etiqueta = new EtiquetaSucursal();
        etiqueta.setValor(valorVisible);
        etiqueta.setClaveNormalizada(claveNormalizada);

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
}
