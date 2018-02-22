package artur.pl.deezertestapp.View.Utils.Adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import artur.pl.deezertestapp.Model.Entity.Album;
import artur.pl.deezertestapp.R;

/**
 * Created by artur on 14.02.2018.
 */

public class AlbumListAdapter extends PagerAdapter {


    Context context;
    List<Album> albumList;
    LayoutInflater mLayoutInflater;
    ImageView imageView;

    public AlbumListAdapter(Context context, List<Album> albumList) {
        this.albumList = albumList;
        this.context = context;
        mLayoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return albumList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.album_list_item, container, false);
        imageView = (ImageView) itemView.findViewById(R.id.artistBgImageView);
        Glide.with(context)
                .load(albumList.get(position).getCoverUrl())
                .apply(new RequestOptions()
                        .placeholder(R.mipmap.ic_launcher)
                        .centerInside()
                        .diskCacheStrategy(DiskCacheStrategy.NONE))
                .into(imageView);
        imageView.setTag(albumList.get(position));
        TextView textView = (TextView) itemView.findViewById(R.id.artistTextView);
        textView.setText(albumList.get(position).getLabel());
        System.out.println("INSISTATE ITEM: " +position + "  " + albumList.get(position));
        container.addView(itemView);

        return itemView;
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        imageView = (ImageView)((LinearLayout)object).findViewById(R.id.artistBgImageView);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }




}