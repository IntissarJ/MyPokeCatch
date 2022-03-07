package com.example.mypokecatch.database.PokemonData;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface PokemonDao {

    @Insert
    void insert(Pokemon pokemon);

    @Update
    void update(Pokemon pokemon);

    @Delete
    void delete(Pokemon pokemon);

    @Query("DELETE FROM pokemon_table")
    void deleteAllPokemons();

    @Query("SELECT * FROM pokemon_table ORDER BY pokemonId desc")
    LiveData<List<Pokemon>> getAllPokemons();

    @Query("SELECT COUNT(*) FROM pokemon_table")
    Integer getPokeTableSize();

}
