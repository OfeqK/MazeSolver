package GUI;

import java.io.FileWriter;
import java.util.Stack;

public class Grid {
    public Cell[][] cells;

    public Grid(int tile, int rows, int cols){
        cells = new Cell[rows][cols]; // cells[y][x] == cells[row][col]
        for (int y = 0; y < cells.length; y++){
            for (int x = 0; x < cells[0].length; x++){
                cells[y][x] = new Cell(x, y, tile, new int[]{cols, rows});
            }
        }
    }

    public Cell getCell(int row, int col){ // just an easier and more readable way to get the cell back
        return cells[row][col];
    }

    public String toString(){
        String str = "";
        for (int y = 0; y < cells.length; y++){
            for (int x = 0; x < cells[0].length; x++){
                Cell cell = this.getCell(y, x);
                str +=  "(" + cell.x + "," + cell.y + "), ";
            }
            str += "\n";
        }
        return str;
    }

    public void removeWalls(Cell curCell, Cell nextCell){
        int dx = curCell.x - nextCell.x;
        if (dx == 1){ // the current cell is to the right of the next cell
            curCell.setWall("left", false);
            nextCell.setWall("right", false);
        } if (dx == -1) {   // the current cell is to the left of the next cell
            curCell.setWall("right", false);
            nextCell.setWall("left", false);
        }


        int dy = curCell.y - nextCell.y;
        if (dy == 1){ // the current cell is below the next cell
            curCell.setWall("top", false);
            nextCell.setWall("bottom", false);
        } if (dy == -1) {   // the current cell is above the next cell
            curCell.setWall("bottom", false);
            nextCell.setWall("top", false);
        }
    }

    public void generateMaze(GridPanel gridPanel){
        Stack<Cell> stack = new Stack<>();
        boolean generatingMaze = true;
        Cell current_cell = this.getCell(0, 0); // the top left cell

        while (generatingMaze){

            current_cell.visited = true;
            current_cell.drawCurrent(gridPanel.getGraphics());

            Object nextCell = current_cell.checkNeighbors(this);
            if (nextCell instanceof Cell){ // the function did not return false which means we have a random neighbor
                ((Cell) nextCell).visited = true;
                stack.push(current_cell); // for a dead end edge case
                this.removeWalls(current_cell, (Cell) nextCell);
                current_cell = (Cell) nextCell;
            } else if (stack.size() > 0){
                current_cell = stack.pop();
            } else {
                generatingMaze = false;
            }

            gridPanel.repaint();  // repaint / update the screen according to the change in the grid

            // Sleep or add a delay if you want to control the pace of visualization
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
