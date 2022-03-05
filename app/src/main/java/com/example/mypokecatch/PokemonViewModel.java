package com.example.mypokecatch;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

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

public class PokemonViewModel extends AndroidViewModel {

    private MutableLiveData<List<Pokemon>> pokemons;

    public PokemonViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<Pokemon>> getPokemons() {
        if (pokemons == null) {
            pokemons = new MutableLiveData<>();
            loadUsers();
        }
        return pokemons;
    }

    public Pokemon getPokemon(int id){
        if (pokemons.getValue().size() < id || id < 0) return null;
        return pokemons.getValue().get(id);
    }

    private void loadUsers() {
        // Do an asynchronous operation to fetch users.
        List<Pokemon> pks = setConnection();
        pokemons.setValue(pks);
    }

    private List<Pokemon> setConnection()
    {
        URL url;
        try
        {
            url = new URL("https://pokeapi.co/api/v2/pokemon?limit=100");
            return onInitializePokemons(url);
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    private List<Pokemon> onInitializePokemons(URL url)
    {
        List<Pokemon> pks = new ArrayList<>();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url.toString(), null,
                response -> {
                    try {
                        JSONArray results = response.getJSONArray("results");
                        for (int i = 0; i < results.length(); i++) {
                            JSONObject result = results.getJSONObject(i);
                            String name = result.getString("name");
                            pks.add(new Pokemon(
                                    name.substring(0, 1).toUpperCase() + name.substring(1),
                                    result.getString("url")
                            ));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                volleyError -> Toast.makeText(this.getApplication(), volleyError.getMessage(), Toast.LENGTH_SHORT).show()
        );

        RequestQueue queue = Volley.newRequestQueue(this.getApplication());
        queue.add(jsonObjectRequest);
        return pks;
    }

}
