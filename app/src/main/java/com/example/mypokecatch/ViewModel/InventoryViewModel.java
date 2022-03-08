package com.example.mypokecatch.ViewModel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.mypokecatch.database.InventoryData.Inventory;
import com.example.mypokecatch.database.InventoryData.InventoryRepository;
import com.example.mypokecatch.database.InventoryPokemonData.InventoryCustomPokemonCrossRef;
import com.example.mypokecatch.database.InventoryPokemonData.InventoryWithCustomPokemons;
import com.example.mypokecatch.database.CustomPokemonData.CustomPokemon;

public class InventoryViewModel extends AndroidViewModel {

    private InventoryRepository repository;
    private static final String TAG = "INVENTORY_VM";
    private LiveData<Inventory> inventory;

    public InventoryViewModel(@NonNull Application application) {
        super(application);
        repository = new InventoryRepository(application);
        updateVM();
    }

    public boolean updateVM(){
        Log.d(TAG, "updateVM: count is: " + getInventoryCount());
        this.inventory = repository.getInventory();
        return true;
    }

    public void insert(Inventory inventory) {
        repository.insert(inventory);
    }

    public void update(Inventory inventory) {
        repository.update(inventory);
    }

    public void delete(Inventory inventory) {
        repository.delete(inventory);
    }

    public void insertPokemon(CustomPokemon customPokemon, Inventory inventory) {
        repository.insertPokemon(
                new InventoryCustomPokemonCrossRef(
                        customPokemon.getPokemonId(), inventory.getInventoryId()
                )
        );
    }

    public void insertPokemon(int pokemon_id, int inventory_id) {
        repository.insertPokemon(
                new InventoryCustomPokemonCrossRef(
                        pokemon_id, inventory_id
                )
        );
    }

    public void deletePokemon(CustomPokemon customPokemon, Inventory inventory) {
        repository.deletePokemon(
                new InventoryCustomPokemonCrossRef(
                        customPokemon.getPokemonId(), inventory.getInventoryId()
                )
        );
    }

    public LiveData<InventoryWithCustomPokemons> getAllPokemon(Inventory inventory) {
        return repository.getInventoryWithPokemons(inventory.getInventoryId());
    }

    public boolean isEmpty() { return repository.isInventoryTableEmpty(); }

    public LiveData<Inventory> getInventory() {
        if (this.inventory == null) updateVM();
        return this.inventory;

    }

    public Integer getInventoryCount(){
        return repository.inventoryTableCount();
    }

}