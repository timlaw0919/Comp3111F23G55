package FunctionC_TomCatchJerry;

public class CheckEndGame {
    public final int[] ExitPoint;
    public Character player;
    public Character pc;

    /**
     * Constructor of CheckEndGame class
     * @param Tom The object which is controlled by computer
     * @param Jerry The object which is controlled by player
     */
    public CheckEndGame(Character Tom, Character Jerry){
        ExitPoint = Tom.getLocation();
        player = Jerry;
        pc = Tom;
    }

    /**
     * Check whether the game is finished
     * @return true if either computer or player wins the game
     */
    public boolean isEndGame(){
        if (player.getLocation()[0] == ExitPoint[0] && player.getLocation()[1] == ExitPoint[1]) {
            player.setGame_state(true);
            System.out.println("Player Wins!");
        }
        else if (player.getLocation()[0] == pc.getLocation()[0] && player.getLocation()[1] == pc.getLocation()[1]) {
            pc.setGame_state(true);
            System.out.println("Computer Wins!");
        }
        return (player.getGame_state() && !pc.getGame_state()) ||
                (!player.getGame_state() && pc.getGame_state());
    }
}
