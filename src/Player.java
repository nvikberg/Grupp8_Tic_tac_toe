public class Player {

    private String name;
    private int playerOrder = 0;
    String[] playedPositions;
    private int wonRounds = 0;  // if the same players keep on playing
                                // we can display at te end how many rounds each player have won

    Player(String name){
        this.name = name;
    }

    public void makeChoice(String buttonID){
        //playedPositions.
    }

    public String getName() {
        return name;
    }

    public int getPlayerOrder() {
        return playerOrder;
    }

    public void setPlayerOrder(int playerOrder) {
        this.playerOrder = playerOrder;
    }

}
