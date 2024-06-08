package com.example.marvelapp.adapters;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ComicAdapter extends RecyclerView.Adapter<HeroAdapter.ViewHolder>{
    private String title;
    private String description;
    private String imageUrl;
    private String extension;
    private String listHeroes;
    public ComicAdapter(String title, String description, String imageUrl, String extension, String listHeroes) {
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.extension = extension;
        this.listHeroes = listHeroes;
    }
    @NonNull
    @Override
    public HeroAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull HeroAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
