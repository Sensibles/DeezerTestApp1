package artur.pl.deezertestapp.View.Utils.Adapters;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.MemoryCategory;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;
import java.util.Random;

import artur.pl.deezertestapp.Model.Entity.Artist;
import artur.pl.deezertestapp.Model.Entity.HistoryItem;
import artur.pl.deezertestapp.R;
import artur.pl.deezertestapp.View.Utils.ItemClickListener;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static artur.pl.deezertestapp.Constants.ARTIST_FAV;
import static artur.pl.deezertestapp.Constants.ARTIST_ITEM;

/**
 * Created by artur on 01.02.2018.
 */

public class ArtistListAdapter extends RecyclerView.Adapter<ArtistListAdapter.ViewHolder> {
    List<Artist> artistsItemList;
    private ItemClickListener itemClickListener;
    private long relative;  //used to set high of element relatively to biggest fan base artist in current artists set.

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.artistTextView)
        public TextView artistTextView;
        @BindView(R.id.artistBgImageView)
        public ImageView artistBgImageView;
        @BindView(R.id.artistCardView)
        public CardView artistCardView;
        @BindView(R.id.favouriteImageButton)
        public ImageButton favouriteImageButton;

        private View v;
        private ArtistListAdapter artistListAdapter;
        private ItemClickListener itemClickListener;
        public ViewHolder(View v, ItemClickListener itemClickListener, ArtistListAdapter artistListAdapter) {
            super(v);
            ButterKnife.bind(this, v);
            this.artistListAdapter = artistListAdapter;
            this.itemClickListener = itemClickListener;
            this.v = v;
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Artist artist = artistListAdapter.artistsItemList.get(getAdapterPosition());
            itemClickListener.onItemClick(ARTIST_ITEM, artist);
        }

        @OnClick(R.id.favouriteImageButton)
        public void onFavButtonClick(View v){
            Artist artist = artistListAdapter.artistsItemList.get(getAdapterPosition());
            itemClickListener.onItemClick(ARTIST_FAV, artist);
            Log.d("FAB", "CLICKED");
        }

    }

    public ArtistListAdapter(List<Artist> artistsItemList, ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
        this.artistsItemList = artistsItemList;
        relative = setRelativeValue();
    }


    private int getElementHeightByFans(int min, int max, int position){
        int value = Math.round(artistsItemList.get(position).getNumberOfFans() / relative);
        return value>(max-min)?min+(max-min):min+value;
    }

    private long setRelativeValue(){
        int max = 0;
        for(Artist artist : artistsItemList){
            if(max < artist.getNumberOfFans())
                max = artist.getNumberOfFans();
        }
        return Math.round(max/200.0);
    }

    @Override
    public ArtistListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                            int viewType) {
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.artist_list_item, parent, false);

        ArtistListAdapter.ViewHolder vh = new ArtistListAdapter.ViewHolder(v, itemClickListener, this);
        return vh;
    }

    @Override
    public void onBindViewHolder(ArtistListAdapter.ViewHolder holder, int position) {
        holder.artistTextView.setText(artistsItemList.get(position).getName());
        holder.artistCardView.getLayoutParams().height = getElementHeightByFans(200,450, position);
        if(artistsItemList.get(position).isFavorite())
            holder.favouriteImageButton.setImageResource(R.drawable.ic_favorite_white_24dp);
        else
            holder.favouriteImageButton.setImageResource(R.drawable.ic_favorite_border_white_24dp);
        Glide.with(holder.v)
                .load(artistsItemList.get(position).getThumbnailUrl())
                .apply(new RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(R.mipmap.ic_launcher)
                        .centerInside())
                .into(holder.artistBgImageView);

    }

    @Override
    public int getItemCount() {
        if(artistsItemList == null)
            return 0;
        return artistsItemList.size();
    }

    public List<Artist> getArtistsItemList() {
        return artistsItemList;
    }

    public void setArtistsItemList(List<Artist> artistsItemList) {
        this.artistsItemList = artistsItemList;
    }



}
