package main.java.com.utility;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.awt.Color;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class ImageHandling {
    /**
     * Converts a color matrix into a base64-encoded image.
     *
     * @param imageMatrix the color matrix representing the image, where the first dimension
     *                    represents the height (rows), the second dimension represents the width
     *                    (columns), and the third dimension represents the RGB values (0: red,
     *                    1: green, 2: blue) of each pixel
     * @return a base64-encoded string representing the image
     * @throws IOException if an error occurs during image encoding
     */
    public String matrixToBase64(int[][][] imageMatrix, String imgFormat){
        int height = imageMatrix.length;
        int width = imageMatrix[0].length;

        // Create a BufferedImage with the same dimensions as the color matrix
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        // Set pixel values in the BufferedImage
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = (imageMatrix[y][x][0] << 16) | (imageMatrix[y][x][1] << 8) | imageMatrix[y][x][2];
                image.setRGB(x, y, rgb);
            }
        }

        // Convert BufferedImage to byte array
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try{
            ImageIO.write(image, imgFormat, baos);
        } catch (IOException e){
            System.err.println("Error turning matrix into base64");
        }

        // Encode byte array to base64 string
        byte[] bytes = baos.toByteArray();
        String base64Img = Base64.getEncoder().encodeToString(bytes);

        //Format so frontend understands image
        base64Img = "data:image/" + imgFormat  + ";base64," + base64Img;
        return base64Img;
    }


    /**
     * Converts a base64-encoded image into a matrix representing its color values.
     *
     * @param base64Img the base64-encoded image string
     * @return a 3D array representing the color values of each pixel in the image,
     *         where the first dimension represents the height (rows), the second dimension
     *         represents the width (columns), and the third dimension represents the RGB values
     *         (0: red, 1: green, 2: blue) of each pixel
     * @throws IOException if an error occurs during image decoding
     */
    public int[][][] base64ToMatrix(String base64Img){
        //Decode base64 image into a byte array 
        byte[] imageBytes = Base64.getDecoder().decode(base64Img);

        // Create ByteArrayInputStream from byte array
        ByteArrayInputStream byteArrayInStream = new ByteArrayInputStream(imageBytes);

        // Read image from ByteArrayInputStream
        try{
            BufferedImage image = ImageIO.read(byteArrayInStream);

            // Get image dimensions
            int width = image.getWidth();
            int height = image.getHeight();
         
            // Create color matrix
            int[][][] colorMatrix = new int[height][width][3];

            // Populate the color matrix with RGB values
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    // Get RGB color of pixel
                    Color pixelColor = new Color(image.getRGB(x, y));
                    // Store RGB values in color matrix
                    colorMatrix[y][x][0] = pixelColor.getRed();
                    colorMatrix[y][x][1] = pixelColor.getGreen();
                    colorMatrix[y][x][2] = pixelColor.getBlue();
                }
            }
            return colorMatrix;
        } catch (IOException e){
            System.err.println("Error with turning base64 into matrix");
        }
        int[][][] falseReturn = new int[1][1][1];
        return falseReturn;
    }
}
