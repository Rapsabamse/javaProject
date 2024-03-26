package main.java.com.filters;

/**
 * Class responsible for doing a blur filter on a 3D array
 */
public class Blur {
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
    public int[][][] gaussianBlur(int[][][] imageMatrix, int radius) {
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
                    
                    //Calculate totalWeight:
                    //  creates a rectangle with width = radius,
                    //  gets the weight of each pixel in this rectangle
                    //  weight is multiplied with current pixel and added into sum
                    //  weight is added into total weight
                    for (int offsetY = -radius; offsetY <= radius; offsetY++) {
                        for (int offsetX = -radius; offsetX <= radius; offsetX++) {
                            int pixelY = Math.min(Math.max(y + offsetY, 0), height - 1);
                            int pixelX = Math.min(Math.max(x + offsetX, 0), width - 1);
                    
                            double weight = kernel[offsetY + radius][offsetX + radius];
                            sum += imageMatrix[pixelY][pixelX][color] * weight;
                            totalWeight += weight;
                        }
                    }
                    // Normalize the sum by the total weight
                    int blurredValue = (int) (sum / totalWeight);

                    //Put new value into matrix
                    blurredMatrix[y][x][color] = Math.min(Math.max(blurredValue, 0), 255);
                }
            }
        }
        System.err.println("Blur done");
        return blurredMatrix;
    }

    /**
     * Creates a Gaussian kernel for image blurring.
     * 
     * @param radius The radius of the kernel. The size of the kernel will be (2 * radius + 1) x (2 * radius + 1).
     * @return The Gaussian kernel represented as a 2D array of doubles.
     */
    private static double[][] createGaussianKernel(int radius) {
        // Calculate the size of the kernel
        int size = 2 * radius + 1;

        // Create a 2D array to store the kernel
        double[][] kernel = new double[size][size];

        // Calculate the normalization factor (to make brightness correct)
        double sigma = radius / 3.0;
        double normFactor = 1 / (2 * Math.PI * sigma * sigma);

        // Populate the kernel with Gaussian values
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                int x = i - radius;
                int y = j - radius;
                //normalized to make overall brightness correct
                kernel[i][j] = normFactor * Math.exp(-(x * x + y * y) / (2 * sigma * sigma));
            }
        }

        return kernel;
    }
}
