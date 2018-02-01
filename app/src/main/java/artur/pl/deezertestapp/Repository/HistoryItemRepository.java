package artur.pl.deezertestapp.Repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

import artur.pl.deezertestapp.DeezerTestApp;
import artur.pl.deezertestapp.Model.Dao.HistoryItemDao;
import artur.pl.deezertestapp.Model.Entity.HistoryItem;

/**
 * Created by artur on 26.01.2018.
 */

public class HistoryItemRepository {

    private Application application;
    private HistoryItemDao historyItemDao;

    public HistoryItemRepository(Application application, HistoryItemDao historyItemDao) {
        this.application = application;
        this.historyItemDao = historyItemDao;
    }

    public LiveData<List<HistoryItem>> getAllHistoryItems(){
       return historyItemDao.getAllHistoryItems();
    }

    public LiveData<List<HistoryItem>> getHistoryItemsForText(String text){
        return historyItemDao.getHistoryItemsForText(text);
    }

    public void insertHistoryItem(HistoryItem historyItem){
        new AddHistoryItem().execute(historyItem);
    }

    public void deleteAllHistoryItems(){
        new DeleteAllHistoryItems().execute();
    }
    private class DeleteAllHistoryItems extends AsyncTask<Object, Void, Void> {
        @Override
        protected Void doInBackground(Object... o){
            historyItemDao.deleteAllHistoryItems();
            return null;
        }
    }

    private class AddHistoryItem extends AsyncTask<HistoryItem, Void, Void> {
        @Override
        protected Void doInBackground(HistoryItem... historyItems){
            historyItemDao.insertHistoryItem(historyItems[0]);
            return null;
        }
    }
}
