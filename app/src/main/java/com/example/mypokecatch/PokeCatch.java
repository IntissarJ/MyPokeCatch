package com.example.mypokecatch;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.mypokecatch.Activity.MainActivity;
import com.example.mypokecatch.Adapter.PokemonAdapter;
import com.example.mypokecatch.ViewModel.PokemonViewModel;
import com.example.mypokecatch.database.CustomPokemonData.CustomPokemon;
import com.example.mypokecatch.database.iPokemon;

import java.util.List;

public class PokeCatch extends AppCompatActivity implements PokemonAdapter.OnPokemonListener{

    private List<iPokemon> customPokemons;
    private PokemonAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poke_catch);
        PokemonViewModel model = new ViewModelProvider(this).get(PokemonViewModel.class);
        model.getAllPokemons().observe(this, pokemons -> {
            // update UI
            this.customPokemons = (List<iPokemon>)(Object) pokemons;
            adapter = new PokemonAdapter(customPokemons, this);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
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