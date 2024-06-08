package com.example.marvelapp.classes;

import java.io.Serializable;
import java.util.List;

public class Comic implements Serializable {
    private String title;
    private String description;
    private String imageUrl;
    private String extension;
    private String listHeroes;

    public Comic(String title, String description, String imageUrl,String extension, String listHeroes) {
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.extension = extension;
        this.listHeroes = listHeroes;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getListHeroes() {
        return listHeroes;
    }

    public void setListHeroes(String listHeroes) {
        this.listHeroes = listHeroes;
    }
}
