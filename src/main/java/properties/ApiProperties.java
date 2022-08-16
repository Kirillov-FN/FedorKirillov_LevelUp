package properties;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ApiProperties {
    protected static Properties property = new Properties();

    static {
        {
            try {
                FileInputStream fis = new FileInputStream("src/main/resources/api/ApiTests.properties");
                property.load(fis);
                fis.close();
            } catch (IOException e) {
                System.err.println("ОШИБКА: Файл свойств отсуствует!");
            }
        }
    }

    public static String getProperty(String name) {
        return property.getProperty(name);
    }
}
