package calculator.suits;

import calculator.CalculatorMultiTests;
import calculator.CalculatorSumTests;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

@SuiteDisplayName("Сьют для сложения и умножения")
@SelectClasses({CalculatorSumTests.class, CalculatorMultiTests.class})
@Suite
public class SumAndMultiCases {

}
