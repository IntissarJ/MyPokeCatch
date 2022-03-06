package com.example.mypokecatch.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface InventoryDao {

    @Insert
    void insert(Pokemon pokemon);

    @Update
    void update(Pokemon pokemon);

    @Delete
    void delete(Pokemon pokemon);

    @Query("DELETE FROM inventory_table")
    void deleteAllPokemonsFromInventory();

    @Query("SELECT * FROM inventory_table ORDER BY id desc")
    LiveData<List<Pokemon>> getAllPokemonsFromInventory();

    @Query("SELECT COUNT(*) FROM inventory_table")
    Integer getInventoryTableSize();
}

