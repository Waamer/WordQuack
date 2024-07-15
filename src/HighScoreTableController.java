import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.filter.IntegerFilter;
import io.github.palexdev.materialfx.filter.StringFilter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Comparator;
import java.util.Objects;

public class HighScoreTableController {

    @FXML
    public MFXButton backButton;
    @FXML
    private MFXTableView<Player> table;

    private final String savedGamesPath = "savedGames/";

    public void initialize() {
        setupTable();
        table.autosizeColumnsOnInitialization();
        loadHighScores();
    }

    private void setupTable() {
        MFXTableColumn<Player> nameCol = new MFXTableColumn<>("Player Name", true);
        MFXTableColumn<Player> levelCol = new MFXTableColumn<>("Level", true);
        MFXTableColumn<Player> scoreCol = new MFXTableColumn<>("High Score", true, Comparator.comparing(Player::getScore));

        nameCol.setRowCellFactory(person -> new MFXTableRowCell<>(Player::getPlayerName));
        levelCol.setRowCellFactory(person -> new MFXTableRowCell<>(Player::getLevel));
        scoreCol.setRowCellFactory(person -> new MFXTableRowCell<>(Player::getScore));

        table.getTableColumns().addAll(nameCol, levelCol, scoreCol);
        table.getFilters().addAll(
                new StringFilter<>("Name", Player::getPlayerName),
                new IntegerFilter<>("Level", Player::getLevel),
                new IntegerFilter<>("High Score", Player::getScore)
        );
    }

    private void loadHighScores() {
        File directory = new File(savedGamesPath);
        if (!directory.exists()) {
            return; // No saved games folder, return
        }

        File[] files = directory.listFiles();
        if (files == null) {
            return; // No files found in saved games folder, return
        }

        ObservableList<Player> highScoresList = FXCollections.observableArrayList();

        for (File file : files) {
            if (file.isFile() && file.getName().endsWith(".json")) {
                JSONParser jsonParser = new JSONParser();
                try (FileReader reader = new FileReader(file)) {
                    String playerName = file.getName().replace(".json", "");
                    Object obj = jsonParser.parse(reader);
                    JSONObject player = (JSONObject) obj;
                    JSONObject playerObject = (JSONObject) player.get("Player:");
                    int level = Integer.parseInt(playerObject.get("Level:").toString());
                    int score = Integer.parseInt(playerObject.get("Score:").toString());
                    highScoresList.add(new Player(playerName, level, score));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        table.setItems(highScoresList);
    }

    public void clickBackButton() throws IOException {
        backButton.getScene().setRoot(FXMLLoader.load(getClass().getResource("mainMenu.fxml")));
    }
}
