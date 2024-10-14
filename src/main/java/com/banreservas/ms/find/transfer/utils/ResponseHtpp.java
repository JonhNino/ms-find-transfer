package com.banreservas.ms.find.transfer.utils;

import com.banreservas.ms.find.transfer.dto.ErrorResponse;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.config.inject.ConfigProperty;
@ApplicationScoped
public class ResponseHtpp {
    @ConfigProperty(name = "quarkus.application.name")
    String microserviceName;
    public Response crearRespuestaError(Response.Status status, String errorCode, String errorMessage) {
        ErrorResponse errorResponse = new ErrorResponse(microserviceName, status.getStatusCode(), errorCode, errorMessage);
        return Response.status(status)
                .entity(errorResponse) // Se asegura de que la entidad tenga el cuerpo JSON
                .type(MediaType.APPLICATION_JSON) // Asegura que la respuesta sea en formato JSON
                .build();
    }
}
