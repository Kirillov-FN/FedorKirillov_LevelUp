package calculator.testdata;

import java.util.stream.Stream;
import org.junit.jupiter.params.provider.Arguments;

public class CalculatorTestsData {
    public static Stream<Arguments> calculatorSubTestsData() {
        return Stream.of(
            Arguments.arguments(3.18, 5.9, -2.72),
            Arguments.arguments(10, 0.1, 9.9),
            Arguments.arguments(Double.MAX_VALUE, Double.MAX_VALUE, 0),
            Arguments.arguments(Double.MAX_VALUE, Double.MIN_VALUE, 1.7976931348623157E308),
            Arguments.arguments(0, 0, 0),
            Arguments.arguments(0, Double.MAX_VALUE, Double.MAX_VALUE * (-1))
        );
    }

    public static Stream<Arguments> calculatorDivTestsData() {
        return Stream.of(
            Arguments.arguments(5, 2, 2.5),
            Arguments.arguments(10, 5, 2),
            Arguments.arguments(23478562, 12876382387L, 0.001823381854806),
            Arguments.arguments(0, 325476, 0),
            Arguments.arguments(1245645465154L, 1, 1245645465154L)
        );
    }
}
