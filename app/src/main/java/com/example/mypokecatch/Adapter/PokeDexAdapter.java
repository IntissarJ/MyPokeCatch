package com.example.mypokecatch.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mypokecatch.database.PokemonData.Pokemon;
import com.example.mypokecatch.R;
import com.example.mypokecatch.ViewModel.PokemonViewHolder;

import java.util.ArrayList;
import java.util.List;

public class PokeDexAdapter extends RecyclerView.Adapter<PokemonViewHolder> implements Filterable
{
    private List<Pokemon> pokemons;

    private List<Pokemon> pokemonsFiltered;

    public PokeDexAdapter(List<Pokemon> pokemons)
    {
        this.pokemons = pokemons;
        this.pokemonsFiltered = pokemons;
    }

    public PokeDexAdapter() {

    }

    public int getPokemonId(int position){
        return pokemons.get(position).getPokemonId();
    }

    public void updateAdapter(List<Pokemon> pokemons) {
        this.pokemons = pokemons;
        this.pokemonsFiltered = pokemons;
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
        Pokemon pokemon = pokemonsFiltered.get(position);
        // set pokemon image
        Glide.with(holder.itemView.getContext()).
                load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/"+
                        pokemon.getPokemonId() +".png")
                .into(holder.getImageView());
        //set pokemon name
        holder.getTextView().setText(pokemon.getName());
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    pokemonsFiltered = pokemons;
                } else {
                    List<Pokemon> filteredList = new ArrayList<>();
                    for (Pokemon row : pokemons) {
                        // name match condition
                        // here we are looking for name match
                        if (row.getName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }
                    pokemonsFiltered = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = pokemonsFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                pokemonsFiltered = (ArrayList<Pokemon>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    @Override
    public int getItemCount() {
        return pokemonsFiltered.size();
    }

    public List<Pokemon> getPokemonsFiltered() {
        return pokemonsFiltered;
    }

    public void setPokemonsFiltered(List<Pokemon> pokemonsFiltered) {
        this.pokemonsFiltered = pokemonsFiltered;
    }
}
