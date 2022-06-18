package homework;

import static org.junit.jupiter.api.Assertions.assertEquals;

import homework.baseclasses.AbstractCalculatorTestBaseClass;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class CalculatorSumTests  extends AbstractCalculatorTestBaseClass {

    @ParameterizedTest
    @CsvSource({
        "2, 3, 5",
        "0, 0, 0",
        "1, 5, 6",
        "9223372036854775807, 0, 9223372036854775807"
    })
    public void longSumTest(long a, long b, long expectedRes) {
        long res = calculator.sum(a, b);
        assertEquals(expectedRes, res);
    }

    @Test
    public void doubleSumTest() {
        double res = calculator.sum(24.2, 0.1);
        assertEquals(24.3, res);
    }

    @Test
    public void sumWithZero() {
        var res = calculator.sum(0, 0);
        assertEquals(0, res);
    }
}

