package base;

import config.ConfigManager;
import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;

public class BaseTest {

    @BeforeClass
    public void setup() {

        System.out.println("Setting up base URI...");
        String baseUrl = ConfigManager.get("base.url");
        System.out.println("Base URL: " + baseUrl);

        RestAssured.baseURI = ConfigManager.get("base.url");
    }
}
