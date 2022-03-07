package com.example.mypokecatch.database.InventoryData;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.mypokecatch.database.PokemonData.Pokemon;

import java.util.List;

@Entity(tableName="inventory_table")
public class Inventory {

    @PrimaryKey(autoGenerate = true)
    private int inventoryId;

    public int getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(int inventoryId) {
        this.inventoryId = inventoryId;
    }
}
