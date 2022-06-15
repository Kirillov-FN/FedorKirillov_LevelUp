package homework.suits;

import homework.CalculatorMultiTests;
import homework.CalculatorSumTests;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

@SuiteDisplayName("Сьют для сложения и умножения")
@SelectClasses({CalculatorSumTests.class, CalculatorMultiTests.class})
@Suite
public class SumAndMultiCases {

}
