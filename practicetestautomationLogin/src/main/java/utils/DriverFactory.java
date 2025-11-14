package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import org.yaml.snakeyaml.Yaml;

public class DriverFactory {
    private static WebDriver driver;
    private static Map<String, String> config;

    public static void loadConfig() {
        try {
            Yaml yaml = new Yaml();
            InputStream input = DriverFactory.class.getClassLoader().getResourceAsStream("config.yml");
            config = yaml.load(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static WebDriver getDriver() {
        if (driver == null) {
            loadConfig();
            driver = new ChromeDriver();
            driver.manage().window().maximize();
        }
        return driver;
    }

    public static String getUrl() {
        return config.get("url");
    }

    public static String getUsername() {
        return config.get("username");
    }

    public static String getPassword() {
        return config.get("password");
    }

    public static void takeScreenshot(String screenshotName) {
        try {
            File screenshotDir = new File(config.get("screenshot_path"));
            if (!screenshotDir.exists()) {
                screenshotDir.mkdirs();
            }

            TakesScreenshot ts = (TakesScreenshot) driver;
            File source = ts.getScreenshotAs(OutputType.FILE);
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String filename = config.get("screenshot_path") + screenshotName + "_" + timestamp + ".png";
            FileUtils.copyFile(source, new File(filename));
            System.out.println("Screenshot saved: " + filename);
        } catch (Exception e) {
            System.out.println("Error taking screenshot: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void closeDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
