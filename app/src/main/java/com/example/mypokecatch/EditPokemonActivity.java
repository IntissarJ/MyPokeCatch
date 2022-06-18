package com.example.mypokecatch;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mypokecatch.ViewModel.CustomPokemonViewModel;
import com.example.mypokecatch.database.CustomPokemonData.CustomPokemon;
import com.example.mypokecatch.ViewModel.PokemonViewModel;
import com.example.mypokecatch.database.CustomPokemonData.CustomPokemonRepository;
import com.example.mypokecatch.database.PokemonData.Pokemon;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class EditPokemonActivity extends AppCompatActivity {

    private static final String TAG = "EDIT_POKI";

    private CustomPokemon customPokemon = null;
    private List<Pokemon> pokemons = new ArrayList<>();

    private PokemonViewModel pokemonViewModel;
    private CustomPokemonViewModel customPokemonViewModel;
    private CustomPokemonRepository repository;
    private ImageView imageView;
    private Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_pokemon);
        customPokemonViewModel = new ViewModelProvider(this).get(CustomPokemonViewModel.class);
        imageView = findViewById(R.id.editPokeImage);
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

        button = findViewById(R.id.savePokemon);
        if (customPokemon != null) {
            name = customPokemon.getName();
            if(customPokemon.getImage() != null){
                Glide.with(imageView.getContext()).
                        load(customPokemon.getImage())
                        .into(imageView);
            } else {
                Glide.with(imageView.getContext()).
                        load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/" +
                                customPokemon.getPokemonId() + ".png")
                        .into(imageView);
            }
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
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE, 100);
                checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, 101);

            }
        });
        button.setOnClickListener(view -> {
            customPokemon.setName(textView.getText().toString());
            customPokemonViewModel.update(customPokemon);
            finish();
        });
    }

    public void checkPermission(String permission, int requestCode)
    {
        // Checking if permission is not granted
        if (ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[] { permission }, requestCode);
        } else {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent,1);
        }

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (data != null && data.getData() != null) {
            Uri selectedImage = data.getData();
            imageView.setImageURI(selectedImage);
            customPokemon.setImage(selectedImage.toString());
        }

    }
}