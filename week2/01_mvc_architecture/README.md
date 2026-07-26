# Topic 01: Spring MVC Architecture & Request Lifecycle

## The First Principle: Network Sockets, Web Servers, and Servlets

In a modern web application, standard Java objects (`@Controller`, `@Service`) do not natively listen to raw network ports like `8080`. Understanding Spring MVC requires stepping back to the OS and JVM socket levels:

1. **TCP Socket Listeners**: When Spring Boot starts, its embedded **Apache Tomcat** web server binds to a TCP port (e.g., `8080`). It creates an OS-level socket listening thread pool (`Acceptor` threads).
2. **HTTP Parsing**: When a client sends a HTTP GET request over TCP bytes (`GET /api/mvc/trace HTTP/1.1`), Tomcat receives the byte stream, parses raw headers and body, and encapsulates them into a **Java Servlet API** object: `HttpServletRequest` (`org.apache.catalina.connector.Request`).
3. **Servlet Container**: The Java Servlet Specification defines a single entry point contract (`Servlet.service(ServletRequest, ServletResponse)`).

---

## Why-Not-Just-What: Traditional Servlets vs. DispatcherServlet Front Controller Pattern

### Traditional Servlet Architecture (The Old Way):
Before Spring MVC, developers wrote individual servlets for every endpoint path in `web.xml`:
```java
@WebServlet("/orders")
public class OrderServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        // Manual parsing, manual JSON conversion, manual exception handling
    }
}
```
**Why this breaks down at enterprise scale:**
- **Code Duplication**: Every servlet repeatedly extracts request parameters, opens transactions, handles error logging, and sets HTTP status codes.
- **Tight Coupling**: Direct dependencies on `HttpServletRequest` and `HttpServletResponse` make unit testing impossible without heavy mocking of Servlet API interfaces.
- **No Central Dispatching**: Cross-cutting concerns like security, authentication, request metrics, and content negotiation must be duplicated across dozens of separate Servlets.

### Spring MVC Solution: Front Controller Pattern (`DispatcherServlet`)
Spring Boot eliminates raw Servlet proliferation by registering **a single master Front Controller Servlet**: `org.springframework.web.servlet.DispatcherServlet`.

---

## Deep Internal Lifecycle Pipeline

```text
===========================================================================================================
                               SPRING MVC REQUEST PROCESSING PIPELINE
===========================================================================================================

 [ CLIENT HTTP REQUEST ]
          |
          v (TCP Socket Byte Stream)
 [ EMBEDDED APACHE TOMCAT SERVER ] (Parses HTTP -> Creates HttpServletRequest & HttpServletResponse)
          |
          v
 [ FILTER CHAIN ] (Security, Cors, Custom Servlet Filters)
          |
          v
 [ DISPATCHER SERVLET ] (Front Controller Entry Point)
          |
          +----> 1. HandlerMapping (RequestMappingHandlerMapping)
          |         - Looks up URI (/api/mvc/trace) in Mapping Registry
          |         - Returns HandlerExecutionChain (Controller Method + Interceptors)
          |
          +----> 2. Pre-Handle Interceptors (HandlerInterceptor.preHandle())
          |         - Executes pre-processing hooks (Logging, Token Verification)
          |
          +----> 3. HandlerAdapter (RequestMappingHandlerAdapter)
          |         - Invokes target Controller Method via Reflection
          |         - Resolves method parameters (@PathVariable, @RequestBody, @RequestParam)
          |
          v
 [ ARCHITECTURE DEMO CONTROLLER ] (Controller Method Executes -> Returns Java Map/Object)
          |
          v
 +--------------+
 | HandlerAdapter| ----> 4. HttpMessageConverter (MappingJackson2HttpMessageConverter)
 +--------------+           - Serializes Java Object to JSON byte stream
                            - Sets Content-Type: application/json
          |
          +----> 5. Post-Handle Interceptors (HandlerInterceptor.postHandle())
          |
          +----> 6. After-Completion Interceptors (HandlerInterceptor.afterCompletion())
          |
          v
 [ TOMCAT CONNECTOR ] ---> Flushes HTTP Response Payload over OS TCP Socket
===========================================================================================================
```

---

## Code Demonstration & Trace Logs

When you invoke `GET http://localhost:8080/api/mvc/trace`, the `RequestLifecycleInterceptor` logs the execution phase directly:

```text
--- [STAGE 1: DISPATCHER_SERVLET -> INTERCEPTOR PRE_HANDLE] ---
📥 HTTP Method: GET
🌐 Request URI: /api/mvc/trace
🎯 Matched Handler: com.platform.mvc.controller.ArchitectureDemoController#traceRequestLifecycle()
🧵 Thread Executing: http-nio-8080-exec-1

--- [STAGE 2: HANDLER_ADAPTER -> CONTROLLER METHOD EXECUTION] ---
⚡ Inside ArchitectureDemoController.traceRequestLifecycle()

--- [STAGE 3: CONTROLLER EXECUTED -> INTERCEPTOR POST_HANDLE] ---
📊 HTTP Status Code: 200
📦 Model & View: N/A (REST JSON response via HttpMessageConverter)

--- [STAGE 4: RESPONSE RENDERED -> INTERCEPTOR AFTER_COMPLETION] ---
⏱️ Total Pipeline Processing Time: 4 ms
-----------------------------------------------------------------
```

---

## Key Components Reference

1. **`DispatcherServlet`**: Central orchestrator that delegates request handling to specialized components.
2. **`HandlerMapping`**: Maps incoming URIs to target handler methods.
3. **`HandlerAdapter`**: Adapts execution mechanics so `DispatcherServlet` can invoke any handler signature uniformly using reflection.
4. **`HttpMessageConverter`**: Serializes/Deserializes payloads between HTTP streams and Java objects (Jackson `ObjectMapper`).
