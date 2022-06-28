package fr.projet.jee.Objets;

public class CustomPair {
    private boolean value1;
    private boolean value2;
    
    public CustomPair(boolean value1, boolean value2) {
        this.value1 = value1;
        this.value2 = value2;
    }
    public boolean isValue1() {
        return value1;
    }
    public boolean isValue2() {
        return value2;
    }
}