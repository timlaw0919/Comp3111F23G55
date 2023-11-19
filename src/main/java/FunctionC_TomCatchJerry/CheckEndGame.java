package FunctionC_TomCatchJerry;

import static FunctionC_TomCatchJerry.Character.toIndex;
import static FunctionC_TomCatchJerry.GameMain.Jerry;
import static FunctionC_TomCatchJerry.GameMain.Tom;

public class CheckEndGame {
    public final int ExitPoint = toIndex(Tom.location);

    /**
     * Check whether the game is finished
     * @return true if either Tom or Jerry wins the game
     */
    public boolean isEndGame(){
        if (toIndex(Jerry.location) == ExitPoint) {
            Jerry.Game_state = true;
        }
        else if (toIndex(Jerry.location) == toIndex(Tom.location)) {
            Tom.Game_state = true;
        }
        return (Jerry.Game_state && !Tom.Game_state) ||
                (!Jerry.Game_state && Tom.Game_state);
    }
}
