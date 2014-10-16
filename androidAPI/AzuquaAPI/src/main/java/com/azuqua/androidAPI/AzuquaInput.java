package com.azuqua.androidAPI;

import java.io.Serializable;
import java.util.ArrayList;

public class AzuquaInput implements  Serializable{
    private String name;
    private String type;
    private String value;
    private String help;
    private String label;
    private String placeholder;
    private String classType;
    private boolean required;
    private boolean readonly;
    private int position;
    private ArrayList<String> options;

    public AzuquaInput(){}

    public AzuquaInput(String name, String type, String value, String help, String label,
                       String placeholder, String classType, boolean required, boolean readonly, int position, ArrayList<String> options){
        this.name = name;
        this.type = type;
        this.value = value;
        this.help = help;
        this.label = label;
        this.placeholder = placeholder;
        this.classType = classType;

        this.required = required;
        this.readonly = readonly;
        this.position = position;
        this.options = options;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getHelp() {
        return help;
    }

    public void setHelp(String help) {
        this.help = help;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getPlaceholder() {
        return placeholder;
    }

    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
    }

    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public boolean isReadonly() {
        return readonly;
    }

    public void setReadonly(boolean readonly) {
        this.readonly = readonly;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public ArrayList<String> getOptions() {
        return options;
    }

    public void setOptions(ArrayList<String> options) {
        this.options = options;
    }
}
