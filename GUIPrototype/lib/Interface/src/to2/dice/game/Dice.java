package to2.dice.game;

public class Dice {
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
