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

@Service
public class imageFiltersService {
    private static final int MIN_RADIUS = 10;
    private static final int MAX_RADIUS = 15;
    private static final double RADIUS_SCALE = 0.012;

    Blur blur_filter = new Blur();
    ThresholdAdaptive thresholdAdaptive_filter = new ThresholdAdaptive();
    ThresholdBasic thresholdBasic_filter = new ThresholdBasic();
    ImageHandling imageHandler = new ImageHandling();

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

    public ServiceResponse thresholdImage(String base64Img){
        //Create an imageMatrix of the base64 string.
        int[][][] imageMatrix = imageHandler.base64ToMatrix(base64Img);

        //threshold the matrix
        imageMatrix = thresholdBasic_filter.threshold(imageMatrix);

        //Convert matrix into a base64 image
        base64Img = imageHandler.matrixToBase64(imageMatrix, "png");
        System.err.println(base64Img);

        return new ServiceResponse(base64Img, 200);
    }


    public ServiceResponse testBlur(){
        return new ServiceResponse("Blur - All good", 200);
    }

    public ServiceResponse testThreshold(){
        return new ServiceResponse("Threshold - All good", 200);
    }
}