package base;

import config.ConfigManager;
import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import org.testng.log4testng.Logger;

public class BaseTest {

    public static final Logger LOGGER = Logger.getLogger(BaseTest.class);
    public static final String BASE_URL = ConfigManager.get("base.url");

    @BeforeClass
    public void setup() {

        System.out.println("Setting up base URI...");
        String baseUrl = ConfigManager.get("base.url");
        System.out.println("Base URL: " + baseUrl);

        RestAssured.baseURI = ConfigManager.get("base.url");
    }
}
