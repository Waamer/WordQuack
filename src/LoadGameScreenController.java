import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class LoadGameScreenController {
    @FXML
    public MFXButton button1, button2, button3, button4, button5, button6;
    @FXML
    private MFXButton backButton;

    public MFXButton[] buttons;
    public ArrayList<String> saveFiles = new ArrayList<>(); // Imported From JSON


    public void initialize() {
        // Specify the relative directory path within the project
        String relativePath = "savedGames/";

        // Get the absolute path of the project directory
        String projectPath = System.getProperty("user.dir");

        // Combine the project directory path with the relative directory path
        String path = projectPath + "/" + relativePath;

        // Create the directory if it doesn't exist
        File directory = new File(path);
        if (!directory.exists()) {
            directory.mkdirs(); // Create directory and any necessary parent directories
        }

        // Set the load path
        loadPaths.path = path;

        // Populate the buttons with available saved games
        buttons = new MFXButton[]{button1, button2, button3, button4, button5, button6};

        File directoryPath = new File(loadPaths.path);
        String[] files = directoryPath.list();

        for (int i = 0; i < Objects.requireNonNull(files).length; i++) {
            if (files[i].endsWith(".json")) {
                saveFiles.add(files[i].replaceFirst(".json", ""));
            }
        }

        for (int i = 0; i < buttons.length; i++) {
            if (i < saveFiles.size()) {
                buttons[i].setText(saveFiles.get(i) + "'s game");
                buttons[i].setDisable(false);
            } else {
                buttons[i].setDisable(true);
            }
        }

    }

    public void clickGameButton(javafx.event.ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("gameplay.fxml"));
        Parent root = loader.load();

        if (event.getSource() instanceof MFXButton button) {
            Stage window = (Stage) button.getScene().getWindow();
            window.setScene(new Scene(root));
            loadPaths.fileName = button.getText().replaceFirst("'s game", "") + ".json";

            File file = new File(loadPaths.path + "\\" + loadPaths.fileName);
            if (!file.exists() || file.length() == 0) {
                System.out.println("Error: File doesn't exist or is empty.");
                return;
            }

            JSONParser jsonParser = new JSONParser();
            try (FileReader reader = new FileReader(file)) {
                Object obj = jsonParser.parse(reader);
                JSONObject player = (JSONObject) obj;
                JSONObject playerObject = (JSONObject) player.get("Player:");
                int seconds = Integer.parseInt(playerObject.get("Seconds:").toString());
                int minutes = Integer.parseInt(playerObject.get("Minutes:").toString());
                int points = Integer.parseInt(playerObject.get("Score:").toString());
                int level = Integer.parseInt(playerObject.get("Level:").toString());
                int finalAccuracy = Integer.parseInt(playerObject.get("finalAccuracy:").toString());
                String difficulty = playerObject.get("Difficulty:").toString();
                String topic = playerObject.get("Topic:").toString();

                // Get the controller instance and update its game state
                gameplayController controller = loader.getController();
                controller.updateGameState(seconds, minutes, points, level, finalAccuracy, difficulty, topic);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    public void clickBackButton() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("mainMenu.fxml"));
        Parent root = loader.load();
        Stage window = (Stage) backButton.getScene().getWindow();
        window.setScene(new Scene(root));
    }

}
