# Spring Boot Best Practices Guide

## Table of Contents
1. [Core Principles](#core-principles)
2. [Dependency Injection & IoC](#dependency-injection--ioc)
3. [Architecture & Design](#architecture--design)
4. [REST API Development](#rest-api-development)
5. [Data Access Layer](#data-access-layer)
6. [Testing Strategy](#testing-strategy)
7. [Configuration Management](#configuration-management)
8. [Exception Handling](#exception-handling)
9. [Security](#security)
10. [Performance Optimization](#performance-optimization)
11. [Monitoring & Observability](#monitoring--observability)
12. [Documentation](#documentation)

---

## Core Principles

### DRY (Don't Repeat Yourself)
- **Extract common logic** into reusable services, utilities, or base classes
- **Use Spring's abstractions** like `@Service`, `@Component` to avoid boilerplate
- **Leverage Spring Boot starters** to avoid manual configuration
- **Create custom annotations** for cross-cutting concerns

### SOLID Principles
- **Single Responsibility**: Each class should have one reason to change
- **Open/Closed**: Classes should be open for extension, closed for modification
- **Liskov Substitution**: Derived classes must be substitutable for base classes
- **Interface Segregation**: Many specific interfaces are better than one general interface
- **Dependency Inversion**: Depend on abstractions, not concretions

---

## Dependency Injection & IoC

### Constructor Injection (Preferred)
```java
@Service
public class OrderService {
    private final PaymentService paymentService;
    private final InventoryService inventoryService;
    
    // Constructor injection - preferred for mandatory dependencies
    public OrderService(PaymentService paymentService, 
                       InventoryService inventoryService) {
        this.paymentService = paymentService;
        this.inventoryService = inventoryService;
    }
}
```

### Best Practices
- **Prefer constructor injection** for mandatory dependencies (immutable, testable)
- **Use setter injection** only for optional dependencies
- **Avoid field injection** except in tests (harder to test, hidden dependencies)
- **Use `@Qualifier`** when multiple beans of same type exist
- **Leverage `@Primary`** for default bean selection

---

## Architecture & Design

### Layered Architecture
```
├── Controller Layer (REST endpoints)
├── Service Layer (Business logic)
├── Repository Layer (Data access)
└── Entity/Model Layer (Domain objects)
```

### Package Structure
```
com.company.app
├── config/          # Configuration classes
├── controller/      # REST controllers
├── service/         # Business logic
├── repository/      # Data access
├── entity/          # JPA entities
├── dto/            # Data Transfer Objects
├── mapper/         # Entity-DTO mappers
├── exception/      # Custom exceptions
└── util/           # Utility classes
```

### Clean Architecture Principles
- **Separate concerns** between layers
- **Use interfaces** for layer boundaries
- **Keep domain logic** independent of frameworks
- **Apply dependency rule**: Inner layers shouldn't know about outer layers

---

## REST API Development

### RESTful Design
```java
@RestController
@RequestMapping("/api/v1/users")
@Validated
public class UserController {
    
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findById(id));
    }
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto createUser(@Valid @RequestBody CreateUserRequest request) {
        return userService.create(request);
    }
    
    @PutMapping("/{id}")
    public UserDto updateUser(@PathVariable Long id, 
                             @Valid @RequestBody UpdateUserRequest request) {
        return userService.update(id, request);
    }
    
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long id) {
        userService.delete(id);
    }
}
```

### Best Practices
- **Use proper HTTP methods** (GET, POST, PUT, DELETE, PATCH)
- **Return appropriate status codes** (200, 201, 204, 400, 404, 500)
- **Version your APIs** (`/api/v1/`)
- **Use DTOs** instead of entities in responses
- **Implement pagination** for list endpoints
- **Add validation** with `@Valid` and Bean Validation

---

## Data Access Layer

### Repository Pattern
```java
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    Optional<User> findByEmail(String email);
    
    @Query("SELECT u FROM User u WHERE u.status = :status")
    List<User> findByStatus(@Param("status") UserStatus status);
    
    @Modifying
    @Query("UPDATE User u SET u.lastLogin = :date WHERE u.id = :id")
    void updateLastLogin(@Param("id") Long id, @Param("date") LocalDateTime date);
}
```

### Transaction Management
```java
@Service
@Transactional(readOnly = true)
public class UserService {
    
    @Transactional
    public User createUser(CreateUserRequest request) {
        // Write operation - needs @Transactional
        return userRepository.save(user);
    }
    
    public User findById(Long id) {
        // Read-only operation - uses class-level readOnly
        return userRepository.findById(id)
            .orElseThrow(() -> new UserNotFoundException(id));
    }
}
```

---

## Testing Strategy

### Unit Testing
```java
@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    
    @Mock
    private UserRepository userRepository;
    
    @InjectMocks
    private UserService userService;
    
    @Test
    void shouldCreateUser() {
        // Given
        CreateUserRequest request = new CreateUserRequest("John", "john@example.com");
        User user = new User("John", "john@example.com");
        when(userRepository.save(any(User.class))).thenReturn(user);
        
        // When
        User result = userService.createUser(request);
        
        // Then
        assertNotNull(result);
        assertEquals("John", result.getName());
        verify(userRepository, times(1)).save(any(User.class));
    }
}
```

### Integration Testing
```java
@SpringBootTest
@AutoConfigureMockMvc
class UserControllerIntegrationTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @Test
    void shouldGetUser() throws Exception {
        mockMvc.perform(get("/api/v1/users/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("John"));
    }
}
```

### Testing Best Practices
- **Write tests first** (TDD approach when possible)
- **Isolate unit tests** with mocks
- **Use `@DataJpaTest`** for repository tests
- **Use `@WebMvcTest`** for controller tests
- **Keep tests independent** and repeatable
- **Aim for 80%+ code coverage**

---

## Configuration Management

### Application Properties
```yaml
# application.yml
spring:
  application:
    name: user-service
  
  datasource:
    url: ${DB_URL:jdbc:postgresql://localhost:5432/userdb}
    username: ${DB_USERNAME:postgres}
    password: ${DB_PASSWORD:password}
  
  jpa:
    hibernate:
      ddl-auto: validate
    show-sql: false

logging:
  level:
    root: INFO
    com.company.app: DEBUG
  pattern:
    console: "%d{yyyy-MM-dd HH:mm:ss} - %msg%n"
```

### Profile-Specific Configuration
```yaml
# application-dev.yml
spring:
  jpa:
    show-sql: true
    hibernate:
      ddl-auto: update

# application-prod.yml
spring:
  jpa:
    show-sql: false
    hibernate:
      ddl-auto: validate
```

### Configuration Properties Class
```java
@Component
@ConfigurationProperties(prefix = "app")
@Validated
public class AppProperties {
    
    @NotNull
    private String name;
    
    @Min(1)
    @Max(100)
    private int maxConnections = 10;
    
    private Security security = new Security();
    
    @Data
    public static class Security {
        private String jwtSecret;
        private long jwtExpiration = 86400;
    }
}
```

---

## Exception Handling

### Global Exception Handler
```java
@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFound(ResourceNotFoundException ex) {
        return ErrorResponse.builder()
            .timestamp(LocalDateTime.now())
            .status(HttpStatus.NOT_FOUND.value())
            .error("Resource Not Found")
            .message(ex.getMessage())
            .build();
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> errors = ex.getBindingResult()
            .getFieldErrors()
            .stream()
            .collect(Collectors.toMap(
                FieldError::getField,
                FieldError::getDefaultMessage
            ));
        
        return ErrorResponse.builder()
            .timestamp(LocalDateTime.now())
            .status(HttpStatus.BAD_REQUEST.value())
            .error("Validation Failed")
            .validationErrors(errors)
            .build();
    }
    
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleGeneral(Exception ex) {
        log.error("Unexpected error", ex);
        return ErrorResponse.builder()
            .timestamp(LocalDateTime.now())
            .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
            .error("Internal Server Error")
            .message("An unexpected error occurred")
            .build();
    }
}
```

---

## Security

### Basic Security Configuration
```java
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/public/**").permitAll()
                .requestMatchers("/api/admin/**").hasRole("ADMIN")
                .requestMatchers("/actuator/health").permitAll()
                .anyRequest().authenticated()
            )
            .sessionManagement(session -> 
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
            .build();
    }
}
```

### Security Best Practices
- **Never store passwords in plain text**
- **Use BCrypt or Argon2** for password hashing
- **Implement JWT** for stateless authentication
- **Enable HTTPS** in production
- **Validate and sanitize** all inputs
- **Use `@PreAuthorize`** for method-level security

---

## Performance Optimization

### Caching
```java
@Service
public class UserService {
    
    @Cacheable(value = "users", key = "#id")
    public User findById(Long id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new UserNotFoundException(id));
    }
    
    @CacheEvict(value = "users", key = "#user.id")
    public User updateUser(User user) {
        return userRepository.save(user);
    }
    
    @CacheEvict(value = "users", allEntries = true)
    public void clearCache() {
        // Clears all user cache entries
    }
}
```

### Database Optimization
```java
@Entity
@Table(indexes = {
    @Index(name = "idx_user_email", columnList = "email"),
    @Index(name = "idx_user_status", columnList = "status")
})
public class User {
    
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<Order> orders;
    
    // Use batch fetching to avoid N+1 queries
    @BatchSize(size = 10)
    @OneToMany(mappedBy = "user")
    private List<Address> addresses;
}
```

### Performance Best Practices
- **Use connection pooling** (HikariCP is default)
- **Enable lazy loading** for associations
- **Use pagination** for large result sets
- **Implement caching** strategically
- **Optimize database queries** with proper indexes
- **Use async processing** for long-running tasks

---

## Monitoring & Observability

### Actuator Configuration
```yaml
management:
  endpoints:
    web:
      exposure:
        include: health,info,metrics,prometheus
      base-path: /actuator
  endpoint:
    health:
      show-details: when-authorized
      probes:
        enabled: true
  metrics:
    tags:
      application: ${spring.application.name}
```

### Custom Health Indicator
```java
@Component
public class DatabaseHealthIndicator implements HealthIndicator {
    
    @Autowired
    private DataSource dataSource;
    
    @Override
    public Health health() {
        try (Connection conn = dataSource.getConnection()) {
            if (conn.isValid(1)) {
                return Health.up()
                    .withDetail("database", "Available")
                    .build();
            }
        } catch (SQLException e) {
            return Health.down()
                .withDetail("database", "Unavailable")
                .withException(e)
                .build();
        }
        return Health.down().build();
    }
}
```

### Logging Best Practices
```java
@Slf4j
@Service
public class OrderService {
    
    public Order processOrder(CreateOrderRequest request) {
        log.info("Processing order for user: {}", request.getUserId());
        
        try {
            Order order = createOrder(request);
            log.debug("Order created successfully: {}", order.getId());
            return order;
        } catch (Exception e) {
            log.error("Failed to process order for user: {}", 
                     request.getUserId(), e);
            throw new OrderProcessingException("Order processing failed", e);
        }
    }
}
```

---

## Documentation

### OpenAPI/Swagger Configuration
```java
@Configuration
public class OpenApiConfig {
    
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("User Service API")
                .version("1.0")
                .description("User management service")
                .contact(new Contact()
                    .name("API Support")
                    .email("api@company.com")))
            .addSecurityItem(new SecurityRequirement()
                .addList("Bearer Authentication"))
            .components(new Components()
                .addSecuritySchemes("Bearer Authentication",
                    new SecurityScheme()
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT")));
    }
}
```

### API Documentation
```java
@Operation(summary = "Get user by ID", 
          description = "Returns a single user by their ID")
@ApiResponses(value = {
    @ApiResponse(responseCode = "200", 
                description = "User found",
                content = @Content(schema = @Schema(implementation = UserDto.class))),
    @ApiResponse(responseCode = "404", 
                description = "User not found"),
    @ApiResponse(responseCode = "500", 
                description = "Internal server error")
})
@GetMapping("/{id}")
public ResponseEntity<UserDto> getUser(@PathVariable Long id) {
    return ResponseEntity.ok(userService.findById(id));
}
```

---

## Summary

### Key Takeaways
1. **Follow SOLID principles** and maintain clean architecture
2. **Use constructor injection** for dependency management
3. **Implement comprehensive testing** at all levels
4. **Handle exceptions globally** with proper error responses
5. **Secure your application** with proper authentication/authorization
6. **Monitor and observe** your application with actuator and logging
7. **Document your APIs** thoroughly with OpenAPI/Swagger
8. **Optimize performance** with caching and proper database design
9. **Use profiles** for environment-specific configurations
10. **Keep your code DRY** by extracting common functionality

### Additional Resources
- [Spring Boot Reference Documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/)
- [Spring Security Documentation](https://docs.spring.io/spring-security/reference/)
- [Spring Data JPA Documentation](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/)
- [Baeldung Spring Tutorials](https://www.baeldung.com/spring-tutorial)

---

*Last Updated: 2024*
*Version: 1.0*
