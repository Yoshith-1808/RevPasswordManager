package com.rev.passwordmanager;

import com.rev.passwordmanager.model.User;
import com.rev.passwordmanager.service.UserService;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserServiceTest {

    private static UserService userService;
    private static User testUser;

    @BeforeAll
    public static void setup() {
        userService = new UserService();
    }

    @Test
    @Order(1)
    public void testRegisterUser() {
        boolean registered = userService.registerUser("testuser", "test@example.com", "Test@1234");
        assertTrue(registered, "User should be registered successfully");

        testUser = userService.getUserByUsername("testuser").orElse(null);
        assertNotNull(testUser, "Registered user should exist");
    }

    @Test
    @Order(2)
    public void testAuthenticateUser() {
        assertTrue(userService.authenticateUser("testuser", "Test@1234"), "User should authenticate successfully");
        assertFalse(userService.authenticateUser("testuser", "WrongPass"), "Authentication should fail with wrong password");
    }

    @Test
    @Order(3)
    public void testUpdatePassword() {
        assertNotNull(testUser);
        boolean updated = userService.updatePassword(testUser.getId(), "NewPass@123");
        assertTrue(updated, "Password should be updated");

        assertTrue(userService.authenticateUser("testuser", "NewPass@123"), "User should login with new password");
    }

    @Test
    @Order(4)
    public void testDeleteUser() {
        assertNotNull(testUser);
        boolean deleted = userService.deleteUser(testUser.getId());
        assertTrue(deleted, "User should be deleted");

        assertFalse(userService.authenticateUser("testuser", "NewPass@123"), "Deleted user should not authenticate");
    }
}
