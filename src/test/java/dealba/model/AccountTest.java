package dealba.model;

import org.iban4j.Iban;
import org.junit.Test;

import static org.junit.Assert.*;

public class AccountTest {

    @Test
    public void testEmptyConstructor() {
        final Account account = new Account();
        assertTrue(account.getIban().isEmpty());
        assertEquals(0, account.getBalance(), .0);
    }

    @Test
    public void testParameterizedConstructor() {
        final String iban = Iban.random().toFormattedString();
        final Account account = new Account(iban);
        assertEquals(iban, account.getIban());
        assertEquals(0, account.getBalance(), .0);
    }

    @Test
    public void testSetBalance() {
        final Account account = new Account();
        account.setBalance(30000);
        assertEquals(30000, account.getBalance(), .0);
    }
}