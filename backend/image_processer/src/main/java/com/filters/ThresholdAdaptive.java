package main.java.com.filters;


//Contains services used for the threshold filter (Experimental, looks bad)

public class ThresholdAdaptive {
    //These numbers can be tweaked, should probably be set as parameters instead
    private static final int CONSTANT = 10;
    private static final int BLOCK_SIZE = 20;

    public int[][][] threshold(int[][][] imageMatrix){
            int height = imageMatrix.length;
            int width = imageMatrix[0].length;

            int pixelAmount = height * width;

            System.err.println("Width: " + width);
            System.err.println("Height: " + height);

            //Add image color intensity to sum
            for(int i = 0; i < height; i++){
                for(int j = 0; j < width; j++){
                    int threshold = calculateThreshold(imageMatrix, width, height, j, i);

                    int colorIntensity = 0;
                    for(int k = 0; k < 3; k++){
                        colorIntensity += imageMatrix[i][j][k];
                    }
                    if(threshold > colorIntensity){
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

    private static int calculateThreshold(int[][][] imageMatrix, int width, int height, int x, int y) {
        int sum = 0;
        int count = 0;
        int blockSize = BLOCK_SIZE;
        int constant  = CONSTANT;
        // Iterate over the local neighborhood
        for (int i = -blockSize / 2; i <= blockSize / 2; i++) {
            for (int j = -blockSize / 2; j <= blockSize / 2; j++) {
                int nx = x + i;
                int ny = y + j;
                    
                // Check if the current neighbor pixel is within bounds
                if (nx >= 0 && nx < width - 1 && ny >= 0 && ny < height) {
                    // Calculate pixel intensity and add to sum
                    for(int k = 0; k < 3; k++){
                        sum += imageMatrix[ny][nx][k];
                    }
                    count++;
                }
            }
        }

        // Calculate average intensity
        int mean = sum / count;

        // Calculate threshold value
        int threshold = mean - constant;

        return threshold;
    }
}