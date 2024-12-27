import org.junit.jupiter.api.*;
import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;

public class LoginFrameTest {

    private LoginFrame loginFrame;

    @BeforeEach
    public void setUp() {
        loginFrame = new LoginFrame();
    }

    @Test
    public void testValidLogin() {
        loginFrame.usernameField.setText("admin");
        loginFrame.passwordField.setText("admin123");

        loginFrame.login();

        // Assuming that the AdminFrame is opened on successful login
        assertTrue(loginFrame.isVisible() == false);
    }

    @Test
    public void testInvalidLogin() {
        loginFrame.usernameField.setText("wrongUser ");
        loginFrame.passwordField.setText("wrongPass");

        loginFrame.login();

        // Check if the error message is shown
        assertTrue(loginFrame.isVisible());
    }
}