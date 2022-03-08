package com.example.mypokecatch.database.InventoryPokemonData;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import com.example.mypokecatch.database.InventoryData.Inventory;
import com.example.mypokecatch.database.CustomPokemonData.CustomPokemon;

import java.util.List;

public class InventoryWithCustomPokemons {

    @Embedded
    public Inventory inventory;

    @Relation(
            parentColumn = "inventoryId",
            entityColumn = "pokemonId",
            associateBy = @Junction(InventoryCustomPokemonCrossRef.class)
    )
    public List<CustomPokemon> customPokemons;

}
