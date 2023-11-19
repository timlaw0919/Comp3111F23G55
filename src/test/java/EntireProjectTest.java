import FunctionA_CreateMaze.constant.CellState;
import Main.BigMainGUI;
import javafx.application.Platform;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import org.junit.Test;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.base.NodeMatchers;
import org.testfx.matcher.control.LabeledMatchers;

import FunctionA_CreateMaze.*;
import FunctionB_ShortestPath.*;
import FunctionC_TomCatchJerry.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import FunctionC_TomCatchJerry.Character;
import javafx.scene.input.KeyEvent;
import org.testfx.util.WaitForAsyncUtils;

import static javafx.scene.input.KeyCode.*;
import static javafx.scene.input.KeyEvent.*;

public class EntireProjectTest extends ApplicationTest {

    // Big Main GUI
    @Override
    public void start(Stage stage) throws Exception {
        BigMainGUI bigMainGUI = new BigMainGUI();
        bigMainGUI.start(stage);
    }

    @Test
    public void bigMainGUI(){
        FxAssert.verifyThat("#welcomeLabel", NodeMatchers.isNotNull());
        FxAssert.verifyThat("#welcomeLabel", LabeledMatchers.hasText("Welcome to G55 Tom and Jerry Maze Game Testing Menu!"));
    }

    @Test
    public void bigMainGUIFunctionAButton() {
        FxAssert.verifyThat("Test Function A", NodeMatchers.isNotNull());
        clickOn("Test Function A", MouseButton.PRIMARY);
    }

    @Test
    public void bigMainGUIFunctionBButton() {
        FxAssert.verifyThat("Test Function B", NodeMatchers.isNotNull());
        clickOn("Test Function B", MouseButton.PRIMARY);
    }

    @Test
    public void bigMainGUIFunctionCButton() {
        FxAssert.verifyThat("Test Function C", NodeMatchers.isNotNull());
        clickOn("Test Function C", MouseButton.PRIMARY);
    }

    // Function A (CellState)//


    // Function A (Cell)
    @Test
    public void Cell(){
        Cell test = new Cell(0,0, CellState.BLOCK);
        assertEquals(0, test.row);
        assertEquals(0, test.col);
        assertEquals(CellState.BLOCK, test.value);
    }

