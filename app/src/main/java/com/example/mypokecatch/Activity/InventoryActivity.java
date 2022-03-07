package com.example.mypokecatch.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.mypokecatch.Adapter.PokemonAdapter;
import com.example.mypokecatch.EditPokemonActivity;
import com.example.mypokecatch.PokeCatch;
import com.example.mypokecatch.R;
import com.example.mypokecatch.ViewModel.InventoryViewModel;
import com.example.mypokecatch.ViewModel.PokemonViewModel;
import com.example.mypokecatch.database.InventoryData.Inventory;
import com.example.mypokecatch.database.InventoryPokemonData.InventoryWithPokemons;
import com.example.mypokecatch.database.PokemonData.Pokemon;

import java.util.ArrayList;
import java.util.List;

public class InventoryActivity extends AppCompatActivity implements PokemonAdapter.OnPokemonListener {

    private static final String TAG = "INVENTORY_ACTIVITY";
    private PokemonAdapter adapter;
    private InventoryViewModel modelInventory;
    private Inventory inventory;
    private InventoryWithPokemons inventoryWithPokemons;
    private PokemonViewModel modelPokemon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);
        modelInventory = new ViewModelProvider(this).get(InventoryViewModel.class);
        modelPokemon = new ViewModelProvider(this).get(PokemonViewModel.class);
        adapter = new PokemonAdapter(new ArrayList<>(), this);
        RecyclerView recyclerView = findViewById(R.id.inventoryRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(this.adapter);

        modelInventory.getInventory().observe(this, inv -> {
            if (inv != null) {
                setupView(inv);
            }
        });
    }

    private void setupView(Inventory inventory) {
        this.inventory = inventory;
        LiveData<InventoryWithPokemons> pokis = modelInventory.getAllPokemon(inventory);
        pokis.observe(this, inventoryWithPokis -> {
            if (inventoryWithPokis != null) {
                setupInventoryWithPokemons(inventoryWithPokis);
            }
        });
    }

    private void setupInventoryWithPokemons(InventoryWithPokemons inv) {
        inventoryWithPokemons = inv;
        adapter.updateAdapter(inventoryWithPokemons.pokemons);
        adapter.notifyDataSetChanged();
        Log.d(TAG, "setupInventoryWithPokemons: Adapter setup");
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
                Toast.makeText(this, "Your pokemons", Toast.LENGTH_SHORT).show();
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
            Pokemon p = (Pokemon) data.getSerializableExtra("updated_pokemon");
            modelPokemon.update(p);
        }
    }

}