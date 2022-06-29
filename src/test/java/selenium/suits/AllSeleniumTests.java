package selenium.suits;

import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

@SuiteDisplayName("Общий сбют для всех тестов селениум")
@SelectPackages("selenium.sendemail")
@Suite
public class AllSeleniumTests {
}
