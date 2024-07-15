import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * this is the test class for {@link JsonParser}.
 */
public class JsonParserTest {

    /**
     * test getting JSON from a file.
     *
     */
    @Test
    public void testGetJSONFromFile() {
        String filename = "test.json"; //replace with a JSON file path
        String jsonText = JsonParser.getJSONFromFile(filename);
        assertNotNull(jsonText); //check that JSON text is not null
        assertFalse(jsonText.isEmpty()); //check that JSON text is not empty
        assertTrue(jsonText.contains("key")); //check if JSON text contains a specific key or value
    }
}

