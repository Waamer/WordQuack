import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * this is the test class for the Player class.
 */
public class PlayerTest {

    /**
     * test method to verify the serialization of a Player instance to JSON.
     *
     * @throws IOException    ff an I/O error occurs during JSON serialization.
     * @throws ParseException ff an error occurs while parsing the JSON string.
     */
    @Test
    void testWriteJSONString() throws IOException, ParseException {
        //player instance
        Player player = new Player("Sid");
        player.setScore(100);
        player.setSeconds(30);
        player.setMinutes(2);
        player.setLevel(3);
        player.setCorrectGuesses(20);
        player.setIncorrectGuesses(5);
        player.setFinalAccuracy(80);

        StringWriter writer = new StringWriter();
        player.writeJSONString(writer);
        String jsonString = writer.toString();

        //go through JSON string to make sure its right
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(jsonString);
        assertEquals(Player.class, obj.getClass());

        //get Player data
        Player parsedPlayer = (Player) obj;
        assertEquals("Sid", parsedPlayer.getPlayerName());
        assertEquals(100, parsedPlayer.getScore());
        assertEquals(30, parsedPlayer.getSeconds());
        assertEquals(2, parsedPlayer.getMinutes());
        assertEquals(3, parsedPlayer.getLevel());
        assertEquals(20, parsedPlayer.getCorrectGuesses());
        assertEquals(5, parsedPlayer.getIncorrectGuesses());
        assertEquals(80, parsedPlayer.getFinalAccuracy());
    }
}
