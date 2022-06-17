package com.example.mypokecatch.database.PokemonData;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.mypokecatch.database.PokemonDatabase;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class PokemonRepository {
    private PokemonDao pokemonDao;
    private LiveData<List<Pokemon>> allPokemons;

    public PokemonRepository(Application application) {
        PokemonDatabase database = PokemonDatabase.getInstance(application);
        pokemonDao = database.pokemonDao();
        allPokemons = pokemonDao.getAllPokemons();
    }

    public LiveData<Pokemon> getPokemon(int id) {
        return pokemonDao.getPokemon(id);
    }

    public LiveData<Pokemon> getAPokemon() {
        return pokemonDao.getAPokemon();
    }

    public void insert(Pokemon pokemon) {
        new InsertPokemonAsyncTask(pokemonDao).execute(pokemon);
    }

    public void update(Pokemon pokemon) {
        new UpdatePokemonAsyncTask(pokemonDao).execute(pokemon);
    }

    public void delete(Pokemon pokemon) {
        new DeletePokemonAsyncTask(pokemonDao).execute(pokemon);
    }

    public boolean isPokemonTableEmpty() {
        Integer integer = null;
        try {
            integer = new IsPokemonTableEmptyAsyncTask(pokemonDao).execute().get();
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
            integer = new IsPokemonTableEmptyAsyncTask(pokemonDao).execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return integer;
    }

    public void deleteAllPokemons() {
        new DeleteAllPokemonsAsyncTask(pokemonDao).execute();
    }

    public LiveData<List<Pokemon>> getAllPokemons() {
        if (allPokemons == null){
            allPokemons = pokemonDao.getAllPokemons();
        }
        return allPokemons;
    }

    private static class InsertPokemonAsyncTask extends AsyncTask<Pokemon, Void, Void> {
        private PokemonDao pokemonDao;

        private InsertPokemonAsyncTask(PokemonDao pokemonDao) {
            this.pokemonDao = pokemonDao;
        }

        @Override
        protected Void doInBackground(Pokemon... pokemons) {
            pokemonDao.insert(pokemons[0]);
            return null;
        }
    }

    private static class UpdatePokemonAsyncTask extends AsyncTask<Pokemon, Void, Void> {
        private PokemonDao pokemonDao;

        private UpdatePokemonAsyncTask(PokemonDao pokemonDao) {
            this.pokemonDao = pokemonDao;
        }

        @Override
        protected Void doInBackground(Pokemon... pokemons) {
            pokemonDao.update(pokemons[0]);
            return null;
        }
    }

    private static class DeletePokemonAsyncTask extends AsyncTask<Pokemon, Void, Void> {
        private PokemonDao pokemonDao;

        private DeletePokemonAsyncTask(PokemonDao pokemonDao) {
            this.pokemonDao = pokemonDao;
        }

        @Override
        protected Void doInBackground(Pokemon... pokemons) {
            pokemonDao.delete(pokemons[0]);
            return null;
        }
    }

    private static class DeleteAllPokemonsAsyncTask extends AsyncTask<Void, Void, Void> {
        private PokemonDao pokemonDao;

        private DeleteAllPokemonsAsyncTask(PokemonDao pokemonDao) {
            this.pokemonDao = pokemonDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            pokemonDao.deleteAllPokemons();
            return null;
        }
    }

    private static class IsPokemonTableEmptyAsyncTask extends AsyncTask<Void, Void, Integer> {
        private PokemonDao pokemonDao;

        private IsPokemonTableEmptyAsyncTask(PokemonDao pokemonDao) {
            this.pokemonDao = pokemonDao;
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            return pokemonDao.getPokeTableSize();
        }
    }

    private static class GetAllPokemonsAsyncTask extends AsyncTask<Void, Void, Void> {
        private PokemonDao pokemonDao;

        private GetAllPokemonsAsyncTask(PokemonDao pokemonDao) {
            this.pokemonDao = pokemonDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            pokemonDao.deleteAllPokemons();
            return null;
        }
    }

    private static class GetAPokemonAsyncTask extends AsyncTask<Void, Void, Void> {
        private PokemonDao pokemonDao;

        private GetAPokemonAsyncTask(PokemonDao pokemonDao) {
            this.pokemonDao = pokemonDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            pokemonDao.deleteAllPokemons();
            return null;
        }
    }

}
