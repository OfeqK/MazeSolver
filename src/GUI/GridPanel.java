package GUI;

import javax.swing.*;
import java.awt.*;

public class GridPanel extends JPanel{
    Grid grid; // creating a grid

    public GridPanel(Grid grid){
        this.grid = grid;
    }

    @Override
    public void paintComponent(Graphics g){ // this kinda is the pygame while loop from my understanding
        super.paintComponent(g); // calls the superclass paint method to override every other thing shown on the screen

        int rows = grid.cells.length;
        int cols = grid.cells[0].length;

        for (int row = 0; row < rows; row++){ // drawing all the cells to the screen
            for (int col = 0; col < cols; col++){
                Cell cell = grid.getCell(row, col);

                // check if the cell is in the final path:
                if (grid.finalPath != null) {
                    if (grid.finalPath.contains(cell)) {
                        cell.isFinal = true;
                    }
                }

                if (cell == grid.currentCell) { // drawing the current cell with a different color
                    cell.drawCurrent(g);
                } else {
                    cell.draw(g);
                }


            }
        }

    }
}
