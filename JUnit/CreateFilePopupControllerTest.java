import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * this is the test class for {@link CreateFilePopupController}.
 */
public class CreateFilePopupControllerTest extends ApplicationTest {

    private CreateFilePopupController controller;

    /**
     * sets up the test environment before each test method is executed.
     */
    @BeforeEach
    void setUp() {
        controller = new CreateFilePopupController();
    }

    /**
     * overrides the start method to set the stage for testing.
     *
     * @param stage the primary stage for the application
     */
    @Override
    public void start(Stage stage) {
        controller.setStage(stage);
    }

    /**
     * test method for {@link CreateFilePopupController#closePopup()}.
     */
    @Test
    void testClosePopup() {
        // Test closing the popup
        Stage stage = controller.stage;
        assertNotNull(stage);
        controller.closePopup();
        assertFalse(stage.isShowing());
    }

    /**
     * test method for {@link CreateFilePopupController#createFile()}.
     *
     */
    @Test
    void testCreateFile() {
        //simulate setting the game play options controller
        controller.setGamePlayOptionsController(new GamePlayOptionsController());
        controller.answerField.setText("testPath/");
        try {
            //attempt to create the file
            controller.createFile();
        } catch (IOException e) {
            //fail the test if an IOException occurs
            fail("IOException occurred while creating file: " + e.getMessage());
        }
        //expected file name and path
        String expectedFileName = "testName.json";
        String expectedFilePath = "testPath/";
        //verify that the file paths and names are set correctly
        assertEquals(expectedFileName, storePaths.fileName);
        assertEquals(expectedFilePath, storePaths.path);
        assertEquals(expectedFileName + ".json", loadPaths.fileName);
        assertEquals(expectedFilePath, loadPaths.path);

        //check if the file is created
        File file = new File(expectedFilePath + expectedFileName + ".json");
        assertTrue(file.exists());
        assertTrue(file.isFile());
    }
}
