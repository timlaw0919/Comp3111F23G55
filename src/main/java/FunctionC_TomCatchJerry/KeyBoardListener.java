package FunctionC_TomCatchJerry;

import javafx.scene.input.KeyEvent;

import java.awt.event.KeyAdapter;

public class KeyBoardListener extends KeyAdapter{
    public Character player;

    /**
     * @param player The character controlled by player
     */
    public KeyBoardListener (Character player){
        this.player = player;
    }

    /**
     * Turns the inputs from the keyboard to following step of the moving object
     * @param e The input received from the keyboard
     */
    public void keyPressed(KeyEvent e){
        switch(e.getCode()){
            case W:	    // Upward
                player.newCol = 0; //player.location[1];
                player.newRow = -1; //player.location[0]-1;
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

