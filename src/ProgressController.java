import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ProgressController {

    @FXML
    public MFXButton backButton;
    @FXML
    public MFXComboBox<String> selectDifficulty;

    public ObservableList<String> difficultyList = FXCollections.observableArrayList("Junior", "Intermediate", "Senior");
    public String selectedDifficulty = topicChoosen.difficultyChosen; // Default value is Intermediate
    public Text level, time, accuracy, highscore;
    JSONObject player;
    JSONObject playerObject;

    public void initialize() {
        selectDifficulty.setItems(difficultyList);
        selectDifficulty.setFloatingText(selectedDifficulty);

        // Event listener for combobox selection change
        selectDifficulty.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            selectedDifficulty = newValue; // Update selected difficulty
        });

        loadPlayerData(); // Load player data when initializing

    }

    private void loadPlayerData() {
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader(loadPaths.path + "\\" + loadPaths.fileName)) {

            Object obj = jsonParser.parse(reader);
            player = (JSONObject) obj;
            playerObject = (JSONObject) player.get("Player:"); // Assign playerObject here
            level.setText(playerObject.get("Level:").toString());
            time.setText(playerObject.get("Minutes:").toString() + "m, " + playerObject.get("Seconds:").toString() + "s");
            accuracy.setText(playerObject.get("finalAccuracy:").toString());
            highscore.setText(playerObject.get("Score:").toString());
            selectDifficulty.setFloatingText(playerObject.get("Difficulty:").toString());

        } catch (ParseException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void clickBackButton() throws IOException {

        topicChoosen.difficultyChosen = selectedDifficulty; // Update the static variable in topicChoosen class

        // Update the 'Difficulty' field in playerObject with the current selectedDifficulty value
        playerObject.put("Difficulty:", selectedDifficulty);

        try (FileWriter file = new FileWriter(loadPaths.path + "/" + loadPaths.fileName)) {
            file.write(player.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }

        // Save the updated playerObject to the JSON file

        // Assuming you have a method to save the JSONObject to the file, you can call it here
        // savePlayerData(playerObject);

        // Navigate back to the main menu
        FXMLLoader loader = new FXMLLoader(getClass().getResource("mainMenu.fxml"));
        Parent root = loader.load();
        Stage window = (Stage) backButton.getScene().getWindow();
        window.setScene(new Scene(root));
    }
}
