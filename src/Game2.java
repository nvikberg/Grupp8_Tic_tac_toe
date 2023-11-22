import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

class Game2 implements ActionListener {

    JFrame frame;
    Random random = new Random();
    JPanel panelKnappar;
    private ArrayList<JButton> buttons = new ArrayList<JButton>();
    private JButton button;
    private int buttonsClicked  = 0;
    private int gamesPlayed = 0;
    private ArrayList<Player> players = new ArrayList<Player>();
    private static HashMap<String, ArrayList<String>> winConditions = new HashMap<>();


    Game2(){
        frame = new JFrame();
        frame.setBounds(580,200,500,500); //set bounds so game will show up in the middle of the screen/nv
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setTitle("Tic Tac Toe");

        createWinConditions();
        layoutCenter2();
        addPlayers();
        assignTurnOrder();

        frame.setVisible(true);
    }

    void layoutCenter2(){
        panelKnappar = new JPanel();
        panelKnappar.setLayout(new GridLayout(3,3,1,1));
        frame.add(panelKnappar,BorderLayout.CENTER);
        for(int i = 1; i < 4; i++){
            for(int j = 1; j < 4; j++){
                button = new JButton();
                button.setName(""+i+j);
                button.setActionCommand(""+i+j);
                button.addActionListener(this);
                buttons.add(button);
            }
        }
        for(JButton button : buttons){
            panelKnappar.add(button);
        }
    }

    @Override

    public void actionPerformed(ActionEvent e) {
        String playedButton = e.getActionCommand();
        String playerSign = currentPlayerChoice(playedButton);
        try {
            addClickSound();
        } catch (UnsupportedAudioFileException ex) {
            throw new RuntimeException(ex);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        } catch (LineUnavailableException ex) {
            throw new RuntimeException(ex);
        }
        for(JButton button : buttons){
            if(button.getActionCommand().equals(playedButton)) {
                buttonsClicked++;
                button.setText(playerSign);
                button.setEnabled(false);
                if(buttonsClicked == 9 || doesCurrentPlayerWin()){
                    //TODO show dialog ask if the player wants the game to be restarted and then call restartGame();
                    gamesPlayed++;
                    int answer = JOptionPane.showConfirmDialog(null,
                            "Do you want to play again?",
                            "Continue playing",
                            JOptionPane.YES_NO_OPTION);
                    if(answer == 0) {
                        restartGame();
                    } else {
                        System.exit(0);
                    }
                }
            }
        }
    }


    //Show the window to ask for the player's names. TODO Can be designed
    public void addPlayer(int numberOfPlayer){
        // Create an object of class player. We will need 2 players in a Multiplayer game.
        // They will have their own symbol based on a randomized funtion to assign it.
        String message = "Player "+ (numberOfPlayer + 1) + " name";
        String name = JOptionPane.showInputDialog(message);
        players.add(new Player(name));
    }

    // hardcoded amount of players.
    public void addPlayers(){
        for(int i = 0; i < 2; i++){
            addPlayer(i);
        }
    }

    // set a player order. Works for hardcoded 2 players.
    public void assignTurnOrder(){
        int decideFirst = random.nextInt(1, 101);
        if(decideFirst % 2 == 0){
            players.get(0).setPlayerOrder(1);
            players.get(0).setCurrent(true);
            JOptionPane.showMessageDialog(null,
                    ""+players.get(0).getName() + " starts the game ",
                    "First player",
                    JOptionPane.PLAIN_MESSAGE);
            players.get(1).setPlayerOrder(2);
        } else {
            players.get(0).setPlayerOrder(2);
            players.get(1).setPlayerOrder(1);
            players.get(1).setCurrent(true);
            JOptionPane.showMessageDialog(null,
                    ""+players.get(1).getName() + " starts the game ",
                    "First player",
                    JOptionPane.PLAIN_MESSAGE);
        }

    }

