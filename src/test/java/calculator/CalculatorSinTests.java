package calculator;

import static org.junit.jupiter.api.Assertions.assertEquals;

import calculator.baseclasses.AbstractCalculatorTestBaseClass;
import org.junit.jupiter.api.Test;

public class CalculatorSinTests extends AbstractCalculatorTestBaseClass {

    @Test
    public void sin90Test() {
        var res = calculator.sin(Math.toDegrees(90));
        assertEquals(1, res, 0.1);
    }

    @Test
    public void sin0Test() {
        var res = calculator.sin(Math.toDegrees(0));
        assertEquals(0, res, 0.1);
    }

    @Test
    public void sin45Test() {
        var res = calculator.sin(Math.toDegrees(45));
        assertEquals(0.707, res, 0.1);
    }
}
