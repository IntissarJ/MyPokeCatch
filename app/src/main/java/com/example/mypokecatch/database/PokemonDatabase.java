package com.example.mypokecatch.database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.mypokecatch.database.InventoryData.Inventory;
import com.example.mypokecatch.database.InventoryData.InventoryDao;
import com.example.mypokecatch.database.InventoryPokemonData.InventoryPokemonCrossRef;
import com.example.mypokecatch.database.PokemonData.Pokemon;
import com.example.mypokecatch.database.PokemonData.PokemonDao;

@Database(entities = {Pokemon.class, Inventory.class, InventoryPokemonCrossRef.class},
        version = 9, exportSchema = false)
public abstract class PokemonDatabase extends RoomDatabase {

    private static PokemonDatabase instance;
    public abstract PokemonDao pokemonDao();
    public abstract InventoryDao inventoryDao();

    public static synchronized PokemonDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    PokemonDatabase.class, "pokemon_database")
                    .fallbackToDestructiveMigration()
//                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private InventoryDao inventoryDao;
        private PokemonDao pokemonDao;

        private PopulateDbAsyncTask(PokemonDatabase db) {
            inventoryDao = db.inventoryDao();
            pokemonDao = db.pokemonDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Pokemon p = new Pokemon("Kingler", "");
            Inventory inv = new Inventory();
            inventoryDao.insert(inv);
            pokemonDao.insert(p);
            InventoryPokemonCrossRef crossref = new InventoryPokemonCrossRef(1, 1);
            inventoryDao.insertInventoryWithPokemons(crossref);
            return null;
        }
    }


}
