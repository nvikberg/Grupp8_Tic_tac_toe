import java.util.ArrayList;

public class Player {

    private String name;
    private int playerOrder = 0;
    public String playerSign;


    private boolean isCurrent = false;
    ArrayList<String> playedPositions = new ArrayList<String>();
    private int wonRounds = 0;  // if the same players keep on playing
                                // we can display at te end how many rounds each player have won

    Player(String name){
        this.name = name;
    }

    public void makeChoice(String buttonID){
        playedPositions.add(buttonID);
    }

    public String getName() {
        return name;
    }

    public int getPlayerOrder() {
        return playerOrder;
    }

    public void setPlayerOrder(int playerOrder) {
        this.playerOrder = playerOrder;
        if(playerOrder==1){
            this.playerSign = "X";
        } else {
            this.playerSign = "O";
        }
    }

    public boolean isCurrent() {
        return isCurrent;
    }

    public void setCurrent(boolean current) {
        isCurrent = current;
    }

}
