package homework.suits;

import org.junit.platform.suite.api.ExcludeClassNamePatterns;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

@SuiteDisplayName("Сьют для всех остальных операций")
@SelectPackages("homework")
@ExcludeClassNamePatterns({
    ".*CalculatorCosTests?",
    ".*CalculatorSinTests?",
    ".*CalculatorTgTests?",
    ".*CalculatorCtgTests?",
    ".*CalculatorSumTests?",
    ".*CalculatorMultiTests?",
    ".*CalculatorSubTests?",
    ".*CalculatorDivTests?"
})
@Suite
public class AllOtherCases {

}
