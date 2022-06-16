package calculator;

import static org.junit.jupiter.api.Assertions.assertEquals;

import calculator.baseclasses.AbstractCalculatorTestBaseClass;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

public class CalculatorMultiTests extends AbstractCalculatorTestBaseClass {

    @Test
    public void longMultTest() {
        var res = calculator.mult(23L, 11);
        assertEquals(253, res);
    }

    @ParameterizedTest
    @ValueSource(doubles = {0, Double.MIN_VALUE, 1, 11, 37.47, Double.MAX_VALUE})
    public void doubleMultWithZeroTest(double a) {
        var res = calculator.mult(a, 0);
        assertEquals(0, res);
    }

    @ParameterizedTest
    @CsvSource(value = {"3.000001, 11.2, 33.6000112", "0.0000001, 10, 0.000001"})
    public void doubleMultiTest(double a, double b, double expected) {
        double res = calculator.mult(a, b);
        assertEquals(expected, res);
    }
}