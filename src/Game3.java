import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class Game3 extends JFrame implements ActionListener {


    private JButton[][] buttons; //two dimensional array for row and column game buttons

    private boolean playerXturn;
    private int turnCount;

    private JTextField playerXField;
    private JTextField playerOField;
    private int playerXScore;
    private int playerOScore;
    private JLabel scoreLabel;
    Random random = new Random();
    private JButton startButton;
    private JButton restartButton;
    private JTextArea resultTextArea;
    private JScrollPane scrollPane;

    Clip clip;

    public Game3() {
        setTitle("Tic Tac Toe");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 600);
        setLocationRelativeTo(null);

        JPanel playerPanel = new JPanel(new GridLayout(3, 2));
        JLabel playerXLabel = new JLabel("Player X Name:");
        playerXField = new JTextField();
        JLabel playerOLabel = new JLabel("Player O Name:");
        playerOField = new JTextField();
        playerPanel.add(playerXLabel);
        playerPanel.add(playerXField);
        playerPanel.add(playerOLabel);
        playerPanel.add(playerOField);

        //player panel for start game

        startButton = new JButton("Start Game");
        startButton.addActionListener(this);
        playerPanel.add(startButton);

        JPanel gamePanel = new JPanel(new GridLayout(3, 3));
        buttons = new JButton[3][3];
        playerXturn = random.nextInt() < 0.5; // random boolean value where there's a 50% chance of it being true
        turnCount = 0;
        playerXScore = 0;
        playerOScore = 0;

        initializeButtons(gamePanel); //method call to create game buttons in game panel


        scoreLabel = new JLabel(playerXField.getText()+" : " + playerXScore + " " +playerOField.getText()+": "+ playerOScore);
        scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);

        restartButton = new JButton("Start Again");
        restartButton.addActionListener(this);

        resultTextArea = new JTextArea(5,20);
        resultTextArea.setEditable(false);
        scrollPane = new JScrollPane(resultTextArea);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(scoreLabel, BorderLayout.NORTH);
        bottomPanel.add(restartButton, BorderLayout.CENTER);
        bottomPanel.add(scrollPane, BorderLayout.SOUTH);

        add(playerPanel, BorderLayout.NORTH);
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
                buttons[i][j].setEnabled(false);

            }
        }

    }

    //reset game (create a new blank game panel)
    private void resetGame() {
        turnCount = 0;
        playerXturn = random.nextInt() < 0.5;// random boolean value where there's a 50% chance of it being true
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
                buttons[i][j].setEnabled(true);

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
            startButton.setEnabled(false);
            resetGame();
            playerXScore = 0;
            playerOScore = 0;
            scoreLabel.setText(playerXField.getText()+": " + playerXScore + " " +playerOField.getText()+": "+ playerOScore);
            resultTextArea.setText("");
            restartButton.setEnabled(true);
            return;
        }
        if (clickedButton == restartButton) {
            playerXScore = 0;
            playerOScore = 0;
            scoreLabel.setText(playerXField.getText()+": " + playerXScore +" "+ playerOField.getText()+": "+ playerOScore);

            resultTextArea.setText("");

            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    buttons[i][j].setText("");
                    buttons[i][j].setEnabled(false);
                }
            }
            restartButton.setEnabled(false);
            startButton.setEnabled(true);

            return;
        }
        if (!clickedButton.getText().equals("")) {
            return;
        }

        if (playerXturn) {
            try {
                addClickSound();
            } catch (UnsupportedAudioFileException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (LineUnavailableException ex) {
                throw new RuntimeException(ex);
            }

            clickedButton.setText("X");
            //clickedButton.setEnabled(false);

            if (checkWin("X")) {
                try {
                    addWinSound();
                } catch (UnsupportedAudioFileException ex) {
                    throw new RuntimeException(ex);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (LineUnavailableException ex) {
                    throw new RuntimeException(ex);
                }
                JOptionPane.showMessageDialog(this, playerXField.getText()+" wins!");
                clip.stop();


                playerXScore++;
                scoreLabel.setText(playerXField.getText()+": " + playerXScore+" " +playerOField.getText()+": "+ playerOScore);
                resultTextArea.append(playerXField.getText()+ " wins!\n");
                checkmaximumScore();
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
            try {
                addClickSound();
            } catch (UnsupportedAudioFileException ex) {
                throw new RuntimeException(ex);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (LineUnavailableException ex) {
                throw new RuntimeException(ex);
            }
            clickedButton.setText("O");
            //clickedButton.setEnabled(false);
            if (checkWin("O")) {
                try {
                    addWinSound();

                } catch (UnsupportedAudioFileException ex) {
                    throw new RuntimeException(ex);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (LineUnavailableException ex) {
                    throw new RuntimeException(ex);
                }
                JOptionPane.showMessageDialog(this, playerOField.getText()+" wins!");
                clip.stop();

                playerOScore++;
                scoreLabel.setText(playerXField.getText()+ ": " + playerXScore+" " +playerOField.getText()+": "+ playerOScore);
                resultTextArea.append(playerOField.getText()+  " wins!\n");
                checkmaximumScore();
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
    }
    public void checkmaximumScore(){
        if (playerXScore > playerOScore) {
            resultTextArea.append( playerXField.getText() + " has the maximum points!\n");
        } else if (playerXScore < playerOScore) {
            resultTextArea.append(playerOField.getText()  + " has the maximum points!\n");
        } else {
            resultTextArea.append("Both players have the same points!\n");
        }

    }
    public void addClickSound() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        File file = new File("Pen Clicking (online-audio-converter.com).wav");
        AudioInputStream clickAudio = AudioSystem.getAudioInputStream(file);
        clip = AudioSystem.getClip();
        clip.open(clickAudio);
        clip.start();
    }
    public void addWinSound() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        File file = new File("Winner.wav");
        AudioInputStream winSound = AudioSystem.getAudioInputStream(file);
        clip = AudioSystem.getClip();
        clip.open(winSound);
        clip.start();

    }

}
