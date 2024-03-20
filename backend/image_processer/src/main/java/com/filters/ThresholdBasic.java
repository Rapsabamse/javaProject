package main.java.com.filters;

public class ThresholdBasic {
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