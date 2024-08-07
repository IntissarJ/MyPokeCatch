package com.example.mypokecatch.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.mypokecatch.R;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getFragmentManager().beginTransaction().replace(android.R.id.content, new SettingsFragment()).commit();
    }

    public static class SettingsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener
    {
        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.settings_main);

        }
        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        item.setVisible(false);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_pokedex:
                Intent home = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(home);
                Toast.makeText(this, "Pokedex", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menu_pokecatch:
                Intent pokeCatching = new Intent(this, PokeCatchActivity.class);
                startActivity(pokeCatching);
                Toast.makeText(this, "PokeCatch", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menu_inventory:
                Intent inventory = new Intent(this, InventoryActivity.class);
                startActivity(inventory);
                Toast.makeText(this, "Your pokemons", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menu_settings:
                Intent settings = new Intent(this, SettingsActivity.class);
                startActivity(settings);
                Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}