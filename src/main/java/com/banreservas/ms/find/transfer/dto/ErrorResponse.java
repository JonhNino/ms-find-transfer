package com.banreservas.ms.find.transfer.dto;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.AllArgsConstructor;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Clase que representa una respuesta de error estandarizada para el microservicio.
 * Esta clase se utiliza para encapsular los detalles de un error ocurrido durante el procesamiento de una solicitud.
 *
 * @author Ing. John Niño
 * @since 2024-09-30
 * @version 1.1
 */
@Data
@AllArgsConstructor
@RegisterForReflection
@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorResponse {

    @NotNull(message = "El nombre del microservicio no puede ser nulo")
    @JsonProperty(value = "microservice_name", required = true)
    @Size(max = 100, message = "El nombre del microservicio no puede exceder los 100 caracteres")
    private String microserviceName;

    @NotNull(message = "El código de estado HTTP no puede ser nulo")
    @JsonProperty(value = "status", required = true)
    private Integer status;

    @NotNull(message = "El código de error no puede ser nulo")
    @JsonProperty(value = "error_code", required = true)
    @Size(max = 50, message = "El código de error no puede exceder los 50 caracteres")
    private String errorCode;

    @JsonProperty(value = "error_message")
    @Size(max = 255, message = "El mensaje de error no puede exceder los 255 caracteres")
    private String errorMessage;
}