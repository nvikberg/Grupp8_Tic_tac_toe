import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class GUI implements ActionListener {

    JFrame frame;
    JPanel panel;

    GUI(){
        frame = new JFrame();
        frame.setSize(500,500);
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());


        //Metod för toppanel.
        layoutCenter();

        frame.setVisible(true);
    }
    void layoutCenter(){
        panel = new JPanel();
        panel.setLayout(new GridLayout(3,3,1,1));
        frame.add(panel,BorderLayout.CENTER);
        for(int i = 0; i < 9; i++){
            JButton knapp = new JButton();
            panel.add(knapp);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
