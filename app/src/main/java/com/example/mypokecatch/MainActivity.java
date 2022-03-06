package com.example.mypokecatch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.example.mypokecatch.database.DataService;
import com.example.mypokecatch.database.PokemonViewModel;
import com.example.mypokecatch.database.Pokemon;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements PokemonAdapter.OnPokemonListener {

    private static final String TAG = "MAIN_ACTIVITY";
    private PokemonViewModel model;
    private PokemonAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter("custom-event-name"));

        List<Pokemon> pokemons = new ArrayList<>();
        adapter = new PokemonAdapter(pokemons, this);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        PokemonOverviewFragment frag = new PokemonOverviewFragment(adapter);
        transaction.add(R.id.overviewContainer, frag);
        transaction.commit();
        Button rBtn = findViewById(R.id.refreshBtn);
        rBtn.setOnClickListener(view -> {
            Log.d("btn", "" + model.getVMCount());
            RefreshData();
        });

        startDataService();
        this.model = new ViewModelProvider(this).get(PokemonViewModel.class);
    }

    private void RefreshData() {
        model.updateVM();
        if (model.getVMCount() > 0) {
            model.getAllPokemons().observe(this, pokis -> {
                adapter.updateAdapter(pokis);
                adapter.notifyDataSetChanged();
            });
        }
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
    public void onPokemonClick(int position) {
        Log.d(TAG, "onPokemonClick: " + position);
        Log.d(TAG, "onPokemonClick: " + adapter.getPokemonId(position));
        Intent intent = new Intent(this, EditPokemonActivity.class);
        intent.putExtra("pokemon_position", adapter.getPokemonId(position));
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            Pokemon p = (Pokemon) data.getSerializableExtra("updated_pokemon");
            this.model.update(p);
        }
    }

    @Override
    protected void onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
        super.onDestroy();
    }
}