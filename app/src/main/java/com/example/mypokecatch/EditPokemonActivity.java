package com.example.mypokecatch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mypokecatch.ViewModel.CustomPokemonViewModel;
import com.example.mypokecatch.database.CustomPokemonData.CustomPokemon;
import com.example.mypokecatch.ViewModel.PokemonViewModel;
import com.example.mypokecatch.database.PokemonData.Pokemon;

import java.util.ArrayList;
import java.util.List;

public class EditPokemonActivity extends AppCompatActivity {

    private static final String TAG = "EDIT_POKI";

    private CustomPokemon customPokemon = null;
    private List<Pokemon> pokemons = new ArrayList<>();

    private PokemonViewModel pokemonViewModel;
    private CustomPokemonViewModel customPokemonViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_pokemon);
        pokemonViewModel = new ViewModelProvider(this).get(PokemonViewModel.class);
        customPokemonViewModel = new ViewModelProvider(this).get(CustomPokemonViewModel.class);
        retrieveData();
    }

    private void retrieveData() {
        int pid = getIntent().getIntExtra("pokemon_position", 0);

        customPokemonViewModel.getPokemon(pid).observe(this, poki -> {
            if (poki != null) {
                customPokemon = poki;
                updateInterface();
            }
        });

    }

    private void updateInterface() {
        String name = "";

        TextView textView = findViewById(R.id.showPokemonName);
        EditText editInput = findViewById(R.id.editPokemonName);
        ImageView imageView = findViewById(R.id.editPokeImage);
        Button button = findViewById(R.id.savePokemon);

        if (customPokemon != null) {
            name = customPokemon.getName();
            Glide.with(this).
                    load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/"
                            + customPokemon.getPokemonId() + ".png")
                    .into(imageView);
        } else {
            name = "default";
        }

        textView.setText(name);
        editInput.setText(name);
        editInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                textView.setText(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        button.setOnClickListener(view -> {
            this.customPokemon.setName(textView.getText().toString());
            customPokemonViewModel.update(this.customPokemon);
            finish();
        });
    }
}