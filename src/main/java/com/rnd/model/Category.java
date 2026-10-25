package com.rnd.model;

public enum Category {
    DEVELOPMENT("Development"),
    GAME("Game"),
    OFFICE("Office"),
    UTILITY("Utility"),
    SYSTEM("System");


    private final String desktopValue;
    Category(String desktopValue){
        this.desktopValue = desktopValue;
    }
    public String getDesktopValue(){
        return desktopValue;
    }

}


