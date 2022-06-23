package selenium.suits;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;
import selenium.sendemail.YahooDeleteTest;

@SuiteDisplayName("Тест на удаление письма")
@SelectClasses(YahooDeleteTest.class)
@Suite
public class YahooDeleteSuit {
}
