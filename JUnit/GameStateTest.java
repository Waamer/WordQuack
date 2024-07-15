import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * this is the test class for {@link GameState}.
 */
public class GameStateTest {

    private GameState gameState;

    /**
     * sets up the test environment before each test method is executed.
     */
    @BeforeEach
    public void setUp() {
        gameState = new GameState(10, 2, 3, 100, new ArrayList<>(), 5, 2, 70);
    }

    /**
     * test getting the seconds.
     */
    @Test
    public void testGetSeconds() {
        assertEquals(10, gameState.getSeconds());
    }

    /**
     * test getting the minutes.
     */
    @Test
    public void testGetMinutes() {
        assertEquals(2, gameState.getMinutes());
    }

    /**
     * test getting the level.
     */
    @Test
    public void testGetLevel() {
        assertEquals(3, gameState.getLevel());
    }

    /**
     * test getting the points.
     */
    @Test
    public void testGetPoints() {
        assertEquals(100, gameState.getPoints());
    }

    /**
     * test getting the word list.
     */
    @Test
    public void testGetWordList() {
        assertEquals(0, gameState.getWordList().size());
    }

    /**
     * test getting the number of correct guesses.
     *
     */
    @Test
    public void testGetCorrectGuesses() {
        assertEquals(5, gameState.getCorrectGuesses());
    }

    /**
     * test getting the number of incorrect guesses.
     */
    @Test
    public void testGetIncorrectGuesses() {
        assertEquals(2, gameState.getIncorrectGuesses());
    }

    /**
     * test getting the final accuracy.
     */
    @Test
    public void testGetFinalAccuracy() {
        assertEquals(70, gameState.getFinalAccuracy());
    }
}
