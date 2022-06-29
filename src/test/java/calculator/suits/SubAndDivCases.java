package calculator.suits;

import calculator.CalculatorDivTests;
import calculator.CalculatorSubTests;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

@SuiteDisplayName("Сьют для вычитания и деления")
@SelectClasses({CalculatorSubTests.class, CalculatorDivTests.class})
@Suite
public class SubAndDivCases {

}
