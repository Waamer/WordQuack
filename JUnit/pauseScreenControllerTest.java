import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * test class for the pauseScreenController class.
 */
public class pauseScreenControllerTest extends ApplicationTest {

    private pauseScreenController controller;
    private Stage stage;

    /**
     * initializes the JavaFX stage for testing.
     *
     * @param stage the JavaFX stage to be initialized.
     * @throws Exception ff an error occurs during stage initialization.
     */
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("pauseScreen.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        controller = loader.getController();
        this.stage = stage;
    }

    /**
     * test method to verify if the stage is set correctly.
     *
     */
    @Test
    public void testSetStage() {
        controller.setStage(stage);
        assertNotNull(controller.stage);
    }

    /**
     * test method to verify if the gameplay controller is set correctly.
     */
    @Test
    public void testSetGameplayController() {
        gameplayController gamePlayController = new gameplayController();
        controller.setGameplayController(gamePlayController);
        assertNotNull(controller.gamePlayController);
    }

    /**
     * test method to verify the functionality of menu buttons.
     */
    @Test
    public void testClickMenuButton() {
        clickOn("#resumeButton");
        assertNull(controller.stage);

        clickOn("#saveButton");
        assertNotNull(controller.stage);

        clickOn("#exitButton");
        assertNull(controller.stage); //stage should be closed

        clickOn("#menuButton");
        assertNull(controller.stage); //stage should be closed
    }

    /**
     * test method to verify if the popup is closed correctly.
     */
    @Test
    public void testClosePopup() {
        controller.closePopup();
        assertNull(controller.stage); //stage should be closed
    }
}
