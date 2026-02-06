package com.rev.passwordmanager.ui;

import com.rev.passwordmanager.model.PasswordEntry;
import com.rev.passwordmanager.model.SecurityQuestion;
import com.rev.passwordmanager.model.User;
import com.rev.passwordmanager.service.*;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class MainMenu {

    private static final Scanner scanner = new Scanner(System.in);

    private static final UserService userService = new UserService();
    private static final PasswordService passwordService = new PasswordService();
    private static final SecurityQuestionService sqService = new SecurityQuestionService();
    private static final OTPService otpService = new OTPService();

    private User loggedInUser = null;

    // -------------------- Public entry point --------------------
    public void start() {
        showMainMenu();
    }

    // -------------------- Main Menu --------------------
    private void showMainMenu() {
        while (true) {
            System.out.println("\n=== Password Manager ===");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Forgot Password");
            System.out.println("4. Exit");
            System.out.print("Choose: ");
            String choice = scanner.nextLine();
            switch (choice) {
                case "1" -> registerUser();
                case "2" -> loginUser();
                case "3" -> forgotPassword();
                case "4" -> {
                    System.out.println("Goodbye!");
                    System.exit(0);
                }
                default -> System.out.println("Invalid choice, try again.");
            }
        }
    }

    // -------------------- Registration --------------------
    private void registerUser() {
        System.out.print("Username: ");
        String username = scanner.nextLine().trim();

        System.out.print("Email: ");
        String email = scanner.nextLine().trim();

        System.out.print("Password: ");
        String password = scanner.nextLine().trim();

        boolean success = userService.registerUser(username, email, password);
        if (!success) {
            System.out.println("Registration failed. Username might already exist.");
            return;
        }

        System.out.println("Set up 1 security question for account recovery:");
        System.out.print("Question: ");
        String question = scanner.nextLine().trim();
        System.out.print("Answer: ");
        String answer = scanner.nextLine().trim();

        Optional<User> userOpt = userService.getUserByUsername(username);
        userOpt.ifPresent(user -> sqService.addSecurityQuestion(user.getId(), question, answer));

        System.out.println("Registration complete! You can now log in.");
    }

    // -------------------- Login --------------------
    private void loginUser() {
        System.out.print("Username: ");
        String username = scanner.nextLine().trim();
        System.out.print("Password: ");
        String password = scanner.nextLine().trim();

        boolean authenticated = userService.authenticateUser(username, password);
        if (authenticated) {
            loggedInUser = userService.getUserByUsername(username).orElse(null);
            System.out.println("Login successful! Welcome, " + loggedInUser.getUsername());
            showUserMenu();
        } else {
            System.out.println("Invalid credentials.");
        }
    }

    // -------------------- Forgot Password --------------------
    private void forgotPassword() {
        System.out.print("Enter your username: ");
        String username = scanner.nextLine().trim();

        Optional<User> userOpt = userService.getUserByUsername(username);
        if (userOpt.isEmpty()) {
            System.out.println("User not found.");
            return;
        }

        User user = userOpt.get();
        List<SecurityQuestion> questions = sqService.getSecurityQuestions(user.getId());
        if (questions.isEmpty()) {
            System.out.println("No security questions found for this user.");
            return;
        }

        System.out.println("Answer the following security question:");
        SecurityQuestion sq = questions.get(0); // assuming 1 question
        System.out.println(sq.getQuestion());
        String answer = scanner.nextLine().trim();

        if (!sqService.verifyAnswer(sq, answer)) {
            System.out.println("Incorrect answer. Cannot reset password.");
            return;
        }

        String otp = otpService.generateOTP(user.getId());
        System.out.println("OTP generated (simulated): " + otp); // in real app, send via email/SMS
        System.out.print("Enter OTP: ");
        String enteredOtp = scanner.nextLine().trim();

        if (!otpService.verifyOTP(user.getId(), enteredOtp)) {
            System.out.println("Invalid or expired OTP.");
            return;
        }

        System.out.print("Enter new password: ");
        String newPassword = scanner.nextLine().trim();
        boolean updated = userService.updatePassword(user.getId(), newPassword);
        if (updated) System.out.println("Password reset successful!");
        else System.out.println("Failed to reset password.");
    }

    // -------------------- User Menu --------------------
    private void showUserMenu() {
        while (true) {
            System.out.println("\n--- User Menu ---");
            System.out.println("1. Add Password Entry");
            System.out.println("2. View Password Entries");
            System.out.println("3. Search Password Entry");
            System.out.println("4. Update Account Password");
            System.out.println("5. Delete Password Entry");
            System.out.println("6. Update Master Password");
            System.out.println("7. Logout");
            System.out.print("Choose: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> addPasswordEntry();
                case "2" -> viewPasswordEntries();
                case "3" -> searchPasswordEntry();
                case "4" -> updateAccountPassword();
                case "5" -> deleteAccountEntry();
                case "6" -> updateMasterPassword();
                case "7" -> {
                    loggedInUser = null;
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    // -------------------- Add Password Entry --------------------
    private void addPasswordEntry() {
        System.out.print("Title/Account Name: ");
        String title = scanner.nextLine().trim();
        System.out.print("Username for account: ");
        String username = scanner.nextLine().trim();

        System.out.print("Do you want auto-generated password? (y/n): ");
        boolean autoGenerate = scanner.nextLine().trim().equalsIgnoreCase("y");
        String password = "";
        if (!autoGenerate) {
            System.out.print("Enter password: ");
            password = scanner.nextLine().trim();
        }

        boolean success = passwordService.addPasswordEntry(
                loggedInUser.getId(), title, username, password, autoGenerate
        );
        System.out.println(success ? "Password entry added!" : "Failed to add password entry.");
    }

    // -------------------- View Password Entries --------------------
    private void viewPasswordEntries() {
        List<PasswordEntry> entries = passwordService.getPasswordEntries(loggedInUser.getId());
        if (entries.isEmpty()) {
            System.out.println("No password entries found.");
            return;
        }

        System.out.println("\n--- Your Password Entries ---");
        for (PasswordEntry e : entries) {
            String decrypted = passwordService.decryptPassword(e.getAccountPassword());
            System.out.println("Title: " + e.getAccountName());
            System.out.println("Username: " + e.getAccountUsername());
            System.out.println("Password: " + decrypted);
            System.out.println("--------------------------");
        }
    }

    // -------------------- Search Password Entry --------------------
    private void searchPasswordEntry() {
        System.out.print("Enter title to search: ");
        String title = scanner.nextLine().trim();
        Optional<PasswordEntry> entryOpt = passwordService.searchEntry(loggedInUser.getId(), title);

        if (entryOpt.isEmpty()) {
            System.out.println("Entry not found.");
            return;
        }

        PasswordEntry e = entryOpt.get();
        String decrypted = passwordService.decryptPassword(e.getAccountPassword());
        System.out.println("Title: " + e.getAccountName());
        System.out.println("Username: " + e.getAccountUsername());
        System.out.println("Password: " + decrypted);
    }

    // -------------------- Update Account Password --------------------
    private void updateAccountPassword() {
        System.out.print("Enter account title to update: ");
        String title = scanner.nextLine().trim();
        Optional<PasswordEntry> entryOpt = passwordService.searchEntry(loggedInUser.getId(), title);

        if (entryOpt.isEmpty()) {
            System.out.println("Account not found.");
            return;
        }

        PasswordEntry entry = entryOpt.get();
        System.out.print("Enter new password: ");
        String newPassword = scanner.nextLine().trim();

        boolean updated = passwordService.updatePasswordEntry(
                entry.getId(),
                entry.getAccountName(),
                entry.getAccountUsername(),
                newPassword
        );

        System.out.println(updated ? "Account password updated!" : "Failed to update password.");
    }

    // -------------------- Delete Password Entry --------------------
    private void deleteAccountEntry() {
        System.out.print("Enter account title to delete: ");
        String title = scanner.nextLine().trim();
        Optional<PasswordEntry> entryOpt = passwordService.searchEntry(loggedInUser.getId(), title);

        if (entryOpt.isEmpty()) {
            System.out.println("Account not found.");
            return;
        }

        PasswordEntry entry = entryOpt.get();
        boolean deleted = passwordService.deletePasswordEntry(entry.getId());
        System.out.println(deleted ? "Password entry deleted!" : "Failed to delete entry.");
    }

    // -------------------- Update Master Password --------------------
    private void updateMasterPassword() {
        System.out.print("Enter new master password: ");
        String newPassword = scanner.nextLine().trim();
        boolean updated = userService.updatePassword(loggedInUser.getId(), newPassword);
        System.out.println(updated ? "Master password updated!" : "Failed to update password.");
    }
}
