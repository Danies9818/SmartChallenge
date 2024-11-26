package com.smartChallenge.ExceptionController;

import com.smartChallenge.controller.UserController;
import com.smartChallenge.exception.ValidationException;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

//@RestControllerAdvice(assignableTypes = UserController.class)
public class RestExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(RestExceptionHandler.class);


    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<?> handleValidationException(ValidationException ex) {
        logger.error(ex.getMessage());
        return ResponseEntity.badRequest().body(Map.of("mensaje", ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGenericException(Exception ex, HttpServletRequest request) {
        String path = request.getRequestURI();
        if (path.startsWith("/v3/api-docs") || path.startsWith("/swagger-ui")) {
            logger.warn("Excepción ignorada en Swagger: {}", ex.getMessage());
            return null; // Permite que Swagger gestione la excepción internamente
        }
        logger.error("Error: {}", ex.getMessage(), ex);
        return ResponseEntity.internalServerError().body(Map.of("mensaje", "Error interno del servidor"));
    }

    @Schema(description = "Estructura de la respuesta de error")
    public class ErrorResponse {

        @Schema(description = "Mensaje descriptivo del error", example = "error:")
        private String mensaje;

        public ErrorResponse(String mensaje) {
            this.mensaje = mensaje;
        }

        public String getMensaje() {
            return mensaje;
        }

        public void setMensaje(String mensaje) {
            this.mensaje = mensaje;
        }
    }

}
