package com.banreservas.ms.find.transfer.dto;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@RegisterForReflection
@JsonIgnoreProperties(ignoreUnknown = true)
public class TransferenciaEstado {

    @NotNull(message = "El código único no puede ser nulo")
    @JsonProperty(value = "codigo_unico", required = true)
    @Size(min = 11, max = 11, message = "El código único debe tener exactamente 11 dígitos")
    private Long codigoUnico;

    @NotNull(message = "El estado no puede ser nulo")
    @JsonProperty(value = "estado", required = true)
    @Size(max = 50, message = "El estado no puede exceder los 50 caracteres")
    private String estado;

    @JsonProperty(value = "descripcion")
    @Size(max = 255, message = "La descripción no puede exceder los 255 caracteres")
    private String descripcion;
}