package FunctionC_TomCatchJerry;

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
                player.setDirection(1);
                break;
            case A:	// Left
                player.setDirection(3);
                break;

            case S: 	// Downward
                player.setDirection(2);
                break;

            case D:	// Right
                player.setDirection(4);
                break;

            default:
                player.setDirection(0);
                break;
        }
    }

}

