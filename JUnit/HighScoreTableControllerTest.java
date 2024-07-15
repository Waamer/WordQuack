import javafx.scene.control.TableView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.TableViewMatchers.containsRowAtIndex;
import static org.testfx.matcher.control.TableViewMatchers.hasNumRows;

/**
 * this is the test class for {@link HighScoreTableController}.
 */
public class HighScoreTableControllerTest extends ApplicationTest {

    private HighScoreTableController controller;

    /**
     * sets up the test environment before each test method is executed.
     */
    @BeforeEach
    public void setUp() {
        controller = new HighScoreTableController();
    }

    /**
     * starts the JavaFX application before each test method is executed.
     *
     */
    @Override
    public void start(javafx.stage.Stage stage) throws Exception {
        //load the FXML file and start the JavaFX application
        new FXMLLoader(getClass().getResource("HighScoreTable.fxml")).load();
    }

    /**
     * test loading high scores.
     */
    @Test
    public void testLoadHighScores() {
        //test loading high scores when there are no scores
        controller.loadHighScores();
        TableView<Player> table = controller.table;
        assertEquals(0, table.getItems().size()); //check that the table is empty
    }

    /**
     * test clicking the back button.
     */
    @Test
    public void testClickBackButton() {
        clickOn(controller.backButton);
        verifyThat("#backButton", (node) -> node.isVisible()); //verify that the button is visible after click
    }
}
