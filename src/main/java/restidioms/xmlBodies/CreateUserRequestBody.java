package restidioms.xmlBodies;

public class CreateUserRequestBody {
    private String username;
    private String password;

    public CreateUserRequestBody(String username, String password) {
        if(isNullOrEmpty(username) || isNullOrEmpty(password)) {
            throw new IllegalArgumentException("Username and password must not be null or empty");
        }
        this.username = username;
        this.password = password;
    }

    private boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    public String toXml() {
        return "<user>" +
               "<login>" + username + "</login>" +
               "<password>" + password + "</password>" +
               "</user>";
    }
}
