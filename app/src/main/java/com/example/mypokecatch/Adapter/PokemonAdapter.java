package com.example.mypokecatch.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filterable;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.example.mypokecatch.R;
import com.example.mypokecatch.database.CustomPokemonData.CustomPokemon;
import com.example.mypokecatch.ViewModel.PokemonViewHolder;
import com.example.mypokecatch.database.iPokemon;

import java.util.List;

public class PokemonAdapter extends PokeDexAdapter implements Filterable {
    private final OnPokemonListener onPokemonListener;

    public PokemonAdapter(List<iPokemon> customPokemons, OnPokemonListener listener) {
        super(customPokemons);
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
        holder.getEditBtn().setOnClickListener(view ->
                onPokemonListener.onPokemonClick(holder.getBindingAdapterPosition()));

        iPokemon customPokemon = getPokemonsFiltered().get(position);

        // set pokemon image
        Glide.with(holder.itemView.getContext()).
                load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/" +
                        customPokemon.getPokemonId() + ".png")
                .into(holder.getImageView());
        //set pokemon name
        holder.getTextView().setText(customPokemon.getName());
    }

    public interface OnPokemonListener {
        void onPokemonClick(int position);
    }



}
