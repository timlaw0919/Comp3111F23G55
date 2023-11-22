package FunctionC_TomCatchJerry;

import javafx.scene.input.KeyEvent;

import java.awt.event.KeyAdapter;

/**
 * KeyBoardListener class is used to update the direction of the specific moving object according to the input received from key press by player.
 */
public class KeyBoardListener extends KeyAdapter{
    public Character player;

    /**
     * @param player The character controlled by player
     */
    public KeyBoardListener (Character player){
        this.player = player;
    }

    /**
     * Get the inputs from the keyboard to update the direction of the moving object
     * @param e The input received from the keyboard
     */
    public void keyPressed(KeyEvent e){
        switch(e.getCode()){
            case W:	    // Upward
                player.newCol = 0;
                player.newRow = -1;
                break;
            case A:	    // Left
                player.newCol = -1;
                player.newRow = 0;
                break;

            case S: 	// Downward
                player.newCol = 0;
                player.newRow = 1;
                break;

            case D: 	// Right
                player.newCol = 1;
                player.newRow = 0;
                break;

            default:
                break;
        }
    }

}

