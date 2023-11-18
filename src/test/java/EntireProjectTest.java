import FunctionA_CreateMaze.constant.CellState;
import FunctionC_TomCatchJerry.Character;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.junit.jupiter.api.Test;

import static javafx.scene.input.KeyCode.*;
import static javafx.scene.input.KeyEvent.*;
import static org.junit.jupiter.api.Assertions.*;

import FunctionA_CreateMaze.*;
import FunctionB_ShortestPath.*;
import FunctionC_TomCatchJerry.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.jar.JarEntry;

class EntireProjectTest {
    // Big Main GUI


    // Function A (CellState)//


    // Function A (Cell)
    @Test
    void Cell(){
        Cell test = new Cell(0,0, CellState.BLOCK);
        assertEquals(0, test.row);
        assertEquals(0, test.col);
        assertEquals(CellState.BLOCK, test.value);
    }

    @Test
    void CellEquals(){
        Cell cell1 = new Cell(0,0, CellState.BLOCK);
        Cell cell2 = new Cell(0,0, CellState.PATH);
        boolean test = cell1.equals(cell2);
        assertTrue(test);
    }

    // Function A (CSVOutput)


    // Function A (MazeGenerator)
    void MazeGenerator(){

    }

    void EntryPointGenerator(){

    }

    void initializeMaze(){

    }

    void generateMaze(){

    }

    void checkValidNeighbors(){

    }

    void getValidNeighbors(){

    }

    void cellOnGrid(){

    }

    void cellOnEdge(){

    }

    void cellOnCorner(){

    }

    void checkIfEntryPoint(){

    }

    void checkIfExitPoint(){

    }

    void CellOnOppositeEdge(){

    }

    void getMaze(){

    }

    // Function A (MazeGUI)


    // Function A (MazeLoader)


    // Function B (Node)
    @Test void Node(){
        Node node = new Node(new int[] {0,0}, null, 10, 0);  // Target Function
        assertArrayEquals(new int[] {0,0}, node.currentPosition);
        assertEquals(null, node.parent);
        assertEquals(10, node.forwardCost);
        assertEquals(0, node.backwardCost);
        assertEquals(10, node.totalCost);
    }

    // Function B (AStarAlgorithm)
    @Test
    void AStarAlgorithm(){
        AStarAlgorithm aStarAlgorithm = new AStarAlgorithm(new int[]{0,0}, new int[]{4,4}, "maze_map_for_unit_testing.csv"); // Target Function
        int[][] maze = {{1,1,1,1,1,1,1,1}, {2,0,0,1,0,0,0,1}, {1,1,0,0,0,1,0,1}, {1,0,0,1,0,0,0,1}, {1,0,1,0,1,0,1,1}, {1,0,0,0,0,0,0,1}, {1,1,1,1,1,1,0,1}, {1,1,1,1,1,1,3,1}};
        assertArrayEquals(new int[]{0,0}, aStarAlgorithm.tomLocation);
        assertArrayEquals(new int[]{4,4}, aStarAlgorithm.jerryLocation);
        assertArrayEquals(maze, aStarAlgorithm.maze);
    }

    @Test
    void changeLocation(){
        AStarAlgorithm aStarAlgorithm = new AStarAlgorithm(new int[]{0,0}, new int[]{4,4}, "maze_map_for_unit_testing.csv");
        assertArrayEquals(new int[]{10,20}, aStarAlgorithm.changeLocation((new int[]{10,20}), 0)); // Target Function
        assertArrayEquals(new int[]{20,10}, aStarAlgorithm.changeLocation((new int[]{20,10}), 1)); // Target Function
    }

    @Test
    void checkExplored(){
        List<Node> nodeList = new ArrayList<>();
        nodeList.add(new Node(new int[] {0,0}, null, 10, 0));
        AStarAlgorithm aStarAlgorithm = new AStarAlgorithm(new int[]{0,0}, new int[]{4,4}, "maze_map_for_unit_testing.csv");
        assertTrue(aStarAlgorithm.checkExplored(nodeList, new int[]{0,0})); // Target Function
        assertFalse(aStarAlgorithm.checkExplored(nodeList, new int[]{1,1})); // Target Function
    }

