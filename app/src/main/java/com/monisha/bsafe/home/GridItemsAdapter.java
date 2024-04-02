package com.monisha.bsafe.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.RecyclerView;

import com.monisha.bsafe.R;
import com.monisha.bsafe.home.data.GridItemClickListener;
import com.monisha.bsafe.home.data.GridItem;

import java.util.List;

public class GridItemsAdapter extends RecyclerView.Adapter<GridItemsAdapter.ViewHolder> {

    private final List<GridItem> localDataSet;
    private final GridItemClickListener clickListener;

    public GridItemsAdapter(List<GridItem> data, GridItemClickListener listener) {
        localDataSet = data;
        clickListener = listener;
    }

    // represents a grid item
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;
        private final ImageView imageView;

        public ViewHolder(View view) {
            super(view);
            textView = view.findViewById(R.id.text);
            imageView = view.findViewById(R.id.image);
        }

        public TextView getTextView() {
            return textView;
        }

        public ImageView getImageView() {
            return imageView;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_item, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        final @StringRes int name = localDataSet.get(position).getName();
        final @DrawableRes int resource = localDataSet.get(position).getDrawableRes();
        viewHolder.getTextView().setText(name);
        viewHolder.getImageView().setImageDrawable(
                AppCompatResources.getDrawable(viewHolder.imageView.getContext(), resource)
        );
        viewHolder.itemView.setOnClickListener(
                clickedView -> clickListener.onItemClick(localDataSet.get(position))
        );
    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}

