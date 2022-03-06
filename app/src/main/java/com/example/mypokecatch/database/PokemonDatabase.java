package com.example.mypokecatch.database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Pokemon.class}, version = 2)
public abstract class PokemonDatabase extends RoomDatabase {

    private static PokemonDatabase instance;
    public abstract PokemonDao pokemonDao();

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
        }
    };

    // TODO Maak service
    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private PokemonDao PokemonDao;

        private PopulateDbAsyncTask(PokemonDatabase db) {
            PokemonDao = db.pokemonDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            return null;
        }
    }
    


}
