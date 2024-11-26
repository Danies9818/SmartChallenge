
# **Proyecto Registro de Usuarios (Swagger, H2, Spring Boot)**

Este proyecto es una aplicación basada en Spring Boot que incluye la documentación de la API utilizando **Swagger** (Springdoc OpenAPI). Proporciona endpoints RESTful para la gestión de usuarios con validaciones y manejo global de excepciones.

---

## **Tecnologías Utilizadas**

- **Java 17**
- **Spring Boot 3.x**
- **Spring Web**
- **Springdoc OpenAPI (Swagger)**
- **H2 Database** (Base de datos en memoria para pruebas)
- **Maven**

---

## **Características del Proyecto**

1. **Endpoints RESTful**:
    - Registro de usuarios.
    - Manejo de validaciones personalizadas.
    - Gestión de errores global.

2. **Documentación con Swagger**:
    - **Swagger UI** disponible en: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html).
    - Especificación OpenAPI en formato JSON: [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs).

3. **Base de Datos en Memoria (H2)**:
    - Consola H2 disponible en: [http://localhost:8080/h2-console](http://localhost:8080/h2-console).

4. **Manejador Global de Excepciones**:
    - Captura y retorna mensajes de error personalizados.

---

## **Instalación y Configuración**

### **Prerrequisitos**
- Java 17 o superior.
- Maven 3.8.x o superior.

### **Clonar el Repositorio**
```bash
git clone https://github.com/Danies9818/SmartChallenge.git
```

### **Compilar y Ejecutar**
1. Compila el proyecto:
   ```bash
   mvn clean install
   ```

2. Ejecuta la aplicación:
   ```bash
   mvn spring-boot:run
   ```

3. La aplicación estará disponible en: [http://localhost:8080](http://localhost:8080).

---

## **Uso de la API**

### **Endpoints Disponibles**

| Método | Endpoint           | Descripción                      |
|--------|--------------------|----------------------------------|
| POST   | `/api/users`       | Registrar un nuevo usuario       | |

### **Ejemplo de Petición**

#### **Registrar un Usuario**
- **URL**: `/api/users`
- **Método**: `POST`
- **Cuerpo de la Petición**:
  ```json
  {
      "name": "Juan Perez",
      "email": "juan.perez@example.com",
      "password": "Password123!",
      "phones": [
          {
              "number": "123456789",
              "citycode": "1",
              "contrycode": "57"
          }
      ]
  }
  ```
- **Respuesta Exitosa**:
  ```json
  {
      "id": "123e4567-e89b-12d3-a456-426614174000",
      "name": "Juan Perez",
      "email": "juan.perez@example.com",
      "created": "2024-11-26T10:00:00",
      "modified": "2024-11-26T10:00:00",
      "last_login": "2024-11-26T10:00:00",
      "token": "jwt-token-aqui",
      "isActive": true
  }
  ```

- **Errores Posibles**:
    - **Correo ya registrado**:
      ```json
      {
          "mensaje": "El correo ya registrado"
      }
      ```

---

## **Configuración Adicional**

### **Base de Datos H2**
La aplicación utiliza H2 en memoria para pruebas. Puedes acceder a la consola de la base de datos en:
- **URL**: [http://localhost:8080/h2-console](http://localhost:8080/h2-console)
- **JDBC URL**: `jdbc:h2:mem:testdb`
- **Usuario**: `sa`
- **Contraseña**: *(dejar vacío)*

### **Configuración de Swagger**
Swagger UI está habilitado por defecto. Los endpoints relacionados son:
- **Documentación Swagger UI**: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)
- **OpenAPI JSON**: [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)

---

## **Estructura del Proyecto**

```plaintext
src
├── main
│   ├── java
│   │   └── com.smartChallenge
│   │       ├── config
│   │       │   ├── SecurityConfig.java       # Configuración de seguridad
│   │       │   ├── SwaggerConfig.java        # Configuración de Swagger
│   │       ├── controller
│   │       │   └── UserController.java       # Controlador REST para usuarios
│   │       ├── dto
│   │       │   ├── UserRequest.java          # DTO para solicitudes de usuarios
│   │       │   ├── UserResponse.java         # DTO para respuestas de usuarios
│   │       ├── exception
│   │       │   ├── ValidationException.java  # Excepción personalizada
│   │       │   ├── ExceptionController.java  # Controlador de excepciones
│   │       │   ├── RestExceptionHandler.java # Manejador global de excepciones
│   │       ├── jwt
│   │       │   └── JWTUtil.java              # Utilidad para generación de JWT
│   │       ├── model
│   │       │   ├── Phone.java                # Modelo para teléfonos
│   │       │   ├── User.java                 # Modelo para usuarios
│   │       ├── repository
│   │       │   └── UserRepository.java       # Repositorio para operaciones con usuarios
│   │       ├── service
│   │       │   └── UserService.java          # Lógica de negocio para usuarios
│   │       ├── transformer
│   │       │   └── UserTransformer.java      # Transformaciones entre entidades y DTOs
│   │       ├── util
│   │       │   └── Validation.java           # Utilidades para validaciones
│   │       └── SmartChallengeApplication.java # Clase principal de la aplicación
│   └── resources
│       ├── static                            # Recursos estáticos
│       ├── templates                         # Plantillas (si aplica)
│       ├── application.properties            # Configuración de la aplicación
└── test                                      # Pruebas unitarias e integración
                                # Pruebas unitarias e integración
```

---

## **Pruebas**

Ejecuta las pruebas con Maven:
```bash
mvn test
```

Las pruebas incluyen:
- Validaciones de controladores con **MockMvc**.
- Simulación de servicios con **Mockito**.

---


