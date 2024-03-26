package com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import com.model.ServiceResponse;

import main.java.com.filters.Blur;
import main.java.com.filters.ThresholdAdaptive;
import main.java.com.filters.ThresholdBasic;
import main.java.com.utility.ImageHandling;

import javax.imageio.ImageIO;

import java.io.IOException;

import main.java.com.filters.*;
import main.java.com.utility.*;

/**
 * Class responsible for service layer in imageFilterService
 */
@Service
public class imageFiltersService {
    private static final int MIN_RADIUS = 10;
    private static final int MAX_RADIUS = 15;
    private static final double RADIUS_SCALE = 0.012;

    Blur blur_filter = new Blur();
    ThresholdAdaptive thresholdAdaptive_filter = new ThresholdAdaptive();
    ThresholdBasic thresholdBasic_filter = new ThresholdBasic();
    ImageHandling imageHandler = new ImageHandling();

    /**
     * Applies a Gaussian blur to the input image represented by a Base64 string.
     * 
     * This method performs Gaussian blur operation on the input image with an adaptively
     * determined radius based on the image dimensions.
     * 
     * Uses primitive adaptive selection of radius
     * 
     * @param base64Img The input image encoded as a Base64 string.
     * @return A ServiceResponse object containing the blurred image encoded as a Base64 string
     *         and an HTTP status code indicating the operation's success (200 for success).
     */
    public ServiceResponse blurImage(String base64Img){
        //Create an imageMatrix of the base64 string.
        int[][][] imageMatrix = imageHandler.base64ToMatrix(base64Img);

        //Adaptively get a radius for blur filter
        int avgDimension = (int)(((double)(imageMatrix.length + imageMatrix[0].length) / 2) * RADIUS_SCALE);
        int radius =  Math.min(Math.max(avgDimension, MIN_RADIUS), MAX_RADIUS);

        System.err.println("Radius: " + radius);

        //Blur the matrix
        imageMatrix = blur_filter.gaussianBlur(imageMatrix, radius);

        //Convert matrix into a base64 image
        base64Img = imageHandler.matrixToBase64(imageMatrix, "png");

        return new ServiceResponse(base64Img, 200);
    }

    /**
     * Thresholds the input image represented by a Base64 string.
     * 
     * This method performs a simple thresholding operation on the input image.
     * Pixels with intensity less than the average intensity are set to black, 
     * while pixels with intensity greater than or equal to the average intensity are set to white.
     * 
     * @param base64Img The input image encoded as a Base64 string.
     * @return A ServiceResponse object containing the thresholded image encoded as a Base64 string
     *         and an HTTP status code indicating the operation's success (200 for success).
     */
    public ServiceResponse thresholdImage(String base64Img){
        //Create an imageMatrix of the base64 string.
        int[][][] imageMatrix = imageHandler.base64ToMatrix(base64Img);

        //threshold the matrix
        imageMatrix = thresholdBasic_filter.threshold(imageMatrix);

        //Convert matrix into a base64 image
        base64Img = imageHandler.matrixToBase64(imageMatrix, "png");

        return new ServiceResponse(base64Img, 200);
    }


    public ServiceResponse testBlur(){
        return new ServiceResponse("Blur - All good", 200);
    }

    public ServiceResponse testThreshold(){
        return new ServiceResponse("Threshold - All good", 200);
    }
}