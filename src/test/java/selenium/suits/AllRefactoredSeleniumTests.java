package selenium.suits;

import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

@SuiteDisplayName("Общий съют для всех отрефаченных тестов селениум")
@SelectPackages("selenium.sendemail.refactor")
@Suite
public class AllRefactoredSeleniumTests {
}
