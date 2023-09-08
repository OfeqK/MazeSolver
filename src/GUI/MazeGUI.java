package GUI;

import javax.swing.*;
import java.awt.event.*;

public class MazeGUI {
    static int rows = 5;
    static int cols = 5;
    static int tile = 40; // each pixel is represented as 40px
    static int width = cols * tile;
    static int height = rows * tile;

    // very helpful video: https://www.youtube.com/watch?v=MTe9lehPPbI. VERY, VERY HELPFUL VIDEO
    public static void main(String[] args) {

        Grid grid = new Grid(tile, rows, cols);
//        System.out.println(grid);
        GridPanel gridPanel = new GridPanel(grid);

        //? My first time using a GUI in java...
        JFrame frame = new JFrame("Maze Project"); // initialize the JFrame - the gui with the title 'Maze Project'
        frame.setSize(width + tile / 2 - 2, height + tile);
        frame.setLocation(0, 0); // tbh I have no idea what this even does, but it works
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // makes it so that the user can close the window

        frame.setContentPane(gridPanel);
        frame.setVisible(true);

        // Start maze generation in a separate thread, so we can view it on the screen - I hope
        Thread mazeGenerationThread = new Thread(() -> { // lambda function - I didn't know that was even possible. Cool!
            grid.generateMaze(gridPanel, tile);
        });

        mazeGenerationThread.start();

        // getting the mouse position twice for the start and the end only if we have finished generating:
        boolean foundStart = false;
        boolean foundEnd = false;
        int[] startPos = new int[2]; // x , y - remember to divide it by the tile when using it
        int[] endPos = new int[2]; // x , y
        System.out.println("Click on the screen twice once the maze generation has been finished");
        System.out.println("The first time on the starting cell");
        System.out.println("The second one on the ending cell");
        gridPanel.addMouseListener(new MouseAdapter() {// provides empty implementation of all
            // MouseListener`s methods, allowing us to
            // override only those which interests us
            @Override //I override only one method for presentation
            public void mousePressed(MouseEvent e) {
                if (!foundStart) {
                    System.out.printf("Start Cell: (%d, %d)\n", e.getX() / tile, e.getY() / tile);
                    startPos[0] = e.getX();
                    startPos[1] = e.getY();
                } else if (!foundEnd){
                    System.out.printf("End Cell: (%d, %d)\n", e.getX() / tile, e.getY() / tile);
                    endPos[0] = e.getX();
                    endPos[1] = e.getY();
                }
            }
        });
    }
}
