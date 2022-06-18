package homework;

import static org.junit.jupiter.api.Assertions.assertEquals;

import homework.baseclasses.AbstractCalculatorTestBaseClass;
import org.junit.jupiter.api.Test;

public class CalculatorTgTests extends AbstractCalculatorTestBaseClass {

    @Test
    public void tg360Test() {
        var res = calculator.tg(Math.toDegrees(360));
        assertEquals(0, res, 0.1);
    }

    @Test
    public void tg0Test() {
        var res = calculator.tg(Math.toDegrees(0));
        assertEquals(0, res, 0.1);
    }

    @Test
    public void tg45Test() {
        var res = calculator.tg(Math.toDegrees(45));
        assertEquals(1, res, 0.1);
    }
}
