package com.example.mypokecatch;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.mypokecatch.Activity.InventoryActivity;
import com.example.mypokecatch.Activity.MainActivity;
import com.example.mypokecatch.Adapter.PokemonAdapter;
import com.example.mypokecatch.ViewModel.CustomPokemonViewModel;
import com.example.mypokecatch.ViewModel.InventoryViewModel;
import com.example.mypokecatch.ViewModel.PokemonViewModel;
import com.example.mypokecatch.database.CustomPokemonData.CustomPokemon;
import com.example.mypokecatch.database.CustomPokemonData.CustomPokemonRepository;
import com.example.mypokecatch.database.DataService;
import com.example.mypokecatch.database.InventoryData.Inventory;
import com.example.mypokecatch.database.InventoryData.InventoryRepository;
import com.example.mypokecatch.database.InventoryPokemonData.InventoryCustomPokemonCrossRef;
import com.example.mypokecatch.database.InventoryPokemonData.InventoryWithCustomPokemons;
import com.example.mypokecatch.database.PokemonData.Pokemon;
import com.example.mypokecatch.database.PokemonData.PokemonRepository;
import com.example.mypokecatch.database.iPokemon;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PokeCatch extends AppCompatActivity implements PokemonAdapter.OnPokemonListener{

    private List<iPokemon> pokemons;

    private iPokemon pokemon = null;
    private ImageView pokeImg;
    private TextView pokeName;
    private Button catchButton;


    private PokemonViewModel model;
    private CustomPokemonViewModel modelCustomPokemon;
    private InventoryViewModel modelInventory;
    private CustomPokemon customPokemon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poke_catch);
        model = new ViewModelProvider(this).get(PokemonViewModel.class);
        modelCustomPokemon = new ViewModelProvider(this).get(CustomPokemonViewModel.class);
        modelInventory = new ViewModelProvider(this).get(InventoryViewModel.class);

        model.getAllPokemons().observe(this, pokemons -> {
            if (pokemons != null) {
                // update UI
                this.pokemons = (List<iPokemon>) (Object) pokemons;
                randomPokemon();
            }
        });

    }

    public void randomPokemon(){
        Random r = new Random();
        int randomInt = r.nextInt(100) + 1;

        pokemon = pokemons.get(randomInt);
        pokeImg = findViewById(R.id.pokecatch_image);
        pokeName = findViewById(R.id.pokecatch_name);
        pokeName.setText(pokemon.getName());

        Glide.with(this).
                load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/"
                        + pokemon.getPokemonId() + ".png")
                .into(pokeImg);

        catchPokemon();
    }

    public void catchPokemon(){
        catchButton = findViewById(R.id.pokecatch_button);

        modelCustomPokemon.getPokemon(pokemon.getPokemonId()).observe(this, poki -> {
            catchButton.setOnClickListener(view -> {
                if(poki != null){
                    Toast.makeText(this, "You already have" + pokemon.getName(), Toast.LENGTH_LONG).show();
                    randomPokemon();
                } else {
                    customPokemon = new CustomPokemon(pokemon.getName(),pokemon.getUrl());
                    customPokemon.setPokemonId(pokemon.getPokemonId());
                    modelCustomPokemon.insert(customPokemon);
                    modelInventory.insertPokemon(pokemon.getPokemonId(), 1);
                    finish();
                }
            });
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        item.setVisible(false);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId())
        {
            case R.id.menu_pokedex:
                Intent home = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(home);
                Toast.makeText(this, "Pokedex", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menu_pokecatch:
                Intent pokeCatching = new Intent(this, PokeCatch.class);
                startActivity(pokeCatching);
                Toast.makeText(this, "PokeCatch", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menu_inventory:
                Intent inventory = new Intent(this, InventoryActivity.class);
                startActivity(inventory);
                Toast.makeText(this, "Your pokemons", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onPokemonClick(int position) {

    }
}