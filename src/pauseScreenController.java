import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;

public class pauseScreenController {
    @FXML
    private MFXButton resumeButton, saveButton, exitButton, settingsButton, menuButton;

    private Stage stage;
    private gameplayController gamePlayController; // Assuming this is the correct class name
    private GamePlayOptionsController gamePlayOptionsController;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setGameplayController(gameplayController gamePlayController) {
        this.gamePlayController = gamePlayController;
    }

    public void clickMenuButton(ActionEvent event) throws IOException {
        if (event.getSource() instanceof MFXButton button) {
            FXMLLoader loader = new FXMLLoader();
            Parent root = null;
            switch (button.getText()) {
                case "Resume":
                    closePopup();
                    break;
                case "Save":
                    String playerName = storePaths.fileName;

                    JSONObject playerDetails = new JSONObject();
                    playerDetails.put("Name:", playerName);
                    playerDetails.put("Score:", gameplayController.points);
                    playerDetails.put("Seconds:", gameplayController.seconds);
                    playerDetails.put("Minutes:", gameplayController.minutes);
                    playerDetails.put("Level:", gameplayController.level);
                    playerDetails.put("finalAccuracy:", gameplayController.finalAccuracy);
                    playerDetails.put("Difficulty:", gameplayController.difficulty);
                    playerDetails.put("Topic:", gameplayController.topic);

                    JSONObject player = new JSONObject();
                    player.put("Player:", playerDetails);
                    String path;
                    String fileName;

                    if (storePaths.fileName == null) fileName = loadPaths.fileName.replaceFirst(".json", "");
                    else fileName = storePaths.fileName.replaceFirst(".json", "");

                    if (storePaths.path == null) path = loadPaths.path;
                    else path = storePaths.path;

                    // Write JSON file
                    try (FileWriter file = new FileWriter(path + "/" + fileName + ".json")) {
                        file.write(player.toJSONString());
                        file.flush();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    break;
                case "Exit":
                    closePopup();
                    gamePlayController.exitGame();
                    break;
                case "Main Menu":
                    closePopup();
                    gamePlayController.loadMainMenu();
                    break;
            }
            if (root != null) {
                Stage window = (Stage) button.getScene().getWindow();
                window.setScene(new Scene(root));
            }
        }
    }

    private void closePopup() {
        stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }
}
