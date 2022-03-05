package com.example.mypokecatch;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class PokemonAdapter extends RecyclerView.Adapter<PokemonViewHolder>
{
    List<Pokemon> pokemons;
    private OnPokemonListener onPokemonListener;


    PokemonAdapter(List<Pokemon> pokemons, OnPokemonListener listener)
    {
        this.pokemons = pokemons;
        this.onPokemonListener = listener;
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
        holder.editBtn.setOnClickListener(view ->
                onPokemonListener.onPokemonClick(holder.getBindingAdapterPosition()));

        Pokemon pokemon = pokemons.get(position);
        // set pokemon image
        Glide.with(holder.itemView.getContext()).
                load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/"+ pokemon.getId() +".png")
                .into(holder.imageView);
        //set pokemon name
        holder.textView.setText(pokemon.getName());

    }

    public interface OnPokemonListener {
        void onPokemonClick(int position);
    }

    @Override
    public int getItemCount() {
        return pokemons.size();
    }
}
