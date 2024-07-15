import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * this is test class for {@link mathPopupController}.
 *
 */
public class mathPopupControllerTest extends ApplicationTest {

    private mathPopupController controller;

    /**
     * start method to initialize the JavaFX scene for testing.
     * @param stage The primary stage to be used for displaying the JavaFX scene.
     * @throws Exception If an exception occurs during loading.
     */
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("mathPopup.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        controller = loader.getController();
    }

    /**
     * test for initialization.
     */
    @Test
    public void testInitialize() {
        assertNotNull(controller);
        assertNotNull(controller.questionText);
        assertNotNull(controller.responseText);
        assertNotNull(controller.answerField);
        assertTrue(controller.questionText.getText().contains("What is the result of: "));
    }

    /**
     * test for checking answer.
     */
    @Test
    public void testCheckAnswer() {
        //correct answer
        controller.answerField.setText(String.valueOf(controller.firstNumber * controller.secondNumber));
        clickOn("#answerField");
        type(KeyCode.ENTER);
        assertNull(controller.stage);

        //incorrect answer
        controller.answerField.setText("100");
        clickOn("#answerField");
        type(KeyCode.ENTER);
        assertTrue(controller.responseText.getText().contains("Incorrect answer"));
    }

    /**
     * test for closing popup.
     */
    @Test
    public void testClosePopup() {
        controller.closePopup();
        assertNull(controller.stage);
    }
}



