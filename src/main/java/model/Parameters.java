package model;


import game2d.Handler;

import java.util.HashMap;

public class Parameters {
    private Handler handler;
    private HashMap<String, Double> regularParameters, proParameters, superParameters, customParameters, timeParameters;
    private String[] keys;
    private Double[] regValues, proValues, superValues;
    private int i = 0;

    public Parameters(Handler handler) {
        this.handler = handler;
        regularParameters = new HashMap<>();
        proParameters = new HashMap<>();
        superParameters = new HashMap<>();
        customParameters = new HashMap<>();
        keys = new String[]{"maxForce", "maxAngular", "airDensity", "restitutionCoef", "contactTime", "stamina", "walkSpeed", "sprintSpeed", "blueMass", "redMass"};
        regValues = new Double[]{2000d, 500d, 1168d, 68d, 120d, 11000d, 50d, 100d, 80d, 80d};
        proValues = new Double[]{3000d, 700d, 1168d, 68d, 90d, 13000d, 75d, 150d, 80d, 80d};
        superValues = new Double[]{6500d, 800d, 1168d, 68d, 80d, 14000d, 100d, 200d, 80d, 80d};
        initMaps();
        customParameters.putAll(superParameters);
    }

    public void initMaps() {
        for (String s : keys) {
            regularParameters.put(s, regValues[i]);
            i++;
        }
        i = 0;
        for (String s : keys) {
            proParameters.put(s, proValues[i]);
            i++;
        }
        i = 0;
        for (String s : keys) {
            superParameters.put(s, superValues[i]);
            i++;
        }
    }

    public void setCustom(HashMap<String, Double> parameters) {
        customParameters.putAll(parameters);
    }

    public HashMap<String, Double> getRegularParameters() {
        return regularParameters;
    }

    public HashMap<String, Double> getProParameters() {
        return proParameters;
    }

    public HashMap<String, Double> getSuperParameters() {
        return superParameters;
    }

    public HashMap<String, Double> getCustomParameters() {
        return customParameters;
    }

    public HashMap<String, Double> getTimeParameters() {
        return timeParameters;
    }

}