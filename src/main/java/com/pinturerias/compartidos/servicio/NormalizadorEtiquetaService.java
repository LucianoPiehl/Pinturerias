package com.pinturerias.compartidos.servicio;

import com.pinturerias.excepciones.ExcepcionApi;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.util.Arrays;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class NormalizadorEtiquetaService {

    public String normalizarValorVisible(String valor) {
        if (valor == null || valor.isBlank()) {
            throw new ExcepcionApi(400, "El valor de la etiqueta no puede estar vacio");
        }
        return valor.trim().replaceAll("\\s+", " ");
    }

    public String generarClaveNormalizada(String valor) {
        String limpio = normalizarValorVisible(valor).toLowerCase(Locale.ROOT);
        limpio = Normalizer.normalize(limpio, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "");
        limpio = limpio.replaceAll("[^a-z0-9\\s]", " ")
                .replaceAll("\\s+", " ")
                .trim();

        if (limpio.isBlank()) {
            throw new ExcepcionApi(400, "La etiqueta no tiene caracteres validos");
        }

        return Arrays.stream(limpio.split(" "))
                .map(this::singularizar)
                .collect(Collectors.joining(" "));
    }

    private String singularizar(String token) {
        if (token.length() <= 4) {
            return token;
        }

        if (token.endsWith("es") && token.length() > 5) {
            String base = token.substring(0, token.length() - 2);
            if (!base.endsWith("s")) {
                return base;
            }
        }

        if (token.endsWith("s")
                && !token.endsWith("ss")
                && !token.endsWith("us")
                && !token.endsWith("is")) {
            return token.substring(0, token.length() - 1);
        }

        return token;
    }
}
