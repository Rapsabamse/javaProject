package com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import com.model.*;

@Service
public class imageFiltersService {
    /**
     * @return {message: a message of status, code: http response code}
     */
    public ServiceResponse testBlur(){
        return new ServiceResponse("All good", 200);
    }
}