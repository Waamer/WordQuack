import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class mainMenuController {

    @FXML
    private MFXButton newGameButton, exitButton;

    @FXML
    public void clickMenuButton(ActionEvent event) throws IOException {
        if (event.getSource() instanceof MFXButton button) {
            FXMLLoader loader = new FXMLLoader();
            Parent root = null;
            switch (button.getId()) {
                case "newGameButton":
                    loader.setLocation(getClass().getResource("GamePlayOptions.fxml"));
                    root = loader.load();
                    break;
                case "loadSavedGameButton":
                    loader.setLocation(getClass().getResource("LoadGameScreen.fxml"));
                    root = loader.load();
                    break;
                case "tutorialButton":
                    loader.setLocation(getClass().getResource("tutorial.fxml"));
                    root = loader.load();
                    break;
                case "progressSettingsButton":
                    openMathQuestionPopup();
                    break;
                case "highscoreButton":
                    loader.setLocation(getClass().getResource("HighScoreTable.fxml"));
                    root = loader.load();
                    break;
                case "settingsButton":
                    loader.setLocation(getClass().getResource("settings.fxml"));
                    root = loader.load();
                    break;
                case "exitButton":
                    Stage stage = (Stage) exitButton.getScene().getWindow();
                    stage.close();
                    break;
            }
            if (root != null && button.getId().equals("progressSettingsButton")) {
                // Do nothing, pop-up handled separately
            } else if (root != null) {
                Stage window = (Stage) button.getScene().getWindow();
                window.setScene(new Scene(root));
            }
        }
    }

    private void openMathQuestionPopup() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("mathPopup.fxml"));
            Parent root = loader.load();

            // Create a new stage for the popup
            Stage popupStage = new Stage();
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.setTitle("Math Question");
            popupStage.setScene(new Scene(root));

            // Set the controller and show the popup
            mathPopupController controller = loader.getController();
            controller.setStage(popupStage);
            controller.setMainMenuController(this); // Pass a reference to the mainMenuController
            popupStage.showAndWait(); // Wait for the popup to close before continuing

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadProgressPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ProgressTracker.fxml"));
            Parent root = loader.load();
            Stage window = (Stage) newGameButton.getScene().getWindow(); // Assuming newGameButton is part of the main menu scene
            window.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
