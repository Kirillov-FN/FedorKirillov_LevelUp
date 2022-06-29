package calculator;

import static org.junit.jupiter.api.Assertions.assertEquals;

import calculator.baseclasses.AbstractCalculatorTestBaseClass;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class CalculatorSqrtTests extends AbstractCalculatorTestBaseClass {

    @ParameterizedTest
    @CsvSource({"0, 0", "2, 1.414", "9, 3", "635.04, 25.2", "-4, 2"})
    public void sqrtTest(double a, double expected) {
        var res = calculator.sqrt(a);
        assertEquals(expected, res, 0.001);
    }
}
