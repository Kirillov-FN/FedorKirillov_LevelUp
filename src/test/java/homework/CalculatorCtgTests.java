package homework;

import static org.junit.jupiter.api.Assertions.assertEquals;

import homework.baseclasses.AbstractCalculatorTestBaseClass;
import org.junit.jupiter.api.Test;

public class CalculatorCtgTests extends AbstractCalculatorTestBaseClass {

    @Test
    public void ctg90Test() {
        var res = calculator.ctg(Math.toDegrees(90));
        assertEquals(0, res);
    }

    @Test
    public void ctg0Test() {
        var res = calculator.ctg(Math.toDegrees(0));
        assertEquals(0, res);
    }

    @Test
    public void ctg45Test() {
        var res = calculator.ctg(Math.toDegrees(45));
        assertEquals(1, res);
    }
}
