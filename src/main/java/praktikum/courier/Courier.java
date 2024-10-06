package praktikum.courier;

import org.apache.commons.lang3.RandomStringUtils;

public class Courier {
    private String login;
    private String password;
    private String firstName;

    public Courier(String login, String password, String firstName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }

    public Courier() {
    }

    public static Courier random() {
        return new Courier("TestLogin" + RandomStringUtils.randomAlphanumeric(5, 15),
                "Password@123", "TestFirstName");
    }

    public static Courier withoutPassword() {
        return new Courier("TestLogin" + RandomStringUtils.randomAlphanumeric(5, 15),
                null, "TestFirstName");
    }

    public static Courier withoutLogin() {
        return new Courier(null,
                "Password@123", "TestFirstName");
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
