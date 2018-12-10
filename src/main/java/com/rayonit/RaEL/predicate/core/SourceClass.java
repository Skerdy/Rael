package com.rayonit.RaEL.predicate.core;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class SourceClass {

    private String className;
    private String classMethod;
    private Class sourceClass;
    private Method method;

    public SourceClass() {
    }

    public SourceClass(String className, String classMethod) {
        this.className = className;
        this.classMethod = classMethod;
    }


    public Object invokeMethod(Object instance) throws InvocationTargetException, IllegalAccessException {
        return method.invoke(instance);
    }

    public Object invokeMethod(Object instance, String methodName) throws InvocationTargetException, IllegalAccessException {
        Method[] methods = sourceClass.getMethods();
        for (Method method : methods) {
            if (Modifier.isPublic(method.getModifiers()) && method.getName().equals(methodName))
                return method.invoke(instance);
        }
        return null;
    }


    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassMethod() {
        return classMethod;
    }

    public void setClassMethod(String classMethod) {
        this.classMethod = classMethod;
    }

    public Class getSourceClass() {
        return sourceClass;
    }

    public void setSourceClass(Class sourceClass) {
        this.sourceClass = sourceClass;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }


}
