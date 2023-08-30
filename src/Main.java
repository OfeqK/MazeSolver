import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class Main {
    public static void main(String[] args) {
        String name = "401x401 maze.png";
        int[][] maze = extractData.get_pixels(name); // y, x

        // * Finding the start & end of the path:
        int height = maze.length;
        int width = maze[0].length;
        boolean start_found = false;
        boolean end_found = false;
        int[] start = new int[2]; // y , x
        int[] end = new int[2]; // y , x

        for (int y = 0; y < height; y++){
            for(int x = 0 ; x < width; x++){
                if (maze[y][x] == 1){
                    start = new int[] {y, x};
                    start_found = true;
                    break;
                }
            }
            if (start_found){
                break;
            }
        }

        for (int y = height - 1; y >= 0; y--){ // iterating backwards
            for(int x = width - 1; x >= 0; x--){
                if (maze[y][x] == 1){
                    end = new int[] {y, x};
                    end_found = true;
                    break;
                }
            }
            if (end_found){
                break;
            }
        }

        ArrayList<int[]> path = solve(maze, start, end);
        if (path != null){
            System.out.println("Path found:");
            System.out.print("[");
            for (int[] point : path) {
                System.out.print(Arrays.toString(point) + ", ");
            }
            System.out.print("]");
        } else {
            System.out.println("No path found.");
        }

        System.out.println("Saving solved image");
        save(name, path);
    }

    public static ArrayList<int[]> solve(int[][] maze, int[] start, int[] end) {
        Stack<List<Object>> stack = new Stack<>();
        int height = maze.length;
        int width = maze[0].length;
        boolean[][] visited = new boolean[height][width];

        ArrayList<int[]> initialPath = new ArrayList<>();
        initialPath.add(start);
        stack.push(Arrays.asList(start[0], start[1], initialPath));

        while (!stack.isEmpty()) {
            List<Object> node = stack.pop();
            int y = (int) node.get(0);
            int x = (int) node.get(1);
            ArrayList<int[]> path = (ArrayList<int[]>) node.get(2);

            // check if we have reached the end:
            if ((x == end[1]) && (y == end[0])) {
                return path;
            }

            // base case:
            if (x < 0 || x >= width || y < 0 || y >= height || maze[y][x] != 1 || visited[y][x]) {
                continue;
            }

            visited[y][x] = true;
            int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

            for (int[] direction : directions) {
                int dy = direction[0];
                int dx = direction[1];
                int new_x = x + dx;
                int new_y = y + dy;

                ArrayList<int[]> new_path = new ArrayList<>(path);
                new_path.add(new int[]{new_y, new_x});
                stack.push(Arrays.asList(new_y, new_x, new_path));
            }
        }

        // no solution was found
        return null;
    }


    public static void save(String name, ArrayList<int[]> path){
        File file = new File("C:\\Users\\User\\Documents\\אופק תכנות\\Java\\MazeSolver\\src\\" + name);
        BufferedImage img;
        try {
            img = ImageIO.read(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

//        int width = img.getWidth();
//        int height = img.getHeight();
//        for (int y = 0; y < height; y++){
//            for (int x = 0; x < width; x++){
//                // retrieving contents of a pixel
//                int pixel = img.getRGB(x, y);
//                // creating a color object from pixel value
//                Color color = new Color(pixel, true);
//                // retrieving the RGB values from the color object
//                int red = color.getRed();
//                int blue = color.getBlue();
//                int green = color.getGreen();
//                // modifying the rgb values
//                red = 0;
//                green = 255;
//                blue = 255;
//                // creating the color object
//                color = new Color(red, green, blue);
//                // setting the new color to the image
//                img.setRGB(x, y, color.getRGB());
//            }
//        }

        for (int[] pixels: path){
            int y = pixels[0];
            int x = pixels[1];
//            // retrieving contents of a pixel
//            int pixel = img.getRGB(x, y);
//            // creating a color object from pixel value
//            Color color = new Color(pixel, true);
            // modifying the rgb values
            int red = 0;
            int green = 255;
            int blue = 255;
            // creating the color object
            Color color = new Color(red, green, blue);
            // setting the new color to the image
            img.setRGB(x, y, color.getRGB());
        }

        // saving the modified image
        File outputFile = new File("C:\\Users\\User\\Documents\\אופק תכנות\\Java\\MazeSolver\\src\\solved_" + name);
        try {
            ImageIO.write(img, "png", outputFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Saved the image successfully");
    }

}