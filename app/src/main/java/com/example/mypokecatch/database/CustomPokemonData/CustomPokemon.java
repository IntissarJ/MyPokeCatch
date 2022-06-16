package com.example.mypokecatch.database.CustomPokemonData;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.mypokecatch.database.iPokemon;

@Entity(tableName = "customPokemon_table")
public class CustomPokemon implements iPokemon {

    @PrimaryKey(autoGenerate = true)
    private int pokemonId;

    private String name;
    private String url;
    private String image;

    public CustomPokemon(String name, String url) {
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


}
