package com.example.mypokecatch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mypokecatch.database.Pokemon;
import com.example.mypokecatch.database.PokemonViewModel;

import java.util.ArrayList;
import java.util.List;

public class EditPokemonActivity extends AppCompatActivity {

    private static final String TAG = "EDIT_POKI";
    private Pokemon pokemon;
    private List<Pokemon> ps = new ArrayList<>();
    private PokemonViewModel vm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_pokemon);
        vm =  new ViewModelProvider(this).get(PokemonViewModel.class);
        retrieveData();
        updateInterface();


    }

    private void retrieveData(){
        int pid = getIntent().getIntExtra("pokemon_position", 0);
        boolean ready = vm.updateVM();
        if (ready) {
            LiveData<List<Pokemon>> data = vm.getAllPokemons();
            if (data != null){
                data.observe(this, new Observer<List<Pokemon>>() {
                    @Override
                    public void onChanged(List<Pokemon> pokis) {
                        if (pokis != null && pokis.size() > 0){
                            ps = pokis;
                            setupPokemon(pid);
                        }
                    }
                });
            } else {
                Log.d(TAG, "retrievePokemon: No data found");
            }
        }
    }

    private void setupPokemon(int pid) {
        for (Pokemon p : ps) {
            if (p.getId() == pid){
                this.pokemon = p;
                updateInterface();
                break;
            }
        }
    }

    private void updateInterface(){
        TextView textView = findViewById(R.id.showPokemonName);
        EditText editInput = findViewById(R.id.editPokemonName);
        ImageView imageView = findViewById(R.id.editPokeImage);
        Button button = findViewById(R.id.savePokemon);

        String name = "";
        if (pokemon != null){
            name = pokemon.getName();
            Glide.with(this).
                    load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/"+ pokemon.getId() +".png")
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
            this.pokemon.setName(textView.getText().toString());
            vm.update(this.pokemon);
            finish();
        });
    }
}