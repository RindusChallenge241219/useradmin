package dealba;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
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
}