package calculator;

import calculator.baseclasses.AbstractCalculatorTestBaseClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class CalculatorIsNegativeTests extends AbstractCalculatorTestBaseClass {

    @ParameterizedTest
    @ValueSource(longs = {-1, -2, -10, -Long.MAX_VALUE, Long.MIN_VALUE})
    public void isNegativeTrueTest(long a) {
        Assertions.assertTrue(calculator.isNegative(a));
    }

    @ParameterizedTest
    @ValueSource(longs = {0, 2, 10, Long.MAX_VALUE})
    public void isNegativeFalseTest(long a) {
        Assertions.assertFalse(calculator.isNegative(a));
    }

}
