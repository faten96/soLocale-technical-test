package com.solocale.AdvertisingCampaign.service.impl;

import com.solocale.AdvertisingCampaign.service.CalculatorService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CalculatorServiceImpl implements CalculatorService {

    private static final Map<Character, Integer> PRECEDENCE = Map.of(
            '+', 1, '-', 1, '*', 2, '/', 2
    );

    @Override
    public double evaluate(String expression) {
        if (expression == null || expression.isBlank()) {
            throw new IllegalArgumentException("Expression cannot be empty");
        }

        try {
            String normalized = expression.replaceAll("[\\[\\{]", "(").replaceAll("[\\]\\}]", ")");
            return evaluatePostfix(toPostfix(normalized));
        } catch (ArithmeticException e) {
            throw new ArithmeticException(e.getMessage());
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid expression");
        }
    }

    private List<String> toPostfix(String expr) {
        List<String> output = new ArrayList<>();
        Deque<Character> stack = new ArrayDeque<>();
        StringBuilder number = new StringBuilder();

        for (char ch : expr.toCharArray()) {
            if (Character.isWhitespace(ch)) continue;
            if (Character.isDigit(ch) || ch == '.') {
                number.append(ch);
            } else {
                if (!number.isEmpty()) {
                    output.add(number.toString());
                    number.setLength(0);
                }

                if (ch == '(') stack.push(ch);
                else if (ch == ')') {
                    while (!stack.isEmpty() && stack.peek() != '(')
                        output.add(String.valueOf(stack.pop()));
                    if (stack.isEmpty() || stack.pop() != '(')
                        throw new IllegalArgumentException("Mismatched parentheses");
                } else if (PRECEDENCE.containsKey(ch)) {
                    while (!stack.isEmpty() && PRECEDENCE.getOrDefault(stack.peek(), 0) >= PRECEDENCE.get(ch))
                        output.add(String.valueOf(stack.pop()));
                    stack.push(ch);
                } else throw new IllegalArgumentException("Unexpected character: " + ch);
            }
        }

        if (!number.isEmpty()) output.add(number.toString());
        while (!stack.isEmpty()) output.add(String.valueOf(stack.pop()));
        return output;
    }

    private double evaluatePostfix(List<String> postfix) {
        Deque<Double> stack = new ArrayDeque<>();

        for (String token : postfix) {
            if (token.matches("[+\\-*/]")) {
                if (stack.size() < 2) throw new IllegalArgumentException("Invalid operation");
                double b = stack.pop();
                double a = stack.pop();

                double result = switch (token) {
                    case "+" -> a + b;
                    case "-" -> a - b;
                    case "*" -> a * b;
                    case "/" -> {
                        if (b == 0) throw new ArithmeticException("Division by zero");
                        yield a / b;
                    }
                    default -> throw new IllegalArgumentException("Unknown operator: " + token);
                };
                stack.push(result);
            } else {
                stack.push(Double.parseDouble(token));
            }
        }

        if (stack.size() != 1) throw new IllegalArgumentException("Invalid expression format");
        return stack.pop();
    }
}
