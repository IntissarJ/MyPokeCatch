package com.example.mypokecatch.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.mypokecatch.database.CustomPokemonData.CustomPokemon;
import com.example.mypokecatch.database.CustomPokemonData.CustomPokemonRepository;

import java.util.List;

public class CustomPokemonViewModel extends AndroidViewModel {

    private CustomPokemonRepository repository;
    private static final String TAG = "POKI_VM";
    private LiveData<List<CustomPokemon>> customPokemons;

    public CustomPokemonViewModel(@NonNull Application application) {
        super(application);
        repository = new CustomPokemonRepository(application);
        updateVM();
    }

    public boolean updateVM(){
        this.customPokemons = repository.getAllPokemons();
        return true;
    }

    public void insert(CustomPokemon pokemon) {
        repository.insert(pokemon);
    }

    public void update(CustomPokemon pokemon) {
        repository.update(pokemon);
    }

    public void delete(CustomPokemon pokemon) {
        repository.delete(pokemon);
    }

    public void deleteAllPokemons() {
        repository.deleteAllPokemons();
    }

    public boolean isEmpty() { return repository.isPokemonTableEmpty(); }

    public LiveData<List<CustomPokemon>> getAllPokemons() {
        if (this.customPokemons == null) updateVM();
        return customPokemons;
    }

    public LiveData<CustomPokemon> getPokemon(int id) {
        return repository.getPokemon(id);
    }

    public Integer getVMCount(){
        return repository.pokeTableCount();
    }


}
