package pl.aogiri.challenges.PieceSlidingPuzzleGame;

import javafx.util.converter.DateTimeStringConverter;
import pl.aogiri.challenges.PieceSlidingPuzzleGame.Game.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Map;

public class GameWindow extends JFrame {

    private static final long serialVersionUID = 1L;
    private static Game game = new Game();
    private static int t = 0;

    private GameWindow(){
        setSize(new Dimension(400,400));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(5,4));
        setVisible(true);
        setTitle("15 Piece Sliding Puzzle");;
        game.setup();

        load();
    }

    private void load(){
        getContentPane().removeAll();
        JLabel x = new JLabel(LocalTime.MIN.plusSeconds(t).toString());
        x.setPreferredSize(new Dimension(200,100));
        x.setHorizontalAlignment(JLabel.LEFT);
        JLabel y = new JLabel(String.valueOf(game.getActions()));
        y.setPreferredSize(new Dimension(200,100));
        y.setHorizontalAlignment(JLabel.LEFT);
        JLabel test = new JLabel("Time : ");
        test.setHorizontalAlignment(JLabel.RIGHT);
        add(test);
        add(x);
        test = new JLabel("Moves : ");
        test.setHorizontalAlignment(JLabel.RIGHT);
        add(test);
        add(y);
        for(int i = 0 ; i<4 ; i++)
            for(int k = 0 ; k<4 ; k++) {
                JLabel temp = new JLabel(String.valueOf(game.getBlocks().get(i+"x"+k)));
                temp.setPreferredSize(new Dimension(100,100));
                temp.setHorizontalAlignment(JLabel.CENTER);
                temp.setBorder(BorderFactory.createLineBorder(Color.BLUE,1));
                temp.setBackground(Color.RED);
                temp.addMouseListener(new MouseAdapterPiece(i, k, this));
                add(temp);
            }
            validate();
    }

    private static class MouseAdapterPiece extends MouseAdapter{
        int x;
        int y;
        Map<String,String> blocks;
        GameWindow gameWindow;

        public MouseAdapterPiece(int x, int y, GameWindow gameWindow) {
            this.x = x;
            this.y = y;
            this.blocks = game.getBlocks();
            this.gameWindow = gameWindow;
        }

        /**
         * {@inheritDoc}
         *
         * @param e
         */
        @Override
        public void mouseClicked(MouseEvent e) {
            super.mouseClicked(e);
            System.out.println("Debug mouse click");

                if (x > 0) {
                    if(blocks.get((x - 1) + "x" + y).equals(""))
                        move(blocks, (x + "x" + y), ((x - 1) + "x" + y), gameWindow);
                    System.out.println("Moved 1");
                }
                if (x < 3) {
                    if(blocks.get((x + 1) + "x" + y).equals(""))
                        move(blocks, (x + "x" + y), ((x + 1) + "x" + y), gameWindow);
                    System.out.println("Moved 2");
                }
                if (y > 0) {
                    if(blocks.get(x + "x" + (y - 1)).equals(""))
                        move(blocks, (x + "x" + y), (x + "x" + (y - 1)), gameWindow);
                    System.out.println("Moved 3");
                }
                if (y < 3) {
                    if(blocks.get(x + "x" + (y + 1)).equals(""))
                        move(blocks, (x + "x" + y), (x + "x" + (y + 1)), gameWindow);
                    System.out.println("Moved 4");
                }
        }

        /**
         * {@inheritDoc}
         *
         * @param e
         * @since 1.6
         */
        @Override
        public void mouseDragged(MouseEvent e) {
            this.mouseClicked(e);
        }

        /**
         * {@inheritDoc}
         *
         * @param e
         */
        @Override
        public void mouseReleased(MouseEvent e) {
            this.mouseClicked(e);
        }

        /**
         * {@inheritDoc}
         *
         * @param e
         */
        @Override
        public void mousePressed(MouseEvent e) {
            this.mouseClicked(e);
        }
    }

    private static void move(Map<String,String> blocks, String a, String b,GameWindow gw){
        blocks.replace(b, blocks.get(a));
        blocks.replace(a, "");
        System.out.println(a + " " + b);
        game.increaseAction();
        gw.load();
        if (game.isVictory()){
            game.getTimer().stop();
            JOptionPane.showMessageDialog(null, "Nice ... you won with " + game.getActions() + " moves in " + LocalTime.MIN.plusSeconds(t).toString() + "!");
        }
        if(t==0 || !game.getTimer().isRunning()){
            game.setTimer(new Timer(1000, (x)-> {
                    System.out.println("Time");
                    t++;
                    gw.load();
                }
            ));
            game.getTimer().start();
        }
    }


    public static void main(String[] args){

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GameWindow();
            }
        });
    }
}