    @Test
    void checkValidNode(){
        AStarAlgorithm aStarAlgorithm = new AStarAlgorithm(new int[]{0,0}, new int[]{4,4}, "maze_map_for_unit_testing.csv");
        List<Node> frontier = new ArrayList<>();
        List<Node> expandedNode = new ArrayList<>();
        frontier.add(new Node(new int[] {0,0}, null, 10, 0));
        assertTrue(aStarAlgorithm.checkValidNode(expandedNode, frontier, new int[]{1,1})); // Target Function
        assertFalse(aStarAlgorithm.checkValidNode(expandedNode, frontier, new int[]{0,0})); // Target Function
    }

    @Test
    void findNeighbor(){
//        List<Node> expectedResult = new ArrayList<>();
//        expectedResult.add(new Node(new int[] {1,1}, new Node(new int[]{1,0}, null, 12, 0), 11, 1));
        AStarAlgorithm aStarAlgorithm = new AStarAlgorithm(new int[]{1,0}, new int[]{7,6}, "maze_map_for_unit_testing.csv");
        Node currentNode = new Node(new int[] {1,0}, null, 12, 0);
        List<Node> expandedNode = new ArrayList<>();
        expandedNode.add(new Node(new int[] {1,0}, null, 12, 0));
        List<Node> frontier = new ArrayList<>();

        List<Node> functionReturn = aStarAlgorithm.findNeighbor(currentNode, expandedNode, frontier); // Target Function
        assertEquals(1, functionReturn.size());
        assertArrayEquals(new int[] {1,1}, functionReturn.get(0).currentPosition);
        assertArrayEquals(new int[] {1,0}, functionReturn.get(0).parent.currentPosition);
        assertEquals(11, functionReturn.get(0).forwardCost);
        assertEquals(1, functionReturn.get(0).backwardCost);
        assertEquals(12, functionReturn.get(0).totalCost);
    }

    @Test
    void pathGeneratorByAStar(){
        AStarAlgorithm aStarAlgorithm = new AStarAlgorithm(new int[]{7,6}, new int[]{1,0}, "maze_map_for_unit_testing.csv");
        List<int[]> expectedResult = new ArrayList<>();
        expectedResult.addAll(Arrays.asList(new int[]{1,0}, new int[]{1,1}, new int[]{1,2}, new int[]{2,2}, new int[]{2,3}, new int[]{2,4}, new int[]{3,4}, new int[]{3,5}, new int[]{4,5}, new int[]{5,5}, new int[]{5,6}, new int[]{6,6}, new int[]{7,6}));

        List<int[]> actualResult = aStarAlgorithm.pathGeneratorByAStar(); // Target Function
        assertEquals(expectedResult.size(), actualResult.size());
        for (int i = 0; i < expectedResult.size(); i++) {
            assertArrayEquals(expectedResult.get(i), actualResult.get(i));
        }
    }

    @Test
    void tomNextMovement(){
        AStarAlgorithm aStarAlgorithm1 = new AStarAlgorithm(new int[]{7,6}, new int[]{1,0}, "maze_map_for_unit_testing.csv");
        assertArrayEquals(new int[] {6,6}, aStarAlgorithm1.tomNextMovement()); // Target Function
        AStarAlgorithm aStarAlgorithm2 = new AStarAlgorithm(new int[]{7,6}, new int[]{1,0}, "maze_map_for_unit_testing_no_path_same.csv");
        assertArrayEquals(new int[] {7,6}, aStarAlgorithm2.tomNextMovement()); // Target Function
        AStarAlgorithm aStarAlgorithm3 = new AStarAlgorithm(new int[]{7,6}, new int[]{1,0}, "maze_map_for_unit_testing_no_path.csv");
        assertArrayEquals(new int[] {6,6}, aStarAlgorithm3.tomNextMovement()); // Target Function
    }

    // Function B (CSVOutput)


    // Function B (CSVOutputForGUI)


    // Function B (MazeWithShortestPath)


    // Function C (DirectionState)


    // Function C (Character)
    @Test
    void Character(){
        Character character = new Character(); // Target Function
        assertEquals(0,character.newRow);
        assertEquals(0,character.newCol);
        assertArrayEquals(new int[]{0, 0}, character.location);
        assertEquals(0,character.lastPos);
        assertEquals(0,character.speed);
        assertFalse(character.Game_state);
    }

