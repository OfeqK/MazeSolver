package GUI;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.Stack;

public class Grid {
    public Cell[][] cells;
    public Cell currentCell; // making it accessible to other classes

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
                str += ("(" + cell.x + "," + cell.y + "), ");
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

    public void generateMaze(GridPanel gridPanel, int delayInMillis){
        Stack<Cell> stack = new Stack<>();
        boolean generatingMaze = true;
        currentCell = this.getCell(0, 0); // the top left cell
        System.out.println("Generating...");

        while (generatingMaze){
            gridPanel.repaint();  // repaint / update the screen according to the change in the grid

            currentCell.visited = true;

            Object nextCell = currentCell.checkNeighbors(this);
            if (nextCell instanceof Cell){ // the function did not return false which means we have a random neighbor
                ((Cell) nextCell).visited = true;
                stack.push(currentCell); // for a dead end edge case
                this.removeWalls(currentCell, (Cell) nextCell);
                currentCell = (Cell) nextCell;
            } else if (stack.size() > 0){
                currentCell = stack.pop();
            } else {
                generatingMaze = false;
            }

            // Sleep or add a delay if you want to control the pace of visualization
            try {
                Thread.sleep(delayInMillis);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Finished generating!\n");
        int tile = currentCell.tile;
        solveMaze(gridPanel, delayInMillis, tile);
    }

    public void solveMaze(GridPanel gridPanel, int delayInMillis, int tile){
        int[][] startEndPos = getStartEndPos(gridPanel, tile);
        int[] startPos = startEndPos[0]; // x , y = col, row
        int[] endPos = startEndPos[1]; // x, y = col, row
        currentCell = this.getCell(startPos[1] / tile, startPos[0] / tile);
        Stack<Cell> stack = new Stack<>(); // [cell]
        stack.push(currentCell);
        boolean solvingMaze = true;

        while(stack.size() != 0 && solvingMaze){
            gridPanel.repaint();  // repaint / update the screen according to the change in the grid

            currentCell = stack.pop();
            currentCell.visited = false; // reverting what the maze generation had done

            if (currentCell.x == endPos[0] / tile && currentCell.y == endPos[1] / tile) {
                solvingMaze = false;
            }

            LinkedList<Cell> neighbors = currentCell.chooseNeighbors(this); // returns a list of valid neighbors or False if there are none
            if (neighbors.size() == 0) // no neighbors found - base case
                continue;
            else{
                for (Cell neighbor : neighbors) {
                    stack.push(neighbor);
                }
            }

            // Sleep or add a delay if you want to control the pace of visualization
            try {
                Thread.sleep(delayInMillis);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Finished solving!");
    }

    public int[][] getStartEndPos(GridPanel gridPanel, int tile){
        // getting the mouse position twice for the start and the end only if we have finished generating:
        final boolean[] foundStart = {false}; // has to be this way for it to be accessed by the inner
        final boolean[] foundEnd = {false};
        int[] startPos = new int[2]; // x , y - remember to divide it by the tile when using it
        int[] endPos = new int[2];

        System.out.println("Click on the screen twice");
        System.out.println("The first time on the starting cell");
        System.out.println("The second time on the ending cell");
        // thank you stack overflow for this
        gridPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (!foundStart[0]) {
                    System.out.printf("Start Cell: (%d, %d)\n", e.getX() / tile, e.getY() / tile);
                    startPos[0] = e.getX();
                    startPos[1] = e.getY();
                    foundStart[0] = true;
                } else if (!foundEnd[0]) {
                    System.out.printf("End Cell: (%d, %d)\n", e.getX() / tile, e.getY() / tile);
                    endPos[0] = e.getX();
                    endPos[1] = e.getY();
                    foundEnd[0] = true;
                }

                if (foundStart[0] && foundEnd[0]) {
                    gridPanel.removeMouseListener(this); // remove the listener after both clicks
                }
            }
        });

        // waiting for the user to click twice
        while (!(foundStart[0] && foundEnd[0])) {
            try {
                Thread.sleep(100); // sleep for a short time to avoid busy-waiting
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return new int[][]{startPos, endPos};
    }
}
