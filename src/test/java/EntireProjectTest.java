import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import FunctionB_ShortestPath.*;

class EntireProjectTest {
    // Big Main GUI


    // Function A (CellState)


    // Function A (Cell)


    // Function A (CSVOutput)


    // Function A (Main)


    // Function A (MazeGenerator)


    // Function A (MazeGUI)


    // Function A (MazeLoader)


    // Function B (Node)


    // Function B (AStarAlgorithm)
    @Test
    void AStarAlgorithm(){
        AStarAlgorithm aStarAlgorithm = new AStarAlgorithm(new int[]{0,0}, new int[]{4,4});
        assertArrayEquals(new int[]{0,0}, aStarAlgorithm.tomLocation); // Target Function
        assertArrayEquals(new int[]{4,4}, aStarAlgorithm.jerryLocation); // Target Function
    }


    @Test
    void changeLocation(){
        AStarAlgorithm aStarAlgorithm = new AStarAlgorithm(new int[]{0,0}, new int[]{4,4});
        assertArrayEquals(new int[]{10,20}, aStarAlgorithm.changeLocation((new int[]{10,20}), 0)); // Target Function
        assertArrayEquals(new int[]{20,10}, aStarAlgorithm.changeLocation((new int[]{20,10}), 1)); // Target Function
    }

    @Test
    void checkExplored(){

    }

    // Function B (Main)


    // Function B (CSVOutput)


    // Function B (CSVOutputForGUI)


    // Function B (MazeWithShortestPath)


    // Function C (DirectionState)


    // Function C (Character)


    // Function C (CheckEndGame)


    // Function C (GameMain)


    // Function C (GameMazeGUI)


    // Function C (InfoGUI)


    // Function C (KeyBoardListener)


    // Function C (MainGUI)

}