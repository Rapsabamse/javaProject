package com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Base64;
import java.awt.Color;

import com.model.ServiceResponse;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class imageFiltersService {

    private static final int MIN_RADIUS = 10;
    private static final int MAX_RADIUS = 15;
    private static final double RADIUS_SCALE = 0.012;

    public ServiceResponse blurImage(String base64Img){
        //Create an imageMatrix of the base64 string.
        int[][][] imageMatrix = base64ToMatrix(base64Img);

        //Adaptively get a radius for blur filter
        int avgDimension = (int)(((double)(imageMatrix.length + imageMatrix[0].length) / 2) * RADIUS_SCALE);
        int radius =  Math.min(Math.max(avgDimension, MIN_RADIUS), MAX_RADIUS);

        System.err.println("Radius: " + radius);

        //Blur the matrix
        imageMatrix = gaussianBlur(imageMatrix, radius);

        //Convert matrix into a base64 image
        base64Img = matrixToBase64(imageMatrix);

        //Format base64
        base64Img = "data:image/png;base64," + base64Img;

        return new ServiceResponse(base64Img, 200);
    }


    public ServiceResponse testBlur(){
        return new ServiceResponse("Blur - All good", 200);
    }

    public ServiceResponse testThreshold(){
        return new ServiceResponse("Threshold - All good", 200);
    }

    /**
     * Applies Gaussian blur to a color matrix representing an image.
     *  TODO: fix explanation
     * @param imageMatrix the color matrix representing the image, where the first dimension
     *                    represents the height (rows), the second dimension represents the width
     *                    (columns), and the third dimension represents the RGB values 
     *                    (0: red, 1: green, 2: blue) 
     *                    of each pixel the radius of the Gaussian blur kernel
     * @param radius      
     * @return the color matrix after applying Gaussian blur
     */
    private static int[][][] gaussianBlur(int[][][] imageMatrix, int radius) {
        int height = imageMatrix.length;
        int width = imageMatrix[0].length;
        
        System.err.println("Height: " + height);
        System.err.println("Width: " + width);

        // Create a Gaussian kernel
        double[][] kernel = createGaussianKernel(radius);
    
        int[][][] blurredMatrix = new int[height][width][3];

        for (int color = 0; color < 3; color++) {
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    double sum = 0;
                    double totalWeight = 0;
                    
                    //Calculate totalWeight
                    for (int offsetY = -radius; offsetY <= radius; offsetY++) {
                        for (int offsetX = -radius; offsetX <= radius; offsetX++) {
                            int pixelY = Math.min(Math.max(y + offsetY, 0), height - 1);
                            int pixelX = Math.min(Math.max(x + offsetX, 0), width - 1);
                    
                            double weight = kernel[offsetY + radius][offsetX + radius];  // Adjusted indexing
                            sum += imageMatrix[pixelY][pixelX][color] * weight;
                            totalWeight += weight;
                        }
                    }
                    // Normalize the sum by the total weight
                    int blurredValue = (int) (sum / totalWeight);
                    // System.err.println("Adding to matrix[" + y + "][" + x + "][" + color + "]");
                    //Put new value into matrix
                    blurredMatrix[y][x][color] = Math.min(Math.max(blurredValue, 0), 255);
                }
            }
        }
        System.err.println("Blur done");
        return blurredMatrix;
    }

    private static double[][] createGaussianKernel(int radius) {
        // Calculate the size of the kernel
        int size = 2 * radius + 1;
    
        // Create a 2D array to store the kernel
        double[][] kernel = new double[size][size];
    
        // Calculate the normalization factor
        double sigma = radius / 3.0; // Adjust sigma for desired blur level
        double normFactor = 1 / (2 * Math.PI * sigma * sigma);
    
        // Populate the kernel with Gaussian values
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                int x = i - radius;
                int y = j - radius;
                kernel[i][j] = normFactor * Math.exp(-(x * x + y * y) / (2 * sigma * sigma));
            }
        }
    
        return kernel;
    }


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
    private static String matrixToBase64(int[][][] imageMatrix){
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
            ImageIO.write(image, "png", baos);
        } catch (IOException e){
            System.err.println("Error turning matrix into base64");
        }

        // Encode byte array to base64 string
        byte[] bytes = baos.toByteArray();
        return Base64.getEncoder().encodeToString(bytes);
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
    private static int[][][] base64ToMatrix(String base64Img){
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