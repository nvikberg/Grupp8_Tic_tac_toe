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


        //Metod för toppanel.
        layoutCenter();

        frame.setVisible(true);
    }
    void layoutCenter(){
        panelKnappar = new JPanel();
        panelKnappar.setLayout(new GridLayout(3,3,1,1));
        frame.add(panelKnappar,BorderLayout.CENTER);
        for(int i = 0; i < 9; i++){
            knappar[i] = new JButton();
            knappar[i].addActionListener(this);
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
                        vinst();
                    }
                }
                else{
                    if(knappar[i].getText().isEmpty()){
                        //Designa knappen efter att spelare har tryckt på den.

                        spelare=true;
                        vinst();
                    }
                }
            }
        }
    }
    void vinst(){
        if(knappar[0].getText())
    }
    void startSlump(){
        if(random.nextInt(2)==0){
            spelare=true;
        }
        else {
            spelare=false;
        }
    }
    void restart(){
        for(int i =0;i<9;i++){
            knappar[i].setText("");
        }
        try{
            Thread.sleep(4000);
        }catch (InterruptedException e){
            System.out.println(e);
        }
    }

}
