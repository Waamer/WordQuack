import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * this is the test class for the ProgressController class.
 *
 */
public class ProgressControllerTest extends ApplicationTest {

    private ProgressController controller;
    private Stage stage;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("progress.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        controller = loader.getController();
        this.stage = stage;
    }

    /**
     * test if the initialize method sets up all necessary components properly.
     */
    @Test
    public void testInitialize() {
        assertNotNull(controller.selectDifficulty);
        assertNotNull(controller.difficultyList);
        assertNotNull(controller.level);
        assertNotNull(controller.time);
        assertNotNull(controller.accuracy);
        assertNotNull(controller.highscore);
    }

    /**
     * test if the loadPlayerData method throws RuntimeException.
     */
    @Test
    public void testLoadPlayerData() {
        assertThrows(RuntimeException.class, () -> controller.loadPlayerData());
    }

    /**
     * test if clicking the back button navigates back to the main menu.
     *
     * @throws IOException      tf an I/O error occurs when loading the main menu FXML file
     * @throws TimeoutException tf the operation exceeds the given time limit
     */
    @Test
    public void testClickBackButton() throws IOException, TimeoutException {
        clickOn("#backButton");
        assertNotNull(controller.backButton.getScene().getRoot());
    }
}
