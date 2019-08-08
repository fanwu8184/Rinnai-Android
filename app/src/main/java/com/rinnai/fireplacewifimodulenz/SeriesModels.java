package com.rinnai.fireplacewifimodulenz;

import java.util.ArrayList;

public class SeriesModels {
    public ArrayList<SeriesModel> seriesModels = new ArrayList<SeriesModel>();

    public void insert(FireModelDao fireModelDao) {
        String fireType = convertFiretype(fireModelDao);

        if (hasModelType(fireModelDao)) {
            for (SeriesModel seriesModel: seriesModels) {
                if (seriesModel.country.equals(fireModelDao.getFireCountry()) && seriesModel.modelType.equals(fireType)) {
                    seriesModel.modelCodes.add(fireModelDao.getFireModel());
                }
            }
        } else {
            SeriesModel seriesModel = new SeriesModel(fireModelDao.getFireCountry(), fireType, fireModelDao.getFireModel());
            seriesModels.add(seriesModel);
        }
    }

    private boolean hasModelType(FireModelDao fireModelDao) {
        String fireType = convertFiretype(fireModelDao);
        for (SeriesModel seriesModel: seriesModels) {
            if (seriesModel.country.equals(fireModelDao.getFireCountry()) && seriesModel.modelType.equals(fireType)) {
                return true;
            }
        }
        return false;
    }

    private String convertFiretype(FireModelDao fireModelDao) {
        String fireType = "";
        if (fireModelDao.getFireType().startsWith("800")) {
            fireType = "800";
        } else if (fireModelDao.getFireType().startsWith("1000")) {
            fireType = "1000";
        } else if (fireModelDao.getFireType().startsWith("1500")) {
            fireType = "1500";
        } else if (fireModelDao.getFireType().startsWith("LS800")) {
            fireType = "LS800";
        } else if (fireModelDao.getFireType().startsWith("LS1000")) {
            fireType = "LS1000";
        } else if (fireModelDao.getFireType().startsWith("LS1500")) {
            fireType = "LS1500";
        } else {
            fireType = fireModelDao.getFireType();
        }
        return fireType;
    }

    public boolean containsFirstLetterBaseOnType(String type, String letter) {
        for (SeriesModel seriesModel: seriesModels) {
            if (seriesModel.modelType.equals(type)) {
                for(String modelCode: seriesModel.modelCodes) {
                    if(modelCode.startsWith(letter.toUpperCase())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean containsModelCodeBaseOnType(String type, String code) {
        for (SeriesModel seriesModel: seriesModels) {
            if (seriesModel.modelType.equals(type)) {
                if (seriesModel.modelCodes.contains(code)) {
                    return true;
                }
            }
        }
        return false;
    }
}
