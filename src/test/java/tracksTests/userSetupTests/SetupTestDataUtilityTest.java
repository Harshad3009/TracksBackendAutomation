package tracksTests.userSetupTests;

import base.BaseTest;
import clients.TracksAppAsApi;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

//@Ignore
public class SetupTestDataUtilityTest extends BaseTest {

    private static final int NUM_OF_USERS = 5;

    // Username and Password patterns
    private static final String USERNAME_PATTERN = "userName%d";
    private static final String PASSWORD_PATTERN = "password@%d";

    @Test
    public void createRandomUsers() {
        TracksAppAsApi adminTracks = new TracksAppAsApi(BASE_URL, ADMIN_USERNAME, ADMIN_PASSWORD);

        Map<String, String> userCredentials = generateRandomUserCredentials(NUM_OF_USERS);

        for (Map.Entry<String, String> user : userCredentials.entrySet()) {
            adminTracks.createUser(user.getKey(), user.getValue());
        }
    }

    private Map<String, String> generateRandomUserCredentials(int count) {
        Map<String, String> userCredentials = new HashMap<>();
        for(int i = 5; i < count+5; i++) {
            userCredentials.put(String.format(USERNAME_PATTERN, i), String.format(PASSWORD_PATTERN, i));
        }
        return userCredentials;
    }
}
