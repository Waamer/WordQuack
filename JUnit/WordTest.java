import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * this is the test class for the Word class.
 */
public class WordTest {

    /**
     * test if the constructor initializes the word, wordInfo, and solved fields correctly.
     */
    @Test
    public void testConstructor() {
        String word = "Test";
        String wordInfo = "Test Word Info";
        Word wordObject = new Word(word, wordInfo);
        assertFalse(wordObject.isSolved());
        assertEquals(word, wordObject.getWord());
        assertEquals(wordInfo, wordObject.getWordInfo());
    }

    /**
     * test if the setters and getters work as expected.
     *
     */
    @Test
    public void testGettersAndSetters() {
        String word = "Test";
        String wordInfo = "Test Word Info";
        Word wordObject = new Word(word, wordInfo);
        assertFalse(wordObject.isSolved());

        //test setters
        wordObject.setSolved(true);
        assertTrue(wordObject.isSolved());

        //test getters
        assertEquals(word, wordObject.getWord());
        assertEquals(wordInfo, wordObject.getWordInfo());
    }
}
