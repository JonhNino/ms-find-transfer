package com.banreservas.ms.find.transfer.exception;

import com.banreservas.ms.find.transfer.dto.ErrorResponse;
import io.quarkus.security.UnauthorizedException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@Provider
public class CustomAuthenticationExceptionMapper implements ExceptionMapper<UnauthorizedException> {

    @ConfigProperty(name = "quarkus.application.name")
    String microserviceName;

    @Override
    public Response toResponse(UnauthorizedException exception) {
        ErrorResponse errorResponse = new ErrorResponse(
                microserviceName,
                Response.Status.UNAUTHORIZED.getStatusCode(),
                "UNAUTHORIZED",
                "No autorizado: Se requiere autenticación para acceder a este recurso"
        );

        return Response.status(Response.Status.UNAUTHORIZED)
                .entity(errorResponse)
                .build();
    }
}