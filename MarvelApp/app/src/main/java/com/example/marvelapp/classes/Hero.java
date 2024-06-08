package com.example.marvelapp.classes;

import android.os.Parcelable;

import org.json.JSONArray;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
        String fechaFormateada ="";
        SimpleDateFormat formatoFechaEntrada = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        // Parsear el String a Date
        try {
            // Parsear el String a Date
            Date fechaDate = formatoFechaEntrada.parse(modified);

            // Formateador para convertir Date a String con el formato "dd-MM-yyyy"
            SimpleDateFormat formatoFechaSalida = new SimpleDateFormat("dd-MM-yyyy");

            // Convertir Date a String con el nuevo formato
            fechaFormateada = formatoFechaSalida.format(fechaDate);

            // Imprimir la fecha formateada

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return fechaFormateada;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }


}
