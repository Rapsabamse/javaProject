package com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import com.model.ServiceResponse;

@Service
public class imageFiltersService {
    public ServiceResponse testBlur(){
        return new ServiceResponse("All good", 200);
    }
}