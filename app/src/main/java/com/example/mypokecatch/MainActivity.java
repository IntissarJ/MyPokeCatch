package com.example.mypokecatch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements PokemonAdapter.OnPokemonListener {

    private List<Pokemon> pokemons = new ArrayList<>();
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

        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchview = (SearchView) menuItem.getActionView();
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
    public void onPokemonClick(int position) {
        Pokemon p = pokemons.get(position);
        Intent intent = new Intent(this, EditPokemonActivity.class);
        startActivity(intent);
    }

}