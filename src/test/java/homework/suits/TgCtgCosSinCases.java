package homework.suits;

import homework.CalculatorCosTests;
import homework.CalculatorCtgTests;
import homework.CalculatorDivTests;
import homework.CalculatorSinTests;
import homework.CalculatorSubTests;
import homework.CalculatorTgTests;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

@SuiteDisplayName("Сьют для тригонометрических функций")
@SelectClasses({CalculatorTgTests.class, CalculatorCtgTests.class, CalculatorCosTests.class, CalculatorSinTests.class})
@Suite
public class TgCtgCosSinCases {

}
