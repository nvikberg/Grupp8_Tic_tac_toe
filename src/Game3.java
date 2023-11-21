import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class Game3 extends JFrame implements ActionListener {


    private JButton[][] buttons; //two dimensional array for row and column game buttons

    private boolean playerXturn;
    private int turnCount;
    private int playerXScore;
    private int playerOScore;
    private JLabel scoreLabel;
    Random random = new Random();
    private JButton startButton;
    private JButton restartButton;
    private JTextArea resultTextArea;
    private JScrollPane scrollPane;

    public Game3() {
        setTitle("Tic Tac Toe");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 600);
        setLocationRelativeTo(null);

        //button panel for start game
        JPanel buttonPanel = new JPanel();
        startButton = new JButton("Start Game");
        startButton.addActionListener(this);
        buttonPanel.add(startButton);

        JPanel gamePanel = new JPanel(new GridLayout(3, 3));
        buttons = new JButton[3][3];
        playerXturn = random.nextInt() < 0.5; // random boolean value where there's a 50% chance of it being true
        turnCount = 0;
        playerXScore = 0;
        playerOScore = 0;

        initializeButtons(gamePanel); //method call to create game buttons in game panel


        scoreLabel = new JLabel("Player X: " + playerXScore + "  Player O: " + playerOScore);
        scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);

        restartButton = new JButton("Play Again");
        restartButton.addActionListener(this);

        resultTextArea = new JTextArea(5,20);
        resultTextArea.setEditable(false);
        scrollPane = new JScrollPane(resultTextArea);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(scoreLabel, BorderLayout.NORTH);
        bottomPanel.add(restartButton, BorderLayout.CENTER);
        bottomPanel.add(scrollPane, BorderLayout.SOUTH);

        add(buttonPanel, BorderLayout.NORTH);
        add(gamePanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
        setVisible(true);


    }

    //initializeButtons in game panel
    private void initializeButtons(JPanel gamePanel) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j] = new JButton();
                buttons[i][j].setFont(new Font(Font.SANS_SERIF, Font.BOLD, 40));
                buttons[i][j].addActionListener(this);
                gamePanel.add(buttons[i][j]);
            }
        }

    }

    //reset game (create a new blank game panel)
    private void resetGame() {
        turnCount = 0;
        playerXturn = random.nextInt() < 0.5;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
            }
        }
    }
    // en method for check winner

    private boolean checkWin(String symbol) {
        for (int i = 0; i < 3; i++) {
            if (buttons[i][0].getText().equals(symbol) && buttons[i][1].getText().equals(symbol) && buttons[i][2].getText().equals(symbol)) {
                return true;
            }
            if (buttons[0][i].getText().equals(symbol) && buttons[1][i].getText().equals(symbol) && buttons[2][i].getText().equals(symbol)) {
                return true;
            }

        }
        if (buttons[0][0].getText().equals(symbol) && buttons[1][1].getText().equals(symbol) && buttons[2][2].getText().equals(symbol)) {
            return true;
        }

        if (buttons[0][2].getText().equals(symbol) && buttons[1][1].getText().equals(symbol) && buttons[2][0].getText().equals(symbol)) {
            return true;
        }
        return false;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        JButton clickedButton = (JButton) e.getSource();


        if (clickedButton == startButton) {
            resetGame();
            return;
        }
        if (clickedButton == restartButton) {
            playerXScore = 0;
            playerOScore = 0;
            scoreLabel.setText("Player X: " + playerXScore + " Player O: " + playerOScore);
            resultTextArea.setText("");
            resetGame();
            return;
        }

        if (playerXturn) {
            clickedButton.setText("X");
            if (checkWin("X")) {
                JOptionPane.showMessageDialog(this, "Player X wins!");
                playerXScore++;
                scoreLabel.setText("Player X: " + playerXScore + " Player O: " + playerOScore);
                resultTextArea.append("Player X wins!\n");
                resetGame();
            } else {
                turnCount++;
                // if every player gets same points then it will be draw
                if (turnCount == 9) {
                    JOptionPane.showMessageDialog(this, "It's a draw!");
                    resultTextArea.append("It's a draw!\n");
                    resetGame();
                } else {
                    playerXturn = false;
                }
            }

        } else {
            clickedButton.setText("O");
            if (checkWin("O")) {
                JOptionPane.showMessageDialog(this, "Player O wins!");
                playerOScore++;
                scoreLabel.setText("Player X: " + playerXScore + " Player O: " + playerOScore);
                resultTextArea.append("Player O wins!\n");
                resetGame();
            } else {
                turnCount++;
                if (turnCount == 9) {
                    JOptionPane.showMessageDialog(this, "It's a draw!");
                    resultTextArea.append("It's a draw!\n");
                    resetGame();
                } else {
                    playerXturn =true;
                }
            }
        }
        if (playerXScore>playerOScore){
            resultTextArea.append("Player X has the maximum points!\n");

        }else if (playerXScore<playerOScore){
            resultTextArea.append("Player O has the maximum points!\n");
        } else{
            resultTextArea.append ("Both players have the same points!\n");
        }


    }
}
