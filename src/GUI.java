import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class GUI implements ActionListener {

    JFrame frame;
    JPanel panel;
    JButton[] knappar;

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
        panel = new JPanel();
        panel.setLayout(new GridLayout(3,3,1,1));
        frame.add(panel,BorderLayout.CENTER);
        for(int i = 0; i < 9; i++){
            knappar[i] = new JButton();
            knappar[i].addActionListener(this);
            panel.add(knappar[i]);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton source = (JButton) e.getSource();

        for(int i = 0; i < 9; i++){
            if(source==knappar[i]){
                knappar[i].setText("XXXxxxXXXXXXX");
                panel.revalidate();
                panel.repaint();
            }
        }
    }
}
