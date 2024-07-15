import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXSlider;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import javax.sound.sampled.*;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * this is the test class for the settingsController class.
 *
 */
public class settingsControllerTest extends ApplicationTest {

    private SettingsController controller;
    private Stage stage;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("settings.fxml"));
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
        assertNotNull(controller.backButton);
        assertNotNull(controller.soundSlider);
        assertNotNull(controller.musicSlider);
    }

    /**
     * test if handleBackButton method stops and starts the audio clip and navigates back to the main menu.
     *
     * @throws IOException              ff an I/O error occurs when loading the main menu FXML file
     * @throws InterruptedException     ff the thread is interrupted while waiting
     * @throws UnsupportedAudioFileException ff the audio file format is not supported
     * @throws LineUnavailableException ff a line matching the specified description cannot be opened
     * @throws TimeoutException        ff the operation exceeds the given time limit
     */
    @Test
    public void testHandleBackButton() throws IOException, InterruptedException, UnsupportedAudioFileException, LineUnavailableException, TimeoutException {
        //set initial volume
        Main.clip.open(Main.audioInputStream);
        Main.clip.start();
        FloatControl gainControl = (FloatControl) Main.clip.getControl(FloatControl.Type.MASTER_GAIN);
        float initialVolume = gainControl.getValue();

        //simulate changing volume
        controller.musicSlider.setValue(75);
        controller.handleBackButton();

        //check if volume is set properly
        assertEquals((float) (controller.musicSlider.getValue() * 0.7) - 75, gainControl.getValue());

        //check if back button navigates back to main menu
        clickOn("#backButton");
        assertNotNull(controller.backButton.getScene().getRoot());
    }
}


