package pl.aogiri.challenges.PieceSlidingPuzzleGame.Game;

import javax.swing.Timer;
import java.util.*;

public class Game {
    private Timer timer;
    private int actions;
    private Map<String, String> blocks;
    private Map<String, String> victory = new LinkedHashMap<>();

    public Game() {
    }

    public void setup(){
        actions=0;
        generateBlocks();
    }

    private void generateBlocks(){
        this.blocks = new LinkedHashMap<>();
        String [] x = {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15",""};
        List<String> numbers = Arrays.asList(x);
        Collections.shuffle(numbers);

        ListIterator<String > iterator = numbers.listIterator();

        int counter = 0;
        for(int i = 0 ; i<4 ; i++)
            for(int k = 0 ; k<4 ; k++) {
                counter++;
                blocks.put(i+"x"+k,iterator.next());
                if (counter == 16){
                    victory.put(i + "x" + k, "");
                    break;
                }

                victory.put(i + "x" + k, String.valueOf(counter));
            }
        shuffle();
    }

    private void shuffle() {

    }


    public Timer getTimer() {
        return timer;
    }

    public void setTimer(Timer timer) {
        this.timer = timer;
    }

    public int getActions() {
        return actions;
    }

    public void setActions(int actions) {
        this.actions = actions;
    }

    public Map<String,String> getBlocks() {
        return blocks;
    }

    public void setBlocks(Map<String,String> blocks) {
        this.blocks = blocks;
    }

    public boolean isVictory(){
        System.out.println(victory.toString());
        System.out.println(blocks.toString());
        return blocks.equals(victory);
    }

    public void increaseAction(){
        this.actions++;
    }
}
