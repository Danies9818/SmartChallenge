package com.smartChallenge.controller;

import com.smartChallenge.ExceptionController.RestExceptionHandler;
import com.smartChallenge.dto.UserRequest;
import com.smartChallenge.dto.UserResponse;
import com.smartChallenge.exception.ValidationException;
import com.smartChallenge.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @Operation(summary = "Registrar un nuevo usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario registrado exitosamente",
                    content = @Content(schema = @Schema(implementation = UserResponse.class))),
            @ApiResponse(responseCode = "400", description = "Error en los datos proporcionados",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = RestExceptionHandler.ErrorResponse.class)
                    )
            ),
            @ApiResponse(responseCode = "500", description = "Error en el servidor",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = RestExceptionHandler.ErrorResponse.class)
                    ))
    })
    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody UserRequest userRequest) {
        try {
            UserResponse user = userService.saveUser(userRequest);
            return ResponseEntity.ok(user);
        }catch (ValidationException ex){
            return ResponseEntity.badRequest().body(Map.of("mensaje", ex.getMessage()));
        }

    }
}
