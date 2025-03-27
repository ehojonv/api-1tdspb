package tdsp;

public class Card {
    private int id;
    private String name;
    private String txt;

    public Card() {
    }

    public Card(int id, String name, String txt) {
        this.id = id;
        this.name = name;
        this.txt = txt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }
}
