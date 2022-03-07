package com.example.mypokecatch.database.InventoryData;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.mypokecatch.database.InventoryPokemonData.InventoryPokemonCrossRef;
import com.example.mypokecatch.database.PokemonData.Pokemon;
import com.example.mypokecatch.database.PokemonDatabase;

import java.util.concurrent.ExecutionException;

public class InventoryRepository {

    private InventoryDao InventoryDao;
    private LiveData<Inventory> inventory;

    public InventoryRepository(Application application) {
        PokemonDatabase database = PokemonDatabase.getInstance(application);
        InventoryDao = database.inventoryDao();
        inventory = InventoryDao.getInventory();
    }

    public void insert(Inventory Inventory) {
        new InventoryRepository.InsertInventoryAsyncTask(InventoryDao).execute(Inventory);
    }

    public void insertPokemon(InventoryPokemonCrossRef crossref){
        new InventoryRepository.InsertPokemonAsyncTask(InventoryDao).execute(crossref);
    }

    public void update(Inventory Inventory) {
        new InventoryRepository.UpdateInventoryAsyncTask(InventoryDao).execute(Inventory);
    }

    public void delete(Inventory Inventory) {
        new InventoryRepository.DeleteInventoryAsyncTask(InventoryDao).execute(Inventory);
    }

    public boolean isInventoryTableEmpty() {
        Integer integer = null;
        try {
            integer = new InventoryRepository.IsInventoryTableEmptyAsyncTask(InventoryDao).execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return integer <= 0;
    }

    public Integer inventoryTableCount() {
        Integer integer = null;
        try {
            integer = new InventoryRepository.IsInventoryTableEmptyAsyncTask(InventoryDao).execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return integer;
    }

    public LiveData<Inventory> getInventory() {
        if (inventory == null) {
            inventory = InventoryDao.getInventory();
        }
        return inventory;
    }

    public void deletePokemon(InventoryPokemonCrossRef inventoryPokemonCrossRef) {
        new InventoryRepository.DeletePokemonAsyncTask(InventoryDao).execute(inventoryPokemonCrossRef);
    }


    private static class InsertInventoryAsyncTask extends AsyncTask<Inventory, Void, Void> {
        private InventoryDao InventoryDao;

        private InsertInventoryAsyncTask(InventoryDao InventoryDao) {
            this.InventoryDao = InventoryDao;
        }

        @Override
        protected Void doInBackground(Inventory... Inventorys) {
            InventoryDao.insert(Inventorys[0]);
            return null;
        }
    }

    private static class InsertPokemonAsyncTask extends AsyncTask<InventoryPokemonCrossRef, Void, Void> {
        private InventoryDao InventoryDao;

        private InsertPokemonAsyncTask(InventoryDao InventoryDao) {
            this.InventoryDao = InventoryDao;
        }

        @Override
        protected Void doInBackground(InventoryPokemonCrossRef... crossref) {
            InventoryDao.insertInventoryWithPokemons(crossref[0]);
            return null;
        }
    }

    private static class UpdateInventoryAsyncTask extends AsyncTask<Inventory, Void, Void> {
        private InventoryDao InventoryDao;

        private UpdateInventoryAsyncTask(InventoryDao InventoryDao) {
            this.InventoryDao = InventoryDao;
        }

        @Override
        protected Void doInBackground(Inventory... Inventorys) {
            InventoryDao.update(Inventorys[0]);
            return null;
        }
    }

    private static class DeleteInventoryAsyncTask extends AsyncTask<Inventory, Void, Void> {
        private InventoryDao InventoryDao;

        private DeleteInventoryAsyncTask(InventoryDao InventoryDao) {
            this.InventoryDao = InventoryDao;
        }

        @Override
        protected Void doInBackground(Inventory... Inventorys) {
            InventoryDao.delete(Inventorys[0]);
            return null;
        }
    }

    public class DeletePokemonAsyncTask extends AsyncTask<InventoryPokemonCrossRef, Void, Integer>  {
        private InventoryDao InventoryDao;

        public DeletePokemonAsyncTask(InventoryDao inventoryDao) {
            this.InventoryDao = InventoryDao;
        }

        @Override
        protected Integer doInBackground(InventoryPokemonCrossRef... crossref) {
            InventoryDao.deletePokemon(crossref[0]);
            return null;
        }
    }

    private static class IsInventoryTableEmptyAsyncTask extends AsyncTask<Void, Void, Integer> {
        private InventoryDao InventoryDao;

        private IsInventoryTableEmptyAsyncTask(InventoryDao InventoryDao) {
            this.InventoryDao = InventoryDao;
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            return InventoryDao.getInventoryTableSize();
        }
    }


}