    @Test
    public void CellEquals(){
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
    @Test
    public void Node(){
        Node node = new Node(new int[] {0,0}, null, 10, 0);  // Target Function
        assertArrayEquals(new int[] {0,0}, node.currentPosition);
        assertEquals(null, node.parent);
        assertEquals(10, node.forwardCost);
        assertEquals(0, node.backwardCost);
        assertEquals(10, node.totalCost);
    }

    // Function B (AStarAlgorithm)
    @Test
    public void AStarAlgorithm(){
        AStarAlgorithm aStarAlgorithm = new AStarAlgorithm(new int[]{0,0}, new int[]{4,4}, "maze_map_for_unit_testing.csv"); // Target Function
        int[][] maze = {{1,1,1,1,1,1,1,1}, {2,0,0,1,0,0,0,1}, {1,1,0,0,0,1,0,1}, {1,0,0,1,0,0,0,1}, {1,0,1,0,1,0,1,1}, {1,0,0,0,0,0,0,1}, {1,1,1,1,1,1,0,1}, {1,1,1,1,1,1,3,1}};
        assertArrayEquals(new int[]{0,0}, aStarAlgorithm.tomLocation);
        assertArrayEquals(new int[]{4,4}, aStarAlgorithm.jerryLocation);
        assertArrayEquals(maze, aStarAlgorithm.maze);
    }

    @Test
    public void changeLocation(){
        AStarAlgorithm aStarAlgorithm = new AStarAlgorithm(new int[]{0,0}, new int[]{4,4}, "maze_map_for_unit_testing.csv");
        assertArrayEquals(new int[]{10,20}, aStarAlgorithm.changeLocation((new int[]{10,20}), 0)); // Target Function
        assertArrayEquals(new int[]{20,10}, aStarAlgorithm.changeLocation((new int[]{20,10}), 1)); // Target Function
    }

    @Test
    public void checkExplored(){
        List<Node> nodeList = new ArrayList<>();
        nodeList.add(new Node(new int[] {0,0}, null, 10, 0));
        AStarAlgorithm aStarAlgorithm = new AStarAlgorithm(new int[]{0,0}, new int[]{4,4}, "maze_map_for_unit_testing.csv");
        assertTrue(aStarAlgorithm.checkExplored(nodeList, new int[]{0,0})); // Target Function
        assertFalse(aStarAlgorithm.checkExplored(nodeList, new int[]{1,1})); // Target Function
    }

    @Test
    public void checkValidNode(){
        AStarAlgorithm aStarAlgorithm = new AStarAlgorithm(new int[]{0,0}, new int[]{4,4}, "maze_map_for_unit_testing.csv");
        List<Node> frontier = new ArrayList<>();
        List<Node> expandedNode = new ArrayList<>();
        frontier.add(new Node(new int[] {0,0}, null, 10, 0));
        assertTrue(aStarAlgorithm.checkValidNode(expandedNode, frontier, new int[]{1,1})); // Target Function
        assertFalse(aStarAlgorithm.checkValidNode(expandedNode, frontier, new int[]{0,0})); // Target Function
    }

    @Test
    public void findNeighbor(){
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
    public void pathGeneratorByAStar(){
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
    public void tomNextMovement(){
        AStarAlgorithm aStarAlgorithm1 = new AStarAlgorithm(new int[]{7,6}, new int[]{1,0}, "maze_map_for_unit_testing.csv");
        assertArrayEquals(new int[] {6,6}, aStarAlgorithm1.tomNextMovement()); // Target Function
        AStarAlgorithm aStarAlgorithm2 = new AStarAlgorithm(new int[]{7,6}, new int[]{1,0}, "maze_map_for_unit_testing_no_path_same.csv");
        assertArrayEquals(new int[] {7,6}, aStarAlgorithm2.tomNextMovement()); // Target Function
        AStarAlgorithm aStarAlgorithm3 = new AStarAlgorithm(new int[]{7,6}, new int[]{1,0}, "maze_map_for_unit_testing_no_path.csv");
        assertArrayEquals(new int[] {6,6}, aStarAlgorithm3.tomNextMovement()); // Target Function
    }

    // Function B (CSVOutput)
    @Test
    public void CSVOutput() throws IOException{
        AStarAlgorithm aStarAlgorithm = new AStarAlgorithm(new int[] {7,6}, new int[] {1,0}, "maze_map_for_unit_testing.csv");
        FunctionB_ShortestPath.CSVOutput.outputCSVFile(aStarAlgorithm.pathGeneratorByAStar(), "path_coordinates.csv");

        BufferedReader reader1 = new BufferedReader(new FileReader("path_coordinates.csv"));
        BufferedReader reader2 = new BufferedReader(new FileReader("maze_map_testing_CSVOutput_expected.csv"));
        String line1 = reader1.readLine();
        String line2 = reader2.readLine();
        boolean check = true;
        while (line1 != null || line2 != null) {
            if(line1 == null || line2 == null) {
                check = false;
                break;
            }
            else if(! line1.equalsIgnoreCase(line2)) {
                check = false;
                break;
            }
            line1 = reader1.readLine();
            line2 = reader2.readLine();
        }
        assertTrue(check);
    }

    // Function B (CSVOutputForGUI)
    @Test
    public void CSVOutputForGUI() throws IOException{
        AStarAlgorithm aStarAlgorithm = new AStarAlgorithm(new int[] {7,6}, new int[] {1,0}, "maze_map_for_unit_testing.csv");
        CSVOutputForGUI.outputCSVFile(aStarAlgorithm.pathGeneratorByAStar(), "maze_map_with_path.csv", "maze_map_for_unit_testing.csv"); // Target Function

        BufferedReader reader1 = new BufferedReader(new FileReader("maze_map_with_path.csv"));
        BufferedReader reader2 = new BufferedReader(new FileReader("maze_map_testing_CSVOutputForGUI_expected.csv"));
        String line1 = reader1.readLine();
        String line2 = reader2.readLine();
        boolean check = true;
        while (line1 != null || line2 != null) {
            if(line1 == null || line2 == null) {
                check = false;
                break;
            }
            else if(! line1.equalsIgnoreCase(line2)) {
                check = false;
                break;
            }
            line1 = reader1.readLine();
            line2 = reader2.readLine();
        }
        assertTrue(check);
    }

    // Function B (MazeWithShortestPathGUI)
    @Test
    public void MazeWithShortestPathGUIButton(){
        FxAssert.verifyThat("Test Function B", NodeMatchers.isNotNull());
        clickOn("Test Function B", MouseButton.PRIMARY);
        FxAssert.verifyThat("Back to Testing Menu", NodeMatchers.isNotNull()); // Target Function
        clickOn("Back to Testing Menu", MouseButton.PRIMARY); // Target Function
    }


    // Function C (Character)
    @Test
    public void Character(){
        Character character = new Character(); // Target Function
        assertEquals(0,character.newRow);
        assertEquals(0,character.newCol);
        assertArrayEquals(new int[]{0, 0}, character.location);
        assertEquals(0,character.lastPos);
        assertEquals(0,character.speed);
        assertFalse(character.Game_state);
    }

    @Test
    public void move(){
        GameMain.mazeSize = 30;
        GameMain.maze = MazeLoader.loadMazeFromCSV("maze_map_testing.csv");
        GameMain.Jerry.location = new int[]{21,29};
        KeyEvent a = new KeyEvent(KEY_PRESSED, "","",A,false,false,false,false);
        new KeyBoardListener(GameMain.Jerry).keyPressed(a);
        GameMain.Jerry.move(); // Target Function
        assertArrayEquals(new int[]{21,28},GameMain.Jerry.location);
        assertEquals(659,GameMain.Jerry.lastPos);
    }

    @Test
    public void moveWithShortestPath() {
        GameMain.mazeSize = 30;
        GameMain.maze = MazeLoader.loadMazeFromCSV("maze_map_testing.csv");
        GameMain.Tom.location = new int[]{2, 1};
        GameMain.Jerry.location = new int[]{20, 20};
        GameMain.shortestPath = new AStarAlgorithm(GameMain.Tom.location, GameMain.Jerry.location, "maze_map_testing.csv");
        GameMain.Tom.MoveWithShortestPath();
        assertArrayEquals(new int[]{2, 2}, GameMain.Tom.location);
        assertEquals(61, GameMain.Tom.lastPos);
    }

    @Test
    public void IsWithinBoundary(){
        GameMain.mazeSize = 30;
        Character character = new Character();
        character.location = new int[]{5,100};
        assertTrue(character.IsWithinBoundary(character.location[0])); // Target Function
        assertFalse(character.IsWithinBoundary(character.location[1])); // Target Function
    }

    @Test
    public void IsPath(){
        GameMain.maze = MazeLoader.loadMazeFromCSV("maze_map_testing.csv");
        Character character = new Character();
        assertFalse(character.IsPath(7,2)); // Target Function
        assertTrue(character.IsPath(3,10)); // Target Function
    }
    @Test
    public void toIndex(){
        GameMain.mazeSize = 30;
        int[] location = new int[]{12,24};
        assertEquals(384, Character.toIndex(location)); // Target Function
    }
    @Test
    public void reset(){
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
    public void isEndGame(){
        GameMain.mazeSize = 30;
        GameMain.maze = MazeLoader.loadMazeFromCSV("maze_map_testing.csv");
        GameMain.Jerry.Game_state = false;
        GameMain.Tom.Game_state = false;
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
    @Test
    public void newMaze(){
        int[] maze_size = new int[]{20,25,30,35,40};
        for (int size: maze_size){
            GameMain.mazeSize = size;
            GameMain.maze = GameMain.newMaze();
            assertEquals(size, GameMain.maze.length);
        }
    }
    // Function C (GameMazeGUI)
    @Test
    public void setGridPane(){
        GameMain.mazeSize = 30;
        GameMain.maze = MazeLoader.loadMazeFromCSV("maze_map_testing.csv");
        GameMazeGUI gameMazeGUI = new GameMazeGUI();
        gameMazeGUI.SetGridPane();  // Target Function
        assertEquals(GameMain.mazeSize*GameMain.mazeSize,gameMazeGUI.cells.size());
        assertArrayEquals(new int[]{21,29},GameMain.Jerry.location);
        assertEquals(659,gameMazeGUI.entryIndex);
        assertEquals(gameMazeGUI.JerryJerry,gameMazeGUI.cells.get(659).getFill());
        assertArrayEquals(new int[]{2,0},GameMain.Tom.location);
        assertEquals(60,gameMazeGUI.exitIndex);
        assertEquals(gameMazeGUI.TomTom,gameMazeGUI.cells.get(60).getFill());
        assertEquals(gameMazeGUI.block,gameMazeGUI.cells.get(0).getFill());
        assertEquals(Color.WHITE,gameMazeGUI.cells.get(61).getFill());
    }

    @Test
    public void updateGridPane(){
        GameMain.mazeSize = 30;
        GameMain.maze = MazeLoader.loadMazeFromCSV("maze_map_testing.csv");
        GameMazeGUI gameMazeGUI = new GameMazeGUI();
        gameMazeGUI.SetGridPane();
        GameMain.Jerry.lastPos = 0;
        GameMain.Jerry.location = new int[]{21,28};
        GameMain.Tom.location = new int[]{2,1};
        gameMazeGUI.updatedGridPane(GameMain.Jerry,gameMazeGUI.JerryJerry); // Target function
        assertEquals(gameMazeGUI.block,gameMazeGUI.cells.get(0).getFill());
        GameMain.Jerry.lastPos = 659;
        gameMazeGUI.updatedGridPane(GameMain.Jerry,gameMazeGUI.JerryJerry); // Target function
        assertEquals(Color.web("#F1CD85"),gameMazeGUI.cells.get(659).getFill());
        GameMain.Jerry.lastPos = 628;
        gameMazeGUI.updatedGridPane(GameMain.Jerry,gameMazeGUI.JerryJerry); // Target function
        assertEquals(Color.WHITE,gameMazeGUI.cells.get(628).getFill());
        GameMain.Tom.lastPos = 60;
        gameMazeGUI.updatedGridPane(GameMain.Tom,gameMazeGUI.TomTom); // Target function
        assertEquals(Color.web("#808990"),gameMazeGUI.cells.get(60).getFill());
        assertEquals(gameMazeGUI.JerryJerry,gameMazeGUI.cells.get(658).getFill());
        assertEquals(gameMazeGUI.TomTom,gameMazeGUI.cells.get(61).getFill());
    }

    @Test
    public void GameMazeGUI_start(){
        GameMazeGUI gameMazeGUI = new GameMazeGUI();
        Platform.runLater(() -> {
            Stage stage = new Stage();
            gameMazeGUI.start(stage); // Target function
            assertNotNull(stage);
        });
    }

    @Test
    public void testHomeButton(){
        GameMazeGUI gameMazeGUI = new GameMazeGUI();
        Platform.runLater(() -> {
            Stage stage = new Stage();
            gameMazeGUI.start(stage);
        });
        WaitForAsyncUtils.waitForFxEvents();
        FxAssert.verifyThat("Home", NodeMatchers.isNotNull());
        clickOn("Home", MouseButton.PRIMARY); // Target Function
    }

    @Test
    public void testSceneOnKeyPress(){
        GameMain.mazeSize = 30;
        GameMain.maze = MazeLoader.loadMazeFromCSV("maze_map_testing.csv");
        GameMazeGUI gameMazeGUI = new GameMazeGUI();
        Platform.runLater(() -> {
            Stage stage = new Stage();
            gameMazeGUI.start(stage);
        });
        WaitForAsyncUtils.waitForFxEvents();
        press(A); // Target Function
        assertArrayEquals(new int[]{21,28},GameMain.Jerry.location);
    }

    @Test
    public void testRestartButton() throws InterruptedException {
        GameMain.mazeSize = 30;
        GameMain.maze = MazeLoader.loadMazeFromCSV("maze_map_testing.csv");
        GameMain.Jerry.Game_state = false;
        GameMain.Tom.Game_state = false;
        GameMazeGUI gameMazeGUI = new GameMazeGUI();
        Platform.runLater(() -> {
            Stage stage = new Stage();
            gameMazeGUI.start(stage);
        });
        WaitForAsyncUtils.waitForFxEvents();
        press(A);
        sleep(1600);
        assertTrue(GameMain.Tom.Game_state);
        FxAssert.verifyThat("Restart", NodeMatchers.isNotNull());
        clickOn("Restart", MouseButton.PRIMARY); // Target Function
    }

    // Function C (InfoGUI)
    @Test
    public void InfoGUI_start(){
        InfoGUI infoGUI = new InfoGUI();
        Platform.runLater(() -> {
            try {
                Stage stage = new Stage();
                infoGUI.start(stage); // Target function
                assertNotNull(stage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Test
    public void InfoGUI_OKButton(){
        InfoGUI infoGUI = new InfoGUI();
        Platform.runLater(() -> {
            try {
                Stage stage = new Stage();
                infoGUI.start(stage);
                assertNotNull(stage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        WaitForAsyncUtils.waitForFxEvents();

        FxAssert.verifyThat("OK!", NodeMatchers.isNotNull());
        clickOn("OK!", MouseButton.PRIMARY); // Target Function
    }


    // Function C (KeyBoardListener)
    @Test
    public void KeyBoardListener(){
        KeyBoardListener keyBoardListener = new KeyBoardListener(GameMain.Jerry);
        assertEquals(GameMain.Jerry,keyBoardListener.player);
    }
    @Test
    public void keyPress(){
        KeyBoardListener keyBoardListener = new KeyBoardListener(GameMain.Jerry);
        KeyEvent w = new KeyEvent(KEY_PRESSED, "","",W,false,false,false,false);
        KeyEvent a = new KeyEvent(KEY_PRESSED, "","",A,false,false,false,false);
        KeyEvent s = new KeyEvent(KEY_PRESSED, "","",S,false,false,false,false);
        KeyEvent d = new KeyEvent(KEY_PRESSED, "","",D,false,false,false,false);
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
    }
    // Function C (MainGUI)
    @Test
    public void testEnumSpeedConstructor(){
        MainGUI.Speed fast = MainGUI.Speed.FAST;
        MainGUI.Speed moderate = MainGUI.Speed.MODERATE;
        MainGUI.Speed slow = MainGUI.Speed.SLOW;
        assertEquals(150,fast.value);
        assertEquals(200,moderate.value);
        assertEquals(250,slow.value);
    }

    @Test
    public void MainGUI_start(){
        MainGUI mainGUI = new MainGUI();
        Platform.runLater(() -> {
            try {
                Stage stage = new Stage();
                mainGUI.start(stage); // Target function
                assertNotNull(stage);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Test
    public void testInfoButton(){
        MainGUI mainGUI = new MainGUI();
        Platform.runLater(() -> {
            try {
                Stage stage = new Stage();
                mainGUI.start(stage);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        WaitForAsyncUtils.waitForFxEvents();

        FxAssert.verifyThat("?", NodeMatchers.isNotNull());
        clickOn("?", MouseButton.PRIMARY); // Target Function
    }

    @Test
    public void testBackMenuButton(){
        MainGUI mainGUI = new MainGUI();
        Platform.runLater(() -> {
            try {
                Stage stage = new Stage();
                mainGUI.start(stage);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        WaitForAsyncUtils.waitForFxEvents();

        FxAssert.verifyThat("Back to Testing Menu", NodeMatchers.isNotNull());
        clickOn("Back to Testing Menu", MouseButton.PRIMARY); // Target Function
    }

    @Test
    public void testStartButton(){
        MainGUI mainGUI = new MainGUI();
        Platform.runLater(() -> {
            try {
                Stage stage = new Stage();
                mainGUI.start(stage);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        WaitForAsyncUtils.waitForFxEvents();

        FxAssert.verifyThat("Start", NodeMatchers.isNotNull());
        clickOn("Start", MouseButton.PRIMARY); // Target Function
    }

}