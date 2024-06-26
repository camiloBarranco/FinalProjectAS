package com.example.marvelapp.classes;

import java.io.Serializable;

public class Story implements Serializable {
    private String name;
    private String type;

    public Story(String name, String type) {
        this.name = name;
        this.type = type;
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
}
