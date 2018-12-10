package com.rayonit.RaEL.core;

import java.util.HashMap;

/**
 * @author Skerdjan Gurabardhi
 * @author Brigen Tafilica
 */

public abstract class RaelPairGenerator<T extends HashMap> {

    public static final String PAYLOAD_REGEX = "payload(";

    public static final String SPLITTER = ":";


    // the character used to get the nested childs of the json
    public static final String JSON_NESTED_CHAR = ".";

    public RaelPair generateStaticPair(String expression){
        RaelPair result = new RaelPair();
        String[] pair = generateArray(expression);
        result.setKey(pair[0]);
        result.setValue(pair[1]);
        return result;
    }

    public RaelPair generateDynamicPair(String expression, T t){
        RaelPair result = new RaelPair();
        String[] pair = generateArray(expression);
        result.setKey(formatValue(pair[0], t).toString());
        result.setValue(formatValue(pair[1], t));
        return result;
    }

    private Object formatValue(String value, T payload){
        Object result = value;
        if(value.contains(PAYLOAD_REGEX)) {
            value = value.replace(PAYLOAD_REGEX,"").replace(")", "");
            if(value.contains(JSON_NESTED_CHAR)){
                result = getNestedValue(value,payload);
            }
            else {
                result = payload.get(value);
            }
        }
        return result;
    }

    protected String[] generateArray(String expression){
        expression = expression.replace("\"","").replace("{", "").replace("}","");
        return expression.split(SPLITTER);
    }

    private Object getNestedValue(String value, T t){
        Object result = t;
        String[] keys = value.split("\\.");
        System.out.println("keys size" +  keys.length);
        for(int i=0; i< keys.length;i++){
            result = ((T)result).get(keys[i]);
        }
        return result;
    }

}
