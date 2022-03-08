package com.example.mypokecatch.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.mypokecatch.database.CustomPokemonData.CustomPokemon;
import com.example.mypokecatch.database.PokemonData.Pokemon;
import com.example.mypokecatch.database.PokemonData.PokemonRepository;

import java.util.List;

public class PokemonViewModel extends AndroidViewModel {

    private PokemonRepository repository;
    private static final String TAG = "POKI_VM";
    private LiveData<List<Pokemon>> pokemons;

    public PokemonViewModel(@NonNull Application application) {
        super(application);
        repository = new PokemonRepository(application);
        updateVM();
    }

    public boolean updateVM(){
        this.pokemons = repository.getAllPokemons();
        return true;
    }

    public LiveData<Pokemon> getPokemon(int id) {
        return repository.getPokemon(id);
    }

    public LiveData<Pokemon> getPokemon() {
        return repository.getAPokemon();
    }

    public void insert(Pokemon pokemon) {
        repository.insert(pokemon);
    }

    public void update(Pokemon pokemon) {
        repository.update(pokemon);
    }

    public void delete(Pokemon pokemon) {
        repository.delete(pokemon);
    }

    public void deleteAllPokemons() {
        repository.deleteAllPokemons();
    }

    public boolean isEmpty() { return repository.isPokemonTableEmpty(); }

    public LiveData<List<Pokemon>> getAllPokemons() {
        if (this.pokemons == null) updateVM();
        return pokemons;
    }

    public Integer getVMCount(){
        return repository.pokeTableCount();
    }


}
