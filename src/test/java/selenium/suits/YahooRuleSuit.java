package selenium.suits;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;
import selenium.sendemail.YahooRuleTest;

@SuiteDisplayName("Тест на удаление письма")
@SelectClasses(YahooRuleTest.class)
@Suite
public class YahooRuleSuit {
}
