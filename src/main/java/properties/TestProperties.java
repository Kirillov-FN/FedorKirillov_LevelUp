package properties;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class TestProperties {
    protected static Properties property = new Properties();

    static {
        {
            try {
                FileInputStream fis = new FileInputStream("src/main/resources/selenium/SeleniumTests.properties");
                property.load(fis);
                fis.close();
                //--//
                //FileOutputStream fos = new FileOutputStream("src/main/resources/selenium/SeleniumTests.properties");
                //property.setProperty("yahoo.ruleTest.mailSubject", "Тест message subject - rule");
                //property.save(fos, null);
                //fos.close();
            } catch (IOException e) {
                System.err.println("ОШИБКА: Файл свойств отсуствует!");
            }
        }
    }

    public static String getProperty(String name) {
        return property.getProperty(name);
    }
}
