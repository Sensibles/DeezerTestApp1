package artur.pl.deezertestapp.ViewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import artur.pl.deezertestapp.Model.Entity.HistoryItem;
import artur.pl.deezertestapp.Repository.HistoryItemRepository;

/**
 * Created by artur on 26.01.2018.
 */

public class SearchViewModel extends ViewModel{
    private HistoryItemRepository historyItemRepository;

    public SearchViewModel(HistoryItemRepository historyItemRepository){
        this.historyItemRepository = historyItemRepository;
    }

    public LiveData<List<HistoryItem>> getHistoryItems(){
            return historyItemRepository.getAllHistoryItems();
    }

    public void insertHistoryItem(HistoryItem historyItem){
        historyItemRepository.insertHistoryItem(historyItem);
    }

    public void clearHistory(){
        historyItemRepository.deleteAllHistoryItems();
    }



}
