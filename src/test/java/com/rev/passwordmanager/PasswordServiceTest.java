package com.rev.passwordmanager;

import com.rev.passwordmanager.model.PasswordEntry;
import com.rev.passwordmanager.model.User;
import com.rev.passwordmanager.service.PasswordService;
import com.rev.passwordmanager.service.UserService;
import org.junit.jupiter.api.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PasswordServiceTest {

    private static UserService userService;
    private static PasswordService passwordService;
    private static User testUser;

    @BeforeAll
    public static void setup() {
        userService = new UserService();
        passwordService = new PasswordService();

        userService.registerUser("pwuser", "pw@example.com", "Password@123");
        testUser = userService.getUserByUsername("pwuser").orElseThrow();
    }

    @Test
    @Order(1)
    public void testAddPasswordEntry() {
        boolean added = passwordService.addPasswordEntry(testUser.getId(), "Gmail", "user@gmail.com", "Gmail@123", false);
        assertTrue(added, "Password entry should be added");

        List<PasswordEntry> entries = passwordService.getPasswordEntries(testUser.getId());
        assertEquals(1, entries.size(), "There should be 1 password entry");
    }

    @Test
    @Order(2)
    public void testDecryptPassword() {
        List<PasswordEntry> entries = passwordService.getPasswordEntries(testUser.getId());
        PasswordEntry entry = entries.get(0);

        String decrypted = passwordService.decryptPassword(entry.getAccountPassword());
        assertEquals("Gmail@123", decrypted, "Decrypted password should match original");
    }

    @Test
    @Order(3)
    public void testUpdatePasswordEntry() {
        List<PasswordEntry> entries = passwordService.getPasswordEntries(testUser.getId());
        PasswordEntry entry = entries.get(0);

        boolean updated = passwordService.updatePasswordEntry(entry.getId(), entry.getAccountName(), entry.getAccountUsername(), "NewGmail@123");
        assertTrue(updated, "Password entry should be updated");

        String decrypted = passwordService.decryptPassword(passwordService.getPasswordEntries(testUser.getId()).get(0).getAccountPassword());
        assertEquals("NewGmail@123", decrypted, "Password should reflect updated value");
    }

    @Test
    @Order(4)
    public void testDeletePasswordEntry() {
        List<PasswordEntry> entries = passwordService.getPasswordEntries(testUser.getId());
        PasswordEntry entry = entries.get(0);

        boolean deleted = passwordService.deletePasswordEntry(entry.getId());
        assertTrue(deleted, "Password entry should be deleted");

        List<PasswordEntry> afterDelete = passwordService.getPasswordEntries(testUser.getId());
        assertTrue(afterDelete.isEmpty(), "No entries should remain");
    }

    @AfterAll
    public static void cleanup() {
        userService.deleteUser(testUser.getId());
    }
}
