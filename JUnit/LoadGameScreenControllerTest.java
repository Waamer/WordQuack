import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

/**
 * this is the test class for {@link LoadGameScreenController}.
 */
@ExtendWith(ApplicationExtension.class)
public class LoadGameScreenControllerTest {
    private LoadGameScreenController controller;

    /**
     * setup method to initialize the controller and mock buttons.
     */
    @BeforeEach
    public void setUp() {
        controller = new LoadGameScreenController();
        controller.button1 = mock(MFXButton.class);
        controller.button2 = mock(MFXButton.class);
        controller.button3 = mock(MFXButton.class);
        controller.button4 = mock(MFXButton.class);
        controller.button5 = mock(MFXButton.class);
        controller.button6 = mock(MFXButton.class);
        controller.backButton = mock(MFXButton.class);
    }

    /**
     * @param stage The primary stage of the JavaFX application.
     */
    @Start
    public void start(Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("LoadGameScreen.fxml"));
            loader.setController(controller);
            Parent root = loader.load();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * test initialization of buttons.
     */
    @Test
    public void testInitialize() {
        controller.initialize();
        for (MFXButton button : controller.buttons) {
            verify(button).setDisable(true);
        }
    }

    /**
     * test clicking a game button.
     */
    @Test
    public void testClickGameButton() {
        File testFile = createTestJsonFile("testFile.json");
        controller.loadPaths = new LoadPaths();
        controller.loadPaths.path = testFile.getParent();
        controller.loadPaths.fileName = testFile.getName();
        ActionEvent event = new ActionEvent();
        controller.clickGameButton(event);
        gameplayController mockController = mock(gameplayController.class);
        FXMLLoader loader = new FXMLLoader();
        loader.setController(mockController);
        assertTrue(loader.getController() instanceof gameplayController);
        verify(mockController).updateGameState(0, 0, 0, 0, 0, "", "");
    }

    /**
     * test clicking the back button.
     *
     */
    @Test
    public void testClickBackButton() {
        ActionEvent event = new ActionEvent();
        controller.clickBackButton();
        verify(controller.backButton.getScene().getWindow()).setScene(any(Scene.class));
    }

    /**
     * helper method to create a test JSON file.
     * @param filename the name of the file to create.
     * @return the created file.
     */
    private File createTestJsonFile(String filename) {
        JSONObject playerObject = new JSONObject();
        playerObject.put("Seconds:", 0);
        playerObject.put("Minutes:", 0);
        playerObject.put("Score:", 0);
        playerObject.put("Level:", 0);
        playerObject.put("finalAccuracy:", 0);
        playerObject.put("Difficulty:", "");
        playerObject.put("Topic:", "");

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("Player:", playerObject);

        try {
            File file = new File(filename);
            FileWriter writer = new FileWriter(file);
            writer.write(jsonObject.toJSONString());
            writer.close();
            return file;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
