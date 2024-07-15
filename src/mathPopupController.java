import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Random;

public class mathPopupController {

    @FXML
    private Text questionText, responseText;
    @FXML
    private TextField answerField;

    private int firstNumber;
    private int secondNumber;
    private Stage stage;
    private mainMenuController mainMenuController;

    @FXML
    public void initialize() {
        generateQuestion();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private void generateQuestion() {
        Random random = new Random();
        firstNumber = random.nextInt(9) + 1; // Generate a random number between 1 and 9
        secondNumber = random.nextInt(9) + 1;
        questionText.setText("What is the result of: " + firstNumber + " x " + secondNumber + " ?");
    }

    public void setMainMenuController(mainMenuController mainMenuController) {
        this.mainMenuController = mainMenuController;
    }

    @FXML
    private void checkAnswer() {
        try {
            int userAnswer = Integer.parseInt(answerField.getText());
            if (userAnswer == (firstNumber * secondNumber)) {
                // Correct answer, close the popup
                stage.close();
                // Proceed to progress page
                mainMenuController.loadProgressPage();
            } else {
                // Incorrect answer, display a message
                responseText.setText("Incorrect answer. Please try again.");
                answerField.clear();
            }
        } catch (NumberFormatException e) {
            // Invalid input, do nothing
        }
    }

    @FXML
    private void closePopup() {
        stage.close();
    }

}
