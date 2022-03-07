package com.example.mypokecatch.database.InventoryPokemonData;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import com.example.mypokecatch.database.InventoryData.Inventory;
import com.example.mypokecatch.database.PokemonData.Pokemon;

import java.util.List;

public class InventoryWithPokemons {

    @Embedded
    public Inventory inventory;

    @Relation(
            parentColumn = "inventoryId",
            entityColumn = "pokemonId",
            associateBy = @Junction(InventoryPokemonCrossRef.class)
    )
    public List<Pokemon> pokemons;

}
