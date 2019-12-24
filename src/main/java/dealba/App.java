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
        }
    }
}