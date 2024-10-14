package com.banreservas.ms.find.transfer.resource;

import com.banreservas.ms.find.transfer.dto.ErrorResponse;
import com.banreservas.ms.find.transfer.dto.TransferenciaEstado;
import com.banreservas.ms.find.transfer.utils.ResponseHtpp;
import io.quarkus.security.Authenticated;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

import java.util.Optional;

@Path("/transferencias")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)

public class TransferenciaResource {

    @ConfigProperty(name = "quarkus.application.name")
    String microserviceName;

    @Inject
    ResponseHtpp responseHtpp;

    @GET
    @Path("/{codigoUnico : .*}")
    @Operation(
            summary = "Consultar el estado de una transferencia",
            description = "Permite consultar el estado de una transferencia mediante un código único de 11 dígitos."
    )
    @APIResponses({
            @APIResponse(
                    responseCode = "200",
                    description = "Transferencia encontrada correctamente",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = TransferenciaEstado.class))
            ),
            @APIResponse(
                    responseCode = "400",
                    description = "Solicitud inválida: El formato del código único no es correcto",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class))
            ),
            @APIResponse(
                    responseCode = "401",
                    description = "No autorizado: Se requiere autenticación para acceder a este recurso",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class))
            ),
            @APIResponse(
                    responseCode = "403",
                    description = "Prohibido: No tiene permisos para acceder a esta información",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class))
            ),
            @APIResponse(
                    responseCode = "404",
                    description = "No encontrado: No se encontró ninguna transferencia con el código único proporcionado",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class))
            ),
            @APIResponse(
                    responseCode = "500",
                    description = "Error interno del servidor: Ocurrió un problema al procesar la solicitud",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class))
            ),
            @APIResponse(
                    responseCode = "503",
                    description = "Servicio no disponible: El servicio de consulta de transferencias está temporalmente fuera de servicio",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    //@Authenticated
   // @RolesAllowed({"user", "admin"})
    public Response consultarEstadoTransferencia(
            @Parameter(description = "Código único de la transferencia (número entero de 11 dígitos)", required = true)
            @PathParam("codigoUnico") String codigoUnico) {

        // Validar si el código único es nulo, vacío o tiene un formato incorrecto
        if (!isCodigoUnicoValido(codigoUnico)) {
            return responseHtpp.crearRespuestaError(Response.Status.BAD_REQUEST, "INVALID_CODE", "El código único debe ser un número de 11 dígitos.");
        }

        Long codigoUnicoLong = Long.parseLong(codigoUnico);
        try {
            Optional<TransferenciaEstado> transferencia = obtenerTransferenciaPorCodigo(codigoUnicoLong);

            if (transferencia.isPresent()) {
                return Response.ok(transferencia.get()).build();
            } else {
                return responseHtpp.crearRespuestaError(Response.Status.NOT_FOUND, "NOT_FOUND", "Transferencia no encontrada.");
            }

       } catch (ServiceUnavailableException e) {
            return responseHtpp.crearRespuestaError(Response.Status.SERVICE_UNAVAILABLE, "SERVICE_UNAVAILABLE", "El servicio está temporalmente fuera de servicio.");
        } catch (IllegalArgumentException e) {
            return responseHtpp.crearRespuestaError(Response.Status.BAD_REQUEST, "ILLEGAL_ARGUMENT", "Argumento ilegal proporcionado.");
        } catch (NullPointerException e) {
            return responseHtpp.crearRespuestaError(Response.Status.INTERNAL_SERVER_ERROR, "NULL_POINTER", "Ocurrió un error inesperado al procesar la solicitud.");
        } catch (Exception e) {
            return responseHtpp.crearRespuestaError(Response.Status.INTERNAL_SERVER_ERROR, "UNKNOWN_ERROR", "Ocurrió un error inesperado: " + e.getMessage());
        }
    }
    private boolean isCodigoUnicoValido(String codigoUnico) {
        // Verificar si el código es nulo o vacío, y si es un número de exactamente 11 dígitos
        return codigoUnico != null && codigoUnico.matches("\\d{11}");
    }
    private Optional<TransferenciaEstado> obtenerTransferenciaPorCodigo(Long codigoUnico) {
        if (codigoUnico.equals(12345678901L)) {
            return Optional.of(new TransferenciaEstado(codigoUnico, "Completada", "Transferencia realizada con éxito"));
        }
        return Optional.empty();
    }
    
}
