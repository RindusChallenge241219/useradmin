package dealba;

import static java.lang.System.out;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import de.vandermeer.asciitable.AsciiTable;
import dealba.model.UserInfo;

public class App {

    public static void main(final String[] args) {

        // Administrator login
        final String login;
        final String password;
        out.println("Please authenticate. An administrator account will be created if it does not exist.");

        try (Scanner scanner = new Scanner(System.in)) {
            out.print("  Enter login: ");
            login = scanner.nextLine().toLowerCase();

            if (login.isEmpty()) {
                out.println("Error: login cannot be empty string");
                return;
            }

            out.print("  Enter password: ");
            password = scanner.nextLine();
            if (password.isEmpty()) {
                out.println("Error: password cannot be empty string");
                return;
            }

            final Crud crud;
            try {
                crud = new Crud(login, password);
            } catch (IllegalArgumentException err) {
                out.println(err.getMessage());
                return;
            }
            out.println("You are now logged in. Welcome " + login + ".");

            int selection;
            do {
                selection = promptMainMenu(scanner);
                if (selection == 1) {
                    createUserMenu(scanner, crud);
                } else if (selection == 2) {
                    listUsers(crud);
                } else if (selection == 3) {
                    updateUser(scanner, crud);
                } else if (selection == 4) {
                    deleteUser(scanner, crud);
                }
            } while (selection != 0);
        }
    }

    /**
     * Prompt main menu. An integer is return for each option:
     * <ul>
     * <li>1 to create an user
     * <li>2 to list users
     * <li>3 to update users
     * <li>4 to delete users
     * <li>0 to exit
     * </ul>
     */
    private static int promptMainMenu(final Scanner scanner) {
        out.print("\nPlease select an option:\n"
                + "(1) Create an user\n"
                + "(2) List users\n"
                + "(3) Update users\n"
                + "(4) Delete users\n"
                + "(0) Exit\n");

        return Integer.parseInt(scanner.nextLine());
    }

    private static void createUserMenu(final Scanner scanner, final Crud crud) {
        out.println("Creating an user:");

        // Prompt first name
        out.print("  Please enter first name: ");
        final String firstName = scanner.nextLine();

        // Validate first name
        if (firstName.isEmpty()) {
            out.println("Error: first name cannot be empty string");
            return;
        }

        // Prompt last name
        out.print("  Please enter last name: ");
        final String lastName = scanner.nextLine();

        // Validate last name
        if (lastName.isEmpty()) {
            out.println("Error: last name cannot be empty string");
            return;
        }

        final UserInfo newUser = crud.createUser(firstName, lastName);        
        out.println("Creating user:");
        out.println(newUser);
    }

    private static void listUsers(final Crud crud) {

        List<UserInfo> users = crud.listUsers();

        AsciiTable table = new AsciiTable();
        table.addRule();
        table.addRow(Arrays.asList("ID", "First name", "Last name"));
        table.addRule();

        users.forEach(user -> {
            table.addRow(Integer.toString(user.getId()), user.getFirstName(), user.getLastName());
            table.addRule();
        });

        out.println(table.render());
    }

    private static void deleteUser(final Scanner scanner, final Crud crud) {
        out.print("  Enter user id: ");
        final int userId = Integer.parseInt(scanner.nextLine());
        crud.deleteUser(userId);
    }

    private static void updateUser(final Scanner scanner, final Crud crud) {

        out.print("  Enter user id of the user to change: ");
        final int userId = Integer.parseInt(scanner.nextLine());

        // Prompt first name
        out.print("  Please enter first name: ");
        final String firstName = scanner.nextLine();
        // Validate first name
        if (firstName.isEmpty()) {
            out.println("Error: first name cannot be empty string");
            return;
        }

        // Prompt last name
        out.print("  Please enter last name: ");
        final String lastName = scanner.nextLine();

        // Validate last name
        if (lastName.isEmpty()) {
            out.println("Error: last name cannot be empty string");
            return;
        }

        crud.updateUser(userId, firstName, lastName);
    }
}