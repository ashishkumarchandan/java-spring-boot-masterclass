package com.platform.exceptionhandling.controller;

import com.platform.exceptionhandling.exception.BusinessRuleException;
import com.platform.exceptionhandling.exception.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getAccount(@PathVariable String id) {
        if ("999".equals(id)) {
            throw new ResourceNotFoundException("Account with ID " + id + " does not exist.");
        }
        return ResponseEntity.ok(Map.of("accountId", id, "balance", 5000.00));
    }

    @PostMapping("/{id}/withdraw")
    public ResponseEntity<Map<String, Object>> withdraw(@PathVariable String id, @RequestParam BigDecimal amount) {
        if (amount.compareTo(new BigDecimal("10000")) > 0) {
            throw new BusinessRuleException("Withdrawal limit exceeded. Maximum single withdrawal limit is $10,000.");
        }
        return ResponseEntity.ok(Map.of("accountId", id, "withdrawn", amount, "status", "SUCCESS"));
    }

    @GetMapping("/bug")
    public ResponseEntity<String> triggerUnexpectedBug() {
        // Simulates unhandled NullPointerException
        String nullStr = null;
        return ResponseEntity.ok(nullStr.toUpperCase());
    }
}
