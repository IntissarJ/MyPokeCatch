package com.example.mypokecatch.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mypokecatch.R;
import com.example.mypokecatch.ViewModel.PokemonViewHolder;
import com.example.mypokecatch.model.Pokemon;

import java.util.ArrayList;
import java.util.List;

public class PokemonAdapter extends RecyclerView.Adapter<PokemonViewHolder> implements Filterable
{
    private List<Pokemon> pokemons;
    private List<Pokemon> pokemonsFiltered;
    private OnPokemonListener onPokemonListener;

    public PokemonAdapter(List<Pokemon> pokemons, OnPokemonListener listener)
    {
        this.pokemons = pokemons;
        this.pokemonsFiltered = pokemons;
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
        holder.getEditBtn().setOnClickListener(view -> onPokemonListener.onPokemonClick(holder.getAdapterPosition()));
        Pokemon pokemon;
        pokemon = pokemonsFiltered.get(position);

        // set pokemon image
        Glide.with(holder.itemView.getContext()).
                load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/"+ pokemon.getId() +".png")
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

    public interface OnPokemonListener {
        void onPokemonClick(int position);
    }

    @Override
    public int getItemCount() {
        return pokemonsFiltered.size();
    }
}
