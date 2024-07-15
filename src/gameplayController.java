import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXProgressBar;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.IOException;
import java.util.*;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class gameplayController {

    @FXML
    public MFXButton Button00, Button01, Button02, Button03, Button04, Button05, Button06, Button07, Button08;
    @FXML
    public Button pauseButton, exitButton, mainMenuButton;
    @FXML
    public Text word1, word2, word3, word4, word5, word6, word7, word8, word9, word10, word11, word12;
    @FXML
    public Text inputWord;
    @FXML
    public Text pointsText;
    @FXML
    public MFXProgressBar progressBar;
    @FXML
    public FontIcon duckTalk, duckGlasses;
    @FXML
    public Text SpeechBubble, difficultyText, levelText, topicText, timeText;

    public MFXButton[] buttonArray;
    public Text[] wordTextArray;
    int wordIDCounter;
    public  static String topic = topicChoosen.topicChosen;
    public static String difficulty = topicChoosen.difficultyChosen; // EXTRACTED FROM JSON PLAYER, OTHERWISE SELECTED BY USER
    public static int level = 1; // EXTRACTED FROM JSON SAVED PLAYER, OTHERWISE STARTS AT 1
    public static int points = 0; // EXTRACTED FROM JSON SAVED PLAYER, OTHERWISE STARTS AT 0
    public ArrayList<Word> wordList = new ArrayList<>();
    public String[][] Verbs = {{"Pant", "Paint", "Plan", "Point"}, {"Round", "Undo", "Rust", "Sort"}, {"Master", "Matter", "Mentor", "Note"}};
    public String[][] Adjectives = {{"Bold", "Old", "Bald", "Bad"}, {"Dirty", "Airy", "Arid", "Dary"}, {"Peachy", "Cheap", "Achy", "Hype"}};
    public String[][] Nouns = {{"Bass", "Base", "Bane", "Bean"}, {"Gouge", "Group", "Gourd", "Gorge"}, {"Outlaw", "Outwit", "Outlet", "Auto"}};
    public String[][] verbLetters = {{"p", "a", "n", "t", "i", "l", "o"}, {"r", "o", "u", "n", "d", "s", "t"}, {"m", "a", "s", "t", "e", "r", "n", "o", "t"}};
    public String[][] adjLetters = {{"b", "o", "l", "d", "a"}, {"d", "i", "r", "t", "y", "a"}, {"p", "e", "a", "c", "h", "y"}};
    public String[][] nounLetters = {{"b", "a", "s", "s", "e", "n"}, {"g", "o", "u", "r", "g", "e", "p", "d"}, {"o", "u", "t", "l", "a", "w", "i", "e", "t"}};
    public String[] hintArray = {
            "Verbs: A verb is a kind of word (part of speech) that tells about an action or a state. It is the main part of a sentence: every sentence has a verb. Examples are 'run', 'jump', 'eat', 'drink', 'be', 'do', 'have', etc.",
            "Adjectives: An adjective is a word that describes a noun. It tells you something about the noun. For example, a red car, a big house, a beautiful flower, etc. It is a describing word.",
            "Nouns: A noun is a kind of word (part of speech) that is usually the name of something such as a person, place, thing, quality, or idea. In a sentence, nouns can play the role of subject, direct object, indirect object, subject complement, object complement, appositive, or adjective. Examples are 'table', 'dog', 'city', 'love', 'idea', etc."
    };
    public static int seconds = 0; // TO BE SAVED IN JSON PLAYER
    public static int minutes = 0; // TO BE SAVED IN JSON PLAYER
    public Timeline timer;
    public int initializeCounter = 0;
    public int correctGuesses = 0;
    public int incorrectGuesses = 0;
    public static int finalAccuracy; // TO BE SAVED/EXTRACTED IN/FROM JSON PLAYER
    private GameState gameState;


    public void initialize() {
        // Set visuals
        progressBar.setProgress(0.0);
        topicText.setText("| " + topic);
        levelText.setText("Level " + level);
        difficultyText.setText("| " + difficulty);
        pointsText.setText("" + points);

        // Initialize Buttons and wordTexts used
        buttonArray = new MFXButton[]{Button00, Button01, Button02, Button03, Button04, Button05, Button06, Button07, Button08};
        wordTextArray = new Text[]{word1, word2, word3, word4, word5, word6, word7, word8, word9, word10, word11, word12};

        // Choose the correct word bank based on the topic
        String[][] selectedArray = Verbs;
        String[] selectedLettersArray = verbLetters[level - 1]; // Assuming level starts from 1
        if (Objects.equals(topic, "Adjectives")) {
            selectedArray = Adjectives;
            selectedLettersArray = adjLetters[level - 1];
        } else if (Objects.equals(topic, "Nouns")) {
            selectedArray = Nouns;
            selectedLettersArray = nounLetters[level - 1];
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

        // Clear the wordList and add the words from past level if any
        wordList.clear();
        for (int i = 0; i < level - 1; i++) {
            for (String word : selectedArray[i]) {
                Word solvedWord = new Word(word, getDefinition(word));
                solvedWord.setSolved(true);
                wordList.add(solvedWord); // Set solved to true for previous levels
            }
        }

        // Add the words for the current level to wordList
        for (String word : selectedArray[level - 1]) {
            wordList.add(new Word(word, getDefinition(word)));
        }

        // Display the words on the screen using wordList, display them differently based on difficulty
        int startIndex = (level - 1) * 4;
        int endIndex = startIndex + 4;
        for (int i = startIndex; i < endIndex; i++) {
            Word currentWord = wordList.get(i);
            switch (difficulty) {
                case "Junior" -> wordTextArray[i].setText(scrambleWord(currentWord.getWord()).toUpperCase());
                case "Intermediate" -> {
                    String scrambledWord = currentWord.getWord().charAt(0) + "*".repeat(currentWord.getWord().length() - 2) + currentWord.getWord().charAt(currentWord.getWord().length() - 1);
                    wordTextArray[i].setText(scrambledWord);
                }
                case "Senior" -> {
                    String scrambledWord = currentWord.getWord().charAt(0) + "*".repeat(currentWord.getWord().length() - 1);
                    wordTextArray[i].setText(scrambledWord);
                }
            }
        }

        // Display the solved words from previous levels
        for (int i = 0; i < startIndex; i++) {
            Word solvedWord = wordList.get(i);
            Text solvedText = wordTextArray[i];
            solvedText.setText(solvedWord.getWord().toUpperCase());

            solvedText.setOnMouseClicked(event -> SpeechBubble.setText("Definition of " + solvedWord.getWord() + ": " + solvedWord.getWordInfo()));

            solvedText.setUnderline(true);
            solvedText.setStyle("-fx-fill: blue; -fx-cursor: hand;");
        }

        // Initialize and start the timer
        if (initializeCounter == 0) {
            timer = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
                seconds++;
                if (seconds == 60) {
                    seconds = 0;
                    minutes++;
                }
                timeText.setText(String.format("%02d:%02d", minutes, seconds));
            }));
            timer.setCycleCount(Timeline.INDEFINITE);
            timer.play();
        }

        initializeCounter++; // Increment the counter to prevent timer from starting again
    }

    public void updateGameState(int seconds, int minutes, int points, int level, int finalAccuracy, String difficulty, String topic) {
        this.seconds = seconds;
        this.minutes = minutes;
        this.points = points;
        this.level = level;
        this.finalAccuracy = finalAccuracy;
        this.difficulty = difficulty;
        this.topic = topic;

        topicChoosen.topicChosen = topic;
        topicChoosen.difficultyChosen = difficulty;

        initialize(); // Reinitialize the game with the updated state
    }


    @FXML
    public void clickPauseButton() {
        if (timer != null) {
            timer.pause();
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("pauseScreen.fxml"));
            Parent root = loader.load();

            // Create a new stage for the popup
            Stage popupStage = new Stage();
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.setTitle("Pause Menu");
            popupStage.setScene(new Scene(root));

            // Show the popup
            pauseScreenController controller = loader.getController();
            controller.setStage(popupStage);
            controller.setGameplayController(this);
            popupStage.showAndWait(); // Wait for the popup to close before continuing
            timer.play();

        } catch (NullPointerException e) {

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void exitGame(){
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }
    public void loadMainMenu() {
        try {
            if (timer != null) {
                timer = null;
            }
            level = 1;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("mainMenu.fxml"));
            Parent root = loader.load();
            Stage window = (Stage) mainMenuButton.getScene().getWindow(); // Assuming newGameButton is part of the main menu scene
            window.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private String scrambleWord(String word) {
        List<String> letters = Arrays.asList(word.split(""));
        Collections.shuffle(letters);
        return String.join("", letters);
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

    public void handleHintButton() throws MultimediaException {
        // Display hint in SpeechBubble based on the selected topic
        if (Objects.equals(topic, "Verbs")) {
            SpeechBubble.setText(hintArray[0]);
        } else if (Objects.equals(topic, "Adjectives")) {
            SpeechBubble.setText(hintArray[1]);
        } else if (Objects.equals(topic, "Nouns")) {
            SpeechBubble.setText(hintArray[2]);
        }
        duckTalk.setIconLiteral("mdi-comment-processing");

        if (!Objects.equals(difficulty, "Junior")) {
            boolean hintApplied = false;

            // Iterate through the wordList to find a word with asterisks and apply hint
            for (Word word : wordList) {
                if (!word.isSolved()) {
                    // Find the corresponding index in wordTextArray
                    int wordIndex = wordList.indexOf(word);
                    Text wordText = wordTextArray[wordIndex];
                    String currentText = wordText.getText();

                    // Check if the word contains asterisks
                    if (currentText.contains("*")) {
                        // Get the index of the asterisk
                        int asteriskIndex = currentText.indexOf("*");

                        // Replace the asterisk with the correct letter from the original word
                        char correctLetter = word.getWord().charAt(asteriskIndex);
                        StringBuilder newText = new StringBuilder(currentText);
                        newText.setCharAt(asteriskIndex, correctLetter);

                        // Update the corresponding Text object with the correct letter
                        wordText.setText(newText.toString());

                        // Deduct points for using hint
                        points--;
                        pointsText.setText("" + points);

                        hintApplied = true;
                        if (!wordText.getText().contains("*")) {
                            solvedWord(word);
                        }
                        break;
                    }
                }
            }

            // If no hint was applied, display a message indicating no hint was available
            if (!hintApplied) {
                SpeechBubble.setText("No hint available for this level.");
            }
        }
    }

    public void handleCheckButton() throws MultimediaException {
        boolean correctGuess = false;
        for (Word word : wordList) {
            if (Objects.equals(inputWord.getText().toUpperCase(), word.getWord().toUpperCase()) && !word.isSolved()) {
                SoundPlayer player = new SoundPlayer();
                player.play("Correct.wav");
                duckTalk.setIconLiteral("mdi-comment-check");
                SpeechBubble.setText("Correct! Good job!");
                switch (level) {
                    case 1 -> points += 2;
                    case 2 -> points += 3;
                    case 3 -> points += 4;
                }
                solvedWord(word);
                correctGuess = true;
                correctGuesses++; // Increment correct guesses
                break;
            }
        }

        if (!correctGuess) {
            SoundPlayer player = new SoundPlayer();
            player.play("Wrong.wav");
            duckTalk.setIconLiteral("mdi-comment-alert");
            SpeechBubble.setText("Incorrect! Try your best, you got this!");
            incorrectGuesses++; // Increment incorrect guesses
        }

        for (MFXButton button : buttonArray) {
            button.setStyle("-fx-background-color: #FBFAF5");
            button.setId("Unselected");
        }
        inputWord.setText("");

        // Calculate and display guess accuracy
        double accuracy = (double) correctGuesses / (correctGuesses + incorrectGuesses);
        finalAccuracy = (int) (accuracy * 100);
        SpeechBubble.setText(SpeechBubble.getText() + "\n\nGuess Accuracy: " + finalAccuracy + "%");
    }


    private void solvedWord(Word word) throws MultimediaException {
        pointsText.setText("" + points);
        word.setSolved(true);
        Text solvedText = wordTextArray[wordList.indexOf(word)];
        String targetWord = word.getWord().toUpperCase();
        solvedText.setText(targetWord);
        progressBar.setProgress(progressBar.getProgress() + 0.25);
        solvedText.setOnMouseClicked(clickEvent -> SpeechBubble.setText("Definition of " + word.getWord() + ": " + word.getWordInfo()));

        // Make the text appear clickable
        solvedText.setUnderline(true);
        solvedText.setStyle("-fx-fill: blue; -fx-cursor: hand;");

        if (progressBar.getProgress() == 1.0) nextLevel();

    }

    public void nextLevel() throws MultimediaException {
        if (level < 3) {
            level++;
            initialize();
        } else {
            timer.stop();
            SoundPlayer player = new SoundPlayer();
            player.play("Congrats.wav");
            SpeechBubble.setText("Congratulations! You have completed all levels!");
            duckTalk.setIconLiteral("mdi-star");
        }
    }

    public GameState getGameState() {
        return gameState;
    }


    private String getDefinition(String word) {
        return switch (word) {
            case "Pant" -> "To breathe with short, quick breaths - He was panting when he reached the top of the mountain";
            case "Paint" -> "To cover the surface of something with paint - The ceiling was painted a dark gray";
            case "Plan" -> "To decided something in advance - My mom and dad want to plan a trip to Florida";
            case "Point" -> "To put one's finger in a direction to create attention - The boys were pointing at me";

            case "Round" -> "To go around something in a changed direction - The ship rounded the island and sailed North";
            case "Undo" -> "To unfasten or loosen something - The knot was difficult to undo";
            case "Rust" -> "To grow old, to be affected by rust - The old blades had rusted away";
            case "Sort" -> "To arrange according to a system - My mother always sorted her mail by date";

            case "Master" -> "To gain knowledge in an area - I have finally mastered Spanish";
            case "Matter" -> "To be important to someone or something - It does not matter what you want to wear";
            case "Mentor" -> "To train and advise someone else - Mr.Miyagi wants to mentor that boy in karate";
            case "Note" -> "To pay attention to something - I noted her new lunchbox";

            case "Bold" -> "A confident and courageous person - She made a bold attempt to save her sister";
            case "Old" -> "Someone or thing that has lived for a long time - My grandmother is very old";
            case "Bald" -> "Lacking some or all of your hair - Your dad is bald!";
            case "Bad" -> "Of poor quality, or bad standard - Your hamster has a very bad diet";

            case "Dirty" -> "Covered or marked in an unclean substance - The frog she brought home was dirty";
            case "Airy" -> "Spacious and well ventilated - The dorm room for my older sister was quite airy";
            case "Arid" -> "Having very little rain, a dry climate - Florida was super arid when we arrived";
            case "Tidy" -> "Arranged neatly and in order - His room was tidy because his friends were coming";

            case "Peachy" -> "To be satisfactory - Everything is just peachy";
            case "Cheap" -> "Not expensive at all  - The hat from the  all was cheap";
            case "Achy" -> "Suffering from continuous dull pain - My leg has been achy since my surgery";
            case "Hype" -> "Something inciting excitement - We were all excited for Sid's hype birthday party";

            case "Bass" -> "The lowest male singing voice - My grandpa is the bass in our church choir";
            case "Base" -> "The lowest part of foundation of an object - The base of the bed was getting rusted";
            case "Bane" -> "A source of harm or ruin - Robots will be the bane of our existence";
            case "Bean" -> "An edible kidney shaped seed - I didn't want to eat my beans for dinner";

            case "Gouge" -> "A chisel with a concave blade - She tried to threaten me with her gouge";
            case "Group" -> "A number of people in the same area or that have something in common - A group of teens entered the store";
            case "Gourd" -> "A large fruit with a very tough skin - I found a gourd in the forest and brought it home";
            case "Gorge" -> "A deep narrow valley - I almost fell in the gorge while hiking with my family";

            case "Outlaw" -> "A person who has broken the law - Bonnie and Clyde were outlaws";
            case "Tote" -> "A large bag used for carry many items - My tote bag was from the Western Bookstore";
            case "Outlet" -> "A pipe where gas or vapor can escape - The outlet beside the garage needed to be replaced";
            case "Auto" -> "Short for automobile, a vehicle - My dad's new auto was a gray Honda";

            default -> "Definition not found";
        };
    }
}
