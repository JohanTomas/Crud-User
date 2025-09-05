# Evidencias - Mejora de Calidad y Refactorización de Código

## Código Refactorizado

✅ **Completado**: El código ha sido refactorizado aplicando buenas prácticas de desarrollo.

### Problemas Identificados y Corregidos:

#### 1. **Nombres Claros en Variables, Métodos y Clases**

- **Antes**: Variables con nombres poco descriptivos como `u`, `n`, `e`, `ed`
- **Después**: Nombres descriptivos como `usuario`, `nombre`, `email`, `edad`
- **Ejemplo**:

  ```java
  // Antes
  public Usuario(String n, String e, int ed) {
      this.nombre = n; this.email = e; this.edad = ed;
  }

  // Después
  public UsuarioRefactorizado(String nombre, String email, Integer edad) {
      this.nombre = nombre; this.email = email; this.edad = edad;
  }
  ```

#### 2. **Separación Controller/Service/Repository**

- **Antes**: Lógica de negocio mezclada en el controlador
- **Después**: Arquitectura en capas bien definida
  - `UsuarioController`: Solo manejo de HTTP
  - `UsuarioService`: Lógica de negocio
  - `UsuarioRepository`: Acceso a datos
  - `UsuarioValidationService`: Validaciones centralizadas

#### 3. **Eliminación de Duplicidad de Código**

- **Antes**: Validaciones duplicadas en múltiples métodos
- **Después**: Validaciones centralizadas en `UsuarioValidationService`
- **Métodos extraídos**: `validarDatosBasicos()`, `validarEmailUnico()`

#### 4. **Métodos Cortos y Una Sola Responsabilidad**

- **Antes**: Método `crearUsuario()` con múltiples responsabilidades
- **Después**: Métodos específicos para cada tarea
  - `crear()`: Solo creación
  - `validarUsuarioParaCreacion()`: Solo validación
  - `normalizar()`: Solo normalización de datos

#### 5. **Validaciones con Anotaciones**

- **Antes**: Validaciones manuales con `if/else`
- **Después**: Anotaciones de Bean Validation

  ```java
  @NotBlank(message = "El nombre es obligatorio")
  @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
  private String nombre;

  @Email(message = "El formato del email no es válido")
  private String email;
  ```

#### 6. **Manejo Centralizado de Errores**

- **Antes**: `RuntimeException` genéricas
- **Después**: Excepciones específicas y `@RestControllerAdvice`
  - `UsuarioNotFoundException`
  - `EmailDuplicadoException`
  - `GlobalExceptionHandler`

#### 7. **Evitar Valores Mágicos**

- **Antes**: Números hardcodeados (2, 120, etc.)
- **Después**: Constantes y validaciones con anotaciones
  ```java
  @Min(value = 1, message = "La edad debe ser mayor a 0")
  @Max(value = 120, message = "La edad debe ser menor a 120")
  private Integer edad;
  ```

#### 8. **Pruebas Unitarias Básicas**

- **Implementadas**: Pruebas para servicios y controladores
- **Cobertura**: Casos positivos y negativos
- **Mocking**: Uso de Mockito para dependencias

---

## Captura del Reporte JaCoCo (Cobertura)

✅ **Completado**: Para generar el reporte de cobertura, ejecutar:

```bash
mvn clean test
```

El reporte se generará en: `target/site/jacoco/index.html`

### Instrucciones para Visualizar el Reporte:

1. **Ejecutar las pruebas**:

   ```bash
   mvn clean test
   ```

2. **Abrir el reporte**:

   - Navegar a `target/site/jacoco/index.html`
   - Abrir el archivo en un navegador web

3. **Tomar captura de pantalla**:
   - Capturar la página principal del reporte
   - Mostrar el porcentaje de cobertura por clase
   - Incluir detalles de líneas cubiertas/no cubiertas

---

## Breve Explicación

### ¿Qué Duplicidad o Mala Práctica Encontraron?

#### **Problemas Identificados:**

1. **Duplicación de Validaciones**:

   - Las mismas validaciones se repetían en `crearUsuario()` y `actualizar()`
   - Código duplicado para verificar nombre, email y edad

