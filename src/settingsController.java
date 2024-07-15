import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXSlider;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class settingsController {

    public MFXButton backButton;
    public MFXSlider soundSlider, musicSlider;
    public static float soundVolume = 0; // JSON REPLACE (Non player settings)
    public static float musicVolume = 50; // JSON REPLACE (Non player settings)


    public void initialize() {
        soundSlider.setValue(50);
        musicSlider.setValue(storePaths.slider);
    }

    public void handleBackButton() throws MultimediaException, UnsupportedAudioFileException, IOException, LineUnavailableException {


        musicVolume = (float) (musicSlider.getValue()*.7) - 75;
        storePaths.slider = (float) musicSlider.getValue();
        Main.clip.stop();
        FloatControl gainControl = (FloatControl) Main.clip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(musicVolume);
        Main.clip.start();


        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("mainMenu.fxml"));
            Parent root = loader.load();
            Stage window = (Stage) backButton.getScene().getWindow(); // Assuming newGameButton is part of the main menu scene
            window.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
