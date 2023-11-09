package FunctionC_TomCatchJerry;

import FunctionC_TomCatchJerry.constant.DirectionState;
import javafx.scene.input.KeyEvent;

import java.awt.event.KeyAdapter;

public class KeyBoardListener extends KeyAdapter{
    public Character player;

    public KeyBoardListener (Character player){
        this.player = player;
    }

    public void keyPressed(KeyEvent e){
        switch(e.getCode()){
            case W:	// Upward
                player.setDirection(DirectionState.UPWARD.ordinal());
                break;
            case A:	// Left
                player.setDirection(DirectionState.LEFT.ordinal());
                break;

            case S: 	// Downward
                player.setDirection(DirectionState.DOWNWARD.ordinal());
                break;

            case D:	// Right
                player.setDirection(DirectionState.RIGHT.ordinal());
                break;

            default:
                player.setDirection(DirectionState.UNMOVE.ordinal());
                break;
        }
    }

}

