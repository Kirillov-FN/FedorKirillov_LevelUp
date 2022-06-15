package homework;

import static org.junit.jupiter.api.Assertions.assertEquals;

import homework.baseclasses.AbstractCalculatorTestBaseClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class CalculatorDivTests extends AbstractCalculatorTestBaseClass {

    @Test
    public void longDivWithZeroTest() {
        Exception exception = Assertions.assertThrows(NumberFormatException.class, () -> calculator.div(100, 0));
        assertEquals("Attempt to divide by zero", exception.getMessage());
    }

    @ParameterizedTest
    @MethodSource("homework.testdata.CalculatorTestsData#calculatorDivTestsData")
    public void longDivTest(long a, long b, double expected) {
        var res = calculator.div(a, b);
        assertEquals(expected, res);
    }

    @Test
    public void doubleDivTest() {
        var res = calculator.div(15.445815634, 16.123464);
        assertEquals(0.9579712916529599, res, 0.000000000000001);
    }

    @Test
    public void doubleDivWithZeroTest() {
        var res = calculator.div(100.123, 0);
        assertEquals(Double.POSITIVE_INFINITY, res);
    }
}
