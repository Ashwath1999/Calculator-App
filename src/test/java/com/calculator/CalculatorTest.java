package com.calculator;

import org.junit.Test;
import static org.junit.Assert.*;

public class CalculatorTest {
    
    Calculator calc = new Calculator();

    @Test
    public void testAdd() {
        assertEquals(5, calc.add(2, 3));
    }

    @Test
    public void testSubtract() {
        assertEquals(1, calc.subtract(5, 4));
    }

    @Test
    public void testMultiply() {
        assertEquals(20, calc.multiply(4, 5));
    }

    @Test
    public void testDivide() {
        assertEquals(2, calc.divide(10, 5));
    }

    @Test(expected = ArithmeticException.class)
    public void testDivideByZero() {
        calc.divide(10, 0);
    }
}
