import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class GamePlayOptionsController {
    @FXML
    public MFXButton backButton, verbButton, adjectiveButton, nounButton;

    public TextField playerName;
    public String name;

    public void initialize() {
        //originally buttons cannot be used
        verbButton.setDisable(true);
        adjectiveButton.setDisable(true);
        nounButton.setDisable(true);

        //wait for the enter key to be pressed, call onNameEntered
        playerName.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                onNameEntered();
            }
        });
    }

    public void onNameEntered() {
        name = playerName.getText().trim();

        // Check if the name is valid
        if (!name.isEmpty()) {
            // Enable the buttons
            verbButton.setDisable(false);
            adjectiveButton.setDisable(false);
            nounButton.setDisable(false);

        }
    }

    private void createFile(String fileName) {
        // Specify the relative directory path within the project
        String relativePath = "savedGames/";

        // Get the absolute path of the project directory
        String projectPath = System.getProperty("user.dir");

        // Combine the project directory path with the relative directory path
        String path = projectPath + "\\" + relativePath;

        // Create the directory if it doesn't exist
        File directory = new File(path);
        if (!directory.exists()) {
            directory.mkdirs(); // Create directory and any necessary parent directories
        }

        // Set the file name and paths
        storePaths.fileName = fileName;
        storePaths.path = path;
        loadPaths.fileName = fileName + ".json";
        loadPaths.path = path;

        try {
            // Create the JSON file in the specified directory
            File gameSave = new File(directory, fileName + ".json");
            gameSave.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // USE JSON FOR EACH TOPIC BUTTON, DEPENDING ON WHICH ONE SAVE TO Player (GET NAME FROM playerName) AND SET IT IN GAMEPLAY
    // Make sure to set all default values as seen on Confluence File Format or change according to project changes (removed settings)
    public void clickGameplayOptionsButton(ActionEvent event) throws IOException {
        if (event.getSource() instanceof MFXButton button) {
            FXMLLoader loader = new FXMLLoader();
            Parent root = switch (button.getId()) {
                case "verbButton" -> {
                    topicChoosen.topicChosen = "Verbs";
                    loader.setLocation(getClass().getResource("gameplay.fxml"));
                    createFile(name);
                    yield loader.load();
                }
                case "adjectiveButton" -> {
                    topicChoosen.topicChosen = "Adjectives";
                    loader.setLocation(getClass().getResource("gameplay.fxml"));
                    createFile(name);
                    yield loader.load();
                }
                case "nounButton" -> {
                    topicChoosen.topicChosen = "Nouns";
                    loader.setLocation(getClass().getResource("gameplay.fxml"));
                    createFile(name);
                    yield loader.load();
                }
                case "backButton" -> {
                    loader.setLocation(getClass().getResource("mainMenu.fxml"));
                    yield loader.load();
                }
                default -> null;
            };
            if (root != null) {
                Stage window = (Stage) button.getScene().getWindow();
                window.setScene(new Scene(root));
            }
        }
    }

    public String getPlayerName() {
        return playerName.getText().trim();
    }
}
