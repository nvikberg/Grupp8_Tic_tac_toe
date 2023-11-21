import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

class Game2 implements ActionListener {

    JFrame frame;
    Random random = new Random();
    JPanel panelKnappar;
    private ArrayList<JButton> buttons = new ArrayList<JButton>();
    private JButton button;


    private ArrayList<Player> players = new ArrayList<Player>();
    private HashMap<String, String[]> winConditions = new HashMap<>();


    Game2(){
        frame = new JFrame();
        frame.setSize(500,500);
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setTitle("Tic Tac Toe");


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

        //String playedButton = buttons.getActionCommand();

        String playedButton = e.getActionCommand();
        String playerSign = currentPlayerChoice(playedButton);
        for(JButton button : buttons){
            if(button.getActionCommand().equals(playedButton)) {
                button.setText(playerSign);
                button.setEnabled(false);
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
            players.get(1).setPlayerOrder(2);
        } else {
            players.get(0).setPlayerOrder(2);
            players.get(1).setPlayerOrder(1);
            players.get(1).setCurrent(true);
        }
    }

    public String currentPlayerChoice(String playedButton){
        if(players.get(0).isCurrent()){
            players.get(0).makeChoice(playedButton);
            players.get(0).setCurrent(false);
            players.get(1).setCurrent(true);
            return players.get(0).playerSign;
        } else {
            players.get(1).makeChoice(playedButton);
            players.get(1).setCurrent(false);
            players.get(0).setCurrent(true);
            return players.get(1).playerSign;
        }
    }

    public void createWinConditions(){
        winConditions.put("row 1", new String[]{"11", "12", "13"});
        winConditions.put("row 2", new String[]{"21", "22", "23"});
        winConditions.put("row 3", new String[]{"31", "32", "33"});
        winConditions.put("col 1", new String[]{"11", "21", "31"});
        winConditions.put("col 2", new String[]{"12", "22", "32"});
        winConditions.put("col 3", new String[]{"13", "23", "33"});
        winConditions.put("diag 1", new String[]{"11", "22", "33"});
        winConditions.put("diag 2", new String[]{"13", "22", "31"});
    }
}
