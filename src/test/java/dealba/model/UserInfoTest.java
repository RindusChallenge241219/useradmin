package dealba.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class UserInfoTest {

    @Test
    public void testEmptyConstructor() {
        final UserInfo user = new UserInfo();
        assertEquals(0, user.getId());
        assertTrue(user.getFirstName().isEmpty());
        assertTrue(user.getLastName().isEmpty());
        assertNull(user.getAdministrator());
        assertTrue(user.getAccount().isEmpty());
    }

    @Test
    public void testParameterizedConstructor() {
        final UserInfo user = new UserInfo(99, "Ronaldo", "Nazario", new Administrator());
        assertEquals(99, user.getId());
        assertEquals("Ronaldo", user.getFirstName());
        assertEquals("Nazario", user.getLastName());
        assertNotNull(user.getAdministrator());
        assertTrue(user.getAccount().isEmpty());
    }

    @Test
    public void testSetters() {
        final UserInfo user = new UserInfo();

        user.setFirstName("Ronaldo");
        assertEquals("Ronaldo", user.getFirstName());

        user.setLastName("Nazario");
        assertEquals("Nazario", user.getLastName());
    }
}