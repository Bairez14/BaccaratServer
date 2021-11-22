import java.io.Serializable;

public class Card {
    // data members
    String suite;
    int value;

    // constructor
    Card(String suite, int value){
        this.suite = suite;
        this.value = value;
    }

    // Setters and Getters
    public String getSuite(){
        return this.suite;
    }

    public void setSuite(String suite){
        this.suite = suite;
    }

    public int getValue() {
        return this.value;
    }
    
    public void setValue(int value) {
        this.value = value;
    }

}