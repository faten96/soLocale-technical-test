package com.solocale.AdvertisingCampaign.controller.impl;

import com.solocale.AdvertisingCampaign.controller.CalculatorController;
import com.solocale.AdvertisingCampaign.service.CalculatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CalculatorControllerImpl implements CalculatorController {

    private final CalculatorService calculatorService;

    @Override
    public ResponseEntity<String> evaluateExpression(String expression) {
        try {
            double result = calculatorService.evaluate(expression);
            return ResponseEntity.ok(String.valueOf(result));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid expression: " + e.getMessage());
        } catch (ArithmeticException e) {
            return ResponseEntity.badRequest().body("Math error: " + e.getMessage());
        }
    }
}