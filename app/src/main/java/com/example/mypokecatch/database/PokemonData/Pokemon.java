package com.example.mypokecatch.database.PokemonData;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.mypokecatch.database.iPokemon;

@Entity(tableName = "pokemon_table")
public class Pokemon implements iPokemon {

    @PrimaryKey(autoGenerate = true)
    private int pokemonId;

    private String name;
    private String url;
    private String image;

    public Pokemon(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public int getPokemonId() {
        return pokemonId;
    }

    public void setPokemonId(int pokemonId) {
        this.pokemonId = pokemonId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String getImage() { return image; }

    @Override
    public void setImage(String url) { this.image = image; }
}
