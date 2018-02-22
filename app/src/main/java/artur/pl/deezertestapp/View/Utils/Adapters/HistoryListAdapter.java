package artur.pl.deezertestapp.View.Utils.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.Predicate;

import java.util.ArrayList;
import java.util.List;

import artur.pl.deezertestapp.Model.Entity.HistoryItem;
import artur.pl.deezertestapp.R;
import artur.pl.deezertestapp.View.Utils.ItemClickListener;
import butterknife.BindView;
import butterknife.ButterKnife;

import static artur.pl.deezertestapp.Constants.SEARCH_ITEM;

/**
 * Created by artur on 26.01.2018.
 */


public class HistoryListAdapter extends RecyclerView.Adapter<HistoryListAdapter.ViewHolder> {
    private List<HistoryItem> historyItemList, filteredItemList;
    private ItemClickListener itemClickListener;

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.historyItemTextView)
        public TextView historyItemTextView;

        private ItemClickListener itemClickListener;
        public ViewHolder(View v, ItemClickListener itemClickListener) {
            super(v);
            this.itemClickListener = itemClickListener;
            ButterKnife.bind(this, v);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onItemClick(SEARCH_ITEM, historyItemTextView.getText().toString());
        }
    }

    public HistoryListAdapter(List<HistoryItem> historyItemList, ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
        this.historyItemList = historyItemList;
        this.filteredItemList = new ArrayList<>(historyItemList);
    }



    @Override
    public HistoryListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                          int viewType) {
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.history_list_item, parent, false);

        HistoryListAdapter.ViewHolder vh = new HistoryListAdapter.ViewHolder(v, itemClickListener);
        return vh;
    }

    @Override
    public void onBindViewHolder(HistoryListAdapter.ViewHolder holder, int position) {
        holder.historyItemTextView.setText(filteredItemList.get(position).getText());

    }

    @Override
    public int getItemCount() {
        if(filteredItemList == null)
            return 0;
        return filteredItemList.size();
    }

    public void setFilter(final String filter){
        filteredItemList = new ArrayList<>(historyItemList);
        if(filter == null || filter.isEmpty())
            return; // If filter is empty, just return whole history item list as filtered one.
        CollectionUtils.filter(filteredItemList, new Predicate() {
            @Override
            public boolean evaluate(Object o) {
                return ((HistoryItem) o).getText().startsWith(filter);
            }
        });
    }



}
