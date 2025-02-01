package restidioms.xmlBodies;

public class CreateUserRequestBody {
    private String username;
    private String password;

    public CreateUserRequestBody(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String toXml() {
        return "<user>" +
               "<login>" + username + "</login>" +
               "<password>" + password + "</password>" +
               "</user>";
    }
}
