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

class Game implements ActionListener {

    JFrame frame;
    Random random = new Random();
    JPanel buttonsPanel;
    JButton[] buttons;
    boolean player;
    final ArrayList<String> players = new ArrayList<>();
    HashMap<String,Integer> scoreBoard = new HashMap<>();
    Clip sound;
    Game(){
        frame = new JFrame();
        frame.setSize(500,500);
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setTitle("Tic Tac Toe");
        buttons = new JButton[9];
        startRandom();
        layoutCenter();
        addPlayer();
        scoreBoard.put(players.getFirst(),0);
        scoreBoard.put(players.getLast(),0);
        layoutTop();
        layoutBottom();
        frame.setVisible(true);
    }
    void layoutCenter(){
        buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(3,3,1,1));
        frame.add(buttonsPanel,BorderLayout.CENTER);
        for(int i = 0; i < 9; i++){
            buttons[i] = new JButton();             //Lägger ut 9 knappar på en panel som är placerade i center.
            buttons[i].addActionListener(this);     //Lägger till en AL och tar bort focusen så att dom ser lite snyggare ut.
            buttons[i].setFocusable(false);
            buttonsPanel.add(buttons[i]);
        }
    }
    void layoutBottom(){
        JPanel bottom = new JPanel();
        bottom.setLayout(new FlowLayout());                 //Sätter en panel i botten av framen.
        JButton reset = new JButton("Restart!");            //Skapar en knapp för att kunna 0 ställa spelplanen.
        reset.setFocusable(false);                          //Tarbort focusen från knapparna.
        //Skapar en anonym AL som ska funka specifikt för den knappen.
        reset.addActionListener(e -> {                   //Använder ett lambda uttryck för att minska koden.
            for(int i = 0; i < buttons.length; i++){                 //0ar alla knappar.
                buttons[i].setText("");
                startRandom();
                layoutTop();

            }
        });
        bottom.add(reset);
        frame.add(bottom,BorderLayout.SOUTH);
    }
    void layoutTop(){
        String name;
        if(player)
            name = players.getFirst();

        else
            name = players.getLast();               //Bestämmer vem som ska börja beroende på om boolen bli falsk eller true.

        JPanel top = new JPanel();
        top.setLayout(new FlowLayout());            //Sätter flowlayout mest för att det ser snyggare ut om det är centrerat.
        JLabel turn = new JLabel(name);             //Sätter JLabelns namn till rätt persons tur.
        top.add(turn);
        frame.add(top,BorderLayout.NORTH);          //Lägger ny panel i NORTH på framen.
        top.revalidate();                           //Revalidatar och repaintar panelen varjegång metoden kallas så att texten updateras.
        top.repaint();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        for(int i = 0; i < buttons.length; i++){
            if(e.getSource()==buttons[i]){                  //Kollar källan mot Arrayn av knappar.
                if(player){
                    if(buttons[i].getText().isEmpty()){

                                                            //Fonts Etc
                                                            //Designa knappen efter att spelaren har tryckt på den.
                        buttons[i].setText("X");
                        player=false;
                        layoutTop();                        //Bytar mellan spelarna genom att sätta på och av boolen spelare.
                        check();                            //Kollar efter varje knapp klick ifall det finns en vinnande kombination.
                    }
                }
                else{
                    if(buttons[i].getText().isEmpty()){

                                                            //Fonts etc
                                                            //Designa knappen efter att spelare har tryckt på den.
                        buttons[i].setText("O");
                        player=true;
                        layoutTop();
                        check();
                    }
                }
            }
        }
    }
    void check(){
        int[][] winAlternativ = {{0,1,2},{3,4,5},{6,7,8},  //Vågrät vinst.
                {0,3,6},{1,4,7},{2,5,8},                    //Lodrät vinst.
                {0,4,8},{2,4,6}                             //Vinst på diagonalen.
        };
        for(int[] win: winAlternativ){                       //Går igenom arrayen övan med alla korrekta möjligheter för vinst med hjälp av en for each loop.
            if(buttons[win[0]].getText().equals("X") &&        //Kollar dom vågräta alternativen.
                    buttons[win[1]].getText().equals("X") &&   //Kollar dom lodräta alternativen.
                    buttons[win[2]].getText().equals("X")){    //Kollar dom diagonala alternativen.
                rstPanel(players.getFirst());                 //Metod eller text för vad som händer fall den här ikonen vinner även behöver equalsen fyllas i så vi kan jämföra.
            }
            if(buttons[win[0]].getText().equals("O") &&
                    buttons[win[1]].getText().equals("O") &&
                    buttons[win[2]].getText().equals("O")){

                rstPanel(players.getLast()); //Metod eller text för vad som händer fall den här ikonen vinner även behöver equalsen fyllas i så vi kan jämföra.
            }
        }
    }
    void startRandom(){
        player= random.nextInt(2) == 0;                    //Slumpar 0-1 och avgör om boolen ska bli false eller true (Splare1 / Spelare2)
    }
    void rstPanel(String vinnare){
        int choice = JOptionPane.showOptionDialog(null,"Vill du fortsätta spela ?",vinnare+" är vinnaren!!",JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE,null,null,0);
        int score = scoreBoard.get(vinnare)+1;
        scoreBoard.put(vinnare,score);
        if(JOptionPane.YES_OPTION==choice) {                   //Tar int värdet från JOptionPane.YES_NO_OPTION som är 1 eller 0 och spara det i val.
            for (int i = 0; i < buttons.length; i++) {
                buttons[i].setText("");                     //Metod som 0 sätter strängarna på knapparna så att man återigen kan klicka på dom.
            }
        }
        if(JOptionPane.NO_OPTION==choice){
            int player1Score = scoreBoard.get(players.getFirst());
            int player2Score = scoreBoard.get(players.getLast());
            String message = players.getFirst()+":"+player1Score+" poäng!\n"+players.getLast()+":"+player2Score+" poäng!";
            JOptionPane.showMessageDialog(null,message);
            System.exit(0);                                     //Stänger programmet.
        }
    }
   public void addPlayer(){                                     //Fråga efter namn på spelarna.
       for(int i= 1;i<3;i++){
           String message = "Player "+ (i) + " name";            // Create an object of class player. We will need 2 players in a Multiplayer game.
           String name = JOptionPane.showInputDialog(message);   // They will have their own symbol based on a randomized funtion to assign it.
           players.add(name);
       }
   }
    void playSound() throws LineUnavailableException, UnsupportedAudioFileException, IOException {
        /*Throw hanterar exceptions innom metoden. Kan ses som en ersättning för ett try/catch block.
        UnsupportedAudioFileException i detta fallet hanterar den ljudfiler som inte stöds, IOExceptions hanterar läsning/öppning och stängning av filen.
        LineUnavailableException hanterar eventuella fel på linjen/tråden som det körs på. Alla dom här exceptionsen behövs och det går inte att köra programmet utan dom.
         */
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File("VictorySong.wav"));
        sound = AudioSystem.getClip();                       //Ger sound möjligheten att hantera olika ljud kommandon.
        sound.open(audioStream);
        sound.start();
    }
}
/*
TODO
Designa:
Fönster,Knappar,JOptionPanes,
Lägga till ljud för VG alternativt nytt GM.
*/