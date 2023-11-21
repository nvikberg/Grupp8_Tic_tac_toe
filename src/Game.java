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
    boolean spelare;
    private ArrayList<Player> players = new ArrayList<Player>();
    private ArrayList<String> winConditions = new ArrayList<>();

    Game(){
        frame = new JFrame();
        frame.setSize(500,500);
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setTitle("Tic Tac Toe");

        knappar = new JButton[9];

        startSlump();

        layoutCenter();
        addPlayer();
        layoutTop();

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
    void layoutTop(){
        String name;
        if(spelare)
            name = players.getFirst().getName();

        else
            name = players.getLast().getName();

        JPanel top = new JPanel();
        top.setLayout(new FlowLayout());
        JLabel turn = new JLabel(name);
        top.add(turn);
        frame.add(top,BorderLayout.NORTH);
        top.revalidate();
        top.repaint();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        for(int i = 0; i < 9; i++){
            if(e.getSource()==knappar[i]){                  //Kollar källan mot Arrayn av knappar.
                if(spelare){
                    if(knappar[i].getText().isEmpty()){

                                                            //Fonts Etc
                                                            //Designa knappen efter att spelaren har tryckt på den.
                        knappar[i].setText("X");
                        spelare=false;
                        layoutTop();                        //Bytar mellan spelarna genom att sätta på och av boolen spelare.
                        check();                            //Kollar efter varje knapp klick ifall det finns en vinnande kombination.
                    }
                }
                else{
                    if(knappar[i].getText().isEmpty()){

                                                            //Fonts etc
                                                            //Designa knappen efter att spelare har tryckt på den.
                        knappar[i].setText("O");
                        spelare=true;
                        layoutTop();
                        check();
                    }
                }
            }
        }
    }
    //Karl
    void check(){
        int[][] vinstAlternativ = {{0,1,2},{3,4,5},{6,7,8},  //Vågrät vinst.
                {0,3,6},{1,4,7},{2,5,8},                    //Lodrät vinst.
                {0,4,8},{2,4,6}                             //Vinst på diagonalen.
        };
        for(int[] vinst: vinstAlternativ){                  //Går igenom arrayen övan med alla korrekta möjligheter för vinst med hjälp av en for each loop.
            if(knappar[vinst[0]].getText().equals("X") &&
                    knappar[vinst[1]].getText().equals("X") &&
                    knappar[vinst[2]].getText().equals("X")){
                restartPanel(players.getFirst().getName()+" är vinnaren!!"); //Metod eller text för vad som händer fall den här ikonen vinner även behöver equalsen fyllas i så vi kan jämföra.
            }
            else if(knappar[vinst[0]].getText().equals("O") &&
                    knappar[vinst[1]].getText().equals("O") &&
                    knappar[vinst[2]].getText().equals("O")){
                restartPanel(players.getLast().getName()+" är vinnaren!!"); //Metod eller text för vad som händer fall den här ikonen vinner även behöver equalsen fyllas i så vi kan jämföra.
            }
        }
    }
    //Karl
    void startSlump(){
        spelare= random.nextInt(2) == 0;                    //Slumpar 0-1 och avgör om boolen ska bli false eller true (Splare1 / Spelare2)
    }
    //Karl
    void restartPanel(String vinnare){
        int val = JOptionPane.showOptionDialog(null,"Vill du fortsätta spela ?",vinnare,JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE,null,null,0);
        if(JOptionPane.YES_OPTION==val) {                   //Tar int värdet från JOptionPane.YES_NO_OPTION som är 1 eller 0 och spara det i val.
            for (int i = 0; i < 9; i++) {
                knappar[i].setText("");                     //Metod som 0 sätter strängarna på knapparna så att man återigen kan klicka på dom.
            }
        }
        if(JOptionPane.NO_OPTION==val)
            System.exit(0);                                 //Stänger programmet.
    }
   public void addPlayer(){                                     //Fråga efter namn på spelarna. TODO Designa fönstert
       for(int i= 1;i<3;i++){
           String message = "Player "+ (i) + " name";            // Create an object of class player. We will need 2 players in a Multiplayer game.
           String name = JOptionPane.showInputDialog(message);   // They will have their own symbol based on a randomized funtion to assign it.
           players.add(new Player(name));
       }

    }
}
