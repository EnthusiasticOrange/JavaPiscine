package edu.school21.chaseLogic;

public class GameObject {

    private int position;
    private char sign;
    private char endCondition;

    public GameObject(int position, char sign) {
        this.position = position;
        this.sign = sign;
    }

    public GameObject(int position, char sign, char endCondition) {
        this.position = position;
        this.sign = sign;
        this.endCondition = endCondition;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public char getSign() {
        return sign;
    }

    public char getEndCondition() {
        return endCondition;
    }

}
