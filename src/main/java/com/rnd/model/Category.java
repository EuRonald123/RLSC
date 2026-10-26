package com.rnd.model;

public enum Category {
    NONE(null),
    DEVELOPMENT("Development"),
    GAME("Game"),
    OFFICE("Office"),
    UTILITY("Utility"),
    SYSTEM("System"),
    SETTINGS("Settings"),
    EDUCATION("Education");

    private final String desktopValue;
    Category(String desktopValue){
        this.desktopValue = desktopValue;
    }
    public String getDesktopValue(){
        return desktopValue;
    }

}


