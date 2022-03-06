package com.example.mypokecatch.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity(tableName="inventory_table")
public class Inventory {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private List<Pokemon> ownedPokemon;

    public Inventory(List<Pokemon> ownedPokemon) {
        this.ownedPokemon = ownedPokemon;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Pokemon> getOwnedPokemon() {
        return ownedPokemon;
    }

    public void setOwnedPokemon(List<Pokemon> ownedPokemon) {
        this.ownedPokemon = ownedPokemon;
    }
}
