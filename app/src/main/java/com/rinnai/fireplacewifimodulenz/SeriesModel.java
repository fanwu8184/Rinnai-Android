package com.rinnai.fireplacewifimodulenz;

import java.util.ArrayList;
import java.util.Collections;

public class SeriesModel {
    public String country;
    public String modelType;
    public ArrayList<String> modelCodes;

    public SeriesModel(String country, String modelType, String modelCode){
        this.country = country;
        this.modelType = modelType;
        this.modelCodes = new ArrayList<String>();
        this.modelCodes.add(modelCode);
    }
}
