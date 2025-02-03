package tracksTests.tracksAppAsApiTests;

import base.BaseTest;
import clients.TracksAppAsApi;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.Random;

public class TracksAppAsApiTests extends BaseTest {

    private static TracksAppAsApi tracksAppAsApi;

    @BeforeClass
    public static void setupTracksAppAsApi() {
        tracksAppAsApi = new TracksAppAsApi(BASE_URL, ADMIN_USERNAME, ADMIN_PASSWORD);
    }

    @Test
    public void fetchSignupPageTest() {
        tracksAppAsApi.fetchSingupPage();
        SoftAssert softAssert = new SoftAssert();

        // validate authenticity_token is fetched and stored
        softAssert.assertNotNull(tracksAppAsApi.getAuthenticity_token(), "authenticity_token should not be null");

        // Validate cookies are stored
        softAssert.assertNotNull(tracksAppAsApi.getSessionCookies());
        softAssert.assertFalse(tracksAppAsApi.getSessionCookies().asList().isEmpty());

        softAssert.assertAll();

    }

    @Test
    public void createUserTest() {
        Response response = tracksAppAsApi.createUser("testUser" + new Random().nextInt(), "password" + new Random().nextInt());

        Assert.assertEquals(response.getStatusCode(), 302);
        Assert.assertEquals((response.getHeader("Location")), BASE_URL);
    }


}
