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
            case W:	    // Upward
                player.setDirection(DirectionState.UPWARD);
                break;
            case A:	    // Left
                player.setDirection(DirectionState.LEFT);
                break;

            case S: 	// Downward
                player.setDirection(DirectionState.DOWNWARD);
                break;

            case D: 	// Right
                player.setDirection(DirectionState.RIGHT);
                break;

            default:
                player.setDirection(DirectionState.UNMOVE);
                break;
        }
    }

}

