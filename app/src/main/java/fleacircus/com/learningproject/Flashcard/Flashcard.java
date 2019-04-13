package fleacircus.com.learningproject.Flashcard;

public class Flashcard {

    private String Card_Front;
    private String Card_Back;
    private int count;

    public String getCard_Front() {
        return Card_Front;
    }

    public void setCard_Front(String card_Front) {
        Card_Front = card_Front;
    }

    public String getCard_Back() {
        return Card_Back;
    }

    public void setCard_Back(String card_Back) {
        Card_Back = card_Back;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
