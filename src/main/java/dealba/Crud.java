package dealba;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.hibernate.service.ServiceRegistry;
import org.iban4j.CountryCode;
import org.iban4j.Iban;

import dealba.model.Account;
import dealba.model.Administrator;
import dealba.model.UserInfo;

public class Crud {

    /**
     * Hibernate session.
     */
    private final Session session;

    private final Administrator currentAdmin;

    public Crud(final String login, final String password) {
        this.session = startHibernate();
        this.currentAdmin = adminLogin(login, password);
    }

    private Session startHibernate() {
        // Disable logging
        Logger.getLogger("org.hibernate").setLevel(Level.OFF);

        final Configuration config = new Configuration().configure().addAnnotatedClass(Account.class)
                .addAnnotatedClass(Administrator.class).addAnnotatedClass(UserInfo.class);
        final ServiceRegistry registry = new StandardServiceRegistryBuilder().applySettings(config.getProperties())
                .build();
        final SessionFactory factory = config.buildSessionFactory(registry);
        return factory.openSession();
    }

    /**
     * Login an admin for the given login/password.
     *
     * @param login    Administrator to log in. If it does not exist in the database
     *                 a new administrator will be created with the passed login and
     *                 password.
     * @param password Administrator's password.
     * @throws IllegalArgumentException if the passed password does not match for
     *                                  an existing administrator
     */
    private Administrator adminLogin(final String login, final String password) {

        Administrator admin = session.get(Administrator.class, login);

        if (admin == null) {
            admin = new Administrator(login, password);

            final Transaction transaction = session.beginTransaction();
            session.save(admin);
            transaction.commit();
        } else if (!password.equals(admin.getPassword())) {
            throw new IllegalArgumentException("Invalid password");
        }

        return admin;
    }

    /**
     * Create an UserInfo instance in the database. This instance is marked as
     * created by the current administrator and assigned a Spanish bank account
     * with balance 0.
     *
     * @param firstName User's first name.
     * @param lastName  User's last name.
     * @return created UserInfo
     */
    public UserInfo createUser(final String firstName, final String lastName) {

        UserInfo userInfo = new UserInfo(0, firstName, lastName, currentAdmin);

        // Create an account for the new user
        Account account = new Account(Iban.random(CountryCode.ES).toFormattedString());
        userInfo.getAccount().add(account);

        Transaction transaction = session.beginTransaction();
        session.save(account);
        session.save(userInfo);
        transaction.commit();

        return userInfo;
    }

    /**
     * List all the users in the database.
     *
     * @return list of users.
     */
    public List<UserInfo> listUsers() {
        Transaction transaction = session.beginTransaction();
        Query<UserInfo> users = session.createQuery("from UserInfo u ORDER by u.id", UserInfo.class);
        transaction.commit();
        return users.list();
    }

    /**
     * Delete an user in the database. This operation is restricted to the administrator
     * who created the user.
     *
     * @param id User id, used to find the user to be deleted.
     */
    public void deleteUser(int id) {

        UserInfo user = session.get(UserInfo.class, id);
        if (user != null && currentAdmin.equals(user.getAdministrator())) {
            Transaction transaction = session.beginTransaction();
            user.getAccount().forEach(session::delete);
            session.delete(user);
            transaction.commit();
        }
    }

    /**
     * Update an user in the database by changing its first and last names. This operation
     * is restricted to the administrator who created the user.
     *
     * @param id        User id, used to find the user to be deleted.
     * @param firstName New first name.
     * @param lastName  New last name.
     */
    public void updateUser(final int id, final String firstName, final String lastName) {

        UserInfo user = session.get(UserInfo.class, id);
        if (user != null && currentAdmin.equals(user.getAdministrator())) {
            user.setFirstName(firstName);
            user.setLastName(lastName);

            session.beginTransaction();
            session.saveOrUpdate(user);
            session.getTransaction().commit();
        }
    }
}