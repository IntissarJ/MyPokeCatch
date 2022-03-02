package com.example.mypokecatch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


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

public class MainActivity extends AppCompatActivity {

    private List<Pokemon> pokemons = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setConnection();

        PokemonAdapter adapter = new PokemonAdapter(pokemons);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    void setConnection()
    {
        URL url;
        try
        {
            url = new URL("https://pokeapi.co/api/v2/pokemon?limit=898");
            onInitializePokemons(url);
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }
    }

    void onInitializePokemons(URL url)
    {
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
        RequestQueue queue;
        queue = Volley.newRequestQueue(this);
        queue.add(jsonObjectRequest);
    }
}