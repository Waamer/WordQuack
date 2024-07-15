import java.util.ArrayList;
import java.util.List;

public class GameState {
    private int seconds;
    private int minutes;
    private int level;
    private int points;
    private ArrayList<Word> wordList;
    private int correctGuesses;
    private int incorrectGuesses;
    private int finalAccuracy;

    // Constructor
    public GameState(int seconds, int minutes, int level, int points, ArrayList<Word> wordList,
                     int correctGuesses, int incorrectGuesses, int finalAccuracy) {
        this.seconds = seconds;
        this.minutes = minutes;
        this.level = level;
        this.points = points;
        this.wordList = new ArrayList<>(wordList); // Create a new list to avoid modifying original list
        this.correctGuesses = correctGuesses;
        this.incorrectGuesses = incorrectGuesses;
        this.finalAccuracy = finalAccuracy;
    }

    public int getSeconds() {
        return seconds;
    }

    public int getMinutes() {
        return minutes;
    }

    public int getLevel() {
        return level;
    }

    public int getPoints() {
        return points;
    }

    public List<Word> getWordList() {
        return new ArrayList<>(wordList); // Return a new list to avoid modifying original list
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

}
