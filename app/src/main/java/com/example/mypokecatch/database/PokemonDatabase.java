package com.example.mypokecatch.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.mypokecatch.database.CustomPokemonData.CustomPokemon;
import com.example.mypokecatch.database.CustomPokemonData.CustomPokemonDao;
import com.example.mypokecatch.database.InventoryData.Inventory;
import com.example.mypokecatch.database.InventoryData.InventoryDao;
import com.example.mypokecatch.database.InventoryPokemonData.InventoryCustomPokemonCrossRef;
import com.example.mypokecatch.database.PokemonData.Pokemon;
import com.example.mypokecatch.database.PokemonData.PokemonDao;

@Database(entities = {Pokemon.class, Inventory.class,
        InventoryCustomPokemonCrossRef.class, CustomPokemon.class},
        version = 21, exportSchema = false)
public abstract class PokemonDatabase extends RoomDatabase {

    private static PokemonDatabase instance;
    public abstract CustomPokemonDao customPokemonDao();
    public abstract PokemonDao pokemonDao();
    public abstract InventoryDao inventoryDao();

    public static synchronized PokemonDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    PokemonDatabase.class, "pokemon_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

}
