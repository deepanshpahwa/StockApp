package com.example.stalker.Bean;

import io.realm.RealmObject;

public class CustomIndicatorBeanRealm extends RealmObject {

    private String firstElement;
    private String secondElement;
    private boolean hasThirdElement;
    private String thirdElement;
    private String firstMathFunction;
    private String secondMathFunction;
    private String name;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getFirstElement() {
        return firstElement;
    }

    public void setFirstElement(String firstElement) {
        this.firstElement = firstElement;
    }

    public String getSecondElement() {
        return secondElement;
    }

    public void setSecondElement(String secondElement) {
        this.secondElement = secondElement;
    }

    public boolean hasThirdElement() {
        return hasThirdElement;
    }

    public void setHasThirdElement(boolean hasThirdElement) {
        this.hasThirdElement = hasThirdElement;
    }

    public String getThirdElement() {
        return thirdElement;
    }

    public void setThirdElement(String thirdElement) {
        this.thirdElement = thirdElement;
    }

    public String getFirstMathFunction() {
        return firstMathFunction;
    }

    public void setFirstMathFunction(String firstMathFunction) {
        this.firstMathFunction = firstMathFunction;
    }

    public String getSecondMathFunction() {
        return secondMathFunction;
    }

    public void setSecondMathFunction(String secondMathFunction) {
        this.secondMathFunction = secondMathFunction;
    }

}
