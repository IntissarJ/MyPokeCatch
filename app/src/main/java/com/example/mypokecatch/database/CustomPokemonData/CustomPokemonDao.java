package com.example.mypokecatch.database.CustomPokemonData;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CustomPokemonDao {

    @Insert
    void insert(CustomPokemon customPokemon);

    @Update
    void update(CustomPokemon customPokemon);

    @Delete
    void delete(CustomPokemon customPokemon);

    @Query("DELETE FROM customPokemon_table")
    void deleteAllPokemons();

    @Query("SELECT * FROM customPokemon_table ORDER BY pokemonId desc")
    LiveData<List<CustomPokemon>> getAllPokemons();

    @Query("SELECT * FROM customPokemon_table WHERE pokemonId = :id")
    LiveData<CustomPokemon> getAPokemon(int id);

    @Query("SELECT COUNT(*) FROM customPokemon_table")
    Integer getPokeTableSize();

}
