package com.example.mypokecatch.database;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.mypokecatch.PokemonAdapter;
import com.example.mypokecatch.PokemonOverviewFragment;
import com.example.mypokecatch.R;

import java.util.ArrayList;
import java.util.List;

public class PokemonViewModel extends AndroidViewModel {

    private PokemonRepository repository;
    private static final String TAG = "POKI_VM";
    private LiveData<List<Pokemon>> pokemons;
    private List<Pokemon> pokemon2;

    public PokemonViewModel(@NonNull Application application) {
        super(application);
        repository = new PokemonRepository(application);
//        updateVM();
    }

    public boolean updateVM(){
        Log.d(TAG, "updateVM: count is: " + getVMCount());
        this.pokemons = repository.getAllPokemons();
        return true;
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
        if (this.pokemons == null) return null;
        return pokemons;
    }

    public Integer getVMCount(){
        return repository.pokeTableCount();
    }



}
