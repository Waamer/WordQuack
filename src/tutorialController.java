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
import java.util.*;

public class tutorialController {
    @FXML
    public MFXComboBox<String> selectTopic;
    @FXML
    public Text topicText, talkingText, inputWord, word0, word1;
    @FXML
    public MFXButton Button00, Button01, Button02, Button03, Button04, Button05;
    @FXML
    public MFXButton backButton;

    public ObservableList<String> topicList = FXCollections.observableArrayList("Verbs", "Adjectives", "Nouns");
    public ArrayList<Word> wordList = new ArrayList<>();
    public MFXButton[] buttonArray;
    public Text[] wordTextArray;
    public String[] Verbs = {"Paint", "Plan"};
    public String[] Adjectives = {"Peachy", "Cheap"};
    public String[] Nouns = {"Base", "Bane"};
    public String[] verbLetters = {"p", "a", "n", "t", "i", "l"};
    public String[] adjLetters = {"p", "e", "a", "c", "h", "y"};
    public String[] nounLetters = {"b", "a", "s", "s", "e", "n"};
    public String[] hintArray = {
            "Verbs: A verb is a kind of word (part of speech) that tells about an action or a state. It is the main part of a sentence: every sentence has a verb. Examples are 'run', 'jump', 'eat', 'drink', 'be', 'do', 'have', etc.",
            "Adjectives: An adjective is a word that describes a noun. It tells you something about the noun. For example, a red car, a big house, a beautiful flower, etc. It is a describing word.",
            "Nouns: A noun is a kind of word (part of speech) that is usually the name of something such as a person, place, thing, quality, or idea. In a sentence, nouns can play the role of subject, direct object, indirect object, subject complement, object complement, appositive, or adjective. Examples are 'table', 'dog', 'city', 'love', 'idea', etc."
    };

    public void initialize() {
        selectTopic.setItems(topicList);
        buttonArray = new MFXButton[]{Button00, Button01, Button02, Button03, Button04, Button05};
        wordTextArray = new Text[]{word0, word1};
    }

    public void topicChange() {
        String topic = selectTopic.getSelectionModel().getSelectedItem();
        int topicIndex = selectTopic.getSelectionModel().getSelectedIndex();
        topicText.setText(hintArray[topicIndex]);

        String[] selectedArray = Verbs;
        String[] selectedLettersArray = verbLetters;
        if (Objects.equals(topic, "Adjectives")) {
            selectedArray = Adjectives;
            selectedLettersArray = adjLetters;
        } else if (Objects.equals(topic, "Nouns")) {
            selectedArray = Nouns;
            selectedLettersArray = nounLetters;
        }

        // Shuffle the selected letters array
        List<String> shuffledLetters = Arrays.asList(selectedLettersArray);
        Collections.shuffle(shuffledLetters);
        selectedLettersArray = shuffledLetters.toArray(new String[0]);

        // Assign shuffled letters to buttons based on the selectedLettersArray
        for (int i = 0; i < buttonArray.length; i++) {
            if (i < selectedLettersArray.length) {
                buttonArray[i].setText(selectedLettersArray[i].toUpperCase());
                buttonArray[i].setVisible(true);
            } else {
                buttonArray[i].setVisible(false); // Hide extra buttons
            }
        }

        // Add the words for the current level to wordList
        wordList.clear();
        for (String word : selectedArray) {
            wordList.add(new Word(word, ""));
        }

        // Display the words on the screen using wordList, display them differently based on difficulty
        Word firstWord = wordList.getFirst();
        String scrambledWord = firstWord.getWord().charAt(0) + "*".repeat(firstWord.getWord().length() - 3) + (firstWord.getWord().charAt(firstWord.getWord().length() - 2)) + (firstWord.getWord().charAt(firstWord.getWord().length() - 1));
        wordTextArray[0].setText(scrambledWord);
        Word secondWord = wordList.get(1);
        scrambledWord = secondWord.getWord().charAt(0) + "*".repeat(secondWord.getWord().length() - 2) + (secondWord.getWord().charAt(secondWord.getWord().length() - 1));
        wordTextArray[1].setText(scrambledWord);
    }

    public void gameButtonPress(ActionEvent event) throws MultimediaException {
        SoundPlayer player = new SoundPlayer();
        player.play("ButtonPress.wav");
        MFXButton clickedButton = (MFXButton) event.getSource();
        if (clickedButton.getStyle().contains("-fx-background-color: #F0DBB9")) {
            clickedButton.setStyle("-fx-background-color: #FBFAF5");
            clickedButton.setId("Unselected");
            inputWord.setText(inputWord.getText().replace(clickedButton.getText(), ""));
        } else {
            clickedButton.setStyle("-fx-background-color: #F0DBB9");
            clickedButton.setId("Selected");
            inputWord.setText(inputWord.getText() + clickedButton.getText());
        }
    }

    public void handleCheckButton() throws MultimediaException {
        boolean correctGuess = false;
        for (Word word : wordList) {
            if (Objects.equals(inputWord.getText().toUpperCase(), word.getWord().toUpperCase()) && !word.isSolved()) {
                SoundPlayer player = new SoundPlayer();
                player.play("Correct.wav");
                talkingText.setText("Correct! Good job!");
                word.setSolved(true);
                Text solvedText = wordTextArray[wordList.indexOf(word)];
                String targetWord = word.getWord().toUpperCase();
                solvedText.setText(targetWord);
                correctGuess = true;
                break;
            }
        }

        if (!correctGuess) {
            SoundPlayer player = new SoundPlayer();
            player.play("Wrong.wav");
            talkingText.setText("Incorrect! Try your best, you got this!");
        }

        if (wordList.stream().allMatch(Word::isSolved) && wordList.size() == 2) {
            SoundPlayer player = new SoundPlayer();
            player.play("Congrats.wav");
            talkingText.setText("Congratulations! You've solved all the words!");
        }

        for (MFXButton button : buttonArray) {
            button.setStyle("-fx-background-color: #FBFAF5");
            button.setId("Unselected");
        }
        inputWord.setText("");
    }

    public void clickBackButton() throws  Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("mainMenu.fxml"));
        Parent root = loader.load();
        Stage window  = (Stage) backButton.getScene().getWindow();
        window.setScene(new Scene(root));
    }

}
