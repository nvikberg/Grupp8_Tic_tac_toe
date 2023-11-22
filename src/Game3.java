import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.UIManager;

public class Game3 extends JFrame implements ActionListener {


    private JButton[][] buttons; //two dimensional array for row and column game buttons

    private boolean playerXturn;
    private int turnCount;
    private int playerXScore;
    private int playerOScore;
    private int turnCount, playerXScore, playerOScore;
    private JTextField playerXField, playerOField, currentPlayer;
    private JLabel scoreLabel;
    Random random = new Random();
    private JButton startButton, restartButton;
    private JTextArea resultTextArea;
    private JScrollPane scrollPane;

    public Game3() {
        //adding UI manager "Look and Feel design Nimbus to the game /nv
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
                    break;
                }
            }
        } catch (Exception e) {
        }
        //key theme inputs for nimbus with a color change /nv
        UIManager.put("nimbusBase", new Color(0,83,153));      // Base color
        UIManager.put("nimbusBlueGrey", new Color(238,78,52));  // BlueGrey
        UIManager.put("control", new Color( 50,90,130));
        UIManager.put("text", new Color( 255,255,255));
        UIManager.put("TextArea.background", new Color(0,0,0));


        setTitle("Tic Tac Toe");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 600);
        setLocationRelativeTo(null);

        //button panel for start game
        JPanel buttonPanel = new JPanel();
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
        startButton.addActionListener(this);
        buttonPanel.add(startButton);
        playerPanel.add(startButton, 4);
        playerPanel.add(new JSeparator(SwingConstants.VERTICAL));

        currentPlayer = new JTextField();
        playerPanel.add(currentPlayer );
        //Game panel for game board

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
        // If players want to play a new game
        restartButton = new JButton("RESET GAME");
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
                buttons[i][j].setEnabled(true);
            }
        }
        if(playerXturn == true){
            currentPlayer.setText(""+playerXField.getText()+" turn!");
        } else {
            currentPlayer.setText(""+playerOField.getText()+" turn!");
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
        if(isWinner == false){
            if(symbol.equals("X")){
                currentPlayer.setText(""+playerOField.getText()+" turn!");
            } else {
                currentPlayer.setText(""+playerXField.getText()+" turn!");
            }
        }
        return isWinner;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        JButton clickedButton = (JButton) e.getSource();


        //Action when the start button is clicked
        if (clickedButton == startButton) {
            resetGame();
            return;
        }
            playerXScore = 0;
            playerOScore = 0;
            if(playerXturn == true){
                currentPlayer.setText(""+playerXField.getText()+" turn!");
            } else {
                currentPlayer.setText(""+playerOField.getText()+" turn!");
            }
            scoreLabel.setText(playerXField.getText()+": " + playerXScore + " " +playerOField.getText()+": "+ playerOScore);
            resultTextArea.setText("");
            restartButton.setEnabled(true);
            return;
        }

        //Action when the restart button is clicked
        if (clickedButton == restartButton) {
            playerXScore = 0;
            playerOScore = 0;
            scoreLabel.setText("Player X: " + playerXScore + " Player O: " + playerOScore);
            resultTextArea.setText("");
            resetGame();
            return;
        }

        if (playerXturn) {
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
            if (checkWin("X")) {
                JOptionPane.showMessageDialog(this, "Player X wins!");
                try {
                    addWinSound();
                } catch (UnsupportedAudioFileException | LineUnavailableException | IOException ex) {
                    throw new RuntimeException(ex);
                }
                JOptionPane.showMessageDialog(this, playerXField.getText()+" wins!");
                clip.stop();
                playerXScore++;
                scoreLabel.setText("Player X: " + playerXScore + " Player O: " + playerOScore);
                resultTextArea.append("Player X wins!\n");
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

        } else {
            try {
                addClickSound();
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                throw new RuntimeException(ex);
            }
            currentPlayer.setText(""+playerOField.getText()+" turn!");
            clickedButton.setText("O");
            if (checkWin("O")) {
                JOptionPane.showMessageDialog(this, "Player O wins!");
                try {
                    addWinSound();

                } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                    throw new RuntimeException(ex);
                }
                JOptionPane.showMessageDialog(this, playerOField.getText()+" wins!");
                clip.stop();
                playerOScore++;
                scoreLabel.setText("Player X: " + playerXScore + " Player O: " + playerOScore);
                resultTextArea.append("Player O wins!\n");
                resetGame();
                playerXturn =true;

            } else {
                turnCount++;
                if (turnCount == 9) {
                    JOptionPane.showMessageDialog(this, "It's a draw!");
                    resultTextArea.append("It's a draw!\n");
                    resetGame();
                    playerXturn =true;

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
    // to check maximumScore and add it result textarea
    public void checkmaximumScore(){
        if (playerXScore > playerOScore) {
            resultTextArea.append( playerXField.getText() + " is the current leader!\n");
        } else if (playerXScore < playerOScore) {
            resultTextArea.append(playerOField.getText()  + " is the current leader!\n");
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
