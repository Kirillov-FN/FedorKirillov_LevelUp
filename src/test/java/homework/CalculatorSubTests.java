package homework;

import static org.junit.jupiter.api.Assertions.assertEquals;

import homework.baseclasses.AbstractCalculatorTestBaseClass;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class CalculatorSubTests extends AbstractCalculatorTestBaseClass {

    @Test
    public void longSubTest() {
        var res = calculator.sub(9223372036854775807L, 10);
        assertEquals(9223372036854775797L, res);
    }

    @ParameterizedTest
    @MethodSource("homework.testdata.CalculatorTestsData#calculatorSubTestsData")
    public void doubleSubTest(double a, double b, double exceptedRes) {
        var res = calculator.sub(a, b);
        assertEquals(exceptedRes, res);
    }
}
