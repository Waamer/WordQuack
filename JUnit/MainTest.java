import javafx.embed.swing.JFXPanel;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * this is the test class for {@link Main}.
 */
public class MainTest {

    /**
     * setup method to initialize JavaFX for testing.
     */
    @BeforeAll
    public static void setUp() {
        new JFXPanel();
    }

    /**
     * test for the main method.
     *
     */
    @Test
    public void testMain() {
        String[] args = {};
        assertDoesNotThrow(() -> Main.main(args));
    }

    /**
     * test for the start method.
     */
    @Test
    public void testStart() {
        assertDoesNotThrow(() -> {
            Stage stage = new Stage();
            Main main = new Main();
            main.start(stage);
        });
    }

    /**
     * test for the static block initialization.
     */
    @Test
    public void testStaticBlock() {
        assertDoesNotThrow(() -> {
            assertNotNull(Main.audioInputStream);
            assertNotNull(Main.clip);
            assertTrue(Main.clip.isOpen());
        });
    }
}
