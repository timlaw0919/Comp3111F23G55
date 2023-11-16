package FunctionC_TomCatchJerry;

import FunctionC_TomCatchJerry.constant.DirectionState;
import javafx.scene.input.KeyEvent;

import java.awt.event.KeyAdapter;

public class KeyBoardListener extends KeyAdapter{
    public Character player;

    public KeyBoardListener (Character player){
        this.player = player;
    }

    /**
     * Turns the inputs from the keyboard to direction of the moving object
     * @param e The input received from the keyboard
     */
    public void keyPressed(KeyEvent e){
        switch(e.getCode()){
            case W:	    // Upward
//                player.setDirection(DirectionState.UPWARD);
                player.newCol = 0; //player.location[1];
                player.newRow = -1; //player.location[0]-1;
                break;
            case A:	    // Left
//                player.setDirection(DirectionState.LEFT);
                player.newCol = -1;
                player.newRow = 0;
                break;

            case S: 	// Downward
//                player.setDirection(DirectionState.DOWNWARD);
                player.newCol = 0;
                player.newRow = 1;
                break;

            case D: 	// Right
//                player.setDirection(DirectionState.RIGHT);
                player.newCol = 1;
                player.newRow = 0;
                break;

            default:
//                player.setDirection(DirectionState.UNMOVE);
                break;
        }
    }

}

