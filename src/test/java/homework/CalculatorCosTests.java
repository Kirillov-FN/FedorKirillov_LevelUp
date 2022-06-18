package homework;

import static org.junit.jupiter.api.Assertions.assertEquals;

import homework.baseclasses.AbstractCalculatorTestBaseClass;
import org.junit.jupiter.api.Test;

public class CalculatorCosTests extends AbstractCalculatorTestBaseClass {

    @Test
    public void cos90Test() {
        var res = calculator.cos(Math.toDegrees(90));
        assertEquals(0, res, 0.1);
    }

    @Test
    public void cos0Test() {
        var res = calculator.cos(Math.toDegrees(0));
        assertEquals(1, res, 0.1);
    }

    @Test
    public void cos45Test() {
        var res = calculator.cos(Math.toDegrees(45));
        assertEquals(0.707, res, 0.1);
    }
}
