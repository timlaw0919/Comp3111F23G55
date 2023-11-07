package FunctionC_TomCatchJerry;

public class CheckEndGame {
    public final int[] ExitPoint;
    public Character player;
    public Character pc;
    public CheckEndGame(Character Tom, Character Jerry){
        ExitPoint = Tom.getLocation();
        player = Jerry;
        pc = Tom;
    }

    public boolean isEndGame(){
        if (player.getLocation()[0] == ExitPoint[0] && player.getLocation()[1] == ExitPoint[1]) {
            player.setGame_state(true);
        }
        else if (player.getLocation()[0] == pc.getLocation()[0] && player.getLocation()[1] == pc.getLocation()[1]) {
            pc.setGame_state(true);
        }
        return (player.getGame_state() && !pc.getGame_state()) ||
                (!player.getGame_state() && pc.getGame_state());
    }
}
