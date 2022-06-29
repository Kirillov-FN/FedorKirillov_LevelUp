package selenium.suits;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;
import selenium.sendemail.YahooDraftTest;

@SuiteDisplayName("Тест на удаление письма")
@SelectClasses(YahooDraftTest.class)
@Suite
public class YahooDraftSuit {
}
