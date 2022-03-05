package com.example.mypokecatch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;


import android.content.Intent;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements PokemonAdapter.OnPokemonListener {

    private List<Pokemon> pokemons = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PokemonViewModel model = new ViewModelProvider(this).get(PokemonViewModel.class);

        model.getPokemons().observe(this, pokemons -> {
            // update UI
            this.pokemons = pokemons;
            PokemonAdapter adapter = new PokemonAdapter(pokemons, this);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            PokemonOverviewFragment frag = new PokemonOverviewFragment(pokemons, adapter);
            transaction.add(R.id.overviewContainer, frag);
            transaction.commit();
        });

//        PokemonAdapter adapter = new PokemonAdapter(pokemons, this);
//        RecyclerView recyclerView = findViewById(R.id.recyclerView);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setAdapter(adapter);

    }

    void setConnection() {
        URL url;
        try {
            url = new URL("https://pokeapi.co/api/v2/pokemon?limit=100");
            onInitializePokemons(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    void onInitializePokemons(URL url) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url.toString(), null,
                response -> {
                    try {
                        JSONArray results = response.getJSONArray("results");
                        for (int i = 0; i < results.length(); i++) {
                            JSONObject result = results.getJSONObject(i);
                            String name = result.getString("name");
                            pokemons.add(new Pokemon(
                                    name.substring(0, 1).toUpperCase() + name.substring(1),
                                    result.getString("url")
                            ));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> {
        });

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jsonObjectRequest);
    }

    @Override
    public void onPokemonClick(int position) {
        Pokemon p = pokemons.get(position);
        Intent intent = new Intent(this, EditPokemonActivity.class);
        startActivity(intent);
    }

}