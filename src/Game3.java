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
    ImageIcon iconImage = new ImageIcon("hashtag1.png");
    ImageIcon winnerXImage = new ImageIcon("purpleWinner.png");
    ImageIcon drawImage = new ImageIcon("draw.png");
    ImageIcon winnerOImage = new ImageIcon("yellowWinner.png");





    public Game3() {
        //adding UI manager "Look and Feel design Nimbus to the game /nv
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
                    break;
                }
            }
        } catch (Exception ignored) {
        }
        //key theme inputs for nimbus with a color change /nv
        UIManager.put("nimbusBase", new Color(149, 212, 163));
        UIManager.put("nimbusBlueGrey", new Color(248, 240, 131));
        UIManager.put("control", new Color(234, 119, 133));
       // UIManager.put("text", new Color( 255,255,255));
        UIManager.put("TextArea.background", new Color(149, 212, 163));
        UIManager.put("TextField.background", new Color(149, 212, 163));

        setTitle("Tic Tac Toe");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(500,100,500, 600); //setting bounds
        setIconImage(iconImage.getImage()); //place logo in frame (switches out java logo)
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
        playerPanel.add(startButton);
        playerPanel.add(new JSeparator(SwingConstants.VERTICAL));

        currentPlayer = new JTextField();
        playerPanel.add(currentPlayer);
        //Game panel for game board

        JPanel gamePanel = new JPanel(new GridLayout(3, 3));
        buttons = new JButton[3][3];
        //playerXturn = random.nextInt() < 0.5; // random boolean value where there's a 50% chance of it being true
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
    private void restartGame() {
        turnCount = 0;
        playerXturn = random.nextInt() < 0.5;// random boolean value where there's a 50% chance of it being true
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
                buttons[i][j].setEnabled(true);
            }
        }
        if (playerXturn == true) {
            currentPlayer.setText("" + playerXField.getText() + "'s turn!");
        } else if (playerXturn == false) {
            currentPlayer.setText("" + playerOField.getText() + "'s turn!");
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
        if (!isWinner) {
            if (symbol.equals("X")) {
                currentPlayer.setText("" + playerOField.getText() + "'s turn!");
            } else {
                currentPlayer.setText("" + playerXField.getText() + "'s turn!");
            }
        } else {
            currentPlayer.setText("");
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
            playerXScore = 0;
            playerOScore = 0;
            scoreLabel.setText(playerXField.getText() + ": " + playerXScore + " " + playerOField.getText() + ": " + playerOScore);
            resultTextArea.setText("");
            restartButton.setEnabled(true);
            restartGame();
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
            clickedButton.setText("X");
            //clickedButton.setEnabled(false);
            try {
                addClickSound();
            } catch (UnsupportedAudioFileException | LineUnavailableException | IOException ex) {
                throw new RuntimeException(ex);
            }
            // call checkWin method to check PlayerX is winner or not
            if (checkWin("X")) {
                try {
                    addWinSound();
                } catch (UnsupportedAudioFileException | LineUnavailableException | IOException ex) {
                    throw new RuntimeException(ex);
                }
                JOptionPane.showMessageDialog(this, playerXField.getText() + " wins!","Winner", JOptionPane.INFORMATION_MESSAGE,winnerXImage);
                clip.stop();
                playerXScore++;
                scoreLabel.setText(playerXField.getText() + ": " + playerXScore + " " + playerOField.getText() + ": " + playerOScore);
                resultTextArea.append(playerXField.getText() + " wins!\n");
                checkmaximumScore();
                playerXturn = false;
                restartGame();
            } else {
                turnCount++;
                // if every player gets same points then it will be draw
                if (turnCount == 9) {
                    JOptionPane.showMessageDialog(this, "It's a draw!","Even Steven",JOptionPane.INFORMATION_MESSAGE,drawImage);
                    resultTextArea.append("It's a draw!\n");
                    playerXturn = false;
                    restartGame();
                } else {
                    playerXturn = false;
                }
            }
            //Action for  playerO turn
        } else {
            clickedButton.setText("O");
            //clickedButton.setEnabled(false);
            try {
                addClickSound();
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                throw new RuntimeException(ex);
            }
            currentPlayer.setText("" + playerOField.getText() + "'s turn!");
            // call checkwin method to check PlayerO is winner or not
            if (checkWin("O")) {
                try {
                    addWinSound();
                } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                    throw new RuntimeException(ex);
                }
                JOptionPane.showMessageDialog(this, playerOField.getText() + " wins!", "Winner", JOptionPane.INFORMATION_MESSAGE,winnerOImage);
                clip.stop();
                playerOScore++;
                scoreLabel.setText(playerXField.getText() + ": " + playerXScore + " " + playerOField.getText() + ": " + playerOScore);
                resultTextArea.append(playerOField.getText() + " wins!\n");
                checkmaximumScore();
                playerXturn = true;
                restartGame();
            } else {
                turnCount++;
                // if every player gets same points then it will be draw
                if (turnCount == 9) {
                    JOptionPane.showMessageDialog(this, "It's a draw!");
                    resultTextArea.append("It's a draw!\n");
                    playerXturn = true;
                    restartGame();
                } else {
                    playerXturn = true;
                }
            }
        }
    }

    // to check maximumScore and add it result textarea
    public void checkmaximumScore() {
        if (playerXScore > playerOScore) {
            resultTextArea.append(playerXField.getText() + " is the current leader.\n");
        } else if (playerXScore < playerOScore) {
            resultTextArea.append(playerOField.getText() + " is the current leader.\n");
        } else {
            resultTextArea.append("Equal score.\n");
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