package to2.dice.game;

import java.io.Serializable;

public class Dice implements Serializable {
    private int diceNumber;
    private int[] diceArray;

    public Dice(int diceNumber){
        this.diceNumber = diceNumber;
        diceArray = new int[diceNumber];
    }

    public Dice(int[] diceArray){
        this.diceNumber = diceArray.length;
        this.diceArray = diceArray;
    }

    public int[] getDiceArray(){
        return diceArray;
    }

    public void setDiceArray(int[] diceArray) {
        this.diceArray = diceArray;
    }
}
