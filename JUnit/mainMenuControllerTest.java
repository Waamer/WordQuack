import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * this is the test class for {@link mainMenuController}.
 */
public class mainMenuControllerTest extends ApplicationTest {

    /**
     * start method to initialize the JavaFX scene for testing.
     * @param stage The primary stage to be used for displaying the JavaFX scene.
     * @throws IOException If an I/O error occurs during loading.
     */
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("mainMenu.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * test for clicking menu buttons.
     */
    @Test
    public void testClickMenuButton() {
        clickOn("#newGameButton");
        assertNotNull(lookup("#verbButton").query());

        clickOn("#loadSavedGameButton");
        assertNotNull(lookup("#button1").query());

        clickOn("#tutorialButton");
        assertNotNull(lookup("#tutorialText").query());

        clickOn("#progressSettingsButton");
        assertNotNull(lookup("#questionLabel").query());

        clickOn("#highscoreButton");
        assertNotNull(lookup("#table").query());

        clickOn("#settingsButton");
        assertNotNull(lookup("#settingsLabel").query());

        clickOn("#exitButton");
        assertFalse(Stage.getWindows().stream().anyMatch(Stage::isShowing));
    }

    /**
     * test for opening math question popup.
     *
     */
    @Test
    public void testOpenMathQuestionPopup() {
        mainMenuController controller = new mainMenuController();
        assertDoesNotThrow(controller::openMathQuestionPopup);
    }

    /**
     * test for loading progress page.
     */
    @Test
    public void testLoadProgressPage() {
        mainMenuController controller = new mainMenuController();
        assertDoesNotThrow(controller::loadProgressPage);
    }
}
