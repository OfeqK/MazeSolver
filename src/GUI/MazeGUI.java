package GUI;

import javax.swing.*;
import java.util.Arrays;

public class MazeGUI {
    static int rows = 5;
    static int cols = 5;
    static int tile = 50; // each pixel is 20px
    static int width = cols * tile;
    static int height = rows * tile;

    // very helpful video: https://www.youtube.com/watch?v=MTe9lehPPbI. VERY VERY HELPFUL VIDEO
    public static void main(String[] args) {

        Grid grid = new Grid(tile, rows, cols);
        System.out.println(grid);
        GridPanel gridPanel = new GridPanel(grid);

        //? My first time using a GUI in java...
        JFrame frame = new JFrame("Maze Project"); // initialize the JFrame - the gui with the title 'Maze Project'
        frame.setSize(width + 100, height + 100);
        frame.setLocation(0, 0); // tbh i have no idea what this even does, but it works
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // makes it so that the user can close the window

        frame.setContentPane(gridPanel);
        frame.setVisible(true);

        // Start maze generation in a separate thread, so we can view it on the screen - i hope
        Thread mazeGenerationThread = new Thread(() -> { // lambda function - i didn't know that was even possible. Cool!
            grid.generateMaze(gridPanel);
        });

        mazeGenerationThread.start();
    }
}
