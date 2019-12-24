package dealba;

import static java.lang.System.out;

import java.util.Scanner;

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
                // TODO: process selection
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
}