    @Test
    void move(){
        GameMain.mazeSize = 30;
        GameMain.maze = MazeLoader.loadMazeFromCSV("maze_map_testing.csv");
        Character character = new Character();
        character.move();
    }

    @Test
    void moveWithShortestPath(){
        AStarAlgorithm aStarAlgorithm1 = new AStarAlgorithm(new int[]{7,6}, new int[]{1,0}, "maze_map_for_unit_testing.csv");
    }
    @Test
    void IsWithinBoundary(){
        GameMain.mazeSize = 30;
        Character character = new Character();
        character.location = new int[]{5,100};
        assertTrue(character.IsWithinBoundary(character.location[0])); // Target Function
        assertFalse(character.IsWithinBoundary(character.location[1])); // Target Function
    }

    @Test
    void IsPath(){
        GameMain.maze = MazeLoader.loadMazeFromCSV("maze_map_testing.csv");
        Character character = new Character();
        assertFalse(character.IsPath(7,2)); // Target Function
        assertTrue(character.IsPath(3,10)); // Target Function
    }
    @Test
    void toIndex(){
        GameMain.mazeSize = 30;
        int[] location = new int[]{12,24};
        assertEquals(384, Character.toIndex(location)); // Target Function
    }
    @Test
    void reset(){
        Character character = new Character();
        int row = 21;
        int col = 29;
        character.location = new int[]{2,0};
        character.reset(row,col); // Target Function
        assertEquals(60,character.lastPos);
        assertArrayEquals(new int[]{21,29},character.location);
        assertEquals(0,character.newRow);
        assertEquals(0,character.newCol);
        assertFalse(character.Game_state);
    }

    // Function C (CheckEndGame)
    @Test
    void isEndGame(){
        GameMain.Tom.location = new int[]{2,0};
        CheckEndGame checkEndGame = new CheckEndGame(); // Target Function
        assertEquals(60,checkEndGame.ExitPoint);
        assertFalse(checkEndGame.isEndGame());
        GameMain.Jerry.location = new int[]{2,0};
        assertTrue(checkEndGame.isEndGame()); // Target Function
        GameMain.Tom.Game_state = false;
        GameMain.Jerry.Game_state = false;
        GameMain.Tom.location = new int[]{14,7};
        GameMain.Jerry.location = new int[]{14,7};
        assertTrue(checkEndGame.isEndGame()); // Target Function
    }

    // Function C (GameMain)


    // Function C (GameMazeGUI)


    // Function C (InfoGUI)


    // Function C (KeyBoardListener)
    @Test
    void KeyBoardListener(){
        assertEquals(GameMain.Jerry,new KeyBoardListener(GameMain.Jerry).player);
    }
    @Test
    void keyPress(){
        KeyBoardListener keyBoardListener = new KeyBoardListener(GameMain.Jerry);
        KeyEvent w = new KeyEvent(KEY_PRESSED, "","",W,false,false,false,false);
        KeyEvent a = new KeyEvent(KEY_PRESSED, "","",A,false,false,false,false);
        KeyEvent s = new KeyEvent(KEY_PRESSED, "","",S,false,false,false,false);
        KeyEvent d = new KeyEvent(KEY_PRESSED, "","",D,false,false,false,false);
        KeyEvent q = new KeyEvent(KEY_PRESSED, "","",Q,false,false,false,false);
        keyBoardListener.keyPressed(w); // Target Function
        assertEquals(-1,GameMain.Jerry.newRow);
        assertEquals(0,GameMain.Jerry.newCol);
        keyBoardListener.keyPressed(a); // Target Function
        assertEquals(0,GameMain.Jerry.newRow);
        assertEquals(-1,GameMain.Jerry.newCol);
        keyBoardListener.keyPressed(s); // Target Function
        assertEquals(1,GameMain.Jerry.newRow);
        assertEquals(0,GameMain.Jerry.newCol);
        keyBoardListener.keyPressed(d); // Target Function
        assertEquals(0,GameMain.Jerry.newRow);
        assertEquals(1,GameMain.Jerry.newCol);
        keyBoardListener.keyPressed(q); // Target Function
        assertEquals(0,GameMain.Jerry.newRow);
        assertEquals(1,GameMain.Jerry.newCol);

    }
    // Function C (MainGUI)

}