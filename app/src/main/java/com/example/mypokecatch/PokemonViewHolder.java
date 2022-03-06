package com.example.mypokecatch;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;

public class PokemonViewHolder extends RecyclerView.ViewHolder
{

    ImageView imageView;
    TextView textView;
    Button editBtn;

    public PokemonViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.imageView);
        textView = itemView.findViewById(R.id.textView);
        editBtn = itemView.findViewById(R.id.editPokemon);
    }
}
