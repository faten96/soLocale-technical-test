package com.solocale.AdvertisingCampaign.service;

import com.solocale.AdvertisingCampaign.service.impl.CalculatorServiceImpl;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CalculatorServiceTest {
    private final CalculatorService calculatorService = new CalculatorServiceImpl();

    @Test
    void shouldEvaluateSimpleExpression() {
        assertThat(calculatorService.evaluate("2+3*4")).isEqualTo(14.0);
    }

    @Test
    void shouldEvaluateWithParentheses() {
        assertThat(calculatorService.evaluate("(2+3)*4")).isEqualTo(20.0);
    }

    @Test
    void shouldHandleNestedBrackets() {
        assertThat(calculatorService.evaluate("{[(1*2+(3/3)/(7*3))]+[5*(6-2)]}")).isCloseTo(20.0, within(0.001));
    }

    @Test
    void shouldThrowOnDivisionByZero() {
        assertThatThrownBy(() -> calculatorService.evaluate("5/0"))
                .isInstanceOf(ArithmeticException.class)
                .hasMessageContaining("Division by zero");
    }

    @Test
    void shouldThrowOnInvalidInput() {
        assertThatThrownBy(() -> calculatorService.evaluate("2++3"))
                .isInstanceOf(IllegalArgumentException.class);
    }
}