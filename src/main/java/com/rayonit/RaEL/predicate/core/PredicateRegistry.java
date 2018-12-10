package com.rayonit.RaEL.predicate.core;

import com.rayonit.commons.tolling.RawData;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;


public class PredicateRegistry implements Registry, Invokable {

    private SourceClass sourceClass;

    public PredicateRegistry(String classJson){
        this.sourceClass = new SourceClass();
        registerClassFromProperties(classJson);
    }

    @Override
    public void registerClass(String className, String classMethod) {
        if(sourceClass!=null){
            Class classObject;
            sourceClass.setClassName(className);
            sourceClass.setClassMethod(classMethod);
            try {
                classObject = Class.forName(className);
                sourceClass.setSourceClass(classObject);
                Method[] allMethods = classObject.getDeclaredMethods();
                for (Method method : allMethods) {
                    System.out.println("Method : " + method.getName());
                    if (Modifier.isPublic(method.getModifiers()) && method.getName().equals(classMethod)) {
                        sourceClass.setMethod(method);
                        System.out.println("Method selected : " + method.getName());
                    }
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        }
    }

    private void registerClassFromProperties(String classAndMethod){
        JSONParser jsonParser = new JSONParser();
        JSONObject object = new JSONObject();

        try {
            object = (JSONObject) jsonParser.parse(classAndMethod);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if(object.isEmpty()){
            registerClass(RawData.class,"getMethod");
        } else {
            String className = object.get("class").toString();
            String methodName = object.get("method").toString();
            registerClass(className,methodName);
        }
    }

    public void registerClass(Class inputClass, String classMethod) {
        if (sourceClass == null)
            sourceClass = new SourceClass();
            sourceClass.setSourceClass(inputClass);
            sourceClass.setClassName(inputClass.getName());
            Method[] allMethods = inputClass.getDeclaredMethods();
            for (Method method : allMethods) {
                if (Modifier.isPublic(method.getModifiers()) && method.getName().equals(classMethod)) {
                    sourceClass.setClassMethod(method.getName());
                    sourceClass.setMethod(method);
                }
            }
       }

       // invokon metoden per te krahasuar

    @Override
    public Object invoke(Object object) {
        try {
            return this.sourceClass.invokeMethod(object);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Object invoke(Object object , String methodName){

        try {
            return this.sourceClass.invokeMethod(object, methodName);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Class getTransportClass(){
        return this.sourceClass.getSourceClass();
    }
}
