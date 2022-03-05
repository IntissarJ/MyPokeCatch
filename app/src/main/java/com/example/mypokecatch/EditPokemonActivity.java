package com.example.mypokecatch;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class EditPokemonActivity extends AppCompatActivity {

    private Pokemon pokemon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_pokemon);
        this.pokemon = (Pokemon) getIntent().getSerializableExtra("pokemon_positions");

        TextView textView = findViewById(R.id.showPokemonName);
        EditText editInput = findViewById(R.id.editPokemonName);
        ImageView imageView = findViewById(R.id.editPokeImage);
        Button button = findViewById(R.id.savePokemon);
        button.setOnClickListener(view -> {
            this.pokemon.setName(textView.getText().toString());
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("updated_pokemon", this.pokemon);
            setResult(RESULT_OK,  intent);
            finish();
        });

        textView.setText(pokemon.getName());
        editInput.setText(pokemon.getName());
        Log.d(TAG, "onCreate: " + pokemon.getUrl());
        // set pokemon image
        Glide.with(this).
                load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/"+ pokemon.getId() +".png")
                .into(imageView);

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
    }
}