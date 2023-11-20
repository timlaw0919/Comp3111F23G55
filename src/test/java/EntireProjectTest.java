import FunctionA_CreateMaze.constant.CellState;
import Main.BigMainGUI;
import javafx.application.Platform;

import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

//import org.controlsfx.tools.Platform;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.RepeatedTest;
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
import java.util.*;

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
    public void bigMainGUIButton() {
        FxAssert.verifyThat("#welcomeLabel", NodeMatchers.isNotNull());
        FxAssert.verifyThat("#welcomeLabel", LabeledMatchers.hasText("Welcome to G55 Tom and Jerry Maze Game Testing Menu!"));
        FxAssert.verifyThat("Test Function A", NodeMatchers.isNotNull());
        clickOn("Test Function A", MouseButton.PRIMARY); //Target Function
        FxAssert.verifyThat("Back to Testing Menu", NodeMatchers.isNotNull());
        clickOn("Back to Testing Menu", MouseButton.PRIMARY);
        FxAssert.verifyThat("Test Function B", NodeMatchers.isNotNull());
        clickOn("Test Function B", MouseButton.PRIMARY);  //Target Function
        FxAssert.verifyThat("Back to Testing Menu", NodeMatchers.isNotNull());
        clickOn("Back to Testing Menu", MouseButton.PRIMARY);
        FxAssert.verifyThat("Test Function C", NodeMatchers.isNotNull());
        clickOn("Test Function C", MouseButton.PRIMARY);  //Target Function
    }


    // Function A (CellState)
    @Test
    public void CellStateEnumTest() {
        // Test the enum values and their assigned integer values
        // Target Function
        assertEquals(0, CellState.PATH.ordinal());
        assertEquals(1, CellState.BLOCK.ordinal());
        assertEquals(2, CellState.ENTRY.ordinal());
        assertEquals(3, CellState.EXIT.ordinal());
    }


    // Function A (Cell)
    @Test
    public void CellTest(){
        // test Cell constructor
        Cell test = new Cell(0,0, CellState.BLOCK);     // Target Function
        assertEquals(0, test.row);
        assertEquals(0, test.col);
        assertEquals(CellState.BLOCK, test.value);
    }

    @Test
    public void CellEqualsTest(){
        // test two cell equal base on same row and col
        Cell cell1 = new Cell(0,0, CellState.BLOCK);
        Cell cell2 = new Cell(0,0, CellState.PATH);
        Cell cell3 = new Cell(0,1,CellState.BLOCK);

        // Target Function
        assertTrue(cell1.equals(cell2));            // valid (Cell with same row/col)
        assertFalse(cell1.equals(cell3));           // invalid (Cell with different row/col)
        assertFalse(cell1.equals(new Object()));    // invalid (not Cell)
    }

    // Function A (CSVOutput)
    @Test
    public void CSVOutputTestA() throws IOException{
        // test have success CSVOutput - readable file
        MazeGenerator mazeGenerator1 = new MazeGenerator(30,30);
        mazeGenerator1.generateMaze();
        FunctionA_CreateMaze.CSVOutput.outputCSVFile(mazeGenerator1.getMaze(),"maze_map.csv");      // Target Function
        BufferedReader reader = new BufferedReader(new FileReader("maze_map.csv"));
        String line = reader.readLine();
        int countEntry = 0;
        int countExit = 0;
        while(line != null){
            if(line.contains("2")){
                countEntry++;
            } else if (line.contains("3")) {
                countExit++;
            }
            line = reader.readLine();
        }
        assertEquals(1,countEntry);
        assertEquals(1,countExit);
    }


    // Function A (MazeGenerator)
    @Test
    public void MazeGenerator(){
        // test mazeGenerator constructor
        int dim = 30;
        MazeGenerator mg = new MazeGenerator(dim,dim);      // Target Function
        Cell[][] test = mg.getMaze();
        assertEquals(30,test.length);
        assertEquals(30,test[0].length);
    }

    private MazeGenerator mazeGenerator;
    @Before
    public void setup() {
        int dim = 30;
        mazeGenerator = new MazeGenerator(dim, dim);
    }

    @Test
    public void initializeMazeTest(){
        // test maze initialize with all BLOCKS and one ENTRY
        mazeGenerator.initializeMaze();     // Target Function
        int countEntry = 0;
        Cell[][] maze  = mazeGenerator.getMaze();
        for(int i = 0; i<maze.length; ++i){
            for(int j = 0; j<maze[0].length; ++j){
                if(maze[i][j].value==CellState.ENTRY){
                    countEntry++;
                }
                else{
                    assertEquals(CellState.BLOCK,maze[i][j].value);
                }
            }
        }
        assertEquals(1,countEntry);
    }

    @Test
    public void EntryPointGeneratorTest(){
        // test ENTRY is generated on the edge
        mazeGenerator.initializeMaze();
        mazeGenerator.EntryPointGenerator();    // Target Function
        assertNotNull(mazeGenerator.EntryPoint);
        boolean test  = mazeGenerator.cellOnEdge(mazeGenerator.EntryPoint);
        assertTrue(test);

    }

    public boolean checkMultiplePaths(MazeGenerator mazeGenerator) {
        Set<Cell> visited = new HashSet<>();
        int[] pathCount = { 0 }; // Use an array to hold the count and pass it as reference
        explorePaths(mazeGenerator,mazeGenerator.EntryPoint, visited, pathCount);
        return pathCount[0]>1; // Return the updated count
    }

    private void explorePaths(MazeGenerator mazeGenerator, Cell currentCell, Set<Cell> visited, int[] pathCount) {
        visited.add(currentCell);
        if (visited.size() >= 100){
            visited.remove(currentCell);
            return;
        }

        if (currentCell.value == CellState.EXIT) {
            pathCount[0]++; // Increment the count
            visited.remove(currentCell);
            return;
        }

        if(pathCount[0]<=1) {
            List<Cell> neighbors = getNeighbors(mazeGenerator, currentCell);
            if (neighbors.isEmpty()){
                System.out.println(currentCell.row+ " " + currentCell.col);
            }
            for (Cell neighbor : neighbors) {
                if (!visited.contains(neighbor)) {           // stop recursion if there are multiple path
                    explorePaths(mazeGenerator, neighbor, visited, pathCount);
                }
            }
        }

        visited.remove(currentCell);
    }

    private List<Cell> getNeighbors(MazeGenerator mazeGenerator, Cell currentCell){
        List<Cell> neighbors = new ArrayList<>();

        int row = currentCell.row;
        int col = currentCell.col;

        Cell[][] maze = mazeGenerator.getMaze();

        // Add neighboring cells (up, down, left, right)
        if (row > 0){
            if (maze[row - 1][col].value!=CellState.BLOCK)
                neighbors.add(maze[row - 1][col]);
        }
        if (row < maze.length - 1){
            if (maze[row + 1][col].value!=CellState.BLOCK)
                neighbors.add(maze[row + 1][col]);
        }
        if (col > 0){
            if (maze[row][col-1].value!=CellState.BLOCK)
                neighbors.add(maze[row][col - 1]);
        }
        if (col < maze[0].length - 1){
            if (maze[row][col+1].value!=CellState.BLOCK)
                neighbors.add(maze[row][col + 1]);
        }

        return neighbors;

    }

    @Test
    @RepeatedTest(50)
    public void generateMazeTest(){
        // test EXIT is generated on the edge + the generated 30*30 maze have multiple paths
        MazeGenerator mazeGenerator1 = new MazeGenerator(30,30);
        mazeGenerator1.generateMaze();  // Target Function
        assertNotNull(mazeGenerator1.ExitPoint);
        boolean test  = mazeGenerator1.cellOnEdge(mazeGenerator1.ExitPoint);
        assertTrue(test);

        assertTrue(checkMultiplePaths(mazeGenerator1));
    }

    @Test
    public void checkValidNeighborsTest() throws Exception {
        // test valid and invalid neighbors
        // Create a sample maze
        MazeGenerator mazeGenerator = new MazeGenerator(5, 5);
        mazeGenerator.initializeMaze();
        Cell[][] maze = mazeGenerator.getMaze();
        maze[1][1].value = CellState.PATH;
        maze[1][2].value = CellState.PATH;
        maze[1][3].value = CellState.PATH;
        maze[2][1].value = CellState.PATH;

        // Target Function
        // Test valid neighbors
        assertTrue(mazeGenerator.checkValidNeighbors(maze[1][1])); // (1, 1) should be a valid neighbor
        assertTrue(mazeGenerator.checkValidNeighbors(maze[2][1])); // (2, 1) should be a valid neighbor
        assertTrue(mazeGenerator.checkValidNeighbors(maze[1][3])); // (1, 3) should be a valid neighbor

        // Test invalid neighbors
        assertFalse(mazeGenerator.checkValidNeighbors(mazeGenerator.EntryPoint)); // EntryPoint should be an invalid neighbor (EntryPoint)
        assertFalse(mazeGenerator.checkValidNeighbors(maze[2][2])); // (1, 1) should be an invalid neighbor (numNeighboringZeros>=4)
        assertFalse(mazeGenerator.checkValidNeighbors(maze[0][0])); // (0, 0) should be an invalid neighbor (on Edge)

        mazeGenerator.generateMaze();
        assertFalse(mazeGenerator.checkValidNeighbors(mazeGenerator.ExitPoint)); // EntryPoint should be an invalid neighbor (visited)

    }

    @Test
    public void getValidNeighborsTest(){
        // test valid returned neighbors list
        // Create a sample maze
        MazeGenerator mazeGenerator1 = new MazeGenerator(5, 5);
        mazeGenerator1.initializeMaze();
        Cell[][] maze = mazeGenerator1.getMaze();
        for(int i = 0; i< maze.length; ++i){
            for(int j = 0; j< maze[0].length; ++j){
                maze[i][j].value = CellState.BLOCK;
            }
        }
        maze[1][1].value = CellState.PATH;
        maze[1][2].value = CellState.PATH;
        maze[1][3].value = CellState.PATH;
        maze[2][1].value = CellState.PATH;


        // Test Cell at (1, 1)
        Cell cell11 = maze[1][1];
        // Case 1: including newly found EXIT
        if(mazeGenerator1.EntryPoint.row==4 || mazeGenerator1.EntryPoint.col==4) {
            List<Cell> neighbors11 = mazeGenerator1.getValidNeighbors(cell11);  // Target Function
            assertEquals(1, neighbors11.size());
            assertTrue(containsCell(neighbors11, mazeGenerator1.ExitPoint.row, mazeGenerator1.ExitPoint.col));
        }
        // Case 2: not including newly found EXIT
        else{
            List<Cell> neighbors11 = mazeGenerator1.getValidNeighbors(cell11);  //Target Function
            assertEquals(2, neighbors11.size());
            assertTrue(containsCell(neighbors11, 1, 2));
            assertTrue(containsCell(neighbors11, 2, 1));
        }

    }

    private boolean containsCell(List<Cell> cells, int row, int col) {
        for (Cell cell : cells) {
            if (cell.row == row && cell.col == col) {
                return true;
            }
        }
        return false;
    }

    @Test
    public void cellOnGridTest(){
        // test a cell on the grid/maze
        // Target Function
        assertTrue(mazeGenerator.cellOnGrid(1,1));      // valid (on Grid)
        assertFalse(mazeGenerator.cellOnGrid(30,0));    // invalid (out of bound)
    }

    @Test
    public void cellOnEdgeTest(){
        // test a cell on edge
        mazeGenerator.initializeMaze();
        Cell[][] maze = mazeGenerator.getMaze();
        // Target Function
        assertTrue(mazeGenerator.cellOnEdge(maze[0][4]));       // valid (on Edge)
        assertFalse(mazeGenerator.cellOnEdge(maze[1][1]));      // invalid (inside)
    }

    @Test
    public void cellOnCornerTest(){
        // test a cell on corner
        mazeGenerator.initializeMaze();
        Cell[][] maze = mazeGenerator.getMaze();
        // Target Function
        assertTrue(mazeGenerator.cellOnCorner(maze[0][0]));     // valid (on Corner)
        assertFalse(mazeGenerator.cellOnCorner(maze[0][1]));    // invalid (on Edge but not Corner)
        assertFalse(mazeGenerator.cellOnCorner(maze[8][1]));    // invalid (inside)
    }

    @Test
    public void checkIfEntryPointTest(){
        // test ENTRY
        mazeGenerator.initializeMaze();
        Cell[][] maze = mazeGenerator.getMaze();
        // Target Function
        assertTrue(mazeGenerator.checkIfEntryPoint(mazeGenerator.EntryPoint));      // valid (ENTRY)
        assertFalse(mazeGenerator.checkIfEntryPoint(maze[9][9]));                   // invalid (inside + ENTRY must on Edge)
    }

    @Test
    public void checkIfExitPointTest(){
        // test if EXIT is (not) found
        // Test if EXIT is not found
        mazeGenerator.initializeMaze();
        Cell[][] maze = mazeGenerator.getMaze();
        if(mazeGenerator.EntryPoint.row==0){
            // Target Function
            // test cell on opposite edge of ENTRY
            assertTrue(mazeGenerator.checkIfExitPoint(maze[maze.length-1][3]));
            // test cell on other 3 edges
            assertFalse(mazeGenerator.checkIfExitPoint(maze[0][3]));
            assertFalse(mazeGenerator.checkIfExitPoint(maze[7][maze.length-1]));
            assertFalse(mazeGenerator.checkIfExitPoint(maze[7][0]));
            // test cell of interior
            assertFalse(mazeGenerator.checkIfExitPoint(maze[3][9]));
        }
        else if (mazeGenerator.EntryPoint.row==maze.length-1) {
            // Target Function
            // test cell on opposite edge of ENTRY
            assertTrue(mazeGenerator.checkIfExitPoint(maze[0][3]));
            // test cell on other 3 edges
            assertFalse(mazeGenerator.checkIfExitPoint(maze[maze.length-1][3]));
            assertFalse(mazeGenerator.checkIfExitPoint(maze[7][maze.length-1]));
            assertFalse(mazeGenerator.checkIfExitPoint(maze[7][0]));
            // test cell of interior
            assertFalse(mazeGenerator.checkIfExitPoint(maze[3][9]));
        }
        else if (mazeGenerator.EntryPoint.col==0) {
            // Target Function
            // test cell on opposite edge of ENTRY
            assertTrue(mazeGenerator.checkIfExitPoint(maze[7][maze.length-1]));
            // test cell on other 3 edges
            assertFalse(mazeGenerator.checkIfExitPoint(maze[maze.length-1][3]));
            assertFalse(mazeGenerator.checkIfExitPoint(maze[0][3]));
            assertFalse(mazeGenerator.checkIfExitPoint(maze[7][0]));
            // test cell of interior
            assertFalse(mazeGenerator.checkIfExitPoint(maze[3][9]));
        }
        else if (mazeGenerator.EntryPoint.col==maze.length-1) {
            // Target Function
            // test cell on opposite edge of ENTRY
            assertTrue(mazeGenerator.checkIfExitPoint(maze[7][0]));
            // test cell on other 3 edges
            assertFalse(mazeGenerator.checkIfExitPoint(maze[maze.length-1][3]));
            assertFalse(mazeGenerator.checkIfExitPoint(maze[0][3]));
            assertFalse(mazeGenerator.checkIfExitPoint(maze[7][maze.length-1]));
            // test cell of interior
            assertFalse(mazeGenerator.checkIfExitPoint(maze[3][9]));
        }

        // Test if EXIT is found
        mazeGenerator.generateMaze();
        // Target Function
        assertTrue(mazeGenerator.checkIfExitPoint(mazeGenerator.ExitPoint));
        assertFalse(mazeGenerator.checkIfExitPoint(maze[3][9]));

    }

    @Test
    public void cellOnOppositeEdgeTest(){
        // test two cells on opposite edge
        mazeGenerator.initializeMaze();
        Cell[][] maze = mazeGenerator.getMaze();
        //Target Function
        assertTrue(mazeGenerator.CellOnOppositeEdge(maze[0][2],maze[maze.length-1][5]));                // valid (up and bottom)
        assertTrue(mazeGenerator.CellOnOppositeEdge(maze[2][0],maze[5][maze.length-1]));                // valid (left and right)

        assertFalse(mazeGenerator.CellOnOppositeEdge(maze[maze.length-1][2],maze[maze.length-1][5]));   // invalid (same edge)
        assertFalse(mazeGenerator.CellOnOppositeEdge(maze[0][2],maze[5][0]));                           // invalid (up and left)
        assertFalse(mazeGenerator.CellOnOppositeEdge(maze[0][2],maze[5][maze.length-1]));               // invalid (up and right)
        assertFalse(mazeGenerator.CellOnOppositeEdge(maze[maze.length-1][2],maze[5][0]));               // invalid (bottom and left)
        assertFalse(mazeGenerator.CellOnOppositeEdge(maze[maze.length-1][2],maze[5][maze.length-1]));   // invalid (bottom and right)
        assertFalse(mazeGenerator.CellOnOppositeEdge(maze[3][3],maze[1][4]));                           // invalid (inside and inside)
        assertFalse(mazeGenerator.CellOnOppositeEdge(maze[0][3],maze[1][4]));                           // invalid (edge and inside)
    }

    @Test
    public void getMaze(){
        // test getMaze before/after initializing maze
        // Test before initialize maze (all null pointer)
        Cell[][] maze1 = mazeGenerator.getMaze();   //Target Function
        for(int i = 0; i<maze1.length; ++i){
            for(int j = 0; j<maze1.length; ++j){
                assertNull(maze1[i][j]);
            }
        }

        // Test after initialize maze (all Cell pointer)
        mazeGenerator.initializeMaze();
        Cell[][] maze2 = mazeGenerator.getMaze();
        for(int i = 0; i<maze1.length; ++i){
            for(int j = 0; j<maze1.length; ++j){
                assertNotNull(maze1[i][j]);
            }
        }
    }

    // Function A (MazeGUI)
    @Test
    public void testMazeGUI() {
        FxAssert.verifyThat("Test Function A", NodeMatchers.isNotNull());
        clickOn("Test Function A", MouseButton.PRIMARY);
        FxAssert.verifyThat("Back to Testing Menu", NodeMatchers.isNotNull()); // Target Function
        clickOn("Back to Testing Menu", MouseButton.PRIMARY); // Target Function
    }

    // Function A (MazeLoader)
    @Test
    public void loadMazefromCSVTest(){
        // test maze size + all elements have same value as Cell[][]
        // setup
        MazeGenerator mazeGenerator1 = new MazeGenerator(30,30);
        mazeGenerator1.generateMaze();
        FunctionA_CreateMaze.CSVOutput.outputCSVFile(mazeGenerator1.getMaze(),"maze_map.csv");
        Cell[][] cellmaze = mazeGenerator1.getMaze();

        int[][] maze = MazeLoader.loadMazeFromCSV("maze_map.csv");  // Target Function
        assertEquals(30,maze.length);
        assertEquals(30,maze[0].length);

        for(int i = 0; i<maze.length; ++i){
            for(int j = 0; j< maze[0].length; ++j){
                assertEquals(cellmaze[i][j].value.ordinal(),maze[i][j]);
            }
        }

    }

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
            else if(! line1.equals(line2)) {
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
            else if(! line1.equals(line2)) {
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
        GameMain.Jerry = new Character();
        GameMain.Tom = new Character();
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
        GameMain.Jerry = new Character();
        GameMain.Tom = new Character();
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
        GameMain.Jerry = new Character();
        GameMain.Tom = new Character();
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
        GameMain.Tom = new Character();
        GameMain.Jerry = new Character();
        GameMain.Jerry.speed = 200;
        GameMazeGUI gameMazeGUI = new GameMazeGUI();
        Platform.runLater(() -> {
            Stage stage = new Stage();
            gameMazeGUI.start(stage);
        });
        WaitForAsyncUtils.waitForFxEvents();
        press(A); // Target Function
        sleep(100);
        assertArrayEquals(new int[]{21,28},GameMain.Jerry.location);
    }

    @Test
    public void testRestartButton() {
        GameMain.mazeSize = 30;
        GameMain.maze = MazeLoader.loadMazeFromCSV("maze_map_testing.csv");
        GameMain.Jerry = new Character();
        GameMain.Tom = new Character();
        GameMazeGUI gameMazeGUI = new GameMazeGUI();
        Platform.runLater(() -> {
            Stage stage = new Stage();
            gameMazeGUI.start(stage);
        });
        WaitForAsyncUtils.waitForFxEvents();
        GameMain.Jerry.Game_state = true;
        sleep(100);
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
        assertEquals(150,fast.value);   // Target Function
        assertEquals(200,moderate.value);   // Target Function
        assertEquals(250,slow.value);   // Target Function
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