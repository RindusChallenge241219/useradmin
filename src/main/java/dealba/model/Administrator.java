package dealba.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Administrator {

    @Id
    private final String login;

    private final String password;

    public Administrator() {
        this("", "");
    }

    public Administrator(final String login, final String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "Administrator{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
