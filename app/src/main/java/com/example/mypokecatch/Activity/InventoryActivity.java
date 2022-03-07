package com.example.mypokecatch.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.mypokecatch.Adapter.PokemonAdapter;
import com.example.mypokecatch.PokeCatch;
import com.example.mypokecatch.R;
import com.example.mypokecatch.ViewModel.InventoryViewModel;
import com.example.mypokecatch.ViewModel.PokemonViewModel;
import com.example.mypokecatch.database.InventoryData.Inventory;
import com.example.mypokecatch.database.PokemonData.Pokemon;

import java.util.ArrayList;
import java.util.List;

public class InventoryActivity extends AppCompatActivity implements PokemonAdapter.OnPokemonListener {

    private static final String TAG = "INVENTORY_ACTIVITY";
    private PokemonAdapter adapter;
    private InventoryViewModel model;
    private Inventory inventory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);
        model = new ViewModelProvider(this).get(InventoryViewModel.class);

        model.getInventory().observe(this, new Observer<Inventory>() {
            @Override
            public void onChanged(Inventory inv) {
                if (inv != null){
                    inventory = inv;
                    Log.d(TAG, "onChanged: " + inventory.getInventoryId());
                } else {
                    inventory = new Inventory();
                    model.insert(inventory);
                }
            }
        });

        List<Pokemon> pokemons = new ArrayList<>();
        adapter = new PokemonAdapter(pokemons, this);
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