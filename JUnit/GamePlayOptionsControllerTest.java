import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.testfx.framework.junit5.ApplicationTest;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

/**
 * this is the test class for {@link GamePlayOptionsController}.
 */
public class GamePlayOptionsControllerTest extends ApplicationTest {

    private GamePlayOptionsController controller;

    /**
     * sets up the test environment before each test method is executed.
     */
    @BeforeEach
    public void setUp() {
        controller = new GamePlayOptionsController();
        controller.playerName = new TextField();
        controller.verbButton = new MFXButton();
        controller.adjectiveButton = new MFXButton();
        controller.nounButton = new MFXButton();
    }

    /**
     * test the initialization of the controller.
     */
    @Test
    public void testInitialize() {
        // Make sure the buttons are disabled
        controller.initialize();
        assertTrue(controller.verbButton.isDisabled());
        assertTrue(controller.adjectiveButton.isDisabled());
        assertTrue(controller.nounButton.isDisabled());
    }

    /**
     * test handling name entered event.
     */
    @Test
    public void testNameEntered() {
        //empty name
        controller.onNameEntered();
        assertTrue(controller.verbButton.isDisabled());
        assertTrue(controller.adjectiveButton.isDisabled());
        assertTrue(controller.nounButton.isDisabled());

        //non-empty name
        controller.playerName.setText("TestName");
        controller.onNameEntered();
        assertFalse(controller.verbButton.isDisabled());
        assertFalse(controller.adjectiveButton.isDisabled());
        assertFalse(controller.nounButton.isDisabled());
    }

    /**
     * Ttst creating a file.
     */
    @Test
    public void testCreateFile() throws IOException {
        String fileName = "TestFile";
        String relativePath = "savedGames/";
        String projectPath = System.getProperty("user.dir");
        String path = projectPath + "\\" + relativePath;
        File directory = new File(path);
        assertFalse(directory.exists());

        //mocking the static method call to File.createNewFile()
        try (MockedStatic<File> mockedFile = Mockito.mockStatic(File.class)) {
            mockedFile.when(() -> new File(directory, fileName + ".json")).thenReturn(mock(File.class));
            controller.createFile(fileName);
            assertTrue(directory.exists());
        }
    }

    /**
     * test handling the click event of gameplay options button.
     *
     */
    @Test
    public void testClickGameplayOptionsButton() throws IOException {
        //verb
        ActionEvent event = mock(ActionEvent.class);
        MFXButton verbButton = controller.verbButton;
        verbButton.setId("verbButton");
        controller.clickGameplayOptionsButton(event);
        assertEquals("Verbs", topicChoosen.topicChosen);

        //adjective
        MFXButton adjectiveButton = controller.adjectiveButton;
        adjectiveButton.setId("adjectiveButton");
        controller.clickGameplayOptionsButton(event);
        assertEquals("Adjectives", topicChoosen.topicChosen);

        //noun
        MFXButton nounButton = controller.nounButton;
        nounButton.setId("nounButton");
        controller.clickGameplayOptionsButton(event);
        assertEquals("Nouns", topicChoosen.topicChosen);

        //back
        MFXButton backButton = controller.backButton;
        backButton.setId("backButton");
        controller.clickGameplayOptionsButton(event);
    }

    /**
     * test getting the player name.
     */
    @Test
    public void testGetPlayerName() {
        String name = "TestName";
        controller.playerName.setText(name);
        assertEquals(name, controller.getPlayerName());
    }
}
