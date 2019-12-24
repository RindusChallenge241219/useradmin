package dealba.model;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class UserInfo {

    /**
     * User id (PK).
     */
    @Id
    @GeneratedValue
    private final int id;

    private String firstName;

    private String lastName;

    /**
     * Administrator who created this user.
     */
    @ManyToOne
    private final Administrator administrator;

    @OneToMany
    private final List<Account> account;

    public UserInfo() {
        this(0, "", "", null);
    }

    public UserInfo(final int id, final String firstName, final String lastName, final Administrator administrator) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.administrator = administrator;
        this.account = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Administrator getAdministrator() {
        return administrator;
    }

    public List<Account> getAccount() {
        return account;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", administrator=" + administrator +
                ", account=" + account +
                '}';
    }
}
