package homework;

import homework.baseclasses.AbstractCalculatorTestBaseClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class CalculatorIsPositiveTests extends AbstractCalculatorTestBaseClass {

    @ParameterizedTest
    @ValueSource(longs = {1, 2, 10, Long.MAX_VALUE})
    public void isPositiveTrueTest(long a) {
        Assertions.assertTrue(calculator.isPositive(a));
    }

    @ParameterizedTest
    @ValueSource(longs = {0, -2, -10, Long.MIN_VALUE})
    public void isPositiveFalseTest(long a) {
        Assertions.assertFalse(calculator.isPositive(a));
    }

}
