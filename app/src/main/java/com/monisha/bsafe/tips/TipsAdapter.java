package com.monisha.bsafe.tips;

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
import com.monisha.bsafe.tips.data.TipItem;

import java.util.List;

public class TipsAdapter extends RecyclerView.Adapter<TipsAdapter.ViewHolder> {

    private final List<TipItem> localDataSet;

    public TipsAdapter(List<TipItem> data) {
        localDataSet = data;
    }

    // viewholder means row in this case
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView heading;
        private final ImageView headingImage;
        private final TextView tip;

        public ViewHolder(View view) {
            super(view);
            heading = view.findViewById(R.id.heading);
            headingImage = view.findViewById(R.id.image);
            tip = view.findViewById(R.id.tip);
        }

        public TextView getHeading() {
            return heading;
        }

        public ImageView getHeadingImage() {
            return headingImage;
        }

        public TextView getTip() {
            return tip;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.tips_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        final @StringRes int heading = localDataSet.get(position).getHeading();
        final @DrawableRes int headingImage = localDataSet.get(position).getHeadingImage();
        final @StringRes int tip = localDataSet.get(position).getTip();

        if (heading != -1) { // means this row is a heading row
            viewHolder.getHeading().setVisibility(View.VISIBLE);
            viewHolder.getHeadingImage().setVisibility(View.VISIBLE);
            viewHolder.getTip().setVisibility(View.GONE);

            viewHolder.getHeading().setText(heading); // assigning the text
            viewHolder.getHeadingImage().setImageDrawable(
                    AppCompatResources.getDrawable(viewHolder.headingImage.getContext(), headingImage)
            );
        } else { // means this is a tip row
            viewHolder.getHeading().setVisibility(View.GONE);
            viewHolder.getHeadingImage().setVisibility(View.GONE);
            viewHolder.getTip().setVisibility(View.VISIBLE);

            viewHolder.getTip().setText(tip);
        }
    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}