    public String currentPlayerChoice(String playedButton){
        if(players.get(0).isCurrent()){
            players.get(0).makeChoice(playedButton);
            // if the player doesn't win make player two the current player
            if(!doesCurrentPlayerWin()){
                players.get(0).setCurrent(false);
                players.get(1).setCurrent(true);
            } else {
                players.get(0).setWonRounds();
                JOptionPane.showMessageDialog(null,
                        ""+players.get(0).getName() + " won. You have won "+ players.get(0).getWonRounds() + " rounds in total!",
                        "Titel",
                        JOptionPane.PLAIN_MESSAGE);
                try {
                    addWinSound();
                } catch (UnsupportedAudioFileException ex) {
                    throw new RuntimeException(ex);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (LineUnavailableException ex) {
                    throw new RuntimeException(ex);
                }
            }
            return players.get(0).playerSign;
        } else {
            players.get(1).makeChoice(playedButton);
            // if the player doesn't win make player one the current player
            if(!doesCurrentPlayerWin()){
                players.get(1).setCurrent(false);
                players.get(0).setCurrent(true);
            } else {
                players.get(1).setWonRounds();
                JOptionPane.showMessageDialog(null,
                        ""+players.get(1).getName() + " won. You have won "+ players.get(1).getWonRounds() + " rounds in total!",
                        "Titel",
                        JOptionPane.PLAIN_MESSAGE);
                try {
                    addWinSound();
                } catch (UnsupportedAudioFileException ex) {
                    throw new RuntimeException(ex);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (LineUnavailableException ex) {
                    throw new RuntimeException(ex);
                }
            }
            return players.get(1).playerSign;
        }
    }

    public boolean doesCurrentPlayerWin(){
        boolean isWinner = false;
        if(players.get(0).isCurrent()){
            isWinner = players.get(0).checkForWin();
        } else {
            isWinner = players.get(1).checkForWin();
        }
        return isWinner;
    }

    public void restartGame(){
        buttonsClicked=0;
        gamesPlayed++;
        for(JButton button : buttons){
            button.setText("");
            button.setEnabled(true);
        }
        assignTurnOrder();
    }

    public void createWinConditions(){
        ArrayList<String> row1 = new ArrayList<>();
        row1.add("11");
        row1.add("12");
        row1.add("13");
        ArrayList<String> row2 = new ArrayList<>();
        row2.add("21");
        row2.add("22");
        row2.add("23");
        ArrayList<String> row3 = new ArrayList<>();
        row3.add("31");
        row3.add("32");
        row3.add("33");
        ArrayList<String> col1 = new ArrayList<>();
        col1.add("11");
        col1.add("21");
        col1.add("31");
        ArrayList<String> col2 = new ArrayList<>();
        col2.add("12");
        col2.add("22");
        col2.add("32");
        ArrayList<String> col3 = new ArrayList<>();
        col3.add("13");
        col3.add("23");
        col3.add("33");
        ArrayList<String> diag1 = new ArrayList<>();
        diag1.add("11");
        diag1.add("22");
        diag1.add("33");
        ArrayList<String> diag2 = new ArrayList<>();
        diag2.add("13");
        diag2.add("22");
        diag2.add("31");
        winConditions.put("row 1", row1);
        winConditions.put("row 2", row2);
        winConditions.put("row 3", row3);
        winConditions.put("col 1", col1);
        winConditions.put("col 2", col2);
        winConditions.put("col 3", col3);
        winConditions.put("diag 1", diag1);
        winConditions.put("diag 2", diag2);
    }
    public static HashMap<String, ArrayList<String>> getWinConditions() {
        return winConditions;
    }

    public void addClickSound() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        File file = new File("Pen Clicking (online-audio-converter.com).wav");
        AudioInputStream clickAudio = AudioSystem.getAudioInputStream(file);
        Clip clip = AudioSystem.getClip();
        clip.open(clickAudio);
        clip.start();
    }
    public void addWinSound() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        File file = new File("Slapping Three Faces.wav");
        AudioInputStream winSound = AudioSystem.getAudioInputStream(file);
        Clip clip = AudioSystem.getClip();
        clip.open(winSound);
        clip.start();
    }
}
