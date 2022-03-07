package com.example.mypokecatch.database.InventoryData;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.mypokecatch.database.InventoryPokemonData.InventoryPokemonCrossRef;
import com.example.mypokecatch.database.InventoryPokemonData.InventoryWithPokemons;
import com.example.mypokecatch.database.PokemonData.Pokemon;

import java.util.List;

@Dao
public interface InventoryDao {

    @Insert
    void insert(Inventory inventory);

    @Update
    void update(Inventory inventory);

    @Delete
    void delete(Inventory inventory);


//    @Query("DELETE * FROM inventory_table")
//    void deleteAllInventorys();

    @Query("Select * FROM inventory_table LIMIT 1")
    LiveData<Inventory> getInventory();

//    @Query("SELECT * FROM inventory_table ORDER BY inventoryId")
//    LiveData<List<Pokemon>> getAllPokemonsFromInventory();

    @Query("SELECT COUNT(*) FROM inventory_table")
    Integer getInventoryTableSize();

    @Transaction
    @Query("SELECT * FROM inventory_table")
    List<InventoryWithPokemons> getInventoryWithPokemons();

    @Delete
    void deletePokemon(InventoryPokemonCrossRef crossref);

    @Insert
    void insertInventoryWithPokemons(InventoryPokemonCrossRef inventoryWithPokemons);
}

