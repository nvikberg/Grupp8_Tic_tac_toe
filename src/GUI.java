import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

class GUI implements ActionListener {

    JFrame frame;
    Random random = new Random();
    JPanel panelKnappar;
    JButton[] knappar;
    boolean spelare;

    GUI(){
        frame = new JFrame();
        frame.setSize(500,500);
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setTitle("Tic Tac Toe");

        knappar = new JButton[9];

        startSlump();

        //Metod för toppanel.

        layoutCenter();

        frame.setVisible(true);
    }
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

    @Override
    public void actionPerformed(ActionEvent e) {

        for(int i = 0; i < 9; i++){
            if(e.getSource()==knappar[i]){
                if(spelare){
                    if(knappar[i].getText().isEmpty()){
                        //Designa knappen efter att spelaren har tryckt på den.
                        knappar[i].setText("O");
                        spelare=false;
                        check();
                    }
                }
                else{
                    if(knappar[i].getText().isEmpty()){
                        //Designa knappen efter att spelare har tryckt på den.
                        knappar[i].setText("X");
                        spelare=true;
                        check();
                    }
                }
            }
        }
    }
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
            if(knappar[vinst[0]].getText().equals("O") &&
                    knappar[vinst[1]].getText().equals("O") &&
                    knappar[vinst[2]].getText().equals("O")){
                String O="O är vinnaren!";
                restartPanel(O);
                    //Metod eller text för vad som händer fall den här ikonen vinner även behöver equalsen fyllas i så vi kan jämföra.
            }
        }
    }
    void startSlump(){
        if(random.nextInt(2)==0){   //Slumpar 0-1 och avgör om boolen ska bli false eller true (Splare1 / Spelare2)
            spelare=true;
        }
        else {
            spelare=false;
        }
    }
    void restartPanel(String vinnare){
         JOptionPane.showOptionDialog(null,"Vill du fortsätta spela ?",vinnare,JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE,null,null,0);
        if(JOptionPane.YES_OPTION==0){
            restart();
        }
        else
            System.exit(0);
    }
    void restart(){
        for(int i =0;i<9;i++){
            knappar[i].setText(""); //Metod som 0 sätter strängarna på knapparna så att man återigen kan klicka på dom.
        }
    }
}
