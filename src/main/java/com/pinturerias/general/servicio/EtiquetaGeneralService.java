package com.pinturerias.general.servicio;

import com.pinturerias.compartidos.dto.EtiquetaCreateDTO;
import com.pinturerias.compartidos.dto.EtiquetaDTO;
import com.pinturerias.compartidos.enumeracion.Contexto;
import com.pinturerias.compartidos.servicio.NormalizadorEtiquetaService;
import com.pinturerias.compartidos.servicio.ValidadorDuplicidadEtiquetaService;
import com.pinturerias.excepciones.RecursoNoEncontradoException;
import com.pinturerias.general.entidad.EtiquetaGeneral;
import com.pinturerias.general.repositorio.EtiquetaGeneralRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EtiquetaGeneralService {

    private final EtiquetaGeneralRepository repository;
    private final NormalizadorEtiquetaService normalizadorEtiquetaService;
    private final ValidadorDuplicidadEtiquetaService validadorDuplicidadEtiquetaService;

    public EtiquetaGeneralService(EtiquetaGeneralRepository repository,
                                  NormalizadorEtiquetaService normalizadorEtiquetaService,
                                  ValidadorDuplicidadEtiquetaService validadorDuplicidadEtiquetaService) {
        this.repository = repository;
        this.normalizadorEtiquetaService = normalizadorEtiquetaService;
        this.validadorDuplicidadEtiquetaService = validadorDuplicidadEtiquetaService;
    }

    public List<EtiquetaDTO> listar() {
        return repository.findAll().stream()
                .map(this::toDTO)
                .toList();
    }

    public EtiquetaDTO crear(EtiquetaCreateDTO dto) {
        String valorVisible = normalizadorEtiquetaService.normalizarValorVisible(dto.getValor());
        String claveNormalizada = normalizadorEtiquetaService.generarClaveNormalizada(valorVisible);
        validadorDuplicidadEtiquetaService.validarClaveLibreEnTodoElSistema(claveNormalizada);

        EtiquetaGeneral etiqueta = new EtiquetaGeneral();
        etiqueta.setValor(valorVisible);
        etiqueta.setClaveNormalizada(claveNormalizada);

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
}
