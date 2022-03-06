package com.example.mypokecatch.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.mypokecatch.Adapter.PokemonAdapter;
import com.example.mypokecatch.EditPokemonActivity;
import com.example.mypokecatch.PokeCatch;
import com.example.mypokecatch.PokemonOverviewFragment;
import com.example.mypokecatch.R;
import com.example.mypokecatch.ViewModel.PokemonViewModel;
import com.example.mypokecatch.model.Pokemon;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements PokemonAdapter.OnPokemonListener {

    private List<Pokemon> pokemons;
    private PokemonAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PokemonViewModel model = new ViewModelProvider(this).get(PokemonViewModel.class);

        model.getPokemons().observe(this, pokemons -> {
            // update UI
            this.pokemons = pokemons;
            adapter = new PokemonAdapter(pokemons, this);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            PokemonOverviewFragment frag = new PokemonOverviewFragment(pokemons, adapter);
            transaction.add(R.id.overviewContainer, frag);
            transaction.commit();
        });



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

        switch(item.getItemId())
        {
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
                Toast.makeText(this, "Your pokemons", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onPokemonClick(int position) {
        Pokemon p = pokemons.get(position);
        Intent intent = new Intent(this, EditPokemonActivity.class);
        startActivity(intent);
    }

}