package restidioms.xmlBodies;

public class CreateContextRequestBody {

    private final String contextName;

    public CreateContextRequestBody(String contextName) {
        if(isNullOrEmpty(contextName)) {
            throw new IllegalArgumentException("Context name must not be null or empty");
        }
        this.contextName = contextName;
    }

    private boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    public String toXml() {
        return "<context>" +
               "<name>" + contextName + "</name>" +
               "</context>";
    }
}
