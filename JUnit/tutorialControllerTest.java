import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.scene.text.Text;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * this is the test class for the tutorialController class.
 *
 */
public class tutorialControllerTest {

    private tutorialController tutorialController;

    @Mock
    private MFXComboBox<String> selectTopicMock;
    @Mock
    private Text topicTextMock, talkingTextMock, inputWordMock, word0Mock, word1Mock;
    @Mock
    private MFXButton Button00Mock, Button01Mock, Button02Mock, Button03Mock, Button04Mock, Button05Mock, backButtonMock;

    @BeforeEach
    public void setUp() {
        //initialize JavaFX toolkit
        new JFXPanel();
        MockitoAnnotations.openMocks(this);
        tutorialController = new TutorialController();
        tutorialController.selectTopic = selectTopicMock;
        tutorialController.topicText = topicTextMock;
        tutorialController.talkingText = talkingTextMock;
        tutorialController.inputWord = inputWordMock;
        tutorialController.word0 = word0Mock;
        tutorialController.word1 = word1Mock;
        tutorialController.Button00 = Button00Mock;
        tutorialController.Button01 = Button01Mock;
        tutorialController.Button02 = Button02Mock;
        tutorialController.Button03 = Button03Mock;
        tutorialController.Button04 = Button04Mock;
        tutorialController.Button05 = Button05Mock;
        tutorialController.backButton = backButtonMock;
    }

    /**
     * test if the initialize method sets up the UI components correctly.
     */
    @Test
    public void testInitialize() {
        tutorialController.initialize();
        verify(selectTopicMock).setItems(anyList());
        verify(selectTopicMock).setItems(anyList());
        verify(selectTopicMock).setItems(anyList());
    }

    /**
     * test if the topicChange method updates UI components based on the selected topic.
     */
    @Test
    public void testTopicChange() {
        //mock data
        List<String> topicList = Arrays.asList("Verbs", "Adjectives", "Nouns");
        when(selectTopicMock.getSelectionModel().getSelectedItem()).thenReturn("Verbs");
        when(selectTopicMock.getSelectionModel().getSelectedIndex()).thenReturn(0);
        tutorialController.topicList = topicList;

        tutorialController.topicChange();

        //verify that UI components are updated correctly
        verify(topicTextMock).setText(anyString());
        verify(Button00Mock, times(6)).setVisible(anyBoolean());
        verify(Button01Mock, times(6)).setVisible(anyBoolean());
        verify(Button02Mock, times(6)).setVisible(anyBoolean());
        verify(Button03Mock, times(6)).setVisible(anyBoolean());
        verify(Button04Mock, times(6)).setVisible(anyBoolean());
        verify(Button05Mock, times(6)).setVisible(anyBoolean());
        verify(word0Mock).setText(anyString());
        verify(word1Mock).setText(anyString());
    }

    /**
     * test if the gameButtonPress method handles button press events correctly.
     */
    @Test
    public void testGameButtonPress() {
        ActionEvent eventMock = mock(ActionEvent.class);
        when(Button00Mock.getText()).thenReturn("A");
        when(inputWordMock.getText()).thenReturn("");
        when(Button00Mock.getStyle()).thenReturn("-fx-background-color: #F0DBB9");

        tutorialController.gameButtonPress(eventMock);

        verify(Button00Mock).setText(anyString());
        verify(Button00Mock).setVisible(true);
        verify(inputWordMock).setText("A");
    }

    /**
     * test if the handleCheckButton method checks the input word and updates UI components accordingly.
     */
    @Test
    public void testHandleCheckButton() {
        Word word1 = new Word("Test", "");
        Word word2 = new Word("Mock", "");
        tutorialController.wordList = new LinkedList<>(Arrays.asList(word1, word2));
        when(inputWordMock.getText()).thenReturn("Test");

        tutorialController.handleCheckButton();

        verify(talkingTextMock).setText("Correct! Good job!");
        verify(word0Mock).setText("Test");
        verify(word1Mock, never()).setText(anyString());
        verify(Button00Mock, times(6)).setStyle(anyString());
        verify(Button00Mock, times(6)).setId(anyString());
        verify(inputWordMock).setText("");
    }

    /**
     * test if the clickBackButton method navigates back to the main menu.
     */
    @Test
    public void testClickBackButton() throws Exception {
        tutorialController.clickBackButton();
        verify(backButtonMock).getScene();
        verify(backButtonMock).setScene(any(Scene.class));
    }
}
