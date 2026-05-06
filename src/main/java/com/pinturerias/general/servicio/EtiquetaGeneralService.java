package com.pinturerias.general.servicio;

import com.pinturerias.compartidos.dto.EtiquetaCreateDTO;
import com.pinturerias.compartidos.dto.EtiquetaDTO;
import com.pinturerias.compartidos.entidad.shared.Etiqueta;
import com.pinturerias.compartidos.enumeracion.Contexto;
import com.pinturerias.compartidos.servicio.NormalizadorEtiquetaService;
import com.pinturerias.compartidos.servicio.ValidadorDuplicidadEtiquetaService;
import com.pinturerias.excepciones.RecursoNoEncontradoException;
import com.pinturerias.general.repositorio.EtiquetaGeneralRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EtiquetaGeneralService {

    private final EtiquetaGeneralRepository repository;
    private final NormalizadorEtiquetaService normalizador;
    private final ValidadorDuplicidadEtiquetaService validador;

    public List<EtiquetaDTO> listar() {
        return repository.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }
    public List<EtiquetaDTO> listarDisponibles() {
        return repository.findByHabilitadoTrue()
                .stream()
                .map(this::toDTO)
                .toList();
    }
    public boolean etiquetaExistente(String claveNormalizada) {
        return repository.existsByClaveNormalizada(claveNormalizada);
    }

    public EtiquetaDTO crear(EtiquetaCreateDTO dto) {

        //Normalización
        String valorVisible = normalizador.normalizarValorVisible(dto.getValor());
        String claveNormalizada = normalizador.generarClaveNormalizada(valorVisible);

        //Validación
        validador.validarClaveLibreGeneral(claveNormalizada);

        //Creación
        Etiqueta etiqueta = Etiqueta.builder()
                .valor(valorVisible)
                .claveNormalizada(claveNormalizada)
                .build();

        return toDTO(repository.save(etiqueta));
    }

    public void deshabilitar(Long id) {
        if (!repository.existsById(id)) {
            throw new RecursoNoEncontradoException("Etiqueta general no encontrada");
        }
        Etiqueta etiqueta = repository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("..."));
        etiqueta.setHabilitado(Boolean.parseBoolean("false"));
        repository.save(etiqueta);

    }

    private EtiquetaDTO toDTO(Etiqueta etiqueta) {
        return EtiquetaDTO.builder()
                .id(etiqueta.getId())
                .valor(etiqueta.getValor())
                .contexto(Contexto.GENERAL)
                .habilitado(etiqueta.getHabilitado())
                .build();
    }
}
