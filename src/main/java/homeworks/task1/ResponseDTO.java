package homeworks.task1;

public class ResponseDTO {
    private boolean endOfWord;
    private int pos;
    private String[] text;

    public ResponseDTO(boolean endOfWord, int pos, String[] text) {
        this.endOfWord = endOfWord;
        this.pos = pos;
        this.text = text;
    }

    public boolean isEndOfWord() {
        return endOfWord;
    }

    public void setEndOfWord(boolean endOfWord) {
        this.endOfWord = endOfWord;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public String[] getText() {
        return text;
    }

    public void setText(String[] text) {
        this.text = text;
    }
}
