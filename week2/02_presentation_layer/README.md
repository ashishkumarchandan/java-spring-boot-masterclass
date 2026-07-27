# Topic 02: Presentation Layer & RESTful APIs

## The First Principle: HTTP Semantics & Stateless Resource Representations

The **Presentation Layer** acts as the front boundary of your application. In REST (Representational State Transfer), endpoints do not expose internal methods or database operations; instead, they expose **nouns (Resources)** over standard HTTP protocol verbs and MIME types.

At the network socket layer:
- **HTTP Request**: Consists of Request Line (`METHOD /uri HTTP/1.1`), Headers (`Content-Type`, `Accept`), and Entity Body (JSON byte payload).
- **Jackson `ObjectMapper`**: Spring Web automatically uses Jackson to deserialize incoming JSON byte streams into POJOs (`@RequestBody`) and serialize returning POJOs into JSON byte responses.

---

## Why-Not-Just-What: Traditional RPC vs RESTful Annotations

### RPC / Servlet Approach (The Flawed Way):
```java
// Anti-pattern: HTTP GET used to mutate state; verb in URL path
@GetMapping("/deleteUserById?id=5")
public String removeUser() { ... }
```
**Why this breaks down:**
- **Cache Corruption**: HTTP spec defines `GET` as idempotent and safe. Web proxies and CDNs may aggressively cache `GET` responses or re-issue them automatically, causing unintended state mutations or data corruption.
- **Unpredictable Status Codes**: Returning `200 OK` with `{"status": "error", "reason": "not found"}` forces API consumers to manually parse JSON bodies to discover standard failure conditions.

### Modern RESTful Annotations in Spring Boot:
- `@RestController`: Convenience meta-annotation combining `@Controller` and `@ResponseBody`. Tells Spring MVC that handler return values should be serialized directly into the HTTP response body via `HttpMessageConverter` (Jackson) instead of resolving an HTML view.
- `@GetMapping`, `@PostMapping`, `@PutMapping`, `@DeleteMapping`: Route specific HTTP verbs to target handler methods.
- `@PathVariable`: Extracts dynamic values embedded directly in URI paths (`/api/v1/products/{id}`).
- `@RequestParam`: Extracts URL query parameters (`/api/v1/products?category=Electronics`).
- `@RequestBody`: Deserializes JSON payload from HTTP request body into a Java object.
- `@RequestHeader`: Extracts HTTP request headers (e.g., `User-Agent`, `Authorization`).

---

## REST Verb & Status Code Standard Mapping

| HTTP Verb | Intent | Idempotent? | Expected Success Code |
| :--- | :--- | :---: | :--- |
| **GET** | Retrieve resource state | Yes | `200 OK` |package com.platform.mvc.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/mvc")
public class ArchitectureDemoController {

    @GetMapping("/trace")
    public Map<String, Object> traceRequestLifecycle() {
        System.out.println("--- [STAGE 2: HANDLER_ADAPTER -> CONTROLLER METHOD EXECUTION] ---");
        System.out.println("⚡ Inside ArchitectureDemoController.traceRequestLifecycle()");
        
        return Map.of(
            "message", "Request routed successfully through Spring MVC DispatcherServlet Pipeline!",
            "architecture", "Embedded Tomcat -> Servlet Filter -> DispatcherServlet -> HandlerMapping -> Controller",
            "status", "SUCCESS"
        );
    }

    @PostMapping("/echo")
    public Map<String, Object> echoUserRequest(@RequestBody Map<String, Object> body) {
        System.out.println("--- [POST REQUEST RECEIVED] ---");
        System.out.println("Payload: " + body);

        return Map.of(
            "status", "SUCCESS",
            "receivedData", body,
            "serverNote", "Spring Boot parsed your JSON request body automatically!"
        );
    }
}


| **POST** | Create a new resource | No | `201 Created` |
| **PUT** | Replace or update existing resource | Yes | `200 OK` |
| **DELETE** | Remove a resource | Yes | `204 No Content` |

---

## Code Example: `ProductApiController`

```java
@RestController
@RequestMapping("/api/v1/products")
public class ProductApiController {

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductRequest request) {
        // Deserializes JSON body into request object and returns 201 Created
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
    }
}
```

---

## cURL Verification Commands

```bash
# 1. Fetch all products
curl -X GET http://localhost:8080/api/v1/products

# 2. Filter products by query param
curl -X GET "http://localhost:8080/api/v1/products?category=Electronics"

# 3. Create a new product (POST JSON)
curl -X POST http://localhost:8080/api/v1/products \
  -H "Content-Type: application/json" \
  -d '{"name": "Wireless Mouse", "category": "Electronics", "price": 29.99}'

# 4. Delete product by ID
curl -X DELETE http://localhost:8080/api/v1/products/101
```
