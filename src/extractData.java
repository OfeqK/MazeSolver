import java.io.File;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class extractData {
    public static int[][] get_pixels(String name) {
        File file = new File("C:\\Users\\User\\Documents\\אופק תכנות\\Java\\MazeSolver\\src\\" + name);
        BufferedImage img;
        try {
            img = ImageIO.read(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // initialize the int array that will contain the maze:
        int width = img.getWidth();
        int height = img.getHeight();
        int[][] maze = new int[height][width];

        for (int y = 0; y < height; y++){
            for (int x = 0; x < width; x++){
                // retrieving contents of a pixel
                int pixel = img.getRGB(x, y);
                // creating a color object from pixel value
                Color color = new Color(pixel, true);
                // retrieving the RGB values from the color object
                int red = color.getRed();
                int blue = color.getBlue();
                int green = color.getGreen();
                if ((red == 255) && (blue == 255) && (green == 255)){ // white pixel = path = 1
                    maze[y][x] = 1;
                } else {
                    maze[y][x] = 0;
                }
            }
        }
        return maze;

    }

}
