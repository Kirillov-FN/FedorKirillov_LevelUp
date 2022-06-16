package calculator;

import static org.junit.jupiter.api.Assertions.assertEquals;

import calculator.baseclasses.AbstractCalculatorTestBaseClass;
import org.junit.jupiter.api.Test;

public class CalculatorPowTests extends AbstractCalculatorTestBaseClass {

    @Test
    public void zeroPowTest() {
        var res = calculator.pow(0, 0);
        assertEquals(1, res);
    }

    @Test
    public void powTest() {
        var res = calculator.pow(2, 10);
        assertEquals(1024, res);
    }
}
