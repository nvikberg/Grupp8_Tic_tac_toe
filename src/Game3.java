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
    private JTextField playerXField, playerOField;
    private JLabel scoreLabel,currentPlayer;
    Random random = new Random();
    private JButton startButton, restartButton;
    private JTextArea resultTextArea;
    Clip clip;
    ImageIcon iconImage = new ImageIcon("hashtag1.png");
    ImageIcon winnerXImage = new ImageIcon("purpleWinner.png");
    ImageIcon drawImage = new ImageIcon("draw.png");
    ImageIcon winnerOImage = new ImageIcon("yellowWinner.png");


    public Game3() {
        setDesign();

        //player panel for start player name and start game
        JPanel playerPanel = new JPanel(new GridLayout(4, 3));
        JLabel playerXLabel = new JLabel("Player Name:  ");
        playerXLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        playerXField = new JTextField();
        playerXField.setText("Player 1");
        playerPanel.add(playerXLabel);
        playerPanel.add(playerXField);
        // the Separator takes the next space otherwise currenPlayer ends up there.
        playerPanel.add(new JSeparator(SwingConstants.VERTICAL));

        JLabel playerOLabel = new JLabel("Player Name:  ");
        playerOLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        playerOField = new JTextField();
        playerOField.setText("Player 2");
        playerPanel.add(playerOLabel);
        playerPanel.add(playerOField);
        playerPanel.add(new JSeparator(SwingConstants.VERTICAL));

        startButton = new JButton("Start Game");
        startButton.setHorizontalAlignment(SwingConstants.CENTER);
        startButton.addActionListener(this);
        playerPanel.add(new JSeparator(SwingConstants.VERTICAL));
        playerPanel.add(startButton);
        playerPanel.add(new JSeparator(SwingConstants.VERTICAL));

        JLabel playersTurnLabel = new JLabel("Who's up  ");
        playersTurnLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        playerPanel.add(playersTurnLabel);

        //playerPanel.add(new JSeparator(SwingConstants.HORIZONTAL));
        currentPlayer = new JLabel();
        playerPanel.add(currentPlayer);
        playerPanel.add(new JSeparator(SwingConstants.VERTICAL));

        //Game panel for game board

        JPanel gamePanel = new JPanel(new GridLayout(3, 3));
        buttons = new JButton[3][3];
        turnCount = 0;
        playerXScore = 0;
        playerOScore = 0;

        initializeButtons(gamePanel); //method call to create game buttons in game panel

        //Score label to get updated score
        scoreLabel = new JLabel(playerXField.getText() + " : " + playerXScore + " " + playerOField.getText() + ": " + playerOScore);
        scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // If players want to play a new game. Can change names again and the score nullates in ActionListeners
        restartButton = new JButton("RESET GAME");
        restartButton.addActionListener(this);

        //to get final result
        resultTextArea = new JTextArea(5, 20);
        resultTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultTextArea);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(scoreLabel, BorderLayout.NORTH);
        bottomPanel.add(restartButton, BorderLayout.CENTER);
        bottomPanel.add(scrollPane, BorderLayout.SOUTH);

        add(playerPanel, BorderLayout.NORTH);
        add(gamePanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
        setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        JButton clickedButton = (JButton) e.getSource();
        //Action when the start button is clicked. Sets the variables on 0 (the player score is already set somewhere else and can be removed)
        if (clickedButton == startButton) {
            startButton.setEnabled(false);
            playerXField.setEnabled(false);
            playerOField.setEnabled(false);
            playerXScore = 0;
            playerOScore = 0;
            scoreLabel.setText(playerXField.getText() + ": " + playerXScore + " " + playerOField.getText() + ": " + playerOScore);
            resultTextArea.setText("");
            restartButton.setEnabled(true);
            // in the metod restartGame the first player is decided. PlayerX true == playerX is first PlayerX false == PlayerO is first
            restartGame();
            return;
        }

        //Action when the Reset Game is clicked. Removes the scores of the previous players and let them chose new names
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
        // If there is text in the currently pressed button the actionListener is cancelled and nothing happens.
        // that way we avoid disabling the currently pressed button.
        if (!clickedButton.getText().isEmpty()) {
            return;
        }
        //Action for  playerX turn. It repeats what happens in PlayerO turn. PlayerXturn holds value True or False.
        //If the current player is player X (Player one) then mark the button with his symbol, play the sound,
        // check if with that move the player X wins (checkWin returns true or false) if true do actions, if false do other actions and change the player order
        // by setting playerXturn = false;
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
            // The same thing like with the player playing with X. Repetition. At the end if player O is not a winner switch the turn back to Player X
            // by setting playerXturn = true;
        } else {
            clickedButton.setText("O");
            //clickedButton.setEnabled(false);
            try {
                addClickSound();
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                throw new RuntimeException(ex);
            }
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
                    JOptionPane.showMessageDialog(this, "It's a draw!","Even Steven",JOptionPane.INFORMATION_MESSAGE,drawImage);
                    resultTextArea.append("It's a draw!\n");
                    playerXturn = true;
                    restartGame();
                } else
                    playerXturn = true;
            }
        }
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
    //restart game (creates a new blank game panel). Desides if playerX will be first or second. Writes who's turn it is.
    //Restart game is used every time since the button start game has been clicked. Called after a win for any of the players or a draw.
    // It is also called when the button Start Game is hit.
    private void restartGame() {
        turnCount = 0;
        playerXturn = random.nextInt() < 0.5;// random boolean value where there's a 50% chance of it being true
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
                buttons[i][j].setEnabled(true);
            }
        }
        if (playerXturn) {
            currentPlayer.setText(playerXField.getText() + "'s turn!");
        } else {
            currentPlayer.setText(playerOField.getText() + "'s turn!");
        }
    }


    // en method for check winner
    private boolean checkWin(String symbol) {
        boolean isWinner = false;
        for (int i = 0; i < 3; i++) {
            // First if -> checks if all the buttons of the same ROW are holding the same player symbol. If yes - this is the winner of the game
            if (buttons[i][0].getText().equals(symbol) && buttons[i][1].getText().equals(symbol) && buttons[i][2].getText().equals(symbol)) {
                isWinner = true;
            }
            // Second if -> checks if all the buttons of the same COLUMN are holding the same player symbol. If yes - this is the winner of the game
            if (buttons[0][i].getText().equals(symbol) && buttons[1][i].getText().equals(symbol) && buttons[2][i].getText().equals(symbol)) {
                isWinner = true;
            }
        }
        // Third if -> checks if all the buttons of DIAGONAL left to right are holding the same player symbol. If yes - this is the winner of the game
        if (buttons[0][0].getText().equals(symbol) && buttons[1][1].getText().equals(symbol) && buttons[2][2].getText().equals(symbol)) {
            isWinner = true;
        }
        // Forth if -> checks if all the buttons of DIAGONAL right to left are holding the same player symbol. If yes - this is the winner of the game
        if (buttons[0][2].getText().equals(symbol) && buttons[1][1].getText().equals(symbol) && buttons[2][0].getText().equals(symbol)) {
            isWinner = true;
        }
        //If there is no winner the text showing which is the current player switches from the current to the next player.
        if (!isWinner) {
            if (symbol.equals("X")) {
                currentPlayer.setText(playerOField.getText() + "'s turn!");
            } else {
                currentPlayer.setText("" + playerXField.getText() + "'s turn!");
            }
        } else {
            currentPlayer.setText("");  // if there is a winner then the current player text is set to empty before assigning the first player of the next round.
        }
        return isWinner;
    }


    // to check who has bigger overall score and add it result textarea
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


    // Here is all the Color schemes that were chosen to represent the project. They are called in the constructor on start.
    public void setDesign(){
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
        UIManager.put("TextArea.background", new Color(255, 255, 255));
        UIManager.put("TextField.background", new Color(255, 255, 255));
        UIManager.put("Label.font", new Font("Times", Font.BOLD, 14));
        UIManager.put("TextArea.font", new Font("Times", Font.BOLD, 14));
        UIManager.put("TitledBorder.font", new Font("Times", Font.BOLD, 14));
        UIManager.put("OptionPane.messageFont", new Font("Times", Font.BOLD, 14));

        setTitle("Tic Tac Toe");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(500,100,500, 600); //setting bounds
        setIconImage(iconImage.getImage()); //place logo in frame (switches out java logo)
        setLocationRelativeTo(null);
    }
}