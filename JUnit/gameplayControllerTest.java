import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * this is the test class for {@link GameplayController}.
 */
public class gameplayControllerTest {

    private gameplayController controller;

    /**
     * sets up the test environment before each test method is executed.
     */
    @BeforeEach
    public void setUp() {
        controller = new gameplayController();
    }

    /**
     * test the scrambling of a word.
     */
    @Test
    public void testScrambleWord() {
        String word = "example";
        String scrambledWord = controller.scrambleWord(word);

        //check if the scrambled word contains the same letters as the original word
        for (char c : word.toCharArray()) {
            assertTrue(scrambledWord.contains(String.valueOf(c)));
        }

        //check if the length of the scrambled word is the same as the original word
        assertEquals(word.length(), scrambledWord.length());
    }

    /**
     * test fetching the definition of a word.
     */
    @Test
    public void testGetDefinition() {
        // test a known word with a known definition
        String definition = controller.getDefinition("Pant");
        assertEquals("To breathe with short, quick breaths - He was panting when he reached the top of the mountain", definition);

        // test a word with no known definition
        String unknownDefinition = controller.getDefinition("UnknownWord");
        assertEquals("Definition not found", unknownDefinition);
    }

    /**
     * test progressing to the next level.
     */
    @Test
    public void testNextLevel() {
        gameplayController.level = 1;
        gameplayController.points = 10;
        assertDoesNotThrow(() -> controller.nextLevel());
        assertEquals(2, gameplayController.level);

        //test completing the last level
        gameplayController.level = 3;
        assertDoesNotThrow(() -> controller.nextLevel());
        assertEquals(3, gameplayController.level);
    }

    /**
     * test handling the check button action.
     */
    @Test
    public void testHandleCheckButton() {
        //ensure the input word is empty initially
        assertEquals("", controller.inputWord.getText());

        //test correct guess
        String correctWord = "TestWord";
        controller.inputWord.setText(correctWord);
        assertDoesNotThrow(controller::handleCheckButton);
        assertEquals(2, controller.points); // Points should increase by 2 for correct guess
        assertEquals("", controller.inputWord.getText()); // Input word should be cleared after checking

        //test incorrect guess
        String incorrectWord = "WrongWord";
        controller.inputWord.setText(incorrectWord);
        assertDoesNotThrow(controller::handleCheckButton);
        assertEquals(2, controller.points);
        assertEquals("", controller.inputWord.getText()); // Input word should be cleared after checking
    }

    /**
     * test handling a solved word.
     *
     */
    @Test
    public void testSolvedWord() {
        //ensure wordList is initially empty
        assertTrue(controller.wordList.isEmpty());

        //add a word to wordList
        Word word = new Word("Test", "Test definition");
        controller.wordList.add(word);
        controller.wordTextArray[0] = new Text();
        assertDoesNotThrow(() -> controller.solvedWord(word));
        assertEquals(1, controller.points); // Points should increase by 1 after solving the word
        assertFalse(controller.wordList.isEmpty());
        assertTrue(controller.wordList.get(0).isSolved()); // The solved word should have isSolved set to true
    }

    /**
     * test handling the hint button action.
     */
    @Test
    public void testHandleHintButton() {
        //test when hint is available
        controller.topic = "Verbs"; //assuming topic is set to "Verbs" where hint is available
        assertDoesNotThrow(controller::handleHintButton);
        assertTrue(controller.SpeechBubble.getText().contains("A verb is a kind of word"));

        //test when hint is not available
        controller.difficulty = "Junior"; //assuming difficulty is set to "Junior" where hint is not available
        assertDoesNotThrow(controller::handleHintButton);
        assertTrue(controller.SpeechBubble.getText().contains("No hint available"));
    }
}
