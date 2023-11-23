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
    private int turnCount, playerXScore, playerOScore;
    private JTextField playerXField, playerOField, currentPlayer;
    private JLabel scoreLabel;
    Random random = new Random();
    private JButton startButton, restartButton;
    private JTextArea resultTextArea;
    private JScrollPane scrollPane;
    Clip clip;

    public Game3() {
        setTitle("Tic Tac Toe");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 600);
        setLocationRelativeTo(null);

        //player panel for start player name and start game
        JPanel playerPanel = new JPanel(new GridLayout(4, 2));
        JLabel playerXLabel = new JLabel("Player Name:");
        playerXLabel.setHorizontalAlignment(SwingConstants.CENTER);
        playerXField = new JTextField();
        playerXField.setText("Player 1");

        JLabel playerOLabel = new JLabel("Player Name:");
        playerOLabel.setHorizontalAlignment(SwingConstants.CENTER);
        playerOField = new JTextField();
        playerOField.setText("Player 2");

        playerPanel.add(playerXLabel);
        playerPanel.add(playerXField);
        playerPanel.add(playerOLabel);
        playerPanel.add(playerOField);

        startButton = new JButton("Start Game");
        startButton.setHorizontalAlignment(SwingConstants.CENTER);
        startButton.addActionListener(this);
        playerPanel.add(startButton, 4);
        playerPanel.add(new JSeparator(SwingConstants.VERTICAL));

        currentPlayer = new JTextField();
        playerPanel.add(currentPlayer);
        //Game panel for game board

        JPanel gamePanel = new JPanel(new GridLayout(3, 3));
        buttons = new JButton[3][3];
        playerXturn = random.nextInt() < 0.5; // random boolean value where there's a 50% chance of it being true
        turnCount = 0;
        playerXScore = 0;
        playerOScore = 0;

        initializeButtons(gamePanel); //method call to create game buttons in game panel

        //Score label to get updated score

        scoreLabel = new JLabel(playerXField.getText() + " : " + playerXScore + " " + playerOField.getText() + ": " + playerOScore);
        scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // If players want to play a new game
        restartButton = new JButton("RESET GAME");
        restartButton.addActionListener(this);

        //to get final result
        resultTextArea = new JTextArea(5, 20);
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

    // If players want to play a new game
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
        if (playerXturn == true) {
            currentPlayer.setText("" + playerXField.getText() + " turn!");
        } else {
            currentPlayer.setText("" + playerOField.getText() + " turn!");
        }
    }
    // en method for check winner

    private boolean checkWin(String symbol) {
        boolean isWinner = false;
        for (int i = 0; i < 3; i++) {
            if (buttons[i][0].getText().equals(symbol) && buttons[i][1].getText().equals(symbol) && buttons[i][2].getText().equals(symbol)) {
                isWinner = true;
            }
            if (buttons[0][i].getText().equals(symbol) && buttons[1][i].getText().equals(symbol) && buttons[2][i].getText().equals(symbol)) {
                isWinner = true;
            }

        }
        if (buttons[0][0].getText().equals(symbol) && buttons[1][1].getText().equals(symbol) && buttons[2][2].getText().equals(symbol)) {
            isWinner = true;
        }

        if (buttons[0][2].getText().equals(symbol) && buttons[1][1].getText().equals(symbol) && buttons[2][0].getText().equals(symbol)) {
            isWinner = true;
        }
        if (isWinner == false) {
            if (symbol.equals("X")) {
                currentPlayer.setText("" + playerOField.getText() + " turn!");
            } else {
                currentPlayer.setText("" + playerXField.getText() + " turn!");
            }
        }
        return isWinner;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        JButton clickedButton = (JButton) e.getSource();
        //Action when the start button is clicked
        if (clickedButton == startButton) {
            startButton.setEnabled(false);
            playerXField.setEnabled(false);
            playerOField.setEnabled(false);
            resetGame();
            playerXScore = 0;
            playerOScore = 0;
            if (playerXturn == true) {
                currentPlayer.setText("" + playerXField.getText() + " turn!");
            } else {
                currentPlayer.setText("" + playerOField.getText() + " turn!");
            }
            scoreLabel.setText(playerXField.getText() + ": " + playerXScore + " " + playerOField.getText() + ": " + playerOScore);
            resultTextArea.setText("");
            restartButton.setEnabled(true);
            return;
        }

        //Action when the restart button is clicked
        if (clickedButton == restartButton) {
            playerXScore = 0;
            playerOScore = 0;
            playerXField.setEnabled(true);
            playerOField.setEnabled(true);
            scoreLabel.setText(playerXField.getText() + ": " + playerXScore + " " + playerOField.getText() + ": " + playerOScore);
            resultTextArea.setText("");
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    buttons[i][j].setText("");
                    buttons[i][j].setEnabled(false);
                }
            }
            restartButton.setEnabled(false);
            startButton.setEnabled(true);
            currentPlayer.setText("");
            return;
        }
        if (!clickedButton.getText().isEmpty()) {
            return;
        }
        //Action for  playerX turn

        if (playerXturn) {
            try {
                addClickSound();
            } catch (UnsupportedAudioFileException | LineUnavailableException | IOException ex) {
                throw new RuntimeException(ex);
            }
            clickedButton.setText("X");

            // call checkwin method to check PlayerX is winner or not

            if (checkWin("X")) {
                try {
                    addWinSound();
                } catch (UnsupportedAudioFileException | LineUnavailableException | IOException ex) {
                    throw new RuntimeException(ex);
                }
                JOptionPane.showMessageDialog(this, playerXField.getText() + " wins!");
                clip.stop();
                playerXScore++;
                scoreLabel.setText(playerXField.getText() + ": " + playerXScore + " " + playerOField.getText() + ": " + playerOScore);
                resultTextArea.append(playerXField.getText() + " wins!\n");
                checkmaximumScore();
                resetGame();
                playerXturn = false;


            } else {
                turnCount++;
                // if every player gets same points then it will be draw
                if (turnCount == 9) {
                    JOptionPane.showMessageDialog(this, "It's a draw!");
                    resultTextArea.append("It's a draw!\n");
                    resetGame();
                    playerXturn = false;


                } else {
                    playerXturn = false;

                }
            }
            //Action for  playerO turn
        } else {
            try {
                addClickSound();
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                throw new RuntimeException(ex);
            }
            currentPlayer.setText("" + playerOField.getText() + " turn!");
            clickedButton.setText("O");
            // call checkwin method to check PlayerO is winner or not

            if (checkWin("O")) {
                try {
                    addWinSound();

                } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                    throw new RuntimeException(ex);
                }
                JOptionPane.showMessageDialog(this, playerOField.getText() + " wins!");
                clip.stop();
                playerOScore++;
                scoreLabel.setText(playerXField.getText() + ": " + playerXScore + " " + playerOField.getText() + ": " + playerOScore);
                resultTextArea.append(playerOField.getText() + " wins!\n");
                checkmaximumScore();
                resetGame();
                playerXturn = true;

            } else {
                turnCount++;
                // if every player gets same points then it will be draw
                if (turnCount == 9) {
                    JOptionPane.showMessageDialog(this, "It's a draw!");
                    resultTextArea.append("It's a draw!\n");
                    resetGame();
                    playerXturn = true;

                } else {
                    playerXturn = true;
                }
            }
        }
    }

    // to check maximumScore and add it result textarea
    public void checkmaximumScore() {
        if (playerXScore > playerOScore) {
            resultTextArea.append(playerXField.getText() + " is the current leader!\n");
        } else if (playerXScore < playerOScore) {
            resultTextArea.append(playerOField.getText() + " is the current leader!\n");
        } else {
            resultTextArea.append("Equal score!\n");
        }

    }

    //a method for add click game button sound
    public void addClickSound() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        AudioInputStream clickAudio = AudioSystem.getAudioInputStream(new File("Pen Clicking (online-audio-converter.com).wav"));
        clip = AudioSystem.getClip();
        clip.open(clickAudio);
        clip.start();
    }

    // a method for add win sound
    public void addWinSound() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        AudioInputStream winSound = AudioSystem.getAudioInputStream(new File("Winner.wav"));
        clip = AudioSystem.getClip();
        clip.open(winSound);
        clip.start();
    }
}