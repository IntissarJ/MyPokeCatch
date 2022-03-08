package com.example.mypokecatch.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.mypokecatch.Adapter.PokeDexAdapter;
import com.example.mypokecatch.PokeCatch;
import com.example.mypokecatch.R;
import com.example.mypokecatch.ViewModel.PokemonViewModel;
import com.example.mypokecatch.database.CustomPokemonData.CustomPokemon;
import com.example.mypokecatch.database.DataService;
import com.example.mypokecatch.database.iPokemon;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity{

    private static final String TAG = "MAIN_ACTIVITY";
    private PokeDexAdapter adapter;
    private PokemonViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startDataService();

        model = new ViewModelProvider(this).get(PokemonViewModel.class);
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter("custom-event-name"));

        //        pokedexRecycler
        adapter = new PokeDexAdapter(new ArrayList<>());
        RecyclerView recyclerView = findViewById(R.id.pokedexRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(this.adapter);

        model.getAllPokemons().observe(this , pokis -> {
            if (pokis != null) {
                adapter.updateAdapter((List<iPokemon>) (Object) pokis);
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void RefreshData() {
        model.updateVM();
    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            String message = intent.getStringExtra("message");
            Log.d("receiver", "Got message: " + message);
            if (message.equals("false")) {
                boolean result = model.updateVM();
                if (result) {
                    RefreshData();
                }
            }
        }
    };

    public void startDataService() {
        Intent intent = new Intent(getApplication(), DataService.class);
        startService(intent);
    }

    @Override
    protected void onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        // Search menu item
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchview = (SearchView) searchItem.getActionView();
        searchview.setQueryHint("Type voor pokemon naam");
        searchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                adapter.getFilter().filter(query);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_pokedex:
                Intent home = new Intent(this, MainActivity.class);
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

}