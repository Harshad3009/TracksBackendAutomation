package tracksTests.tracksAppAsApiTests;

import base.BaseTest;
import clients.TracksAppAsApi;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class TracksAppAsApiTests extends BaseTest {

    private static TracksAppAsApi tracksAppAsApi;

    @BeforeClass
    public static void setupTracksAppAsApi() {
        tracksAppAsApi = new TracksAppAsApi(BASE_URL, ADMIN_USERNAME, ADMIN_PASSWORD);
    }

    @Test
    public void fetchSignupPageTest() {
        tracksAppAsApi.fetchSingupPage();
        System.out.println(tracksAppAsApi.getAuthenticity_token());
        System.out.println(tracksAppAsApi.getSessionCookies());
        SoftAssert softAssert = new SoftAssert();

        // validate authenticity_token is fetched and stored
        softAssert.assertNotNull(tracksAppAsApi.getAuthenticity_token(), "authenticity_token should not be null");

        // Validate cookies are stored
        softAssert.assertNotNull(tracksAppAsApi.getSessionCookies());
        softAssert.assertFalse(tracksAppAsApi.getSessionCookies().asList().isEmpty());

        softAssert.assertAll();

    }


}
