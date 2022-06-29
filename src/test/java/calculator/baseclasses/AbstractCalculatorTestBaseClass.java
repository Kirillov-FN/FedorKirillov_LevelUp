package calculator.baseclasses;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import ru.levelup.qa.at.calculator.Calculator;

@TestInstance(Lifecycle.PER_CLASS)
public abstract class AbstractCalculatorTestBaseClass {
    protected Calculator calculator;

    @BeforeAll
    private void printTestClassName() {
        System.out.print("\nRun test class " + this.getClass().getCanonicalName() + "\n");
    }

    @BeforeEach
    private void creatingObject() {
        //System.out.printf("Init calculator\n");
        calculator = new Calculator();
    }

    @AfterEach
    private void destroyObject() {
        //System.out.printf("Set calculator as null\n");
        calculator = null;
    }
}
