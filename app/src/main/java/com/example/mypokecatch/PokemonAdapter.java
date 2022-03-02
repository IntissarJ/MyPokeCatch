package com.example.mypokecatch;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class PokemonAdapter extends RecyclerView.Adapter<PokemonViewHolder>
{
    List<Pokemon> pokemons;

    PokemonAdapter(List<Pokemon> pokemons)
    {
        this.pokemons = pokemons;
    }

    @NonNull
    @Override
    public PokemonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.pokemon_item, parent, false);

        return new PokemonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PokemonViewHolder holder, int position) {
        Pokemon pokemon = pokemons.get(position);
        // set pokemon image
        Glide.with(holder.itemView.getContext()).
                load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/"+ pokemon.getId() +".png")
                .into(holder.imageView);
        //set pokemon name
        holder.textView.setText(pokemon.getName());
    }

    @Override
    public int getItemCount() {
        return pokemons.size();
    }
}
