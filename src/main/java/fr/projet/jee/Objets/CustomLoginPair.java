package fr.projet.jee.Objets;

public class CustomLoginPair {
    private boolean value1;
    private int value2;
    
    public CustomLoginPair(boolean value1, int value2) {
        this.value1 = value1;
        this.value2 = value2;
    }
    public boolean isValue1() {
        return value1;
    }
    public int isValue2() {
        return value2;
    }
}