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

                        spelare=false;
                        check();
                    }
                }
                else{
                    if(knappar[i].getText().isEmpty()){
                        //Designa knappen efter att spelare har tryckt på den.

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
        for(int[] vinst: vinstAlternativ){
            if(knappar[vinst[0]].getText().equals() &&
                    knappar[vinst[1]].getText().equals() &&
                    knappar[vinst[2]].getText().equals(){
                   //Metod eller text för vad som händer fall den här ikonen vinner.
            }
            if(knappar[vinst[0]].getText().equals() &&
                    knappar[vinst[1]].getText().equals() &&
                    knappar[vinst[2]].getText().equals()){
                    //Metod eller text för vad som händer fall den här ikonen vinner.
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
    void restart(){
        for(int i =0;i<9;i++){
            knappar[i].setText(""); //Metod som 0 sätter strängarna på knapparna så att man återigen kan klicka på dom.
        }
        try{
            Thread.sleep(4000);         //Sleepar tråden i 4000ms för att man ska hinna se att man har vunnit innan det återställs.
        }catch (InterruptedException e){
            System.out.println(e);        //Try/Catch blocket är bara med för att sleep metoden ska funka.
        }
    }

}
