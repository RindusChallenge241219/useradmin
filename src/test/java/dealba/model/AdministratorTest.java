package dealba.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class AdministratorTest {

    @Test
    public void testEmptyConstructor() {
        final Administrator admin = new Administrator();
        assertTrue(admin.getLogin().isEmpty());
        assertTrue(admin.getPassword().isEmpty());
    }

    @Test
    public void testParameterizedConstructor() {
        final Administrator admin = new Administrator("Krusty", "Burger");
        assertEquals("Krusty", admin.getLogin());
        assertEquals("Burger", admin.getPassword());
    }
}