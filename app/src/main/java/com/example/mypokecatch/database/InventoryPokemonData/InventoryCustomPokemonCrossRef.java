package com.example.mypokecatch.database.InventoryPokemonData;

import androidx.room.Entity;

@Entity(primaryKeys = {"inventoryId", "pokemonId"})
public class InventoryCustomPokemonCrossRef {

    public int inventoryId;
    public int pokemonId;

    public InventoryCustomPokemonCrossRef(int inventoryId, int pokemonId) {
        this.inventoryId = inventoryId;
        this.pokemonId = pokemonId;
    }

}
