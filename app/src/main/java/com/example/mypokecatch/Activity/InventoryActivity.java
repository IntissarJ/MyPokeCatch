package com.example.mypokecatch.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.mypokecatch.Adapter.PokemonAdapter;
import com.example.mypokecatch.EditPokemonActivity;
import com.example.mypokecatch.PokeCatch;
import com.example.mypokecatch.R;
import com.example.mypokecatch.ViewModel.CustomPokemonViewModel;
import com.example.mypokecatch.ViewModel.InventoryViewModel;
import com.example.mypokecatch.ViewModel.PokemonViewModel;
import com.example.mypokecatch.database.CustomPokemonData.CustomPokemon;
import com.example.mypokecatch.database.InventoryData.Inventory;
import com.example.mypokecatch.database.InventoryPokemonData.InventoryWithCustomPokemons;
import com.example.mypokecatch.database.PokemonData.Pokemon;
import com.example.mypokecatch.database.iPokemon;

import java.util.ArrayList;
import java.util.List;

public class InventoryActivity extends AppCompatActivity
        implements PokemonAdapter.OnPokemonListener {

    private static final String TAG = "INVENTORY_ACTIVITY";
    private PokemonAdapter adapter;

    private InventoryViewModel modelInventory;
    private CustomPokemonViewModel modelCustomPokemon;
    private PokemonViewModel modelPokemon;

    private Inventory inventory;
    private InventoryWithCustomPokemons inventoryWithPokemons;
    private CustomPokemon customPokemon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);
        modelInventory = new ViewModelProvider(this).get(InventoryViewModel.class);
        modelCustomPokemon = new ViewModelProvider(this).get(CustomPokemonViewModel.class);
        modelPokemon = new ViewModelProvider(this).get(PokemonViewModel.class);

        if (modelInventory.isEmpty()) {
            createFirstPokemon();
            createFirstInventory();
            createInventoryPokemonLink();
        }

        modelInventory.getInventory().observe(this, inv -> {
            if (inv != null) {
                inventory = inv;
                setupView();
            }
        });


        adapter = new PokemonAdapter(new ArrayList<>(), this);
        RecyclerView recyclerView = findViewById(R.id.inventoryRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Log.i("TAG", modelInventory.getInventory().toString());
        recyclerView.setAdapter(this.adapter);
    }

    private void createInventoryPokemonLink() {
        modelInventory.insertPokemon(1, 1);
    }

    private void createFirstPokemon() {
        customPokemon = new CustomPokemon("Bulbasaur", "https://pokeapi.co/api/v2/pokemon/1/");
        modelCustomPokemon.insert(customPokemon);
    }

    private void createFirstInventory() {
        inventory = new Inventory();
        modelInventory.insert(inventory);
    }

    private void setupView() {
        modelInventory.getAllPokemon(inventory).observe(this, inventoryWithPokis -> {
            if (inventoryWithPokis != null) {
                inventoryWithPokemons = inventoryWithPokis;
                Log.i(TAG,"null or : " + inventoryWithPokemons.customPokemons.size());
                setupInventoryWithPokemons();
            }
        });
    }

    private void setupInventoryWithPokemons() {
        adapter.updateAdapter((List<iPokemon>) (Object) inventoryWithPokemons.customPokemons);
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
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
            case R.id.menu_settings:
                Intent settings = new Intent(this, SettingsActivity.class);
                startActivity(settings);
                Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onPokemonClick(int position) {
        Intent intent = new Intent(this, EditPokemonActivity.class);
        intent.putExtra("pokemon_position", adapter.getPokemonId(position));
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            CustomPokemon p = (CustomPokemon) data.getSerializableExtra("updated_pokemon");
            modelCustomPokemon.update(p);
        }
    }

}