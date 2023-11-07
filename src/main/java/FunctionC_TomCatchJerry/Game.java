package FunctionC_TomCatchJerry;

import FunctionA_CreateMaze.MazeLoader;
import FunctionB_ShortestPath.AStarAlgorithm;

import java.awt.event.KeyListener;

public class Game {
    public static final int[][] maze = MazeLoader.loadMazeFromCSV("maze_map.csv");
    public static Character Tom = new Character(); // Computer
    public static Character Jerry = new Character(); // Player
    public static AStarAlgorithm obj1 = new AStarAlgorithm(Tom.getLocation(), Jerry.getLocation());


    public static void main(String[] args) {
        GameMazeGUI.makeGUI(args);
    }
}
