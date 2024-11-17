package agent;

public class ArkaAgent {
    private String username;
    private String password;

    public ArkaAgent(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
