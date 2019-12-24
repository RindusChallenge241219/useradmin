package dealba;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

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
}