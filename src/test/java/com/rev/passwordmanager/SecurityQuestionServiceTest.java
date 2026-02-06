package com.rev.passwordmanager;

import com.rev.passwordmanager.model.SecurityQuestion;
import com.rev.passwordmanager.model.User;
import com.rev.passwordmanager.service.SecurityQuestionService;
import com.rev.passwordmanager.service.UserService;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SecurityQuestionServiceTest {

    private static UserService userService;
    private static SecurityQuestionService sqService;
    private static User testUser;

    @BeforeAll
    public static void setup() {
        userService = new UserService();
        sqService = new SecurityQuestionService();

        userService.registerUser("squser", "sq@example.com", "SqPass@123");
        testUser = userService.getUserByUsername("squser").orElseThrow();
    }

    @Test
    @Order(1)
    public void testAddSecurityQuestion() {
        boolean added = sqService.addSecurityQuestion(testUser.getId(), "Favorite color?", "Blue");
        assertTrue(added, "Security question should be added");
    }

    @Test
    @Order(2)
    public void testGetSecurityQuestions() {
        List<SecurityQuestion> questions = sqService.getSecurityQuestions(testUser.getId());
        assertEquals(1, questions.size(), "There should be 1 security question");
        assertEquals("Favorite color?", questions.get(0).getQuestion());
    }

    @Test
    @Order(3)
    public void testVerifyAnswer() {
        SecurityQuestion sq = sqService.getSecurityQuestions(testUser.getId()).get(0);
        assertTrue(sqService.verifyAnswer(sq, "Blue"), "Answer should be verified correctly");
        assertFalse(sqService.verifyAnswer(sq, "Red"), "Wrong answer should fail verification");
    }

    @AfterAll
    public static void cleanup() {
        userService.deleteUser(testUser.getId());
    }
}
