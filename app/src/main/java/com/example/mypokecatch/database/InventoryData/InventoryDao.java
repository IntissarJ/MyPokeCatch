package com.example.mypokecatch.database.InventoryData;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.mypokecatch.database.InventoryPokemonData.InventoryCustomPokemonCrossRef;
import com.example.mypokecatch.database.InventoryPokemonData.InventoryWithCustomPokemons;

@Dao
public interface InventoryDao {

    @Insert
    void insert(Inventory inventory);

    @Update
    void update(Inventory inventory);

    @Delete
    void delete(Inventory inventory);

    @Query("Select * FROM inventory_table LIMIT 1")
    LiveData<Inventory> getInventory();

    @Query("SELECT COUNT(*) FROM inventory_table")
    Integer getInventoryTableSize();

    @Transaction
    @Query("SELECT * FROM inventory_table WHERE inventoryId = :inventoryId LIMIT 1")
    LiveData<InventoryWithCustomPokemons> getInventoryWithPokemons(int inventoryId);

    @Delete
    void deletePokemon(InventoryCustomPokemonCrossRef crossref);

    @Insert
    void insertInventoryWithPokemons(InventoryCustomPokemonCrossRef inventoryWithPokemons);
}

