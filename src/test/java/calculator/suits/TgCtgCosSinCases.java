package calculator.suits;

import calculator.CalculatorCosTests;
import calculator.CalculatorCtgTests;
import calculator.CalculatorSinTests;
import calculator.CalculatorTgTests;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

@SuiteDisplayName("Сьют для тригонометрических функций")
@SelectClasses({CalculatorTgTests.class, CalculatorCtgTests.class, CalculatorCosTests.class, CalculatorSinTests.class})
@Suite
public class TgCtgCosSinCases {

}
