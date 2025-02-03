package tracksTests.userSetupTests;

import base.BaseTest;
import clients.TracksAppAsApi;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

//@Ignore
public class SetupTestDataUtilityTest extends BaseTest {

    private final int numberOfUsersToCreate = 5;

    // Username and Password patterns
    private final String usernamePattern = "userName%d";
    private final String passwordPattern = "password@%d";

    @Test
    public void createRandomUsers() {
        TracksAppAsApi adminTracks = new TracksAppAsApi(BASE_URL, ADMIN_USERNAME, ADMIN_PASSWORD);

        Map<String, String> userCredentials = generateRandomUserCredentials(numberOfUsersToCreate);

        for (Map.Entry<String, String> user : userCredentials.entrySet()) {
            adminTracks.createUser(user.getKey(), user.getValue());
        }
    }

    private Map<String, String> generateRandomUserCredentials(int count) {
        Map<String, String> userCredentials = new HashMap<>();
        for(int i = 5; i < count+5; i++) {
            userCredentials.put(String.format(usernamePattern, i), String.format(passwordPattern, i));
        }
        return userCredentials;
    }
}
