package com.monisha.bsafe.selfdefense;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.monisha.bsafe.R;
import com.monisha.bsafe.selfdefense.data.EmergencyButtonClickListener;
import com.monisha.bsafe.selfdefense.data.SelfDefenseItem;

import java.util.List;

public class SelfDefenseAdapter extends RecyclerView.Adapter<SelfDefenseAdapter.ViewHolder> {

    private final List<SelfDefenseItem> localDataSet;
    private final EmergencyButtonClickListener listener;

    public SelfDefenseAdapter(List<SelfDefenseItem> data, EmergencyButtonClickListener listener) {
        localDataSet = data;
        this.listener = listener;
    }

    // viewholder means row in this case
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView title;
        private final ImageView image;
        private final Button number;

        public ViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.title);
            image = view.findViewById(R.id.image);
            number = view.findViewById(R.id.number);
        }

        public TextView getTitle() {
            return title;
        }

        public ImageView getImage() {
            return image;
        }

        public Button getNumber() {
            return number;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.self_defense_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        final @StringRes int title = localDataSet.get(position).getTitle();
        final int image = localDataSet.get(position).getPicture();
        final String number = localDataSet.get(position).getNumber();

        if (number.isEmpty()) { // means this row is a self-defense row
            viewHolder.getTitle().setVisibility(View.VISIBLE);
            viewHolder.getImage().setVisibility(View.VISIBLE);
            viewHolder.getNumber().setVisibility(View.GONE);

            viewHolder.getTitle().setText(title); // assigning the text
            Glide.with(viewHolder.image.getContext()).asGif()
                    .load(image).into(viewHolder.image);
        } else { // means this is a emergency number row
            viewHolder.getTitle().setVisibility(View.GONE);
            viewHolder.getImage().setVisibility(View.VISIBLE);
            viewHolder.getNumber().setVisibility(View.VISIBLE);

            viewHolder.getNumber().setText(title); // assigning the text
            viewHolder.getImage().setImageDrawable(
                    AppCompatResources.getDrawable(viewHolder.image.getContext(), image)
            );
            viewHolder.getNumber().setOnClickListener(view -> {
                listener.onItemClick(number);
            });
        }
    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }
}