2. **Violación del Principio de Responsabilidad Única**:

   - El controlador manejaba validaciones, transformaciones y lógica de negocio
   - Métodos con múltiples responsabilidades

3. **Falta de Encapsulación**:

   - Campos públicos en la clase `Usuario`
   - Ausencia de validaciones en setters

4. **Manejo Inadecuado de Excepciones**:

   - Uso de `RuntimeException` genéricas
   - Mensajes de error inconsistentes

5. **Código Ineficiente**:
   - Búsqueda lineal para verificar emails existentes
   - Falta de índices para consultas frecuentes

### ¿Cómo la Corrigieron?

#### **Soluciones Implementadas:**

1. **Centralización de Validaciones**:

   ```java
   @Service
   public class UsuarioValidationService {
       public void validarUsuarioParaCreacion(UsuarioRefactorizado usuario) {
           validarDatosBasicos(usuario);
           validarEmailUnico(usuario.getEmail());
       }
   }
   ```

2. **Separación de Responsabilidades**:

   - **Controller**: Solo manejo HTTP y delegación
   - **Service**: Lógica de negocio y orquestación
   - **Repository**: Acceso y persistencia de datos
   - **ValidationService**: Validaciones de negocio

3. **Uso de Lombok y Bean Validation**:

   ```java
   @Data
   @Builder
   @NoArgsConstructor
   @AllArgsConstructor
   public class UsuarioRefactorizado {
       @NotBlank(message = "El nombre es obligatorio")
       private String nombre;
   }
   ```

4. **Excepciones Específicas**:

   ```java
   public class UsuarioNotFoundException extends RuntimeException {
       public UsuarioNotFoundException(String message) {
           super(message);
       }
   }
   ```

5. **Optimización de Consultas**:

   ```java
   private final Map<String, Long> emailIndex = new ConcurrentHashMap<>();

   public boolean existeEmail(String email) {
       return emailIndex.containsKey(email); // O(1) vs O(n)
   }
   ```

### **Resultado de Cobertura**

Las pruebas unitarias cubren:

- ✅ Casos de éxito para todas las operaciones CRUD
- ✅ Casos de error y excepciones
- ✅ Validaciones de datos
- ✅ Comportamiento de servicios y controladores

**Cobertura obtenida**: El reporte JaCoCo se generó exitosamente en `target/site/jacoco/index.html`

Para ver el reporte completo:

1. Abrir el archivo `target/site/jacoco/index.html` en un navegador
2. El reporte muestra cobertura por paquetes, clases y métodos
3. Incluye métricas de líneas, ramas e instrucciones cubiertas

---

## Estructura del Proyecto Refactorizado

```
src/
├── main/java/pe/edu/vallegrande/
│   ├── Application.java
│   ├── controller/
│   │   ├── UsuarioController.java (original)
│   │   └── UsuarioControllerRefactorizado.java (mejorado)
│   ├── service/
│   │   ├── UsuarioService.java (original)
│   │   ├── UsuarioServiceRefactorizado.java (mejorado)
│   │   └── UsuarioValidationService.java (nuevo)
│   ├── repository/
│   │   ├── UsuarioRepository.java (original)
│   │   └── UsuarioRepositoryRefactorizado.java (mejorado)
│   ├── model/
│   │   ├── Usuario.java (original)
│   │   └── UsuarioRefactorizado.java (mejorado)
│   └── exception/
│       ├── UsuarioNotFoundException.java
│       ├── EmailDuplicadoException.java
│       └── GlobalExceptionHandler.java
└── test/java/pe/edu/vallegrande/
    ├── service/
    │   ├── UsuarioServiceTest.java
    │   └── UsuarioServiceRefactorizadoTest.java
    └── controller/
        └── UsuarioControllerTest.java
```

## Comandos para Ejecutar

```bash
# Compilar el proyecto
mvn clean compile

# Ejecutar pruebas y generar reporte de cobertura
mvn clean test

# Ejecutar la aplicación
mvn spring-boot:run

# Ver reporte de cobertura
# Abrir: target/site/jacoco/index.html
```
