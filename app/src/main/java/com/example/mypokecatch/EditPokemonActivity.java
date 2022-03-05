package com.example.mypokecatch;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class EditPokemonActivity extends AppCompatActivity {

    private Pokemon pokemon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_pokemon);
        this.pokemon = (Pokemon) getIntent().getSerializableExtra("pokemon_positions");

        TextView textView = findViewById(R.id.showPokemonName);
        EditText editInput = findViewById(R.id.editPokemonName);
        ImageView image = findViewById(R.id.editPokeImage);

        textView.setText(pokemon.getName());
        editInput.setText(pokemon.getName());
        Log.d(TAG, "onCreate: " + pokemon.getUrl());


    }
}