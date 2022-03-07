package com.example.mypokecatch.database.InventoryPokemonData;

import androidx.room.Entity;

@Entity(primaryKeys = {"inventoryId", "pokemonId"})
public class InventoryPokemonCrossRef {

    public int inventoryId;
    public int pokemonId;

    public InventoryPokemonCrossRef(int inventoryId, int pokemonId) {
        this.inventoryId = inventoryId;
        this.pokemonId = pokemonId;
    }
}
