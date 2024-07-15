public class Word {

    private String word;
    private String wordInfo;
    private boolean solved;


    public Word(String word, String wordInfo) {
        this.word = word;
        this.wordInfo = wordInfo;
        this.solved = false;
    }

    public String getWord() {
        return word;
    }

    public String getWordInfo() {
        return wordInfo;
    }

    public boolean isSolved() {
        return solved;
    }

    public void setSolved(boolean solved) {
        this.solved = solved;
    }
}
