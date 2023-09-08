package GUI;

import javax.swing.*;

public class MazeGUI {
    static int rows = 20;
    static int cols = 20;
    static int tile = 40; // each pixel is represented as 40px
    static int width = cols * tile;
    static int height = rows * tile;

    // very helpful video: https://www.youtube.com/watch?v=MTe9lehPPbI. VERY VERY HELPFUL VIDEO
    public static void main(String[] args) {

        Grid grid = new Grid(tile, rows, cols);
//        System.out.println(grid);
        GridPanel gridPanel = new GridPanel(grid);

        //? My first time using a GUI in java...
        JFrame frame = new JFrame("Maze Project"); // initialize the JFrame - the gui with the title 'Maze Project'
        frame.setSize(width + tile / 2, height + tile);
        frame.setLocation(0, 0); // tbh i have no idea what this even does, but it works
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // makes it so that the user can close the window

        frame.setContentPane(gridPanel);
        frame.setVisible(true);

        // Start maze generation in a separate thread, so we can view it on the screen - i hope
        Thread mazeGenerationThread = new Thread(() -> { // lambda function - i didn't know that was even possible. Cool!
            grid.generateMaze(gridPanel, tile);
            System.out.println("finished!");
        });

        mazeGenerationThread.start();
    }
}
