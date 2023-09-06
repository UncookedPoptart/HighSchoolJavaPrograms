import java.io.*;
import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class App {
    private static Color[][] pixels;
    private static BufferedImage img;
    public static void main(String[] args) throws Exception {

        //adds image file and creates buffered image
        try {
            img = ImageIO.read(new File("image name.jpeg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        pixels = new Color[img.getWidth()][img.getHeight()];

        //adds pixels into a 2d array
        for(int y = 0; y < img.getHeight(); y++){
            for(int x = 0; x < img.getWidth(); x++){
                pixels[x][y] = new Color(img.getRGB(x,y), true);
            }
        }

        //algoritm
        colorShiftDiagonal();
    }

    public static boolean inRange(int check, int target){
        return target-7 < check && check < target+7;
    }

    public static void invertRevert() throws IOException{

        boolean[][][] slope = new boolean[img.getWidth()][img.getHeight()][3];

        for(int z = 0; z < 256; z++){

            //modifies pixels
            for(int y = 0; y < pixels[0].length; y++){
                for(int x = 0; x < pixels.length; x++){

                    int[] color = new int[3];
                    color[0] = pixels[x][y].getRed();
                    color[1] = pixels[x][y].getGreen();
                    color[2] = pixels[x][y].getBlue();
                    for(int w = 0; w < 3; w++){
                        if(!slope[x][y][w]){
                            color[w] += 2;
                            if(color[w] > 255){
                                color[w] = 255;
                                slope[x][y][w] = true;
                            }
                        }else{
                            color[w] -= 2;
                            if(color[w] < 0){
                                color[w] = 0;
                                slope[x][y][w] = false;
                            }
                        }
                    }
                        pixels[x][y] = new Color(color[0], color[1], color[2]);
                }
            }
            //creates new images with specifications
            BufferedImage newImg = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
            Graphics2D gtd = newImg.createGraphics();

            for(int y = 0; y < pixels[0].length; y++){
                for(int x = 0; x < pixels.length; x++){
                    gtd.setColor(pixels[x][y]);
                    gtd.fillRect(x, y, x+1, y+1);
                }
            }

            //adds image to homework folder
            File file = new File("folder for images"+z+".jpeg");
            ImageIO.write(newImg, "jpeg", file);
        }
    }

    public static void colorShift() throws IOException{

        //establishes new color lines
        int[][][] line = new int[pixels.length][pixels[0].length][765];
        for(int y = 0; y < pixels[0].length; y++){
            for(int x = 0; x < pixels.length; x++){
                for(int z = 0; z < pixels[x][y].getRed(); z++){
                    while(true){
                        int rand = (int)(Math.random()*255);
                        if(line[x][y][rand] == 0){
                            line[x][y][rand] = 1;
                            break;
                        }
                    }
                }
                for(int z = 0; z < pixels[x][y].getGreen(); z++){
                    while(true){
                        int rand = (int)(Math.random()*255)+255;
                        if(line[x][y][rand] == 0){
                            line[x][y][rand] = 1;
                            break;
                        }
                    }
                }
                for(int z = 0; z < pixels[x][y].getBlue(); z++){
                    while(true){
                        int rand = (int)(Math.random()*255)+510;
                        if(line[x][y][rand] == 0){
                            line[x][y][rand] = 1;
                            break;
                        }
                    }
                }
            }
        }

        //creates images with shifted color lines
        for(int z = 0; z < 768; z+=3){
            //modifies pixels
            for(int y = 0; y < pixels[0].length; y++){
                for(int x = 0; x < pixels.length; x++){

                    int[] color = new int[3];

                    for(int w = 0; w < 255; w++){
                        color[0] += line[x][y][(w+z)%765];
                    }
                    for(int w = 0; w < 255; w++){
                        color[1] += line[x][y][(w+z+255)%765];
                    }
                    for(int w = 0; w < 255; w++){
                        color[2] += line[x][y][(w+z+510)%765];
                    }

                    pixels[x][y] = new Color(color[0], color[1], color[2]);
                }
            }
            //creates new images with specifications
            BufferedImage newImg = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
            Graphics2D gtd = newImg.createGraphics();

            for(int y = 0; y < pixels[0].length; y++){
                for(int x = 0; x < pixels.length; x++){
                    gtd.setColor(pixels[x][y]);
                    gtd.fillRect(x, y, x+1, y+1);
                }
            }

            //adds image to homework folder
            File file = new File("folder for image"+z+".jpeg");
            ImageIO.write(newImg, "jpeg", file);
        }
    }

    public static void colorShiftDiagonal() throws IOException{

        //adds overlay bufferedimage
        BufferedImage solar = null;
        try {
            solar = ImageIO.read(new File("image name.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Color[][] opix = new Color[solar.getWidth()][solar.getHeight()];

        //adds pixels into a 2d array
        for(int y = 0; y < solar.getHeight(); y++){
            for(int x = 0; x < solar.getWidth(); x++){
                opix[x][y] = new Color(solar.getRGB(x,y), true);
            }
        }

        //creates overlay array
        boolean[][] whites = new boolean[solar.getWidth()][solar.getHeight()];
        for(int y = 0; y < solar.getHeight(); y++){
            for(int x = 0; x < solar.getWidth(); x++){
                if(opix[x][y].getRed() > 200){
                    whites[x][y] = true;
                }
            }
        }

        //establishes new color lines
        int[][][] line = new int[pixels.length][pixels[0].length][765];
        for(int y = 0; y < pixels[0].length; y++){
            for(int x = 0; x < pixels.length; x++){
                for(int z = 0; z < pixels[x][y].getRed(); z++){
                    while(true){
                        int rand = (int)(Math.random()*255);
                        if(line[x][y][rand] == 0){
                            line[x][y][rand] = 1;
                            break;
                        }
                    }
                }
                for(int z = 0; z < pixels[x][y].getGreen(); z++){
                    while(true){
                        int rand = (int)(Math.random()*255)+255;
                        if(line[x][y][rand] == 0){
                            line[x][y][rand] = 1;
                            break;
                        }
                    }
                }
                for(int z = 0; z < pixels[x][y].getBlue(); z++){
                    while(true){
                        int rand = (int)(Math.random()*255)+510;
                        if(line[x][y][rand] == 0){
                            line[x][y][rand] = 1;
                            break;
                        }
                    }
                }
            }
        }

        //creates images with shifted color lines
        for(int z = 2304; z < whites.length*4; z++){
            //modifies pixels
            Color[][] temp = new Color[pixels.length][pixels[0].length];

            for(int y = 0; y < pixels[0].length; y++){
                for(int x = 0; x < pixels.length; x++){
                    //for all pixels
                    int[] color = new int[3];

                    for(int w = 0; w < 255; w++){
                        color[0] += line[x][y][(w+z/3)%765];
                    }
                    for(int w = 0; w < 255; w++){
                        color[1] += line[x][y][(w+z/3+255)%765];
                    }
                    for(int w = 0; w < 255; w++){
                        color[2] += line[x][y][(w+z/3+510)%765];
                    }

                    temp[(x+z)%1182][y] = new Color(color[0], color[1], color[2]);
                }
            }

            //override to white
            for(int y = 0; y < opix[0].length; y++){
                for(int x = 0; x < opix.length; x++){
                    if(whites[x][y]){
                        temp[x][y] = Color.white;
                    }
                }
            }

            //creates new images with specifications
            BufferedImage newImg = new BufferedImage(solar.getWidth(), solar.getHeight(), BufferedImage.TYPE_INT_RGB);
            Graphics2D gtd = newImg.createGraphics();

            for(int y = 0; y < whites[0].length; y++){
                for(int x = 0; x < whites.length; x++){
                    gtd.setColor(temp[x][y]);
                    gtd.fillRect(x, y, x+1, y+1);
                }
            }

            //adds image to homework folder
            File file = new File("folder for image"+z+".jpeg");
            ImageIO.write(newImg, "jpeg", file);
        }
    }

    
}
