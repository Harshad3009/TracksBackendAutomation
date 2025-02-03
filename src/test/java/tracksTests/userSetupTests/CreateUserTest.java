package tracksTests.userSetupTests;

import base.BaseTest;
import clients.ApiClient;
import config.ConfigManager;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import restidioms.xmlBodies.CreateUserRequestBody;

import java.util.logging.Logger;

public class CreateUserTest extends BaseTest {

    private static final Logger LOGGER = Logger.getLogger(CreateUserTest.class.getName());
    private final String username = "testuser6";
    private final String password = "password6";

     @Test
     public void testCreateUser() {

         CreateUserRequestBody createUserRequestBody = new CreateUserRequestBody(username, password);
         String body = createUserRequestBody.toXml();
         LOGGER.info("Sending XML request: \n" + body);
         ApiClient apiClient = new ApiClient(BASE_URL, ADMIN_USERNAME, ADMIN_PASSWORD);
         Response response = apiClient.sendPostResponse("/users.xml", body);
         LOGGER.info("Response: \n" + response.asString());
         Assert.assertEquals(response.getStatusCode(), 200);
     }
}
