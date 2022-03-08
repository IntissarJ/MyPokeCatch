package com.example.mypokecatch.database.CustomPokemonData;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.mypokecatch.database.PokemonDatabase;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class CustomPokemonRepository {

    private CustomPokemonDao customPokemonDao;
    private LiveData<List<CustomPokemon>> allPokemons;

    public CustomPokemonRepository(Application application) {
        PokemonDatabase database = PokemonDatabase.getInstance(application);
        customPokemonDao = database.customPokemonDao();
        allPokemons = customPokemonDao.getAllPokemons();
    }

    public LiveData<List<CustomPokemon>> getAllPokemons() {
        if (allPokemons == null){
            allPokemons = customPokemonDao.getAllPokemons();
        }
        return allPokemons;
    }

    public LiveData<CustomPokemon> getPokemon(int id) {
        return customPokemonDao.getAPokemon(id);
    }

    public void insert(CustomPokemon CustomPokemon) {
        new InsertPokemonAsyncTask(customPokemonDao).execute(CustomPokemon);
    }

    public void update(CustomPokemon CustomPokemon) {
        new UpdatePokemonAsyncTask(customPokemonDao).execute(CustomPokemon);
    }

    public void delete(CustomPokemon CustomPokemon) {
        new DeletePokemonAsyncTask(customPokemonDao).execute(CustomPokemon);
    }

    public boolean isPokemonTableEmpty() {
        Integer integer = null;
        try {
            integer = new IsPokemonTableEmptyAsyncTask(customPokemonDao).execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return integer <= 0;
    }

    public Integer pokeTableCount() {
        Integer integer = null;
        try {
            integer = new IsPokemonTableEmptyAsyncTask(customPokemonDao).execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return integer;
    }

    public void deleteAllPokemons() {
        new DeleteAllPokemonsAsyncTask(customPokemonDao).execute();
    }



    private static class InsertPokemonAsyncTask extends AsyncTask<CustomPokemon, Void, Void> {
        private CustomPokemonDao customPokemonDao;

        private InsertPokemonAsyncTask(CustomPokemonDao CustomPokemonDao) {
            this.customPokemonDao = CustomPokemonDao;
        }

        @Override
        protected Void doInBackground(CustomPokemon... customPokemons) {
            customPokemonDao.insert(customPokemons[0]);
            return null;
        }
    }

    private static class UpdatePokemonAsyncTask extends AsyncTask<CustomPokemon, Void, Void> {
        private CustomPokemonDao customPokemonDao;

        private UpdatePokemonAsyncTask(CustomPokemonDao CustomPokemonDao) {
            this.customPokemonDao = CustomPokemonDao;
        }

        @Override
        protected Void doInBackground(CustomPokemon... customPokemons) {
            customPokemonDao.update(customPokemons[0]);
            return null;
        }
    }

    private static class DeletePokemonAsyncTask extends AsyncTask<CustomPokemon, Void, Void> {
        private CustomPokemonDao customPokemonDao;

        private DeletePokemonAsyncTask(CustomPokemonDao CustomPokemonDao) {
            this.customPokemonDao = CustomPokemonDao;
        }

        @Override
        protected Void doInBackground(CustomPokemon... customPokemons) {
            customPokemonDao.delete(customPokemons[0]);
            return null;
        }
    }

    private static class DeleteAllPokemonsAsyncTask extends AsyncTask<Void, Void, Void> {
        private CustomPokemonDao customPokemonDao;

        private DeleteAllPokemonsAsyncTask(CustomPokemonDao CustomPokemonDao) {
            this.customPokemonDao = CustomPokemonDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            customPokemonDao.deleteAllPokemons();
            return null;
        }
    }

    private static class IsPokemonTableEmptyAsyncTask extends AsyncTask<Void, Void, Integer> {
        private CustomPokemonDao customPokemonDao;

        private IsPokemonTableEmptyAsyncTask(CustomPokemonDao CustomPokemonDao) {
            this.customPokemonDao = CustomPokemonDao;
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            return customPokemonDao.getPokeTableSize();
        }
    }

}
