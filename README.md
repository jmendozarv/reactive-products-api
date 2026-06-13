# Reactive Products API

Microservicio reactivo para gestion de productos, construido con Java 17, Spring Boot 3, Spring WebFlux, Spring Data R2DBC y H2.

## Requisitos

- Java 17+
- Maven 3.9+
- Docker y Docker Compose

## Ejecutar localmente

```bash
mvn spring-boot:run
```

La API queda disponible en `http://localhost:8080`.

## Ejecutar con Docker

```bash
docker compose up --build
```

Para detener la solucion:

```bash
docker compose down
```

## Tests y cobertura

Ejecutar pruebas unitarias y de controlador:

```bash
mvn test
```

Generar reporte JaCoCo y validar el minimo de 70% en la capa de servicios:

```bash
mvn verify
```

El reporte HTML queda en:

```text
target/site/jacoco/index.html
```

## Endpoints

### Crear producto

```bash
curl -X POST http://localhost:8080/api/v1/products \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Laptop",
    "description": "Laptop ultraliviana",
    "price": 999.99,
    "stock": 8
  }'
```

### Listar productos

```bash
curl http://localhost:8080/api/v1/products
```

### Obtener producto por id

```bash
curl http://localhost:8080/api/v1/products/1
```

### Actualizar producto

```bash
curl -X PUT http://localhost:8080/api/v1/products/1 \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Laptop Pro",
    "description": "Laptop ultraliviana actualizada",
    "price": 1199.99,
    "stock": 5
  }'
```

### Eliminar producto

```bash
curl -X DELETE http://localhost:8080/api/v1/products/1
```

