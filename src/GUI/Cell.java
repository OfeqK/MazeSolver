package GUI;

import java.util.*;
import java.awt.*;
import java.util.Random;

public class Cell {
    int x, y, tile, cols, rows;
    int[] res;
    boolean visited = false;

    Color black = new Color(0, 0, 0);
    Color light_blue = new Color(0, 255, 255);
    Color turquoise = new Color(0, 82, 84);

    HashMap<String, Boolean> walls;

    public Cell(int x, int y, int tile, int[] res){
        this.x = x;
        this.y = y;
        this.tile = tile;
        this.res = res; // x, y
        this.cols = res[0];
        this.rows = res [1];

        walls = new HashMap<>();
        walls.put("top", true);
        walls.put("left", true);
        walls.put("bottom", true);
        walls.put("right", true);
    }

    public boolean hasWall(String direction) { // hasWall and setWall functions for easier access to the hashmap. It makes the code more clear imo.
        return walls.get(direction);
    }

    public void setWall(String direction, boolean value) {
        walls.put(direction, value);
    }

    public void drawCurrent(Graphics g){
        int wn_x = x * tile;
        int wn_y = y * tile;
        g.setColor(turquoise);
        g.fillRect(wn_x + 1, wn_y + 1, tile - 1, tile - 1); // draw the square but make it fill only the white area and not the borders
    }

    public void draw(Graphics g){
//        System.out.printf("x: %d, y: %d\n", x, y);
        int wn_x = x * tile;
        int wn_y = y * tile;
        if (this.visited){
            g.setColor(light_blue);
            g.fillRect(wn_x, wn_y , tile, tile); // draw the square but make it fill only the white area and not the borders
        }

        g.setColor(black);
        if (this.hasWall("top")){
            g.drawLine(wn_x, wn_y, wn_x + tile, wn_y);
        }
        if (this.hasWall("left")){
            g.drawLine(wn_x, wn_y, wn_x, wn_y + tile);
        }
        if (this.hasWall("bottom")){
            g.drawLine(wn_x, wn_y + tile, wn_x + tile, wn_y + tile);
        }
        if (this.hasWall("right")){
            g.drawLine(wn_x + tile, wn_y, wn_x + tile, wn_y + tile);
        }

    }

    public Object checkCell(Grid grid, int cur_x, int cur_y){
        if (cur_x < 0 || cur_x > cols - 1 || cur_y < 0 || cur_y > rows - 1){ // the cell is out of boundaries
            return false;
        }
        return grid.getCell(cur_y, cur_x);
    }

    public Object checkNeighbors(Grid grid){
        LinkedList<Cell> neighbors = new LinkedList<>();
        Object top = this.checkCell(grid, x, y - 1);
        if (top instanceof Cell){ // the function returned a valid cell that has not been visited
            if (!((Cell) top).visited){
                neighbors.push(((Cell) top));
            }
        }

        Object right = this.checkCell(grid, x + 1, y );
        if (right instanceof Cell){ // the function returned a valid cell that has not been visited
            if (!((Cell) right).visited){
                neighbors.push(((Cell) right));
            }
        }

        Object bottom = this.checkCell(grid, x, y + 1);
        if (bottom instanceof Cell){ // the function returned a valid cell that has not been visited
            if (!((Cell) bottom).visited){
                neighbors.push(((Cell) bottom));
            }
        }

        Object left = this.checkCell(grid, x - 1, y);
        if (left instanceof Cell){ // the function returned a valid cell that has not been visited
            if (!((Cell) left).visited){
                neighbors.push(((Cell) left));
            }
        }

        if (neighbors.size() > 0){
            return neighbors.get(new Random().nextInt(neighbors.size())); // returns a random neighbor
        } else {
            return false;
        }
    }
}
