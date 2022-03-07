package com.example.mypokecatch.database.PokemonData;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.mypokecatch.database.PokemonData.Pokemon;
import com.example.mypokecatch.database.PokemonDatabase;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class PokemonRepository {
    private PokemonDao PokemonDao;
    private LiveData<List<Pokemon>> allPokemons;

    public PokemonRepository(Application application) {
        PokemonDatabase database = PokemonDatabase.getInstance(application);
        PokemonDao = database.pokemonDao();
        allPokemons = PokemonDao.getAllPokemons();
    }

    public void insert(Pokemon Pokemon) {
        new InsertPokemonAsyncTask(PokemonDao).execute(Pokemon);
    }

    public void update(Pokemon Pokemon) {
        new UpdatePokemonAsyncTask(PokemonDao).execute(Pokemon);
    }

    public void delete(Pokemon Pokemon) {
        new DeletePokemonAsyncTask(PokemonDao).execute(Pokemon);
    }

    public boolean isPokemonTableEmpty() {
        Integer integer = null;
        try {
            integer = new IsPokemonTableEmptyAsyncTask(PokemonDao).execute().get();
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
            integer = new IsPokemonTableEmptyAsyncTask(PokemonDao).execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return integer;
    }

    public void deleteAllPokemons() {
        new DeleteAllPokemonsAsyncTask(PokemonDao).execute();
    }

    public LiveData<List<Pokemon>> getAllPokemons() {
        if (allPokemons == null){
            allPokemons = PokemonDao.getAllPokemons();
        }
        return allPokemons;
    }

    private static class InsertPokemonAsyncTask extends AsyncTask<Pokemon, Void, Void> {
        private PokemonDao PokemonDao;

        private InsertPokemonAsyncTask(PokemonDao PokemonDao) {
            this.PokemonDao = PokemonDao;
        }

        @Override
        protected Void doInBackground(Pokemon... Pokemons) {
            PokemonDao.insert(Pokemons[0]);
            return null;
        }
    }

    private static class UpdatePokemonAsyncTask extends AsyncTask<Pokemon, Void, Void> {
        private PokemonDao PokemonDao;

        private UpdatePokemonAsyncTask(PokemonDao PokemonDao) {
            this.PokemonDao = PokemonDao;
        }

        @Override
        protected Void doInBackground(Pokemon... Pokemons) {
            PokemonDao.update(Pokemons[0]);
            return null;
        }
    }

    private static class DeletePokemonAsyncTask extends AsyncTask<Pokemon, Void, Void> {
        private PokemonDao PokemonDao;

        private DeletePokemonAsyncTask(PokemonDao PokemonDao) {
            this.PokemonDao = PokemonDao;
        }

        @Override
        protected Void doInBackground(Pokemon... Pokemons) {
            PokemonDao.delete(Pokemons[0]);
            return null;
        }
    }

    private static class DeleteAllPokemonsAsyncTask extends AsyncTask<Void, Void, Void> {
        private PokemonDao PokemonDao;

        private DeleteAllPokemonsAsyncTask(PokemonDao PokemonDao) {
            this.PokemonDao = PokemonDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            PokemonDao.deleteAllPokemons();
            return null;
        }
    }

    private static class IsPokemonTableEmptyAsyncTask extends AsyncTask<Void, Void, Integer> {
        private PokemonDao PokemonDao;

        private IsPokemonTableEmptyAsyncTask(PokemonDao PokemonDao) {
            this.PokemonDao = PokemonDao;
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            return PokemonDao.getPokeTableSize();
        }
    }

    private static class GetAllPokemonsAsyncTask extends AsyncTask<Void, Void, Void> {
        private PokemonDao PokemonDao;

        private GetAllPokemonsAsyncTask(PokemonDao PokemonDao) {
            this.PokemonDao = PokemonDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            PokemonDao.deleteAllPokemons();
            return null;
        }
    }

    private static class GetAPokemonAsyncTask extends AsyncTask<Void, Void, Void> {
        private PokemonDao PokemonDao;

        private GetAPokemonAsyncTask(PokemonDao PokemonDao) {
            this.PokemonDao = PokemonDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            PokemonDao.deleteAllPokemons();
            return null;
        }
    }

}
