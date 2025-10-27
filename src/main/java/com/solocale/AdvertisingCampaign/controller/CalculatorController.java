package com.solocale.AdvertisingCampaign.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public interface CalculatorController {

    @Operation(
            summary = "Evaluate a mathematical expression",
            description = "Supports +, -, *, / operations with parentheses and nested brackets. Example: {[(1*2+(3/4)/(7*3))]+[5*(6-2)]}"
    )
    @ApiResponse(responseCode = "200", description = "Expression evaluated successfully")
    @ApiResponse(responseCode = "400", description = "Invalid or malformed expression")
    @PostMapping("/api/calculator/evaluate")
    ResponseEntity<String> evaluateExpression(@RequestBody String expression);
}
