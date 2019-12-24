package dealba.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Account {

    @Id
    private final String iban;

    private double balance;

    public Account() {
        this("");
    }

    /**
     * Create an account with the given IBAN number. Balance starts at 0.
     */
    public Account(final String iban) {
        this.iban = iban;
        this.balance = 0;
    }

    public String getIban() {
        return iban;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "Account{" +
                "iban='" + iban + '\'' +
                ", balance=" + balance +
                '}';
    }
}
