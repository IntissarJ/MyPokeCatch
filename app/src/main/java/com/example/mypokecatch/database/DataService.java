package com.example.mypokecatch.database;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.mypokecatch.database.PokemonRepository;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;

public class DataService extends Service {

    private PokemonRepository repo;
    static String pokis_been_filled = "pokis_been_filled";

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        this.repo = new PokemonRepository(this.getApplication());
        if (setConnection()){
            Intent bent = new Intent("custom-event-name");
            Boolean b = repo.isPokemonTableEmpty();
            String k = b ? "true" : "false";
            bent.putExtra("message", k);
            LocalBroadcastManager.getInstance(this).sendBroadcast(bent);
            stopSelf();
        };
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private boolean setConnection()
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
        return false;
    }

    private boolean onInitializePokemons(URL url)
    {
//        repo.deleteAllPokemons();

        if (repo.pokeTableCount() > 0) {
            return true;
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url.toString(), null,
                response -> {
                    try {
                        JSONArray results = response.getJSONArray("results");
                        for (int i = 0; i < results.length(); i++) {
                            JSONObject result = results.getJSONObject(i);
                            String name = result.getString("name");

                            repo.insert(new com.example.mypokecatch.database.Pokemon(
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
        Log.d("sender", repo.pokeTableCount().toString());
        return true;
    }
}