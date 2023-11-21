import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

class Game implements ActionListener {

    JFrame frame;
    Random random = new Random();
    JPanel panelKnappar;
    JButton[] knappar;
    private JButton buttons;
    boolean spelare;


    private ArrayList<Player> players = new ArrayList<Player>();

    Game(){
        frame = new JFrame();
        frame.setSize(500,500);
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setTitle("Tic Tac Toe");

        knappar = new JButton[9];

        startSlump();

        //Metod för toppanel.

        //layoutCenter();

        //To open a more working version uncomment row 33 and comment the next 3 rows.
        layoutCenter2();
        addPlayers();
        assignTurnOrder();

        frame.setVisible(true);
    }
    //Karl
    void layoutCenter(){
        panelKnappar = new JPanel();
        panelKnappar.setLayout(new GridLayout(3,3,1,1));
        frame.add(panelKnappar,BorderLayout.CENTER);
        for(int i = 0; i < 9; i++){
            knappar[i] = new JButton();             //Lägger ut 9 knappar på en panel som är placerade i center.
            knappar[i].addActionListener(this);     //Lägger till en AL och tar bort focusen så att dom ser lite snyggare ut.
            knappar[i].setFocusable(false);
            panelKnappar.add(knappar[i]);
        }
    }

    void layoutCenter2(){
        panelKnappar = new JPanel();
        panelKnappar.setLayout(new GridLayout(3,3,1,1));
        frame.add(panelKnappar,BorderLayout.CENTER);
        for(int i = 1; i < 4; i++){
            for(int j = 1; j < 4; j++){
                buttons = new JButton();
                buttons.setActionCommand(""+i+j);
                buttons.addActionListener(this);
                panelKnappar.add(buttons);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String playedButton = buttons.getActionCommand();
        //System.out.println(e.getSource()==buttons[playedButton]);
        for(int i = 0; i < 9; i++){
            if(e.getSource()==knappar[i]){      //Kollar källan mot Arrayn av knappar.
                if(spelare){
                    if(knappar[i].getText().isEmpty()){
                        //Fonts Etc
                        //Designa knappen efter att spelaren har tryckt på den.
                        knappar[i].setText("O");
                        spelare=false;              //Bytar mellan spelarna genom att sätta på och av boolen spelare.
                        check();                    //Kollar efter varje knapp klick ifall det finns en vinnande kombination.
                    }
                }
                else{
                    if(knappar[i].getText().isEmpty()){
                        //Fonts etc
                        //Designa knappen efter att spelare har tryckt på den.
                        knappar[i].setText("X");
                        spelare=true;
                        check();
                    }
                }
            }
        }
    }
    //Karl
    void check(){
        int[][] vinstAlternativ = {{0,1,2},{3,4,5},{6,7,8},  //Vågrät vinst.
                {0,3,6},{1,4,7},{2,5,8}, //Lodrät vinst.
                {0,4,8},{2,4,6} //Vinst på diagonalen.
        };
        for(int[] vinst: vinstAlternativ){      //Går igenom arrayen övan med alla korrekta möjligheter för vinst med hjälp av en for each loop.
            if(knappar[vinst[0]].getText().equals("X") &&
                    knappar[vinst[1]].getText().equals("X") &&
                    knappar[vinst[2]].getText().equals("X")){
                String X ="X är vinnaren!";
                restartPanel(X);
                   //Metod eller text för vad som händer fall den här ikonen vinner även behöver equalsen fyllas i så vi kan jämföra.
            }
            else if(knappar[vinst[0]].getText().equals("O") &&
                    knappar[vinst[1]].getText().equals("O") &&
                    knappar[vinst[2]].getText().equals("O")){
                String O="O är vinnaren!";
                restartPanel(O);
                    //Metod eller text för vad som händer fall den här ikonen vinner även behöver equalsen fyllas i så vi kan jämföra.
            }
        }
    }
    //Karl
    void startSlump(){
        spelare= random.nextInt(2) == 0; //Slumpar 0-1 och avgör om boolen ska bli false eller true (Splare1 / Spelare2)
    }
    //Karl
    void restartPanel(String vinnare){
        int val = JOptionPane.showOptionDialog(null,"Vill du fortsätta spela ?",vinnare,JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE,null,null,0);
        if(JOptionPane.YES_OPTION==val) {           //Tar int värdet från JOptionPane.YES_NO_OPTION som är 1 eller 0 och spara det i val.
            for (int i = 0; i < 9; i++) {
                knappar[i].setText(""); //Metod som 0 sätter strängarna på knapparna så att man återigen kan klicka på dom.
            }
        }
        if(JOptionPane.NO_OPTION==val)
            System.exit(0);            //Stänger programmet.
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
            players.get(1).setPlayerOrder(2);
        } else {
            players.get(0).setPlayerOrder(2);
            players.get(1).setPlayerOrder(1);
        }
    }

}
