import org.json.simple.JSONAware;
import org.json.simple.JSONStreamAware;
import org.json.simple.JSONValue;

import java.io.IOException;
import java.io.Writer;
import java.util.LinkedHashMap;
import java.util.Map;

public class Player implements JSONStreamAware {

    private String playerName;
    private int score;
    private int seconds;
    private int minutes;
    private int level;
    private int correctGuesses;
    private int incorrectGuesses;
    private int finalAccuracy;

    public Player(String name, int lev, int points) {
        playerName = name;
        score = points;
        seconds = 0;
        minutes = 0;
        level = lev;
        correctGuesses = 0;
        incorrectGuesses = 0;
        finalAccuracy = 0;

    }

    public String getPlayerName() {
        return playerName;
    }

    public int getScore() {
        return score;
    }

    public int getLevel() {
        return level;
    }

    public int getSeconds() {
        return seconds;
    }

    public int getMinutes() {
        return minutes;
    }

    public int getCorrectGuesses() {
        return correctGuesses;
    }

    public int getIncorrectGuesses() {
        return incorrectGuesses;
    }

    public int getFinalAccuracy() {
        return finalAccuracy;
    }

    public void setScore(int newScore) {
        score = newScore;
    }

    public void setSeconds(int time) {
        seconds = time;
    }

    public void setMinutes(int time) {
        minutes = time;
    }

    public void setLevel(int theLevel) {
        level = theLevel;
    }

    public void setCorrectGuesses(int guesses) {
        correctGuesses = guesses;
    }

    public void setIncorrectGuesses(int guesses) {
        incorrectGuesses = guesses;
    }

    public void setFinalAccuracy(int accuracy) {
        finalAccuracy = accuracy;
    }

    @Override
    public void writeJSONString(Writer writer) throws IOException {
        Map obj = new LinkedHashMap();
        obj.put("playerName", playerName);
        obj.put("score", score);
        obj.put("seconds", seconds);
        obj.put("minutes", minutes);
        obj.put("level", level);
        obj.put("correctGuesses", correctGuesses);
        obj.put("incorrectGuesses", incorrectGuesses);
        obj.put("finalAccuracy", finalAccuracy);
        JSONValue.writeJSONString(obj, writer);
    }
}
