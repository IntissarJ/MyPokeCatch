package com.example.mypokecatch.ViewModel;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.example.mypokecatch.R;

public class PokemonViewHolder extends RecyclerView.ViewHolder
{

    private ImageView imageView;
    private TextView textView;
    private Button editBtn;

    public ImageView getImageView() {
        return imageView;
    }

    public Button getEditBtn() {
        return editBtn;
    }

    public TextView getTextView() {
        return textView;
    }

    private RequestQueue requestQueue;

    public PokemonViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.imageView);
        textView = itemView.findViewById(R.id.textView);
        editBtn = itemView.findViewById(R.id.editPokemon);
    }
}
