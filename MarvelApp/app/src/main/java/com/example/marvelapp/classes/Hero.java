package com.example.marvelapp.classes;

import android.os.Parcelable;

import org.json.JSONArray;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Hero implements Serializable{
    private String name, description,image,extension,modified;
    private int id;


    public Hero(int id,String name, String description, String image,String extension,String modified) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.image = image;
        this.extension = extension;
        this.modified = modified;


    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image+"."+extension;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }


}
