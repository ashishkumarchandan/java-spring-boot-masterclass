package com.platform.mvc.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class RequestLifecycleInterceptor implements HandlerInterceptor {

    private static final String START_TIME_ATTR = "startTime";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        long startTime = System.currentTimeMillis();
        request.setAttribute(START_TIME_ATTR, startTime);
        
        System.out.println("\n--- [STAGE 1: DISPATCHER_SERVLET -> INTERCEPTOR PRE_HANDLE] ---");
        System.out.println("📥 HTTP Method: " + request.getMethod());
        System.out.println("🌐 Request URI: " + request.getRequestURI());
        System.out.println("🎯 Matched Handler: " + handler);
        System.out.println("🧵 Thread Executing: " + Thread.currentThread().getName());
        return true; // Continue execution chain to Controller
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           @Nullable ModelAndView modelAndView) throws Exception {
        System.out.println("--- [STAGE 3: CONTROLLER EXECUTED -> INTERCEPTOR POST_HANDLE] ---");
        System.out.println("📊 HTTP Status Code: " + response.getStatus());
        System.out.println("📦 Model & View: " + (modelAndView != null ? modelAndView.getViewName() : "N/A (REST JSON response via HttpMessageConverter)"));
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                                @Nullable Exception ex) throws Exception {
        long startTime = (Long) request.getAttribute(START_TIME_ATTR);
        long duration = System.currentTimeMillis() - startTime;

        System.out.println("--- [STAGE 4: RESPONSE RENDERED -> INTERCEPTOR AFTER_COMPLETION] ---");
        System.out.println("⏱️ Total Pipeline Processing Time: " + duration + " ms");
        if (ex != null) {
            System.err.println("⚠️ Exception intercepted during lifecycle: " + ex.getMessage());
        }
        System.out.println("-----------------------------------------------------------------\n");
    }
}
