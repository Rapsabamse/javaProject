package main.java.com.filters;

/**
 * Class responsible for doing a threshold filter on a 3D array
 */
public class ThresholdBasic {
    /**
     * Applies a thresholding operation to an RGB image matrix.
     * Pixels with intensity less than the average intensity are set to black (0), 
     * Pixels with intensity greater than or equal to the average intensity are set to white (255).
     *
     * @param imageMatrix The input RGB image matrix represented as a 3D array.
     *                    The dimensions are [height][width][color_channel], where color_channel represents the Red, Green, and Blue channels.
     * @return The thresholded RGB image matrix with pixels set to either black or white.
     */
    public int[][][] threshold(int[][][] imageMatrix){
        int height = imageMatrix.length;
        int width = imageMatrix[0].length;

        int sum = 0;
        int pixelAmount = height * width;

        //Add image color intensity to sum
        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                for(int k = 0; k < 3; k++){
                    sum += imageMatrix[i][j][k];
                }
            }
        }

        //Sum is now average intensity
        //Everything < sum = black, Everything > sum = white
        sum = sum/pixelAmount;

        //Check every pixels intensity and turn them black or white depending on their color intensity
        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                int colorIntensity = 0;
                for(int k = 0; k < 3; k++){
                    colorIntensity += imageMatrix[i][j][k];
                }
                if(sum > colorIntensity){
                    for(int k = 0; k < 3; k++){
                        imageMatrix[i][j][k] = 0;
                    }
                } else {
                    for(int k = 0; k < 3; k++){
                        imageMatrix[i][j][k] = 255;
                    }
                }
            }
        }

        return imageMatrix;
    }
}